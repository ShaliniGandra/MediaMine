package mediamine.m2_suffix;

import java.util.Arrays;

/**
 * SA-IS (Suffix Array by Induced Sorting) - linear-time suffix array construction.
 * MediaMine use: indexing very large logistics-document collections.
 * Time: O(n), Space: O(n).
 */
public class SAIS {

    /** Suffix array of a string (lexicographic order, no sentinel needed). */
    public static int[] buildSuffixArray(String s) {
        int n = s.length();
        int[] arr = new int[n];
        int max = 0;
        for (int i = 0; i < n; i++) { arr[i] = s.charAt(i); max = Math.max(max, arr[i]); }
        return sais(arr, max);
    }

    /** Core SA-IS. All values of s must lie in [0, upper]. */
    public static int[] sais(int[] s, int upper) {
        int n = s.length;
        if (n == 0) return new int[0];
        if (n == 1) return new int[] {0};
        if (n == 2) return s[0] < s[1] ? new int[] {0, 1} : new int[] {1, 0};

        int[] sa = new int[n];
        boolean[] ls = new boolean[n];               // true = S-type, false = L-type
        for (int i = n - 2; i >= 0; i--) {
            ls[i] = (s[i] == s[i + 1]) ? ls[i + 1] : (s[i] < s[i + 1]);
        }

        int[] sumL = new int[upper + 1];
        int[] sumS = new int[upper + 1];
        for (int i = 0; i < n; i++) {
            if (!ls[i]) sumS[s[i]]++;
            else sumL[s[i] + 1]++;
        }
        for (int i = 0; i <= upper; i++) {
            sumS[i] += sumL[i];
            if (i < upper) sumL[i + 1] += sumS[i];
        }

        int[] lmsMap = new int[n + 1];
        Arrays.fill(lmsMap, -1);
        int m = 0;
        for (int i = 1; i < n; i++) if (!ls[i - 1] && ls[i]) lmsMap[i] = m++;
        int[] lms = new int[m];
        int p = 0;
        for (int i = 1; i < n; i++) if (!ls[i - 1] && ls[i]) lms[p++] = i;

        induce(lms, s, sa, ls, sumS, sumL, upper);

        if (m > 0) {
            int[] sortedLms = new int[m];
            int q = 0;
            for (int v : sa) if (v >= 0 && lmsMap[v] != -1) sortedLms[q++] = v;

            int[] recS = new int[m];
            int recUpper = 0;
            recS[lmsMap[sortedLms[0]]] = 0;
            for (int i = 1; i < m; i++) {
                int l = sortedLms[i - 1], r = sortedLms[i];
                int endL = (lmsMap[l] + 1 < m) ? lms[lmsMap[l] + 1] : n;
                int endR = (lmsMap[r] + 1 < m) ? lms[lmsMap[r] + 1] : n;
                boolean same = true;
                if (endL - l != endR - r) {
                    same = false;
                } else {
                    while (l < endL) {
                        if (s[l] != s[r]) break;
                        l++; r++;
                    }
                    if (l == n || s[l] != s[r]) same = false;
                }
                if (!same) recUpper++;
                recS[lmsMap[sortedLms[i]]] = recUpper;
            }

            int[] recSa = sais(recS, recUpper);
            for (int i = 0; i < m; i++) sortedLms[i] = lms[recSa[i]];
            induce(sortedLms, s, sa, ls, sumS, sumL, upper);
        }
        return sa;
    }

    private static void induce(int[] lms, int[] s, int[] sa, boolean[] ls,
                               int[] sumS, int[] sumL, int upper) {
        int n = s.length;
        Arrays.fill(sa, -1);
        int[] buf = new int[upper + 1];

        System.arraycopy(sumS, 0, buf, 0, upper + 1);
        for (int d : lms) {
            if (d == n) continue;
            sa[buf[s[d]]++] = d;
        }

        System.arraycopy(sumL, 0, buf, 0, upper + 1);
        sa[buf[s[n - 1]]++] = n - 1;
        for (int i = 0; i < n; i++) {
            int v = sa[i];
            if (v >= 1 && !ls[v - 1]) sa[buf[s[v - 1]]++] = v - 1;
        }

        System.arraycopy(sumL, 0, buf, 0, upper + 1);
        for (int i = n - 1; i >= 0; i--) {
            int v = sa[i];
            if (v >= 1 && ls[v - 1]) sa[--buf[s[v - 1] + 1]] = v - 1;
        }
    }
}
