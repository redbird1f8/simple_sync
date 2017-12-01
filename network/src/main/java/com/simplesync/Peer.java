package com.simplesync;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.ArrayList;


public class Peer implements NetworkEventListener {
    NetworkEventProducer networkEvents;
    ArrayList<RemotePeer> connections = new ArrayList<>();
    RemotePeerConnector BasicConnector = new RemotePeerConnector(0);

    public class RemotePeerConnector implements GenericFutureListener<ChannelFuture> {
        int channelNumber;

        public RemotePeerConnector(int channelNumber) {
            this.channelNumber = channelNumber;
        }

        @Override// типа событие
        public void operationComplete(ChannelFuture future) throws Exception {
            System.out.println("somene tried to connect " + future.isDone() + "   " + future.isSuccess());
            System.out.println(future.cause());

            if (future.isDone() && future.isSuccess()) {
                if (channelNumber == 0) {
                    connections.add(new RemotePeer((NioServerSocketChannel) future.channel()));
                    System.out.println(connections.size());

                }
            }
        }
    }

    public void connect(String host, int port) throws InterruptedException {
        RemotePeer rp = new RemotePeer(host, port);
        rp.AddListener(this);
        rp.connectCommand();
    }

    public void actionPerformed(NetworkEvent e) {
        if (e instanceof RemotePeerEvent) {// ToDo: мсправить
            RemotePeerEvent rpe = (RemotePeerEvent) e;
            if (rpe.connected) {
                connections.add(rpe.getSource());
                System.out.println("YEAAAAAASSSSS!!!");
            }
        }
    }

    public void listen(int port) {// ждёт новых подключений
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bstrap = new ServerBootstrap()
                .group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new InwardConnectionInitialiser());
        ChannelFuture cf = bstrap.bind(port);
        cf.addListener(BasicConnector);
/*
        EventLoopGroup dataInBoss = new NioEventLoopGroup();
        ServerBootstrap dinstrap = new ServerBootstrap()
                .group(dataInBoss, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new InwardConnectionInitialiser());
        dinstrap.bind(port + 1);

        EventLoopGroup dataOutBoss = new NioEventLoopGroup();
        ServerBootstrap doutstrap = new ServerBootstrap()
                .group(dataOutBoss, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new InwardConnectionInitialiser());
        doutstrap.bind(port + 2);*/


    }
}
