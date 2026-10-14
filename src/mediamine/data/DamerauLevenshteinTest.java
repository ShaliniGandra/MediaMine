package mediamine.data;

import java.util.ArrayList;
import java.util.List;

import mediamine.m3_dp.DamerauLevenshtein;
import mediamine.model.MediaItem;

public class DamerauLevenshteinTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" MEDIAMINE DAMERAU-LEVENSHTEIN");
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
        // BUILD PRODUCT DICTIONARY
        // =================================

        List<String> dictionary =
                new ArrayList<>();

        for (MediaItem item : mediaItems) {

            if (item.getTitle() != null &&
                    !item.getTitle().trim().isEmpty()) {

                dictionary.add(item.getTitle());
            }
        }

        System.out.println(
                "Dictionary size: " +
                dictionary.size()
        );

        // =================================
        // TYPO TEST
        // =================================

        String query = "raod";

        System.out.println("\n---------------------------------");
        System.out.println("TYPO CORRECTION TEST");
        System.out.println("---------------------------------");

        System.out.println(
                "Input query: " +
                query
        );

        long start =
                System.nanoTime();

        String closest =
                DamerauLevenshtein.closest(
                        query,
                        dictionary
                );

        long end =
                System.nanoTime();

        double executionTime =
                (end - start) / 1_000_000.0;

        System.out.println(
                "Closest product: " +
                closest
        );

        if (closest != null) {

            int distance =
                    DamerauLevenshtein.distance(
                            query.toLowerCase(),
                            closest.toLowerCase()
                    );

            System.out.println(
                    "Damerau-Levenshtein distance: " +
                    distance
            );
        }

        // =================================
        // TRANSPOSITION TEST
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("TRANSPOSITION TEST");
        System.out.println("---------------------------------");

        String a = "milk";
        String b = "mlik";

        int transpositionDistance =
                DamerauLevenshtein.distance(a, b);

        System.out.println(
                "\"" + a + "\" → \"" + b + "\""
        );

        System.out.println(
                "Distance: " +
                transpositionDistance
        );

        // =================================
        // KNOWN TESTS
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("KNOWN DISTANCE TESTS");
        System.out.println("---------------------------------");

        System.out.println(
                "milk → milk: " +
                DamerauLevenshtein.distance(
                        "milk",
                        "milk"
                )
        );

        System.out.println(
                "milk → mlik: " +
                DamerauLevenshtein.distance(
                        "milk",
                        "mlik"
                )
        );

        System.out.println(
                "abc → xyz: " +
                DamerauLevenshtein.distance(
                        "abc",
                        "xyz"
                )
        );

        // =================================
        // PERFORMANCE
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("PERFORMANCE");
        System.out.println("---------------------------------");

        System.out.printf(
                "Dictionary search time: %.4f ms%n",
                executionTime
        );

        // =================================
        // COMPLETE
        // =================================

        System.out.println("\n=================================");
        System.out.println(" DAMERAU-LEVENSHTEIN COMPLETE");
        System.out.println("=================================");
    }
}