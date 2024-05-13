package main;

import bipartiteGraph.BipartiteGraph;
import bipartiteGraph.Edge;
import bipartiteGraph.Node;
import csv.Apk;
import csv.DistancesStorer;
import csv.Neo4JCsvWriter;
import fileTree.FileTree;
import matching.DistanceComputer;
import inMemory.Indexer;
import matching.MetropolisAlgorithm;
import matching.SimilarityScoresComputer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Wrong number of arguments");
            return;
        }
        String path = args[0];
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
                    if (distancesStorer.hasDistanceBeenAlreadyComputed(apk1.getName(), apk2.getName())) {
                        continue;
                    }
                    float distance = compareTwoApks(apk1.getPath(), apk2.getPath());
                    distancesStorer.addDistance(apk1.getName(), apk2.getName(), distance);
                } catch (IOException e) {
                    System.out.println("Error while comparing");
                }
            }
        }
        new Neo4JCsvWriter().writeCsv(Paths.get("csv/distances.csv"), distancesStorer.getDistances());
    }

    private static List<Apk> listApks(String path) throws IOException {
        try (Stream<Path> paths = Files.list(Paths.get(path))) {
            return paths.filter(Files::isDirectory)
                    .map(Apk::new)
                    .collect(Collectors.toList());
        }
    }

    private static float compareTwoApks(Path apk1, Path apk2) throws IOException {
        FileTree tree1 = FileTree.buildTree(apk1);
        FileTree tree2 = FileTree.buildTree(apk2);
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
