import bipartiteGraph.BipartiteGraph;
import bipartiteGraph.Edge;
import bipartiteGraph.Node;
import fileTree.FileTree;
import matching.Indexer;
import matching.MetropolisAlgorithm;
import matching.SimilarityScoresComputer;
import org.junit.jupiter.api.Test;

import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    @Test
    void testApp() throws IOException {
        Path apk1 = Paths.get("resources/lichess-apk");
        Path apk2 = Paths.get("resources/chesscom-apk");

        FileTree tree1 = FileTree.buildTree(apk1);
        FileTree tree2 = FileTree.buildTree(apk2);

        BipartiteGraph graph = BipartiteGraph.buildFromTrees(tree1, tree2);
        List<Node> graph_nodes_1 = graph.getNodeGroup1();
        List<Node> graph_nodes_2 = graph.getNodeGroup2();

        Indexer indexer = new Indexer();
        indexer.addNodes(graph_nodes_1, 1);
        indexer.addNodes(graph_nodes_2, 2);
        var similarityScoresComputer = new SimilarityScoresComputer(indexer);
        var similarityScores = similarityScoresComputer.computeSimilarityScores();
        graph.buildEdgesFromNeighborhoods(similarityScores);

        MetropolisAlgorithm metropolisAlgorithm = new MetropolisAlgorithm(
                graph,
                2.5,
                0.8,
                10
        );

        metropolisAlgorithm.run();

        List<Edge> matching = metropolisAlgorithm.getMatching();
        assertTrue(matching.size() < graph_nodes_1.size());
        assertFalse(matching.isEmpty());
    }

    @Test
    void testAppWithTwoIdenticalApks() throws IOException {
        Path apk1 = Paths.get("resources/lichess-apk");
        Path apk2 = Paths.get("resources/lichess-apk");

        FileTree tree1 = FileTree.buildTree(apk1);
        FileTree tree2 = FileTree.buildTree(apk2);

        BipartiteGraph graph = BipartiteGraph.buildFromTrees(tree1, tree2);
        List<Node> graph_nodes_1 = graph.getNodeGroup1();
        List<Node> graph_nodes_2 = graph.getNodeGroup2();

        Indexer indexer = new Indexer();
        indexer.addNodes(graph_nodes_1, 1);
        indexer.addNodes(graph_nodes_2, 2);
        var similarityScoresComputer = new SimilarityScoresComputer(indexer);
        var similarityScores = similarityScoresComputer.computeSimilarityScores();
        graph.buildEdgesFromNeighborhoods(similarityScores);
        assertFalse(graph.getEdges().isEmpty());

        MetropolisAlgorithm metropolisAlgorithm = new MetropolisAlgorithm(
                graph,
                2.5,
                0.8,
                10
        );

        metropolisAlgorithm.run();

        List<Edge> matching = metropolisAlgorithm.getMatching();
        assertEquals(graph_nodes_1.size(), matching.size());
    }

    @Test
    void testAppWithTwoIdenticalApksWithDifferentVersions() throws IOException {
        Path apk1 = Paths.get("resources/lichess-apk");
        Path apk2 = Paths.get("resources/lichess-2021-apk");

        FileTree tree1 = FileTree.buildTree(apk1);
        FileTree tree2 = FileTree.buildTree(apk2);

        BipartiteGraph graph = BipartiteGraph.buildFromTrees(tree1, tree2);
        List<Node> graph_nodes_1 = graph.getNodeGroup1();
        List<Node> graph_nodes_2 = graph.getNodeGroup2();

        Indexer indexer = new Indexer();
        indexer.addNodes(graph_nodes_1, 1);
        indexer.addNodes(graph_nodes_2, 2);
        var similarityScoresComputer = new SimilarityScoresComputer(indexer);
        var similarityScores = similarityScoresComputer.computeSimilarityScores();
        graph.buildEdgesFromNeighborhoods(similarityScores);
        assertFalse(graph.getEdges().isEmpty());

        MetropolisAlgorithm metropolisAlgorithm = new MetropolisAlgorithm(
                graph,
                2.5,
                0.8,
                10
        );

        metropolisAlgorithm.run();

        List<Edge> matching = metropolisAlgorithm.getMatching();
        assertTrue(matching.size() > graph_nodes_1.size() / 2);
        assertTrue(matching.size() < graph_nodes_1.size());
    }
}
