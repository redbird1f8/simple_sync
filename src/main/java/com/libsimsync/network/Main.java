package com.libsimsync.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

        class Main {
            public static void main(String[] args) throws InterruptedException {
                Peer first =  new Peer();
                Peer second = new Peer();
                Peer third  = new Peer();

                first.listen(9100);
                second.connect("localhost",9100);
                third.connect("localhost",9100);

            }
        }
