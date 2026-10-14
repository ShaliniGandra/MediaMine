package mediamine.m5_np;
import java.util.*;
/** Module 5: NP-completeness and approximation for content-selection constraints. */
public class NPModuleDemo {
 public static void run(){
  System.out.println("=========== MODULE 5: NP-COMPLETENESS & APPROXIMATION ===========");
  List<SAT3.Clause> clauses=Arrays.asList(
   new SAT3.Clause(new SAT3.Literal(0,true),new SAT3.Literal(1,true),new SAT3.Literal(2,false)),
   new SAT3.Clause(new SAT3.Literal(0,false),new SAT3.Literal(1,true),new SAT3.Literal(2,true)),
   new SAT3.Clause(new SAT3.Literal(0,true),new SAT3.Literal(1,false),new SAT3.Literal(2,true)));
  boolean[] assignment=SAT3.solve(3,clauses); System.out.println("[3-SAT] satisfiable = "+(assignment!=null)+" assignment = "+Arrays.toString(assignment));
  Reductions.Graph g=Reductions.threeSatToClique(clauses); System.out.println("[3-SAT -> CLIQUE] graph vertices = "+g.n+", max clique size = "+Reductions.maxCliqueSize(g)+" (target = number of clauses)");
  Reductions.Graph conflict=new Reductions.Graph(6); conflict.addEdge(0,1);conflict.addEdge(1,2);conflict.addEdge(2,3);conflict.addEdge(3,4);conflict.addEdge(4,5);conflict.addEdge(0,5);conflict.addEdge(1,4);
  Set<Integer> cover=Reductions.vertexCover2Approx(conflict); System.out.println("[Vertex-Cover 2-Approx] cover = "+cover+", size = "+cover.size());
  System.out.println("[Relationships] Independent Set is complement of Vertex Cover; CLIQUE in G is Independent Set in complement(G).");
 }
 public static void main(String[] args){run();}
}
