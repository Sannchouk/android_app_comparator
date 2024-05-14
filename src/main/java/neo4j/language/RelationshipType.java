package neo4j.language;

public enum RelationshipType {

    COMPARED_TO("COMPARED_TO");

    private final String name;

    RelationshipType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
