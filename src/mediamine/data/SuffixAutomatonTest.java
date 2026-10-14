package mediamine.data;

import java.util.List;

import mediamine.m2_suffix.SuffixAutomaton;
import mediamine.model.MediaItem;

public class SuffixAutomatonTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("   MEDIAMINE SUFFIX AUTOMATON");
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
        // SEARCH QUERY
        // =================================

        String query = "milk";

        System.out.println(
                "\nSearch Query: " + query
        );

        // =================================
        // BUILD AND SEARCH
        // =================================

        long start =
                System.nanoTime();

        int matchedItems = 0;
        int totalStates = 0;

        for (MediaItem item : mediaItems) {

            if (item.getTitle() == null) {
                continue;
            }

            String title =
                    item.getTitle().toLowerCase();

            if (title.isEmpty()) {
                continue;
            }

            SuffixAutomaton automaton =
                    new SuffixAutomaton(title);

            if (automaton.contains(query)) {
                matchedItems++;
            }

            totalStates +=
                    automaton.stateCount();
        }

        long end =
                System.nanoTime();

        double executionTime =
                (end - start) / 1_000_000.0;

        // =================================
        // RESULTS
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("SUFFIX AUTOMATON RESULTS");
        System.out.println("---------------------------------");

        System.out.println(
                "Query: " + query
        );

        System.out.println(
                "Matched items: " +
                matchedItems
        );

        System.out.println(
                "Total automaton states: " +
                totalStates
        );

        System.out.printf(
                "Execution time: %.4f ms%n",
                executionTime
        );

        // =================================
        // SAMPLE MATCHES
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("SAMPLE MATCHES");
        System.out.println("---------------------------------");

        int sampleCount = 0;

        for (MediaItem item : mediaItems) {

            if (sampleCount >= 10) {
                break;
            }

            if (item.getTitle() == null) {
                continue;
            }

            String title =
                    item.getTitle().toLowerCase();

            if (title.isEmpty()) {
                continue;
            }

            SuffixAutomaton automaton =
                    new SuffixAutomaton(title);

            if (automaton.contains(query)) {

                sampleCount++;

                System.out.println(
                        sampleCount + ". " +
                        item
                );
            }
        }

        // =================================
        // SINGLE ITEM TEST
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("SINGLE ITEM TEST");
        System.out.println("---------------------------------");

        if (!mediaItems.isEmpty()) {

            MediaItem firstItem =
                    mediaItems.get(0);

            String title =
                    firstItem.getTitle().toLowerCase();

            SuffixAutomaton automaton =
                    new SuffixAutomaton(title);

            System.out.println(
                    "Product: " +
                    firstItem.getTitle()
            );

            System.out.println(
                    "Contains \"" +
                    query +
                    "\": " +
                    automaton.contains(query)
            );

            System.out.println(
                    "Automaton states: " +
                    automaton.stateCount()
            );
        }

        // =================================
        // COMPLETE
        // =================================

        System.out.println("\n=================================");
        System.out.println(" SUFFIX AUTOMATON TEST COMPLETE");
        System.out.println("=================================");
    }
}