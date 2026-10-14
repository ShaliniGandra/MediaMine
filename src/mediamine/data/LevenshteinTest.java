package mediamine.data;

import java.util.ArrayList;
import java.util.List;

import mediamine.m3_dp.Levenshtein;
import mediamine.model.MediaItem;

public class LevenshteinTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("   MEDIAMINE LEVENSHTEIN TEST");
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
        // TEST QUERY
        // =================================

        String query = "choclate";

        System.out.println("\n---------------------------------");
        System.out.println("TYPO / QUERY CORRECTION");
        System.out.println("---------------------------------");

        System.out.println(
                "User query: " + query
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
        // FIND CLOSEST PRODUCT NAME
        // =================================

        long start =
                System.nanoTime();

        String closest =
                Levenshtein.closest(
                        query,
                        dictionary
                );

        long end =
                System.nanoTime();

        double executionTime =
                (end - start) / 1_000_000.0;

        // =================================
        // RESULT
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("CLOSEST MATCH");
        System.out.println("---------------------------------");

        System.out.println(
                "Input: " + query
        );

        System.out.println(
                "Closest product: " +
                closest
        );

        // =================================
        // DISTANCE
        // =================================

        if (closest != null) {

            int distance =
                    Levenshtein.distance(
                            query.toLowerCase(),
                            closest.toLowerCase()
                    );

            System.out.println(
                    "Edit distance: " +
                    distance
            );

            // =================================
            // EDIT SCRIPT
            // =================================

            System.out.println("\n---------------------------------");
            System.out.println("EDIT OPERATIONS");
            System.out.println("---------------------------------");

            System.out.println(
                    Levenshtein.editScript(
                            query.toLowerCase(),
                            closest.toLowerCase()
                    )
            );
        }

        // =================================
        // PERFORMANCE
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("PERFORMANCE");
        System.out.println("---------------------------------");

        System.out.printf(
                "Execution time: %.4f ms%n",
                executionTime
        );

        // =================================
        // SIMPLE KNOWN TESTS
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("KNOWN DISTANCE TESTS");
        System.out.println("---------------------------------");

        System.out.println(
                "kitten → sitting: " +
                Levenshtein.distance(
                        "kitten",
                        "sitting"
                )
        );

        System.out.println(
                "milk → milk: " +
                Levenshtein.distance(
                        "milk",
                        "milk"
                )
        );

        System.out.println(
                "abc → xyz: " +
                Levenshtein.distance(
                        "abc",
                        "xyz"
                )
        );

        // =================================
        // COMPLETE
        // =================================

        System.out.println("\n=================================");
        System.out.println("   LEVENSHTEIN TEST COMPLETE");
        System.out.println("=================================");
    }
}