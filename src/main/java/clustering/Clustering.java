package clustering;

import java.util.*;

public class Clustering {

    public List<List<String>> cluster(double[][] distanceMatrix, List<String> labels, double threshold) {
        int n = distanceMatrix.length;
        List<Set<Integer>> clusters = new ArrayList<>();

        // Initialize each item as a cluster
        for (int i = 0; i < n; i++) {
            clusters.add(new HashSet<>(Collections.singletonList(i)));
        }

        boolean merged;
        do {
            merged = false;
            double minDistance = Double.MAX_VALUE;
            int clusterA = -1, clusterB = -1;

            // Find the two closest clusters
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

            // Merge the two closest clusters if the distance is below the threshold
            if (minDistance < threshold && clusterA != -1 && clusterB != -1) {
                clusters.get(clusterA).addAll(clusters.get(clusterB));
                clusters.remove(clusterB);
                merged = true;
            }

        } while (merged);

        // Convert sets to lists of labels
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
