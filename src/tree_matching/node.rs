use super::indexer::Tokens;

#[derive(Debug, PartialEq, Eq, Hash)]
pub struct Node {
    pub name: String,
    pub tokens : Tokens,
}

impl Clone for Node {
    fn clone(&self) -> Self {
        Node {
            name: self.name.clone(), // Cloning the inner String
            tokens: self.tokens.clone(), // Cloning the inner Vec<String>
        }
    }
}

impl Node {
    pub fn new(name: &str) -> Node {
        Node {
            name: name.to_string(),
            tokens: vec![],
        }
    }

    pub fn add_token(&mut self, token: &str) {
        self.tokens.push(token.to_string());
    }

    pub fn remove_token(&mut self, token: &str) {
        self.tokens.retain(|t| t != token);
    }

    pub fn remove_all_tokens(&mut self) {
        self.tokens.clear();
    }

    pub fn get_tokens(&self) -> &Vec<String> {
        &self.tokens
    }
}