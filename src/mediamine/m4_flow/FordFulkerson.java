package mediamine.m4_flow;

/**
 * Ford-Fulkerson maximum flow using DFS to find augmenting paths.
 * MediaMine use: maximum number of parcels that can move from warehouses to destinations.
 * Time: O(E * maxFlow) for integer capacities. Mutates the network passed in.
 */
public class FordFulkerson {

    public static long maxFlow(FlowNetwork g, int s, int t) {
        long total = 0;
        while (true) {
            boolean[] visited = new boolean[g.size()];
            long pushed = dfs(g, s, t, Long.MAX_VALUE, visited);
            if (pushed == 0) break;
            total += pushed;
        }
        return total;
    }

    private static long dfs(FlowNetwork g, int u, int t, long bottleneck, boolean[] visited) {
        if (u == t) return bottleneck;
        visited[u] = true;
        for (int id : g.adjacent(u)) {
            FlowNetwork.Edge e = g.edge(id);
            if (!visited[e.to] && e.residual() > 0) {
                long pushed = dfs(g, e.to, t, Math.min(bottleneck, e.residual()), visited);
                if (pushed > 0) {
                    g.push(id, pushed);
                    return pushed;
                }
            }
        }
        return 0;
    }
}
