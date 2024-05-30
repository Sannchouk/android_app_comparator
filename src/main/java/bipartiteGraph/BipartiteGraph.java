package bipartiteGraph;

import fileTree.FileTree;
import inMemory.AttributeSetter;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class represents a bipartite graph, that is used during the matching process.
 */
@Getter
public class BipartiteGraph {
    @Setter
    private List<Node> nodeGroup1;
    @Setter
    private List<Node> nodeGroup2;
    @Setter
    private List<Edge> edges;
    private final AttributeSetter attributeSetter = new AttributeSetter();

    public static BipartiteGraph buildFromTrees(FileTree tree1, FileTree tree2) {
        BipartiteGraph graph = new BipartiteGraph();
        List<Node> nodes1 = new ArrayList<>();
        List<Node> nodes2 = new ArrayList<>();
        graph.initNodes(tree1, nodes1, 1);
        graph.initNodes(tree2, nodes2, 2);
        graph.addNodes(nodes1, 1);
        graph.addNodes(nodes2, 2);
        return graph;
    }

    public BipartiteGraph() {
        this.nodeGroup1 = new ArrayList<>();
        this.nodeGroup2 = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    /**
     * Adds a list of nodes to the graph.
     * @param nodes the list of nodes
     * @param group the group of the nodes
     */
    public void addNodes(List<Node> nodes, int group) {
        for (Node node : nodes) {
            addNode(node, group);
        }
    }

    /**
     * Finds a node in a group.
     * @param group the group
     * @param node the node
     * @return the node if it is found, null otherwise
     */
    public Node findNode(int group, Node node) {
        List<Node> nodeGroup = (group == 1) ? nodeGroup1 : (group == 2) ? nodeGroup2 : null;
        if (nodeGroup == null) return null;

        for (Node n : nodeGroup) {
            if (n == node) {
                return node;
            }
        }
        return null;
    }

    /**
     * Gets the group of a node.
     * @param node the node
     * @return the group of the node
     */
    public Integer getGroup(Node node) {
        if (findNode(1, node) != null) {
            return 1;
        }
        if (findNode(2, node) != null) {
            return 2;
        }
        return null;
    }

    /**
     * Adds a node to the graph.
     * @param node the node
     * @param group the group of the node
     */
    public void addNode(Node node, int group) {
        if (group == 1) {
            nodeGroup1.add(node);
        } else if (group == 2) {
            nodeGroup2.add(node);
        }
    }

    /**
     * Adds a list of edges to the graph.
     * @param edges the list of edges
     * @throws IllegalArgumentException if both nodes are in the same group
     */
    public void addEdges(List<Edge> edges) throws IllegalArgumentException {
        for (Edge edge : edges) {
            addEdge(edge);
        }
    }

    /**
     * Adds an edge to the graph.
     * @param edge the edge
     * @throws IllegalArgumentException if both nodes are in the same group
     */
    public void addEdge(Edge edge) throws IllegalArgumentException {
        Integer sourceGroup = getGroup(edge.getSource());
        Integer targetGroup = getGroup(edge.getTarget());

        if (sourceGroup == null || targetGroup == null || sourceGroup.equals(targetGroup)) {
            throw new IllegalArgumentException("Both nodes are in the same group");
        }

        this.edges.add(edge);
    }

    /**
     * Removes an edge from the graph.
     * @param edge the edge
     */
    public void removeEdge(Edge edge) {
        this.edges.remove(edge);
    }

    /**
     * Builds the edges of the graph from a map of neighborhoods.
     * @param neighborhoods the map of neighborhoods
     */
    public void buildEdgesFromNeighborhoods(Map<Node, HashMap<Node, Double>> neighborhoods) {
        for (Map.Entry<Node, HashMap<Node, Double>> entry : neighborhoods.entrySet()) {
            Node node = entry.getKey();
            Map<Node, Double> neighborhood = entry.getValue();
            buildEdgesFromNeighborhood(node, neighborhood);
        }
    }

    /**
     * Builds the edges of the graph from a neighborhood.
     * @param node the node
     * @param neighborhood the neighborhood
     */
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

    private void initNodes(FileTree tree, List<Node> nodes, int group) {
        ConcurrentMap<Node, Integer> parents = new ConcurrentHashMap<>();
        List<FileTree> fileTreeNodes = tree.getNodes();
        List<Node> synchronizedNodes = Collections.synchronizedList(new ArrayList<>());
        createNodes(fileTreeNodes, synchronizedNodes, parents, group);
        nodes.addAll(synchronizedNodes);
        setParentRelationships(parents, synchronizedNodes);
    }

    private void createNodes(List<FileTree> fileTreeNodes, List<Node> synchronizedNodes, ConcurrentMap<Node,Integer> parents, int group) {
        fileTreeNodes.parallelStream().forEach(fileTree -> {
            Node node;
            if (fileTree.getGraphNode() == null) {
                node = new Node(fileTree.getPath());
                node.setId(fileTree.getId());
                node.setGroup(group);
                attributeSetter.tokenize(node);
                synchronizedNodes.add(node);
                fileTree.setGraphNode(node);
            } else {
                node = fileTree.getGraphNode();
            }
            if (fileTree.getParent() != null) {
                parents.put(node, fileTree.getParent().getId());
            }
        });
    }

    private void setParentRelationships(ConcurrentMap<Node, Integer> parents, List<Node> synchronizedNodes) {
        parents.entrySet().parallelStream().forEach(entry -> {
            Node node = entry.getKey();
            Integer parentId = entry.getValue();
            Node parent = synchronizedNodes.stream().filter(n -> n.getId() == parentId).findFirst().orElse(null);
            node.setParent(parent);
            if (parent != null) {
                synchronized (parent) {
                    parent.getChildren().add(node);
                }
            }
        });
    }

}