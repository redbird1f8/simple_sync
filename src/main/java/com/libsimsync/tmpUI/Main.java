package com.libsimsync.tmpUI;

import javax.swing.*;

/**
 * Created by Nickitakalinkin on 05.12.17.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame("Sync");
            }
        });
    }
}
