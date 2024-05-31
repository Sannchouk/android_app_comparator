package matching.computers.attributes.sizes;

public class SizeDistanceComputer {

    public double computeDistanceBetweenTwoSizes(String size1, String size2) {
        int size1Value = Integer.parseInt(size1);
        int size2Value = Integer.parseInt(size2);
        return 1 - (double) Math.min(size1Value, size2Value) /  Math.max(size1Value, size2Value);
    }
}
