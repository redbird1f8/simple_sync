package com.libsimsync.config.methods;

import com.libsimsync.config.FileEntry;
import com.libsimsync.config.FileInfo;
import com.libsimsync.config.ResolvingMethod;

public class ChooseForeign implements ResolvingMethod {
    /**
     * metodName is method that returns string which can be used to identify type of resolving method.
     *
     * @return string containing identification information
     */
    @Override
    public String methodName() {
        return "foreign";
    }

    /**
     * resolve is method that resolves file conflicts
     *
     * @param local   FileEntry object which represents local file
     * @param foreign FileInfo class representing foreign file
     * @return 0 - local file is saved
     * 1 - foreign file is saved
     */
    @Override
    public int resolve(FileEntry local, FileInfo foreign) {
        return ResolvingMethod.FOREIGN;
    }
}
