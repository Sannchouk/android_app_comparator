package fileTree;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Getter
public class FileTree {
    public static int ID_COUNTER = 0;
    @Setter
    private int id;
    private Path path;
    private FileTree parent;
    private final List<FileTree> children;

    public FileTree(Path path) {
        this.path = path;
        this.parent = null;
        this.children = new ArrayList<>();
        this.id = ID_COUNTER++;
    }

    public List<FileTree> getNodes() {
        List<FileTree> nodes = new ArrayList<>();
        nodes.add(this);
        for (FileTree child : children) {
            nodes.addAll(child.getNodes());
        }
        return nodes;
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
        FileTree rootNode = new FileTree(rootPath);

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
        System.out.println(indent + path.toString());
        for (FileTree child : children) {
            child.printTreeRec(depth + 1);
        }
    }

    public void cleanPaths() {
        Path parentFolder = this.path.getParent();
        cleanPathsRec(parentFolder);
    }

    private void cleanPathsRec(Path parentFolder) {
        path = parentFolder.relativize(path);
        for (FileTree child : children) {
            child.cleanPathsRec(parentFolder);
        }
    }

    public int getNumberOfFiles() {
        int numberOfFiles = 0;
        for (FileTree child : children) {
            numberOfFiles += child.getNumberOfFiles();
        }
        return numberOfFiles + 1;
    }

    public long getTotalSize() {
        long totalSize = 0;
        for (FileTree child : children) {
            totalSize += child.getTotalSize();
        }
        try {
            return (long) Files.size(path) + totalSize;
        } catch (IOException e) {
            return totalSize;
        }
    }
}


