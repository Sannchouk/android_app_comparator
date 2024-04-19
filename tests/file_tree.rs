use std::path::Path;
use std::fs::{self, File};
use std::io::Write;

#[test]
fn test_build_tree() {
    //GIVEN
    let test_dir = Path::new("./test_dir");
    fs::create_dir_all(&test_dir).unwrap();

    let file1_path = test_dir.join("file1.txt");
    let mut file1 = File::create(&file1_path).unwrap();
    file1.write_all(b"Contenu du fichier 1").unwrap();

    let subdir_path = test_dir.join("subdir");
    fs::create_dir(&subdir_path).unwrap();

    let file2_path = subdir_path.join("file2.txt");
    let mut file2 = File::create(&file2_path).unwrap();
    file2.write_all(b"Contenu du fichier 2").unwrap();

    // WHEN
    let tree = pji::file_tree::TreeNode::build_tree(&test_dir).unwrap();

    // CLEANUP
    fs::remove_file(file1_path).unwrap();
    fs::remove_file(file2_path).unwrap();
    fs::remove_dir(subdir_path).unwrap();
    fs::remove_dir(test_dir).unwrap();

    // THEN
    assert_eq!(tree.name, "test_dir");
    assert_eq!(tree.children.len(), 2);
    assert_eq!(tree.children[0].name, "test_dir/subdir");
    assert_eq!(tree.children[1].name, "test_dir/file1.txt");
    assert_eq!(tree.children[0].children.len(), 1);
    assert_eq!(tree.children[0].children[0].name, "test_dir/subdir/file2.txt");
}