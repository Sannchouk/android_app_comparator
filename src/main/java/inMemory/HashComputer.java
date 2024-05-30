package inMemory;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

public class HashComputer {
    
    private static String DEFAULT_DIRECTORY_HASH = "00000000000000000000000000000000";


    public String generateHash(Path path) throws IOException, NoSuchAlgorithmException {

        if (new File(String.valueOf(path)).isDirectory()) {
            return DEFAULT_DIRECTORY_HASH;
        }

        int[] simhash = new int[64];
        Set<String> hashSet = calculateHashesBinary(path);

        for (String hash : hashSet) {
            BigInteger hashInt = new BigInteger(hash, 16);

            for (int i = 0; i < 64; i++) {
                BigInteger bitmask = BigInteger.ONE.shiftLeft(63 - i);
                if (hashInt.and(bitmask).signum() != 0) {
                    simhash[i] += 1;
                } else {
                    simhash[i] -= 1;
                }
            }
        }
        StringBuilder simhashString = new StringBuilder();
        for (int i = 0; i < 64; i++) {
            if (simhash[i] > 0) {
                simhashString.append("1");
            } else {
                simhashString.append("0");
            }
        }

        return simhashString.toString();
    }

    private Set<String> calculateHashesBinary(Path path) throws IOException, NoSuchAlgorithmException {
        Set<String> hashSet = new HashSet<>();
        try (InputStream inputStream = new FileInputStream(String.valueOf(path))) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
                byte[] hashBytes = md.digest();
                StringBuilder hexString = new StringBuilder();
                for (byte hashByte : hashBytes) {
                    String hex = Integer.toHexString(0xff & hashByte);
                    if (hex.length() == 1) {
                        hexString.append('0');
                    }
                    hexString.append(hex);
                }
                hashSet.add(hexString.toString());
            }
        }
        return hashSet;
    }
}
