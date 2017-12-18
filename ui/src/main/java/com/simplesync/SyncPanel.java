package com.simplesync;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.*;
import java.util.Collections;
import java.util.Enumeration;

//import sun.jvm.hotspot.code.Location;

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


           // System.out.println("результат парсинга:");
//            for (int b : parseForAndrew(localIP.getHostAddress())) {
//
//                System.out.println(b);
//            }


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // myIP.setText("Ваш IP: " + localIP.getHostAddress());
        myIP.setText("Ваш IP: " + localIP.getHostAddress());
        myIP.setBounds(panelDimension.width / 2 - syncButton.getWidth() / 4, panelDimension.height - 50,
                panelDimension.width, 10);
        // for delete
//        myIP.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                JDialog ipDialog = new JDialog();
//                try {
//                    InetAddress localIPs = InetAddress.getAllByName("host");
//                    localIPs.;
//
//                    DefaultListModel listModel = new DefaultListModel();
//                    for()
//
//                    JList jList = new JList();
//                } catch (UnknownHostException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        });

        workingPanel.add(myIP);

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

//    public static int[] parseForAndrew(String ip) {
//        //System.out.println(ip);
//        String[] splittedIp = ip.split("[.]");
//        //for(String str : splittedIp) System.out.println(str);
//
//        int[] bIp = new int[4];
//        for (int i = 0; i < 4; i++) {
//            //System.out.println(splittedIp[i]);
//            bIp[i] = Integer.parseInt(splittedIp[i]);
//        }
//            return bIp;
//        }


    }

