package neo4j.language;

/**
 * This class is responsible for handling the relationship types in the Neo4j language.
 */
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
