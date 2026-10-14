package mediamine.m2_suffix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Suffix Array built with prefix doubling (O(n log^2 n)).
 * MediaMine use: index shipment descriptions and delivery documents, then answer substring
 * queries by binary search in O(m log n).
 */
public class SuffixArray {
    private final String text;
    private final int[] sa;

    public SuffixArray(String text) {
        this.text = text;
        this.sa = build(text);
    }

    private static int[] build(String s) {
        final int n = s.length();
        Integer[] idx = new Integer[n];
        int[] rank = new int[n];
        int[] tmp = new int[n];
        for (int i = 0; i < n; i++) { idx[i] = i; rank[i] = s.charAt(i); }

        for (int k = 1; ; k <<= 1) {
            final int step = k;
            final int[] r = rank;
            java.util.Comparator<Integer> cmp = (a, b) -> {
                if (r[a] != r[b]) return Integer.compare(r[a], r[b]);
                int ra = a + step < n ? r[a + step] : -1;
                int rb = b + step < n ? r[b + step] : -1;
                return Integer.compare(ra, rb);
            };
            Arrays.sort(idx, cmp);
            tmp[idx[0]] = 0;
            for (int i = 1; i < n; i++) {
                tmp[idx[i]] = tmp[idx[i - 1]] + (cmp.compare(idx[i - 1], idx[i]) < 0 ? 1 : 0);
            }
            System.arraycopy(tmp, 0, rank, 0, n);
            if (n == 0 || rank[idx[n - 1]] == n - 1) break;
        }
        int[] result = new int[n];
        for (int i = 0; i < n; i++) result[i] = idx[i];
        return result;
    }

    public int[] getSuffixArray() { return sa.clone(); }
    public String getText() { return text; }
    public int length() { return sa.length; }

    /** Returns sorted start positions of every occurrence of pattern. */
    public List<Integer> search(String pattern) {
        List<Integer> res = new ArrayList<>();
        int m = pattern.length();
        if (m == 0) return res;
        int lo = lowerBound(pattern);
        for (int i = lo; i < sa.length && text.startsWith(pattern, sa[i]); i++) res.add(sa[i]);
        Collections.sort(res);
        return res;
    }

    public boolean contains(String pattern) {
        int lo = lowerBound(pattern);
        return lo < sa.length && text.startsWith(pattern, sa[lo]);
    }

    /** First suffix index whose first |pattern| chars are >= pattern. */
    private int lowerBound(String pattern) {
        int lo = 0, hi = sa.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            String suf = text.substring(sa[mid], Math.min(text.length(), sa[mid] + pattern.length()));
            if (suf.compareTo(pattern) < 0) lo = mid + 1; else hi = mid;
        }
        return lo;
    }

    /** Naive reference construction used for testing / comparison. */
    public static int[] buildNaive(String s) {
        Integer[] idx = new Integer[s.length()];
        for (int i = 0; i < idx.length; i++) idx[i] = i;
        Arrays.sort(idx, (a, b) -> s.substring(a).compareTo(s.substring(b)));
        int[] r = new int[idx.length];
        for (int i = 0; i < r.length; i++) r[i] = idx[i];
        return r;
    }
}
