#[cfg(test)]
mod tests {
    use pji::tree_matching::metropolis_algorithm::{MetropolisAlgorithm, Mapping};
    use pji::tree_matching::bipartite_graph::{BipartiteGraph, Edge};
    use pji::tree_matching::node::Node;

    #[test]
    fn test_compute_cost() {
        let graph = BipartiteGraph::new_empty(); 
        let node1 = Node { name: "node1".to_string(), tokens: vec![] };
        let node2 = Node { name: "node2".to_string(), tokens: vec![] };
        let node3 = Node { name: "node3".to_string(), tokens: vec![] };
        let edges: Mapping = vec![
            Edge { source: node1.clone(), target: node2.clone(), value: 1.0 },
            Edge { source: node2.clone(), target: node3.clone(), value: 2.0 },
            Edge { source: node1.clone(), target: node3.clone(), value: 3.0 },
        ];

        let metropolis_algorithm = MetropolisAlgorithm::new(graph, 2.5, 0.8, 10);

        let cost = metropolis_algorithm.compute_cost(&edges);
        let expected_cost_with_no_exp:f64 = -2.5 * (1.0 + 2.0 + 3.0) / 3.0;
        let expected_cost:f64 = expected_cost_with_no_exp.exp();
        assert!(cost == expected_cost);
    }

    #[test]
    fn test_select_edge_from() {
        let graph = BipartiteGraph::new_empty(); 
        let node1 = Node { name: "node1".to_string(), tokens: vec![] };
        let node2 = Node { name: "node2".to_string(), tokens: vec![] };
        let node3 = Node { name: "node3".to_string(), tokens: vec![] };
        let edges: Mapping = vec![
            Edge { source: node1.clone(), target: node2.clone(), value: 1.0 },
            Edge { source: node2.clone(), target: node3.clone(), value: 2.0 },
            Edge { source: node1.clone(), target: node3.clone(), value: 3.0 },
        ];

        let metropolis_algorithm_sure = MetropolisAlgorithm::new(graph.clone(), 2.5, 1.0, 10);
        let metropolis_algorithm_unsure = MetropolisAlgorithm::new(graph.clone(), 2.5, 0.0, 10);
        
        let edge = metropolis_algorithm_sure.select_edge_from(&edges);
        assert!(edge.is_some());
        let not_edge = metropolis_algorithm_unsure.select_edge_from(&edges);
        assert!(not_edge.is_none());
    }
}
