package com.libsimsync.tmpUI;

import com.libsimsync.network.Synchronizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Nickitakalinkin on 04.12.17.
 */
public class MainFrame extends JFrame {

    JTabbedPane jTabbedPane;

    SettingsPanel settingsPanel;
    Dimension dimension = new Dimension(500,200);

    Synchronizer synchronizer;

    ImageIcon image;

    public MainFrame(String name, Synchronizer synchronizer) {

        image = new ImageIcon("./Pictures/ic05r.png");
        setTitle("Sync");
        setIconImage(image.getImage());

        this.synchronizer = synchronizer;

        jTabbedPane = new JTabbedPane();

        this.setLayout(new BorderLayout()); // temp

//        setBackground(Color.cyan);
//        setName(name);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);



        SyncPanel syncPanel = new SyncPanel(dimension, synchronizer);
        settingsPanel = new SettingsPanel(this,500,200);

        jTabbedPane.addTab("Главная",syncPanel); // заглушка
        jTabbedPane.addTab("Настройки",settingsPanel);

        add(jTabbedPane);
        //add(settingsPanel,BorderLayout.CENTER);




        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                synchronizer.SaveFileInfo("./Inf");
                System.out.println("Окно закрыто");
            }
        });


        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);



    }

}
