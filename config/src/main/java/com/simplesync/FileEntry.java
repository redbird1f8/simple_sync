package com.simplesync;

import java.nio.file.Path;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileEntry)) return false;
        FileEntry fileEntry = (FileEntry) o;
        return Objects.equals(path, fileEntry.path) &&
                Objects.equals(rule, fileEntry.rule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, rule);
    }
}
