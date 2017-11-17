package com.libsimsync.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Share {
    final List<Device> acceptedDevices;
    private String name;
    private final UUID uuid;
    Path rootPath;
    FileLister files;


    Share(List<Device> devices, String name, UUID id, Path root, FileLister files){
        acceptedDevices = devices;
        this.name = name;//TODO need setName
        uuid = id;
        rootPath = root;
        this.files = files;
    }

    public Share(List<Device> devices, String name, Path root, FileLister files){
        acceptedDevices = devices;
        this.name = name;//TODO need setName
        uuid = UUID.randomUUID();
        rootPath = root;
        this.files = files;
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

    public Iterator<FileEntry> GetFileIterator(){
        return files.getIterator();
    }


}
