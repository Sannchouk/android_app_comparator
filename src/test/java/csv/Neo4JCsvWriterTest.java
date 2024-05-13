package csv;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class Neo4JCsvWriterTest {

    static String testFileName = "test_distances.csv";

    @AfterAll
    static void cleanUp() {
        Path testFile = Paths.get(testFileName);
        if (testFile.toFile().exists()) {
            testFile.toFile().delete();
        }
    }

    @Test
    void testWriteCsv() throws IOException {
        //GIVEN
        Map<Path, HashMap<Path, Float>> distances = new HashMap<>();
        HashMap<Path, Float> source1Distances = new HashMap<>();
        source1Distances.put(Path.of("nodeB"), 10.5f);
        source1Distances.put(Path.of("nodeC"), 15.2f);
        distances.put(Path.of("nodeA"), source1Distances);
        Neo4JCsvWriter writer = new Neo4JCsvWriter();
        Path testFilename = Path.of("test_distances.csv");

        //WHEN
        writer.writeCsv(testFilename, distances);

        //THEN
        assertTrue(testFilename.toFile().exists());
        String expectedContent = "source,target,weight\n" +
                "nodeA,nodeB,10.5\n" +
                "nodeA,nodeC,15.2\n";
        byte[] encodedBytes = Files.readAllBytes(Paths.get(testFilename.toString()));
        String actualContent = new String(encodedBytes, StandardCharsets.UTF_8);
        assertEquals(expectedContent, actualContent);
    }
}
