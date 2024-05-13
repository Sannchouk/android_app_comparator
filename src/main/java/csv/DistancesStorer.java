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
        boolean isTargetMapInitialized = distances.get(target) != null;
        if (isTargetMapInitialized && distances.get(target).get(source) != null) {
            throw new IllegalArgumentException("Distance already exists");
        }
        this.distances.putIfAbsent(source, new HashMap<>());
        this.distances.get(source).put(target, distance);
    }
}
