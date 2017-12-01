package com.libsimsync.network;
import io.netty.channel.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;


public class RemotePeer {

    public String           host;
    public int              port;
    private EventLoopGroup   group;

    private NioSocketChannel commandChannel;
    private NioSocketChannel inputChannel;
    private NioSocketChannel outputChannel;

    NetworkEventProducer NetworkEvents;

    private boolean provocer = false;
    boolean commandChannelConnected = false;
    boolean inputChannelConnected   = false;
    boolean outputChannelConnected  = false;


    class OutboundConnectionInitializer extends ChannelInitializer<SocketChannel>{
        int channelNumber;
        public  OutboundConnectionInitializer(int channelNumber){
            this.channelNumber = channelNumber;
        }
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast("objectDecoder", new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
            pipeline.addLast("objectEncoder", new ObjectEncoder());
            if(channelNumber == 0) {
                pipeline.addLast("handler", commandHandler);
            }else if(channelNumber == 1){
                pipeline.addLast("handler", dataInHandler);
            }else if(channelNumber == 2) {
                pipeline.addLast("handler", dataOutHandler);
            }

        }
    }
    class OutboundConnectionHandler extends ChannelInboundHandlerAdapter{

        int channelNumber;
        public OutboundConnectionHandler(int channelNumber){
            this.channelNumber = channelNumber;
        }
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            if(channelNumber == 0) {
                commandChannel = (NioSocketChannel) ctx.channel();
                stateChange(true);
            }
            else if(channelNumber == 1) {
                inputChannel = (NioSocketChannel) ctx.channel();
                System.out.println("inputChannelIsConnected!");
            }
            else if(channelNumber == 2) {
                outputChannel = (NioSocketChannel) ctx.channel();
                System.out.println("outputChannelIsConnected!");
            }
        }
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
            if(channelNumber == 0) {
                Command com = (Command)msg;
                if(com.commandID == 0){
                    System.out.println(msg);
                }
                else if(com.commandID == 1) connectData();
                else System.out.println("Unknown command");
            }

        }
    }
    public OutboundConnectionHandler commandHandler = new OutboundConnectionHandler(0);
    public OutboundConnectionHandler dataInHandler = new OutboundConnectionHandler(1);
    public OutboundConnectionHandler dataOutHandler = new OutboundConnectionHandler(2);
    public RemotePeer(NioSocketChannel commandChannel){
        this.commandChannel = commandChannel;
        this.host = commandChannel.remoteAddress().getHostName();
        this.port = commandChannel.remoteAddress().getPort();
        System.out.println(port);
        NetworkEvents = new NetworkEventProducer();
    }
    public RemotePeer(String host, int port){
        this.host = host;
        this.port = port;
        NetworkEvents = new NetworkEventProducer();
    }
    public void connectCommand()throws InterruptedException{
        provocer = true;
        group = new NioEventLoopGroup();
        Bootstrap newConnection = new Bootstrap();
        newConnection.group(group)
                     .channel(NioSocketChannel.class)
                     .handler(new OutboundConnectionInitializer(0));
        newConnection.connect(host, port);

    }
    public void connectData()throws InterruptedException{

        group = new NioEventLoopGroup();
        Bootstrap newIn = new Bootstrap();
        newIn.group(group)
                .channel(NioSocketChannel.class)
                .handler(new OutboundConnectionInitializer(1));
        newIn.connect(host, port+2);

        Bootstrap newOut = new Bootstrap();
        newOut.group(group)
                .channel(NioSocketChannel.class)
                .handler(new OutboundConnectionInitializer(2));
        newOut.connect(host, port+1);

    }
    public void setInputChannel(NioSocketChannel channel){
        System.out.println("DataInChannel set");
        inputChannel = channel;
    }
    public void setOutputChannel(NioSocketChannel channel){
        System.out.println("DataOutChannel set");
        outputChannel = channel;
    }
    public void sendObject(Object msg){
        commandChannel.write(msg);
        commandChannel.flush();
    }
    public void sendCommand(int id, Object arg){
        Command com = new Command(id,arg);
        commandChannel.write(com);
        commandChannel.flush();
    }
    public void stateChange(boolean connected){
        System.out.println(connected);
            NetworkEvents.actionPerformedFire(new RemotePeerEvent(this,connected));

    }
    public void AddListener(NetworkEventListener networkEventListener){
        NetworkEvents.addListener(networkEventListener);
    }
}



