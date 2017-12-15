package com.simplesync;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
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

    /**
     * Конструктор, предполагающий что все файлы подлежат синхронизации
     * и используется правило по умолчанию
     * @param name Имя шары
     * @param root корневая директория шары
     */
    public Share(String name, Path root){
        acceptedDevices = new ArrayList<>(5);
        this.name = name;
        uuid = UUID.randomUUID();
        rootPath = root;
        files = new FileLister(rootPath);
    }

    public String getName() {
        return name;
    }

    public UUID getUUID() {
        return uuid;
    }

    public List<Device> getAcceptedDevices() {
        return acceptedDevices;
    }

    public void addDevice(Device device){
        acceptedDevices.add(device);
    }

    public void removeDevice(Device device){
        acceptedDevices.add(device);
    }

    public boolean isAccepted(Device device){
        if(acceptedDevices.contains(device)){
            return true;
        }
        return false;
    }

    public Iterator<FileEntry> getFileIterator(){
        return files.getIterator();
    }

    public List<FileEntry> getFileList(){
        return files.getFileList();
    }




}
