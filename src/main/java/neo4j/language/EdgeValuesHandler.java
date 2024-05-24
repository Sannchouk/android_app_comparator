package neo4j.language;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for handling the edge values in the Neo4j language.
 */
@Getter
class EdgeValuesHandler {

    private int edgeValuesNumber = 0;

    private Map<String, Float> edgeValues;

    public EdgeValuesHandler() {
        this.edgeValues = new HashMap<>();
    }

    public void addEdgeValue(Float value) {
        this.edgeValues.put(String.format("r%d", ++edgeValuesNumber), value);
    }

}
