package clustering;

import java.util.*;

/**
 * This class represents a clustering algorithm.
 */
public class Clustering {

    /**
     * Clusters the given labels based on the distance matrix and the threshold.
     *
     * @param distanceMatrix the distance matrix
     * @param labels         the labels
     * @param threshold      the threshold
     * @return the clusters
     */
    public List<List<String>> cluster(double[][] distanceMatrix, List<String> labels, double threshold) {
        List<Set<Integer>> clusters = initializeClusters(distanceMatrix.length);

        boolean merged;
        do {
            merged = mergeClosestClusters(clusters, distanceMatrix, threshold);
        } while (merged);

        return convertClustersToLabels(clusters, labels);
    }

    /**
     * Initializes clusters where each element starts in its own cluster.
     *
     * @param size the number of elements
     * @return a list of clusters
     */
    private List<Set<Integer>> initializeClusters(int size) {
        List<Set<Integer>> clusters = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            clusters.add(new HashSet<>(Collections.singletonList(i)));
        }
        return clusters;
    }

    /**
     * Merges the closest clusters if their distance is below the threshold.
     *
     * @param clusters       the list of clusters
     * @param distanceMatrix the distance matrix
     * @param threshold      the distance threshold
     * @return true if clusters were merged, false otherwise
     */
    private boolean mergeClosestClusters(List<Set<Integer>> clusters, double[][] distanceMatrix, double threshold) {
        double minDistance = Double.MAX_VALUE;
        int clusterAIndex = -1, clusterBIndex = -1;

        for (int i = 0; i < clusters.size(); i++) {
            for (int j = i + 1; j < clusters.size(); j++) {
                double distance = calculateClusterDistance(clusters.get(i), clusters.get(j), distanceMatrix);
                if (distance < minDistance) {
                    minDistance = distance;
                    clusterAIndex = i;
                    clusterBIndex = j;
                }
            }
        }

        if (minDistance < threshold && clusterAIndex != -1 && clusterBIndex != -1) {
            clusters.get(clusterAIndex).addAll(clusters.get(clusterBIndex));
            clusters.remove(clusterBIndex);
            return true;
        }

        return false;
    }

    /**
     * Calculates the average distance between two clusters.
     *
     * @param clusterA       the first cluster
     * @param clusterB       the second cluster
     * @param distanceMatrix the distance matrix
     * @return the average distance between the two clusters
     */
    private double calculateClusterDistance(Set<Integer> clusterA, Set<Integer> clusterB, double[][] distanceMatrix) {
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

    /**
     * Converts clusters of indices to clusters of labels.
     *
     * @param clusters the clusters of indices
     * @param labels   the list of labels
     * @return the clusters of labels
     */
    private List<List<String>> convertClustersToLabels(List<Set<Integer>> clusters, List<String> labels) {
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
}
