#[cfg(test)]
mod tests {

    use pji::tree_matching::bipartite_graph::{BipartiteGraph, Edge, Node};

    #[test]
    fn test_find_node() {
        let node1 = Node { name : String::from("name1") };
        let node2 = Node { name : String::from("name2") };
        let _node3 = Node { name : String::from("name3") };

        let graph = BipartiteGraph::new(vec![node1.clone()], vec![node2.clone()], vec![]);

        assert_eq!(graph.find_node(1, "name1"), Some(&node1));
        assert_eq!(graph.find_node(2, "name2"), Some(&node2));
        assert_eq!(graph.find_node(1, "name3"), None);
        assert_eq!(graph.find_node(2, "name1"), None);
    }

    #[test]
    fn add_edge() {
        let node1 = Node { name : String::from("name1") };
        let node2 = Node { name : String::from("name2") };
        let edge = Edge { source: node1.clone(), target: node2.clone() };

        let mut graph = BipartiteGraph::new(vec![node1.clone()], vec![node2.clone()], vec![]);

        let result = graph.add_edge(edge.clone());

        assert_eq!(result, Ok(()));
        assert_eq!(graph.edges.len(), 1);
        assert_eq!(graph.edges[0], edge);
    }

    #[test]
    fn add_edge_fails_if_same_group() {
        let node1 = Node { name : String::from("name1") };
        let node2 = Node { name : String::from("name2") };
        let node3 = Node { name : String::from("name3") };
        let edge = Edge { source: node1.clone(), target: node2.clone() };

        let mut graph = BipartiteGraph::new(vec![node1.clone(), node2.clone()], vec![node3.clone()], vec![]);

        let result = graph.add_edge(edge.clone());

        assert_eq!(result, Err("Both nodes are in the same group"));
        assert_eq!(graph.edges.len(), 0);
    }

    #[test]
    fn remove_edge() {
        let node1 = Node { name : String::from("name1") };
        let node2 = Node { name : String::from("name2") };
        let edge = Edge { source: node1.clone(), target: node2.clone() };

        let mut graph = BipartiteGraph::new(vec![node1.clone()], vec![node2.clone()], vec![edge.clone()]);

        graph.remove_edge(&edge);

        assert_eq!(graph.edges.len(), 0);
    }

    #[test]
    fn get_group() {
        let node1 = Node { name : String::from("name1") };
        let node2 = Node { name : String::from("name2") };
        let node3 = Node { name : String::from("name3") };

        let graph = BipartiteGraph::new(vec![node1.clone(), node2.clone()], vec![node3.clone()], vec![]);

        assert_eq!(graph.get_group("name1"), Some(1));
        assert_eq!(graph.get_group("name2"), Some(1));
        assert_eq!(graph.get_group("name3"), Some(2));
        assert_eq!(graph.get_group("name4"), None);
    }

    #[test]
    fn remove_all_adjacent_edges() {
        let node1 = Node { name : String::from("name1") };
        let node2 = Node { name : String::from("name2") };
        let node3 = Node { name : String::from("name3") };
        let node4 = Node { name : String::from("name4") };
        let node5 = Node { name : String::from("name5") };
        let edge1 = Edge { source: node1.clone(), target: node2.clone() };
        let edge2 = Edge { source: node1.clone(), target: node3.clone() };
        let edge3 = Edge { source: node1.clone(), target: node4.clone() };
        let edge4 = Edge { source: node5.clone(), target: node2.clone() };
        let edge5 = Edge { source: node5.clone(), target: node3.clone() };

        let edges = vec![edge1.clone(), edge2.clone(), edge3.clone(), edge4.clone(), edge5.clone()];
        let mut graph = BipartiteGraph::new(vec![node1.clone(), node5.clone()], vec![node2.clone(), node3.clone(), node4.clone()], edges);

        graph.remove_all_adjacent_edges(&edge1);

        assert_eq!(graph.edges.len(), 2);
        assert_eq!(graph.edges[0], edge1);
        assert_eq!(graph.edges[1], edge5);
    }
}
