use super::bipartite_graph::{BipartiteGraph, Edge};
use rand::Rng;

pub type Matching = Vec<Edge>;

pub struct MetropolisAlgorithm {
    graph: BipartiteGraph,
    beta: f64,
    gamma: f64,
    nb_iterations: usize,
    current_matching: Matching,
}

impl MetropolisAlgorithm {

    pub fn new(graph: BipartiteGraph, beta: f64, gamma: f64, nb_iterations: usize) -> MetropolisAlgorithm {
        let current_matching = graph.edges.clone();
        MetropolisAlgorithm {
            graph,
            beta,
            gamma,
            nb_iterations,
            current_matching: current_matching,
        }
    }

    pub fn get_graph(&self) -> &BipartiteGraph {
        &self.graph
    }

    pub fn get_matching(&self) -> &Matching {
        &self.current_matching
    }

    /// Compute the cost of a given matching
    pub fn compute_cost(&self, edges: &Matching) -> f64 {
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
    pub fn select_edge_from(&self, edges: &Matching) -> Option<Edge> {
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
            if (e.source == edge.source) || (e.target == edge.target){
                // We add the edge to the list of connected edges
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
    pub fn remove_edge_and_adjacent_ones(&mut self, edges: &mut Matching, edge: &Edge){
        let connected_edges = self.connected_edges(edges, edge);
        Self::remove_edges(edges, &connected_edges);
    }
    
    /// Suggest a new matching from the precendent one 
    pub fn select_new_matching(&mut self) -> Matching {
        if self.current_matching.len() == 0 {
            return vec![];
        }
        let mut new_matching = vec![];
        let mut remaining_edges = self.graph.edges.clone();
        remaining_edges.sort_by(|a, b| a.value.partial_cmp(&b.value).unwrap());
        let to_keep = rand::thread_rng().gen_range(0..self.current_matching.len());
        for _ in 0..to_keep {
            let edge = remaining_edges.pop();
            if edge.is_none() {
                break;
            }
            let edge = edge.unwrap();
            new_matching.push(edge.clone());
            self.remove_edge_and_adjacent_ones(&mut remaining_edges, &edge);
        }
        while !remaining_edges.is_empty() {
            let mut edge = self.select_edge_from(&remaining_edges);
            loop {
                if edge.is_some() {
                    break;
                }
                edge = self.select_edge_from(&remaining_edges);
            }
            let edge = edge.unwrap();
            new_matching.push(edge.clone());
            self.remove_edge_and_adjacent_ones(&mut remaining_edges, &edge);
        }
        new_matching
    }

    pub fn run(&mut self) {
        let mut current_cost = self.compute_cost(&self.current_matching);
        for _ in 0..self.nb_iterations {
            let new_matching = self.select_new_matching();
            let new_cost = self.compute_cost(&new_matching);
            if new_cost < current_cost {
                self.current_matching = new_matching;
                current_cost = new_cost;
            }
        }
    }
}