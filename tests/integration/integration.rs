#[cfg(test)]
mod tests {
    #[test]
    fn test_app() {
        let apk1 = Path::new("resources/lichess-apk");
        let apk2 = Path::new("resources/chesscom-apk");
        let tree1 = Tree::build_tree(apk1);
        let tree2 = Tree::build_tree(apk2);
        let graph1 = BipartiteGraph::new_from_tree(tree1);
        
    }
}
