
use std::fs;
use std::path::Path;
use std::io;

// Structure d'un nœud de l'arbre
#[derive(Debug)]
pub struct TreeNode {
    pub name: String,
    pub children: Vec<TreeNode>,
}

impl TreeNode {
    pub fn new(name: &str) -> TreeNode {
        TreeNode {
            name: name.to_string(),
            children: Vec::new(),
        }
    }

    // Fonction pour construire l'arbre à partir d'une arborescence de fichiers
    pub fn build_tree(root_path: &Path) -> io::Result<TreeNode> {
        let root_path = root_path.strip_prefix("./").unwrap_or(root_path);
        let mut root_node = TreeNode {
            name: root_path.to_string_lossy().to_string(),
            children: Vec::new(),
        };

        if root_path.is_dir() {
            for entry in fs::read_dir(root_path)? {
                let entry = entry?;
                let path = entry.path()
                    .strip_prefix("./")
                    .unwrap_or(&entry.path())
                    .to_path_buf();
                let child_node = Self::build_tree(&path)?;
                root_node.children.push(child_node);
            }
        }

        Ok(root_node)
    }

    pub fn print_tree(&mut self, depth: usize) {
        println!("{:indent$}{}", "", self.name, indent = depth * 2);
        for child in &mut self.children {
            child.print_tree(depth + 1);
        }
    }
}


