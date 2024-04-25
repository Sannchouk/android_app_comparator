#[cfg(test)]
mod tests {
    use std::path::Path;
    use pji::tree_matching::bipartite_graph::BipartiteGraph;
    use pji::file_tree::TreeNode;
    use pji::tree_matching::indexer::Indexer;
    use pji::tree_matching::node::Node;
    use pji::tree_matching::metropolis_algorithm::{MetropolisAlgorithm, Mapping};

    #[test]
    fn test_app() {
        let apk1 = Path::new("resources/lichess-apk/org");
        let apk2 = Path::new("resources/chesscom-apk/org");

        let tree1 = TreeNode::build_tree(apk1).unwrap_or_else(|e| panic!("Error: {}", e));
        let tree2 = TreeNode::build_tree(apk2).unwrap_or_else(|e| panic!("Error: {}", e));

        let nodes1 = tree1.get_all_nodes_data();
        let nodes2 = tree2.get_all_nodes_data();

        let mut graph_nodes_1 = vec![];
        for data in nodes1 {
            let mut graph_node = Node::new(&data);
            graph_node.tokenize();
            graph_nodes_1.push(graph_node);
        }

        let mut graph_nodes_2 = vec![];
        for data in nodes2 {
            let mut graph_node = Node::new(&data);
            graph_node.tokenize();
            graph_nodes_2.push(graph_node);
        }


        let mut indexer = Indexer::new();
        indexer.add_nodes(graph_nodes_1.clone(), 1);
        indexer.add_nodes(graph_nodes_2.clone(), 2);
        let mut similarity_scores = indexer.compute_similarity_scores();

        let mut graph = BipartiteGraph::new(graph_nodes_1.clone(), graph_nodes_2.clone(), vec![]);
        let _ = graph.build_edges_from_neighborhoods(&similarity_scores);

        let mut metropolis_algorithm = MetropolisAlgorithm::new(graph, 2.5, 0.8, 10);
        metropolis_algorithm.run();
        let mapping = metropolis_algorithm.get_mapping();

        println!("{:?}", mapping.len());
        println!("nodes1 length {:?}", graph_nodes_1.len());
        println!("nodes2 length {:?}", graph_nodes_2.len());

        for edge in mapping {
            println!("{:?}", edge);
        }

        assert!(mapping.len() > 0);
        assert!(false);
    }
}
