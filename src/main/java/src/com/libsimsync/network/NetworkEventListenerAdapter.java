package com.libsimsync.network;

import java.util.UUID;

public class NetworkEventListenerAdapter implements NetworkEventListener{
    public void newPeerConnection(RemotePeer remotePeer){}
    public void connectionEstablished(RemotePeer remotePeer){}
    public void connectionLost(RemotePeer remotePeer){}
    public void userCommand(Command command, RemotePeer remotePeer){}
    public void fileRequested(String path, UUID ShareID){}

}
