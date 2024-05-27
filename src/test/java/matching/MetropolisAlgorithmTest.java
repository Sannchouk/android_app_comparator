package matching;

import bipartiteGraph.BipartiteGraph;
import bipartiteGraph.Edge;
import bipartiteGraph.Node;
import matching.algorithm.MetropolisAlgorithm;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MetropolisAlgorithmTest {

    @Test
    void testAlgorithm() {
        // Given
        Map<String, String> attributes = new HashMap<>();
        attributes.put("size", "1");
        Node node1 = new Node(Path.of("node1"), attributes);
        Node node2 = new Node(Path.of("node2"), attributes);
        Node node3 = new Node(Path.of("node3"), attributes);
        Node node4 = new Node(Path.of("node4"), attributes);
        Edge edge1 = new Edge(node1, node3, 1.0);
        Edge edge2 = new Edge(node1, node4, 2.0);
        Edge edge3 = new Edge(node2, node3, 4.0);
        Edge edge4 = new Edge(node2, node4, 3.0);
        List<Edge> edges = List.of(edge1, edge2, edge3, edge4);
        BipartiteGraph graph = new BipartiteGraph();
        graph.addNodes(List.of(node1, node2), 1);
        graph.addNodes(List.of(node3, node4), 2);
        graph.addEdges(edges);
        MetropolisAlgorithm metropolisAlgorithm = new MetropolisAlgorithm(graph, 2.5, 0.8, 10);
        double biggestCost = metropolisAlgorithm.computeCost(List.of(edge2, edge3));
        double lowestCost = metropolisAlgorithm.computeCost(List.of(edge1, edge4));
        double acceptableCost = (biggestCost + lowestCost) / 2;

        // When
        metropolisAlgorithm.run();

        List<Edge> matching = metropolisAlgorithm.getMatching();
        double finalCost = metropolisAlgorithm.computeCost(matching);
        assertTrue(lowestCost <= finalCost);
        assertTrue(finalCost < acceptableCost);
    }
}
