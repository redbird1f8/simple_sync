package com.simplesync;

import com.simplesync.nconf.SymShare;
import com.simplesync.nconf.SyncDevice;
import com.simplesync.nconf.XMLSymShareReader;
import com.simplesync.nconf.XMLSymShareWriter;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;

/**
 * Created by Nickitakalinkin on 11.12.17.
 */
public class ConfigManager {

    private static volatile SymShare symShare = new SymShare(); //возможно будет нормальный Синглтон
    private boolean dirChoosen;

    public static SymShare getSymShare() {
        return symShare;
    }

    public static int getDeviceCount() {
        return symShare.getDevices().size();
    }

    public static String getPath() {
        return symShare.getRootPath();
    }

    public static void getConfigFromXML() {
        //XMLSymShareReader xmlSymShareReader = new XMLSymShareReader();
        //SymShare symShare = new SymShare(); //тут можно дефолтный путь
        try {
            symShare = XMLSymShareReader.read();
        } catch (IOException e) {
            setDefaultConfig();
            applyConfig();
            // getConfigFromXML();
            //e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        //return symShare;
    }

    public static void setDefaultConfig() {
        String path = System.getProperty("user.dir") + "/DefaultDirectory";
        File dir = new File(path);
        if(!dir.exists()) dir.mkdirs();

        symShare.setRootPath(path);
    }

    public static void changeDirectory(String path) {
        symShare.setRootPath(path);
    }

    public static void addDevice(String name, String ipAddress) {
        symShare.addDevice(new SyncDevice(name, ipAddress));
    }

    public static void deleteAllDevices() {
        symShare.getDevices().clear();
    }

    public static void applyConfig() {
        try {
            XMLSymShareWriter.write(symShare);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean containsDevice(SyncDevice deviceForVerification) {
        for (SyncDevice device : symShare.getDevices())
            if (device.isEqual(deviceForVerification)) return true;

        return false;


    }


}
