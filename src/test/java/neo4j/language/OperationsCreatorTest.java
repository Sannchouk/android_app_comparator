package neo4j.language;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperationsTest {

    @Test
    public void testCreateNode() {
        OperationsCreator operations = OperationsCreator.getInstance();
        String result = operations.createNode("Person");
        assertEquals("CREATE (:Person)", result);
    }

    @Test
    public void testMatchNode() {
        OperationsCreator operations = OperationsCreator.getInstance();
        String result = operations.matchNode("n", "Person");
        assertEquals("MATCH (n:Person", result);
    }

    @Test
    public void testCreateRelationship() {
        OperationsCreator operations = OperationsCreator.getInstance();
        String result = operations.createRelationship("Alice", "Bob", RelationshipType.COMPARED_TO, 0.0f);
        assertEquals("CREATE (Alice)-[r1:COMPARED_TO]-(Bob)", result);
    }

    @Test
    public void testCreateWithInstruction() {
        OperationsCreator operations = OperationsCreator.getInstance();
        String result = operations.createWithInstruction();
        assertEquals("WITH 1 AS _", result);
    }

    @Test
    public void testSetEdgeValues() {
        OperationsCreator operations = OperationsCreator.getInstance();
        operations.createRelationship("Alice", "Bob", RelationshipType.COMPARED_TO, 0.5f);
        List<String> result = operations.setEdgeValues();
        // Assuming EdgeValuesHandler is initialized with some test data
        assertEquals(1, result.size());
        assertEquals("SET r1.value = 0.5", result.get(0)); // Assuming "someValue" is the test value
    }
}
