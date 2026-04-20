package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileFs implements fs {
    private Directory root;
    private Directory current;

    FileFs(){
        root = new Directory("/", null);
        current = root;
    }


    @Override
    public void cd(String name) {
        if(name.equals("*")){
            cdWildcard();
            return;
        }

        if(name.equals(".")) return;

        if(name.equals("..")){
            if(current.parent!=null){
                current = current.parent;
            }
            return;
        }

        if(current.children.containsKey(name)){
            current = current.children.get(name);
            return;
        }
    }

    @Override
    public void mkdir(String name) {
        if(!current.children.containsKey(name)){
            current.children.put(name, new Directory(name, current));
        }
    }

    @Override
    public String pwd() {
        List<String> path = new ArrayList<>();

        Directory temp = current;
        while(temp!=null){
            path.add(temp.name);
            temp = temp.parent;
        }

        Collections.reverse(path);
        return String.join("/", path).replace("//", "/");

    }

    private void cdWildcard(){
        List<Directory> options = new ArrayList<>();

        options.add(current);

        if(current.parent!=null){
            options.add(current.parent);
        }

        options.addAll(current.children.values());

        current = options.getFirst();

    }
}
