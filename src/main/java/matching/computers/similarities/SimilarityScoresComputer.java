package matching.computers.similarities;

import bipartiteGraph.BipartiteGraph;
import bipartiteGraph.Node;
import edu.gatech.gtri.bktree.BkTreeSearcher;
import edu.gatech.gtri.bktree.Metric;
import edu.gatech.gtri.bktree.MutableBkTree;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimilarityScoresComputer {
    private final BipartiteGraph graph;
    private final DistanceComputer distanceComputer;
    private final PropagationComputer propagationComputer;
    private final PenalizationComputer penalizationComputer;

    private static final int EXTENSION_DISTANCE_THRESHOLD = 0;
    private static final int NAME_DISTANCE_THRESHOLD = 2;
    private static final int HASH_DISTANCE_THRESHOLD = 6;

    private final Map<Node, HashMap<Node, Double>> similarityScores = new HashMap<>();

    public SimilarityScoresComputer(BipartiteGraph graph) {
        this.graph = graph;
        this.distanceComputer = new DistanceComputer();
        this.propagationComputer = new PropagationComputer();
        this.penalizationComputer = new PenalizationComputer();
    }

    public Map<Node, HashMap<Node, Double>> computeSimilarityScores() {
        Metric<Node> distanceMetric = distanceComputer::computeDistance;

        MutableBkTree<Node> bkTree = new MutableBkTree<>(distanceMetric);
        for (Node node : graph.getNodeGroup2()) {
            bkTree.add(node);
        }
        BkTreeSearcher<Node> searcher = new BkTreeSearcher<>(bkTree);

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (Node node : graph.getNodeGroup1()) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                HashMap<Node, Double> scores;
                synchronized (searcher) {
                    scores = computeSimilarityScoresForNode(node, searcher);
                }
                synchronized (similarityScores) {
                    similarityScores.put(node, scores);
                }
            }, executor);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executor.shutdown();

        computePropagationScores();
        applyPenalization();
        return similarityScores;
    }

    private void computePropagationScores() {
        for (Node node : graph.getNodeGroup1()) {
            for (Node neighbor : graph.getNodeGroup2()) {
                if (similarityScores.get(node).get(neighbor) != 0.0) {
                    propagationComputer.computePropagationScores(similarityScores, node, neighbor);
                }
            }
        }
    }

    private void applyPenalization() {
        for (Node node : graph.getNodeGroup1()) {
            for (Node neighbor : graph.getNodeGroup2()) {
                if (similarityScores.get(node).get(neighbor) != 0.0) {
                    penalizationComputer.applyPenalization(similarityScores, node, neighbor);
                }
            }
        }
    }

    private HashMap<Node, Double> computeSimilarityScoresForNode(Node node, BkTreeSearcher<Node> searcher) {
        HashMap<Node, Double> neighbors = new HashMap<>();
        for(Node nodes : graph.getNodeGroup2()) {
            neighbors.put(nodes, 0.0);
        }
        Set<BkTreeSearcher.Match<? extends Node>> matches = searcher.search(node, distanceComputer.computeDistanceForAttributes(EXTENSION_DISTANCE_THRESHOLD, NAME_DISTANCE_THRESHOLD, HASH_DISTANCE_THRESHOLD));
        for (BkTreeSearcher.Match<? extends Node> match : matches) {
            double similarity = match.getDistance() != 0 ? (double) 1 / match.getDistance() : 1.0;
            neighbors.put(match.getMatch(), similarity);
        }
        return neighbors;
    }
}
