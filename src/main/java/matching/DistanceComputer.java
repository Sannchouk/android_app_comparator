package matching;

public class DistanceComputer {

    public float computeDistance(int size1, int size2, float matchingSize) {
        return matchingSize / (size1 + size2 - matchingSize * 2);
    }
}
