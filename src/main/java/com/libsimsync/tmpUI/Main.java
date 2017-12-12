package com.libsimsync.tmpUI;

import com.libsimsync.config.nconf.XMLSymShareWriter;
import com.libsimsync.managing.ConfigManager;

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
        //if(ConfigManager.getSymShare() == null) ConfigManager.setDefaultConfig();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame("SyncManager");
            }
        });
    }
}
