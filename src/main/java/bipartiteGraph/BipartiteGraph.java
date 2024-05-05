package bipartiteGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BipartiteGraph {
    private List<Node> nodeGroup1;
    private List<Node> nodeGroup2;
    private List<Edge> edges;

    public BipartiteGraph() {
        this.nodeGroup1 = new ArrayList<>();
        this.nodeGroup2 = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public List<Node> getNodeGroup1() {
        return nodeGroup1;
    }

    public List<Node> getNodeGroup2() {
        return nodeGroup2;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void addNodes(List<Node> nodes, int group) {
        for (Node node : nodes) {
            addNode(node, group);
        }
    }

    public Node findNode(int group, String name) {
        List<Node> nodeGroup = (group == 1) ? nodeGroup1 : (group == 2) ? nodeGroup2 : null;
        if (nodeGroup == null) return null;

        for (Node node : nodeGroup) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }

    public Integer getGroup(String name) {
        if (findNode(1, name) != null) {
            return 1;
        }
        if (findNode(2, name) != null) {
            return 2;
        }
        return null;
    }

    public void addNode(Node node, int group) {
        if (group == 1) {
            nodeGroup1.add(node);
        } else if (group == 2) {
            nodeGroup2.add(node);
        }
    }

    public void removeNode(Node node, int group) {
        if (group == 1) nodeGroup1.remove(node);
        else if (group == 2) nodeGroup2.remove(node);
    }

    public void addEdges(List<Edge> edges) throws IllegalArgumentException {
        for (Edge edge : edges) {
            addEdge(edge);
        }
    }

    public void addEdge(Edge edge) throws IllegalArgumentException {
        Integer sourceGroup = getGroup(edge.getSource().getName());
        Integer targetGroup = getGroup(edge.getTarget().getName());

        if (sourceGroup == null || targetGroup == null || sourceGroup.equals(targetGroup)) {
            throw new IllegalArgumentException("Both nodes are in the same group");
        }

        this.edges.add(edge);
    }

    public void removeEdge(Edge edge) {
        this.edges.remove(edge);
    }

    public List<Edge> connectedEdges(Edge edge) {
        List<Edge> connectedEdges = new ArrayList<>();
        for (Edge e : edges) {
            if ((e.getSource().equals(edge.getSource()) && !e.getTarget().equals(edge.getTarget())) ||
                    (!e.getSource().equals(edge.getSource()) && e.getTarget().equals(edge.getTarget()))) {
                connectedEdges.add(e);
            }
        }
        return connectedEdges;
    }

    public void removeAllAdjacentEdges(Edge edge) {
        List<Edge> edgesToRemove = connectedEdges(edge);
        for (Edge e : edgesToRemove) {
            removeEdge(e);
        }
    }

    public void buildEdgesFromNeighborhoods(Map<Node, HashMap<Node, Double>> neighborhoods) {
        for (Map.Entry<Node, HashMap<Node, Double>> entry : neighborhoods.entrySet()) {
            Node node = entry.getKey();
            Map<Node, Double> neighborhood = entry.getValue();
            buildEdgesFromNeighborhood(node, neighborhood);
        }
    }

    private void buildEdgesFromNeighborhood(Node node, Map<Node, Double> neighborhood) {
        for (Map.Entry<Node, Double> entry : neighborhood.entrySet()) {
            Node neighbor = entry.getKey();
            double value = entry.getValue();
            edges.add(new Edge(node, neighbor, value));
        }
    }

    @Override
    public String toString() {
        return "BipartiteGraph{" +
                "nodeGroup1=" + nodeGroup1 +
                ", nodeGroup2=" + nodeGroup2 +
                ", edges=" + edges +
                '}';
    }
}