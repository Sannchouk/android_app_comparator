package matching.computers.similarities;

import bipartiteGraph.Node;

import java.util.HashMap;
import java.util.Map;

public class PropagationComputer {
    private static final int PROPAGATION = 3;
    private static final float[] W_PROPAGATION_WEIGHTS = {0.4f, 0.04f, 0.004f};
    private static final float[] V_PROPAGATION_WEIGHTS = {0.8f, 0.08f, 0.008f};

    public void computePropagationScores(Map<Node, HashMap<Node, Double>> similarityScores, Node node, Node neighbor) {
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
}
