package matching.computers.names;

import matching.computers.attributes.names.LevenshteinNameDistanceComputer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LevenshteinNameDistanceComputerTest {

    private final LevenshteinNameDistanceComputer distanceComputer = new LevenshteinNameDistanceComputer();

    @ParameterizedTest
    @CsvSource({
            "kitten, sitting, 3",
            "flaw, lawn, 2",
            "saturday, sunday, 3",
            "gumbo, gambol, 2",
            "book, back, 2",
            "apple, apple, 0",
            "distance, difference, 5",
            "a, '', 1",
            "'', '', 0",
            "'', a, 1"
    })
    public void testComputeDistanceBetweenTwoNames(String name1, String name2, double expectedDistance) {
        assertEquals(expectedDistance, distanceComputer.computeDistanceBetweenTwoNames(name1, name2));
    }
}
