package mediamine.data;

import java.util.List;

import mediamine.model.MediaItem;

public class SuffixArrayTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("   MEDIAMINE SUFFIX ARRAY TEST");
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
        // CREATE SEARCH SERVICE
        // =================================

        SuffixSearchService suffixService =
                new SuffixSearchService(mediaItems);

        String query = "milk";

        System.out.println(
                "\nSearch Query: " + query
        );

        // =================================
        // SUFFIX ARRAY SEARCH
        // =================================

        long start = System.nanoTime();

        List<MediaItem> results =
                suffixService.searchUsingSuffixArray(query);

        long end = System.nanoTime();

        double time =
                (end - start) / 1_000_000.0;

        // =================================
        // RESULTS
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("SUFFIX ARRAY RESULTS");
        System.out.println("---------------------------------");

        System.out.println(
                "Matches found: " +
                results.size()
        );

        System.out.printf(
                "Execution time: %.4f ms%n",
                time
        );

        // =================================
        // SAMPLE RESULTS
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("SAMPLE RESULTS");
        System.out.println("---------------------------------");

        int limit =
                Math.min(10, results.size());

        for (int i = 0; i < limit; i++) {

            System.out.println(
                    (i + 1) + ". " +
                    results.get(i)
            );
        }

        // =================================
        // POSITION TEST
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("POSITION TEST");
        System.out.println("---------------------------------");

        if (!results.isEmpty()) {

            MediaItem firstItem =
                    results.get(0);

            List<Integer> positions =
                    suffixService.findPositions(
                            firstItem,
                            query
                    );

            System.out.println(
                    "Item: " +
                    firstItem.getTitle()
            );

            System.out.println(
                    "Pattern: " +
                    query
            );

            System.out.println(
                    "Match positions: " +
                    positions
            );
        }

        // =================================
        // COMPLETE
        // =================================

        System.out.println("\n=================================");
        System.out.println("   SUFFIX ARRAY TEST COMPLETE");
        System.out.println("=================================");
    }
}