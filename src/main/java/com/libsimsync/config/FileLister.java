package com.libsimsync.config;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ListIterator;

public class FileLister {
    List<FileEntry> pathList;

    public FileLister(List<FileEntry> le){
        pathList = le;
    }

    public FileLister.Iterator getIterator(){
        return new FileLister.Iterator();
    }

    public class Iterator{
        ListIterator<FileEntry> iterator = pathList.listIterator();
        java.util.Iterator<Path> currentDirectoryIterator = null;
        FileEntry current = null;

        public FileEntry next() throws IOException{ //We assume that pathList Contains only files and directories that do not contain other directories. It may change.
            FileEntry ret = null;                   //and class should be redone.
            if (hasNext()){
                if(currentDirectoryIterator == null) {           //TODO make another implementation
                    ret = iterator.next();
                    current = ret;
                }
                if(current.getPath().toFile().isDirectory()){
                    if(currentDirectoryIterator == null) {
                        currentDirectoryIterator = Files.newDirectoryStream(ret.getPath()).iterator();
                    }
                    if (currentDirectoryIterator.hasNext()){
                        ret = new FileEntry(currentDirectoryIterator.next(), current.getRule());
                    }
                    else {
                        currentDirectoryIterator = null;
                    }
                }
            }

            return ret;
        }

        public boolean hasNext(){
            if(currentDirectoryIterator != null) {
                if (currentDirectoryIterator.hasNext()) {
                    return true;
                }
                else {
                    if(iterator.hasNext()){
                        return true;
                    }
                }
            }
            if(iterator.hasNext()){
                return true;
            }
            return false;
        }

    }

}
