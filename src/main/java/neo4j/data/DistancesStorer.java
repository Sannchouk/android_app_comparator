package neo4j.data;

import lombok.Getter;
import neo4j.data.Apk;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for storing the distances between the APKs.
 */
@Getter
public final class DistancesStorer {

    private Map<Apk, HashMap<Apk, Float>> distances;

    public DistancesStorer() {
        this.distances = new HashMap<>();
    }

    /**
     * Adds a distance between two APKs.
     * @param source the source APK
     * @param target the target APK
     * @param distance the distance between the two APKs
     */
    public void addDistance(Apk source, Apk target, Float distance) {
        this.distances.putIfAbsent(source, new HashMap<>());
        this.distances.get(source).put(target, distance);
    }

    /**
     * Checks if the distance between two APKs has already been computed.
     * @param source the source APK
     * @param target the target APK
     * @return true if the distance has already been computed, false otherwise
     */
    public boolean hasDistanceBeenAlreadyComputed(Apk source, Apk target) {
        return distances.get(target) != null && distances.get(target).get(source) != null;
    }
}
