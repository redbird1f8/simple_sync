package com.libsimsync.config;

import java.nio.file.Path;

public class FileEntry {
    private Path path;
    private Rule rule;

    public FileEntry(Path entryPath, Rule enrtyRule){
        path = entryPath;
        rule = enrtyRule;
    }

    public Path getPath() {
        return path;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }


}
