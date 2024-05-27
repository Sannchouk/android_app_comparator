package inMemory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class SimHash {

    public String generateHash(Path path) throws IOException, NoSuchAlgorithmException {
        File file = path.toFile();
        if (file.isDirectory()) {
            // Handle directory
            BigInteger combinedSimHash = BigInteger.ZERO;
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isFile()) {
                        String fileContent = readFile(f.getPath());
                        BigInteger simHashValue = generateSimHash(fileContent);
                        combinedSimHash = combinedSimHash.add(simHashValue);
                    }
                }
            }
            // Convert the combined simhash to a hex string
            return combinedSimHash.toString(16);
        } else {
            // Handle single file
            String fileContent = readFile(file.getPath());
            BigInteger simHashValue = generateSimHash(fileContent);
            return simHashValue.toString(16);
        }
    }

    private static String readFile(String filePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append(" ");
            }
        }
        return contentBuilder.toString();
    }

    private static String[] tokenize(String text) {
        return text.split("\\s+"); // Split by whitespace
    }

    private static BigInteger hashToken(String token) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashBytes = md.digest(token.getBytes());
        return new BigInteger(1, hashBytes);
    }

    private static BigInteger generateSimHash(String text) throws NoSuchAlgorithmException {
        int hashBits = 128;
        int[] v = new int[hashBits];
        String[] tokens = tokenize(text);

        for (String token : tokens) {
            BigInteger tokenHash = hashToken(token);
            for (int i = 0; i < hashBits; i++) {
                BigInteger bitmask = BigInteger.ONE.shiftLeft(i);
                if (tokenHash.and(bitmask).signum() != 0) {
                    v[i] += 1;
                } else {
                    v[i] -= 1;
                }
            }
        }

        BigInteger fingerprint = BigInteger.ZERO;
        for (int i = 0; i < hashBits; i++) {
            if (v[i] >= 0) {
                fingerprint = fingerprint.or(BigInteger.ONE.shiftLeft(i));
            }
        }

        return fingerprint;
    }
}
