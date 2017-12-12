package com.libsimsync.tmpUI;

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

    ImageIcon image;

    public MainFrame(String name) {

        image = new ImageIcon("./Pictures/ic05.png");

        setIconImage(image.getImage());

        jTabbedPane = new JTabbedPane();

        this.setLayout(new BorderLayout()); // temp

        setBackground(Color.cyan);
        setName(name);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);



        SyncPanel syncPanel = new SyncPanel(dimension);
        settingsPanel = new SettingsPanel(this,500,200);

        jTabbedPane.addTab("Главная",syncPanel); // заглушка
        jTabbedPane.addTab("Настройки",settingsPanel);

        add(jTabbedPane);
        //add(settingsPanel,BorderLayout.CENTER);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.out.println("Окно закрыто");
            }
        });


        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);



    }

}
