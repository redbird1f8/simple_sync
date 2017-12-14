package com.libsimsync.tmpUI;

import com.libsimsync.network.Synchronizer;
import sun.jvm.hotspot.code.Location;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Nickitakalinkin on 05.12.17.
 */
public class SyncPanel extends JPanel {

    Dimension panelDimension;
    InetAddress localIP;

    JLabel myIP = new JLabel("192.168.1.0");
    SyncButton syncButton;
    Synchronizer synchronizer;

    SyncPanel(Dimension dimension, Synchronizer synchronizer) {

        panelDimension = dimension;

        this.synchronizer = synchronizer; // Todo: потом сделаю синглтон обертку

        setLayout(new BorderLayout());
        JPanel workingPanel = new JPanel();

        workingPanel.setLayout(null);
        syncButton = new SyncButton("SYNC");
        syncButton.setBounds(dimension.width / 2 - syncButton.getWidth() / 2,
                15, syncButton.getWidth(), syncButton.getHeight());//120 30
        workingPanel.add(syncButton);


        try {
            localIP = InetAddress.getLocalHost();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }



        myIP.setText("Ваш IP: " + localIP.getHostAddress());

        myIP.setBounds(panelDimension.width / 2 - syncButton.getWidth() / 4, panelDimension.height - 50,
                panelDimension.width, 10);

//       // myIP.setPreferredSize(new Dimension(panelDimension.width,10));
//        myIP.setLocation(myIP.getWidth()/2  - myIP.getWidth()/2,panelDimension.height - 50);


        workingPanel.add(myIP);
//        JPanel ipPanel = new JPanel();
//        ipPanel.setLayout(new GridLayout(1,3));
//        ipPanel.add(new JPanel());
//        ipPanel.add(myIP);
//        ipPanel.add(new JPanel());
//
//        add(ipPanel,BorderLayout.SOUTH);

        add(workingPanel, BorderLayout.CENTER);


        syncButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    synchronizer.sync();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });


    }


}
