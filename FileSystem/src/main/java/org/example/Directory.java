package org.example;

import java.util.HashMap;

public class Directory extends FileSystemNode{
    String name;
    Directory parent;
    HashMap<String, FileSystemNode> children = new HashMap<>();

    Directory(String name, Directory parent){
        super(name, parent);
    }

    @Override
    public boolean isDirectory() { return true; }
}
