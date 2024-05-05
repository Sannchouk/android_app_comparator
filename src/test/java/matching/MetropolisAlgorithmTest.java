package matching;

import bipartiteGraph.BipartiteGraph;
import bipartiteGraph.Edge;
import bipartiteGraph.Node;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MetropolisAlgorithmTest {

    @Test
    void testComputeCost() {
        BipartiteGraph graph = new BipartiteGraph();
        Node node1 = new Node("node1", List.of());
        Node node2 = new Node("node2", List.of());
        Node node3 = new Node("node3", List.of());
        List<Edge> edges = List.of(
                new Edge(node1, node2, 1.0),
                new Edge(node2, node3, 2.0),
                new Edge(node1, node3, 3.0)
        );

        MetropolisAlgorithm metropolisAlgorithm = new MetropolisAlgorithm(graph, 2.5, 0.8, 10);

        double cost = metropolisAlgorithm.computeCost(edges);
        double expectedCost = Math.exp(-2.5 * (1.0 + 2.0 + 3.0) / 3.0);
        assertEquals(expectedCost, cost);
    }

    @Test
    void testAlgorithm() {
        // Given
        Node node1 = new Node("node1", List.of());
        Node node2 = new Node("node2", List.of());
        Node node3 = new Node("node3", List.of());
        Node node4 = new Node("node4", List.of());
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
        double biggestCost = metropolisAlgorithm.computeCost(List.of(edge1, edge4));
        double lowestCost = metropolisAlgorithm.computeCost(List.of(edge2, edge3));
        double acceptableCost = (biggestCost + lowestCost) / 2;

        // When
        metropolisAlgorithm.run();

        List<Edge> matching = metropolisAlgorithm.getMatching();
        double finalCost = metropolisAlgorithm.computeCost(matching);
        assert(lowestCost < finalCost);
        assert(finalCost < acceptableCost);
    }
}
