package com.libsimsync.config;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * FileInfo is a class for providing information about foreign file attributes
 * needed for resolving conflicts.
 */
public class FileInfo implements Serializable {
    //public final Path filePath;
    public final String filePath;
    public final long lastModifiedDate;
    public final long creationDate;
    public final Device origin;

    /**
     *
     * @param path
     * @param lmDate
     * @param crDate
     * @param orig
     */
    public FileInfo(String path, long lmDate, long crDate, Device orig) {
        //filePath = Paths.get(path);
        filePath = path;
        lastModifiedDate = lmDate;
        creationDate = crDate;
        origin = orig;
    }
}
