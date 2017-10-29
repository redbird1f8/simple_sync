package com.libsimsync.config;

import java.util.UUID;

public class Device {
    private String name;
    private UUID id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;//do we really need this?
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Device)
            return id.equals(((Device)obj).getId());
        else
            return false;
    }
}
