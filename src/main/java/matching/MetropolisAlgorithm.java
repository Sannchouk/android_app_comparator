package matching;

import bipartiteGraph.BipartiteGraph;
import bipartiteGraph.Edge;
import lombok.Getter;

import java.awt.desktop.SystemEventListener;
import java.util.ArrayList;
import java.util.List;

public class MetropolisAlgorithm {
    @Getter
    private final BipartiteGraph graph;
    private final double beta;
    private final double gamma;
    private final int nbIterations;
    private List<Edge> currentMatching;
    private final MatchingSuggester suggester;

    public MetropolisAlgorithm(BipartiteGraph graph, double beta, double gamma, int nbIterations) {
        this.graph = graph;
        this.beta = beta;
        this.gamma = gamma;
        this.nbIterations = nbIterations;
        this.currentMatching = new ArrayList<>(graph.getEdges());
        this.suggester = new MatchingSuggester(gamma);
    }

    public List<Edge> getMatching() {
        return currentMatching;
    }

    public double computeCost(List<Edge> edges) {
        double cost = 0.0;
        for (Edge edge : edges) {
            cost += 1 / (1 + edge.getValue());
        }
        cost *= -beta;
        if (edges.isEmpty()) {
            return Double.POSITIVE_INFINITY;
        }
        cost /= edges.size();
        return Math.exp(cost);
    }

    public void run() {
        double currentCost = computeCost(currentMatching);
        for (int i = 0; i < nbIterations; i++) {
            List<Edge> newMatching = selectNewMatching();
            double newCost = computeCost(newMatching);
            System.out.println("Current cost: " + currentCost);
            System.out.println("New cost: " + newCost);
            System.out.println("Current matching: " + currentMatching.size());
            if (newCost < currentCost) {
                currentMatching = newMatching;
                currentCost = newCost;
            }
        }
    }

    private List<Edge> selectNewMatching() {
        return suggester.suggestNewMatching(graph, currentMatching);
    }
}
