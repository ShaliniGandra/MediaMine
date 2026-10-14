package mediamine.m1_strings;

import java.util.ArrayList;
import java.util.List;

/**
 * Knuth-Morris-Pratt string matching.
 * MediaMine use: search shipment IDs and delivery keywords inside documents.
 * Time: O(n + m), Space: O(m).
 */
public class KMP {

    /** Builds the longest-proper-prefix-which-is-also-suffix (failure) table. */
    public static int[] buildLPS(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        int len = 0;
        for (int i = 1; i < m; ) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                lps[i++] = ++len;
            } else if (len > 0) {
                len = lps[len - 1];
            } else {
                lps[i++] = 0;
            }
        }
        return lps;
    }

    /** Returns the start indices of every occurrence of pattern in text. */
    public static List<Integer> search(String text, String pattern) {
        List<Integer> result = new ArrayList<>();
        int n = text.length(), m = pattern.length();
        if (m == 0 || m > n) return result;
        int[] lps = buildLPS(pattern);
        int j = 0;
        for (int i = 0; i < n; i++) {
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) j = lps[j - 1];
            if (text.charAt(i) == pattern.charAt(j)) j++;
            if (j == m) {
                result.add(i - m + 1);
                j = lps[j - 1];
            }
        }
        return result;
    }
}
