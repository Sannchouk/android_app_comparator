package main;

class ArgumentParser {
    public String path = "apks/";
    public boolean needNeo4jFile = false;
    public boolean cluster = false;
    public boolean already = false;

    public void parseArguments(String[] args) {
        if (args.length > 0 && !args[0].startsWith("-")) {
            path = args[0];
        }

        int startIndex = path.equals("apks/") ? 0 : 1;

        for (int i = startIndex; i < args.length; i++) {
            switch (args[i]) {
                case "-neo4j":
                    needNeo4jFile = true;
                    break;
                case "-cluster":
                    cluster = true;
                    break;
                case "-already":
                    already = true;
                    break;
                default:
                    System.out.println("Unknown command: " + args[i]);
                    break;
            }
        }
    }
}
