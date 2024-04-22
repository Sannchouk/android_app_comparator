#[derive(Debug, PartialEq)]
pub struct Edge {
    pub source: Node,
    pub target: Node,
}

#[derive(Debug, PartialEq)]
pub struct Node {
    pub name: String,
}

pub struct BipartiteGraph {
    pub node_group_1: Vec<Node>,
    pub node_group_2: Vec<Node>,
    pub edges: Vec<Edge>,
}

impl Clone for Node {
    fn clone(&self) -> Self {
        Node {
            name: self.name.clone(), // Cloning the inner String
        }
    }
}

impl Clone for Edge {
    fn clone(&self) -> Self {
        Edge {
            source: self.source.clone(), // Cloning the inner Node
            target: self.target.clone(), // Cloning the inner Node
        }
    }
}

impl BipartiteGraph {

    pub fn new(node_group_1: Vec<Node>, node_group_2: Vec<Node>, edges: Vec<Edge>) -> BipartiteGraph {
        BipartiteGraph {
            node_group_1,
            node_group_2,
            edges,
        }
    }

    pub fn find_node(&self, group: usize, name: &str) -> Option<&Node> {
        let node_group = match group {
            1 => &self.node_group_1,
            2 => &self.node_group_2,
            _ => return None,
        };

        for node in node_group {
            if node.name == name {
                return Some(node);
            }
        }

        None
    }

    pub fn get_group(&self, name: &str) -> Option<usize> {
        if self.find_node(1, name).is_some() {
            return Some(1);
        }
        if self.find_node(2, name).is_some() {
            return Some(2);
        }
        None
    }

    pub fn add_edge(&mut self, edge: Edge) -> Result<(), &'static str> {
        if self.get_group(&edge.source.name) == self.get_group(&edge.target.name) {
            return Err("Both nodes are in the same group");
        }
        self.edges.push(edge);
        Ok(())
    }

    pub fn remove_edge(&mut self, edge: &Edge) {
        self.edges.retain(|e| e != edge);
    }

    pub fn remove_all_adjacent_edges(&mut self, edge: &Edge) {
        self.edges.retain(|e| !(e.source == edge.source) ^ (e.target == edge.target));
    }
}

