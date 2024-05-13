package integration;

import bipartiteGraph.BipartiteGraph;
import bipartiteGraph.Edge;
import bipartiteGraph.Node;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigList;
import fileTree.FileTree;
import matching.Indexer;
import matching.MetropolisAlgorithm;
import matching.SimilarityScoresComputer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utils.AppConfigModifier;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    static final AppConfigModifier appConfigModifier = new AppConfigModifier();

    @AfterAll
    static void tearDown() throws IOException {
        appConfigModifier.restoreConfig();
    }


    @ParameterizedTest
    @MethodSource({"tokenProperties"})
    void testApp(Map<String, Boolean> tokenProperties) throws IOException {
        //GIVEN
        Path apk1 = Paths.get("resources/lichess-apk");
        Path apk2 = Paths.get("resources/chesscom-apk");
        FileTree tree1 = FileTree.buildTree(apk1);
        FileTree tree2 = FileTree.buildTree(apk2);
        BipartiteGraph graph;
        ConfigFactory.invalidateCaches();
        appConfigModifier.modifyConfigForTesting(tokenProperties);
        graph = BipartiteGraph.buildFromTrees(tree1, tree2);
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

        //WHEN
        metropolisAlgorithm.run();
        List<Edge> matching = metropolisAlgorithm.getMatching();

        //THEN
        assertTrue(graph_nodes_1.stream().anyMatch(node -> node.getTokens().size() == 1 + tokenProperties.values().stream().filter(b -> b).count()));
        assertTrue(matching.size() < graph_nodes_1.size());
        assertFalse(matching.isEmpty());
    }

    @ParameterizedTest
    @MethodSource({"tokenProperties"})
    void testAppWithTwoIdenticalApks(Map<String, Boolean> tokenProperties) throws IOException {
        //GIVEN
        Path apk1 = Paths.get("resources/lichess-apk");
        FileTree tree1 = FileTree.buildTree(apk1);
        FileTree tree2 = FileTree.buildTree(apk1);
        ConfigFactory.invalidateCaches();
        appConfigModifier.modifyConfigForTesting(tokenProperties);
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

        //WHEN
        metropolisAlgorithm.run();
        List<Edge> matching = metropolisAlgorithm.getMatching();

        //THEN
        assertTrue(graph_nodes_1.stream().anyMatch(node -> node.getTokens().size() == 1 + tokenProperties.values().stream().filter(b -> b).count()));
        assertEquals(graph_nodes_1.size(), matching.size());
    }

    @ParameterizedTest
    @MethodSource({"tokenProperties"})
    void testAppWithTwoIdenticalApksWithDifferentVersions(Map<String, Boolean> tokenProperties) throws IOException {
        //GIVEN
        Path apk1 = Paths.get("resources/lichess-apk");
        Path apk2 = Paths.get("resources/lichess-2021-apk");
        FileTree tree1 = FileTree.buildTree(apk1);
        FileTree tree2 = FileTree.buildTree(apk2);
        BipartiteGraph graph;
        ConfigFactory.invalidateCaches();
        appConfigModifier.modifyConfigForTesting(tokenProperties);
        graph = BipartiteGraph.buildFromTrees(tree1, tree2);
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

        //WHEN
        metropolisAlgorithm.run();
        List<Edge> matching = metropolisAlgorithm.getMatching();

        //THEN
        assertTrue(graph_nodes_1.stream().anyMatch(node -> node.getTokens().size() == 1 + tokenProperties.values().stream().filter(b -> b).count()));
        assertTrue(matching.size() > graph_nodes_1.size() / 2);
        assertTrue(matching.size() < graph_nodes_1.size());
    }

    private static Stream<Map<String, Boolean>> tokenProperties() {
        Map<String, Boolean> noOtherTokens = Map.of("fileSize", false, "fileHash", false);
        Map<String, Boolean> allTokens = Map.of("fileSize", true, "fileHash", true);
        return Stream.of(noOtherTokens, allTokens);
    }
}
