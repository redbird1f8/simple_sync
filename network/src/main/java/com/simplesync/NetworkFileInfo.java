package com.simplesync;

import java.io.IOException;

public class NetworkFileInfo extends FileInfo {

    public RemotePeer origin;

    /**
     * @param path
     * @param lmDate
     * @param crDate
     * @param orig
     */
    public NetworkFileInfo(String path, String relPath, long lmDate, long crDate, RemotePeer orig) {
        super(path, relPath, lmDate, crDate);
        this.origin = orig;
    }

    public NetworkFileInfo(String absolutePath, String relativePath) throws IOException {
        super(absolutePath, relativePath);
    }
}
