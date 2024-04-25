#[cfg(test)]
mod tests {
    use pji::tree_matching::node::Node;

    #[test]
    fn test_tokenize() {
        let mut node = Node::new("element1/element2/element3");
        node.tokenize();
        assert_eq!(
            node.get_tokens(),
            &vec!["element1".to_string(), "element2".to_string(), "element3".to_string()]
        );
    }
}
