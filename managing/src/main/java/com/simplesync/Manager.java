package com.simplesync;
import com.simplesync.methods.ChooseLocal;

import java.io.IOException;
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
 * Фасад (для упрощения работы с подсистемой)
 * Еще не дорабатывался
 * Вообще тестовый класс (забейте на него )
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

    public static void main(String[] args) {
        ArrayList<FileEntry> fileList = new ArrayList<>();

        Path path = Paths.get("/Users/Nickitakalinkin/Documents/Workspace/Java/ArrayVSLinked");

        FileEntry fileEntry = new FileEntry(path,new Rule(new ChooseLocal(),false));
        List<FileEntry> listFiles =  new ArrayList<FileEntry>();
        listFiles.add(fileEntry);


        FileLister fileLister = new FileLister(listFiles);
        Iterator iterator = fileLister.getIterator();


        try {
            FileChannel fileChannel = null;

            while(iterator.hasNext()){
                FileEntry fE = (FileEntry) iterator.next();
                System.out.println(fE.getPath().toFile());
                fileChannel = (FileChannel) Files.newByteChannel(fE.getPath(), StandardOpenOption.WRITE,StandardOpenOption.CREATE);
                ByteBuffer mBuf = ByteBuffer.allocate(26);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
