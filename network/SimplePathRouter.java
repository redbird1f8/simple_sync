package com.libsimsync.network;

import java.util.UUID;

public class SimplePathRouter implements PathRouter {
    private String root;
    public SimplePathRouter(String root){
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
}
