package mediamine.m2_suffix;
import java.util.Arrays; import java.util.List; import mediamine.model.MediaDocument;
/** Module 2: suffix structures for transcript/news indexing and similarity. */
public class SuffixModuleDemo {
 public static void run(){
  System.out.println("=========== MODULE 2: SUFFIX STRUCTURES ===========");
  MediaDocument d1=new MediaDocument("N1","Climate Report","Renewable energy policy changes across cities.");
  MediaDocument d2=new MediaDocument("N2","Energy Report","Renewable energy investment grows across cities.");
  String corpus=d1.getContent()+"#"+d2.getContent();
  SuffixArray sa=new SuffixArray(corpus);
  System.out.println("[Suffix Array] search 'Renewable energy' = "+sa.search("Renewable energy"));
  int[] fast=SAIS.buildSuffixArray("banana");
  System.out.println("[SA-IS] banana = "+Arrays.toString(fast));
  int[] lcp=LCPKasai.buildLCP(corpus,fast=SAIS.buildSuffixArray(corpus));
  System.out.println("[LCP/Kasai] longest repeated substring = '"+LCPKasai.longestRepeatedSubstring(corpus,fast,lcp)+"'");
  SuffixTree st=new SuffixTree(corpus);
  System.out.println("[Suffix Tree] contains 'cities' = "+st.contains("cities"));
  System.out.println("[Suffix Tree] occurrences of 'energy' = "+st.findAll("energy"));
  SuffixAutomaton sam=new SuffixAutomaton(corpus);
  System.out.println("[Suffix Automaton] contains 'Renewable energy' = "+sam.contains("Renewable energy")+", states = "+sam.stateCount());
 }
 public static void main(String[] args){run();}
}
