package fileTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileTree {
    private String data;
    private FileTree parent;
    private List<FileTree> children;

    public FileTree(String data) {
        this.data = data;
        this.parent = null;
        this.children = new ArrayList<>();
    }

    public String getData() {
        return data;
    }

    public FileTree getParent() {
        return parent;
    }

    public List<FileTree> getChildren() {
        return children;
    }

    public void addChild(FileTree child) {
        children.add(child);
        child.setParent(this);
    }

    public void setParent(FileTree parent) {
        this.parent = parent;
    }

    public static FileTree buildTree(Path rootPath) throws IOException {
        rootPath = rootPath.normalize();
        FileTree rootNode = new FileTree(rootPath.toString());

        if (Files.isDirectory(rootPath)) {
            try (var entries = Files.newDirectoryStream(rootPath)) {
                for (var entry : entries) {
                    FileTree childNode = buildTree(entry);
                    rootNode.addChild(childNode);
                }
            }
        }
        return rootNode;
    }

    public void printTree() {
        printTreeRec(0);
    }

    private void printTreeRec(int depth) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append("  ");
        }
        System.out.println(indent + data);
        for (FileTree child : children) {
            child.printTreeRec(depth + 1);
        }
    }

    public List<String> getAllNodesData() {
        List<String> nodes = new ArrayList<>();
        nodes.add(data);
        for (FileTree child : children) {
            nodes.addAll(child.getAllNodesData());
        }
        return nodes;
    }
}


