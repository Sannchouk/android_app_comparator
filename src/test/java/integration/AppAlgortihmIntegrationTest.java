package integration;

import neo4j.data.Apk;
import algo.AlgoRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import utils.AppConfigModifier;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppAlgortihmIntegrationTest {

    @Test
    void testApp() throws URISyntaxException {
        //GIVEN
        Path resources = Paths.get(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("apks")).toURI());

        //WHEN
        var algorithmResults = AlgoRunner.run(resources.toString());

        //THEN
        assertEquals(3, algorithmResults.getApks().size());
        for (Apk apk : algorithmResults.getApks()) {
            assertTrue(apk.getPath().toString().contains("apks"));
            assertTrue(apk.getTotalSize() > 0);
            assertTrue(apk.getNumberOfFiles() > 0);
        }
        assertEquals(2, algorithmResults.getDistances().size());
        Apk chesscomApk = algorithmResults.getApks().stream().filter(apk -> apk.getName().equals("chesscom-apk")).findFirst().get();
        Apk lichessApk = algorithmResults.getApks().stream().filter(apk -> apk.getName().equals("lichess-apk")).findFirst().get();
        Apk lichess2021Apk = algorithmResults.getApks().stream().filter(apk -> apk.getName().equals("lichess-2021-apk")).findFirst().get();
        float chesscomToLichess = 0;
        float chesscomToLichess2021 = 0;
        float lichessToLichess2021 = 0;
        if (algorithmResults.getDistances().containsKey(chesscomApk)) {
            Map<Apk, Float> chesscomDistances = algorithmResults.getDistances().get(chesscomApk);
            if (chesscomDistances != null) {
                if (chesscomDistances.containsKey(lichessApk)) {
                    chesscomToLichess = chesscomDistances.get(lichessApk);
                }
                if (chesscomDistances.containsKey(lichess2021Apk)) {
                    chesscomToLichess2021 = chesscomDistances.get(lichess2021Apk);
                }
            }
        }
        if (algorithmResults.getDistances().containsKey(lichessApk)) {
            Map<Apk, Float> lichessDistances = algorithmResults.getDistances().get(lichessApk);
            if (lichessDistances != null) {
                if (lichessDistances.containsKey(lichess2021Apk)) {
                    lichessToLichess2021 = lichessDistances.get(lichess2021Apk);
                }
                if (lichessDistances.containsKey(chesscomApk)) {
                    chesscomToLichess = lichessDistances.get(chesscomApk);
                }
            }
        }
        if (algorithmResults.getDistances().containsKey(lichess2021Apk)) {
            Map<Apk, Float> lichess2021Distances = algorithmResults.getDistances().get(lichess2021Apk);
            if (lichess2021Distances != null) {
                if (lichess2021Distances.containsKey(lichessApk)) {
                    lichessToLichess2021 = lichess2021Distances.get(lichessApk);
                }
                if (lichess2021Distances.containsKey(chesscomApk)) {
                    chesscomToLichess2021 = lichess2021Distances.get(chesscomApk);
                }
            }
        }
        assertTrue(chesscomToLichess > 0 && chesscomToLichess < 1);
        assertTrue(chesscomToLichess2021 > 0 && chesscomToLichess2021 < 1);
        assertTrue(lichessToLichess2021 > 0 && lichessToLichess2021 < 1);
        assertTrue(lichessToLichess2021 < chesscomToLichess2021);
    }
}
