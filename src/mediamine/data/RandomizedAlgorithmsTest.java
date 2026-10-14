package mediamine.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import mediamine.m6_parallel.RandomizedAlgorithms;

public class RandomizedAlgorithmsTest {

    public static void main(String[] args) {

        System.out.println(
            "=========== RANDOMIZED ALGORITHMS TEST ==========="
        );

        // ---------------------------------------------------------
        // 1. RANDOMIZED QUICKSORT
        // ---------------------------------------------------------
        int[] values = {
            42, 15, 8, 23, 4, 16, 9, 31, 7
        };

        System.out.println(
            "Original array  = " + Arrays.toString(values)
        );

        Random random = new Random(42);

        RandomizedAlgorithms.quickSort(values, random);

        System.out.println(
            "Sorted array    = " + Arrays.toString(values)
        );

        // ---------------------------------------------------------
        // 2. RESERVOIR SAMPLING
        // ---------------------------------------------------------
        List<String> mediaStream = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            mediaStream.add("media_" + i);
        }

        int sampleSize = 5;

        List<String> sample =
            RandomizedAlgorithms.reservoirSample(
                mediaStream,
                sampleSize,
                new Random(42)
            );

        System.out.println();
        System.out.println(
            "Media stream size = " + mediaStream.size()
        );

        System.out.println(
            "Sample size       = " + sample.size()
        );

        System.out.println(
            "Reservoir sample  = " + sample
        );

        // ---------------------------------------------------------
        // 3. MILLER-RABIN PRIMALITY TEST
        // ---------------------------------------------------------
        long[] numbers = {
            2, 3, 17, 97, 100, 221, 1009
        };

        System.out.println();
        System.out.println("Miller-Rabin primality checks:");

        for (long number : numbers) {

            boolean prime =
                RandomizedAlgorithms.millerRabin(number);

            System.out.println(
                number + " -> prime = " + prime
            );
        }

        System.out.println();
        System.out.println(
            "=================================================="
        );

        System.out.println(
            "Randomized Algorithms test completed."
        );
    }
}