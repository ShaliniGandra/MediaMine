package mediamine.data;

import java.util.List;

import mediamine.m3_dp.MatrixChain;
import mediamine.m3_dp.MatrixChain.Result;
import mediamine.model.MediaItem;

public class MatrixChainTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("   MEDIAMINE MATRIX CHAIN TEST");
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
        // MATRIX DEFINITIONS
        // =================================
        /*
         * We model a MediaMine processing pipeline:
         *
         * A1 = Demand Matrix
         * A2 = Route Matrix
         * A3 = Cost Matrix
         * A4 = Analytics Matrix
         *
         * Dimensions:
         *
         * A1 = 10 x 30
         * A2 = 30 x 5
         * A3 = 5 x 60
         * A4 = 60 x 20
         *
         * dims = [10, 30, 5, 60, 20]
         */

        int[] dims = {
                10, 30, 5, 60, 20
        };

        String[] names = {
                "Demand",
                "Route",
                "Cost",
                "Analytics"
        };

        System.out.println("\n---------------------------------");
        System.out.println("MATRIX CHAIN");
        System.out.println("---------------------------------");

        for (int i = 0; i < names.length; i++) {

            System.out.println(
                    names[i] +
                    " = " +
                    dims[i] +
                    " x " +
                    dims[i + 1]
            );
        }

        // =================================
        // SOLVE
        // =================================

        long start =
                System.nanoTime();

        Result result =
                MatrixChain.solve(
                        dims,
                        names
                );

        long end =
                System.nanoTime();

        double executionTime =
                (end - start) / 1_000_000.0;

        // =================================
        // RESULT
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("OPTIMAL RESULT");
        System.out.println("---------------------------------");

        System.out.println(
                "Minimum multiplication cost: " +
                result.minCost
        );

        System.out.println(
                "Optimal parenthesization: " +
                result.parenthesization
        );

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

        System.out.println(
                "Number of matrices: " +
                names.length
        );

        // =================================
        // VALIDATION
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("CORRECTNESS CHECK");
        System.out.println("---------------------------------");

        boolean validCost =
                result.minCost > 0;

        boolean validOrder =
                result.parenthesization != null
                && !result.parenthesization.isEmpty();

        if (validCost && validOrder) {

            System.out.println(
                    "✓ Minimum multiplication cost calculated."
            );

            System.out.println(
                    "✓ Optimal parenthesization generated."
            );

        } else {

            System.out.println(
                    "⚠ Matrix-chain validation failed."
            );
        }

        // =================================
        // STANDARD TEST
        // =================================
        /*
         * Classic Matrix Chain example:
         *
         * A1 = 10 x 30
         * A2 = 30 x 5
         * A3 = 5 x 60
         *
         * Expected minimum cost = 4500
         */

        System.out.println("\n---------------------------------");
        System.out.println("STANDARD TEST");
        System.out.println("---------------------------------");

        int[] standardDims = {
                10, 30, 5, 60
        };

        Result standardResult =
                MatrixChain.solve(
                        standardDims
                );

        System.out.println(
                "Expected cost: 4500"
        );

        System.out.println(
                "Actual cost: " +
                standardResult.minCost
        );

        System.out.println(
                "Optimal order: " +
                standardResult.parenthesization
        );

        if (standardResult.minCost == 4500) {

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
        System.out.println(" MATRIX CHAIN TEST COMPLETE");
        System.out.println("=================================");
    }
}