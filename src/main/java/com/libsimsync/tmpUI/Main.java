package com.libsimsync.tmpUI;

import com.libsimsync.config.nconf.SyncDevice;
import com.libsimsync.config.nconf.XMLSymShareWriter;
import com.libsimsync.managing.ConfigManager;
import com.libsimsync.network.Synchronizer;

import javax.swing.*;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

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
        //synchronizer.LoadFileInfo("./Inf");
        for (SyncDevice device : ConfigManager.getSymShare().getDevices())
            synchronizer.connect(device.getIpAddress());

            //if(ConfigManager.getSymShare() == null) ConfigManager.setDefaultConfig();

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new MainFrame("SyncManager",synchronizer);
                }
            });
    }
}
