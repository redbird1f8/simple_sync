package com.libsimsync.tmpUI;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

/**
 * Created by Nickitakalinkin on 05.12.17.
 */
public class DeviceAddition  {

    private String address;
    private String name;

    DeviceAddition()  {

        JFrame ipNameFrame = new JFrame("Введите информаию о устройстве");
        ipNameFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        ipNameFrame.setVisible(true);
        ipNameFrame.setLayout(new GridLayout(3, 1));


        try {
            MaskFormatter maskFormatter = new MaskFormatter("###.###.###.###");
            maskFormatter.setPlaceholderCharacter('_'); // default

            //ip
            JPanel ipPanel = new JPanel();
            ipPanel.setLayout(new BorderLayout());
            ipPanel.add(new JLabel("IPAddress устройства "), BorderLayout.WEST);
            JFormattedTextField ipTextField = new JFormattedTextField(maskFormatter);


            ipPanel.add(ipTextField, BorderLayout.CENTER);

            ipNameFrame.add(ipPanel); // warning


            // device
            JPanel namePanel = new JPanel();
            namePanel.setLayout(new BorderLayout());
            namePanel.add(new JLabel("Имя устройства"), BorderLayout.WEST);
            JTextField deviceName = new JTextField("Default name", ipTextField.getColumns());


            namePanel.add(deviceName, BorderLayout.CENTER);

            ipNameFrame.add(namePanel);


            //OK
            JButton okButton = new JButton("Добавить");
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    address = ipTextField.getText();
                    name = deviceName.getText();
                    ipNameFrame.dispose();
                    // setVisible(false)
                }
            });


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//    }
}
