package com.libsimsync.managing;
import com.libsimsync.config.methods.ChooseLocal;
import com.libsimsync.network.*;
import com.*;
import com.libsimsync.config.*;
import com.libsimsync.network.OutwardConnectioInitialiser;
import com.sun.nio.zipfs.ZipPath;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Nickitakalinkin on 17.10.17.
 *
 * Еще не дорабатывался
 * Вообще тестовый класс (забейте на него )
 */



 class Manager {



  Share share;
  CommonConfig config;

public Manager(){}


public Manager(Share share, CommonConfig config){
    this.share = share;
    this.config = config;


    // etc

}




    public Share createNewShare(String shareName ){

    Share share = null;
    return share;


    }

    public void setConfiguration(){


    }

    public Share viewShare(Share share){
        return share;
    }

    public Share deleteShare(Share share){ // ?

        return  share;
    }

    public void syncShare(Share share, Device device){

    }

    public static void main(String[] args) {

        String path = "";

    }

}
