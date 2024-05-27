package matching.algorithm;

import bipartiteGraph.BipartiteGraph;
import bipartiteGraph.Edge;
import lombok.Getter;
import matching.algorithm.MatchingSuggester;

import java.util.ArrayList;
import java.util.List;

public class MetropolisAlgorithm {
    @Getter
    private final BipartiteGraph graph;
    private final double beta;
    private final double gamma;
    private final int nbIterations;
    private List<Edge> currentMatching;
    @Getter
    private final MatchingSuggester suggester;
    private final CostComputer costComputer;

    public MetropolisAlgorithm(BipartiteGraph graph, double beta, double gamma, int nbIterations) {
        this.graph = graph;
        this.beta = beta;
        this.gamma = gamma;
        this.nbIterations = nbIterations;
        this.currentMatching = new ArrayList<>(graph.getEdges());
        this.suggester = new MatchingSuggester(gamma);
        this.costComputer = new CostComputer(beta);
    }

    public List<Edge> getMatching() {
        return currentMatching;
    }

    public void run() {
        double currentCost = computeCost(currentMatching);
        for (int i = 0; i < nbIterations; i++) {
            List<Edge> newMatching = selectNewMatching();
            double newCost = computeCost(newMatching);
            if (!isFullMatchingCorrect(currentMatching) || newCost < currentCost) {
                currentMatching = newMatching;
                currentCost = newCost;
            }
        }
        graph.setEdges(currentMatching);
    }

    public double computeCost(List<Edge> matching) {
        return this.costComputer.computeCost(matching, graph.getNodeGroup1(), graph.getNodeGroup2()
        );
    }

    private boolean isFullMatchingCorrect(List<Edge> matching) {
        return matching.size() <= Math.max(graph.getNodeGroup1().size(), graph.getNodeGroup2().size());
    }

    private List<Edge> selectNewMatching() {
        return suggester.suggestNewMatching(graph.getEdges());
    }
}
