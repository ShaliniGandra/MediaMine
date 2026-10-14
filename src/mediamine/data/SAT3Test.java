package mediamine.data;

import java.util.Arrays;
import java.util.List;

import mediamine.m5_np.SAT3;
import mediamine.m5_np.SAT3.Clause;
import mediamine.m5_np.SAT3.Literal;

public class SAT3Test {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("        MEDIAMINE 3-SAT TEST");
        System.out.println("=================================");

        // =================================
        // CREATE 3-SAT FORMULA
        // =================================

        /*
         * Formula:
         *
         * (x1 OR x2 OR NOT x3)
         * AND
         * (NOT x1 OR x2 OR x3)
         * AND
         * (x1 OR NOT x2 OR x3)
         */

        List<Clause> clauses = Arrays.asList(

                new Clause(
                        new Literal(0, true),
                        new Literal(1, true),
                        new Literal(2, false)
                ),

                new Clause(
                        new Literal(0, false),
                        new Literal(1, true),
                        new Literal(2, true)
                ),

                new Clause(
                        new Literal(0, true),
                        new Literal(1, false),
                        new Literal(2, true)
                )
        );

        // =================================
        // DISPLAY FORMULA
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("3-SAT FORMULA");
        System.out.println("---------------------------------");

        for (int i = 0; i < clauses.size(); i++) {

            System.out.println(
                    "Clause " + (i + 1) +
                    ": " + clauses.get(i)
            );
        }

        // =================================
        // SOLVE
        // =================================

        long start =
                System.nanoTime();

        boolean[] assignment =
                SAT3.solve(
                        3,
                        clauses
                );

        long end =
                System.nanoTime();

        double executionTime =
                (end - start) / 1_000_000.0;

        // =================================
        // RESULT
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("3-SAT RESULT");
        System.out.println("---------------------------------");

        if (assignment != null) {

            System.out.println(
                    "Satisfiable: true"
            );

            System.out.println(
                    "Assignment: " +
                    Arrays.toString(assignment)
            );

        } else {

            System.out.println(
                    "Satisfiable: false"
            );
        }

        // =================================
        // VERIFY ASSIGNMENT
        // =================================

        if (assignment != null) {

            boolean allClausesSatisfied =
                    true;

            for (Clause clause : clauses) {

                if (!clause.eval(assignment)) {

                    allClausesSatisfied =
                            false;

                    break;
                }
            }

            System.out.println(
                    "All clauses satisfied: " +
                    allClausesSatisfied
            );

            if (allClausesSatisfied) {

                System.out.println(
                        "✓ Assignment verified successfully."
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
                "Number of variables: 3"
        );

        System.out.println(
                "Number of clauses: " +
                clauses.size()
        );

        // =================================
        // UNSAT TEST
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("UNSATISFIABLE TEST");
        System.out.println("---------------------------------");

        /*
         * These clauses cannot all be satisfied:
         *
         * (x1 OR x1 OR x1)
         * (NOT x1 OR NOT x1 OR NOT x1)
         */

        List<Clause> unsatClauses = Arrays.asList(

                new Clause(
                        new Literal(0, true),
                        new Literal(0, true),
                        new Literal(0, true)
                ),

                new Clause(
                        new Literal(0, false),
                        new Literal(0, false),
                        new Literal(0, false)
                )
        );

        boolean[] unsatResult =
                SAT3.solve(
                        1,
                        unsatClauses
                );

        if (unsatResult == null) {

            System.out.println(
                    "Satisfiable: false"
            );

            System.out.println(
                    "✓ Unsatisfiable test passed."
            );

        } else {

            System.out.println(
                    "✗ Unsatisfiable test failed."
            );
        }

        // =================================
        // COMPLETE
        // =================================

        System.out.println("\n=================================");
        System.out.println("       3-SAT TEST COMPLETE");
        System.out.println("=================================");
    }
}