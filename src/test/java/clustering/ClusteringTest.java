package clustering;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Arrays;

public class ClusteringTest {

    @Test
    public void testSingleCluster() {
        double[][] distanceMatrix = {
                {0.0}
        };
        List<List<String>> clusters = new Clustering().cluster(distanceMatrix, List.of("A"), 1.0);
        assertEquals(1, clusters.size());
        assertEquals(List.of("A"), clusters.get(0));
    }

    @Test
    public void testTwoClusters() {
        double[][] distanceMatrix = {
                {0.0, 0.8},
                {0.8, 0.0}
        };
        List<List<String>> clusters = new Clustering().cluster(distanceMatrix, List.of("A", "B"), 0.7);
        assertEquals(2, clusters.size());
        assertTrue(clusters.contains(List.of("A")));
        assertTrue(clusters.contains(List.of("B")));
    }

    @Test
    public void testMultipleClusters() {
        double[][] distanceMatrix = {
                {0.0, 0.8, 0.9},
                {0.8, 0.0, 0.85},
                {0.9, 0.85, 0.0}
        };
        List<List<String>> clusters = new Clustering().cluster(distanceMatrix, List.of("A", "B", "C"), 0.5);
        assertEquals(3, clusters.size());
        assertTrue(clusters.contains(List.of("A")));
        assertTrue(clusters.contains(List.of("B")));
        assertTrue(clusters.contains(List.of("C")));
    }
}
