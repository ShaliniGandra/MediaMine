package mediamine.m4_flow;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * Dinic's algorithm: BFS builds a level graph, DFS sends blocking flows along it.
 * MediaMine use: large distribution networks.
 * Time: O(V^2 * E) in general, O(E * sqrt(V)) on unit-capacity bipartite networks. Mutates the network passed in.
 */
public class Dinic {
    private final FlowNetwork g;
    private final int[] level;
    private final int[] iter;

    public Dinic(FlowNetwork g) {
        this.g = g;
        this.level = new int[g.size()];
        this.iter = new int[g.size()];
    }

    public static long maxFlow(FlowNetwork g, int s, int t) {
        return new Dinic(g).run(s, t);
    }

    public long run(int s, int t) {
        long total = 0;
        while (bfs(s, t)) {
            Arrays.fill(iter, 0);
            long f;
            while ((f = dfs(s, t, Long.MAX_VALUE)) > 0) total += f;
        }
        return total;
    }

    private boolean bfs(int s, int t) {
        Arrays.fill(level, -1);
        level[s] = 0;
        Queue<Integer> q = new ArrayDeque<>();
        q.add(s);
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int id : g.adjacent(u)) {
                FlowNetwork.Edge e = g.edge(id);
                if (level[e.to] < 0 && e.residual() > 0) {
                    level[e.to] = level[u] + 1;
                    q.add(e.to);
                }
            }
        }
        return level[t] >= 0;
    }

    private long dfs(int u, int t, long limit) {
        if (u == t) return limit;
        for (; iter[u] < g.adjacent(u).size(); iter[u]++) {
            int id = g.adjacent(u).get(iter[u]);
            FlowNetwork.Edge e = g.edge(id);
            if (e.residual() > 0 && level[e.to] == level[u] + 1) {
                long pushed = dfs(e.to, t, Math.min(limit, e.residual()));
                if (pushed > 0) {
                    g.push(id, pushed);
                    return pushed;
                }
            }
        }
        return 0;
    }
}
