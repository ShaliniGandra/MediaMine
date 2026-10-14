package mediamine.m3_dp;

/**
 * Optimal Binary Search Tree.
 * MediaMine use: build a lookup tree over shipment keys so that the most frequently accessed
 * shipments sit near the root and the expected search cost is minimal.
 * Keys must be given in sorted order. Time: O(n^3), Space: O(n^2).
 */
public class OptimalBST {

    public static class Node {
        public final String key;
        public Node left, right;
        Node(String key) { this.key = key; }
    }

    private final String[] keys;
    private final int[] freq;
    private final long[][] cost;
    private final int[][] root;
    private final int n;

    public OptimalBST(String[] sortedKeys, int[] frequencies) {
        this.keys = sortedKeys;
        this.freq = frequencies;
        this.n = sortedKeys.length;
        this.cost = new long[n + 2][n + 2];
        this.root = new int[n + 2][n + 2];
        compute();
    }

    private void compute() {
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) prefix[i + 1] = prefix[i] + freq[i];

        // cost[i][j] for keys i..j (1-indexed); cost[i][i-1] = 0
        for (int i = 1; i <= n; i++) { cost[i][i] = freq[i - 1]; root[i][i] = i; }
        for (int len = 2; len <= n; len++) {
            for (int i = 1; i + len - 1 <= n; i++) {
                int j = i + len - 1;
                cost[i][j] = Long.MAX_VALUE;
                long sum = prefix[j] - prefix[i - 1];
                for (int r = i; r <= j; r++) {
                    long left = r > i ? cost[i][r - 1] : 0;
                    long right = r < j ? cost[r + 1][j] : 0;
                    long c = left + right + sum;
                    if (c < cost[i][j]) { cost[i][j] = c; root[i][j] = r; }
                }
            }
        }
    }

    /** Minimum expected search cost (weighted by the given frequencies). */
    public long minCost() { return n == 0 ? 0 : cost[1][n]; }

    public Node buildTree() { return n == 0 ? null : build(1, n); }

    private Node build(int i, int j) {
        if (i > j) return null;
        int r = root[i][j];
        Node node = new Node(keys[r - 1]);
        node.left = build(i, r - 1);
        node.right = build(r + 1, j);
        return node;
    }

    /** Sideways text drawing of the tree (root at the left). */
    public static String print(Node node) {
        StringBuilder sb = new StringBuilder();
        print(node, 0, sb);
        return sb.toString();
    }

    private static void print(Node node, int depth, StringBuilder sb) {
        if (node == null) return;
        print(node.right, depth + 1, sb);
        for (int i = 0; i < depth; i++) sb.append("      ");
        sb.append(node.key).append('\n');
        print(node.left, depth + 1, sb);
    }
}
