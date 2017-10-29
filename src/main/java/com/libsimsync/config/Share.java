package com.libsimsync.config;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.UUID;

public class Share {
    final LinkedList<Device> acceptedDevices;
    private String name;
    private final UUID uuid;
    Path rootPath;
    FileLister files;


    Share(){  //TODO
        acceptedDevices = null;
        uuid = null;
    }
    public String getName() {
        return name;
    }

    public UUID getUUID() {
        return uuid;
    }

    public boolean isAccepted(Device device){
        if(acceptedDevices.contains(device)){
            return true;
        }
        return false;
    }

    public FileLister.Iterator GetFileIterator(){
        return files.getIterator();
    }
}
