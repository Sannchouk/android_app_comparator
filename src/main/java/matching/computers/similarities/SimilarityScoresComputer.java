package matching.computers.similarities;

import bipartiteGraph.Node;
import edu.gatech.gtri.bktree.BkTreeSearcher;
import edu.gatech.gtri.bktree.Metric;
import edu.gatech.gtri.bktree.MutableBkTree;
import inMemory.Indexer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimilarityScoresComputer {
    private final Indexer indexer;
    private final DistanceComputer distanceComputer;
    private final PropagationComputer propagationComputer;
    private final PenalizationComputer penalizationComputer;

    private static final int EXTENSION_DISTANCE_THRESHOLD = 0;
    private static final int NAME_DISTANCE_THRESHOLD = 3;
    private static final int HASH_DISTANCE_THRESHOLD = 6;

    private final Map<Node, HashMap<Node, Double>> similarityScores = new HashMap<>();

    public SimilarityScoresComputer(Indexer indexer) {
        this.indexer = indexer;
        this.distanceComputer = new DistanceComputer();
        this.propagationComputer = new PropagationComputer();
        this.penalizationComputer = new PenalizationComputer();
    }

    public Map<Node, HashMap<Node, Double>> computeSimilarityScores() {
        Metric<Node> distanceMetric = distanceComputer::computeDistance;

        MutableBkTree<Node> bkTree = new MutableBkTree<>(distanceMetric);
        for (Node node : indexer.getGroup2()) {
            bkTree.add(node);
        }
        BkTreeSearcher<Node> searcher = new BkTreeSearcher<>(bkTree);
        for (Node node : indexer.getGroup1()) {
            similarityScores.put(node, computeSimilarityScoresForNode(node, searcher));
        }
        computePropagationScores();
        applyPenalization();
        return similarityScores;
    }

    private void computePropagationScores() {
        for (Node node : indexer.getGroup1()) {
            for (Node neighbor : indexer.getGroup2()) {
                if (similarityScores.get(node).get(neighbor) != 0.0) {
                    propagationComputer.computePropagationScores(similarityScores, node, neighbor);
                }
            }
        }
    }

    private void applyPenalization() {
        for (Node node : indexer.getGroup1()) {
            for (Node neighbor : indexer.getGroup2()) {
                if (similarityScores.get(node).get(neighbor) != 0.0) {
                    penalizationComputer.applyPenalization(similarityScores, node, neighbor);
                }
            }
        }
    }

    private HashMap<Node, Double> computeSimilarityScoresForNode(Node node, BkTreeSearcher<Node> searcher) {
        HashMap<Node, Double> neighbors = new HashMap<>();
        for(Node nodes : indexer.getGroup2()) {
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
