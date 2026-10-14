package mediamine.data;

import mediamine.m6_parallel.BloomFilter;

public class BloomFilterTest {

    public static void main(String[] args) {

        System.out.println("=========== BLOOM FILTER TEST ===========");

        // Create Bloom Filter
        BloomFilter filter = new BloomFilter(1000, 5);

        // Media identifiers / content identifiers
        String[] mediaItems = {
            "media_001",
            "media_002",
            "media_003",
            "media_004",
            "media_005"
        };

        // Add items
        for (String item : mediaItems) {
            filter.add(item);
        }

        System.out.println("Added items:");
        for (String item : mediaItems) {
            System.out.println("  " + item);
        }

        // Test items that were inserted
        System.out.println("\nMembership checks:");

        for (String item : mediaItems) {
            System.out.println(
                item + " -> " + filter.mightContain(item)
            );
        }

        // Test items that were NOT inserted
        String[] newItems = {
            "media_100",
            "media_200",
            "media_300"
        };

        System.out.println("\nNon-inserted item checks:");

        for (String item : newItems) {
            System.out.println(
                item + " -> " + filter.mightContain(item)
            );
        }

        System.out.println("\n==========================================");
        System.out.println("Bloom Filter test completed.");
    }
}