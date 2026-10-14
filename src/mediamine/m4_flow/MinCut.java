package mediamine.m4_flow;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Minimum s-t cut (max-flow min-cut theorem).
 * MediaMine use: identify the distribution bottleneck - the set of links whose total capacity limits throughput.
 * Works on a copy of the network, so the input is left untouched.
 */
public class MinCut {

    public static class Result {
        public final long value;
        public final List<String> sourceSide = new ArrayList<>();
        public final List<String> sinkSide = new ArrayList<>();
        public final List<String> cutEdges = new ArrayList<>();
        Result(long value) { this.value = value; }
    }

    public static Result find(FlowNetwork original, int s, int t) {
        FlowNetwork g = original.copy();
        long value = Dinic.maxFlow(g, s, t);

        // vertices reachable from s in the residual graph form the source side of the cut
        boolean[] reach = new boolean[g.size()];
        Queue<Integer> q = new ArrayDeque<>();
        q.add(s);
        reach[s] = true;
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int id : g.adjacent(u)) {
                FlowNetwork.Edge e = g.edge(id);
                if (!reach[e.to] && e.residual() > 0) { reach[e.to] = true; q.add(e.to); }
            }
        }

        Result r = new Result(value);
        for (int v = 0; v < g.size(); v++) (reach[v] ? r.sourceSide : r.sinkSide).add(g.name(v));
        for (int id = 0; id < g.edgeCount(); id += 2) {       // original (forward) edges only
            FlowNetwork.Edge e = g.edge(id);
            if (reach[e.from] && !reach[e.to]) {
                r.cutEdges.add(g.name(e.from) + " -> " + g.name(e.to) + " (cap " + e.capacity + ")");
            }
        }
        return r;
    }
}
