use std::path::Path;
use std::fs;
use std::io::Write;
use std::fs::OpenOptions;
use std::rc::Rc;
use std::cell::RefCell;

#[test]
fn test_build_tree() {
    // GIVEN
    let test_dir = Path::new("./test_dir");
    fs::create_dir_all(&test_dir).unwrap_or_else(|e| {
        if e.kind() == std::io::ErrorKind::AlreadyExists {
            // Directory already exists, no need to create
        } else {
            panic!("Failed to create test_dir: {}", e)
        }
    });

    let file1_path = test_dir.join("file1.txt");
    // Create file1 if it doesn't exist, otherwise open it
    let mut file1 = OpenOptions::new()
        .write(true)
        .create_new(true) // This option prevents overwriting if the file already exists
        .open(&file1_path)
        .unwrap_or_else(|e| {
            if e.kind() == std::io::ErrorKind::AlreadyExists {
                OpenOptions::new()
                    .write(true)
                    .open(&file1_path)
                    .unwrap()
            } else {
                panic!("Failed to create or open file1: {}", e)
            }
        });
    file1.write_all(b"Contenu du fichier 1").unwrap();

    let subdir_path = test_dir.join("subdir");
    fs::create_dir(&subdir_path).unwrap_or_else(|e| {
        if e.kind() == std::io::ErrorKind::AlreadyExists {
            // Directory already exists, no need to create
        } else {
            panic!("Failed to create subdir: {}", e)
        }
    });

    let file2_path = subdir_path.join("file2.txt");
    // Similar approach for file2
    let mut file2 = OpenOptions::new()
        .write(true)
        .create_new(true)
        .open(&file2_path)
        .unwrap_or_else(|e| {
            if e.kind() == std::io::ErrorKind::AlreadyExists {
                OpenOptions::new()
                    .write(true)
                    .open(&file2_path)
                    .unwrap()
            } else {
                panic!("Failed to create or open file2: {}", e)
            }
        });
    file2.write_all(b"Contenu du fichier 2").unwrap();

    // WHEN
    let tree = pji::file_tree::TreeNode::build_tree(&test_dir).unwrap();

    // CLEANUP
    if let Err(e) = fs::remove_file(&file1_path) {
        if e.kind() != std::io::ErrorKind::NotFound {
            panic!("Failed to remove file1: {}", e);
        }
    }
    if let Err(e) = fs::remove_file(&file2_path) {
        if e.kind() != std::io::ErrorKind::NotFound {
            panic!("Failed to remove file2: {}", e);
        }
    }
    if let Err(e) = fs::remove_dir(&subdir_path) {
        if e.kind() != std::io::ErrorKind::NotFound {
            panic!("Failed to remove subdir: {}", e);
        }
    }
    if let Err(e) = fs::remove_dir(&test_dir) {
        if e.kind() != std::io::ErrorKind::NotFound {
            panic!("Failed to remove test_dir: {}", e);
        }
    } 
    // THEN
    assert_eq!(tree.borrow().data, "test_dir");
    assert_eq!(tree.borrow().children.len(), 2);
    let index = tree.borrow().children.iter().position(|child| child.borrow().data == "test_dir/subdir").unwrap();
    let child = &tree.borrow().children[index];
    assert_eq!(child.borrow().children.len(), 1);
    assert_eq!(child.borrow().children[0].borrow().data, "test_dir/subdir/file2.txt");
    assert!(tree.borrow().children.iter().any(|child| child.borrow().data == "test_dir/file1.txt"));
}

