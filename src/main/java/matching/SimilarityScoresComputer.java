package matching;

import bipartiteGraph.Node;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SimilarityScoresComputer {
    private final Indexer indexer;

    public SimilarityScoresComputer(Indexer indexer) {
        this.indexer = indexer;
    }

    public Map<Node, HashMap<Node, Double>> computeSimilarityScores() {
        Map<Node, HashMap<Node, Double>> neighbors = new HashMap<>();
        for (Node node : indexer.getGroup1()) {
            neighbors.put(node, computeSimilarityScoresForNode(node));
        }
        return neighbors;
    }

    public HashMap<Node, Double> computeSimilarityScoresForNode(Node node) {
        HashMap<Node, Double> neighbors = new HashMap<>();
        for (String tk : node.getTokens()) {
            for (Node neighbor : indexer.getTokenMap().getOrDefault(tk, Collections.emptyList())) {
                if ((indexer.getGroup1().contains(node) && indexer.getGroup1().contains(neighbor)) ||
                        (indexer.getGroup2().contains(node) && indexer.getGroup2().contains(neighbor))) {
                    continue;
                } else {
                    double score = indexer.computeIdf(tk);
                    if (score > 0.0) {
                        neighbors.merge(neighbor, score, Double::sum);
                    }
                }
            }
        }
        return neighbors;
    }
}
