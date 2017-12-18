package com.simplesync;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.UUID;

//20-ая команда - запрос файл инфо
//21-ая команда - посылка файл инфо
public class PeerManager implements PathRouter {


    class PeerManagerEventAdapter extends NetworkEventListenerAdapter {
        @Override
        public void userCommand(Command command, RemotePeer remotePeer) {
            if (command.commandID == 20) {
                peerManagerHandler.FileInfoRequested(remotePeer);
            }
            if (command.commandID == 21) {
                peerManagerHandler.FileInfoArrived((LinkedList<NetworkFileInfo>) command.args, remotePeer);
            }
        }
    }

    PeerManagerEventAdapter peerManagerEventAdapter = new PeerManagerEventAdapter();
    private String rootDirectory;
    private PeerManagerHandler peerManagerHandler;
    private PathRouter pathRouter;
    Peer peer;

    public PeerManager() {
        peer = new Peer();
        peer.setPathRouter(pathRouter);
        peer.addListener(peerManagerEventAdapter);
    }

    public void listen() {
        peer.listen(61020);
    }

    public void connect(String host) {
        peer.connect(host, 61020);
    }

    @Override
    public String getAbsolutePath(UUID ShareID, String relativePath) {
        return rootDirectory + relativePath;//relativePath;
    }

    public String getRootDirectory() {
        return rootDirectory;
    }

    public void setPeerManagerHandler(PeerManagerHandler peerManagerHandler) {
        this.peerManagerHandler = peerManagerHandler;
    }

    /*public void setRootDirectory(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }*/
    public void setPathRouter(PathRouter pathRouter) {
        this.pathRouter = pathRouter;
        peer.setPathRouter(pathRouter);
    }

    public void shutDown() {
        peer.shutDown();
    }

    public void requestFile(FileEntry file, UUID shareID, RemotePeer remotePeer) throws FileNotFoundException {
        //Пока что шара только одна
        peer.request(file.getPath().toString(), shareID);
    }

    public void requestFile(FileInfo file, UUID shareID, RemotePeer remotePeer) throws FileNotFoundException {
        //Пока что шара только одна
        peer.request(file.relPath, shareID);
    }

    public void requestFileInfo() {
        peer.sendCommand(20, null);
    }

    public void sendFileInfo(LinkedList<NetworkFileInfo> fileInfos, RemotePeer remotePeer) {
        peer.sendCommand(21, fileInfos);
    }
}
