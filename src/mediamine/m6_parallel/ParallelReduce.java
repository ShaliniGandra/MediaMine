package mediamine.m6_parallel;
import java.util.concurrent.*;
/** Parallel reduction for large media statistics. */
public final class ParallelReduce {
 private ParallelReduce(){}
 public static long sum(int[] a,int parallelism){ForkJoinPool pool=new ForkJoinPool(parallelism);try{return pool.invoke(new Task(a,0,a.length));}finally{pool.shutdown();}}
 private static final class Task extends RecursiveTask<Long>{
  final int[] a; final int l,r;
  Task(int[] a,int l,int r){this.a=a;this.l=l;this.r=r;}
  protected Long compute(){
   if(r-l<=10_000){long s=0;for(int i=l;i<r;i++)s+=a[i];return s;}
   int m=(l+r)>>>1; Task x=new Task(a,l,m), y=new Task(a,m,r); x.fork(); long b=y.compute(); return x.join()+b;
  }
 }
}
