package algo;

import bipartiteGraph.BipartiteGraph;
import bipartiteGraph.Edge;
import bipartiteGraph.Node;
import neo4j.Apk;
import neo4j.DistancesStorer;
import fileTree.FileTree;
import inMemory.Indexer;
import matching.computers.DistanceComputer;
import matching.algorithm.MetropolisAlgorithm;
import matching.computers.SimilarityScoresComputer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AlgoRunner {

    public static AlgorithmResults run(String path) {
        List<Apk> apks = new ArrayList<>();
        try {
            apks = listApks(path);
            System.out.println("Directories:" + apks);
        } catch (IOException e) {
            System.out.println("Wrong path: " + path);
        }
        DistancesStorer distancesStorer = new DistancesStorer();
        for (Apk apk1: apks) {
            for (Apk apk2 : apks) {
                try {
                    if (distancesStorer.hasDistanceBeenAlreadyComputed(apk1, apk2) || apk1.equals(apk2)) {
                        continue;
                    }
                    float distance = compareTwoApks(apk1, apk2);
                    distancesStorer.addDistance(apk1, apk2, distance);
                } catch (IOException e) {
                    System.out.println("Error while comparing");
                }
            }
        }
        return new AlgorithmResults(apks, distancesStorer.getDistances());
    }

        private static List<Apk> listApks(String path) throws IOException {
            try (Stream<Path> paths = Files.list(Paths.get(path))) {
                return paths.filter(Files::isDirectory)
                        .map(Apk::new)
                        .collect(Collectors.toList());
            }
        }

        static float compareTwoApks(Apk apk1, Apk apk2) throws IOException {
            FileTree tree1 = FileTree.buildTree(apk1.getPath());
            FileTree tree2 = FileTree.buildTree(apk2.getPath());
            apk1.setNumberOfFiles(tree1.getNodes().size());
            apk1.setTotalSize(tree1.getTotalSize());
            apk2.setNumberOfFiles(tree2.getNodes().size());
            apk2.setTotalSize(tree2.getTotalSize());
            BipartiteGraph graph = BipartiteGraph.buildFromTrees(tree1, tree2);
            List<Node> graphNodes1 = graph.getNodeGroup1();
            List<Node> graphNodeGroup2 = graph.getNodeGroup2();
            Indexer indexer = new Indexer();
            indexer.addNodes(graphNodes1, 1);
            indexer.addNodes(graphNodeGroup2, 2);
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
            DistanceComputer distanceComputer = new DistanceComputer();
            return distanceComputer.computeDistance(graphNodes1.size(), graphNodeGroup2.size(), matching.size());
        }
}
