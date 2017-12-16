package com.libsimsync.tmpUI;

import com.libsimsync.config.nconf.SyncDevice;
import com.libsimsync.managing.ConfigManager;
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

    SettingsPanel thisPanel = this;

//    ConfigManager configManager;
//
// Временная часть (для обработки событий)
//         List<TempDevice> devicesList = new ArrayList<>();
//    {
//        for (int i = 0; i < 12; i++) {
//            String adress = "192.168.1." + i;
//            String name = "name" + i;
//            devicesList.add(new TempDevice(name,adress));
//        }
//    }

    //



    JButton chooseLocalDirectory;
    JButton addDevice;
    JButton deleteAllDevices;
    JButton applyConfig;

    String path;
    JLabel pathLabel;
    JTextField pathField;
    File localDirectory = new File(ConfigManager.getSymShare().getRootPath());

//    private  static final int HEIGHT = 200;
//    private  static final int WIDTH = 200;
    SettingsPanel(JFrame frame,int width,int height) {

        setLayout(new BorderLayout());
        setPreferredSize(panelDimension);


        JPanel westPanel = new JPanel();
        westPanel.setBorder(BorderFactory.createEmptyBorder()); // Todo 1
        westPanel.setLayout(new GridLayout(5,1));
        //westPanel.setLayout(new BorderLayout());


        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createEmptyBorder()); // Todo 2
        centerPanel.setLayout(new GridLayout(5,1));

        //centerPanel.setLayout(new BorderLayout());
        JScrollPane centerScrollPane = new JScrollPane(centerPanel);

        //centerScrollPane.setBorder(BorderFactory.createMatteBorder(0,20,0,0, centerPanel.getBackground())); //Todo 3




        // west panel

        // chooseLocalDirectory
        chooseLocalDirectory = new JButton("Выбрать хранилище");
        chooseLocalDirectory.setPreferredSize(buttonDimension);
        //westPanel.add(chooseLocalDirectory,BorderLayout.NORTH);
        westPanel.add(chooseLocalDirectory);

        //addDevice
        addDevice = new JButton("Список устройств");
        addDevice.setPreferredSize(buttonDimension);
        westPanel.add(addDevice);




        //delete all devices
        deleteAllDevices = new JButton("Очистить список устройств");
        deleteAllDevices.setPreferredSize(buttonDimension);
        westPanel.add(deleteAllDevices);

        westPanel.add(new JPanel());

        applyConfig = new JButton("Принять настройки");
        applyConfig.setPreferredSize(buttonDimension);

        westPanel.add(applyConfig);



        add(westPanel,BorderLayout.WEST);






        // center panel (scroll)



        // chooseLocalDirectory Lablel
        pathLabel = new JLabel("path");
        pathLabel.setText(localDirectory.getPath());
        pathLabel.setBorder(BorderFactory.createEtchedBorder()); //Todo 4
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

        JLabel jCountOfDevices = new JLabel("Всего устройств в списке: " + ConfigManager.getDeviceCount());
        jCountOfDevices.setBorder(BorderFactory.createEtchedBorder()); // Todo 5
        centerPanel.add(jCountOfDevices,BorderLayout.CENTER);



        add(centerScrollPane,BorderLayout.CENTER);



//        this.add(pathLabel, BorderLayout.SOUTH);


        chooseLocalDirectory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDirectoryChooser localDirectoryChooser = new LocalDirectoryChooser();
                localDirectory = localDirectoryChooser.getChoosenDirectory();
                //System.out.println(localDirectory.getPath());
                if(localDirectory != null) {
                    pathLabel.setText(localDirectory.getPath());
                    ConfigManager.changeDirectory(localDirectory.getPath()); // TODO заменил
                }


            }
        });

        addDevice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeviceSettings(frame,jCountOfDevices);

                System.out.println();

                //jCountOfDevices.setText("Всего устройств: " + 100);
                jCountOfDevices.repaint();
                jCountOfDevices.revalidate();
            }
        });


        deleteAllDevices.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                Object[] options = {"Да","Нет"};
                int n = JOptionPane.showOptionDialog(thisPanel,"Вы действительно хотите очистить список устройств?",
                        "Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,
                        new ImageIcon("./Pictures/ir06.png"),options,options[0]);
                if(n == 0)  {
                    ConfigManager.deleteAllDevices();
                    jCountOfDevices.setText("Всего устройств: " + ConfigManager.getDeviceCount());
                }
                System.out.println(n);

                jCountOfDevices.repaint();
                jCountOfDevices.revalidate();

            }
        });





        applyConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfigManager.applyConfig();
            }
        });

    }
}
