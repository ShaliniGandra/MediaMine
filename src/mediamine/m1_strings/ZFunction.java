package mediamine.m1_strings;

import java.util.ArrayList;
import java.util.List;

/**
 * Z-Function: z[i] = length of the longest substring starting at i that is also a prefix of s.
 * MediaMine use: detect repeated shipment patterns (e.g. a manifest line repeated over and over).
 * Time: O(n).
 */
public class ZFunction {

    public static int[] compute(String s) {
        int n = s.length();
        int[] z = new int[n];
        if (n == 0) return z;
        z[0] = n;
        int l = 0, r = 0;
        for (int i = 1; i < n; i++) {
            if (i < r) z[i] = Math.min(r - i, z[i - l]);
            while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) z[i]++;
            if (i + z[i] > r) { l = i; r = i + z[i]; }
        }
        return z;
    }

    /** Pattern matching using the Z-function on pattern + separator + text. */
    public static List<Integer> search(String text, String pattern) {
        List<Integer> result = new ArrayList<>();
        if (pattern.isEmpty()) return result;
        String combined = pattern + '\u0001' + text;
        int[] z = compute(combined);
        int m = pattern.length();
        for (int i = m + 1; i < combined.length(); i++) {
            if (z[i] >= m) result.add(i - m - 1);
        }
        return result;
    }

    /**
     * Smallest period p such that s is the block s[0..p) repeated. Returns s.length() if s is not periodic.
     * Example: "SHP01SHP01SHP01" -> 5.
     */
    public static int smallestRepeatingUnit(String s) {
        int n = s.length();
        int[] z = compute(s);
        for (int p = 1; p < n; p++) {
            if (n % p == 0 && z[p] == n - p) return p;
        }
        return n;
    }

    /** True if the string is a block repeated at least twice. */
    public static boolean isRepeatedPattern(String s) {
        return smallestRepeatingUnit(s) < s.length();
    }
}
