package com.libsimsync.config;

import java.nio.file.Path;

public class FileEntry {
    private Path path;
    private Rules rule;

    public FileEntry(Path entryPath, Rules enrtyRule){
        path = entryPath;
        rule = enrtyRule;
    }

    public Path getPath() {
        return path;
    }

    public Rules getRule() {
        return rule;
    }

    public void setRule(Rules rule) {
        this.rule = rule;
    }


}
