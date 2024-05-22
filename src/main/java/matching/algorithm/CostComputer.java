package matching.algorithm;

import bipartiteGraph.Edge;
import lombok.Getter;

import java.math.BigInteger;
import java.util.List;


public class CostComputer {

    private final double beta;
    @Getter
    private final static double WN = 1.0;

    public CostComputer(double beta) {
        this.beta = beta;
    }

    public double computeCost(List<Edge> edges, int maxSize) {
        if (edges.isEmpty()) {
            return Double.POSITIVE_INFINITY;
        }
        double nomatchCost = WN * (maxSize - edges.size());
        double matchCosts = edges.stream().mapToDouble(this::computeEdgeCost).sum();
        return Math.exp(-beta * (nomatchCost + matchCosts) / edges.size());
    }

    private double computeEdgeCost(Edge edge) {
        int node1Size = Integer.parseInt(edge.getSource().getAttributes().get("size"));
        int node2Size = Integer.parseInt(edge.getTarget().getAttributes().get("size"));
        return computeWeight(node1Size, node2Size) * (1 / (1 + edge.getValue()));
    }

    private double computeWeight(int node1Size, int node2Size) {
        return (1 / (1 + Math.exp(-(node1Size + node2Size))));
    }




}
