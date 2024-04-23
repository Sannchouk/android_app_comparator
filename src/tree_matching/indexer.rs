use std::collections::HashMap;

use super::node::Node;

pub struct Indexer {
    pub nodes: Vec<Node>,
    pub token_map : HashMap<String, Vec<Node>>,
}

impl Indexer {
    pub fn new() -> Indexer {
        Indexer {
            nodes: vec![],
            token_map : HashMap::new(),
        }
    }

    pub fn add_node(&mut self, node: Node) {
        println!("Adding node {:?}", node);
        self.nodes.push(node.clone());
        for tk in &node.tokens {
            self.token_map.entry(tk.clone()).or_insert(vec![]).push(node.clone());
        }
    }

    pub fn compute_similarity_scores(&self, node: &Node) -> HashMap<&Node, f64> {
        let mut neighbors: HashMap<&Node, f64> = HashMap::new();

        println!("Node tokens: {:?}", node.tokens);
        println!("Token map: {:?}", self.token_map);

        // For each token contained in the token list of the node
        for tk in &node.tokens {
            // If the token is present in the token map
            if let Some(nodes) = self.token_map.get(tk) {
                // For each node in the list of nodes associated with the token
                for n in nodes {
                    // Add the idf of the token to the score of the node
                    *neighbors.entry(&n).or_insert(0.0) += self.compute_idf(&tk);
                }
            }
        }
        neighbors
    }
            

    pub fn compute_idf(&self, token: &str) -> f64 {
        let count = self.token_map.get(token).map_or(0, |v| v.len());
        // Take into account the case where count is 0 
        ((self.nodes.len() as f64) / (count as f64)).ln()
    }
}

pub type Tokens = Vec<String>;