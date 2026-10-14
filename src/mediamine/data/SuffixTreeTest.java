package mediamine.data;

import java.util.List;

import mediamine.m2_suffix.SuffixTree;
import mediamine.model.MediaItem;

public class SuffixTreeTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      MEDIAMINE SUFFIX TREE");
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
        // SELECT ONE PRODUCT
        // =================================

        if (mediaItems.isEmpty()) {
            System.out.println("No media items found.");
            return;
        }

        MediaItem item = mediaItems.get(0);

        String text =
                item.getTitle().toLowerCase();

        String query = "milk";

        System.out.println("\n---------------------------------");
        System.out.println("TEXT FOR SUFFIX TREE");
        System.out.println("---------------------------------");

        System.out.println(
                "Product: " +
                item.getTitle()
        );

        System.out.println(
                "Text: " +
                text
        );

        System.out.println(
                "Query: " +
                query
        );

        // =================================
        // BUILD SUFFIX TREE
        // =================================

        long start =
                System.nanoTime();

        SuffixTree suffixTree =
                new SuffixTree(text);

        long buildEnd =
                System.nanoTime();

        // =================================
        // SEARCH
        // =================================

        boolean contains =
                suffixTree.contains(query);

        List<Integer> positions =
                suffixTree.findAll(query);

        long searchEnd =
                System.nanoTime();

        double buildTime =
                (buildEnd - start) / 1_000_000.0;

        double searchTime =
                (searchEnd - buildEnd) / 1_000_000.0;

        // =================================
        // SEARCH RESULTS
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("SUFFIX TREE SEARCH");
        System.out.println("---------------------------------");

        System.out.println(
                "Contains query: " +
                contains
        );

        System.out.println(
                "Match positions: " +
                positions
        );

        System.out.println(
                "Number of occurrences: " +
                positions.size()
        );

        // =================================
        // LONGEST REPEATED SUBSTRING
        // =================================

        String longestRepeated =
                suffixTree.longestRepeatedSubstring();

        System.out.println("\n---------------------------------");
        System.out.println("LONGEST REPEATED SUBSTRING");
        System.out.println("---------------------------------");

        if (longestRepeated.isEmpty()) {

            System.out.println(
                    "No repeated substring found."
            );

        } else {

            System.out.println(
                    "Longest repeated substring: \"" +
                    longestRepeated +
                    "\""
            );
        }

        // =================================
        // PERFORMANCE
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("PERFORMANCE");
        System.out.println("---------------------------------");

        System.out.printf(
                "Suffix Tree construction: %.4f ms%n",
                buildTime
        );

        System.out.printf(
                "Search time: %.4f ms%n",
                searchTime
        );

        // =================================
        // DATASET SAMPLE SEARCH
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("DATASET SEARCH");
        System.out.println("---------------------------------");

        int matchedItems = 0;

        long datasetStart =
                System.nanoTime();

        for (MediaItem mediaItem : mediaItems) {

            if (mediaItem.getTitle() == null) {
                continue;
            }

            String title =
                    mediaItem.getTitle().toLowerCase();

            if (title.isEmpty()) {
                continue;
            }

            SuffixTree tree =
                    new SuffixTree(title);

            if (tree.contains(query)) {
                matchedItems++;
            }
        }

        long datasetEnd =
                System.nanoTime();

        double datasetTime =
                (datasetEnd - datasetStart)
                        / 1_000_000.0;

        System.out.println(
                "Query: " +
                query
        );

        System.out.println(
                "Matched items: " +
                matchedItems
        );

        System.out.printf(
                "Dataset search time: %.4f ms%n",
                datasetTime
        );

        // =================================
        // COMPLETE
        // =================================

        System.out.println("\n=================================");
        System.out.println("   SUFFIX TREE TEST COMPLETE");
        System.out.println("=================================");
    }
}