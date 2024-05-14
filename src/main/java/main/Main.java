package main;

import algo.AlgoRunner;
import algo.AlgorithmResults;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Wrong number of arguments");
            return;
        }
        AlgorithmResults results = AlgoRunner.run(args[0]);
        System.out.println("Results: " + results);
    }
}
