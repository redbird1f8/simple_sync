package com.managing;
import com.*;
import com.libsimsync.config.CommonConfig;
import com.libsimsync.config.Share;


/**
 * Created by Nickitakalinkin on 17.10.17.
 *
 * Фасад (для упрощения работы с подсистемой)
 */

public class Manager {


Share share;
CommonConfig config;


public Manager(Share share, CommonConfig config){
    this.share = share;
    this.config = config;


    // etc

}




    public void createNewShare(){

    }

    public void setConfiguration(){

    }

    public void viewShare(){

    }

    public Share deleteShare(){ // ?

    }

    public void  synchronizeShare(){ // maybe just "synchronize",
        // but its can be confused with java  "synchronize" (for thread synchronization)

    }



}
