package mediamine.data;

import java.util.Arrays;

import mediamine.m6_parallel.BloomFilter;
import mediamine.m6_parallel.BrentTheorem;
import mediamine.m6_parallel.ParallelReduce;
import mediamine.m6_parallel.RandomizedAlgorithms;

public class ParallelModuleDemo {

    public static void main(String[] args) {

        System.out.println(
            "=========== MODULE 6: RANDOMIZED & PARALLEL ALGORITHMS ==========="
        );

        // ---------------------------------------------------------
        // 1. Randomized QuickSort
        // ---------------------------------------------------------
        int[] values = {
            42, 7, 19, 3, 88, 12, 55, 1, 34
        };

        RandomizedAlgorithms.quickSort(
            values,
            new java.util.Random(42)
        );

        System.out.println(
            "[Randomized QuickSort] "
            + Arrays.toString(values)
        );

        // ---------------------------------------------------------
        // 2. Reservoir Sampling
        // ---------------------------------------------------------
        java.util.List<String> events =
            new java.util.ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            events.add("VIEW-" + i);
        }

        java.util.List<String> sample =
            RandomizedAlgorithms.reservoirSample(
                events,
                5,
                new java.util.Random(42)
            );

        System.out.println(
            "[Reservoir Sampling] sample = "
            + sample
        );

        // ---------------------------------------------------------
        // 3. Miller-Rabin
        // ---------------------------------------------------------
        System.out.println(
            "[Miller-Rabin] 1000000007 prime? "
            + RandomizedAlgorithms.millerRabin(1000000007L)
        );

        // ---------------------------------------------------------
        // 4. Bloom Filter
        // ---------------------------------------------------------
        BloomFilter filter =
            new BloomFilter(512, 4);

        filter.add("MEDIA-101");
        filter.add("MEDIA-202");

        System.out.println(
            "[Bloom Filter] MEDIA-101? "
            + filter.mightContain("MEDIA-101")
            + ", MEDIA-999? "
            + filter.mightContain("MEDIA-999")
        );

        // ---------------------------------------------------------
        // 5. Parallel Reduction
        // ---------------------------------------------------------
        int[] data = new int[100_000];

        Arrays.fill(data, 2);

        int processors =
            Runtime.getRuntime().availableProcessors();

        long sum =
            ParallelReduce.sum(data, processors);

        System.out.println(
            "[Parallel Reduce] sum = "
            + sum
        );

        // ---------------------------------------------------------
        // 6. Brent's Theorem
        // ---------------------------------------------------------
        System.out.println(
            "[Brent's Theorem] "
            + BrentTheorem.explain(
                1_000_000,
                2_000,
                8
            )
        );

        // ---------------------------------------------------------
        // Completion
        // ---------------------------------------------------------
        System.out.println(
            "=============================================================="
        );
        System.out.println(
            "MODULE 6 TEST COMPLETED"
        );
    }
}