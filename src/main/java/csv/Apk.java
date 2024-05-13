package csv;

import lombok.Getter;

import java.nio.file.Path;

@Getter
public class Apk {

    private static int ID_COUNTER = 0;

    private int id;
    private String name;
    private Path path;

    public Apk(Path path) {
        this.id = ++ID_COUNTER;
        this.name = path.getFileName().toString();
        this.path = path;
    }

    public String toString() {
        return this.name;
    }
}
