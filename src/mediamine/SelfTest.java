package mediamine;
import java.util.*;
import mediamine.m1_strings.*; import mediamine.m2_suffix.*; import mediamine.m3_dp.*; import mediamine.m4_flow.*; import mediamine.m5_np.*; import mediamine.m6_parallel.*;
/** Lightweight smoke tests for all six MediaMine modules. */
public class SelfTest {
 private static int passed,failed; private static void check(String n,boolean ok){if(ok)passed++;else{failed++;System.out.println("FAILED: "+n);}}
 public static void main(String[]args){passed=failed=0;
  check("KMP",KMP.search("media analytics","analytics").equals(Arrays.asList(6)));
  check("Z",ZFunction.isRepeatedPattern("abcabcabc"));
  check("RabinKarp",RabinKarp.search("abcabc","abc").equals(Arrays.asList(0,3)));
  check("SA",Arrays.equals(new SuffixArray("banana").getSuffixArray(),SuffixArray.buildNaive("banana")));
  check("SAIS",Arrays.equals(SAIS.buildSuffixArray("banana"),SuffixArray.buildNaive("banana")));
  check("Levenshtein",Levenshtein.distance("media","medai")==2);
  check("Damerau",DamerauLevenshtein.distance("ca","ac")==1);
  FlowNetwork g=new FlowNetwork(4);g.addEdge(0,1,3);g.addEdge(0,2,2);g.addEdge(1,3,2);g.addEdge(2,3,2);check("Dinic",Dinic.maxFlow(g,0,3)==4);
  List<SAT3.Clause> cs=Arrays.asList(new SAT3.Clause(new SAT3.Literal(0,true),new SAT3.Literal(1,true),new SAT3.Literal(2,true)));check("3SAT",SAT3.solve(3,cs)!=null);
  int[] a={5,1,4,2,3};RandomizedAlgorithms.quickSort(a,new Random(1));check("QuickSort",Arrays.equals(a,new int[]{1,2,3,4,5}));
  check("MillerRabin",RandomizedAlgorithms.millerRabin(1000000007L));
  System.out.println("MediaMine self-test: Passed="+passed+" Failed="+failed+(failed==0?" -> ALL TESTS PASSED":""));
 }
}
