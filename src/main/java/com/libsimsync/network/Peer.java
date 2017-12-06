package com.libsimsync.network;
import io.netty.bootstrap.ServerBootstrap;

import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.UUID;


public class Peer{
    public String debugName;

    EventLoopGroup bossGroup;
    EventLoopGroup workerGroup;
    EventLoopGroup dataInBoss;
    EventLoopGroup dataOutBoss;

    NetworkEventProducer networkEvents = new NetworkEventProducer();
    ArrayList<RemotePeer> connections = new ArrayList<>();


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
                p.addLast("handler",rp.commandHandler);
                signFor(rp);
                //addPeer(rp);
                rp.sendCommand(1,null);


            }
            else {
                if(channelNumber == 1) {

                    for(int i = 0; i < connections.size(); i++){
                        if(connections.get(i).host.toLowerCase().equals(ch.remoteAddress().getAddress().toString())){
                            connections.get(i).setInputChannel((NioSocketChannel) ch);
                            System.err.println(debugName + ": found it!");
                            p.addLast("handler",connections.get(i).dataInHandler);
                            break;
                        }
                    }
                }
                if(channelNumber == 2) {
                    for(int i = 0; i < connections.size(); i++){
                        if(connections.get(i).host.toLowerCase().equals(ch.remoteAddress().getAddress().toString())){
                            connections.get(i).setOutputChannel((NioSocketChannel)ch);
                            System.err.println(debugName + ": found it!");
                            p.addLast("handler",connections.get(i).dataOutHandler);
                            break;
                        }
                    }
                }
            }
        }
    }
    MyChannelInitializer inboundConnectionInitializer = new MyChannelInitializer(0);
    MyChannelInitializer dataInConnectionInitializer  = new MyChannelInitializer(1);
    MyChannelInitializer dataOutConnectionInitializer = new MyChannelInitializer(2);
    class PeerEventAdapter extends NetworkEventListenerAdapter{
        @Override
        public void newPeerConnection(RemotePeer remotePeer) {
            addPeer(remotePeer);
        }

        @Override
        public void connectionEstablished(RemotePeer remotePeer) {
            networkEvents.connectionEstablishedFire(remotePeer);
        }
        @Override
        public void userCommand(Command cmd, RemotePeer src){
            networkEvents.userCommandFire(cmd,src);
        }
    }
    PeerEventAdapter peerEventAdapter = new PeerEventAdapter();
    PathRouter pathRouter = new SimplePathRouter();

    public void setPathRouter(PathRouter pathRouter){
        this.pathRouter = pathRouter;
        for(RemotePeer i:connections){
            i.setPathRouter(pathRouter);
        }
    }
    public void addListener(NetworkEventListener nel){
        networkEvents.addListener(nel);
    }
    public void addPeer(RemotePeer rp){
        rp.debugName = debugName;
        connections.add(rp);
        rp.setPathRouter(pathRouter);
        System.err.println(connections.size() + " peers are connected");
    }
    public void signFor(RemotePeer rp){
        rp.NetworkEvents.addListener(peerEventAdapter);
    }
    public void shutDown() {
        for(RemotePeer i : connections)i.shutDown();
        if(bossGroup != null)bossGroup.shutdownGracefully();
        if(workerGroup != null)workerGroup.shutdownGracefully();
        if(dataInBoss != null)dataInBoss.shutdownGracefully();
        if(dataOutBoss != null)dataOutBoss.shutdownGracefully();
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

    public void request(String name, UUID shareID) throws FileNotFoundException {
        for(int i = 0; i < connections.size(); i++){
            connections.get(i).requestFile(name, shareID);
        }
} //for debug only
    public void connect(String host, int port){
        RemotePeer rp = new RemotePeer(host, port);
        rp.AddListener(peerEventAdapter);
        rp.connect();
    }
    public void listen(int port){
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        dataInBoss = new NioEventLoopGroup();
        dataOutBoss = new NioEventLoopGroup();

        ServerBootstrap bstrap = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(inboundConnectionInitializer);
        bstrap.bind(port);
        ServerBootstrap dinstrap = new ServerBootstrap()
                .group(dataInBoss, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(dataInConnectionInitializer);
        dinstrap.bind(port + 1);
        ServerBootstrap doutstrap = new ServerBootstrap()
                .group(dataOutBoss, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(dataOutConnectionInitializer);
        doutstrap.bind(port + 2);


    }


}
