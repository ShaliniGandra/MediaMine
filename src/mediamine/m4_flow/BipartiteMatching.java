package mediamine.m4_flow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Maximum bipartite matching (Kuhn's augmenting-path algorithm).
 * MediaMine use: assign drivers (left side) to delivery tasks (right side) they are eligible for.
 * Time: O(V * E).
 */
public class BipartiteMatching {
    private final int leftSize, rightSize;
    private final List<List<Integer>> adj = new ArrayList<>();
    private final int[] matchRight;              // matchRight[r] = left vertex matched to r, or -1

    public BipartiteMatching(int leftSize, int rightSize) {
        this.leftSize = leftSize;
        this.rightSize = rightSize;
        for (int i = 0; i < leftSize; i++) adj.add(new ArrayList<>());
        this.matchRight = new int[rightSize];
        Arrays.fill(matchRight, -1);
    }

    /** Left vertex l may be matched with right vertex r. */
    public void addEdge(int l, int r) { adj.get(l).add(r); }

    /** Runs the algorithm and returns the size of the maximum matching. */
    public int maxMatching() {
        Arrays.fill(matchRight, -1);
        int result = 0;
        for (int l = 0; l < leftSize; l++) {
            boolean[] visited = new boolean[rightSize];
            if (tryAugment(l, visited)) result++;
        }
        return result;
    }

    private boolean tryAugment(int l, boolean[] visited) {
        for (int r : adj.get(l)) {
            if (visited[r]) continue;
            visited[r] = true;
            if (matchRight[r] == -1 || tryAugment(matchRight[r], visited)) {
                matchRight[r] = l;
                return true;
            }
        }
        return false;
    }

    /** matchOfLeft()[l] = right vertex assigned to l, or -1. Call after maxMatching(). */
    public int[] matchOfLeft() {
        int[] res = new int[leftSize];
        Arrays.fill(res, -1);
        for (int r = 0; r < rightSize; r++) if (matchRight[r] != -1) res[matchRight[r]] = r;
        return res;
    }
}
