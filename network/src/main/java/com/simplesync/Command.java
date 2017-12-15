package com.simplesync;

public class Command implements java.io.Serializable {
    int commandID;
    Object args;

    public Command(int commandID, Object args) {
        this.commandID = commandID;
        this.args = args;
    }

    @Override
    public String toString() {
        return args.toString();
    }
}
