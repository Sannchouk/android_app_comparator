package bipartiteGraph;

/**
 * This class represents an edge in the bipartite graph.
 */
public class Edge {
    private Node source;
    private Node target;
    private double value;

    public Edge(Node source, Node target) {
        this.source = source;
        this.target = target;
        this.value = 1.0;
    }

    public Edge(Node source, Node target, double value) {
        this.source = source;
        this.target = target;
        this.value = value;
    }

    public Node getSource() {
        return source;
    }

    public Node getTarget() {
        return target;
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Edge edge = (Edge) obj;
        return Double.compare(edge.value, value) == 0 &&
                source.equals(edge.source) &&
                target.equals(edge.target);
    }

    @Override
    public int hashCode() {
        int result = source.hashCode();
        result = 31 * result + target.hashCode();
        result = 31 * result + Double.hashCode(value);
        return result;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "source=" + source +
                ", target=" + target +
                ", value=" + value +
                '}';
    }
}

