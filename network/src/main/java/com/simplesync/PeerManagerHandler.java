package com.simplesync;


import java.util.LinkedList;

public interface PeerManagerHandler {
    void FileInfoRequested(RemotePeer remotePeer);

    void FileInfoArrived(LinkedList<NetworkFileInfo> fileInfos, RemotePeer remotePeer);
}
