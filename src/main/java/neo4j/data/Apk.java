package neo4j.data;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Path;

@Getter
public final class Apk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    private static int ID_COUNTER = 0;

    private int id;
    private String name;
    private transient Path path; // Path is not serializable, so mark it as transient
    @Setter
    private int numberOfFiles;
    @Setter
    private long totalSize;

    public static void resetIdCounter() {
        ID_COUNTER = 0;
    }

    public Apk(Path path) {
        this.id = ++ID_COUNTER;
        this.name = path.getFileName().toString();
        this.path = path;
    }

    @Serial
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(path != null ? path.toString() : null);
    }

    @Serial
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException, IOException {
        in.defaultReadObject();
        String pathStr = (String) in.readObject();
        this.path = pathStr != null ? Path.of(pathStr) : null;
    }

    @Override
    public String toString() {
        return "Apk {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path=" + path +
                ", numberOfFiles=" + numberOfFiles +
                ", totalSize=" + totalSize +
                '}';
    }

    public String toNeo4JString() {
        return String.format("Apk{name:\"%s\", numberOfFiles:\"%d\", size:\"%d\"}", getNeo4JName(), numberOfFiles, totalSize);
    }

    public String getNeo4JName() {
        return name.replace("-apk", "").replace("-", "_");
    }
}
