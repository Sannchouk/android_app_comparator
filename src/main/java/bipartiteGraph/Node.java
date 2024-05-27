package bipartiteGraph;

import lombok.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class represents a node in the bipartite graph.
 */
@Getter
@AllArgsConstructor
public class Node {
    @Setter
    private int id;
    private Path path;
    private List<String> tokens;
    @Setter
    private Integer group = null;
    @Setter
    private Node parent = null;
    @NonNull
    private List<Node> children = new ArrayList<>();
    private Map<String, String> attributes = new HashMap<>();

    public Node(String path) {
        this.path = Path.of(path);
        this.tokens = new ArrayList<>();
    }

    public Node(Path path) {
        this.path = path;
        this.tokens = new ArrayList<>();
    }

    public Node(Path path, List<String> tokens) {
        this.path = path;
        this.tokens = tokens;
    }

    public Node(Path path, Map<String, String> attributes) {
        this.path = path;
        this.attributes = attributes;
        this.tokens = new ArrayList<>(attributes.values());
    }

    /**
     * Adds a token to the node.
     * @param token the token
     */
    public void addToken(String token) {
        tokens.add(token);
    }

    /**
     * Adds an attribute to the node.
     * @param attribute the attribute
     * @param value the value of the attribute
     */
    public void addAttribute(String attribute, String value) {
        attributes.put(attribute, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return path.equals(node.path) && tokens.equals(node.tokens) && group.equals(node.group) && parent.equals(node.parent) && children.equals(node.children);
    }

    @Override
    public int hashCode() {
        int result = path.hashCode();
        result = 31 * result + tokens.hashCode();
        return result;
    }

    public String toString() {
        return "Node{" +
                "path=" + path +
                ", tokens=" + tokens +
                '}';
    }
}
