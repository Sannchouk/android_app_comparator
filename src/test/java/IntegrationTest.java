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
import utils.AppConfigModifier;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    static final AppConfigModifier appConfigModifier = new AppConfigModifier();

    @AfterAll
    static void tearDown() throws IOException {
        appConfigModifier.restoreConfig();
    }


    @Test
    void testApp() throws IOException {
        Path apk1 = Paths.get("resources/lichess-apk");
        Path apk2 = Paths.get("resources/chesscom-apk");

        FileTree tree1 = FileTree.buildTree(apk1);
        FileTree tree2 = FileTree.buildTree(apk2);

        BipartiteGraph graph;
        appConfigModifier.modifyConfigForTesting(Map.of("fileSize", false, "fileHash", false));
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

        BipartiteGraph graph;
        appConfigModifier.modifyConfigForTesting(Map.of("fileSize", false, "fileHash", false));
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

        BipartiteGraph graph;
        appConfigModifier.modifyConfigForTesting(Map.of("fileSize", false, "fileHash", false));
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

        metropolisAlgorithm.run();

        List<Edge> matching = metropolisAlgorithm.getMatching();
        assertTrue(matching.size() > graph_nodes_1.size() / 2);
        assertTrue(matching.size() < graph_nodes_1.size());
    }

    @Test
    void testAppWithTwoIdenticalApksAndAllTokens() throws IOException {
        Path apk1 = Paths.get("resources/lichess-apk");

        FileTree tree1 = FileTree.buildTree(apk1);
        FileTree tree2 = FileTree.buildTree(apk1);

        BipartiteGraph graph;
        ConfigFactory.invalidateCaches();
        appConfigModifier.modifyConfigForTesting(Map.of("fileSize", true, "fileHash", true));
        System.out.println(ConfigFactory.load().getBoolean("fileSize"));
        System.setProperty("fileSize", "true");
        System.setProperty("fileHash", "true");
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

        metropolisAlgorithm.run();
        System.out.println(ConfigFactory.load().getBoolean("fileSize"));

        List<Edge> matching = metropolisAlgorithm.getMatching();
        assertTrue(graph_nodes_1.stream().anyMatch(node -> node.getTokens().size() == 3));
        assertEquals(graph_nodes_1.size(), matching.size(), "All nodes should be matched but the following one are not : " +
                " " + graph_nodes_1.stream().filter(node -> matching.stream().noneMatch(edge -> edge.getSource().equals(node))).collect(Collectors.toList()) +
                "\n" + graph_nodes_2.stream().filter(node -> matching.stream().noneMatch(edge -> edge.getTarget().equals(node))).collect(Collectors.toList())
        );
    }


}
