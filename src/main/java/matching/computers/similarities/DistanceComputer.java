package matching.computers.similarities;

import bipartiteGraph.Node;
import matching.computers.extensions.ExtensionDistanceComputer;
import matching.computers.hashs.HammingDistanceComputer;
import matching.computers.names.LevenshteinNameDistanceComputer;

public class DistanceComputer {
    private static final double HASH_WEIGHT = 0.7;
    private static final double NAME_WEIGHT = 0.3;
    private static final double EXTENSION_WEIGHT = 2;

    public int computeDistanceForAttributes(int extensionDistance, int nameDistance, int hashDistance) {
        return (int) ((EXTENSION_WEIGHT * extensionDistance) + (NAME_WEIGHT * nameDistance + 1) + (HASH_WEIGHT * hashDistance + 1));
    }

    public int computeDistance(Node x, Node y) {
        LevenshteinNameDistanceComputer nameDistanceComputer = new LevenshteinNameDistanceComputer();
        int nameDistance = nameDistanceComputer.computeDistanceBetweenTwoNames(x.getAttributes().get("name"), y.getAttributes().get("name"));

        HammingDistanceComputer hashDistanceComputer = new HammingDistanceComputer();
        int hashDistance = hashDistanceComputer.computeDistanceBetweenTwoHashes(x.getAttributes().get("hash"), y.getAttributes().get("hash"));

        ExtensionDistanceComputer extensionDistanceComputer = new ExtensionDistanceComputer();
        int extensionDistance = extensionDistanceComputer.computeDistance(x.getAttributes().get("extension"), y.getAttributes().get("extension"));

        return computeDistanceForAttributes(extensionDistance, hashDistance, nameDistance);
    }


}
