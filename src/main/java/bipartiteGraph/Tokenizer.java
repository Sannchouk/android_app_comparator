package bipartiteGraph;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;


public class Tokenizer {
    private static final Config config = ConfigFactory.load();

    private boolean fileSize;
    private boolean fileHash;

    public Tokenizer() {
        this.fileSize = config.getBoolean("tokenizer.fileSize");
        this.fileHash = config.getBoolean("tokenizer.fileHash");
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
        if (fileHash) {
            try {
                byte[] fileBytes = getFileBytes(node.getName());
                String tokenFileHash = getSHA256(fileBytes);
                node.addToken(tokenFileHash);
            } catch (IOException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }

    private static byte[] getFileBytes(String filePath) throws IOException {
        File file = new File(filePath);
        byte[] fileBytes = new byte[(int) file.length()];
        FileInputStream fileInputStream = new FileInputStream(file);
        fileInputStream.read(fileBytes);
        fileInputStream.close();
        return fileBytes;
    }

    private static String getSHA256(byte[] inputBytes) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(inputBytes);
        StringBuilder hexString = new StringBuilder();
        for (byte hashByte : hashBytes) {
            String hex = Integer.toHexString(0xff & hashByte);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }


}
