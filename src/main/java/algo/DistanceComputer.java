package algo;

class DistanceComputer {


    private static float CONNECTIVITY_WEIGHT = 0.75F;
    private static float SIZE_RATIO_WEIGHT = 0.25F;


    /**
     * Computes the distance between two sets of elements.
     * @param size1 the size of the first set
     * @param size2 the size of the second set
     * @param matching the number of matching elements
     * @return the distance between the two sets
     */
    public float computeDistance(int size1, int size2, int matching) {
        int biggerSize = Math.max(size1, size2);
        int smallerSize = Math.min(size1, size2);
        float connectivityScore = getConnectivityScore(biggerSize, matching);
        float sizeRatioScore = getSizeRatioScore(smallerSize, biggerSize);
        return 1 - (CONNECTIVITY_WEIGHT * connectivityScore + SIZE_RATIO_WEIGHT * sizeRatioScore);
    }

    private float getConnectivityScore(int biggestSize, int matching) {
        return (float) matching / biggestSize;
    }

    private float getSizeRatioScore(int smallerSize, int biggerSize) {
        return (float) smallerSize / biggerSize;
    }
}
