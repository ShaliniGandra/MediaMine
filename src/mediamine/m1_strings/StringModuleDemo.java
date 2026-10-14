package mediamine.m1_strings;
import java.util.Arrays; import java.util.List;
/** Module 1: keyword and topic detection in MediaMine articles/transcripts. */
public class StringModuleDemo {
 public static void run(){
  System.out.println("=========== MODULE 1: STRING ALGORITHMS ===========");
  String corpus="Climate summit discusses renewable energy. Media analytics tracks climate policy and energy transition. Renewable energy appears in today's report.";
  System.out.println("[KMP] keyword 'renewable energy' positions = "+KMP.search(corpus,"renewable energy"));
  System.out.println("[Z-Function] repeated phrase unit = "+ZFunction.smallestRepeatingUnit("topic-topic-topic"));
  System.out.println("[Rabin-Karp] 'analytics' positions = "+RabinKarp.search(corpus,"analytics"));
  List<String> topics=Arrays.asList("climate","energy","policy","sports","election");
  System.out.println("[Aho-Corasick] topic matches = "+new AhoCorasick(topics).search(corpus));
 }
 public static void main(String[] args){run();}
}
