package com.libsimsync.tmpUI;

import javax.swing.*;
import java.io.File;

/**
 * Created by Nickitakalinkin on 04.12.17.
 */
public class LocalDirectoryChooser extends JComponent {

    private JFileChooser jFileChooser;
    private File choosenDirectory;

    public LocalDirectoryChooser() {

        jFileChooser = new JFileChooser();
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int ret = jFileChooser.showDialog(null,"Открыть файл");
        //
        if(ret == JFileChooser.APPROVE_OPTION) {

            choosenDirectory = jFileChooser.getSelectedFile();
        }

    }

    public File getChoosenDirectory() {
        return choosenDirectory;
    }





}
