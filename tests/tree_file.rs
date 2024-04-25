use std::path::Path;
use std::fs;
use std::io::Write;
use std::fs::OpenOptions;

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
    assert_eq!(tree.name, "test_dir");
    assert_eq!(tree.children.len(), 2);
    let index = tree.children.iter().position(|child| child.name == "test_dir/subdir").unwrap();
    let child = &tree.children[index];
    assert_eq!(child.children.len(), 1);
    assert_eq!(child.children[0].name, "test_dir/subdir/file2.txt");
    assert!(tree.children.iter().any(|child| child.name == "test_dir/file1.txt"));
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

    let mut tree = pji::file_tree::TreeNode::build_tree(&test_dir).unwrap();

    // WHEN
    let new_tree = tree.remove_root_directory_from_paths();

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
    assert_eq!(new_tree.name, "");
    assert_eq!(new_tree.children.len(), 2);
    let index = new_tree.children.iter().position(|child| child.name == "/subdir").unwrap();
    let child = &new_tree.children[index];
    assert_eq!(child.children.len(), 1);
    assert_eq!(child.children[0].name, "/subdir/file2.txt");
    assert!(new_tree.children.iter().any(|child| child.name == "/file1.txt"));
}

#[test]
fn all_nodes_data() {
    // GIVEN
    let tree_node = pji::file_tree::TreeNode {
        name: "root".to_string(),
        children: vec![
            pji::file_tree::TreeNode {
                name: "child1".to_string(),
                children: vec![
                    pji::file_tree::TreeNode {
                        name: "child1-1".to_string(),
                        children: vec![],
                        parent: None,
                    },
                    pji::file_tree::TreeNode {
                        name: "child1-2".to_string(),
                        children: vec![],
                        parent: None,
                    },
                ],
                parent: None,
            },
            pji::file_tree::TreeNode {
                name: "child2".to_string(),
                children: vec![],
                parent: None,
            },
        ],
        parent: None,
    };

    //WHEN
    let nodes = tree_node.get_all_nodes_data();

    //THEN
    assert_eq!(nodes.len(), 5);
    assert!(nodes.contains(&String::from("root")));
    assert!(nodes.contains(&String::from("child1")));
    assert!(nodes.contains(&String::from("child1-1")));
    assert!(nodes.contains(&String::from("child1-2")));
    assert!(nodes.contains(&String::from("child2")));
}