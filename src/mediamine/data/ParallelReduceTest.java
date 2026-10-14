package mediamine.data;

import mediamine.m6_parallel.ParallelReduce;

import java.util.Arrays;

public class ParallelReduceTest {

    public static void main(String[] args) {

        System.out.println("=========== PARALLEL REDUCE TEST ===========");

        // Sample media statistics
        int[] values = {
            10, 20, 30, 40, 50,
            60, 70, 80, 90, 100
        };

        int parallelism = 4;

        System.out.println(
            "Input values = " + Arrays.toString(values)
        );

        System.out.println(
            "Parallelism = " + parallelism
        );

        // Calculate sum using parallel reduction
        long parallelSum =
            ParallelReduce.sum(values, parallelism);

        // Calculate sequential sum for verification
        long sequentialSum = 0;

        for (int value : values) {
            sequentialSum += value;
        }

        System.out.println(
            "Parallel sum   = " + parallelSum
        );

        System.out.println(
            "Sequential sum = " + sequentialSum
        );

        System.out.println(
            "Results match  = "
            + (parallelSum == sequentialSum)
        );

        System.out.println();
        System.out.println("============================================");
        System.out.println("Parallel Reduce test completed.");
    }
}