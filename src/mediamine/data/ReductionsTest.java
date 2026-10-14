package mediamine.data;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import mediamine.m5_np.Reductions;
import mediamine.m5_np.SAT3;
import mediamine.m5_np.SAT3.Clause;
import mediamine.m5_np.SAT3.Literal;



public class ReductionsTest {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("      MEDIAMINE REDUCTIONS TEST");
        System.out.println("=================================");

        // =================================
        // CREATE 3-SAT INSTANCE
        // =================================

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

        System.out.println("\n---------------------------------");
        System.out.println("3-SAT INSTANCE");
        System.out.println("---------------------------------");

        for (int i = 0; i < clauses.size(); i++) {

            System.out.println(
                    "Clause " +
                    (i + 1) +
                    ": " +
                    clauses.get(i)
            );
        }

        // =================================
        // 3-SAT -> CLIQUE
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("3-SAT -> CLIQUE");
        System.out.println("---------------------------------");

        long start =
                System.nanoTime();

        Reductions.Graph graph =
                Reductions.threeSatToClique(
                        clauses
                );

        long end =
                System.nanoTime();

        double reductionTime =
                (end - start) / 1_000_000.0;

        System.out.println(
                "Graph vertices: " +
                graph.n
        );

        System.out.println(
                "Expected vertices: " +
                (clauses.size() * 3)
        );

        System.out.printf(
                "Reduction time: %.4f ms%n",
                reductionTime
        );

        // =================================
        // MAXIMUM CLIQUE
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("MAXIMUM CLIQUE");
        System.out.println("---------------------------------");

        start =
                System.nanoTime();

        int maxClique =
                Reductions.maxCliqueSize(
                        graph
                );

        end =
                System.nanoTime();

        double cliqueTime =
                (end - start) / 1_000_000.0;

        System.out.println(
                "Maximum clique size: " +
                maxClique
        );

        System.out.println(
                "Target clique size: " +
                clauses.size()
        );

        System.out.printf(
                "Maximum clique search time: %.4f ms%n",
                cliqueTime
        );

        if (maxClique >= clauses.size()) {

            System.out.println(
                    "✓ A clique of target size exists."
            );

        } else {

            System.out.println(
                    "✗ Target clique does not exist."
            );
        }

        // =================================
        // CLIQUE VERIFICATION
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("CLIQUE VERIFICATION");
        System.out.println("---------------------------------");

        int[] candidateClique = {
                0,
                3,
                6
        };

        boolean validClique =
                Reductions.isClique(
                        graph,
                        candidateClique
                );

        System.out.println(
                "Candidate vertices: " +
                Arrays.toString(candidateClique)
        );

        System.out.println(
                "Is valid clique: " +
                validClique
        );

        if (validClique) {

            System.out.println(
                    "✓ Clique verification passed."
            );

        } else {

            System.out.println(
                    "⚠ Candidate is not a clique."
            );
        }

        // =================================
        // VERTEX COVER 2-APPROXIMATION
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("VERTEX COVER 2-APPROXIMATION");
        System.out.println("---------------------------------");

        Reductions.Graph conflict =
                new Reductions.Graph(6);

        conflict.addEdge(0, 1);
        conflict.addEdge(1, 2);
        conflict.addEdge(2, 3);
        conflict.addEdge(3, 4);
        conflict.addEdge(4, 5);
        conflict.addEdge(0, 5);
        conflict.addEdge(1, 4);

        Set<Integer> cover =
                Reductions.vertexCover2Approx(
                        conflict
                );

        System.out.println(
                "Graph vertices: " +
                conflict.n
        );

        System.out.println(
                "Approximate vertex cover: " +
                cover
        );

        System.out.println(
                "Cover size: " +
                cover.size()
        );

        // =================================
        // VERIFY VERTEX COVER
        // =================================

        boolean coverValid = true;

        outer:
        for (int u = 0; u < conflict.n; u++) {

            for (int v = u + 1; v < conflict.n; v++) {

                if (conflict.hasEdge(u, v)
                        && !cover.contains(u)
                        && !cover.contains(v)) {

                    coverValid = false;
                    break outer;
                }
            }
        }

        System.out.println(
                "Valid vertex cover: " +
                coverValid
        );

        if (coverValid) {

            System.out.println(
                    "✓ Vertex cover approximation is valid."
            );

        } else {

            System.out.println(
                    "✗ Vertex cover validation failed."
            );
        }

        // =================================
        // RELATIONSHIPS
        // =================================

        System.out.println("\n---------------------------------");
        System.out.println("NP RELATIONSHIPS");
        System.out.println("---------------------------------");

        System.out.println(
                "CLIQUE in G = Independent Set in complement(G)"
        );

        System.out.println(
                "Independent Set = complement of Vertex Cover"
        );

        // =================================
        // COMPLETE
        // =================================

        System.out.println("\n=================================");
        System.out.println("    REDUCTIONS TEST COMPLETE");
        System.out.println("=================================");
    }
}