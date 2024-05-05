package matching;

import bipartiteGraph.Node;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IndexerTest {

        @Test
        public void testAddNode() {
            Indexer indexer = new Indexer();
            Node node1 = new Node("test", Arrays.asList("apple", "banana"));
            Node node2 = new Node("test", Arrays.asList("banana", "orange"));

            indexer.addNode(node1, 1);
            indexer.addNode(node2, 2);

            assertEquals(1, indexer.getGroup1().size());
            assertEquals(1, indexer.getGroup2().size());
            assertEquals(3, indexer.getTokenMap().size());
        }

        @Test
        public void testComputeIdf() {
            Indexer indexer = new Indexer();
            Node node1 = new Node("test", Arrays.asList("apple", "banana"));
            Node node2 = new Node("test", Arrays.asList("banana", "orange"));

            indexer.addNode(node1, 1);
            indexer.addNode(node2, 2);

            assertEquals(0.0, indexer.computeIdf("kiwi"));
            assertEquals(0.6931471805599453, indexer.computeIdf("apple"));
            assertEquals(0.0, indexer.computeIdf("banana"));
            assertEquals(0.6931471805599453, indexer.computeIdf("orange"));
        }
}
