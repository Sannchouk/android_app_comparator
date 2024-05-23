package algo;

import neo4j.data.Apk;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public final class AlgorithmResults {

    List<Apk> apks;
    Map<Apk, HashMap<Apk, Float>> distances;
    double[][] distancesMatrix;

    public AlgorithmResults(List<Apk> apks, Map<Apk, HashMap<Apk, Float>> distances) {
        this.apks = apks;
        this.distances = distances;
        this.distancesMatrix = new double[apks.size()][apks.size()];
        for (int i = 0; i < apks.size(); i++) {
            for (int j = 0; j < apks.size(); j++) {
                if (i == j) {
                    distancesMatrix[i][j] = 0;
                    continue;
                }
                distancesMatrix[i][j] = distances.get(apks.get(i)).get(apks.get(j));
                distancesMatrix[j][i] = distancesMatrix[i][j];
            }
        }
    }

    public String toString() {
        return "Apks: " + apks + "\nDistances: " + distances;
    }
}
