package com.simplesync.nconf;

public class SyncDevice {
    String name;
    String ipAddress;

    public SyncDevice(String name, String ipAddress) {
        this.name = name;
        this.ipAddress = ipAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public boolean isEqual(SyncDevice device) {
        return (this.name.equals(device.name) && this.ipAddress.equals(device.ipAddress)) ;
    }

    public String toString() {
        return name + " | " + ipAddress;
    }

    public static SyncDevice fromString(String str) {

        String[] splitStr = str.split(" [|] ");
//        System.out.println("Разббиение строки");
//        for (String s : splitStr) {
//            System.out.println(s);
//        }
        return new SyncDevice(splitStr[0],splitStr[1]);
    }

}
