package neo4j.language;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperationsCreatorTest {

    @Test
    public void testCreateNode() {
        OperationsCreator operations = new OperationsCreator();
        String result = operations.createNode("Person");
        assertEquals("CREATE (:Person)", result);
    }

    @Test
    public void testMatchNode() {
        OperationsCreator operations = new OperationsCreator();
        String result = operations.matchNode("n", "Person");
        assertEquals("MATCH (n:Person)", result);
    }

    @Test
    public void testCreateRelationship() {
        OperationsCreator operations = new OperationsCreator();
        String result = operations.createRelationship("Alice", "Bob", RelationshipType.COMPARED_TO, 0.0f);
        assertEquals("MERGE (Alice)-[r1:COMPARED_TO]-(Bob)", result);
    }

    @Test
    public void testCreateWithInstruction() {
        OperationsCreator operations = new OperationsCreator();
        String result = operations.createWithInstruction();
        assertEquals("WITH 1 AS _", result);
    }

    @Test
    public void testSetEdgeValues() {
        OperationsCreator operations = new OperationsCreator();
        operations.createRelationship("Alice", "Bob", RelationshipType.COMPARED_TO, 0.5f);
        List<String> result = operations.setEdgeValues();
        assertEquals(1, result.size());
        assertEquals("SET r1.value = 0.5", result.get(0)); // Assuming "someValue" is the test value
    }
}
