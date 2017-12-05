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
public class DeviceAddition extends JDialog {

    private String address;
    private String name;
    private boolean flagApply;

    DeviceAddition(JDialog owner)  {
    super(owner,true);
//        JFrame ipNameFrame = new JFrame("Введите информаию о устройстве");
//        ipNameFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        ipNameFrame.setVisible(true);
//        ipNameFrame.setLayout(new GridLayout(3, 1));


        //setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(3, 1));


        try {
            MaskFormatter maskFormatter = new MaskFormatter("###.###.###.###");

            maskFormatter.setPlaceholderCharacter('0'); // default

            //ip
            JPanel ipPanel = new JPanel();
            ipPanel.setLayout(new BorderLayout());
            ipPanel.add(new JLabel("IPAddress устройства "), BorderLayout.WEST);
            JFormattedTextField ipTextField = new JFormattedTextField(maskFormatter);



            ipPanel.add(ipTextField, BorderLayout.CENTER);

            add(ipPanel); // warning


            // device
            JPanel namePanel = new JPanel();
            namePanel.setLayout(new BorderLayout());
            namePanel.add(new JLabel("Имя устройства"), BorderLayout.WEST);
            JTextField deviceName = new JTextField("Default name", ipTextField.getColumns());


            namePanel.add(deviceName, BorderLayout.CENTER);

            add(namePanel);


            //OK
            JPanel okPanel = new JPanel();
            okPanel.setLayout(new BorderLayout());
            JButton okButton = new JButton("Добавить");
            okPanel.add(okButton);
            add(okPanel);


            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    address = ipTextField.getText();
                    name = deviceName.getText();
                    flagApply = true;
                    dispose();
                    // setVisible(false)
                }
            });


        } catch (ParseException e) {
            e.printStackTrace();
        }


        pack();

        setLocationRelativeTo(owner.getOwner());
        setVisible(true);
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }
    public boolean isApply() {return flagApply;}

//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//    }
}
