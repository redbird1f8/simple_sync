package com.simplesync.nconf;

import java.util.ArrayList;
import java.util.List;

public class SymShare {
    List<SyncDevice> acceptedDevices;
    String rootPath;

    public SymShare() {
        acceptedDevices = new ArrayList<>();
        rootPath = System.getProperty("user.dir"); //??
    }

    public SymShare(String root) {
        rootPath = root;
        acceptedDevices = new ArrayList<>();
    }

    public SymShare(String root, List<SyncDevice> devices) {
        rootPath = root;
        acceptedDevices = devices;
    }

    public List<SyncDevice> getDevices() {
        return acceptedDevices;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void addDevice(SyncDevice newDevice) {
        acceptedDevices.add(newDevice);
    }

    public void setDevices(List<SyncDevice> acceptedDevices) {
        this.acceptedDevices = acceptedDevices;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }


}
