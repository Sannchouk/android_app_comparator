package fileTree;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;


public class FileTreeTest {

    private static final Path TEST_DIR = Paths.get("./test_dir");

    @BeforeAll
    public static void setUp() throws IOException {
        // Create test directory
        Files.createDirectories(TEST_DIR);

        // Create files and directories
        Files.createFile(TEST_DIR.resolve("file1.txt"));
        Files.createDirectories(TEST_DIR.resolve("directory1/subdirectory1"));
        Files.createFile(TEST_DIR.resolve("directory1/file2.txt"));
        Files.createFile(TEST_DIR.resolve("directory1/subdirectory1/file3.txt"));
        Files.createDirectories(TEST_DIR.resolve("directory2"));
        Files.createFile(TEST_DIR.resolve("directory2/file4.txt"));
    }

    @AfterAll
    public static void tearDown() throws IOException {
        // Delete test directory and its contents
        deleteDirectory();
    }

    private static void deleteDirectory() throws IOException {
        if (Files.exists(FileTreeTest.TEST_DIR)) {
            Files.walk(FileTreeTest.TEST_DIR)
                    .sorted((a, b) -> -a.compareTo(b)) // Delete deepest directories first
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            // Handle exception
                            e.printStackTrace();
                        }
                    });
        }
    }

    @Test
    public void testBuildTree() {
        FileTree fileTree = new FileTree(Path.of("parent/child"));
        fileTree.addChild(new FileTree(Path.of("parent/child/grandchild")));
        fileTree.cleanPaths();
        assertEquals(fileTree.getPath(), Path.of("child"));
        assertEquals(fileTree.getChildren().get(0).getPath(), Path.of("child/grandchild"));
    }
}
