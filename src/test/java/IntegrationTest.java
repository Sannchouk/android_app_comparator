import bipartiteGraph.BipartiteGraph;
import bipartiteGraph.Edge;
import bipartiteGraph.Node;
import fileTree.FileTree;
import matching.Indexer;
import matching.MetropolisAlgorithm;
import matching.SimilarityScoresComputer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class IntegrationTest {

    @Test
    void testApp() throws IOException {
        Path apk1 = Paths.get("resources/lichess-apk/assets");
        Path apk2 = Paths.get("resources/chesscom-apk/assets");

        FileTree tree1 = FileTree.buildTree(apk1);
        FileTree tree2 = FileTree.buildTree(apk2);

        List<String> nodes1 = tree1.getAllNodesData();
        List<String> nodes2 = tree2.getAllNodesData();

        List<Node> graph_nodes_1 = new ArrayList<>();
        List<Node> graph_nodes_2 = new ArrayList<>();

        for (String node : nodes1) {
            Node node1 = new Node(node);
            node1.tokenize();
            graph_nodes_1.add(node1);

        }
        for (String node : nodes2) {
            Node node2 = new Node(node);
            node2.tokenize();
            graph_nodes_2.add(node2);
        }

        Indexer indexer = new Indexer();
        indexer.addNodes(graph_nodes_1, 1);
        indexer.addNodes(graph_nodes_2, 2);
        var similarityScoresComputer = new SimilarityScoresComputer(indexer);
        var similarityScores = similarityScoresComputer.computeSimilarityScores();
        var graph = new BipartiteGraph();
        graph.addNodes(graph_nodes_1, 1);
        graph.addNodes(graph_nodes_2, 2);
        graph.buildEdgesFromNeighborhoods(similarityScores);

        MetropolisAlgorithm metropolisAlgorithm = new MetropolisAlgorithm(
                graph,
                2.5,
                0.8,
                10
        );

        metropolisAlgorithm.run();

        List<Edge> matching = metropolisAlgorithm.getMatching();
        System.out.println("Matching: " + matching);
        System.out.println("Matching length: " + matching.size());
        System.out.println("Group 1 length: " + nodes1.size());
        System.out.println("Group 2 length: " + nodes2.size());
    }
}
