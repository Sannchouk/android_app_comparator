package integration;

import bipartiteGraph.BipartiteGraph;
import bipartiteGraph.Edge;
import bipartiteGraph.Node;
import com.typesafe.config.ConfigFactory;
import fileTree.FileTree;
import matching.algorithm.MetropolisAlgorithm;
import matching.computers.similarities.SimilarityScoresComputer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatchingIntegrationTest {

    Path resources = Paths.get(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("apks")).toURI());

    public MatchingIntegrationTest() throws URISyntaxException {
    }


    @Test
    void testApp() throws IOException {
        //GIVEN
        Path apk1 = resources.resolve("lichess-apk");
        Path apk2 = resources.resolve("chesscom-apk");
        FileTree tree1 = FileTree.buildTree(apk1);
        FileTree tree2 = FileTree.buildTree(apk2);
        BipartiteGraph graph;
        ConfigFactory.invalidateCaches();
        graph = BipartiteGraph.buildFromTrees(tree1, tree2);
        List<Node> graph_nodes_1 = graph.getNodeGroup1();
        var similarityScoresComputer = new SimilarityScoresComputer(graph);
        var similarityScores = similarityScoresComputer.computeSimilarityScores();
        graph.buildEdgesFromNeighborhoods(similarityScores);
        MetropolisAlgorithm metropolisAlgorithm = new MetropolisAlgorithm(
                graph,
                2.5,
                0.8,
                10
        );

        //WHEN
        metropolisAlgorithm.run();
        List<Edge> matching = metropolisAlgorithm.getMatching();

        //THEN
        assertTrue(matching.size() < graph_nodes_1.size());
        assertFalse(matching.isEmpty());
    }

    @Test
    void testAppWithTwoIdenticalApks() throws IOException {
        //GIVEN
        Path apk1 = resources.resolve("lichess-apk");
        FileTree tree1 = FileTree.buildTree(apk1);
        FileTree tree2 = FileTree.buildTree(apk1);
        ConfigFactory.invalidateCaches();
        BipartiteGraph graph = BipartiteGraph.buildFromTrees(tree1, tree2);
        List<Node> graph_nodes_1 = graph.getNodeGroup1();
        var similarityScoresComputer = new SimilarityScoresComputer(graph);
        var similarityScores = similarityScoresComputer.computeSimilarityScores();
        graph.buildEdgesFromNeighborhoods(similarityScores);
        MetropolisAlgorithm metropolisAlgorithm = new MetropolisAlgorithm(
                graph,
                2.5,
                0.8,
                10
        );
        metropolisAlgorithm.getSuggester().getRand().setSeed(42);

        //WHEN
        metropolisAlgorithm.run();
        List<Edge> matching = metropolisAlgorithm.getMatching();

        //THEN
        assertTrue(matching.size() <= graph_nodes_1.size());
        assertTrue(matching.size() > graph_nodes_1.size() * 0.99, "matching size: " + matching.size());
    }

    @Test
    void testAppWithTwoIdenticalApksWithDifferentVersions() throws IOException {
        //GIVEN
        Path apk1 = resources.resolve("lichess-apk");
        Path apk2 = resources.resolve("lichess-2021-apk");
        FileTree tree1 = FileTree.buildTree(apk1);
        FileTree tree2 = FileTree.buildTree(apk2);
        BipartiteGraph graph;
        ConfigFactory.invalidateCaches();
        graph = BipartiteGraph.buildFromTrees(tree1, tree2);
        List<Node> graph_nodes_1 = graph.getNodeGroup1();
        var similarityScoresComputer = new SimilarityScoresComputer(graph);
        var similarityScores = similarityScoresComputer.computeSimilarityScores();
        graph.buildEdgesFromNeighborhoods(similarityScores);
        MetropolisAlgorithm metropolisAlgorithm = new MetropolisAlgorithm(
                graph,
                2.5,
                0.8,
                10
        );

        //WHEN
        metropolisAlgorithm.run();
        List<Edge> matching = metropolisAlgorithm.getMatching();

        //THEN
        assertTrue(matching.size() > graph_nodes_1.size() / 2);
        assertTrue(matching.size() < graph_nodes_1.size());
    }
}
