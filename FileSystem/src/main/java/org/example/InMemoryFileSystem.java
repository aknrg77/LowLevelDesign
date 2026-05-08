package org.example;

import java.util.*;

public class InMemoryFileSystem implements FileSystem {
    private Directory root;
    private Directory current;

    InMemoryFileSystem(){
        root = new Directory("/", null);
        current = root;
    }


    @Override
    public void cd(String name) {
        if(name.equals(".")) return;

        if(name.equals("..")){
            if(current.getName()!=null){
                current = current.getParent();
            }
            return;
        }

        if(name.contains("*")){
            for(var node: current.children.values()){
                if(node.isDirectory() && matchWildcard(node.getName(), name)){
                    current = (Directory) node;
                    break;
                }
            }
        }

        if(current.children.containsKey(name)){
            current = (Directory) current.children.get(name);
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
    public void touch(String name) {
        if(!current.children.containsKey(name)){
            current.children.put(name, new File(name, current));
        }
    }

    @Override
    public String pwd() {
        if(current == root) return "";
        Deque<String> path= new ArrayDeque<>();
        Directory temp = current;
        while(temp!=root && temp !=null){
            path.addFirst(temp.getName());
            temp = temp.getParent();
        }
        return "/" + String.join("/",path);
    }

    @Override
    public List<String> ls(){
        return new ArrayList<>(current.children.keySet());
    }

    private boolean matchWildcard(String name, String pattern){
        String regex = "^" + pattern.replace(".", "\\.") + pattern.replace("*", ".*")    + "$";
        return name.matches(regex);
    }
}
