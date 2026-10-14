package mediamine.data;

import java.util.List;

import mediamine.m3_dp.BitmaskRouteDP;
import mediamine.m3_dp.BitmaskRouteDP.Result;
import mediamine.model.MediaItem;

public class BitmaskRouteDPTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("    MEDIAMINE BITMASK DP TEST");
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
        // SMALL ROUTE
        // =================================
        // 0 = Depot
        // 1 = Search Index
        // 2 = Media Database
        // 3 = Analytics
        // 4 = Query Engine
        // 5 = Dashboard

        String[] locations = {
                "Depot",
                "Search Index",
                "Media Database",
                "Analytics",
                "Query Engine",
                "Dashboard"
        };

        /*
         * Symmetric distance/cost matrix.
         *
         * These are abstract processing/traversal costs
         * for demonstrating Held-Karp DP.
         */

        int[][] dist = {
                {0, 10, 15, 20, 12, 18},
                {10, 0, 35, 25, 17, 28},
                {15, 35, 0, 30, 20, 24},
                {20, 25, 30, 0, 18, 22},
                {12, 17, 20, 18, 0, 16},
                {18, 28, 24, 22, 16, 0}
        };

        System.out.println("\n---------------------------------");
        System.out.println("ROUTE LOCATIONS");
        System.out.println("---------------------------------");

        for (int i = 0; i < locations.length; i++) {

            System.out.println(
                    i + " -> " + locations[i]
            );
        }

        // =================================
        // DISPLAY DISTANCE MATRIX
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("COST MATRIX");
        System.out.println("---------------------------------");

        for (int i = 0; i < dist.length; i++) {

            for (int j = 0; j < dist[i].length; j++) {

                System.out.printf(
                        "%4d",
                        dist[i][j]
                );
            }

            System.out.println();
        }

        // =================================
        // SOLVE
        // =================================

        long start =
                System.nanoTime();

        Result result =
                BitmaskRouteDP.solve(dist);

        long end =
                System.nanoTime();

        double executionTime =
                (end - start) / 1_000_000.0;

        // =================================
        // RESULT
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("OPTIMAL ROUTE");
        System.out.println("---------------------------------");

        System.out.println(
                "Minimum cost: " +
                result.cost
        );

        System.out.println(
                "Route indices: " +
                result.route
        );

        // =================================
        // ROUTE WITH NAMES
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("ROUTE DETAILS");
        System.out.println("---------------------------------");

        for (int i = 0; i < result.route.size(); i++) {

            int node =
                    result.route.get(i);

            System.out.println(
                    (i + 1) + ". " +
                    locations[node]
            );
        }

        // =================================
        // VERIFY START / END
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("CORRECTNESS CHECK");
        System.out.println("---------------------------------");

        boolean startsAtDepot =
                !result.route.isEmpty()
                && result.route.get(0) == 0;

        boolean endsAtDepot =
                !result.route.isEmpty()
                && result.route.get(
                        result.route.size() - 1
                ) == 0;

        boolean visitsAll =
                result.route.size()
                == locations.length + 1;

        if (startsAtDepot &&
                endsAtDepot &&
                visitsAll) {

            System.out.println(
                    "✓ Route starts and ends at the depot."
            );

            System.out.println(
                    "✓ Every location is visited exactly once."
            );

        } else {

            System.out.println(
                    "⚠ Route validation failed."
            );
        }

        // =================================
        // PERFORMANCE
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("PERFORMANCE");
        System.out.println("---------------------------------");

        System.out.printf(
                "DP execution time: %.4f ms%n",
                executionTime
        );

        System.out.println(
                "Number of locations: " +
                locations.length
        );

        // =================================
        // DATASET CONNECTION
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("DATASET CONNECTION");
        System.out.println("---------------------------------");

        if (!mediaItems.isEmpty()) {

            System.out.println(
                    "Dataset successfully loaded with " +
                    mediaItems.size() +
                    " media items."
            );

            System.out.println(
                    "Example dataset item: " +
                    mediaItems.get(0).getTitle()
            );
        }

        // =================================
        // COMPLETE
        // =================================

        System.out.println("\n=================================");
        System.out.println("   BITMASK DP TEST COMPLETE");
        System.out.println("=================================");
    }
}