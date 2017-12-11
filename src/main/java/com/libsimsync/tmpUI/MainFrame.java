package com.libsimsync.tmpUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Nickitakalinkin on 04.12.17.
 */
public class MainFrame extends JFrame {

    JTabbedPane jTabbedPane;

    SettingsPanel settingsPanel;
    Dimension dimension = new Dimension(400,200);

    public MainFrame(String name) {

        jTabbedPane = new JTabbedPane();

        this.setLayout(new BorderLayout()); // temp

        setBackground(Color.cyan);
        setName(name);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);



        SyncPanel syncPanel = new SyncPanel(dimension);
        settingsPanel = new SettingsPanel(this,400,200);

        jTabbedPane.addTab("Главная",syncPanel); // заглушка
        jTabbedPane.addTab("Настройки",settingsPanel);

        add(jTabbedPane);
        //add(settingsPanel,BorderLayout.CENTER);



        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

    }

}
