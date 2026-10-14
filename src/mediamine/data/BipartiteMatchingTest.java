package mediamine.data;

import java.util.List;

import mediamine.m4_flow.BipartiteMatching;
import mediamine.model.MediaItem;

public class BipartiteMatchingTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println(" MEDIAMINE BIPARTITE MATCHING TEST");
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
        // CREATE ASSIGNMENT PROBLEM
        // =================================
        /*
         * Left side  = Drivers
         * Right side = Delivery / processing tasks
         *
         * Driver 0 -> Tasks 0, 1
         * Driver 1 -> Tasks 0, 2
         * Driver 2 -> Tasks 1, 3
         * Driver 3 -> Tasks 2, 3
         *
         * Goal:
         * Assign the maximum number of drivers
         * to different tasks.
         */

        String[] drivers = {
                "Driver 0",
                "Driver 1",
                "Driver 2",
                "Driver 3"
        };

        String[] tasks = {
                "Search Task",
                "Database Task",
                "Analytics Task",
                "Dashboard Task"
        };

        BipartiteMatching matching =
                new BipartiteMatching(
                        drivers.length,
                        tasks.length
                );

        // Driver 0 -> Search, Database
        matching.addEdge(0, 0);
        matching.addEdge(0, 1);

        // Driver 1 -> Search, Analytics
        matching.addEdge(1, 0);
        matching.addEdge(1, 2);

        // Driver 2 -> Database, Dashboard
        matching.addEdge(2, 1);
        matching.addEdge(2, 3);

        // Driver 3 -> Analytics, Dashboard
        matching.addEdge(3, 2);
        matching.addEdge(3, 3);

        // =================================
        // DISPLAY GRAPH
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("ELIGIBILITY GRAPH");
        System.out.println("---------------------------------");

        System.out.println(
                "Driver 0 -> Search Task, Database Task"
        );

        System.out.println(
                "Driver 1 -> Search Task, Analytics Task"
        );

        System.out.println(
                "Driver 2 -> Database Task, Dashboard Task"
        );

        System.out.println(
                "Driver 3 -> Analytics Task, Dashboard Task"
        );

        // =================================
        // RUN MATCHING
        // =================================

        long start =
                System.nanoTime();

        int maximumMatching =
                matching.maxMatching();

        long end =
                System.nanoTime();

        double executionTime =
                (end - start) / 1_000_000.0;

        // =================================
        // GET ASSIGNMENTS
        // =================================

        int[] assignments =
                matching.matchOfLeft();

        // =================================
        // RESULT
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("MAXIMUM MATCHING RESULT");
        System.out.println("---------------------------------");

        System.out.println(
                "Maximum matching size: " +
                maximumMatching
        );

        System.out.println("\nAssignments:");

        int assignedCount = 0;

        for (int i = 0; i < assignments.length; i++) {

            if (assignments[i] != -1) {

                System.out.println(
                        drivers[i] +
                        " -> " +
                        tasks[assignments[i]]
                );

                assignedCount++;

            } else {

                System.out.println(
                        drivers[i] +
                        " -> No assignment"
                );
            }
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

        System.out.println(
                "Drivers: " +
                drivers.length
        );

        System.out.println(
                "Tasks: " +
                tasks.length
        );

        // =================================
        // CORRECTNESS CHECK
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("CORRECTNESS CHECK");
        System.out.println("---------------------------------");

        if (maximumMatching == drivers.length
                && assignedCount == drivers.length) {

            System.out.println(
                    "✓ All drivers received unique tasks."
            );

            System.out.println(
                    "✓ Maximum matching found successfully."
            );

        } else {

            System.out.println(
                    "⚠ Not all drivers could be assigned."
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
        System.out.println(" BIPARTITE MATCHING TEST COMPLETE");
        System.out.println("=================================");
    }
}