package matching;

import bipartiteGraph.Node;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SimilarityScoresComputer {
    private final Indexer indexer;

    private static final int PROPAGATION = 3;
    private static final float[] PROPAGATION_WEIGHTS = {0.4f, 0.04f, 0.004f};

    private final Map<Node, HashMap<Node, Double>> similarityScores = new HashMap<>();


    public SimilarityScoresComputer(Indexer indexer) {
        this.indexer = indexer;
    }

    private double computePropagationScore(Node node, Node neighbor, double score) {
        double parentScore;
        Node nodeParent = node.getParent();
        Node neighborParent = neighbor.getParent();
        for (int i = 0; i < PROPAGATION; i++) {
            parentScore = similarityScores.getOrDefault(nodeParent, new HashMap<>()).getOrDefault(neighborParent, 0.0);
            score = score + PROPAGATION_WEIGHTS[i] * parentScore;
//            if (nodeParent != null && neighbo1rParent != null) {
//                similarityScores.getOrDefault()
//            }
        }
        return score;
    }

    public Map<Node, HashMap<Node, Double>> computeSimilarityScores() {
        for (Node node : indexer.getGroup1()) {
            similarityScores.put(node, computeSimilarityScoresForNode(node));
        }
        return similarityScores;
    }

    public HashMap<Node, Double> computeSimilarityScoresForNode(Node node) {
        HashMap<Node, Double> neighbors = new HashMap<>();
        for(Node nodes : indexer.getGroup2()) {
            neighbors.put(nodes, 0.0);
        }
        for (String tk : node.getTokens()) {
            for (Node neighbor : indexer.getTokenMap().getOrDefault(tk, Collections.emptyList())) {
                if ((indexer.getGroup1().contains(node) && indexer.getGroup1().contains(neighbor)) ||
                        (indexer.getGroup2().contains(node) && indexer.getGroup2().contains(neighbor))) {
                    continue;
                } else {
                    double score = indexer.computeIdf(tk);
                    neighbors.merge(neighbor, score, Double::sum);
                }
            }
        }
        return neighbors;
    }
}
