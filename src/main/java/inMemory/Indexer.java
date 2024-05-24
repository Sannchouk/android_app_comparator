package inMemory;

import bipartiteGraph.Node;
import lombok.Getter;

import java.util.*;

/**
 * This class represents an indexer that is used to index nodes by their tokens.
 */
public class Indexer {

    @Getter
    private final Map<String, List<Node>> tokenMap;
    @Getter
    private final List<Node> group1;
    @Getter
    private final List<Node> group2;

    public Indexer() {
        this.tokenMap = new HashMap<>();
        this.group1 = new ArrayList<>();
        this.group2 = new ArrayList<>();
    }

    /**
     * Adds a list of nodes to the indexer.
     * @param nodes the list of nodes
     * @param group the group of the nodes
     */
    public void addNodes(List<Node> nodes, int group) {
        for (Node node : nodes) {
            addNode(node, group);
        }
    }

    /**
     * Adds a node to the indexer.
     * @param node the node
     * @param group the group of the node
     */
    public void addNode(Node node, int group) {
        switch (group) {
            case 1:
                group1.add(node);
                break;
            case 2:
                group2.add(node);
                break;
            default:
                throw new IllegalArgumentException("Invalid group number");
        }
        for (String tk : node.getTokens()) {
            tokenMap.putIfAbsent(tk, new ArrayList<>());
            tokenMap.get(tk).add(node);
        }
    }

    /**
     * Computes the inverse document frequency of a token.
     * @param token the token
     * @return the inverse document frequency
     */
    public double computeIdf(String token) {
        int count = tokenMap.getOrDefault(token, Collections.emptyList()).size();
        int totalNodes = group1.size() + group2.size();
        return (totalNodes != 0 && count != 0) ? Math.log((double) totalNodes / count) : 0.0;
    }
}
