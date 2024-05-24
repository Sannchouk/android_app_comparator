package neo4j.language;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is responsible for creating operations in the Neo4j language.
 */
public class OperationsCreator {

    private final EdgeValuesHandler edgeValuesHandler = new EdgeValuesHandler();

    public OperationsCreator() {
    }

    public String createNode(String node) {
        return "CREATE (:" + node + ")";
    }

    public String matchNode(String matchName, String node) {
        return "MATCH (" + matchName + ":" + node +  ")";
    }

    public String createRelationship(String node1, String node2, RelationshipType relationship, float value) {
        edgeValuesHandler.addEdgeValue(value);
        return "MERGE (" + node1 + ")-[r" + edgeValuesHandler.getEdgeValuesNumber() + ":" + relationship.toString() + "]-(" + node2 + ")";
    }

    public String createWithInstruction() {
        return "WITH 1 AS _";
    }

    public List<String> setEdgeValues() {
        return edgeValuesHandler.getEdgeValues().entrySet().stream()
                .map(entry -> "SET " + entry.getKey() + ".value = " + entry.getValue())
                .collect(Collectors.toList());
    }

    public String matchAllNodes() {
        return "MATCH (n)";
    }

    public String deleteAllNodes() {
        return "DETACH DELETE n;";
    }
}
