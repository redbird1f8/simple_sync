package com.libsimsync.config;


import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ListIterator;

public class FileLister {
    List<FileEntry> pathList;


    public FileLister(List<FileEntry> files){
        pathList = files;
    }

    public FileLister.Iterator getIterator(){
        return new FileLister.Iterator();
    }

    public class Iterator{
        ListIterator<FileEntry> iterator = pathList.listIterator();
        DirectoryStream<Path> directoryStream = null;
        java.util.Iterator<Path> currentDirectoryIterator = null;
        FileEntry current = null;

        public FileEntry next() throws IOException{ //We assume that pathList Contains only files and directories that do not contain other directories. It may change.
            FileEntry ret = null;                   //and class should be redone.
            if (hasNext()){
                if(currentDirectoryIterator != null && !currentDirectoryIterator.hasNext())
                {
                    directoryStream.close();
                    directoryStream = null;
                    currentDirectoryIterator = null;
                }
                if(currentDirectoryIterator == null) {
                    ret = iterator.next();
                    current = ret;
                }
                if(current.getPath().toFile().isDirectory()){
                    if(currentDirectoryIterator == null) {
                        directoryStream = Files.newDirectoryStream(ret.getPath());
                        currentDirectoryIterator = directoryStream.iterator();
                    }
                    if (currentDirectoryIterator.hasNext()){
                        ret = new FileEntry(currentDirectoryIterator.next(), current.getRule());
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
            }
            if(iterator.hasNext()){
                return true;
            }
            return false;
        }

    }

}
