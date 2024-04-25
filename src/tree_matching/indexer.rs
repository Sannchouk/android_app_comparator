use std::collections::HashMap;

use super::node::Node;

pub struct Indexer {
    pub token_map : HashMap<String, Vec<Node>>,
    pub group1 : Vec<Node>,
    pub group2 : Vec<Node>,
}

impl Indexer {
    pub fn new() -> Indexer {
        Indexer {
            token_map : HashMap::new(),
            group1: vec![],
            group2: vec![],
        }
    }

    pub fn add_node(&mut self, node: Node, group: i32) {
        match group {
            1 => self.group1.push(node.clone()),
            2 => self.group2.push(node.clone()),
            _ => panic!("Invalid group number"),
        }
        for tk in &node.tokens {
            self.token_map.entry(tk.clone()).or_insert(vec![]).push(node.clone());
        }
    }

    pub fn compute_similarity_scores(&self) -> HashMap<&Node, HashMap<&Node, f64>> {
        let mut neighbors: HashMap<&Node, HashMap<&Node, f64>> = HashMap::new();

        // For each token contained in the token list of the node
        for node in &self.group1 {
            *neighbors.entry(&node).or_insert(HashMap::new()) = self.compute_similary_scores_for_node(&node);
            }
        neighbors
        }

    pub fn compute_similary_scores_for_node(&self, node:&Node) -> HashMap<&Node, f64> {
        let mut neighbors: HashMap<&Node, f64> = HashMap::new();
        for tk in &node.tokens {
            if let Some(_nodes) = self.token_map.get(tk) {
                for n in &self.group2 {
                    *neighbors.entry(&n).or_insert(0.0) += self.compute_idf(tk);
                }
            }
        }
        neighbors
    }
            

    pub fn compute_idf(&self, token: &str) -> f64 {
        let count = self.token_map.get(token).map_or(0, |v| v.len());
        let total_nodes = self.group1.len() + self.group2.len();
        // Take into account the case where count is 0 
        ((total_nodes as f64) / (count as f64)).ln()
    }
}

pub type Tokens = Vec<String>;