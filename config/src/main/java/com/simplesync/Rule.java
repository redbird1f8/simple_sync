package com.simplesync;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rule)) return false;
        Rule rule = (Rule) o;
        return priority == rule.priority &&
                Objects.equals(method, rule.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, priority);
    }
}
