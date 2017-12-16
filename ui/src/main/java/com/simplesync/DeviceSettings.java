package com.libsimsync.tmpUI;

import com.libsimsync.config.nconf.SyncDevice;
import com.libsimsync.managing.ConfigManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by Nickitakalinkin on 05.12.17.
 */
public class DeviceSettings extends JDialog {
    DeviceSettings thisDeviceSettings = this;

    JPanel additionPanel = new JPanel();

    //Dimension dim = new Dimension(300,200);
    //DeviceSettings(JFrame owner,List<TempDevice> deviceList,JLabel countOfDevices)
    DeviceSettings(JFrame owner,JLabel countOfDevices) {

        super(owner, "Настройки устройств",true);
        List<SyncDevice> deviceList = ConfigManager.getSymShare().getDevices();
        DefaultListModel listModel = new DefaultListModel();
        JList list = new JList(listModel);

        for (SyncDevice device : deviceList) {
            //listModel.addElement(device.getName() + " || " + device.getAdress());
            listModel.addElement(device.toString());
            //listModel.addElement(device);
        }


        additionPanel.setLayout(new BorderLayout(5, 5));
        additionPanel.setBorder(BorderFactory.createEmptyBorder());

        list.setSelectedIndex(0);
        list.setFocusable(false);

        additionPanel.add(new JScrollPane(list), BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 5, 0));


        //addButton
        JButton addButton = new JButton("Добавить");
        addButton.setFocusable(false);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeviceAddition resAddition = new DeviceAddition(thisDeviceSettings);

                if(resAddition.isApply()) {

                    SyncDevice syncDevice = new SyncDevice(resAddition.getName(), resAddition.getAddress());

                    if(!ConfigManager.containsDevice(syncDevice)) {
                        listModel.addElement(syncDevice.toString());
                        deviceList.add(syncDevice);// look here
                    }


                    for (SyncDevice sd : deviceList)  // TODO delete this
                        System.out.println(sd.toString());

                    countOfDevices.setText("Всего устройств: " + deviceList.size());


                    list.repaint();
                    list.revalidate();
                }
            }
        });
        buttonsPanel.add(addButton);


        //removeButton
        JButton removeButton = new JButton("Удалить");
        removeButton.setFocusable(false);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //String textDevice = (String)
                if(!list.isSelectionEmpty()) {
                    int index = list.getSelectedIndex();
                    String deletedString = ((String)listModel.getElementAt(index)); // exception


                    for (int i = 0; i < deviceList.size(); i++) {
                        SyncDevice tempDevice = SyncDevice.fromString(deletedString);

//                        System.out.println("\n important ");
//                        System.out.println(deviceList.get(i));
//                        System.out.println(tempDevice.toString());
                        if(deviceList.get(i).isEqual(tempDevice))
                            deviceList.remove(i);
                    }
//                    deviceList.remove(deviceList); // TODO использование TempDevice везде изменится



//
//                    for (TempDevice d: deviceList) {
//                        System.out.println(d.toString());
//                    }
                    System.out.println(deviceList.size());

                    countOfDevices.setText("Всего устройств: " + deviceList.size());
                    listModel.remove(index);





                    //System.out.println(index);


                    //String deletedString = (String) listModel.getElementAt(list.getSelectedIndex()); //!!!!!!!

                    //System.out.println(deletedString);
                    //TempDevice tempDevice = TempDevice.fromString(deletedString);

                    // deviceList.remove(deviceList.indexOf(tempDevice));
                }
            }
        });
        buttonsPanel.add(removeButton);


        additionPanel.add(buttonsPanel, BorderLayout.SOUTH);
        add(additionPanel);

        pack();
        setLocationRelativeTo(owner);
        setLocation(owner.getX()+10,owner.getY()+20);
        setVisible(true);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // сделать красивое появление
       //ToDo

    }


}
