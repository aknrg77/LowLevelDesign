package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        fs fileFs = new FileFs();
        System.out.println(fileFs.pwd());
        fileFs.mkdir("hello");
        fileFs.mkdir("world");
        fileFs.cd("world");
        fileFs.mkdir("folder1");
        fileFs.cd("folder1");
        System.out.println(fileFs.pwd());
    }
}
