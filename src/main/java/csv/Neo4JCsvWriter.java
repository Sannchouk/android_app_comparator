package csv;

import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Neo4JCsvWriter {

    public void writeCsv(Path filename, Map<String, HashMap<String, Float>> distances) {
        if (filename.toFile().exists()) {
            filename.toFile().delete();
        }
        try {
            filename.toFile().createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (PrintWriter writer = new PrintWriter(filename.toString())) {
            writer.println("source,target,weight");
            for (String source : distances.keySet()) {
                for (String target : distances.get(source).keySet()) {
                    writer.println(source + "," + target + "," + distances.get(source).get(target));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