#[test]
fn test_remove_root_directory_from_paths() {
    // GIVEN
    let test_dir = Path::new("./test_dir-2");
    fs::create_dir_all(&test_dir).unwrap_or_else(|e| {
        if e.kind() == std::io::ErrorKind::AlreadyExists {
            // Directory already exists, no need to create
        } else {
            panic!("Failed to create test_dir: {}", e)
        }
    });

    let file1_path = test_dir.join("file1.txt");
    // Create file1 if it doesn't exist, otherwise open it
    let mut file1 = OpenOptions::new()
        .write(true)
        .create_new(true) // This option prevents overwriting if the file already exists
        .open(&file1_path)
        .unwrap_or_else(|e| {
            if e.kind() == std::io::ErrorKind::AlreadyExists {
                OpenOptions::new()
                    .write(true)
                    .open(&file1_path)
                    .unwrap()
            } else {
                panic!("Failed to create or open file1: {}", e)
            }
        });
    file1.write_all(b"Contenu du fichier 1").unwrap();

    let subdir_path = test_dir.join("subdir");
    fs::create_dir(&subdir_path).unwrap_or_else(|e| {
        if e.kind() == std::io::ErrorKind::AlreadyExists {
            // Directory already exists, no need to create
        } else {
            panic!("Failed to create subdir: {}", e)
        }
    });

    let file2_path = subdir_path.join("file2.txt");
    // Similar approach for file2
    let mut file2 = OpenOptions::new()
        .write(true)
        .create_new(true)
        .open(&file2_path)
        .unwrap_or_else(|e| {
            if e.kind() == std::io::ErrorKind::AlreadyExists {
                OpenOptions::new()
                    .write(true)
                    .open(&file2_path)
                    .unwrap()
            } else {
                panic!("Failed to create or open file2: {}", e)
            }
        });
    file2.write_all(b"Contenu du fichier 2").unwrap();

    let tree = pji::file_tree::TreeNode::build_tree(&test_dir).unwrap();

    // WHEN
    let new_tree = pji::file_tree::TreeNode::remove_root_directory_from_paths(&tree);

    // CLEANUP
    if let Err(e) = fs::remove_file(&file1_path) {
        if e.kind() != std::io::ErrorKind::NotFound {
            panic!("Failed to remove file1: {}", e);
        }
    }
    if let Err(e) = fs::remove_file(&file2_path) {
        if e.kind() != std::io::ErrorKind::NotFound {
            panic!("Failed to remove file2: {}", e);
        }
    }
    if let Err(e) = fs::remove_dir(&subdir_path) {
        if e.kind() != std::io::ErrorKind::NotFound {
            panic!("Failed to remove subdir: {}", e);
        }
    }
    if let Err(e) = fs::remove_dir(&test_dir) {
        if e.kind() != std::io::ErrorKind::NotFound {
            panic!("Failed to remove test_dir: {}", e);
        }
    }    

    // THEN
    assert_eq!(new_tree.borrow().data, "");
    assert_eq!(new_tree.borrow().children.len(), 2);
    let index = new_tree.borrow().children.iter().position(|child| child.borrow().data == "/subdir").unwrap();
    let child = &new_tree.borrow().children[index];
    assert_eq!(child.borrow().children.len(), 1);
    assert_eq!(child.borrow().children[0].borrow().data, "/subdir/file2.txt");
    assert!(new_tree.borrow().children.iter().any(|child| child.borrow().data == "/file1.txt"));
}

#[test]
fn all_nodes_data() {
    // GIVEN
    let tree_node = Rc::new(RefCell::new(pji::file_tree::TreeNode {
        data: "root".to_string(),
        children: vec![
            Rc::new(RefCell::new(pji::file_tree::TreeNode {
                data: "child1".to_string(),
                children: vec![
                    Rc::new(RefCell::new(pji::file_tree::TreeNode {
                        data: "child1-1".to_string(),
                        children: vec![],
                        parent: None,
                    })),
                    Rc::new(RefCell::new(pji::file_tree::TreeNode {
                        data: "child1-2".to_string(),
                        children: vec![],
                        parent: None,
                    })),
                ],
                parent: None,
            })),
            Rc::new(RefCell::new(pji::file_tree::TreeNode {
                data: "child2".to_string(),
                children: vec![],
                parent: None,
            })),
        ],
        parent: None,
    }));

    //WHEN
    let nodes = pji::file_tree::TreeNode::get_all_nodes_data(&tree_node);

    //THEN
    assert_eq!(nodes.len(), 5);
    assert!(nodes.contains(&String::from("root")));
    assert!(nodes.contains(&String::from("child1")));
    assert!(nodes.contains(&String::from("child1-1")));
    assert!(nodes.contains(&String::from("child1-2")));
    assert!(nodes.contains(&String::from("child2")));
}
