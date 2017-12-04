package com.libsimsync.network;

import java.util.UUID;

public class HighLevelPeer implements PathRouter{


    @Override
    public String getAbsolutePath(UUID ShareID, String relativePath) {
        return null;
    }
    Peer peer;
    class HighLevelEventAdapter extends NetworkEventListenerAdapter {
    }

    HighLevelEventAdapter highLevelEventAdapter = new HighLevelEventAdapter();


}
