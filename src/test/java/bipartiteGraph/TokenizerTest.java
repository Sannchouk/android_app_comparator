package bipartiteGraph;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenizerTest {

    @Test
    public void testNameToken() {
        Path path = Paths.get("resources/lichess-apk");
        Node node = new Node(path);
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.tokenize(node);
        assertEquals("lichess-apk", node.getTokens().get(0));
    }
}
