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

    fn test_select_edge_from() {
        let mut graph = BipartiteGraph::new_empty(); 
        let node1 = Node { name: "node1".to_string(), tokens: vec![] };
        let node2 = Node { name: "node2".to_string(), tokens: vec![] };
        let node3 = Node { name: "node3".to_string(), tokens: vec![] };
        let edges: Mapping = vec![
            Edge { source: node2.clone(), target: node3.clone(), value: 2.0 },
            Edge { source: node1.clone(), target: node3.clone(), value: 3.0 },
        ];

        graph.add_nodes(vec![node1.clone(), node2.clone()], 1);
        graph.add_nodes(vec![node3.clone()], 2);
        graph.add_edges(edges.clone());

        let metropolis_algorithm = MetropolisAlgorithm::new(graph, 2.5, 0.8, 10);
        let edge = metropolis_algorithm.select_edge_from(&edges);
        assert!(edge.is_some());
    }

    fn test_remove_all_adjacent_edges() {
        let mut graph = BipartiteGraph::new_empty(); 
        let node1 = Node { name: "node1".to_string(), tokens: vec![] };
        let node2 = Node { name: "node2".to_string(), tokens: vec![] };
        let node3 = Node { name: "node3".to_string(), tokens: vec![] };
        let edges: Mapping = vec![
            Edge { source: node2.clone(), target: node3.clone(), value: 2.0 },
            Edge { source: node1.clone(), target: node3.clone(), value: 3.0 },
        ];

        graph.add_nodes(vec![node1.clone(), node2.clone()], 1);
        graph.add_nodes(vec![node3.clone()], 2);
        graph.add_edges(edges.clone());

        let mut metropolis_algorithm = MetropolisAlgorithm::new(graph, 2.5, 0.8, 10);
        let edge = &edges[0];
        metropolis_algorithm.remove_all_adjacent_edges(edge);
        assert!(metropolis_algorithm.get_graph().edges.len() == 1);
    }
}
