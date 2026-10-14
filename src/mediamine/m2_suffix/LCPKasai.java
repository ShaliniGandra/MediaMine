package mediamine.m2_suffix;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * LCP array via Kasai's algorithm (O(n)).
 * lcp[i] = length of the longest common prefix of suffixes sa[i-1] and sa[i]; lcp[0] = 0.
 * MediaMine use: detect repeated addresses and repeated shipment descriptions.
 */
public class LCPKasai {

    public static int[] buildLCP(String s, int[] sa) {
        int n = s.length();
        int[] rank = new int[n];
        int[] lcp = new int[n];
        for (int i = 0; i < n; i++) rank[sa[i]] = i;
        int h = 0;
        for (int i = 0; i < n; i++) {
            if (rank[i] > 0) {
                int j = sa[rank[i] - 1];
                while (i + h < n && j + h < n && s.charAt(i + h) == s.charAt(j + h)) h++;
                lcp[rank[i]] = h;
                if (h > 0) h--;
            } else {
                h = 0;
            }
        }
        return lcp;
    }

    /** Longest substring that occurs at least twice. */
    public static String longestRepeatedSubstring(String s, int[] sa, int[] lcp) {
        int best = 0, pos = 0;
        for (int i = 1; i < lcp.length; i++) {
            if (lcp[i] > best) { best = lcp[i]; pos = sa[i]; }
        }
        return s.substring(pos, pos + best);
    }

    /** Number of distinct substrings = n(n+1)/2 - sum(lcp). */
    public static long countDistinctSubstrings(String s, int[] lcp) {
        long n = s.length();
        long total = n * (n + 1) / 2;
        for (int v : lcp) total -= v;
        return total;
    }

    /**
     * Maximal repeated substrings of at least minLen characters (fragments that are contained in a longer
     * repeated substring are dropped). Use with addresses joined by a separator to find duplicates.
     */
    public static List<String> repeatedSubstrings(String s, int[] sa, int[] lcp, int minLen) {
        Set<String> set = new LinkedHashSet<>();
        for (int i = 1; i < lcp.length; i++) {
            if (lcp[i] >= minLen) set.add(s.substring(sa[i], sa[i] + lcp[i]));
        }
        List<String> maximal = new ArrayList<>();
        for (String a : set) {
            boolean contained = false;
            for (String b : set) {
                if (b.length() > a.length() && b.contains(a)) { contained = true; break; }
            }
            if (!contained) maximal.add(a);
        }
        return maximal;
    }
}
