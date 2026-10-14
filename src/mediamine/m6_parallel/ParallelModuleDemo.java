package mediamine.m6_parallel;
import java.util.*;
/** Module 6: randomized and parallel algorithms for continuous media analytics. */
public class ParallelModuleDemo {
 public static void run(){
  System.out.println("=========== MODULE 6: RANDOMIZED & PARALLEL ALGORITHMS ===========");
  int[] a={42,7,19,3,88,12,55,1,34}; RandomizedAlgorithms.quickSort(a,new Random(42)); System.out.println("[Randomized QuickSort] "+Arrays.toString(a));
  List<String> events=new ArrayList<>();for(int i=1;i<=100;i++)events.add("VIEW-"+i);System.out.println("[Reservoir Sampling] sample = "+RandomizedAlgorithms.reservoirSample(events,5,new Random(42)));
  System.out.println("[Miller-Rabin] 1000000007 prime? "+RandomizedAlgorithms.millerRabin(1000000007L));
  BloomFilter bf=new BloomFilter(512,4);bf.add("MEDIA-101");bf.add("MEDIA-202");System.out.println("[Bloom Filter] MEDIA-101? "+bf.mightContain("MEDIA-101")+", MEDIA-999? "+bf.mightContain("MEDIA-999"));
  int[] data=new int[100_000];Arrays.fill(data,2);System.out.println("[Parallel Reduce] sum = "+ParallelReduce.sum(data,Runtime.getRuntime().availableProcessors()));
  System.out.println("[Brent's Theorem] "+BrentTheorem.explain(1_000_000,2_000,8));
 }
 public static void main(String[] args){run();}
}
