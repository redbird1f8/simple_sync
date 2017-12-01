package com.libsimsync.config;

import java.util.LinkedList;

public class CommonConfig {
    private Rule defaultRules;
    private LinkedList<Share> shareList;
    private final Device thisDevice;

    
   public CommonConfig(Rule rule, Device device, LinkedList<Share> shareList){  //List
        defaultRules = rule;
        thisDevice = device;
        this.shareList = shareList;
    }

    public Rule getRules(){
        return defaultRules;
    }

    public LinkedList<Share> getShareList() {
        return shareList;
    }

    public Device thisDevice() {
        return thisDevice;
    }
}
