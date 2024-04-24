use super::bipartite_graph::{BipartiteGraph, Edge};
use rand::Rng;

pub type Mapping = Vec<Edge>;

pub struct MetropolisAlgorithm {
    graph: BipartiteGraph,
    beta: f64,
    gamma: f64,
    nb_iterations: usize,
    current_mapping: Mapping,
}

impl MetropolisAlgorithm {

    pub fn new(graph: BipartiteGraph, beta: f64, gamma: f64, nb_iterations: usize) -> MetropolisAlgorithm {
        let current_mapping = graph.edges.clone();
        MetropolisAlgorithm {
            graph,
            beta,
            gamma,
            nb_iterations,
            current_mapping: current_mapping,
        }
    }

    pub fn get_graph(&self) -> &BipartiteGraph {
        &self.graph
    }

    pub fn get_mapping(&self) -> &Mapping {
        &self.current_mapping
    }

    /// Compute the cost of a given matching
    pub fn compute_cost(&self, edges: &Mapping) -> f64 {
        let mut cost:f64 = 0.0;
        for edge in edges {
            cost += edge.value;
        }
        cost *= -self.beta;
        cost /= edges.len() as f64;
        cost = cost.exp();
        cost
    }

    /// Iters through a list of edges. At each step, it returns the edge with a probability of gamma
    pub fn select_edge_from(&self, edges: &Mapping) -> Option<Edge> {
        let mut rng = rand::thread_rng();
        for edge in edges {
            if rng.gen_bool(self.gamma) {
                return Some(edge.clone());
            }
        }
        None
    }

    /// Returns all the edges connected to a given edge
    fn connected_edges(&self, edges: &Vec<Edge>, edge: &Edge) -> Vec<Edge> {
        let mut connected_edges = vec![];
        for e in edges {
            if (e.source == edge.source) ^ (e.target == edge.target){
                connected_edges.push(e.clone());
            }
        }
        connected_edges
    }
    
    /// Removes a list of edges from a list of edges
    fn remove_edges(edges: &mut Vec<Edge>, edges_to_remove: &[Edge]) {
        edges.retain(|e| !edges_to_remove.contains(e));
    }
    
    /// Removes all the edges connected to a given edge from a list of edges
    pub fn remove_all_adjacent_edges(&mut self, edges: &mut Mapping, edge: &Edge){
        let connected_edges = self.connected_edges(edges, edge);
        Self::remove_edges(edges, &connected_edges);
    }
    
    /// Suggest a new mapping from the precendent one 
    pub fn select_new_mapping(&mut self) -> Mapping {
        let mut new_mapping = vec![];
        let mut remaining_edges = self.graph.edges.clone();
        remaining_edges.sort_by(|a, b| a.value.partial_cmp(&b.value).unwrap());
        let to_keep = rand::thread_rng().gen_range(0..self.current_mapping.len());
        println!("To keep: {:?}", to_keep);
        for _ in 0..to_keep {
            println!("Remaining edges: {:?}", remaining_edges);
            let edge = remaining_edges.pop();
            if edge.is_none() {
                break;
            }
            let edge = edge.unwrap();
            new_mapping.push(edge.clone());
            self.remove_all_adjacent_edges(&mut remaining_edges, &edge);
        }
        println!("Wip mapping: {:?}", new_mapping);
        while !remaining_edges.is_empty() {
            let edge = self.select_edge_from(&remaining_edges);
            if edge.is_none() {
                break;
            }
            let edge = edge.unwrap();
            new_mapping.push(edge.clone());
            self.remove_all_adjacent_edges(&mut remaining_edges, &edge);
        }
        new_mapping
    }

    pub fn is_full_mapping(&self, mapping: &Mapping) -> bool {
        let mut nodes = vec![];
        for edge in mapping {
            if nodes.contains(&edge.source) || nodes.contains(&edge.target) {
                return false;
            }
            nodes.push(edge.source.clone());
            nodes.push(edge.target.clone());
        }
        let mut res = true;
        let mut group1 = self.graph.node_group_1.clone();
        let mut group2 = self.graph.node_group_2.clone();
        let mut wanted_nodes = &mut group1;
        wanted_nodes.append(&mut group2);
        for node in wanted_nodes {
            if !nodes.contains(&node) {
                res = false;
            }
        }
        res
    }

    

    pub fn run(&mut self) {
        let mut current_cost = self.compute_cost(&self.current_mapping);
        for _ in 0..self.nb_iterations {
            let new_mapping = self.select_new_mapping();
            let new_cost = self.compute_cost(&new_mapping);
            if new_cost < current_cost && self.is_full_mapping(&new_mapping){
                self.current_mapping = new_mapping;
                current_cost = new_cost;
            }
        }
    }
}