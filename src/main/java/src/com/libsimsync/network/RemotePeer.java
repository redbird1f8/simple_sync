package com.libsimsync.network;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import java.util.LinkedList;

import java.io.*;
import java.util.UUID;

class FullName implements Serializable{
    UUID shareID;
    String name;
    FullName(UUID shareID, String name){
        this.shareID = shareID;
        this.name    = name;
    }
}
public class RemotePeer {
    public String            debugName;
    public String            host;
    public int               port;

    private EventLoopGroup    group;
    private NioEventLoopGroup group1;
    private NioEventLoopGroup group2;

    private NioSocketChannel commandChannel;
    private NioSocketChannel inputChannel;
    private NioSocketChannel outputChannel;

    NetworkEventProducer NetworkEvents;


    boolean commandChannelConnected = false;
    boolean inputChannelConnected   = false;
    boolean outputChannelConnected  = false;



    DataInputStream  currentOutputFileStream = null;
    DataOutputStream currentInputFileStream  = null;
    File             currentOutputFile       = null;
    File             currentInputFile        = null;
    BlockSender      blockSender             = new BlockSender();
    PathRouter pathRouter = new SimplePathRouter();//ToDo jhj fjdjgdl
    LinkedList<FileTransfer> FileQueue = new LinkedList<>();
    class OutboundConnectionInitializer extends ChannelInitializer<SocketChannel>{
        int channelNumber;
        public  OutboundConnectionInitializer(int channelNumber){
            this.channelNumber = channelNumber;
        }
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();

            if(channelNumber == 0) {
                pipeline.addLast("objectDecoder", new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                pipeline.addLast("objectEncoder", new ObjectEncoder());
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
        boolean firstBlock = true;
        private long byteCount       = 0;//Костыль ToDo разкостылить
        private long currentFileSize = 0;
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {

            if(channelNumber == 0) {
                commandChannel = (NioSocketChannel) ctx.channel();
                stateChange(true);
                commandChannelConnected = true;
            }
            else if(channelNumber == 1) {
                inputChannel = (NioSocketChannel) ctx.channel();
                inputChannel.pipeline().addFirst("framer", new FixedLengthFrameDecoder(1024));
                System.err.println(debugName + ": inputChannelIsConnected!");
                inputChannelConnected = true;
            }
            else if(channelNumber == 2) {
                outputChannel = (NioSocketChannel) ctx.channel();
                outputChannel.pipeline().addFirst("framer", new FixedLengthFrameDecoder(1024));
                System.err.println(debugName + ": outputChannelIsConnected!");
                outputChannelConnected = true;
            }
            if(commandChannelConnected && inputChannelConnected && outputChannelConnected){
                connectionEstablished();
            }
        }
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
            //System.err.println(debugName + ": channelRead channel number:" + channelNumber);
            if(channelNumber == 0) {
                Command com = (Command)msg;
                //System.err.println(debugName + ": CommandID: " + com.commandID);
                if(com.commandID == 0){
                    System.err.println(msg);
                }
                else if(com.commandID == 1) connectData();
                else if(com.commandID == 2) {
                    FullName fullName = (FullName)com.args;
                    answerRequest(fullName.name, fullName.shareID);
                } else {
                    userCommand((Command)msg);
                }
            }
            if(channelNumber == 1) {
                //System.err.println(debugName + ": block received");
                ByteBuf b = (ByteBuf)msg;
                byte[] dest = new byte[1024];
                if(firstBlock){
                     currentFileSize = b.getLong(0);
                     System.err.println("in size" + currentFileSize);
                     b.readBytes(dest);
                     currentInputFileStream.write(dest,8,1016);
                     firstBlock = false;
                     byteCount += 1016;
                }else{
                    if(currentFileSize - byteCount > 1024) {
                        b.readBytes(dest);
                        currentInputFileStream.write(dest, 0, 1024);
                        byteCount += 1024;
                    }else{
                        b.readBytes(dest,0,(int)(currentFileSize-byteCount));
                        currentInputFileStream.write(dest, 0, (int)(currentFileSize-byteCount));
                        byteCount = 0;
                        currentFileSize = 0;
                        firstBlock = true;
                        currentInputFileStream.close();
                        FileQueue.pollFirst();
                        if(FileQueue.size()>0)FileQueue.getFirst().start();

                    }
                }
            }
        }
    }
    class BlockSender implements ChannelFutureListener{
        public void sendFirst() throws Exception {
            byte[] block = new byte[1024];
            long length = currentOutputFile.length();
            System.err.println("out size" + length);
            if((currentOutputFileStream.read(block,8,1016)) > 0) {
                ByteBuf toSend = Unpooled.wrappedBuffer(block);
                toSend.setLong(0,length);
                ChannelFuture cf = outputChannel.writeAndFlush(toSend);
                cf.addListener(this);
            }
        }
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {

            byte[] block = new byte[1024];
            int read = currentOutputFileStream.read(block,0,1024);
            if(read > 0) {
                ChannelFuture cf = outputChannel.writeAndFlush(Unpooled.wrappedBuffer(block));
                cf.addListener(this);
            }else{
                currentOutputFileStream.close();
            }

        }
    }
    public class FileTransfer {
        String name;
        String path;
        UUID shareID;
        public FileTransfer(String path,String name,UUID shareID){
            this.name = name;
            this.path = path;
            this.shareID = shareID;
        }
        public void start() throws FileNotFoundException {
            currentInputFile       = new File(path);
            String[] dirs = path.split("/");
            String parentDirs = "";
            for(int i = 0; i < dirs.length - 1; i++){
                parentDirs +=  dirs[i] + '/';
            }
            System.err.println("parent dir - " + parentDirs);
            File parentDir = new File(parentDirs);
            parentDir.mkdirs();
            currentInputFileStream = new DataOutputStream(new FileOutputStream(currentInputFile));
            FullName fullName = new FullName(shareID,name);
            sendCommand(2,fullName);

        }
    }

