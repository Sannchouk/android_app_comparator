package neo4j.language;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class EdgeValuesHandler {

    private int edgeValuesNumber = 0;

    private Map<String, Float> edgeValues;

    public EdgeValuesHandler() {
        this.edgeValues = new HashMap<>();
    }

    public void addEdgeValue(Float value) {
        this.edgeValues.put(String.format("r%d", ++edgeValuesNumber), value);
    }

}
