package mediamine.data;

import java.util.List;

import mediamine.m4_flow.FlowNetwork;
import mediamine.m4_flow.MinCut;
import mediamine.m4_flow.MinCut.Result;
import mediamine.model.MediaItem;

public class MinCutTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("       MEDIAMINE MIN-CUT TEST");
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
        // CREATE NETWORK
        // =================================

        int source = 0;
        int searchServer = 1;
        int mediaServer = 2;
        int analyticsServer = 3;
        int destination = 4;

        FlowNetwork network =
                new FlowNetwork(5);

        network.setName(source, "SOURCE");
        network.setName(searchServer, "SEARCH-SERVER");
        network.setName(mediaServer, "MEDIA-SERVER");
        network.setName(analyticsServer, "ANALYTICS-SERVER");
        network.setName(destination, "SINK");

        network.addEdge(source, searchServer, 10);
        network.addEdge(source, mediaServer, 15);
        network.addEdge(source, analyticsServer, 12);

        network.addEdge(searchServer, destination, 8);
        network.addEdge(mediaServer, destination, 10);
        network.addEdge(analyticsServer, destination, 7);

        // =================================
        // DISPLAY NETWORK
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("FLOW NETWORK");
        System.out.println("---------------------------------");

        System.out.println(
                "SOURCE -> SEARCH-SERVER : 10"
        );

        System.out.println(
                "SOURCE -> MEDIA-SERVER : 15"
        );

        System.out.println(
                "SOURCE -> ANALYTICS-SERVER : 12"
        );

        System.out.println(
                "SEARCH-SERVER -> SINK : 8"
        );

        System.out.println(
                "MEDIA-SERVER -> SINK : 10"
        );

        System.out.println(
                "ANALYTICS-SERVER -> SINK : 7"
        );

        // =================================
        // RUN MIN-CUT
        // =================================

        long start =
                System.nanoTime();

        Result result =
                MinCut.find(
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
        System.out.println("MIN-CUT RESULT");
        System.out.println("---------------------------------");

        System.out.println(
                "Minimum cut capacity: " +
                result.value
        );

        System.out.println(
                "Expected cut capacity: 25"
        );

        // =================================
        // SOURCE SIDE
        // =================================

        System.out.println("\nSource-side vertices:");

        for (String vertex : result.sourceSide) {
            System.out.println(
                    "  " + vertex
            );
        }

        // =================================
        // SINK SIDE
        // =================================

        System.out.println("\nSink-side vertices:");

        for (String vertex : result.sinkSide) {
            System.out.println(
                    "  " + vertex
            );
        }

        // =================================
        // CRITICAL LINKS
        // =================================

        System.out.println("\nCritical links:");

        for (String edge : result.cutEdges) {
            System.out.println(
                    "  " + edge
            );
        }

        // =================================
        // CORRECTNESS CHECK
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("CORRECTNESS CHECK");
        System.out.println("---------------------------------");

        if (result.value == 25
                && !result.sourceSide.isEmpty()
                && !result.sinkSide.isEmpty()
                && !result.cutEdges.isEmpty()) {

            System.out.println(
                    "✓ Minimum cut capacity is correct."
            );

            System.out.println(
                    "✓ Source and sink partitions identified."
            );

            System.out.println(
                    "✓ Critical cut edges identified."
            );

        } else {

            System.out.println(
                    "✗ Min-cut validation failed."
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
        System.out.println("       MIN-CUT TEST COMPLETE");
        System.out.println("=================================");
    }
}