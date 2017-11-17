package com.libsimsync.managing;
import com.*;
import com.libsimsync.config.*;

import java.io.FileOutputStream;


/**
 * Created by Nickitakalinkin on 17.10.17.
 *
 * Фасад (для упрощения работы с подсистемой)
 */

public class Manager {



  Share share;
  CommonConfig config;

public Manager(){}


public Manager(Share share, CommonConfig config){
    this.share = share;
    this.config = config;


    // etc

}




    public Share createNewShare(String shareName, ){


   // Share share = new Share();


    }

    public void setConfiguration(){
        FileOutputStream fileOutputStream = new FileOutputStream("blabla")


    }

    public Share viewShare(Share share){

    }

    public Share deleteShare(Share share){ // ?

        return  share;
    }

    public void syncShare(Share share, Device device){

    }



}
