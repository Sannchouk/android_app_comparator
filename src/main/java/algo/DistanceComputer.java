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
        return 1 - ((float) matching / (size1 + size2));
    }
}
