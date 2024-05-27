package main;

import algo.AlgoRunner;
import algo.AlgorithmResults;

import java.io.*;

class ResultsLoader {
    public AlgorithmResults loadResults(boolean already, String path) {
        AlgorithmResults results;

        if (already) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("results/algorithmResults.ser"))) {
                results = AlgorithmResults.deserialize("results/algorithmResults.ser");
                System.out.println("Loaded serialized AlgorithmResults");
                return results;
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error reading serialized AlgorithmResults: " + e.getMessage());
                return null;
            }
        } else {
            results = AlgoRunner.run(path);

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("results/algorithmResults.ser"))) {
                results.serialize("results/algorithmResults.ser");
                System.out.println("Serialized AlgorithmResults");
                return results;
            } catch (IOException e) {
                System.out.println("Error serializing AlgorithmResults: " + e.getMessage());
                return null;
            }
        }
    }
}
