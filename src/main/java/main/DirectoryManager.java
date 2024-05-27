package main;

import java.io.File;

class DirectoryManager {

    public static boolean ensureResultsDirectory() {
        File resultsDir = new File("results");
        if (!resultsDir.exists()) {
            if (resultsDir.mkdirs()) {
                System.out.println("Created results directory");
                return true;
            } else {
                System.out.println("Failed to create results directory");
                return false;
            }
        }
        return true;
    }
}
