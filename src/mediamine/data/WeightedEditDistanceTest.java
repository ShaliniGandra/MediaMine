package mediamine.data;

import java.util.List;

import mediamine.m3_dp.WeightedEditDistance;
import mediamine.model.MediaItem;

public class WeightedEditDistanceTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" MEDIAMINE WEIGHTED EDIT DISTANCE");
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
        // TEST STRINGS
        // =================================

        String query = "milc";
        String target = "milk";

        System.out.println("\n---------------------------------");
        System.out.println("WEIGHTED DISTANCE TEST");
        System.out.println("---------------------------------");

        System.out.println(
                "Query: " + query
        );

        System.out.println(
                "Target: " + target
        );

        // =================================
        // COST CONFIGURATION
        // =================================

        int insertCost = 1;
        int deleteCost = 1;
        int substituteCost = 2;

        System.out.println(
                "Insertion cost: " +
                insertCost
        );

        System.out.println(
                "Deletion cost: " +
                deleteCost
        );

        System.out.println(
                "Substitution cost: " +
                substituteCost
        );

        // =================================
        // CALCULATE DISTANCE
        // =================================

        long start =
                System.nanoTime();

        int distance =
                WeightedEditDistance.distance(
                        query,
                        target,
                        insertCost,
                        deleteCost,
                        substituteCost
                );

        long end =
                System.nanoTime();

        double executionTime =
                (end - start) / 1_000_000.0;

        // =================================
        // RESULT
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("RESULT");
        System.out.println("---------------------------------");

        System.out.println(
                "Weighted edit distance: " +
                distance
        );

        System.out.printf(
                "Execution time: %.4f ms%n",
                executionTime
        );

        // =================================
        // COMPARE DIFFERENT COSTS
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("COST COMPARISON");
        System.out.println("---------------------------------");

        int lowSubstitution =
                WeightedEditDistance.distance(
                        query,
                        target,
                        1,
                        1,
                        1
                );

        int highSubstitution =
                WeightedEditDistance.distance(
                        query,
                        target,
                        1,
                        1,
                        5
                );

        System.out.println(
                "Substitution cost = 1: " +
                lowSubstitution
        );

        System.out.println(
                "Substitution cost = 5: " +
                highSubstitution
        );

        // =================================
        // DATASET PRODUCT TEST
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("DATASET PRODUCT TEST");
        System.out.println("---------------------------------");

        int sampleCount = 0;

        for (MediaItem item : mediaItems) {

            if (sampleCount >= 5) {
                break;
            }

            if (item.getTitle() == null ||
                    item.getTitle().trim().isEmpty()) {
                continue;
            }

            String productName =
                    item.getTitle();

            int productDistance =
                    WeightedEditDistance.distance(
                            query.toLowerCase(),
                            productName.toLowerCase(),
                            insertCost,
                            deleteCost,
                            substituteCost
                    );

            System.out.println(
                    (sampleCount + 1) +
                    ". " +
                    productName +
                    " | distance = " +
                    productDistance
            );

            sampleCount++;
        }

        // =================================
        // KNOWN TESTS
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("KNOWN TESTS");
        System.out.println("---------------------------------");

        System.out.println(
                "milk → milk: " +
                WeightedEditDistance.distance(
                        "milk",
                        "milk",
                        1,
                        1,
                        2
                )
        );

        System.out.println(
                "milk → milc: " +
                WeightedEditDistance.distance(
                        "milk",
                        "milc",
                        1,
                        1,
                        2
                )
        );

        System.out.println(
                "abc → xyz: " +
                WeightedEditDistance.distance(
                        "abc",
                        "xyz",
                        1,
                        1,
                        2
                )
        );

        // =================================
        // COMPLETE
        // =================================

        System.out.println("\n=================================");
        System.out.println(" WEIGHTED EDIT DISTANCE COMPLETE");
        System.out.println("=================================");
    }
}