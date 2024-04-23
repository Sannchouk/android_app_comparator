#[cfg(test)]
mod tests {
    use pji::tree_matching::indexer::Indexer;
    use pji::tree_matching::node::Node;

    use super::*;

    #[test]
    fn test_compute_idf() {
        let mut indexer = Indexer::new();
        let node1 = Node::new("test");
        let node2 = Node::new("test");
        let node3 = Node::new("test");
        indexer.nodes = vec![node1.clone(), node2.clone(), node3.clone()];
        indexer.token_map.insert("token1".to_string(), vec![node1.clone()]);
        indexer.token_map.insert("token2".to_string(), vec![node1, node2, node3]);
        indexer.token_map.insert("token3".to_string(), vec![]);

        assert!(indexer.compute_idf("token1") > 1.0 && indexer.compute_idf("token1") < 1.1); // Adjusted for floating point precision
        assert_eq!(indexer.compute_idf("token2"), 0.0); // Since all nodes have token2
        assert_eq!(indexer.compute_idf("token3"), f64::INFINITY); // Adjusted for floating point precision
    }

    #[test]
    fn test_compute_similarity_scores() {
        // WHEN
        let tokens_n1 = vec!["apple".to_string(), "banana".to_string(), "orange".to_string()];
        let tokens_n2 = vec!["unique1".to_string(), "unique2".to_string()];
        let tokens1 = vec!["apple".to_string(), "orange".to_string(), "pear".to_string()];
        let tokens2 = vec!["banana".to_string(), "pear".to_string()];
        let tokens3 = vec!["unique3".to_string(), "unique4".to_string()];
        let node_n1 = Node { name: String::from("test"), tokens: tokens_n1 };
        let node_n2 = Node { name: String::from("test"), tokens: tokens_n2 };
        let node1 = Node { name: String::from("test"), tokens: tokens1 };
        let node2 = Node { name: String::from("test"), tokens: tokens2 };
        let node3 = Node { name: String::from("test"), tokens: tokens3 };
        
        let mut indexer = Indexer::new();
        indexer.add_node(node1.clone());
        indexer.add_node(node2.clone());
        indexer.add_node(node3.clone());
        
        // WHEN
        let scores1 = indexer.compute_similarity_scores(&node_n1);
        let scores2 = indexer.compute_similarity_scores(&node_n2);
        
        // THEN
        assert_eq!(scores1.len(), 2);  
        assert_eq!(scores2.len(), 0);  
        
        let node_n1_node1_score = scores1.get(&node1).unwrap();
        let node_n1_node2_score = scores1.get(&node2).unwrap();
        assert!(node_n1_node1_score > node_n1_node2_score);
        
    }
}
