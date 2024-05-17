package matching;

import bipartiteGraph.BipartiteGraph;
import bipartiteGraph.Edge;
import bipartiteGraph.Node;
import matching.algorithm.CostComputer;
import matching.algorithm.MetropolisAlgorithm;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CostComputerTest {

    @Test
    void testComputeCost() {
        Node node1 = new Node(Path.of("node1"), List.of());
        Node node2 = new Node(Path.of("node2"), List.of());
        Node node3 = new Node(Path.of("node3"), List.of());
        List<Edge> edges = List.of(
                new Edge(node1, node2, 1.0),
                new Edge(node2, node3, 2.0),
                new Edge(node1, node3, 3.0)
        );

        CostComputer costComputer = new CostComputer(2.5);

        double cost = costComputer.computeCost(edges, 3);
        double nomatchCost = CostComputer.getWN() * (3 - 6);
        double expectedCost = Math.exp((-2.5 * ((double) 1 /2 + (double) 1 /3 + (double) 1 /4 + nomatchCost) / edges.size()));
        assertEquals(expectedCost, cost);
    }

}
