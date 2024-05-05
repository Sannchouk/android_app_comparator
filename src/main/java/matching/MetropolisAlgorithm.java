package matching;

import bipartiteGraph.BipartiteGraph;
import bipartiteGraph.Edge;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class MetropolisAlgorithm {
    @Getter
    private final BipartiteGraph graph;
    private final double beta;
    private final double gamma;
    private final int nbIterations;
    private List<Edge> currentMatching;

    public MetropolisAlgorithm(BipartiteGraph graph, double beta, double gamma, int nbIterations) {
        this.graph = graph;
        this.beta = beta;
        this.gamma = gamma;
        this.nbIterations = nbIterations;
        this.currentMatching = new ArrayList<>(graph.getEdges());
    }

    public List<Edge> getMatching() {
        return currentMatching;
    }

    public double computeCost(List<Edge> edges) {
        double cost = 0.0;
        for (Edge edge : edges) {
            cost += edge.getValue();
        }
        cost *= -beta;
        cost /= edges.size();
        return Math.exp(cost);
    }

    public void run() {
        double currentCost = computeCost(currentMatching);
        for (int i = 0; i < nbIterations; i++) {
            List<Edge> newMatching = selectNewMatching();
            double newCost = computeCost(newMatching);
            if (newCost < currentCost) {
                currentMatching = newMatching;
                currentCost = newCost;
            }
        }
    }

    private List<Edge> selectNewMatching() {
        MatchingSuggester suggester = new MatchingSuggester();
        return suggester.suggestNewMatching(graph, currentMatching);
    }
}
