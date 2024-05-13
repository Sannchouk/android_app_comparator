package csv;

import java.util.HashMap;
import java.util.Map;

public final class DistancesStorer {

    private Map<Apk, HashMap<Apk, Float>> distances;

    public DistancesStorer() {
        this.distances = new HashMap<>();
    }

    public void addDistance(Apk source, Apk target, Float distance) {
        this.distances.putIfAbsent(source, new HashMap<>());
        this.distances.get(source).put(target, distance);
    }

    public Map<Apk, HashMap<Apk, Float>> getDistances() {
        return this.distances;
    }

    public boolean hasDistanceBeenAlreadyComputed(Apk source, Apk target) {
        return distances.get(target) != null && distances.get(target).get(source) != null;
    }
}
