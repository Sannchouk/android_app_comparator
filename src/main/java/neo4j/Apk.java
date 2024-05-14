package neo4j;

import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;

@Getter
public final class Apk {

    @Getter
    private static int ID_COUNTER = 0;

    private int id;
    private String name;
    private Path path;
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

    public String toString() {
        return "Apk {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path=" + path +
                ", numberOfFiles=" + numberOfFiles +
                ", totalSize=" + totalSize +
                '}';
    }
}
