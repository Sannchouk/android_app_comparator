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

    /**
     * Compute the similarity scores between all nodes.
     *
     * @return the similarity scores
     */
    public Map<Node, HashMap<Node, Double>> computeSimilarityScores() {
        for (Node node : indexer.getGroup1()) {
            similarityScores.put(node, computeSimilarityScoresForNode(node));
        }
        computePropagationScores();
        applyPenalization();
        return similarityScores;
    }

    /**
     * Compute the propagation scores between all nodes.
     */
    private void computePropagationScores() {
        for (Node node : indexer.getGroup1()) {
            for (Node neighbor : indexer.getGroup2()) {
                if (similarityScores.get(node).get(neighbor) != 0.0) {
                    computePropagationScore(node, neighbor);
                }
            }
        }
    }

    /**
     * Compute the propagation score between a node and its neighbor.
     * The propagation score is computed by taking the similarity score between the node and its neighbor
     * and adding the similarity score between the parent of the node and the parent of the neighbor multiplied by a weight.
     * The parents similarity scores are also updated by adding the similarity score between the node and the neighbor multiplied by a weight.
     * The propagation score is computed for 3 levels of propagation.
     * The weights are defined in the W_PROPAGATION_WEIGHTS and V_PROPAGATION_WEIGHTS arrays.
     * W_PROPAGATION_WEIGHTS[i] is the weight for the node and neighbor similarity score at level i.
     * V_PROPAGATION_WEIGHTS[i] is the weight for the parent similarity score at level i.
     *
     * @param node     the node
     * @param neighbor the neighbor
     */
    private void computePropagationScore(Node node, Node neighbor) {
        Node nodeParent = node.getParent();
        Node neighborParent = neighbor.getParent();
        if (nodeParent != null && neighborParent != null) {
            double score = similarityScores.get(node).get(neighbor);
            double parentScore = similarityScores.get(nodeParent).get(neighborParent);
            double newScore;
            double newParentScore;
            for (int i = 0; i < PROPAGATION; i++) {
                newScore = score + W_PROPAGATION_WEIGHTS[i] * parentScore;
                newParentScore = parentScore + V_PROPAGATION_WEIGHTS[i] * score;
                similarityScores.get(node).put(neighbor, newScore);
                similarityScores.get(nodeParent).put(neighborParent, newParentScore);
                score = newScore;
                parentScore = newParentScore;
            }
        }
    }

    /**
     * Apply penalization to the similarity scores between nodes.
     */
    private void applyPenalization() {
        for (Node node : indexer.getGroup1()) {
            for (Node neighbor : indexer.getGroup2()) {
                if (similarityScores.get(node).get(neighbor) != 0.0) {
                    penalize(node, neighbor);
                }
            }
        }
    }

    /**
     * Penalize the similarity score between a node and its neighbor.
     * The penalization is computed by taking the absolute difference between the number of children of the node and the number of children of the neighbor
     * and dividing it by the maximum number of children between the node and the neighbor.
     * The penalization is then multiplied by the similarity score between the node and the neighbor.
     *
     * @param node     the node
     * @param neighbor the neighbor
     */
    private void penalize(Node node, Node neighbor) {
        int nodeChildren = node.getChildren().size();
        int neighborChildren = neighbor.getChildren().size();
        if (nodeChildren == 0 && neighborChildren == 0) {
            return;
        }
        double penalization = (double) Math.abs(nodeChildren - neighborChildren) / Math.max(nodeChildren, neighborChildren);
        double score = similarityScores.get(node).get(neighbor);
        similarityScores.get(node).put(neighbor, score * (1 - penalization));
    }

    /**
     * Compute the similarity scores between a node and all its neighbors.
     * The similarity score between a node and a neighbor is computed by summing the idf of the tokens of the node that are also in the neighbor.
     *
     * @param node the node
     * @return the similarity scores
     */
    private HashMap<Node, Double> computeSimilarityScoresForNode(Node node) {
        HashMap<Node, Double> neighbors = new HashMap<>();
        for(Node nodes : indexer.getGroup2()) {
            neighbors.put(nodes, 0.0);
        }
        for (String tk : node.getTokens()) {
            for (Node neighbor : indexer.getTokenMap().getOrDefault(tk, Collections.emptyList())) {
                if ((node.getGroup() != null && node.getGroup().equals(neighbor.getGroup()))) {
                    continue;
                }
                double score = indexer.computeIdf(tk);
                neighbors.merge(neighbor, score, Double::sum);
            }
        }
        return neighbors;
    }
}
