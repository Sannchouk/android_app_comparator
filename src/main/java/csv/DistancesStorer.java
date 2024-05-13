package csv;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public final class DistancesStorer {

    private Map<String, HashMap<String, Float>> distances;

    public DistancesStorer() {
        this.distances = new HashMap<>();
    }

    public void addDistance(String source, String target, Float distance) {
        this.distances.putIfAbsent(source, new HashMap<>());
        this.distances.get(source).put(target, distance);
    }

    public Map<String, HashMap<String, Float>> getDistances() {
        return this.distances;
    }

    public boolean hasDistanceBeenAlreadyComputed(String source, String target) {
        return distances.get(target) != null && distances.get(target).get(source) != null;
    }
}
