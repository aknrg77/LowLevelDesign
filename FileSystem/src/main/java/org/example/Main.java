package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        InMemoryFileSystem fileSystem = new InMemoryFileSystem();
        System.out.println(fileSystem.ls());
        fileSystem.mkdir("hello");
        fileSystem.cd("hello");
        fileSystem.touch("1.txt");
        fileSystem.touch("name.java");
        fileSystem.touch("example.java");
        fileSystem.cd("*exa");
        System.out.println(fileSystem.pwd());
    }
}
