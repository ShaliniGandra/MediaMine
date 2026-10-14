package mediamine.data;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import mediamine.m5_np.Reductions;
import mediamine.m5_np.SAT3;
import mediamine.m5_np.SAT3.Clause;
import mediamine.m5_np.SAT3.Literal;

public class NPModuleDemo {

    public static void main(String[] args) {

        System.out.println(
            "=========== MODULE 5: NP-COMPLETENESS & APPROXIMATION ==========="
        );

        // ---------------------------------------------------------
        // 1. 3-SAT TEST
        // ---------------------------------------------------------
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

        boolean[] assignment = SAT3.solve(3, clauses);

        System.out.println(
            "[3-SAT] satisfiable = "
            + (assignment != null)
            + ", assignment = "
            + Arrays.toString(assignment)
        );

        // ---------------------------------------------------------
        // 2. 3-SAT -> CLIQUE REDUCTION
        // ---------------------------------------------------------
        Reductions.Graph graph =
            Reductions.threeSatToClique(clauses);

        int maxClique =
            Reductions.maxCliqueSize(graph);

        System.out.println(
            "[3-SAT -> CLIQUE] graph vertices = "
            + graph.n
            + ", max clique size = "
            + maxClique
            + " (target = number of clauses)"
        );

        // ---------------------------------------------------------
        // 3. TEST A VALID CLIQUE
        // ---------------------------------------------------------
        int[] candidateClique = {0, 4, 8};

        boolean isClique =
            Reductions.isClique(graph, candidateClique);

        System.out.println(
            "[CLIQUE TEST] vertices "
            + Arrays.toString(candidateClique)
            + " form a clique = "
            + isClique
        );

        // ---------------------------------------------------------
        // 4. VERTEX COVER 2-APPROXIMATION
        // ---------------------------------------------------------
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
            Reductions.vertexCover2Approx(conflict);

        System.out.println(
            "[Vertex-Cover 2-Approx] cover = "
            + cover
            + ", size = "
            + cover.size()
        );

        // ---------------------------------------------------------
        // 5. RELATIONSHIPS
        // ---------------------------------------------------------
        System.out.println(
            "[Relationships] Independent Set is complement "
            + "of Vertex Cover."
        );

        System.out.println(
            "[Relationships] CLIQUE in G is Independent Set "
            + "in complement(G)."
        );

        System.out.println(
            "==============================================================="
        );
        System.out.println("MODULE 5 TEST COMPLETED");
    }
}