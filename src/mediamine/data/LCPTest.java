package mediamine.data;

import java.util.List;

import mediamine.m2_suffix.LCPKasai;
import mediamine.m2_suffix.SuffixArray;
import mediamine.model.MediaItem;

public class LCPTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      MEDIAMINE LCP TEST");
        System.out.println("=================================");

        // =================================
        // LOAD DATASET
        // =================================

        DatasetManager manager =
                new DatasetManager("dataset");

        List<MediaItem> mediaItems =
                manager.loadMediaItems();

        System.out.println(
                "MediaItems loaded: " +
                mediaItems.size()
        );

        // =================================
        // SELECT ONE ITEM
        // =================================

        if (mediaItems.isEmpty()) {
            System.out.println("No media items found.");
            return;
        }

        MediaItem item = mediaItems.get(0);

        String text =
                item.getTitle().toLowerCase();

        System.out.println("\n---------------------------------");
        System.out.println("TEXT FOR LCP ANALYSIS");
        System.out.println("---------------------------------");

        System.out.println(
                "Product: " +
                item.getTitle()
        );

        System.out.println(
                "Text: " +
                text
        );

        // =================================
        // BUILD SUFFIX ARRAY
        // =================================

        long saStart = System.nanoTime();

        SuffixArray suffixArray =
                new SuffixArray(text);

        long saEnd = System.nanoTime();

        double saTime =
                (saEnd - saStart) / 1_000_000.0;

        int[] sa =
                suffixArray.getSuffixArray();

        // =================================
        // BUILD LCP ARRAY
        // =================================

        long lcpStart = System.nanoTime();

        int[] lcp =
                LCPKasai.buildLCP(text, sa);

        long lcpEnd = System.nanoTime();

        double lcpTime =
                (lcpEnd - lcpStart) / 1_000_000.0;

        // =================================
        // DISPLAY ARRAYS
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("SUFFIX ARRAY");
        System.out.println("---------------------------------");

        System.out.println(
                java.util.Arrays.toString(sa)
        );

        System.out.println("\n---------------------------------");
        System.out.println("LCP ARRAY");
        System.out.println("---------------------------------");

        System.out.println(
                java.util.Arrays.toString(lcp)
        );

        // =================================
        // LONGEST REPEATED SUBSTRING
        // =================================

        String longestRepeated =
                LCPKasai.longestRepeatedSubstring(
                        text,
                        sa,
                        lcp
                );

        System.out.println("\n---------------------------------");
        System.out.println("LONGEST REPEATED SUBSTRING");
        System.out.println("---------------------------------");

        if (longestRepeated.isEmpty()) {
            System.out.println(
                    "No repeated substring found."
            );
        } else {
            System.out.println(
                    "Longest repeated substring: \"" +
                    longestRepeated +
                    "\""
            );
        }

        // =================================
        // DISTINCT SUBSTRINGS
        // =================================

        long distinctCount =
                LCPKasai.countDistinctSubstrings(
                        text,
                        lcp
                );

        System.out.println("\n---------------------------------");
        System.out.println("DISTINCT SUBSTRINGS");
        System.out.println("---------------------------------");

        System.out.println(
                "Distinct substring count: " +
                distinctCount
        );

        // =================================
        // REPEATED SUBSTRINGS
        // =================================

        int minLength = 3;

        List<String> repeated =
                LCPKasai.repeatedSubstrings(
                        text,
                        sa,
                        lcp,
                        minLength
                );

        System.out.println("\n---------------------------------");
        System.out.println("REPEATED SUBSTRINGS");
        System.out.println("---------------------------------");

        System.out.println(
                "Minimum length: " +
                minLength
        );

        System.out.println(
                "Repeated substring count: " +
                repeated.size()
        );

        int limit =
                Math.min(10, repeated.size());

        for (int i = 0; i < limit; i++) {
            System.out.println(
                    (i + 1) + ". \"" +
                    repeated.get(i) +
                    "\""
            );
        }

        // =================================
        // PERFORMANCE
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("PERFORMANCE");
        System.out.println("---------------------------------");

        System.out.printf(
                "Suffix Array construction: %.4f ms%n",
                saTime
        );

        System.out.printf(
                "LCP construction: %.4f ms%n",
                lcpTime
        );

        // =================================
        // COMPLETE
        // =================================

        System.out.println("\n=================================");
        System.out.println("       LCP TEST COMPLETE");
        System.out.println("=================================");
    }
}