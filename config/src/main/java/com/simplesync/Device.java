package com.simplesync;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.UUID;

public class Device implements Serializable {

    private String name;
    private UUID id;
    private InetAddress currentAddress = null;

    Device(String name, UUID id){
        setName(name);
        this.id = id;
    }

    public Device(String name){
        setName(name);
        this.id = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public InetAddress getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(InetAddress addr){
        currentAddress = addr;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Device)
            return id.equals(((Device)obj).getId());
        else
            return false;
    }
}
