package algo;

import neo4j.data.Apk;

import java.io.FileWriter;
import java.util.List;

public class DistanceMatrixWriter {

    public void writeDistanceMatrixInCSV(String path, double[][] distancesMatrix, List<Apk> apks) {
        List<String> apkNames = apks.stream().map(Apk::getName).toList();
        try {
            FileWriter writer = new FileWriter(path);

            for (String apkName : apkNames) {
                writer.append(apkName).append(",");
            }
            writer.append("\n");

            for (int i = 0; i < distancesMatrix.length; i++) {
                writer.append(apkNames.get(i)).append(","); // APK name for each row
                for (int j = 0; j < distancesMatrix[i].length; j++) {
                    writer.append(String.valueOf(distancesMatrix[i][j])).append(",");
                }
                writer.append("\n");
            }

            writer.close();
            System.out.println("Distance matrix has been successfully written to CSV file.");
        } catch (Exception e) {
            System.out.println("Error while writing distance matrix to CSV file: " + e.getMessage());
        }
    }
}
