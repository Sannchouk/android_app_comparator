use pji::tree_matching::node::Node;
use pji::tree_matching::indexer::Indexer;

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_add_node() {
        let mut indexer = Indexer::new();
        let node1 = Node {
            name : "test".to_string(),
            tokens: vec!["apple".to_string(), "banana".to_string()],
        };
        let node2 = Node {
            name : "test".to_string(),
            tokens: vec!["banana".to_string(), "orange".to_string()],
        };

        indexer.add_node(node1.clone(), 1);
        indexer.add_node(node2.clone(), 2);

        assert_eq!(indexer.group1.len(), 1);
        assert_eq!(indexer.group2.len(), 1);
        assert_eq!(indexer.token_map.len(), 3); 
    }

    #[test]
    fn test_compute_similarity_scores() {
        let mut indexer = Indexer::new();
        let node1 = Node {
            name : "test1".to_string(),
            tokens: vec!["apple".to_string(), "banana".to_string()],
        };
        let node2 = Node {
            name : "test2".to_string(),
            tokens: vec!["banana".to_string(), "orange".to_string()],
        };
        let node3 = Node {
            name : "test3".to_string(),
            tokens: vec!["apple".to_string(), "banana".to_string()],
        };
        indexer.add_node(node1.clone(), 1);
        indexer.add_node(node2, 2);
        indexer.add_node(node3.clone(), 2);

        let similarity_scores = indexer.compute_similarity_scores();
        assert_eq!(similarity_scores.len(), 1);
        assert_eq!(similarity_scores.get(&node1).unwrap().len(), 1);
        assert!(similarity_scores.get(&node1).unwrap().contains_key(&node3));
    }
}
