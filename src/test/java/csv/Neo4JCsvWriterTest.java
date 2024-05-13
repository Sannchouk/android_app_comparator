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
        Map<Apk, HashMap<Apk, Float>> distances = new HashMap<>();
        HashMap<Apk, Float> source1Distances = new HashMap<>();
        Apk source1 = new Apk(Path.of("test1"));
        Apk source2 = new Apk(Path.of("test2"));
        Apk source3 = new Apk(Path.of("test3"));
        source1Distances.put(source2, 10.5f);
        source1Distances.put(source3, 15.2f);
        distances.put(source1, source1Distances);
        Neo4JCsvWriter writer = new Neo4JCsvWriter();
        Path testFilename = Path.of("test_distances.csv");

        //WHEN
        writer.writeCsv(testFilename, distances);

        //THEN
        assertTrue(testFilename.toFile().exists());
        String expectedContent = "source,target,weight\n" +
                "1,3,15.2\n" +
                "1,2,10.5\n";
        byte[] encodedBytes = Files.readAllBytes(Paths.get(testFilename.toString()));
        String actualContent = new String(encodedBytes, StandardCharsets.UTF_8);
        assertEquals(expectedContent, actualContent);
    }
}