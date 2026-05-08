package org.example;

import java.util.List;

public interface FileSystem {
    void cd(String name);
    void mkdir(String name);
    String pwd();
    void touch(String name);
    List<String> ls();
}
