package com.simplesync;

import com.simplesync.nconf.SyncDevice;

import javax.swing.*;

/**
 * Created by Nickitakalinkin on 05.12.17.
 */
public class Main {
    public static void main(String[] args) {

//
//        try {
//            XMLSymShareWriter.write(ConfigManager.getSymShare());
//        } catch (XMLStreamException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        ConfigManager.getConfigFromXML();
        Synchronizer synchronizer = new Synchronizer(ConfigManager.getPath());
        synchronizer.listen();
        synchronizer.LoadFileInfo("./Inf");

        for (SyncDevice device : ConfigManager.getSymShare().getDevices())
            synchronizer.connect(device.getIpAddress());

        //if(ConfigManager.getSymShare() == null) ConfigManager.setDefaultConfig();

        SwingUtilities.invokeLater(() -> new MainFrame("SyncManager", synchronizer));
    }
}
