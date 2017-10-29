package com.libsimsync.config;

public class Rule {
    private ResolvingMethod method;
    private byte priority;

    public ResolvingMethod getMethod() {
        return method;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }
}
