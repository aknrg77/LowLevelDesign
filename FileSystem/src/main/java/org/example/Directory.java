package org.example;

import java.util.HashMap;

public class Directory {
    String name;
    Directory parent;
    HashMap<String, Directory> children;

    Directory(String name, Directory parent){
        this.name = name;
        this.parent = parent;
        children = new HashMap<>();
    }
}
