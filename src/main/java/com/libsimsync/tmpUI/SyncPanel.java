package com.libsimsync.tmpUI;

import sun.jvm.hotspot.code.Location;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Nickitakalinkin on 05.12.17.
 */
public class SyncPanel extends JPanel {

    Dimension panelDimension = new Dimension(500,200);
    InetAddress localIP;

    JLabel myIP = new JLabel("192.168.1.0");
    SyncButton syncButton ;

    SyncPanel(Dimension dimension){


        setLayout(new BorderLayout());
        JPanel workingPanel = new JPanel();

        workingPanel.setLayout(null);
        syncButton = new SyncButton("SYNC");
        syncButton.setBounds(dimension.width/2 - syncButton.getWidth()/2,
                15, syncButton.getWidth(), syncButton.getHeight());//120 30
        workingPanel.add(syncButton);



        try {
            localIP = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }



        myIP.setText(localIP.getHostAddress());
        myIP.setBounds(200,180,100, 10);

         workingPanel.add(myIP);

        add(workingPanel,BorderLayout.CENTER);





    }


}
