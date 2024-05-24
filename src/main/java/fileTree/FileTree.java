package fileTree;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a file tree.
 */
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

    /**
     * Returns a list of all nodes in the tree.
     * @return the list of nodes
     */
    public List<FileTree> getNodes() {
        List<FileTree> nodes = new ArrayList<>();
        nodes.add(this);
        for (FileTree child : children) {
            nodes.addAll(child.getNodes());
        }
        return nodes;
    }

    /**
     * Adds a child to the tree.
     * @param child the child
     */
    public void addChild(FileTree child) {
        children.add(child);
        child.setParent(this);
    }

    /**
     * Sets the parent of the tree.
     * @param parent the parent
     */
    public void setParent(FileTree parent) {
        this.parent = parent;
    }

    /**
     * Builds a file tree from a given path.
     * @param rootPath the root path
     * @return the file tree
     * @throws IOException if an I/O error occurs
     */
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

    /**
     * Prints the tree.
     */
    public void printTree() {
        printTreeRec(0);
    }

    /**
     * Prints the tree recursively.
     * @param depth the depth
     */
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

    /**
     * Cleans the paths of the tree.
     */
    public void cleanPaths() {
        Path parentFolder = this.path.getParent();
        cleanPathsRec(parentFolder);
    }

    /**
     * Cleans the paths of the tree recursively.
     * @param parentFolder the parent folder
     */
    private void cleanPathsRec(Path parentFolder) {
        path = parentFolder.relativize(path);
        for (FileTree child : children) {
            child.cleanPathsRec(parentFolder);
        }
    }

    /**
     * Returns the total size of the tree.
     * @return the total size
     */
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


