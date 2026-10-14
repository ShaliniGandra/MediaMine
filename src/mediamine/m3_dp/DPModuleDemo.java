package mediamine.m3_dp;

import java.util.Arrays;
import java.util.List;

import mediamine.data.DatasetManager;
import mediamine.model.MediaItem;

/**
 * Module 3: Advanced Dynamic Programming for MediaMine.
 *
 * Demonstrates:
 * 1. Levenshtein Distance
 * 2. Damerau-Levenshtein Distance
 * 3. Weighted Edit Distance
 * 4. Bitmask Route DP
 * 5. Matrix Chain Multiplication
 * 6. Optimal Binary Search Tree
 */
public class DPModuleDemo {

    public static void run() {

        System.out.println(
                "============================================================"
        );
        System.out.println(
                "        MEDIAMINE - MODULE 3: DYNAMIC PROGRAMMING"
        );
        System.out.println(
                "============================================================"
        );

        // ==========================================
        // LOAD DATASET
        // ==========================================

        DatasetManager manager =
                new DatasetManager("dataset");

        List<MediaItem> mediaItems =
                manager.loadMediaItems();

        System.out.println();
        System.out.println("---------------------------------");
        System.out.println("DATASET");
        System.out.println("---------------------------------");

        System.out.println(
                "MediaItems loaded: " +
                mediaItems.size()
        );

        if (!mediaItems.isEmpty()) {
            System.out.println(
                    "Sample item: " +
                    mediaItems.get(0).getTitle()
            );
        }

        // ==========================================
        // 1. LEVENSHTEIN
        // ==========================================

        System.out.println();
        System.out.println("---------------------------------");
        System.out.println("1. LEVENSHTEIN DISTANCE");
        System.out.println("---------------------------------");

        List<String> known = Arrays.asList(
                "renewable energy",
                "climate policy",
                "sports analytics",
                "election report"
        );

        String typo =
                "renewble enegy";

        String correction =
                Levenshtein.closest(
                        typo,
                        known
                );

        System.out.println(
                "Query: " + typo
        );

        System.out.println(
                "Closest correction: " +
                correction
        );

        // ==========================================
        // 2. DAMERAU-LEVENSHTEIN
        // ==========================================

        System.out.println();
        System.out.println("---------------------------------");
        System.out.println("2. DAMERAU-LEVENSHTEIN");
        System.out.println("---------------------------------");

        String misspelled =
                "medai";

        String correct =
                "media";

        int damerauDistance =
                DamerauLevenshtein.distance(
                        misspelled,
                        correct
                );

        System.out.println(
                "Query: " +
                misspelled
        );

        System.out.println(
                "Target: " +
                correct
        );

        System.out.println(
                "Distance: " +
                damerauDistance
        );

        // ==========================================
        // 3. WEIGHTED EDIT DISTANCE
        // ==========================================

        System.out.println();
        System.out.println("---------------------------------");
        System.out.println("3. WEIGHTED EDIT DISTANCE");
        System.out.println("---------------------------------");

        int weightedCost =
                WeightedEditDistance.distance(
                        "medai",
                        "media",
                        2,
                        2,
                        3
                );

        System.out.println(
                "Query: medai"
        );

        System.out.println(
                "Target: media"
        );

        System.out.println(
                "Insertion cost: 2"
        );

        System.out.println(
                "Deletion cost: 2"
        );

        System.out.println(
                "Substitution cost: 3"
        );

        System.out.println(
                "Weighted correction cost: " +
                weightedCost
        );

        // ==========================================
        // 4. BITMASK ROUTE DP
        // ==========================================

        System.out.println();
        System.out.println("---------------------------------");
        System.out.println("4. BITMASK ROUTE DP");
        System.out.println("---------------------------------");

        int[][] score = {
                {0, 8, 12, 10},
                {8, 0, 7, 9},
                {12, 7, 0, 6},
                {10, 9, 6, 0}
        };

        BitmaskRouteDP.Result routeResult =
                BitmaskRouteDP.solve(score);

        System.out.println(
                "Optimal category-selection tour:"
        );

        System.out.println(
                routeResult
        );

        // ==========================================
        // 5. MATRIX CHAIN
        // ==========================================

        System.out.println();
        System.out.println("---------------------------------");
        System.out.println("5. MATRIX CHAIN MULTIPLICATION");
        System.out.println("---------------------------------");

        int[] dims = {
                20, 30, 10, 30
        };

        MatrixChain.Result matrixResult =
                MatrixChain.solve(dims);

        System.out.println(
                "Matrix dimensions: " +
                Arrays.toString(dims)
        );

        System.out.println(
                "Minimum processing cost: " +
                matrixResult.minCost
        );

        System.out.println(
                "Optimal order: " +
                matrixResult.parenthesization
        );

        // ==========================================
        // 6. OPTIMAL BST
        // ==========================================

        System.out.println();
        System.out.println("---------------------------------");
        System.out.println("6. OPTIMAL BINARY SEARCH TREE");
        System.out.println("---------------------------------");

        String[] keys = {
                "climate",
                "energy",
                "sports",
                "technology"
        };

        int[] freq = {
                40,
                25,
                18,
                10
        };

        OptimalBST bst =
                new OptimalBST(
                        keys,
                        freq
                );

        System.out.println(
                "Search keys: " +
                Arrays.toString(keys)
        );

        System.out.println(
                "Search frequencies: " +
                Arrays.toString(freq)
        );

        System.out.println(
                "Minimum expected search cost: " +
                bst.minCost()
        );

        System.out.println(
                "Optimal search tree:"
        );

        System.out.println(
                OptimalBST.print(
                        bst.buildTree()
                )
        );

        // ==========================================
        // SUMMARY
        // ==========================================

        System.out.println(
                "============================================================"
        );

        System.out.println(
                "              MODULE 3 COMPLETE"
        );

        System.out.println(
                "============================================================"
        );

        System.out.println(
                "✓ Levenshtein Distance"
        );

        System.out.println(
                "✓ Damerau-Levenshtein Distance"
        );

        System.out.println(
                "✓ Weighted Edit Distance"
        );

        System.out.println(
                "✓ Bitmask Route DP"
        );

        System.out.println(
                "✓ Matrix Chain Multiplication"
        );

        System.out.println(
                "✓ Optimal Binary Search Tree"
        );

        System.out.println(
                "✓ Instacart dataset integrated"
        );

        System.out.println(
                "============================================================"
        );
    }

    public static void main(String[] args) {
        run();
    }
}