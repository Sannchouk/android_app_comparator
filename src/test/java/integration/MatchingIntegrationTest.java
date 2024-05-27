package integration;

import bipartiteGraph.BipartiteGraph;
import bipartiteGraph.Edge;
import bipartiteGraph.Node;
import com.typesafe.config.ConfigFactory;
import fileTree.FileTree;
import inMemory.Indexer;
import matching.algorithm.MetropolisAlgorithm;
import matching.computers.similarities.SimilarityScoresComputer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utils.AppConfigModifier;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class MatchingIntegrationTest {

    static final AppConfigModifier appConfigModifier = new AppConfigModifier();

    Path resources = Paths.get(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("apks")).toURI());

    public MatchingIntegrationTest() throws URISyntaxException {
    }

    @AfterAll
    static void tearDown() throws IOException {
        appConfigModifier.restoreConfig();
    }


    @ParameterizedTest
    @MethodSource({"tokenProperties"})
    void testApp(Map<String, Boolean> tokenProperties) throws IOException {
        //GIVEN
        Path apk1 = resources.resolve("lichess-apk");
        Path apk2 = resources.resolve("chesscom-apk");
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
        assertTrue(matching.size() < graph_nodes_1.size());
        assertFalse(matching.isEmpty());
    }

    @ParameterizedTest
    @MethodSource({"tokenProperties"})
    void testAppWithTwoIdenticalApks(Map<String, Boolean> tokenProperties) throws IOException {
        //GIVEN
        Path apk1 = resources.resolve("lichess-apk");
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
        assertTrue(matching.size() > graph_nodes_1.size() * 0.99);
        assertTrue(matching.size() <= graph_nodes_1.size());
    }

    @ParameterizedTest
    @MethodSource({"tokenProperties"})
    void testAppWithTwoIdenticalApksWithDifferentVersions(Map<String, Boolean> tokenProperties) throws IOException {
        //GIVEN
        Path apk1 = resources.resolve("lichess-apk");
        Path apk2 = resources.resolve("lichess-2021-apk");
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
        assertTrue(matching.size() > graph_nodes_1.size() / 2);
        assertTrue(matching.size() < graph_nodes_1.size());
    }

    private static Stream<Map<String, Boolean>> tokenProperties() {
//        Map<String, Boolean> noOtherTokens = Map.of("fileSize", false, "fileHash", false);
        Map<String, Boolean> allTokens = Map.of("fileSize", true, "fileHash", true);
        return Stream.of(allTokens);
    }
}
