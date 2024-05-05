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
        assertEquals(1, similarityScores.get(node1).size());
        assertEquals(0, similarityScores.get(node2).size());
        assertEquals(indexer.computeIdf("apple"), similarityScores.get(node1).get(node4));
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
