package matching.computers.similarities;

import bipartiteGraph.Node;

import java.util.HashMap;
import java.util.Map;

public class PenalizationComputer {
    public void applyPenalization(Map<Node, HashMap<Node, Double>> similarityScores, Node node, Node neighbor) {
        int nodeChildren = node.getChildren().size();
        int neighborChildren = neighbor.getChildren().size();
        if (nodeChildren == 0 && neighborChildren == 0) {
            return;
        }
        double penalization = (double) Math.abs(nodeChildren - neighborChildren) / Math.max(nodeChildren, neighborChildren);
        double score = similarityScores.get(node).get(neighbor);
        similarityScores.get(node).put(neighbor, score * (1 - penalization));
    }
}
