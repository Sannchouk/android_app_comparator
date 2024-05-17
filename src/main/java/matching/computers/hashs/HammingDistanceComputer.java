package matching.computers.hashs;

public class HammingDistanceComputer {

    public int computeDistanceBetweenTwoHashes (String hash1, String hash2) {
        if (hash1.isEmpty() || hash2.isEmpty()) {
            return Math.max(hash1.length(), hash2.length());
        }
        int lengthDifference = Math.abs(hash1.length() - hash2.length());
        if (hash1.length() < hash2.length()) {
            hash1 = "0".repeat(lengthDifference) + hash1;
        } else if (hash2.length() < hash1.length()) {
            hash2 = "0".repeat(lengthDifference) + hash2;
        }
        int convertedHash1 = Integer.parseInt(hash1, 2);
        int convertedHash2 = Integer.parseInt(hash2, 2);
        int xorResult = convertedHash1 ^ convertedHash2;
        return Integer.bitCount(xorResult);
    }



}
