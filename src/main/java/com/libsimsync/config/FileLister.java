package com.libsimsync.config;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

//y
public class FileLister {
    List<FileEntry> pathList;

    List<FileEntry> cacheList;

    /**
     * Конструктор, предполагающий что все файлы подлежат синхронизации
     * и используется правило по умолчанию
     * @param root Путь к корню шары. Всегда должен быть равен rootPath в Share
     */
    FileLister(Path root){
        pathList = new LinkedList<>();
        pathList.add(new FileEntry(root, CommonConfig.getRules()));
    }

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
