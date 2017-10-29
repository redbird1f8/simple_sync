package com.libsimsync.config;

import java.util.LinkedList;

public class CommonConfig {
    private Rule defaultRules;  //needs to be final??
    private LinkedList<Share> shareList;
    private final Device thisDevice;

    CommonConfig(Rule rule, Device device){ //TODO there should be builder. No public constructors.
        defaultRules = rule;
        thisDevice = device;
    }

    public Rule getRules(){
        return defaultRules;
    }
}
