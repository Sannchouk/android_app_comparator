package matching.computers.hashs;

import matching.computers.attributes.hashs.HammingDistanceComputer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HammingDistanceComputerTest {

    @ParameterizedTest
    @CsvSource({
            "'101010', '101010', 0", // Identical hashes
            "'101010', '100110', 2", // Different hashes
            "'1010', '110', 2",      // Different lengths
            "'', '', 0",             // Empty hashes
            "'1010', '', 4"          // One empty hash
    })
    public void testComputeDistanceBetweenTwoHashes(String hash1, String hash2, int expected) {
        HammingDistanceComputer hdc = new HammingDistanceComputer();
        int actual = hdc.computeDistanceBetweenTwoHashes(hash1, hash2);
        assertEquals(expected, actual);
    }
}
