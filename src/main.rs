use std::path::Path;
use std::env;
use std::process;
use getopts::Options;
use pji::file_tree::TreeNode;

fn main() {
    let args: Vec<String> = env::args().collect();
    let program = args[0].clone();

    let mut opts = Options::new();
    opts.optopt("", "json", "set output JSON file name", "NAME");

    let matches = match opts.parse(&args[1..]) {
        Ok(m) => m,
        Err(e) => {
            print_usage(&program, &opts);
            process::exit(1);
        }
    };

    if matches.free.len() != 1 {
        print_usage(&program, &opts);
        process::exit(1);
    }

    let dir_path = &matches.free[0];
    let mut tree: TreeNode = TreeNode::build_tree(Path::new(dir_path)).unwrap();
    if matches.opt_present("json") {
        let json_file = matches.opt_str("json").unwrap();
        tree.convert_to_json_file(&json_file).unwrap();
    }
    else {
        tree.print_tree(0);
    }
}

fn print_usage(program: &str, opts: &Options) {
    let brief = format!("Usage: {} [options] <directory_path>", program);
    print!("{}", opts.usage(&brief));
}
