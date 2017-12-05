package com.libsimsync.tmpUI;

import com.libsimsync.managing.TempDevice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 * Created by Nickitakalinkin on 04.12.17.
 */
public class SettingsPanel extends JComponent {

    Dimension buttonDimension = new Dimension(200,20);
    Dimension panelDimension = new Dimension(500,200);

    // Временная часть (для обработки событий)
         List<TempDevice> devicesList = new ArrayList<>();
    {
        for (int i = 0; i < 12; i++) {
            String adress = "192.168.1." + i;
            String name = "name" + i;
            devicesList.add(new TempDevice(name,adress));
        }
    }

    //



    JButton chooseLocalDirectory;
    JButton addDevice;
    String path;
    JLabel pathLabel;
    JTextField pathField;
    File localDirectory;

//    private  static final int HEIGHT = 200;
//    private  static final int WIDTH = 200;
    SettingsPanel(JFrame frame,int width,int height) {

        setLayout(new BorderLayout());
        setPreferredSize(panelDimension);


        JPanel westPanel = new JPanel();
        westPanel.setBorder(BorderFactory.createEmptyBorder());
        westPanel.setLayout(new GridLayout(4,1));
        //westPanel.setLayout(new BorderLayout());


        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createEmptyBorder());
        centerPanel.setLayout(new GridLayout(4,1));

        //centerPanel.setLayout(new BorderLayout());
        JScrollPane centerScrollPane = new JScrollPane(centerPanel);

        centerPanel.setBorder(BorderFactory.createEmptyBorder());




        // west panel

        // chooseLocalDirectory
        chooseLocalDirectory = new JButton("Выбрать хранилище");
        chooseLocalDirectory.setPreferredSize(buttonDimension);
        //westPanel.add(chooseLocalDirectory,BorderLayout.NORTH);
        westPanel.add(chooseLocalDirectory);

        //addDevice
        addDevice = new JButton("Добавить устройство");
        addDevice.setPreferredSize(buttonDimension);
        westPanel.add(addDevice);

        add(westPanel,BorderLayout.WEST);





        // center panel (scroll)



        // chooseLocalDirectory Lablel
        pathLabel = new JLabel("path");
        pathLabel.setBorder(BorderFactory.createEtchedBorder());
        //pathLabel.setBorder(BorderFactory.createEtchedBorder(Color.gray,Color.gray));
        centerPanel.add(pathLabel);


        // combobox for device
//        JComboBox<String> devicesBox = new JComboBox<>();

//        JMenu devicesMenu = new JMenu();
//        devicesMenu.setBorder(BorderFactory.createEtchedBorder());
//        for(TempDevice device : devicesList ) {
//           // devicesBox.addItem(device.getName() + " | " + device.getAdress()); //  TODO: переделается на нормальный экземпляр
//            devicesMenu.add(device.getName() + " | " + device.getAdress());
//        }
//
//        centerPanel.add(devicesMenu, BorderLayout.CENTER);

//        devicesBox.setBorder(BorderFactory.createEtchedBorder());
//        devicesBox.setEditable(false);
//        for(TempDevice device : devicesList ) {
//           // devicesBox.addItem(device.getName() + " | " + device.getAdress()); //  TODO: переделается на нормальный экземпляр
//            devicesBox.addItem(device.getName() + " | " + device.getAdress());
//        }
//        centerPanel.add(devicesBox, BorderLayout.CENTER);

        JLabel jCountOfDevices = new JLabel("Всего устройств: " + devicesList.size());
        jCountOfDevices.setBorder(BorderFactory.createEtchedBorder());
        centerPanel.add(jCountOfDevices,BorderLayout.CENTER);



        add(centerScrollPane,BorderLayout.CENTER);



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

        addDevice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeviceSettings(frame,devicesList,jCountOfDevices);

                System.out.println();

                //jCountOfDevices.setText("Всего устройств: " + 100);
                jCountOfDevices.repaint();
                jCountOfDevices.revalidate();
            }
        });

    }
}
