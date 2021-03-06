package com.simplesync;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileManager implements PathRouter {
    private String root;

    public FileManager(String root) {
        this.root = root;
    }

    @Override
    public String getAbsolutePath(UUID ShareID, String relativePath) {
        return root + relativePath;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public void deleteFile(FileInfo file, UUID shareID) {
        File f = new File(getAbsolutePath(null, file.relPath));
        f.delete();
    }

    public NetworkFileInfo getFileInfo(String relativePath, UUID ShareID) throws IOException {
        NetworkFileInfo f = new NetworkFileInfo(getAbsolutePath(ShareID, relativePath), relativePath);
        f.isDeleted = false;
        return f;
    }
}
