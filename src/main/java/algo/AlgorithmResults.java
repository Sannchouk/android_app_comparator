package algo;

import neo4j.data.Apk;
import lombok.Getter;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the results of the algorithm.
 */
@Getter
public final class AlgorithmResults implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Apk> apks;
    private Map<Apk, HashMap<Apk, Float>> distances;
    private transient double[][] distancesMatrix;

    public AlgorithmResults(List<Apk> apks, Map<Apk, HashMap<Apk, Float>> distances) {
        this.apks = apks;
        this.distances = distances;
        this.initDistancesMatrix();
    }

    private void initDistancesMatrix() {
        this.distancesMatrix = new double[apks.size()][apks.size()];
        for (int i = 0; i < apks.size(); i++) {
            for (int j = 0; j < apks.size(); j++) {
                if (i == j) {
                    distancesMatrix[i][j] = 0;
                    continue;
                }
                if (distances.get(apks.get(i)) == null || distances.get(apks.get(i)).get(apks.get(j)) == null) {
                    distancesMatrix[i][j] = distances.get(apks.get(j)).get(apks.get(i));
                } else {
                    distancesMatrix[i][j] = distances.get(apks.get(i)).get(apks.get(j));
                }
                distancesMatrix[j][i] = distancesMatrix[i][j];
            }
        }
    }

    /**
     * Serializes the object to a file.
     * @param fileName the name of the file
     * @throws IOException if an I/O error occurs
     */
    public void serialize(String fileName) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(fileName);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(this);
        }
    }

    /**
     * Deserializes the object from a file.
     * @param fileName the name of the file
     * @return the deserialized object
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of the serialized object cannot be found
     */
    public static AlgorithmResults deserialize(String fileName) throws IOException, ClassNotFoundException {
        try (FileInputStream fileIn = new FileInputStream(fileName);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            AlgorithmResults algorithmResults = (AlgorithmResults) in.readObject();
            algorithmResults.initDistancesMatrix();
            return algorithmResults;
        }
    }
}
