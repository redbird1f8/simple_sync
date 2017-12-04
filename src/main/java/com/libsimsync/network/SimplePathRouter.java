package com.libsimsync.network;

import java.util.UUID;

public class SimplePathRouter implements PathRouter {
    @Override
    public String getAbsolutePath(UUID ShareID, String relativePath) {
        return relativePath;
    }
}
