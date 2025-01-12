package main;

class ArgumentParser {
    public String path = "apks/";
    public boolean needNeo4jFile = false;
    public boolean cluster = false;
    public boolean already = false;
    public Double clusterThreshold = null;

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
                case "-already":
                    already = true;
                    break;
                case "-cluster":
                    if (i + 1 < args.length) {
                        try {
                            clusterThreshold = Double.parseDouble(args[++i]);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid cluster threshold value: " + args[i]);
                        }
                    } else {
                        System.out.println("Missing cluster threshold value after -threshold");
                    }
                    break;
                default:
                    System.out.println("Unknown command: " + args[i]);
                    break;
            }
        }
    }
}
