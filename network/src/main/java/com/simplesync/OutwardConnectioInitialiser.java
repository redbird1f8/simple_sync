package com.simplesync;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class OutwardConnectioInitialiser extends io.netty.channel.ChannelInitializer<NioServerSocketChannel> {
    @Override
    protected void initChannel(NioServerSocketChannel ch) throws Exception {
        ChannelPipeline pl = ch.pipeline();
        pl.addLast("encoder", new ObjectEncoder());
        pl.addLast("decoder",new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
    }
}
