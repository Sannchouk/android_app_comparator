use super::bipartite_graph::{BipartiteGraph, Edge};
use rand::Rng;

pub type Mapping = Vec<Edge>;

pub struct MetropolisAlgorithm {
    graph: BipartiteGraph,
    beta: f64,
    gamma: f64,
    nb_iterations: usize,
}

impl MetropolisAlgorithm {

    pub fn new(graph: BipartiteGraph, beta: f64, gamma: f64, nb_iterations: usize) -> MetropolisAlgorithm {
        MetropolisAlgorithm {
            graph,
            beta,
            gamma,
            nb_iterations,
        }
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
}