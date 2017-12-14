package com.libsimsync.tmpUI;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by Nickitakalinkin on 04.12.17.
 */
public class SyncButton extends JButton {
    ImageIcon icon = new ImageIcon("./Pictures/BlueWhiteButton.png");
    Dimension dimension = new Dimension(263 ,103);

    SyncButton(String name){

        setBorderPainted(false);
        //setOpaque(false);
        setSize(icon.getIconWidth(),icon.getIconHeight());
        setIcon(icon);

    }




}
