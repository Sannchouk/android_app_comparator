package matching;

import bipartiteGraph.Node;
import lombok.Getter;

import java.util.*;

public class Indexer {

    @Getter
    private Map<String, List<Node>> tokenMap;
    @Getter
    private List<Node> group1;
    @Getter
    private List<Node> group2;

    public Indexer() {
        this.tokenMap = new HashMap<>();
        this.group1 = new ArrayList<>();
        this.group2 = new ArrayList<>();
    }


    public void addNodes(List<Node> nodes, int group) {
        for (Node node : nodes) {
            addNode(node, group);
        }
    }

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
            tokenMap.computeIfAbsent(tk, k -> new ArrayList<>()).add(node);
        }
    }

    public double computeIdf(String token) {
        int count = tokenMap.getOrDefault(token, Collections.emptyList()).size();
        int totalNodes = group1.size() + group2.size();
        return (totalNodes != 0 && count != 0) ? Math.log((double) totalNodes / count) : 0.0;
    }
}
