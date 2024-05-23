package algo;

public class DistanceComputer {

    public float computeDistance(int size1, int size2, int matching) {
        return 1 - ((float) matching / (size1 + size2));
    }
}
