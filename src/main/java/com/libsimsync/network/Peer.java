package com.libsimsync.network;
import io.netty.bootstrap.ServerBootstrap;

import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.ArrayList;


public class Peer implements NetworkEventListener{
    NetworkEventProducer networkEvents;
    ArrayList<RemotePeer> connections = new ArrayList<>();
    MyChannelInitializer inboundConnectionInitializer = new MyChannelInitializer(0);
    MyChannelInitializer dataInConnectionInitializer  = new MyChannelInitializer(1);
    MyChannelInitializer dataOutConnectionInitializer = new MyChannelInitializer(2);

    class MyChannelInitializer extends ChannelInitializer<SocketChannel>{
        int channelNumber;
        public  MyChannelInitializer(int channelNumber){
            this.channelNumber = channelNumber;
        }

        @Override
        public void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline p = ch.pipeline();
            if(channelNumber == 0) {

                RemotePeer rp = new RemotePeer((NioSocketChannel)ch);
                p.addLast("objectDecoder", new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                p.addLast("objectEncoder", new ObjectEncoder());
                p.addLast(rp.commandHandler);
                addPeer(rp);
                rp.sendCommand(1,null);


            }
            else {
                if(channelNumber == 1) {

                    for(int i = 0; i < connections.size(); i++){
                        if(connections.get(i).host.toLowerCase().equals(ch.remoteAddress().getHostName().toLowerCase())){
                            connections.get(i).setInputChannel((NioSocketChannel) ch);
                            System.err.println("found it!");
                            p.addLast(connections.get(i).dataInHandler);
                            break;
                        }
                    }
                }
                if(channelNumber == 2) {
                    for(int i = 0; i < connections.size(); i++){
                        if(connections.get(i).host.toLowerCase().equals(ch.remoteAddress().getHostName().toLowerCase())){
                            connections.get(i).setOutputChannel((NioSocketChannel)ch);
                            System.err.println("found it!");
                            p.addLast(connections.get(i).dataOutHandler);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void connect(String host, int port) throws InterruptedException{
        RemotePeer rp = new RemotePeer(host, port);
        rp.AddListener(this);
        rp.connectCommand();
    }
    public void actionPerformed(NetworkEvent e){
        if(e instanceof RemotePeerEvent) {// ToDo: мсправить
            RemotePeerEvent rpe = (RemotePeerEvent) e;
            if (rpe.connected) {
                addPeer(rpe.getSource());
            }
        }
    }
    public void addPeer(RemotePeer rp){
        connections.add(rp);
        System.out.println(connections.size() + " peers are connected");
    }
    public void listen(int port){
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bstrap = new ServerBootstrap()
                .group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(inboundConnectionInitializer);
        bstrap.bind(port);

        EventLoopGroup dataInBoss = new NioEventLoopGroup();
        ServerBootstrap dinstrap = new ServerBootstrap()
                .group(dataInBoss, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(dataInConnectionInitializer);
        dinstrap.bind(port + 1);

        EventLoopGroup dataOutBoss = new NioEventLoopGroup();
        ServerBootstrap doutstrap = new ServerBootstrap()
                .group(dataOutBoss, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(dataOutConnectionInitializer);
        doutstrap.bind(port + 2);


    }
    public void sendStr(String str){
        sendCommand(0,str);
    }
    public void sendObject(Object msg){
        for(int i = 0; i < connections.size(); i++){
            connections.get(i).sendObject(msg);
        }
    }

    public void sendCommand(int id, Object args){
        Command toSend = new Command(id,args);
        for(int i = 0; i < connections.size(); i++){
            connections.get(i).sendObject(toSend);
        }
    }
}
