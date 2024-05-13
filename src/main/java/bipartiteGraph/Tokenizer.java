package bipartiteGraph;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;



public class Tokenizer {
    private final Config config = ConfigFactory.load();

    private boolean fileSize;
    private boolean fileHash;

    public Tokenizer() {
        this.fileSize = config.getBoolean("fileSize");
        this.fileHash = config.getBoolean("fileHash");
        System.out.println("=============================== Tokenizer ===============================" );
        System.out.println("File size: " + fileSize);
        System.out.println("File hash: " + fileHash);
    }

    public void tokenize(Node node) {
        node.getTokens().clear();
        System.out.println(node.getPath().toString());
        String tokenFileName = node.getPath().toString().substring(node.getPath().toString().lastIndexOf('/') + 1);
        node.addToken(tokenFileName);
        if (fileSize) {
            int fileSize = getFileSize(node.getPath());
            node.addToken(String.valueOf(fileSize));
        }
        if (fileHash) {
            try {
                byte[] fileBytes = getFileBytes(node.getPath());
                String tokenFileHash = getSHA256(fileBytes);
                node.addToken(tokenFileHash);
            } catch (IOException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Tokens: " + node.getTokens());
    }

    public int getFileSize(Path path) {
        File file = new File(String.valueOf(path));
        return (int) FileUtils.sizeOf(file);
    }

    private static byte[] getFileBytes(Path filePath) throws IOException {
        File file = new File(String.valueOf(filePath));
        if (!file.isFile()) {
            return new byte[0];
        }
        byte[] fileBytes = new byte[(int) file.length()];
        FileInputStream fileInputStream = new FileInputStream(file);
        int readBytes = fileInputStream.read(fileBytes);
        if (readBytes != fileBytes.length) {
            throw new IOException("Cannot read file: " + filePath);
        }
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
