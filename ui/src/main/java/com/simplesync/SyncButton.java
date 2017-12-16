package com.libsimsync.tmpUI;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by Nickitakalinkin on 04.12.17.
 */
public class SyncButton extends JButton {
    ImageIcon icon = new ImageIcon("./Pictures/BlueWhiteButtonRounded.png");
    Dimension dimension = new Dimension(263 ,103);

    SyncButton(String name){
        if(System.getProperty("os.name").equals("Windows")) System.out.println("Buy Mac");
        else System.out.println("all right");

        setBorderPainted(false);
        setOpaque(true);
        setSize(icon.getIconWidth(),icon.getIconHeight());
        setIcon(icon);

    }




}
