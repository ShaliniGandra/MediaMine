package mediamine.m4_flow;

import java.util.ArrayList;
import java.util.List;

/**
 * Directed flow network stored as a residual graph.
 * Every addEdge() creates a forward edge (index 2k) and a reverse edge (index 2k+1), so the reverse of
 * edge e is always e ^ 1.
 */
public class FlowNetwork {

    public static class Edge {
        public final int from, to;
        public final long capacity;
        public long flow;
        Edge(int from, int to, long capacity) { this.from = from; this.to = to; this.capacity = capacity; }
        public long residual() { return capacity - flow; }
    }

    private final int n;
    private final List<Edge> edges = new ArrayList<>();
    private final List<List<Integer>> adj = new ArrayList<>();
    private final String[] names;

    public FlowNetwork(int n) {
        this.n = n;
        this.names = new String[n];
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    }

    public int size() { return n; }
    public void setName(int v, String name) { names[v] = name; }
    public String name(int v) { return names[v] != null ? names[v] : "v" + v; }

    /** Adds a directed edge u -> v with the given capacity; returns the forward edge index. */
    public int addEdge(int u, int v, long capacity) {
        int id = edges.size();
        edges.add(new Edge(u, v, capacity));
        edges.add(new Edge(v, u, 0));
        adj.get(u).add(id);
        adj.get(v).add(id + 1);
        return id;
    }

    public List<Integer> adjacent(int u) { return adj.get(u); }
    public Edge edge(int id) { return edges.get(id); }
    public int edgeCount() { return edges.size(); }

    /** Pushes delta units along edge id (and -delta on its reverse). */
    public void push(int id, long delta) {
        edges.get(id).flow += delta;
        edges.get(id ^ 1).flow -= delta;
    }

    /** Sets all flows back to zero. */
    public void reset() { for (Edge e : edges) e.flow = 0; }

    /** Deep copy with zero flow. */
    public FlowNetwork copy() {
        FlowNetwork c = new FlowNetwork(n);
        System.arraycopy(names, 0, c.names, 0, n);
        for (int i = 0; i < edges.size(); i += 2) {
            Edge e = edges.get(i);
            c.addEdge(e.from, e.to, e.capacity);
        }
        return c;
    }

    /** Human-readable flow on every original edge that carries flow. */
    public String flowSummary() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < edges.size(); i += 2) {
            Edge e = edges.get(i);
            if (e.flow > 0) {
                sb.append("    ").append(name(e.from)).append(" -> ").append(name(e.to))
                  .append("  flow ").append(e.flow).append('/').append(e.capacity).append('\n');
            }
        }
        return sb.toString();
    }
}
