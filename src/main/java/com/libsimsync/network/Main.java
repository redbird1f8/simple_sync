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
import java.util.Scanner;

import java.io.FileNotFoundException;

import static java.lang.Thread.sleep;

        class Main {

            public static void main(String[] args) throws InterruptedException, FileNotFoundException, java.io.IOException {

                exampleForNikita a = new exampleForNikita("./A");
                exampleForNikita b = new exampleForNikita("./B");

                a.listen();
                b.connect("localhost");
                Scanner in = new Scanner(System.in);
                for(int i = 0; true; i++) {
                    in.next();
                    b.sync();
                }
                /*
                Peer first =  new Peer();
                Peer second = new Peer();
                //Peer third  = new Peer();

                first.debugName  = "first";
                second.debugName = "second";

                second.listen(61020);
                first.listen(61040);
                first.connect("localhost",61020);
                sleep(5000);
                first.request("./test.rar", null);
                */

            }
        }
