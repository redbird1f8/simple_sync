package com.simplesync;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * FileInfo is a class for providing information about foreign file attributes
 * needed for resolving conflicts.
 */
public class FileInfo implements Serializable {
    //public final Path filePath;
    public String filePath;
    public String relPath;
    public long lastModifiedDate;
    public long creationDate;
    public boolean isDeleted = false;
    //public final Device origin;

    /**
     * @param path
     * @param lmDate
     * @param crDate
     */
    //public FileInfo(String path, long lmDate, long crDate, Device orig) {
    public FileInfo(String path, String relPath, long lmDate, long crDate) {
        //filePath = Paths.get(path);
        filePath = path;
        this.relPath = relPath;
        lastModifiedDate = lmDate;
        creationDate = crDate;
    }

    public FileInfo(String path, String relPath) throws IOException {
        //filePath = Paths.get(path)
        this.relPath = relPath;
        filePath = path;
        File f = new File(path);
        BasicFileAttributes attr = Files.readAttributes(Paths.get(path), BasicFileAttributes.class);

        //lastModifiedDate = f.get
        creationDate = attr.creationTime().toMillis();
        lastModifiedDate = attr.lastModifiedTime().toMillis();
    }
}