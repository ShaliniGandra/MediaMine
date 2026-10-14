package mediamine.data;

import java.util.List;

import mediamine.m4_flow.FlowNetwork;
import mediamine.m4_flow.FordFulkerson;
import mediamine.model.MediaItem;

public class FordFulkersonTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("  MEDIAMINE FORD-FULKERSON TEST");
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
        // CREATE FLOW NETWORK
        // =================================

        /*
         * Source
         *   |
         *   +---- Search Server ----+
         *   |                       |
         *   +---- Media Server -----+---- Destination
         *   |                       |
         *   +---- Analytics Server -+
         *
         * Expected maximum flow = 25.
         */

        int source = 0;
        int searchServer = 1;
        int mediaServer = 2;
        int analyticsServer = 3;
        int destination = 4;

        int numberOfNodes = 5;

        FlowNetwork network =
                new FlowNetwork(numberOfNodes);

        network.addEdge(
                source,
                searchServer,
                10
        );

        network.addEdge(
                source,
                mediaServer,
                15
        );

        network.addEdge(
                source,
                analyticsServer,
                12
        );

        network.addEdge(
                searchServer,
                destination,
                8
        );

        network.addEdge(
                mediaServer,
                destination,
                10
        );

        network.addEdge(
                analyticsServer,
                destination,
                7
        );

        // =================================
        // DISPLAY NETWORK
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("FLOW NETWORK");
        System.out.println("---------------------------------");

        System.out.println(
                "Source -> Search Server : 10"
        );

        System.out.println(
                "Source -> Media Server : 15"
        );

        System.out.println(
                "Source -> Analytics Server : 12"
        );

        System.out.println(
                "Search Server -> Destination : 8"
        );

        System.out.println(
                "Media Server -> Destination : 10"
        );

        System.out.println(
                "Analytics Server -> Destination : 7"
        );

        // =================================
        // RUN FORD-FULKERSON
        // =================================

        long start =
                System.nanoTime();

        long maxFlow =
                FordFulkerson.maxFlow(
                        network,
                        source,
                        destination
                );

        long end =
                System.nanoTime();

        double executionTime =
                (end - start) / 1_000_000.0;

        // =================================
        // RESULT
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("MAXIMUM FLOW RESULT");
        System.out.println("---------------------------------");

        System.out.println(
                "Maximum flow: " +
                maxFlow
        );

        System.out.println(
                "Expected maximum flow: 25"
        );

        if (maxFlow == 25) {

            System.out.println(
                    "✓ Ford-Fulkerson test passed."
            );

        } else {

            System.out.println(
                    "✗ Ford-Fulkerson test failed."
            );
        }

        // =================================
        // FLOW SUMMARY
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("FLOW SUMMARY");
        System.out.println("---------------------------------");

        System.out.print(
                network.flowSummary()
        );

        // =================================
        // PERFORMANCE
        // =================================

        System.out.println("---------------------------------");
        System.out.println("PERFORMANCE");
        System.out.println("---------------------------------");

        System.out.printf(
                "Execution time: %.4f ms%n",
                executionTime
        );

        System.out.println(
                "Number of nodes: " +
                numberOfNodes
        );

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
        System.out.println(" FORD-FULKERSON TEST COMPLETE");
        System.out.println("=================================");
    }
}