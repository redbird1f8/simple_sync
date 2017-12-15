package com.simplesync;

import java.util.ArrayList;
import java.util.UUID;



/**
 * Создатель событий
 */
class NetworkEventProducer{
    private ArrayList<NetworkEventListener> listeners = new ArrayList<>();

    public void addListener(NetworkEventListener listener){
        listeners.add(listener);
    }
    public void newPeerConnectionFire(RemotePeer rp){
        for(int i = 0; i < listeners.size(); i++){
            listeners.get(i).newPeerConnection(rp);
        }
    }
    public void connectionEstablishedFire(RemotePeer rp){
        for(int i = 0; i < listeners.size(); i++){
            listeners.get(i).connectionEstablished(rp);
        }
    }
    public void connectionLostFire(RemotePeer rp){
        for(int i = 0; i < listeners.size(); i++){
            listeners.get(i).connectionLost(rp);
        }
    }
    public void userCommandFire(Command command, RemotePeer rp){
        for(int i = 0; i < listeners.size(); i++){
            listeners.get(i).userCommand(command,rp);
        }
    }
    public void fileRequestedFire(String path, UUID shareID){
        for(int i = 0; i < listeners.size(); i++){
            listeners.get(i).fileRequested(path, shareID);
        }
    }
}

