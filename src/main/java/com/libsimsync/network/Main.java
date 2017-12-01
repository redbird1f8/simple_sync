package com.libsimsync.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import static java.lang.Thread.sleep;

        class Main {

            public static void main(String[] args) throws InterruptedException {

                Peer first =  new Peer();
                Peer second = new Peer();
                Peer third  = new Peer();

                first.listen(61005);
/*
                third.connect("localhost",61005);
                second.connect("localhost",61005);

                sleep(200);
                //first.sendObject(new Command(0,new String("ololol")));
                first.sendStr("everything is working");
                first.sendCommand(0,"yes, it is");
                second.sendStr("wow");*/

            }
        }
