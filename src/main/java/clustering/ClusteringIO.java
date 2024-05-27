package clustering;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ClusteringIO {

    /**
     * Write a cluster on a file
     * @param clusters the clusters
     * @param filePath the path of the file
     */
    public void writeClustersToFile(List<List<String>> clusters, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (int i = 0; i < clusters.size(); i++) {
                writer.write("Cluster " + (i + 1) + ":\n");
                for (String apkName : clusters.get(i)) {
                    writer.write(apkName + "\n");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Error while writing clusters to file: " + e.getMessage());
        }
    }
}
