package bipartiteGraph;

import fileTree.FileTree;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

public class BipartiteGraphTest {

    @Test
    public void testFindNode() {
        Node node1 = new Node("name1");
        Node node2 = new Node("name2");
        Node node3 = new Node("name3");

        BipartiteGraph graph = new BipartiteGraph();
        graph.addNode(node1, 1);
        graph.addNode(node2, 2);

        assertEquals(node1, graph.findNode(1, node1));
        assertEquals(node2, graph.findNode(2, node2));
        assertNull(graph.findNode(1, node3));
        assertNull(graph.findNode(2, node1));
    }

    @Test
    public void testAddEdge() {
        Node node1 = new Node("name1");
        Node node2 = new Node("name2");
        Edge edge = new Edge(node1, node2);

        BipartiteGraph graph = new BipartiteGraph();
        graph.addNode(node1, 1);
        graph.addNode(node2, 2);

        assertDoesNotThrow(() -> graph.addEdge(edge));
        assertEquals(1, graph.getEdges().size());
        assertTrue(graph.getEdges().contains(edge));
    }

    @Test
    public void testAddEdgeFailsIfSameGroup() {
        Node node1 = new Node("name1");
        Node node2 = new Node("name2");
        Edge edge = new Edge(node1, node2);

        BipartiteGraph graph = new BipartiteGraph();
        graph.addNode(node1, 1);
        graph.addNode(node2, 1); // Same group

        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(edge));
        assertEquals(0, graph.getEdges().size());
    }

    @Test
    public void testRemoveEdge() {
        Node node1 = new Node("name1");
        Node node2 = new Node("name2");
        Edge edge = new Edge(node1, node2);

        BipartiteGraph graph = new BipartiteGraph();
        graph.addNode(node1, 1);
        graph.addNode(node2, 2);
        graph.addEdge(edge);

        graph.removeEdge(edge);

        assertEquals(0, graph.getEdges().size());
    }

    @Test
    public void testGetGroup() {
        Node node1 = new Node("name1");
        Node node2 = new Node("name2");
        Node node3 = new Node("name3");

        BipartiteGraph graph = new BipartiteGraph();
        graph.addNode(node1, 1);
        graph.addNode(node2, 1);
        graph.addNode(node3, 2);

        assertEquals(1, graph.getGroup(node1));
        assertEquals(1, graph.getGroup(node2));
        assertEquals(2, graph.getGroup(node3));
        assertNull(graph.getGroup(new Node("name4")));
    }

    @Test
    public void testBuildFromNeighborhood() {
        Node node1 = new Node("name1");
        Node node2 = new Node("name2");
        Node node3 = new Node("name3");
        Node node4 = new Node("name4");
        Node node5 = new Node("name5");
        HashMap<Node, HashMap<Node, Double>> neighborhoods = new HashMap<>();
        neighborhoods.put(node1, new HashMap<>());
        neighborhoods.get(node1).put(node2, 1.6);
        neighborhoods.get(node1).put(node3, 1.11);
        neighborhoods.get(node1).put(node4, 0.5);
        neighborhoods.put(node5, new HashMap<>());
        neighborhoods.get(node5).put(node2, 3.0);
        neighborhoods.get(node5).put(node3, 2.0);

        BipartiteGraph graph = new BipartiteGraph();
        graph.addNode(node1, 1);
        graph.addNode(node5, 1);
        graph.addNode(node2, 2);
        graph.addNode(node3, 2);
        graph.addNode(node4, 2);
        graph.buildEdgesFromNeighborhoods(neighborhoods);

        assertEquals(2, graph.getNodeGroup1().size());
        assertEquals(3, graph.getNodeGroup2().size());
        assertEquals(5, graph.getEdges().size());
        assertTrue(graph.getEdges().contains(new Edge(node1, node2, 1.6)));
        assertTrue(graph.getEdges().contains(new Edge(node1, node3, 1.11)));
        assertTrue(graph.getEdges().contains(new Edge(node1, node4, 0.5)));
        assertTrue(graph.getEdges().contains(new Edge(node5, node2, 3.0)));
        assertTrue(graph.getEdges().contains(new Edge(node5, node3, 2.0)));
    }

    @Test
    public void testBuildFromTrees() throws IOException {
        Path path1 = Paths.get("name1");
        Path path2 = Paths.get("name2");
        Path path3 = Paths.get("name3");
        Path path4 = Paths.get("name4");
        Path path5 = Paths.get("name5");

        Files.createFile(path1);
        Files.createFile(path2);
        Files.createFile(path3);
        Files.createFile(path4);
        Files.createFile(path5);
        FileTree tree1 = new FileTree(path1);
        FileTree tree2 = new FileTree(path1);
        FileTree tree3 = new FileTree(path3);
        FileTree tree4 = new FileTree(path4);
        FileTree tree5 = new FileTree(path5);
        tree1.addChild(tree3);
        tree1.addChild(tree4);
        tree2.addChild(tree5);

        BipartiteGraph graph = BipartiteGraph.buildFromTrees(tree1, tree2);

        assertEquals(3, graph.getNodeGroup1().size());
        assertEquals(2, graph.getNodeGroup2().size());
        assertEquals(graph.getNodeGroup1().get(1).getPath(), path3);
        assertEquals(graph.getNodeGroup1().get(1).getParent(), graph.getNodeGroup1().get(0));

        Files.delete(path1);
        Files.delete(path2);
        Files.delete(path3);
        Files.delete(path4);
        Files.delete(path5);
    }
}
