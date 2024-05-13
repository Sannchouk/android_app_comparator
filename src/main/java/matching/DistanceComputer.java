package matching;

public class DistanceComputer {

    public float computeDistance(int size1, int size2, float matchingSize) {
        return 1 - (matchingSize*2 / (size1 + size2));
    }
}
