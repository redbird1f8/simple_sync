package com.libsimsync.managing;
import com.*;
import com.libsimsync.config.*;

import java.io.FileOutputStream;


/**
 * Created by Nickitakalinkin on 17.10.17.
 *
 * Фасад (для упрощения работы с подсистемой)
 * Еще не дорабатывался
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



}
