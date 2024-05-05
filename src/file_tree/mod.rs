use std::fs;
use std::fs::File;
use std::io::{self, Write};
use std::path::Path;
use std::rc::Rc;
use std::cell::RefCell;

// Structure d'un nœud de l'arbre
#[derive(Debug)]
pub struct TreeNode<T> {
    pub data: T,
    pub parent: Option<Rc<RefCell<TreeNode<T>>>>,
    pub children: Vec<Rc<RefCell<TreeNode<T>>>>,
}

impl<T> TreeNode<T> {
    pub fn new(data: T) -> Rc<RefCell<TreeNode<T>>> {
        Rc::new(RefCell::new(TreeNode {
            data,
            parent: None,
            children: Vec::new(),
        }))
    }

    pub fn clone(&self) -> Rc<RefCell<TreeNode<T>>> where T: Clone {
        let mut new_children = Vec::new();
        for child in &self.children {
            new_children.push(Rc::clone(child));
        }

        let cloned_node = TreeNode {
            data: self.data.clone(),
            parent: None,
            children: new_children,
        };

        Rc::new(RefCell::new(cloned_node))
    }

    // Fonction pour construire l'arbre à partir d'une arborescence de fichiers
    pub fn build_tree(root_path: &Path) -> io::Result<Rc<RefCell<TreeNode<String>>>> {
        let root_path = root_path.strip_prefix("./").unwrap_or(root_path);
        let mut root_node = TreeNode::<String>::new(root_path.to_string_lossy().to_string());

        if root_path.is_dir() {
            for entry in fs::read_dir(root_path)? {
                let entry = entry?;
                let path = entry.path()
                    .strip_prefix("./")
                    .unwrap_or(&entry.path())
                    .to_path_buf();
                let child_node = TreeNode::<String>::build_tree(&path)?;
                child_node.borrow_mut().parent = Some(Rc::clone(&root_node));
                root_node.borrow_mut().children.push(Rc::clone(&child_node));
            }
        }
        Ok(root_node)
    }

    pub fn print_tree(root_node: &Rc<RefCell<TreeNode<String>>>, depth: usize) {
        let node = root_node.borrow();
        println!("{:indent$}{}", "", node.data, indent = depth * 2);
        for child in &node.children {
            TreeNode::<String>::print_tree(child, depth + 1);
        }
    }

    pub fn remove_root_directory_from_paths(root_node: &Rc<RefCell<TreeNode<String>>>) -> Rc<RefCell<TreeNode<String>>> {
        let binding = root_node.borrow();   
        let prefix = binding.data.split("/").next().unwrap();
        let mut new_node = root_node.clone();
        TreeNode::<String>::remove_root_directory_from_paths_rec(&mut new_node, prefix);
        new_node
    }

    fn remove_root_directory_from_paths_rec(root_node: &mut Rc<RefCell<TreeNode<String>>>, prefix: &str) {
        let mut node = root_node.borrow_mut();
        node.data = node.data.replace(prefix, "");
        for child in &mut node.children {
            TreeNode::<String>::remove_root_directory_from_paths_rec(child, prefix);
        }
    }

    pub fn get_all_nodes_data(root_node: &Rc<RefCell<TreeNode<String>>>) -> Vec<String> {
        let mut nodes = Vec::new();
        let node = root_node.borrow();
        nodes.push(node.data.clone());
        for child in &node.children {
            nodes.append(&mut TreeNode::<String>::get_all_nodes_data(child));
        }
        nodes
    }
}
