package mediamine.m4_flow;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * Edmonds-Karp: Ford-Fulkerson where augmenting paths are found with BFS (shortest in edge count).
 * MediaMine use: capacity/bandwidth-constrained logistics networks.
 * Time: O(V * E^2), independent of the capacity values. Mutates the network passed in.
 */
public class EdmondsKarp {

    public static long maxFlow(FlowNetwork g, int s, int t) {
        long total = 0;
        int n = g.size();
        while (true) {
            int[] parentEdge = new int[n];
            Arrays.fill(parentEdge, -1);
            boolean[] seen = new boolean[n];
            Queue<Integer> q = new ArrayDeque<>();
            q.add(s);
            seen[s] = true;
            while (!q.isEmpty() && !seen[t]) {
                int u = q.poll();
                for (int id : g.adjacent(u)) {
                    FlowNetwork.Edge e = g.edge(id);
                    if (!seen[e.to] && e.residual() > 0) {
                        seen[e.to] = true;
                        parentEdge[e.to] = id;
                        q.add(e.to);
                    }
                }
            }
            if (!seen[t]) break;

            long bottleneck = Long.MAX_VALUE;
            for (int v = t; v != s; v = g.edge(parentEdge[v]).from) {
                bottleneck = Math.min(bottleneck, g.edge(parentEdge[v]).residual());
            }
            for (int v = t; v != s; v = g.edge(parentEdge[v]).from) g.push(parentEdge[v], bottleneck);
            total += bottleneck;
        }
        return total;
    }
}
