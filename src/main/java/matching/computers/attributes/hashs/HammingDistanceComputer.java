package matching.computers.attributes.hashs;

import java.math.BigInteger;

public class HammingDistanceComputer implements HashDistanceComputer {

    public int computeDistanceBetweenTwoHashes(String hash1, String hash2) {
        if (hash1.isEmpty() || hash2.isEmpty()) {
            return Math.max(hash1.length(), hash2.length());
        }

        int lengthDifference = Math.abs(hash1.length() - hash2.length());
        if (hash1.length() < hash2.length()) {
            hash1 = "0".repeat(lengthDifference) + hash1;
        } else if (hash2.length() < hash1.length()) {
            hash2 = "0".repeat(lengthDifference) + hash2;
        }

        BigInteger convertedHash1 = new BigInteger(hash1, 16);
        BigInteger convertedHash2 = new BigInteger(hash2, 16);

        BigInteger xorResult = convertedHash1.xor(convertedHash2);
        return xorResult.bitCount();
    }
}
