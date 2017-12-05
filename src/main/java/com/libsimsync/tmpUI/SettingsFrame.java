package com.libsimsync.tmpUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Nickitakalinkin on 04.12.17.
 */
public class SettingsFrame extends JFrame {

    JTabbedPane jTabbedPane;

    SettingsPanel settingsPanel;

    public SettingsFrame(String name) {

        jTabbedPane = new JTabbedPane();

        this.setLayout(new BorderLayout()); // temp

        setName(name);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);



        SyncPanel syncPanel = new SyncPanel();
        settingsPanel = new SettingsPanel(this,400,200);

        jTabbedPane.addTab("Главная",syncPanel); // заглушка 
        jTabbedPane.addTab("Настройки",settingsPanel);

        add(jTabbedPane);
        //add(settingsPanel,BorderLayout.CENTER);



        pack();
        setLocationRelativeTo(null);

        setVisible(true);

    }

}
