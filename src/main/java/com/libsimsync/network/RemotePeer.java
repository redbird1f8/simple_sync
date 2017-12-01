package com.libsimsync.network;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.DefaultChannelPromise;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.EventObject;

class Command{
    String debugMsg;
    int action;
    ArrayList<Object> arguments;
}

public class RemotePeer {

    private String           host;
    private int              port;
    private EventLoopGroup   group;

    private NioServerSocketChannel commandChannel;
    private NioServerSocketChannel inputChannel;
    private NioServerSocketChannel outputChannel;

    NetworkEventProducer NetworkEvents;

    private ChannelConnector commandChannelConnector;
    private ChannelConnector inputChannelConnector;
    private ChannelConnector outputChannelConnector;

    boolean commandChannelConnected = false;
    boolean inputChannelConnected   = false;
    boolean outputChannelConnected  = false;

    class ChannelConnector implements GenericFutureListener<ChannelFuture> {
        int channelNumber;
        public ChannelConnector(int channelNumber){
            this.channelNumber = channelNumber;
        }
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            if(future.isDone() && future.isSuccess()) {
                if (channelNumber == 0) {
                    commandChannel = (NioServerSocketChannel) future.channel();
                    commandChannelConnected = true;
                }else if(channelNumber == 1){
                    inputChannel =  (NioServerSocketChannel) future.channel();
                    inputChannelConnected = true;
                }else if(channelNumber == 2){
                    outputChannel = (NioServerSocketChannel) future.channel();
                    outputChannelConnected = true;
                }
            }

            if(commandChannelConnected /*&& inputChannelConnected && outputChannelConnected*/){
                stateChange(true);            }
        }
    }
    public RemotePeer(String host, int port){
        this.host = host;
        this.port = port;
        group = new NioEventLoopGroup();
        commandChannelConnector = new ChannelConnector(0);// создаем типа делегаты
        inputChannelConnector   = new ChannelConnector(1);
        outputChannelConnector  = new ChannelConnector(2);
        NetworkEvents = new NetworkEventProducer();
    }

    public RemotePeer(NioServerSocketChannel commandChannel){
        this.commandChannel = commandChannel;
    }
    public void connectCommand()throws InterruptedException{
        Bootstrap newConnection = new Bootstrap();
        newConnection.group(group)
                     .channel(NioServerSocketChannel.class)
                     .handler(new OutwardConnectioInitialiser());

        ChannelFuture cf = newConnection.connect(host, port);
        cf.addListener(commandChannelConnector);
    }



    public void command(String msg){
        commandChannel.write(msg);
    }
    public void stateChange(boolean connected){
        System.out.println("connected " + connected);
            NetworkEvents.actionPerformedFire(new RemotePeerEvent(this,connected));// опреос всех листнеров и вызывает actionPerformed

    }
    public void AddListener(NetworkEventListener networkEventListener){
        NetworkEvents.addListener(networkEventListener);
    }
}



