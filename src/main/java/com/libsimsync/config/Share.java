package com.libsimsync.config;

import java.util.LinkedList;
import java.util.UUID;

public class Share {
    private LinkedList<Device> acceptedDevices;
    private String name;
    private UUID uuid;
    private FileLister files;

    public boolean isAccepted(Device device){
        if(acceptedDevices.contains(device)){
            return true;
        }
        return false;
    }
}
