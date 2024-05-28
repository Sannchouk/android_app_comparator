package algo;

class DistanceComputer {

    /**
     * Computes the distance between two sets of elements.
     * @param size1 the size of the first set
     * @param size2 the size of the second set
     * @param matching the number of matching elements
     * @return the distance between the two sets
     */
    public float computeDistance(int size1, int size2, int matching) {
        int biggestSize = Math.max(size1, size2);
        int smallerSize = Math.min(size1, size2);
        float connectivityScore = getConnectivityScore(biggestSize, matching);
        float sizeRatioScore = getSizeRatioScore(biggestSize, smallerSize);
        return 1 - (connectivityScore * sizeRatioScore);
    }

    private float getConnectivityScore(int biggestSize, int matching) {
        return (float) matching / biggestSize;
    }

    private float getSizeRatioScore(int smallerSize, int biggerSize) {
        return (float) smallerSize / biggerSize;
    }
}
