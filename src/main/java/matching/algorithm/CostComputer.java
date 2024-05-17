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

    public double computeCost(List<Edge> edges, int totalNumberOfNodes) {
        if (edges.isEmpty()) {
            return Double.POSITIVE_INFINITY;
        }
        double cost = edges.stream().mapToDouble(edge -> 1 / (1 + edge.getValue())).sum();
        cost += WN *(totalNumberOfNodes - 2 * edges.size());
        return Math.exp(-beta * cost / edges.size());
    }


}
