package neo4j;

import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Neo4JCsvWriter {

    public void writeNodesCsv(Path filename, List<Apk> nodes) {
        if (filename.toFile().exists()) {
            filename.toFile().delete();
        }
        try {
            filename.toFile().createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (PrintWriter writer = new PrintWriter(filename.toString())) {
            writer.println("id,label");
            for (Apk node : nodes) {
                writer.println(node.getId() + "," + node.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeDistanceCsv(Path filename, Map<Apk, HashMap<Apk, Float>> distances) {
        if (filename.toFile().exists()) {
            filename.toFile().delete();
        }
        try {
            filename.toFile().createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (PrintWriter writer = new PrintWriter(filename.toString())) {
            writer.println(":START_ID,:END_ID,weight");
            for (Apk source : distances.keySet()) {
                for (Apk target : distances.get(source).keySet()) {
                    writer.println(source.getId() + "," + target.getId() + "," + distances.get(source).get(target));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
