package com.libsimsync.managing;

import com.libsimsync.config.nconf.SymShare;
import com.libsimsync.config.nconf.SyncDevice;
import com.libsimsync.config.nconf.XMLSymShareReader;
import com.libsimsync.config.nconf.XMLSymShareWriter;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Nickitakalinkin on 11.12.17.
 */
public class ConfigManager {

    private static volatile SymShare symShare = new SymShare(); //возможно будет Синглтон
    private boolean dirChoosen ;

    public static SymShare getSymShare() {
        return symShare;
    }

    public static int getDeviceCount() {
        return symShare.getDevices().size();
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

        symShare.setRootPath("FileDir");

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


}
