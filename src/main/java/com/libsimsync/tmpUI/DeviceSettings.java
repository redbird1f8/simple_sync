package com.libsimsync.tmpUI;

import com.libsimsync.managing.TempDevice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by Nickitakalinkin on 05.12.17.
 */
public class DeviceSettings extends JFrame {

    JPanel additionPanel = new JPanel();


    DeviceSettings(List<TempDevice> deviceList) {
        DefaultListModel listModel = new DefaultListModel();
        JList list = new JList(listModel);
        for (TempDevice device : deviceList) {
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
                DeviceAddition resAddition = new DeviceAddition();
                deviceList.add(new TempDevice(resAddition.getName(), resAddition.getAddress()));
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
                int index = list.getSelectedIndex();
                String tmpStr = (String) listModel.getElementAt(index);
                listModel.remove(index);
                System.out.println(index);


                //String tmpStr = (String) listModel.getElementAt(list.getSelectedIndex()); //!!!!!!!

                //System.out.println(tmpStr);
                //TempDevice tempDevice = TempDevice.fromString(tmpStr);

               // deviceList.remove(deviceList.indexOf(tempDevice));

            }
        });
        buttonsPanel.add(removeButton);


        additionPanel.add(buttonsPanel, BorderLayout.SOUTH);
        add(additionPanel);
        setVisible(true);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // сделать красивое появление
        setLocationRelativeTo(super.getContentPane());//ToDo
        pack();
    }


}
