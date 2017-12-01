package com.simplesync;

/**
 * ResolvingMethod is an interface that represents objects which can resolve file conflicts using information
 * about local and foreign file. //TODO add information about file conflicts
 */
public interface ResolvingMethod {
    /**
     * Represents value, that means, that local file should be picked
     */
    static int LOCAL = 0;

    /**
     * Represents value, that means, that foreign file should be picked
     */
    static int FOREIGN = 1;

    /**
     * metodName is method that returns string which can be used to identify type of resolving method.
     * @return string containing identification information
     */
    String methodName();

    /**
     * resolve is method that resolves file conflicts
     * @param local FileEntry object which represents local file
     * @param foreign FileInfo class representing foreign file
     * @return 0 - local file is saved
     * 1 - foreign file is saved
     */
    int resolve(FileEntry local, FileInfo foreign); // return type may change
}
