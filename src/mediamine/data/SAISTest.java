package mediamine.data;

import java.util.Arrays;
import java.util.List;

import mediamine.m2_suffix.SAIS;
import mediamine.m2_suffix.SuffixArray;
import mediamine.model.MediaItem;

public class SAISTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("       MEDIAMINE SA-IS TEST");
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
        // SELECT TEST ITEM
        // =================================

        if (mediaItems.isEmpty()) {
            System.out.println("No media items found.");
            return;
        }

        MediaItem item = mediaItems.get(0);

        String text =
                item.getTitle().toLowerCase();

        System.out.println("\n---------------------------------");
        System.out.println("TEXT FOR SA-IS ANALYSIS");
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
        // SA-IS
        // =================================

        long saisStart =
                System.nanoTime();

        int[] saisResult =
                SAIS.buildSuffixArray(text);

        long saisEnd =
                System.nanoTime();

        double saisTime =
                (saisEnd - saisStart)
                        / 1_000_000.0;

        // =================================
        // PREFIX-DOUBLING SUFFIX ARRAY
        // =================================

        long normalStart =
                System.nanoTime();

        SuffixArray suffixArray =
                new SuffixArray(text);

        int[] normalResult =
                suffixArray.getSuffixArray();

        long normalEnd =
                System.nanoTime();

        double normalTime =
                (normalEnd - normalStart)
                        / 1_000_000.0;

        // =================================
        // DISPLAY SA-IS RESULT
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("SA-IS SUFFIX ARRAY");
        System.out.println("---------------------------------");

        System.out.println(
                Arrays.toString(saisResult)
        );

        // =================================
        // DISPLAY NORMAL RESULT
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("PREFIX-DOUBLING SUFFIX ARRAY");
        System.out.println("---------------------------------");

        System.out.println(
                Arrays.toString(normalResult)
        );

        // =================================
        // CORRECTNESS CHECK
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("CORRECTNESS CHECK");
        System.out.println("---------------------------------");

        boolean same =
                Arrays.equals(
                        saisResult,
                        normalResult
                );

        if (same) {

            System.out.println(
                    "✓ SA-IS and Prefix-Doubling "
                    + "returned the same suffix array."
            );

        } else {

            System.out.println(
                    "⚠ SA-IS and Prefix-Doubling "
                    + "returned different results."
            );
        }

        // =================================
        // PERFORMANCE
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("PERFORMANCE");
        System.out.println("---------------------------------");

        System.out.printf(
                "SA-IS time: %.4f ms%n",
                saisTime
        );

        System.out.printf(
                "Prefix-Doubling time: %.4f ms%n",
                normalTime
        );

        // =================================
        // SA-IS SIZE
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("INDEX INFORMATION");
        System.out.println("---------------------------------");

        System.out.println(
                "Text length: " +
                text.length()
        );

        System.out.println(
                "Suffixes indexed: " +
                saisResult.length
        );

        // =================================
        // COMPLETE
        // =================================

        System.out.println("\n=================================");
        System.out.println("       SA-IS TEST COMPLETE");
        System.out.println("=================================");
    }
}