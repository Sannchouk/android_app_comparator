package bipartiteGraph;

import fileTree.FileTree;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class BipartiteGraph {
    @Setter
    private List<Node> nodeGroup1;
    @Setter
    private List<Node> nodeGroup2;
    @Setter
    private List<Edge> edges;
    private static final Tokenizer tokenizer = new Tokenizer(false, false);

    public static BipartiteGraph buildFromTrees(FileTree tree1, FileTree tree2) {
        BipartiteGraph graph = new BipartiteGraph();
        List<Node> nodes1 = new ArrayList<>();
        List<Node> nodes2 = new ArrayList<>();
        initNodes(tree1, nodes1);
        initNodes(tree2, nodes2);
        graph.addNodes(nodes1, 1);
        graph.addNodes(nodes2, 2);
        return graph;
    }

    private static void initNodes(FileTree tree, List<Node> nodes) {
        Map<Node, Integer> parents = new HashMap<>();
        for (FileTree fileTree : tree.getNodes()) {
            Node node = new Node(fileTree.getData());
            node.setId(fileTree.getId());
            tokenizer.tokenize(node);
            nodes.add(node);
            if (fileTree.getParent() != null) {
                parents.put(node, fileTree.getParent().getId());
            }
        }
        for (Map.Entry<Node, Integer> entry : parents.entrySet()) {
            Node node = entry.getKey();
            Integer parentId = entry.getValue();
            Node parent = nodes.stream().filter(n -> n.getId() == parentId).findFirst().orElse(null);
            node.setParent(parent);
            assert parent != null;
            parent.getChildren().add(node);
        }
    }

    public BipartiteGraph() {
        this.nodeGroup1 = new ArrayList<>();
        this.nodeGroup2 = new ArrayList<>();
        this.edges = new ArrayList<>();
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
            if (value > 0.0) edges.add(new Edge(node, neighbor, value));
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