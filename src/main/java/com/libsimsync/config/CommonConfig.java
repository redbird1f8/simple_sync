package com.libsimsync.config;

import java.util.LinkedList;

public class CommonConfig {
    private Rules defaultRules;  //needs to be final??
    private LinkedList<Share> shareList;
    private final Device thisDevice;

    CommonConfig(Rules rule, Device device){
        defaultRules = rule;
        thisDevice = device;
    }

    public Rules getRules(){
        return defaultRules;
    }
}