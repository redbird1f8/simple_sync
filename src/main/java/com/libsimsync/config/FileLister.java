package com.libsimsync.config;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

public class FileLister {
    List<FileEntry> pathList;

    List<FileEntry> cacheList;

    public FileLister(List<FileEntry> files){
        pathList = files;
    }

    public Iterator<FileEntry> getIterator(){
        cacheList = new LinkedList<>();
        for (FileEntry e: pathList) {
            if(e.getPath().toFile().isFile()){
                cacheList.add(e);
            }
            else{
                if (e.getPath().toFile().isDirectory()){
                    Iterator<File> directoryIter = FileUtils.iterateFiles(e.getPath().toFile(), null, true);
                    while (directoryIter.hasNext()){
                        cacheList.add(new FileEntry(directoryIter.next().toPath(), e.getRule()));
                    }
                }
            }
        }
        return cacheList.iterator();
    }



}
