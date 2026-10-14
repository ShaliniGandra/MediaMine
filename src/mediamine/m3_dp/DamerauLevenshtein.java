package mediamine.m3_dp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Damerau-Levenshtein distance (insert, delete, substitute, and transposition of adjacent characters).
 * This is the unrestricted (true) version, not the "optimal string alignment" shortcut.
 * MediaMine use: address correction where typos like "Raod" for "Road" count as a single edit.
 */
public class DamerauLevenshtein {

    public static int distance(String a, String b) {
        int n = a.length(), m = b.length();
        int maxDist = n + m;
        Map<Character, Integer> lastRow = new HashMap<>();   // last row in a where each char was seen

        int[][] d = new int[n + 2][m + 2];
        d[0][0] = maxDist;
        for (int i = 0; i <= n; i++) { d[i + 1][0] = maxDist; d[i + 1][1] = i; }
        for (int j = 0; j <= m; j++) { d[0][j + 1] = maxDist; d[1][j + 1] = j; }

        for (int i = 1; i <= n; i++) {
            int lastMatchCol = 0;
            for (int j = 1; j <= m; j++) {
                int i1 = lastRow.getOrDefault(b.charAt(j - 1), 0);
                int j1 = lastMatchCol;
                int cost = 1;
                if (a.charAt(i - 1) == b.charAt(j - 1)) { cost = 0; lastMatchCol = j; }
                d[i + 1][j + 1] = Math.min(
                        Math.min(d[i][j] + cost, d[i + 1][j] + 1),
                        Math.min(d[i][j + 1] + 1, d[i1][j1] + (i - i1 - 1) + 1 + (j - j1 - 1)));
            }
            lastRow.put(a.charAt(i - 1), i);
        }
        return d[n + 1][m + 1];
    }

    /** Closest dictionary entry using Damerau-Levenshtein distance (case-insensitive). */
    public static String closest(String input, List<String> dictionary) {
        String best = null;
        int bestDist = Integer.MAX_VALUE;
        for (String cand : dictionary) {
            int dist = distance(input.toLowerCase(), cand.toLowerCase());
            if (dist < bestDist) { bestDist = dist; best = cand; }
        }
        return best;
    }
}
