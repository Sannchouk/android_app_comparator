package main;

import algo.AlgorithmResults;
import algo.DistanceMatrixWriter;
import clustering.Clustering;
import clustering.ClusteringIO;
import neo4j.data.Apk;
import neo4j.writer.Neo4JWriter;

import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        if (!DirectoryManager.ensureResultsDirectory()) {
            return;
        }

        ArgumentParser argParser = new ArgumentParser();
        argParser.parseArguments(args);

        ResultsLoader resultsLoader = new ResultsLoader();
        AlgorithmResults results = resultsLoader.loadResults(argParser.already, argParser.path);

        if (results == null) {
            return;
        }

        new DistanceMatrixWriter().writeDistanceMatrixInCSV("results/distanceMatrix.csv", results.getDistancesMatrix(), results.getApks());

        if (argParser.needNeo4jFile) {
            Neo4JWriter writer = new Neo4JWriter();
            writer.write(new File("results/neo4j.txt"), results);
        }

        if (argParser.cluster) {
            try {
                List<String> apkNames = results.getApks().stream().map(Apk::getNeo4JName).toList();
                var clusters = new Clustering().cluster(results.getDistancesMatrix(), apkNames, 0.9);
                System.out.println("Clusters:");
                System.out.println(clusters);
                new ClusteringIO().writeClustersToFile(clusters, "results/clusters.csv");
            } catch (Exception e) {
                System.out.println("Error while clustering: " + e.getMessage());
            }
        }
    }
}
