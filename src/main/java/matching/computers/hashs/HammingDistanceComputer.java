package matching.computers.hashs;

import java.math.BigInteger;

public class HammingDistanceComputer implements HashDistanceComputer {

    public int computeDistanceBetweenTwoHashes(String hash1, String hash2) {
        // Handle empty strings
        if (hash1.isEmpty() || hash2.isEmpty()) {
            return Math.max(hash1.length(), hash2.length());
        }

        // Pad the shorter hash with leading zeroes to match lengths
        int lengthDifference = Math.abs(hash1.length() - hash2.length());
        if (hash1.length() < hash2.length()) {
            hash1 = "0".repeat(lengthDifference) + hash1;
        } else if (hash2.length() < hash1.length()) {
            hash2 = "0".repeat(lengthDifference) + hash2;
        }

        // Convert the hexadecimal strings to BigInteger
        BigInteger convertedHash1 = new BigInteger(hash1, 16);
        BigInteger convertedHash2 = new BigInteger(hash2, 16);

        // Perform XOR and count the number of set bits
        BigInteger xorResult = convertedHash1.xor(convertedHash2);
        return xorResult.bitCount();
    }
}
