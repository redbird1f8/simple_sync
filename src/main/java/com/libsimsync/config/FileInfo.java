package com.libsimsync.config;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * FileInfo is a class for providing information about foreign file attributes
 * needed for resolving conflicts.
 */
public class FileInfo {
    final Path filePath;
    final long lastModifiedDate;
    final long creationDate;
    final Device origin;

    /**
     *
     * @param path
     * @param lmDate
     * @param crDate
     * @param orig
     */
    public FileInfo(String path, long lmDate, long crDate, Device orig) {
        filePath = Paths.get(path);
        lastModifiedDate = lmDate;
        creationDate = crDate;
        origin = orig;
    }
}
