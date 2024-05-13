package csv;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public final class DistancesStorer {

    private Map<Path, HashMap<Path, Float>> distances;

    public DistancesStorer() {
        this.distances = new HashMap<>();
    }

    public void addDistance(Path source, Path target, Float distance) {
        this.distances.putIfAbsent(source, new HashMap<>());
        this.distances.get(source).put(target, distance);
    }

    public Map<Path, HashMap<Path, Float>> getDistances() {
        return this.distances;
    }

    public boolean hasDistanceBeenAlreadyComputed(Path source, Path target) {
        return distances.get(target) != null && distances.get(target).get(source) != null;
    }
}
