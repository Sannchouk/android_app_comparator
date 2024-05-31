package matching.computers.similarities;

import bipartiteGraph.Node;
import matching.computers.attributes.extensions.ExtensionDistanceComputer;
import matching.computers.attributes.hashs.BooleanDistanceComputer;
import matching.computers.attributes.hashs.HammingDistanceComputer;
import matching.computers.attributes.hashs.HashDistanceComputer;
import matching.computers.attributes.names.LevenshteinNameDistanceComputer;
import matching.computers.attributes.sizes.SizeDistanceComputer;

public class DistanceComputer {
    private static final double HASH_WEIGHT = 0.75;
    private static final double NAME_WEIGHT = 0.25;
    private static final double SIZE_WEIGHT = 4;
    private static final double EXTENSION_WEIGHT = 8;

    public int computeDistanceForAttributes(int extensionDistance, int nameDistance, int hashDistance, double sizeDistance) {
        return (int) ((EXTENSION_WEIGHT * extensionDistance) + (NAME_WEIGHT * nameDistance) + (SIZE_WEIGHT * sizeDistance) + (HASH_WEIGHT * hashDistance));
    }

    public int computeDistance(Node x, Node y) {
        LevenshteinNameDistanceComputer nameDistanceComputer = new LevenshteinNameDistanceComputer();
        int nameDistance = nameDistanceComputer.computeDistanceBetweenTwoNames(x.getAttributes().get("name"), y.getAttributes().get("name"));

        HashDistanceComputer hashDistanceComputer = new HammingDistanceComputer();
        int hashDistance = hashDistanceComputer.computeDistanceBetweenTwoHashes(x.getAttributes().get("hash"), y.getAttributes().get("hash"));

        ExtensionDistanceComputer extensionDistanceComputer = new ExtensionDistanceComputer();
        int extensionDistance = extensionDistanceComputer.computeDistance(x.getAttributes().get("extension"), y.getAttributes().get("extension"));

        SizeDistanceComputer sizeDistanceComputer = new SizeDistanceComputer();
        double sizeDistance = sizeDistanceComputer.computeDistanceBetweenTwoSizes(x.getAttributes().get("size"), y.getAttributes().get("size"));

        return computeDistanceForAttributes(extensionDistance, hashDistance, nameDistance, sizeDistance);
    }


}
