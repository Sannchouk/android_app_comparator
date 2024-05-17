package matching.algorithm;

import bipartiteGraph.Edge;
import lombok.Getter;

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
        double matchCosts = edges.stream().mapToDouble(edge -> 1 / (1 + edge.getValue())).sum();
        return Math.exp(-beta * (nomatchCost + matchCosts) / edges.size());
    }


}
