package matching;

import bipartiteGraph.Edge;

public class CostComputer {

    private final int wr = 1;
    private final int wa = 1;
    private final int ws = 1;

    public double computeCost(Edge edge) {
        return labelCost(edge) + ancestorCost(edge) + siblingCost(edge);
    }

    public double labelCost(Edge edge) {
        return edge.getSource().getName().equals(edge.getTarget().getName()) ? 0 : wr;
    }

    public double ancestorCost(Edge edge) {
        if (edge.getSource().getParent() == null && edge.getTarget().getParent() == null) {
            return 0;
        }
        if (edge.getSource().getParent() == null || edge.getTarget().getParent() == null) {
            return wa;
        }
        return edge.getSource().getParent().equals(edge.getTarget().getParent()) ? 0 : wa;
    }

    public double siblingCost(Edge edge) {
//        return edge.getSource().getSiblings().contains(edge.getTarget()) ? 0 : ws;
        return 0;
    }
}
