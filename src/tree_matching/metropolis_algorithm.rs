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
        MetropolisAlgorithm {
            graph,
            beta,
            gamma,
            nb_iterations,
            current_mapping: vec![],
        }
    }

    pub fn get_graph(&self) -> &BipartiteGraph {
        &self.graph
    }

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

    pub fn select_edge_from(&self, edges: &Mapping) -> Option<Edge> {
        let mut rng = rand::thread_rng();
        for edge in edges {
            if rng.gen_bool(self.gamma) {
                return Some(edge.clone());
            }
        }
        None
    }

    fn connected_edges(&self, edge: &Edge) -> Vec<Edge> {
        let mut connected_edges = vec![];
        for e in &self.graph.edges {
            if e.source == edge.source || e.source == edge.target || e.target == edge.source || e.target == edge.target {
                connected_edges.push(e.clone());
            }
        }
        connected_edges
    }
    
    fn remove_edges(edges: &mut Vec<Edge>, edges_to_remove: &[Edge]) {
        edges.retain(|e| !edges_to_remove.contains(e));
    }
    
    pub fn remove_all_adjacent_edges(&mut self, edge: &Edge) {
        let connected_edges = self.connected_edges(edge);
        Self::remove_edges(&mut self.graph.edges, &connected_edges);
    }
    
    pub fn select_new_matching(&mut self, edges: &mut Mapping) -> Mapping {
        let mut new_matching = vec![];
        let mut remaining_edges = self.graph.edges.clone();
        remaining_edges.sort_by(|a, b| a.value.partial_cmp(&b.value).unwrap());
        let to_keep = rand::thread_rng().gen_range(0..edges.len());
        for _ in 0..to_keep {
            let edge = remaining_edges.pop().unwrap();
            new_matching.push(edge.clone());
            self.remove_all_adjacent_edges(&edge);
        }
        while !remaining_edges.is_empty() {
            let edge = remaining_edges.pop().unwrap();
            new_matching.push(edge.clone());
            self.remove_all_adjacent_edges(&edge);
        }
        self.graph.edges = new_matching.clone();
        new_matching
    }
}