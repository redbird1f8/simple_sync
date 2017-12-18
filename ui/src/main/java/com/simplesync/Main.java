package com.simplesync;

import com.simplesync.nconf.SyncDevice;

import javax.swing.*;

/**
 * Created by Nickitakalinkin on 05.12.17.
 */
public class Main {
    public static void main(String[] args) {

        try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        LocalNetworkInformation.getNetworkinformation();
        ConfigManager.setDefaultConfig();

        ConfigManager.getConfigFromXML();

        Synchronizer synchronizer = new Synchronizer(ConfigManager.getPath());

        synchronizer.listen();
       // synchronizer.LoadFileInfo("./Inf");

//        Synchronizer second = new Synchronizer("/Users/Nickitakalinkin/Documents/ExceptStudy/KPO/FORTEST2");
//        second.listen();
        //synchronizer.listen();
        //synchronizer.LoadFileInfo("./Inf");


        for (SyncDevice device : ConfigManager.getSymShare().getDevices())
            synchronizer.connect(device.getIpAddress());

        //if(ConfigManager.getSymShare() == null) ConfigManager.setDefaultConfig();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame("SyncManager", synchronizer);
            }
        });
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new MainFrame("SyncManager2", second);
//            }
//        });


    }
}
