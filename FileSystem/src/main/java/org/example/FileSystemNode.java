package org.example;

public abstract class FileSystemNode {
    String name;
    Directory parent;
    FileSystemNode(String name, Directory parent){
        this.name = name;
        this.parent = parent;
    }
    public String getName() { return name; }
    public Directory getParent() { return parent; }
    public abstract boolean isDirectory();
}
