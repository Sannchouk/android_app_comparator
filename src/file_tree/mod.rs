use std::fs;
use std::fs::File;
use std::io::Write;
use std::path::Path;
use std::io;
use serde::{Serialize, Deserialize};
use serde_json;

// Structure d'un nœud de l'arbre
#[derive(Debug, Serialize, Deserialize)]
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

    pub fn clone (&self) -> TreeNode {
        let mut new_children = Vec::new();
        for child in &self.children {
            new_children.push(child.clone());
        }

        TreeNode {
            name: self.name.clone(),
            children: new_children,
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
        for child in &mut self.children {
            child.print_tree(depth + 1);
        }
    }

    pub fn remove_root_directory_from_paths(&mut self) -> TreeNode {
        let mut new_tree = self.clone();
        let prefix = self.name.split("/").next().unwrap();
        new_tree.remove_root_directory_from_paths_rec(prefix)
    }

    fn remove_root_directory_from_paths_rec(&mut self, prefix: &str) -> TreeNode {
        let mut new_children = Vec::new();
        for child in &mut self.children {
            let new_child = child.remove_root_directory_from_paths_rec(prefix);
            new_children.push(new_child);
        }

        TreeNode {
            name: self.name.replace(prefix, ""),
            children: new_children,
        }
    }

    pub fn convert_to_json_file(&mut self, file_path: &str) -> io::Result<()> {
        let json_data = serde_json::to_string_pretty(self)?;
        let mut file = File::create(file_path)?;
        file.write_all(json_data.as_bytes())?;
        Ok(())
    }

    pub fn get_all_nodes_data(&self) -> Vec<String> {
        let mut nodes = Vec::new();
        nodes.push(self.name.clone());
        for child in &self.children {
            nodes.append(&mut child.get_all_nodes_data());
        }
        nodes
    }
}


