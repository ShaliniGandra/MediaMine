package mediamine.data;

import java.util.List;

import mediamine.m3_dp.OptimalBST;
import mediamine.m3_dp.OptimalBST.Node;
import mediamine.model.MediaItem;

public class OptimalBSTTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("     MEDIAMINE OPTIMAL BST TEST");
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
        // SEARCH KEYS
        // =================================
        /*
         * Keys must be sorted.
         *
         * These represent frequently searched
         * MediaMine categories/queries.
         */

        String[] keys = {
                "analytics",
                "database",
                "media",
                "search",
                "video"
        };

        /*
         * Frequency represents how often each
         * key is expected to be searched.
         *
         * Higher frequency -> should generally
         * be placed closer to the root.
         */

        int[] frequencies = {
                5,
                10,
                20,
                50,
                15
        };

        System.out.println("\n---------------------------------");
        System.out.println("SEARCH KEYS");
        System.out.println("---------------------------------");

        for (int i = 0; i < keys.length; i++) {

            System.out.println(
                    (i + 1) + ". " +
                    keys[i] +
                    " | frequency = " +
                    frequencies[i]
            );
        }

        // =================================
        // BUILD OPTIMAL BST
        // =================================

        long start =
                System.nanoTime();

        OptimalBST optimalBST =
                new OptimalBST(
                        keys,
                        frequencies
                );

        Node root =
                optimalBST.buildTree();

        long end =
                System.nanoTime();

        double executionTime =
                (end - start) / 1_000_000.0;

        // =================================
        // RESULT
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("OPTIMAL BST RESULT");
        System.out.println("---------------------------------");

        System.out.println(
                "Minimum weighted search cost: " +
                optimalBST.minCost()
        );

        System.out.println(
                "Root key: " +
                (root == null ? "None" : root.key)
        );

        // =================================
        // TREE
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("OPTIMAL SEARCH TREE");
        System.out.println("---------------------------------");

        System.out.println(
                OptimalBST.print(root)
        );

        // =================================
        // PERFORMANCE
        // =================================

        System.out.println("---------------------------------");
        System.out.println("PERFORMANCE");
        System.out.println("---------------------------------");

        System.out.printf(
                "Construction time: %.4f ms%n",
                executionTime
        );

        System.out.println(
                "Number of keys: " +
                keys.length
        );

        // =================================
        // CORRECTNESS CHECK
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("CORRECTNESS CHECK");
        System.out.println("---------------------------------");

        boolean hasRoot =
                root != null;

        boolean positiveCost =
                optimalBST.minCost() > 0;

        if (hasRoot && positiveCost) {

            System.out.println(
                    "✓ Optimal BST constructed successfully."
            );

            System.out.println(
                    "✓ Minimum weighted search cost calculated."
            );

        } else {

            System.out.println(
                    "✗ Optimal BST validation failed."
            );
        }

        // =================================
        // STANDARD TEST
        // =================================
        /*
         * Classic Optimal BST example.
         *
         * Keys:
         * 10, 12, 20
         *
         * Frequencies:
         * 34, 8, 50
         *
         * Expected minimum cost = 142
         */

        System.out.println("\n---------------------------------");
        System.out.println("STANDARD TEST");
        System.out.println("---------------------------------");

        String[] standardKeys = {
                "10",
                "12",
                "20"
        };

        int[] standardFreq = {
                34,
                8,
                50
        };

        OptimalBST standardBST =
                new OptimalBST(
                        standardKeys,
                        standardFreq
                );

        long expectedCost = 142;
        long actualCost =
                standardBST.minCost();

        System.out.println(
                "Expected minimum cost: " +
                expectedCost
        );

        System.out.println(
                "Actual minimum cost: " +
                actualCost
        );

        System.out.println(
                "Optimal root: " +
                standardBST.buildTree().key
        );

        if (actualCost == expectedCost) {

            System.out.println(
                    "✓ Standard test passed."
            );

        } else {

            System.out.println(
                    "✗ Standard test failed."
            );
        }

        // =================================
        // DATASET CONNECTION
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("DATASET CONNECTION");
        System.out.println("---------------------------------");

        if (!mediaItems.isEmpty()) {

            System.out.println(
                    "Dataset successfully loaded."
            );

            System.out.println(
                    "Total MediaItems: " +
                    mediaItems.size()
            );

            System.out.println(
                    "Example item: " +
                    mediaItems.get(0).getTitle()
            );
        }

        // =================================
        // COMPLETE
        // =================================

        System.out.println("\n=================================");
        System.out.println("   OPTIMAL BST TEST COMPLETE");
        System.out.println("=================================");
    }
}