package bipartiteGraph;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class Tokenizer {

    private boolean fileSize;
    private boolean fileHash;

    public Tokenizer(boolean fileSize, boolean fileHash) {
        this.fileSize = fileSize;
        this.fileHash = fileHash;
    }

    public void tokenize(Node node) {
        node.getTokens().clear();
        String tokenFileName = node.getName().substring(node.getName().lastIndexOf("/") + 1);
        node.addToken(tokenFileName);
        if (fileSize) {
            File file = new File(node.getName());
            String tokenFileSize = FileUtils.sizeOf(file) + "";
            node.addToken(tokenFileSize);
        }
    }


}
