package com.libsimsync.network;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.UUID;
import com.libsimsync.config.FileEntry;
import com.libsimsync.config.FileInfo;

//20-ая команда - запрос файл инфо
//21-ая команда - посылка файл инфо
public class PeerManager implements PathRouter{



    class PeerManagerEventAdapter extends NetworkEventListenerAdapter {
        @Override
        public void userCommand(Command command, RemotePeer remotePeer) {
            if(command.commandID == 20)peerManagerHandler.FileInfoRequested(remotePeer);
            if(command.commandID == 21)peerManagerHandler.FileInfoArrived((LinkedList<FileInfo>)command.args,remotePeer);
        }
    }
    PeerManagerEventAdapter peerManagerEventAdapter = new PeerManagerEventAdapter();
    private String rootDirectory = ".";
    private PeerManagerHandler peerManagerHandler;
    Peer peer;
    public  PeerManager(){
        peer = new Peer();
        peer.setPathRouter(this);
        peer.addListener(peerManagerEventAdapter);
    }

    public void listen(){
        peer.listen(61020);
    }

    public void connect(String host){ peer.connect(host,61020);}
    @Override
    public String getAbsolutePath(UUID ShareID, String relativePath) {
        return rootDirectory+relativePath;
    }

    public String getRootDirectory() {
        return rootDirectory;
    }
    public void setPeerManagerHandler(PeerManagerHandler peerManagerHandler) {
        this.peerManagerHandler = peerManagerHandler;
    }
    public void setRootDirectory(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }
    public void shutDown(){
        peer.shutDown();
    }
    public void requestFile(FileEntry file, UUID shareID) throws FileNotFoundException {
        //Пока что шара только одна
        peer.request(file.getPath().toString(),shareID);
    }
    public void requestFileInfo(){
        peer.sendCommand(20,null);
    }
    public void sendFileInfo(LinkedList<FileInfo> fileInfos,RemotePeer remotePeer){
        peer.sendCommand(21,fileInfos);
    }





}
