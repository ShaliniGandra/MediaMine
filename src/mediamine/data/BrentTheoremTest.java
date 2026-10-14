package mediamine.data;

import mediamine.m6_parallel.BrentTheorem;

public class BrentTheoremTest {

    public static void main(String[] args) {

        System.out.println("=========== BRENT'S THEOREM TEST ===========");

        // Work (W), Span (S), and number of processors (p)
        long work = 1000;
        long span = 50;
        int processors = 10;

        // Calculate Brent's theorem upper bound
        double bound =
                BrentTheorem.upperBound(work, span, processors);

        System.out.println("Work (W)       = " + work);
        System.out.println("Span (S)       = " + span);
        System.out.println("Processors (p) = " + processors);

        System.out.println(
                "Upper bound = " + bound
        );

        // Use the built-in explanation method
        System.out.println();
        System.out.println(
                BrentTheorem.explain(work, span, processors)
        );

        System.out.println();
        System.out.println("=============================================");
        System.out.println("Brent's Theorem test completed.");
    }
}