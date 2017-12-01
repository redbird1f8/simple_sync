package com.simplesync;

import javax.swing.*;
import java.awt.*;

public class MainForm {

    private final JFrame form = new JFrame();
    private JRadioButton radioButton1;
    private JPanel panel1;

    public MainForm() throws HeadlessException {

        form.setBounds(0, 0, 500, 500);
        form.setContentPane(panel1);
        form.setVisible(true);
    }
}
