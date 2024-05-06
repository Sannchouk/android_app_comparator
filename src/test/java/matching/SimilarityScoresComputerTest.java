package matching;

import bipartiteGraph.Node;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SimilarityScoresComputerTest {

    final Node node1 = new Node("test1", Arrays.asList("apple", "banana"));
    final Node node2 = new Node("test2", Arrays.asList("banana", "orange"));
    final Node node3 = new Node("test3", Collections.singletonList("kiwi"));
    final Node node4 = new Node("test4", Arrays.asList("apple", "kiwi"));


    @Test
    public void testComputeSimilarityScores() {
        Indexer indexer = createMockIndexer();
        SimilarityScoresComputer similarityScoresComputer = new SimilarityScoresComputer(indexer);
        Map<Node, HashMap<Node, Double>> similarityScores = similarityScoresComputer.computeSimilarityScores();

        assertEquals(2, similarityScores.size());
        assertTrue(similarityScores.containsKey(node1));
        assertTrue(similarityScores.containsKey(node2));
        System.out.println(similarityScores);
        assertEquals(2, similarityScores.get(node1).size());
        assertEquals(2, similarityScores.get(node2).size());
        assertEquals(0.0, similarityScores.get(node1).get(node3));
        assertEquals(0.0, similarityScores.get(node2).get(node4));
        assertEquals(indexer.computeIdf("apple"), similarityScores.get(node1).get(node4));
    }

    @Test
    public void testPropagationScores(){
        Indexer indexer = createMockIndexer();
        Node node5 = new Node("test5", Arrays.asList("apple", "banana"));
        Node node6 = new Node("test6", Arrays.asList("banana", "orange"));
        node5.setParent(node1);
        node6.setParent(node3);
        indexer.addNode(node5, 1);
        indexer.addNode(node6, 2);
        SimilarityScoresComputer similarityScoresComputer = new SimilarityScoresComputer(indexer);
        Map<Node, HashMap<Node, Double>> similarityScores = similarityScoresComputer.computeSimilarityScores();

        assert(similarityScores.get(node5).get(node6) > indexer.computeIdf("banana"));
        assert(similarityScores.get(node1).get(node3) > 0);
    }

    @Test
    public void testPenalization() {
        Indexer indexer = createMockIndexer();
        Node node5 = new Node("test5", Arrays.asList("apple", "banana"));
        Node node6 = new Node("test6", Arrays.asList("banana", "orange"));
        Node node7 = new Node("test7", Arrays.asList("banana", "orange"));
        SimilarityScoresComputer similarityScoresComputer = new SimilarityScoresComputer(indexer);
        Map<Node, HashMap<Node, Double>> similarityScoresBeforeNewOnes = similarityScoresComputer.computeSimilarityScores();
        node5.setParent(node1);
        node6.setParent(node4);
        node7.setParent(node4);
        indexer.addNode(node5, 1);
        indexer.addNode(node6, 2);
        indexer.addNode(node7, 2);
        similarityScoresComputer = new SimilarityScoresComputer(indexer);
        Map<Node, HashMap<Node, Double>> similarityScoresAfterNewOnes = similarityScoresComputer.computeSimilarityScores();

        assert(similarityScoresBeforeNewOnes.get(node1).get(node4) < similarityScoresAfterNewOnes.get(node1).get(node4));
    }

    private Indexer createMockIndexer() {
        Indexer indexer = new Indexer();
        indexer.addNode(node1, 1);
        indexer.addNode(node2, 1);
        indexer.addNode(node3, 2);
        indexer.addNode(node4, 2);
        return indexer;
    }
}
