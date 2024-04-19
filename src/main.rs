use std::path::Path;
use std::env;
use pji::file_tree::TreeNode;


fn main() {
    let args: Vec<String> = env::args().collect();

    if args.len() != 2 {
        eprintln!("Usage: {} <directory_path>", args[0]);
        std::process::exit(1);
    }

    let dir_path = &args[1];

    let mut tree: TreeNode = TreeNode::build_tree(Path::new(Path::new(dir_path))).unwrap();
    tree.print_tree(0);
}
