package matching.computers.names;

public class LevenshteinNameDistanceComputer implements NameDistanceComputer {

    public double computeDistanceBetweenTwoNames(String name1, String name2) {
        int m = name1.length();
        int n = name2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int cost = (name1.charAt(i - 1) == name2.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(
                                dp[i - 1][j] + 1,
                                dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + cost);
            }
        }

        return dp[m][n];
    }
}
