package clustering;

import java.util.*;

/**
 * This class represents a clustering algorithm.
 */
public class Clustering {

    /**
     * Clusters the given labels based on the distance matrix and the threshold.
     * @param distanceMatrix the distance matrix
     * @param labels the labels
     * @param threshold the threshold
     * @return the clusters
     */
    public List<List<String>> cluster(double[][] distanceMatrix, List<String> labels, double threshold) {
        int n = distanceMatrix.length;
        List<Set<Integer>> clusters = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            clusters.add(new HashSet<>(Collections.singletonList(i)));
        }

        boolean merged;
        do {
            merged = false;
            double minDistance = Double.MAX_VALUE;
            int clusterA = -1, clusterB = -1;

            for (int i = 0; i < clusters.size(); i++) {
                for (int j = i + 1; j < clusters.size(); j++) {
                    double distance = clusterDistance(clusters.get(i), clusters.get(j), distanceMatrix);
                    if (distance < minDistance) {
                        minDistance = distance;
                        clusterA = i;
                        clusterB = j;
                    }
                }
            }

            if (minDistance < threshold && clusterA != -1 && clusterB != -1) {
                clusters.get(clusterA).addAll(clusters.get(clusterB));
                clusters.remove(clusterB);
                merged = true;
            }

        } while (merged);

        List<List<String>> result = new ArrayList<>();
        for (Set<Integer> cluster : clusters) {
            List<String> clusterLabels = new ArrayList<>();
            for (int index : cluster) {
                clusterLabels.add(labels.get(index));
            }
            result.add(clusterLabels);
        }

        return result;
    }

    private double clusterDistance(Set<Integer> clusterA, Set<Integer> clusterB, double[][] distanceMatrix) {
        double sum = 0.0;
        int count = 0;
        for (int a : clusterA) {
            for (int b : clusterB) {
                sum += distanceMatrix[a][b];
                count++;
            }
        }
        return sum / count;
    }
}
