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
}
