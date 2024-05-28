package matching.computers.attributes.hashs;

public class BooleanDistanceComputer implements HashDistanceComputer {

    public int computeDistanceBetweenTwoHashes(String hash1, String hash2) {
        if (hash1 != null && hash1.equals(hash2)) {
            return 0;
        }
        return 10000;
    }
}
