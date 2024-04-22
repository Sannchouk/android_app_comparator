pub struct Edge {
    pub source: Option<usize>,
    pub target: Option<usize>,
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
}

