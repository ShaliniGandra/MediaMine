package mediamine.m3_dp;

import java.util.List;

/**
 * Levenshtein edit distance (insert, delete, substitute).
 * MediaMine use: correct mistyped delivery addresses by finding the closest known address.
 * Time: O(nm), Space: O(nm) (table kept so the edit script can be recovered).
 */
public class Levenshtein {

    public static int distance(String a, String b) {
        int[][] dp = table(a, b);
        return dp[a.length()][b.length()];
    }

    private static int[][] table(String a, String b) {
        int n = a.length(), m = b.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) dp[i][0] = i;
        for (int j = 0; j <= m; j++) dp[0][j] = j;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
            }
        }
        return dp;
    }

    /** Human-readable list of edit operations turning a into b. */
    public static String editScript(String a, String b) {
        int[][] dp = table(a, b);
        StringBuilder sb = new StringBuilder();
        int i = a.length(), j = b.length();
        java.util.ArrayList<String> ops = new java.util.ArrayList<>();
        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && a.charAt(i - 1) == b.charAt(j - 1) && dp[i][j] == dp[i - 1][j - 1]) {
                i--; j--;
            } else if (i > 0 && j > 0 && dp[i][j] == dp[i - 1][j - 1] + 1) {
                ops.add("replace '" + a.charAt(i - 1) + "' with '" + b.charAt(j - 1) + "' at " + (i - 1));
                i--; j--;
            } else if (i > 0 && dp[i][j] == dp[i - 1][j] + 1) {
                ops.add("delete '" + a.charAt(i - 1) + "' at " + (i - 1));
                i--;
            } else {
                ops.add("insert '" + b.charAt(j - 1) + "' at " + i);
                j--;
            }
        }
        for (int k = ops.size() - 1; k >= 0; k--) {
            sb.append(ops.get(k));
            if (k > 0) sb.append("; ");
        }
        return sb.length() == 0 ? "(no changes)" : sb.toString();
    }

    /** Returns the dictionary entry closest to the input (case-insensitive). */
    public static String closest(String input, List<String> dictionary) {
        String best = null;
        int bestDist = Integer.MAX_VALUE;
        for (String cand : dictionary) {
            int d = distance(input.toLowerCase(), cand.toLowerCase());
            if (d < bestDist) { bestDist = d; best = cand; }
        }
        return best;
    }
}
