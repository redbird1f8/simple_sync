package com.libsimsync.network;

import java.util.UUID;

public interface PathRouter {
    String getAbsolutePath(UUID ShareID, String relativePath);
}
