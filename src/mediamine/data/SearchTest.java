package mediamine.data;

import java.util.Arrays;
import java.util.List;

import mediamine.model.MediaItem;

public class SearchTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("     MEDIAMINE SEARCH BENCHMARK");
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

        MediaSearchService searchService =
                new MediaSearchService(mediaItems);

        String query = "milk";

        System.out.println(
                "\nSearch Query: " + query
        );

        // =================================
        // KMP
        // =================================

        long kmpStart = System.nanoTime();

        List<MediaItem> kmpResults =
                searchService.searchUsingKMP(query);

        long kmpEnd = System.nanoTime();

        double kmpTime =
                (kmpEnd - kmpStart) / 1_000_000.0;

        // =================================
        // RABIN-KARP
        // =================================

        long rkStart = System.nanoTime();

        List<MediaItem> rkResults =
                searchService.searchUsingRabinKarp(query);

        long rkEnd = System.nanoTime();

        double rkTime =
                (rkEnd - rkStart) / 1_000_000.0;

        // =================================
        // Z-FUNCTION
        // =================================

        long zStart = System.nanoTime();

        List<MediaItem> zResults =
                searchService.searchUsingZFunction(query);

        long zEnd = System.nanoTime();

        double zTime =
                (zEnd - zStart) / 1_000_000.0;

        // =================================
        // AHO-CORASICK
        // =================================

        List<String> patterns = Arrays.asList(
                "milk",
                "coffee",
                "chocolate"
        );

        long acStart = System.nanoTime();

        List<MediaItem> acResults =
                searchService.searchUsingAhoCorasick(
                        patterns
                );

        long acEnd = System.nanoTime();

        double acTime =
                (acEnd - acStart) / 1_000_000.0;

        // =================================
        // RESULTS
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("ALGORITHM RESULTS");
        System.out.println("---------------------------------");

        System.out.println(
                "KMP matches: " +
                kmpResults.size()
        );

        System.out.printf(
                "KMP time: %.4f ms%n",
                kmpTime
        );

        System.out.println();

        System.out.println(
                "Rabin-Karp matches: " +
                rkResults.size()
        );

        System.out.printf(
                "Rabin-Karp time: %.4f ms%n",
                rkTime
        );

        System.out.println();

        System.out.println(
                "Z-Function matches: " +
                zResults.size()
        );

        System.out.printf(
                "Z-Function time: %.4f ms%n",
                zTime
        );

        System.out.println();

        System.out.println(
                "Aho-Corasick patterns: " +
                patterns
        );

        System.out.println(
                "Aho-Corasick matched items: " +
                acResults.size()
        );

        System.out.printf(
                "Aho-Corasick time: %.4f ms%n",
                acTime
        );

        // =================================
        // CORRECTNESS CHECK
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("CORRECTNESS CHECK");
        System.out.println("---------------------------------");

        if (kmpResults.size() == rkResults.size()
                && kmpResults.size() == zResults.size()) {

            System.out.println(
                    "✓ KMP, Rabin-Karp and Z-Function "
                    + "returned the same number of matches."
            );

        } else {

            System.out.println(
                    "⚠ KMP/Rabin-Karp/Z-Function "
                    + "match counts are different."
            );
        }

        // =================================
        // SAMPLE RESULTS
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("SAMPLE SEARCH RESULTS");
        System.out.println("---------------------------------");

        int limit =
                Math.min(5, kmpResults.size());

        for (int i = 0; i < limit; i++) {

            System.out.println(
                    (i + 1) + ". " +
                    kmpResults.get(i)
            );
        }

        // =================================
        // AHO-CORASICK SAMPLE RESULTS
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("AHO-CORASICK SAMPLE RESULTS");
        System.out.println("---------------------------------");

        int acLimit =
                Math.min(5, acResults.size());

        for (int i = 0; i < acLimit; i++) {

            System.out.println(
                    (i + 1) + ". " +
                    acResults.get(i)
            );
        }

        // =================================
        // COMPLETE
        // =================================

        System.out.println("\n=================================");
        System.out.println("     BENCHMARK COMPLETE");
        System.out.println("=================================");
    }
}