package org.example;

public class File extends FileSystemNode{
    public String content;
    public File(String name, Directory parent){
        super(name, parent);
    }
    @Override
    public boolean isDirectory() { return false; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
