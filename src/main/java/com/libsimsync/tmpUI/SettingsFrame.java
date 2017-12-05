package com.libsimsync.tmpUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Nickitakalinkin on 04.12.17.
 */
public class SettingsFrame extends JFrame {

    SettingsPanel settingsPanel;

    public SettingsFrame(String name) {

        this.setLayout(new BorderLayout()); // temp

        setName(name);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);




        settingsPanel = new SettingsPanel(400,200);


        //settingsPanel.setBounds(1,1,400,400);
        add(settingsPanel,BorderLayout.CENTER);

        pack();

        setVisible(true);

    }

}
