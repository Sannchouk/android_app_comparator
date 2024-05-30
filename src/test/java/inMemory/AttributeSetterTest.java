package inMemory;

import bipartiteGraph.Node;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;


public class AttributeSetterTest {

    @Test
    public void attributesAreCorrectlySet() throws IOException, NoSuchAlgorithmException {
        Path path = Path.of("test.txt");
        File file = new File(path.toString());
        if (!file.exists()) {
            Files.createFile(path);
        }
        Node node = new Node(path);
        AttributeSetter attributeSetter = new AttributeSetter();

        attributeSetter.setAttributes(node);
        file.delete();

        assertEquals(4, node.getAttributes().entrySet().size());
        assertNotNull(node.getAttributes().get("name"));
        assertNotNull(node.getAttributes().get("extension"));
        assertNotNull(node.getAttributes().get("size"));
        assertNotNull(node.getAttributes().get("hash"));
        assertEquals("test", node.getAttributes().get("name"));
        assertEquals("txt", node.getAttributes().get("extension"));
        assertEquals(String.valueOf(FileUtils.sizeOf(file)),node.getAttributes().get("size"));
        assertEquals(new HashComputer().generateHash(path), node.getAttributes().get("hash"));
    }
}
