package com.libsimsync.network;

import com.libsimsync.config.FileInfo;
import java.util.LinkedList;

public interface PeerManagerHandler {
    public void FileInfoRequested(RemotePeer remotePeer);
    public void FileInfoArrived(LinkedList<FileInfo> fileInfos, RemotePeer remotePeer);
}
