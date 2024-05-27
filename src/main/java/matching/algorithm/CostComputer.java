package matching.algorithm;

import bipartiteGraph.Edge;
import bipartiteGraph.Node;
import lombok.Getter;

import java.math.BigInteger;
import java.util.List;

/**
 * This class is responsible to compute the cost of a matching
 */
public class CostComputer {

    private final double beta;
    @Getter
    private final static double WN = 1.2;

    private int apk1Size;
    private int apk2Size;

    public CostComputer(double beta) {
        this.beta = beta;
    }

    public double computeCost(List<Edge> edges, List<Node> group1, List<Node> group2) {
        if (edges.isEmpty()) {
            return Double.POSITIVE_INFINITY;
        }
        apk1Size = group1.stream()
                .mapToInt(node -> Integer.parseInt(node.getAttributes().get("size")))
                .sum();
        apk2Size = group2.stream()
                .mapToInt(node -> Integer.parseInt(node.getAttributes().get("size")))
                .sum();
        double nomatchCost = WN * (Math.max(group1.size(), group2.size()) - edges.size());
        double matchCosts = edges.stream().mapToDouble(this::computeEdgeCost).sum();
        return Math.exp(-beta * (nomatchCost + matchCosts) / edges.size());
    }

    private double computeEdgeCost(Edge edge) {
        int node1Size = Integer.parseInt(edge.getSource().getAttributes().get("size"));
        int node2Size = Integer.parseInt(edge.getTarget().getAttributes().get("size"));
        return computeWeight(node1Size, node2Size) * (1 / (1 + edge.getValue()));
    }

    private double computeWeight(int node1Size, int node2Size) {
        return (double) node1Size / apk1Size + (double) node2Size / apk2Size;
    }




}
