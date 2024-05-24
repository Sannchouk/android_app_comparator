package main;

import algo.AlgoRunner;
import algo.AlgorithmResults;
import algo.DistanceMatrixWriter;
import clustering.Clustering;
import neo4j.data.Apk;
import neo4j.writer.Neo4JWriter;

import java.io.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String path = "apks/";
        boolean needNeo4jFile = false;
        boolean cluster = false;
        boolean already = false;

        // Check if the first argument could be a new path
        if (args.length > 0 && !args[0].startsWith("-")) {
            path = args[0];
        }

        // Start processing flags from the second argument if a new path was provided
        int startIndex = path.equals("apks/") ? 0 : 1;

        for (int i = startIndex; i < args.length; i++) {
            switch (args[i]) {
                case "-neo4j":
                    needNeo4jFile = true;
                    break;
                case "-cluster":
                    cluster = true;
                    break;
                case "-already":
                    already = true;
                    break;
                default:
                    System.out.println("Unknown command: " + args[i]);
                    break;
            }
        }

        AlgorithmResults results;

        if (already) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("results/algorithmResults.ser"))) {
                results = AlgorithmResults.deserialize("results/algorithmResults.ser");
                System.out.println("Loaded serialized AlgorithmResults");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error reading serialized AlgorithmResults: " + e.getMessage());
                return;
            }
        } else {
            results = AlgoRunner.run(path);

            // Serialize the results
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("results/algorithmResults.ser"))) {
                results.serialize("results/algorithmResults.ser");
                System.out.println("Serialized AlgorithmResults");
            } catch (IOException e) {
                System.out.println("Error serializing AlgorithmResults: " + e.getMessage());
                return;
            }
        }

        new DistanceMatrixWriter().writeDistanceMatrixInCSV("results/distanceMatrix.csv", results.getDistancesMatrix(), results.getApks());

        if (needNeo4jFile) {
            Neo4JWriter writer = new Neo4JWriter();
            writer.write(new File("results/neo4j.txt"), results);
        }
        if (cluster) {
            try {
                List<String> apkNames = results.getApks().stream().map(Apk::getNeo4JName).toList();
                var clusters = new Clustering().cluster(results.getDistancesMatrix(), apkNames, 0.8);
                System.out.println("Clusters:");
                System.out.println(clusters);
            } catch (Exception e) {
                System.out.println("Error while clustering: " + e.getMessage());
            }
        }
    }
}
