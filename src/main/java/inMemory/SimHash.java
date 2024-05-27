package inMemory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class SimHash {

    private static final int HASH_BITS = 64; // Number of bits for the hash

    /**
     * Generates a SimHash for the file or directory at the given path.
     * @param path the path to the file or directory
     * @return the generated SimHash
     * @throws IOException if an I/O error occurs
     */
    public String generateHash(Path path) throws IOException, NoSuchAlgorithmException {
        if (Files.isDirectory(path)) {
            return generateDirectoryHash(path);
        } else {
            return generateFileHash(path);
        }
    }

    private String generateFileHash(Path path) throws IOException, NoSuchAlgorithmException {
        byte[] content = Files.readAllBytes(path);
        return computeSimHash(content);
    }

    private String generateDirectoryHash(Path path) throws IOException, NoSuchAlgorithmException {
        StringBuilder combinedHash = new StringBuilder();
        Files.walk(path)
                .filter(Files::isRegularFile)
                .forEach(file -> {
                    try {
                        combinedHash.append(generateFileHash(file));
                    } catch (IOException | NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                });
        return computeSimHash(combinedHash.toString().getBytes());
    }

    private String computeSimHash(byte[] content) throws NoSuchAlgorithmException {
        int[] hashBits = new int[HASH_BITS];
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] sha256Bytes = digest.digest(content);

        for (int i = 0; i < sha256Bytes.length * 8; i++) {
            int bitmask = 1 << (7 - (i % 8));
            if ((sha256Bytes[i / 8] & bitmask) != 0) {
                hashBits[i % HASH_BITS] += 1;
            } else {
                hashBits[i % HASH_BITS] -= 1;
            }
        }

        long simHash = 0;
        for (int i = 0; i < HASH_BITS; i++) {
            if (hashBits[i] > 0) {
                simHash |= (1L << i);
            }
        }

        return Long.toHexString(simHash);
    }
}
