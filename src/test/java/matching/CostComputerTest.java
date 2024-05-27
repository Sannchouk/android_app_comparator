package matching;

import bipartiteGraph.Edge;
import bipartiteGraph.Node;
import matching.algorithm.CostComputer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CostComputerTest {

    @ParameterizedTest
    @MethodSource({"getParameters"})
    void testComputeCost(List<Edge> edges, int maxSize) {
        CostComputer costComputer = new CostComputer(2.5);

        double actualCost = costComputer.computeCost(edges, maxSize);
        double expectedCost;
        if (maxSize == 0) {
            expectedCost = Double.POSITIVE_INFINITY;
        }
        else {
            double nomatchCost = CostComputer.getWN() * (maxSize - edges.size());
            double matchCosts = edges.stream().mapToDouble(edge -> (1 / (1 + Math.exp(-(1 + 1)))) / (1 + edge.getValue())).sum();
            expectedCost = Math.exp((-2.5 * (matchCosts + nomatchCost) / edges.size()));
        }
        assertEquals(expectedCost, actualCost);
        assertTrue(actualCost > 0);
    }

    private static Stream<Arguments> getParameters() {
        Node node1 = new Node("1");
        Node node2 = new Node("2");
        Node node3 = new Node("3");
        Node node4 = new Node("4");
        Node node5 = new Node("5");

        node1.getAttributes().put("size", "1");
        node2.getAttributes().put("size", "1");
        node3.getAttributes().put("size", "1");
        node4.getAttributes().put("size", "1");
        node5.getAttributes().put("size", "1");

        List<Edge> edges1 = new ArrayList<>();
        edges1.add(new Edge(node1, node2, 3));
        edges1.add(new Edge(node1, node3, 2));
        edges1.add(new Edge(node1, node4, 1));

        List<Edge> edges2 = new ArrayList<>();

        List<Edge> edges3 = new ArrayList<>();
        edges3.add(new Edge(node1, node3, 2));
        edges3.add(new Edge(node2, node4, 1));
        edges3.add(new Edge(node2, node5, 1));

        return Stream.of(Arguments.of(edges1, 3), Arguments.of(edges2, 0), Arguments.of(edges3, 4));
    }

}
