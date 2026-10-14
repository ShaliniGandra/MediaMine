package mediamine.m3_dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Bitmask DP (Held-Karp) for small-scale route optimisation: shortest tour that starts at the depot (node 0),
 * visits every delivery stop exactly once and returns to the depot.
 * Time: O(n^2 * 2^n), Space: O(n * 2^n). Practical for n <= ~20 stops.
 */
public class BitmaskRouteDP {
    private static final int INF = Integer.MAX_VALUE / 4;

    public static class Result {
        public final int cost;
        public final List<Integer> route;
        Result(int cost, List<Integer> route) { this.cost = cost; this.route = route; }
        @Override public String toString() { return "cost=" + cost + ", route=" + route; }
    }

    public static Result solve(int[][] dist) {
        int n = dist.length;
        if (n == 0) return new Result(0, new ArrayList<>());
        if (n == 1) return new Result(0, new ArrayList<>(Arrays.asList(0, 0)));
        int full = 1 << n;

        int[][] dp = new int[full][n];
        int[][] parent = new int[full][n];
        for (int[] row : dp) Arrays.fill(row, INF);
        for (int[] row : parent) Arrays.fill(row, -1);
        dp[1][0] = 0;                                  // only depot visited, standing at depot

        for (int mask = 1; mask < full; mask += 2) {   // masks that include depot (bit 0)
            for (int last = 0; last < n; last++) {
                if ((mask & (1 << last)) == 0 || dp[mask][last] >= INF) continue;
                for (int nxt = 1; nxt < n; nxt++) {
                    if ((mask & (1 << nxt)) != 0) continue;
                    int nm = mask | (1 << nxt);
                    int cand = dp[mask][last] + dist[last][nxt];
                    if (cand < dp[nm][nxt]) { dp[nm][nxt] = cand; parent[nm][nxt] = last; }
                }
            }
        }

        int best = INF, bestLast = -1;
        for (int last = 1; last < n; last++) {
            int c = dp[full - 1][last] + dist[last][0];
            if (dp[full - 1][last] < INF && c < best) { best = c; bestLast = last; }
        }

        List<Integer> route = new ArrayList<>();
        int mask = full - 1, cur = bestLast;
        route.add(0);                                  // return to depot (end of route)
        while (cur != -1) {
            route.add(cur);
            int p = parent[mask][cur];
            mask ^= (1 << cur);
            cur = p;
        }
        java.util.Collections.reverse(route);          // starts at depot 0, ends at depot 0
        return new Result(best, route);
    }
}
