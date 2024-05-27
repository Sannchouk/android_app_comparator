package matching.computers.attributes.extensions;

public class ExtensionDistanceComputer {

    public int computeDistance(String extension1, String extension2) {
        return (extension1 != null && extension1.equals(extension2)) ? 0 : 1;
    }
}
