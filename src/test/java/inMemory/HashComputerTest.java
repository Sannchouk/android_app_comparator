package inMemory;

import matching.computers.attributes.hashs.HammingDistanceComputer;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HashComputerTest {

    private static HashComputer hashComputer = new HashComputer();
    private static Path directoryPath = Path.of("test_directory");
    private static File directory = new File(String.valueOf(directoryPath));

    @AfterAll
    public static void tearDown() {
        directory.delete();
    }

    @Test
    public void testDirectoryHash() throws IOException, NoSuchAlgorithmException {
        if (!directory.exists()) {
            directory.mkdir();
        }

        String hash = hashComputer.generateHash(directoryPath);

        assertEquals("0".repeat(64), hash);
    }

    @Test
    public void closeFileHaveCloseHashes() throws IOException, NoSuchAlgorithmException {
        Path filePath1 = Path.of("test_file_1.txt");
        Path filePath2 = Path.of("test_file_2.txt");
        Path filePath3 = Path.of("test_file_3.txt");
        Path filePath4 = Path.of("test_file_4.txt");
        File file1 = filePath1.toFile();
        File file2 = filePath2.toFile();
        File file3 = filePath3.toFile();
        File file4 = filePath4.toFile();
        BufferedWriter writer1 = new BufferedWriter(new FileWriter(String.valueOf(filePath1)));
        BufferedWriter writer2 = new BufferedWriter(new FileWriter(String.valueOf(filePath2)));
        BufferedWriter writer3 = new BufferedWriter(new FileWriter(String.valueOf(filePath3)));
        BufferedWriter writer4 = new BufferedWriter(new FileWriter(String.valueOf(filePath4)));
        writer1.write("File text number one that we can find");
        writer2.write("File text number two that we can find");
        writer3.write("Different file text for the other two files 1");
        writer4.write("Different file text for the other two files 2");

        String hash1 = hashComputer.generateHash(filePath1);
        String hash2 = hashComputer.generateHash(filePath2);
        String hash3 = hashComputer.generateHash(filePath3);
        String hash4 = hashComputer.generateHash(filePath4);

        HammingDistanceComputer hammingDistanceComputer = new HammingDistanceComputer();
        float distance12 = hammingDistanceComputer.computeDistanceBetweenTwoHashes(hash1, hash2);
        float distance13 = hammingDistanceComputer.computeDistanceBetweenTwoHashes(hash1, hash3);
        float distance14 = hammingDistanceComputer.computeDistanceBetweenTwoHashes(hash1, hash4);
        float distance23 = hammingDistanceComputer.computeDistanceBetweenTwoHashes(hash2, hash3);
        float distance24 = hammingDistanceComputer.computeDistanceBetweenTwoHashes(hash2, hash4);
        float distance34 = hammingDistanceComputer.computeDistanceBetweenTwoHashes(hash3, hash4);
        //first ones are closest between them
        assertTrue(distance12 < distance13);
        assertTrue(distance12 < distance14);
        assertTrue(distance12 < distance23);
        assertTrue(distance12 < distance24);
        //different are closest between them
        assertTrue(distance34 < distance13);
        assertTrue(distance34 < distance14);
        assertTrue(distance34 < distance23);
        assertTrue(distance34 < distance24);
        //different are closest between them than first ones between them
        assertTrue(distance34 < distance12);
    }
}
