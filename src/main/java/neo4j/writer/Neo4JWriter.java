package neo4j.writer;

import algo.AlgorithmResults;
import neo4j.data.Apk;
import neo4j.language.OperationsCreator;
import neo4j.language.RelationshipType;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for writing the file that will be used to populate the Neo4J database.
 */
public class Neo4JWriter {

    private final OperationsCreator operationsCreator = new OperationsCreator();

    /**
     * Writes the file that will be used to populate the Neo4J database
     * @param file the file to write
     * @param algorithmResults the results of the algorithm
     */
    public void write(File file, AlgorithmResults algorithmResults) {
        List<String> operations = getOperations(algorithmResults.getApks(), algorithmResults.getDistances());
        String operationsString = fromListToString(operations);
        try {
            FileUtils.writeStringToFile(file, operationsString, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> getOperations(List<Apk> apks, Map<Apk, HashMap<Apk, Float>> distances) {
        List<String> operations = new ArrayList<>();
        operations.add(operationsCreator.matchAllNodes());
        operations.add(operationsCreator.deleteAllNodes());
        for (Apk apk : apks) {
            operations.add(operationsCreator.createNode(apk.toNeo4JString()));
        }
        operations.add(operationsCreator.createWithInstruction());
        for (Apk apk : apks) {
            operations.add(operationsCreator.matchNode(apk.getNeo4JName(), apk.toNeo4JString()));
        }
        for (Apk source : distances.keySet()) {
            for (Apk target : distances.get(source).keySet()) {
                operations.add(operationsCreator.createRelationship(source.getNeo4JName(), target.getNeo4JName(), RelationshipType.COMPARED_TO, distances.get(source).get(target)));
            }
        }
        operations.addAll(operationsCreator.setEdgeValues());
        return operations;
    }

    private String fromListToString(List<String> operations) {
        StringBuilder builder = new StringBuilder();
        for (String operation : operations) {
            builder.append(operation).append("\n");
        }
        return builder.toString();
    }


}
