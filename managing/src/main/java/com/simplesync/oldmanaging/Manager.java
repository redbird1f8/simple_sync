package com.simplesync.oldmanaging;

import com.simplesync.CommonConfig;
import com.simplesync.Device;
import com.simplesync.Share;

//import com.libsimsync.network.OutwardConnectioInitialiser;


/**
 * Created by Nickitakalinkin on 17.10.17.
 *
 * Еще не дорабатывался
 * Вообще тестовый класс (забейте на него )
 */
    //TODO поправить конфиг
    //TODO доделать синхронизацию
    //TODO заменить заглушик в UI
    //TODO TODO TODO


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
