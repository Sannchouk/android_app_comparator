package main;

import algo.AlgoRunner;
import algo.AlgorithmResults;
import neo4j.writer.Writer;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Wrong number of arguments");
            return;
        }
        AlgorithmResults results = AlgoRunner.run(args[0]);
        Writer writer = new Writer();
        writer.write(new File("results"), results);
        System.out.println("Results: " + results);
    }
}
