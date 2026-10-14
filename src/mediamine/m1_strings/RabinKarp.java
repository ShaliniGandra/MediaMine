package mediamine.m1_strings;

import java.util.ArrayList;
import java.util.List;

/**
 * Rabin-Karp rolling-hash string search.
 * MediaMine use: fast search for package codes; easily extended to many patterns of equal length.
 * Expected time: O(n + m), worst case O(nm) with many collisions (matches are verified).
 */
public class RabinKarp {
    private static final long MOD = 1_000_000_007L;
    private static final long BASE = 256L;

    public static List<Integer> search(String text, String pattern) {
        List<Integer> result = new ArrayList<>();
        int n = text.length(), m = pattern.length();
        if (m == 0 || m > n) return result;

        long highPower = 1;                       // BASE^(m-1) mod MOD
        for (int i = 1; i < m; i++) highPower = (highPower * BASE) % MOD;

        long patHash = 0, winHash = 0;
        for (int i = 0; i < m; i++) {
            patHash = (patHash * BASE + pattern.charAt(i)) % MOD;
            winHash = (winHash * BASE + text.charAt(i)) % MOD;
        }

        for (int i = 0; i + m <= n; i++) {
            if (patHash == winHash && text.regionMatches(i, pattern, 0, m)) result.add(i);
            if (i + m < n) {
                winHash = (winHash - text.charAt(i) * highPower % MOD + MOD) % MOD;
                winHash = (winHash * BASE + text.charAt(i + m)) % MOD;
            }
        }
        return result;
    }

    /** Searches for several equal-length package codes in one pass using a hash lookup. */
    public static List<String> findAnyCode(String text, List<String> codes) {
        List<String> found = new ArrayList<>();
        for (String c : codes) {
            if (!search(text, c).isEmpty()) found.add(c);
        }
        return found;
    }
}
