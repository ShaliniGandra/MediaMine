package mediamine.m3_dp;

/**
 * Matrix-Chain Multiplication order optimisation.
 * MediaMine use: a sequence of logistics operations (e.g. chained transformations of a demand/route/cost matrix
 * such as A[p x q] * B[q x r] * ...) is evaluated in the cheapest parenthesisation order.
 * Time: O(n^3), Space: O(n^2).
 */
public class MatrixChain {

    public static class Result {
        public final long minCost;
        public final String parenthesization;
        Result(long c, String p) { minCost = c; parenthesization = p; }
        @Override public String toString() { return "minCost=" + minCost + ", order=" + parenthesization; }
    }

    /** dims has length n+1; matrix i has size dims[i] x dims[i+1]. Matrices are labelled A1..An. */
    public static Result solve(int[] dims) {
        return solve(dims, null);
    }

    /** Same as solve(dims) but labels the operations with the given names (e.g. "Demand", "Route", "Cost"). */
    public static Result solve(int[] dims, String[] names) {
        int n = dims.length - 1;
        if (n < 1) return new Result(0, "");
        long[][] m = new long[n][n];
        int[][] split = new int[n][n];

        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                m[i][j] = Long.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    long cost = m[i][k] + m[k + 1][j] + (long) dims[i] * dims[k + 1] * dims[j + 1];
                    if (cost < m[i][j]) { m[i][j] = cost; split[i][j] = k; }
                }
            }
        }
        return new Result(m[0][n - 1], build(split, 0, n - 1, names));
    }

    private static String build(int[][] split, int i, int j, String[] names) {
        if (i == j) return names == null ? "A" + (i + 1) : names[i];
        int k = split[i][j];
        return "(" + build(split, i, k, names) + " x " + build(split, k + 1, j, names) + ")";
    }
}
