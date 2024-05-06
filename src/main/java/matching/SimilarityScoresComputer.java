package matching;

import bipartiteGraph.Node;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SimilarityScoresComputer {
    private final Indexer indexer;

    private static final int PROPAGATION = 3;
    private static final float[] W_PROPAGATION_WEIGHTS = {0.4f, 0.04f, 0.004f};
    private static final float[] V_PROPAGATION_WEIGHTS = {0.8f, 0.08f, 0.008f};

    private final Map<Node, HashMap<Node, Double>> similarityScores = new HashMap<>();


    public SimilarityScoresComputer(Indexer indexer) {
        this.indexer = indexer;
    }

    private void computePropagationScores() {
        for (Node node : indexer.getGroup1()) {
            for (Node neighbor : indexer.getGroup2()) {
                computePropagationScore(node, neighbor);
            }
        }
    }

    private void computePropagationScore(Node node, Node neighbor) {
        double score = similarityScores.get(node).get(neighbor);
        double parentScore;
        Node nodeParent = node.getParent();
        Node neighborParent = neighbor.getParent();
        for (int i = 0; i < PROPAGATION; i++) {
            if (nodeParent != null && neighborParent != null) {
                parentScore = similarityScores.get(nodeParent).get(neighborParent);
                double newScore = score + W_PROPAGATION_WEIGHTS[i] * parentScore;
                double newParentScore = parentScore + V_PROPAGATION_WEIGHTS[i] * score;
                similarityScores.get(node).put(neighbor, newScore);
                similarityScores.get(nodeParent).put(neighborParent, newParentScore);
            }
        }
    }

    private void applyPenalizations() {
        for (Node node : indexer.getGroup1()) {
            for (Node neighbor : indexer.getGroup2()) {
                penalize(node, neighbor);
            }
        }
    }

    private void penalize(Node node, Node neighbor) {
        int nodeChildren = node.getChildren().size();
        int neighborChildren = neighbor.getChildren().size();
        if (nodeChildren == 0 || neighborChildren == 0) {
            return;
        }
        double penalization = (double) Math.abs(nodeChildren - neighborChildren) / Math.max(nodeChildren, neighborChildren);
        double score = similarityScores.get(node).get(neighbor);
        similarityScores.get(node).put(neighbor, score * (1 - penalization));
    }

    public Map<Node, HashMap<Node, Double>> computeSimilarityScores() {
        for (Node node : indexer.getGroup1()) {
            similarityScores.put(node, computeSimilarityScoresForNode(node));
        }
        computePropagationScores();
        applyPenalizations();
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