    public OutboundConnectionHandler commandHandler = new OutboundConnectionHandler(0);
    public OutboundConnectionHandler dataInHandler = new OutboundConnectionHandler(1);
    public OutboundConnectionHandler dataOutHandler = new OutboundConnectionHandler(2);
    public RemotePeer(NioSocketChannel commandChannel){
        this.commandChannel = commandChannel;
        this.host = commandChannel.remoteAddress().getAddress().toString();
        this.port = commandChannel.remoteAddress().getPort();
        System.err.println(port);
        NetworkEvents = new NetworkEventProducer();
    }
    public RemotePeer(String host, int port){
        this.host = host;
        this.port = port;
        NetworkEvents = new NetworkEventProducer();
    }
    public void setPathRouter(PathRouter pathRouter){
        this.pathRouter = pathRouter;
    }

    public void connectData(){

        group1 = new NioEventLoopGroup();
        group2 = new NioEventLoopGroup();
        Bootstrap newIn = new Bootstrap();
        newIn.group(group1)
                .channel(NioSocketChannel.class)
                .handler(new OutboundConnectionInitializer(1));
        newIn.connect(host, port+2);

        Bootstrap newOut = new Bootstrap();
        newOut.group(group2)
                .channel(NioSocketChannel.class)
                .handler(new OutboundConnectionInitializer(2));
        newOut.connect(host, port+1);

    }
    public void setInputChannel(NioSocketChannel channel){
        System.err.println(debugName + ": DataInChannel set");
        inputChannel = channel;
    }
    public void setOutputChannel(NioSocketChannel channel){
        System.err.println(debugName + ": DataOutChannel set");
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
    public void userCommand(Command cmd){
        NetworkEvents.userCommandFire(cmd,this);
    }
    public void stateChange(boolean connected){
            if(connected)NetworkEvents.newPeerConnectionFire(this);
            //остальные состояния
    }
    public void connectionEstablished(){
        NetworkEvents.connectionEstablishedFire(this);
    }
    public void AddListener(NetworkEventListener networkEventListener){
        NetworkEvents.addListener(networkEventListener);
    }
    public void answerRequest(String name, UUID shareID) throws Exception {
        currentOutputFile       = new File(pathRouter.getAbsolutePath(shareID,name));
        System.err.println("sending file" + pathRouter.getAbsolutePath(shareID,name));
        currentOutputFileStream = new DataInputStream(new FileInputStream(currentOutputFile));
        blockSender.sendFirst();
    }

    /**
     *
     */
    public void connect(){

        group = new NioEventLoopGroup();
        Bootstrap newConnection = new Bootstrap();
        newConnection.group(group)
                .channel(NioSocketChannel.class)
                .handler(new OutboundConnectionInitializer(0));
        newConnection.connect(host, port);

    }

    /**
     *
     *
     *
     * @param name
     * @param ShareID
     * @throws FileNotFoundException
     */
    public void requestFile(String name, UUID ShareID) throws FileNotFoundException {
        System.out.println("root   " + pathRouter.getAbsolutePath(ShareID,""));
        FileTransfer ft = new FileTransfer(pathRouter.getAbsolutePath(ShareID,name),name,ShareID);
        FileQueue.addLast(ft);
        if(FileQueue.size() == 1) {
            ft.start();
        }
        System.err.println("Transmission requested " + pathRouter.getAbsolutePath(ShareID,name));

    }
    /**
     *
     */
    public void shutDown(){
        if(group!=null)group.shutdownGracefully();
        if(group1!=null)group1.shutdownGracefully();
        if(group2!=null)group2.shutdownGracefully();
    }

}



