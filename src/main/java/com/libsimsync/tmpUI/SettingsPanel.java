package com.libsimsync.tmpUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Nickitakalinkin on 04.12.17.
 */
public class SettingsPanel extends JPanel {

    JButton chooseLocalDirectory;
    String path;
    JLabel pathLabel;
    JTextField pathField;
    File localDirectory;

//    private  static final int HEIGHT = 200;
//    private  static final int WIDTH = 200;
    SettingsPanel(int width,int height) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500,200));


        JPanel westPanel = new JPanel();
        westPanel.setBorder(BorderFactory.createEtchedBorder());


        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createBevelBorder(3));
        JScrollPane centerScrollPane = new JScrollPane(centerPanel);


        // west panel
        chooseLocalDirectory = new JButton("Выбрать хранилище");
        chooseLocalDirectory.setPreferredSize(new Dimension(200,20));
        westPanel.add(chooseLocalDirectory,BorderLayout.NORTH);
        add(westPanel,BorderLayout.WEST);



        // center panel (scroll)
        pathLabel = new JLabel("path");
        //pathLabel.setBorder(BorderFactory.createEtchedBorder(Color.gray,Color.gray));
        centerPanel.add(pathLabel,BorderLayout.CENTER);
        add(centerScrollPane);



//        this.add(pathLabel, BorderLayout.SOUTH);


        chooseLocalDirectory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDirectoryChooser localDirectoryChooser = new LocalDirectoryChooser();
                localDirectory = localDirectoryChooser.getChoosenDirectory();
                //System.out.println(localDirectory.getPath());
                pathLabel.setText(localDirectory.getPath());

            }
        });

    }
}
