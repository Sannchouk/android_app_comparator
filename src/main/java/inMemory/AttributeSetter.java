package inMemory;

import bipartiteGraph.Node;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;



public class AttributeSetter {
    private final Config config = ConfigFactory.load();

    private boolean fileSize;
    private boolean fileHash;

    private HashComputer hashComputer = new HashComputer();

    public AttributeSetter() {
        this.fileSize = config.getBoolean("fileSize");
        this.fileHash = config.getBoolean("fileHash");
    }

    /**
     * Tokenizes a node, and set its attributes.
     * @param node the node
     */
    public void tokenize(Node node) {
        node.getTokens().clear();
        String fileName = node.getPath().toString().substring(node.getPath().toString().lastIndexOf(File.separator) + 1);
        node.addAttribute("name", getFileNameWithoutExtension(fileName));
        node.addAttribute("extension", getExtension(fileName));
        if (fileSize) {
            int fileSize = getFileSize(node.getPath());
            node.addAttribute("size", String.valueOf(fileSize));
        }
        if (fileHash) {
            try {
                String tokenFileHash = hashComputer.generateHash(node.getPath());
                node.addAttribute("hash", tokenFileHash);
            } catch (IOException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns the size of a file.
     * @param path the path of the file
     * @return the size of the file
     */
    public int getFileSize(Path path) {
        File file = new File(String.valueOf(path));
        return (int) FileUtils.sizeOf(file);
    }

    private String getFileNameWithoutExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index == -1) {
            return fileName;
        }
        return fileName.substring(0, index);
    }

    private String getExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index == -1) {
            return "";
        }
        return fileName.substring(index + 1);
    }


}
