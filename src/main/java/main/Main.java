package main;

import algo.AlgoRunner;
import algo.AlgorithmResults;
import neo4j.writer.Writer;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        String path = null;
        if (args.length < 1) {
            path = "src/main/resources/apks";
        }
        else {
            path = args[0];
        }
        AlgorithmResults results = AlgoRunner.run(path);
        Writer writer = new Writer();
        writer.write(new File("results"), results);
        System.out.println("Results: " + results);
    }
}
