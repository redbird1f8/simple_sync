package com.libsimsync.config;

public class Rule {
    private ResolvingMethod method;
    private boolean priority;

    public Rule(ResolvingMethod method, boolean priority){ //public is actually not temporary
        setPriority(priority);
        this.method = method;
    }

    public ResolvingMethod getMethod() {
        return method;
    }

    public boolean getPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }
}
