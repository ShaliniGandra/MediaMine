package mediamine.m6_parallel;
import java.util.*;
/** Randomized algorithms used by MediaMine. */
public final class RandomizedAlgorithms {
 private RandomizedAlgorithms(){}
 public static void quickSort(int[] a, Random rnd){quickSort(a,0,a.length-1,rnd);}
 private static void quickSort(int[] a,int l,int r,Random rnd){if(l>=r)return;int p=l+rnd.nextInt(r-l+1);swap(a,p,r);int i=l;for(int j=l;j<r;j++)if(a[j]<=a[r])swap(a,i++,j);swap(a,i,r);quickSort(a,l,i-1,rnd);quickSort(a,i+1,r,rnd);}
 private static void swap(int[]a,int i,int j){int t=a[i];a[i]=a[j];a[j]=t;}
 public static <T> List<T> reservoirSample(Iterable<T> stream,int k,Random rnd){List<T> res=new ArrayList<>(k);long seen=0;for(T x:stream){seen++;if(res.size()<k)res.add(x);else{long j=nextLongBounded(rnd,seen);if(j<k)res.set((int)j,x);}}return res;}
 private static long nextLongBounded(Random r,long bound){long bits,val;do{bits=r.nextLong()&Long.MAX_VALUE;val=bits%bound;}while(bits-val+(bound-1)<0);return val;}
 public static boolean millerRabin(long n){if(n<2)return false;int[] small={2,3,5,7,11,13,17,19,23,29,31,37};for(int p:small){if(n==p)return true;if(n%p==0)return false;}long d=n-1;int s=0;while((d&1)==0){d>>=1;s++;}int[] bases={2,325,9375,28178,450775,9780504,1795265022};for(long a:bases){if(a%n==0)continue;long x=powMod(a%n,d,n);if(x==1||x==n-1)continue;boolean comp=true;for(int r=1;r<s;r++){x=mulMod(x,x,n);if(x==n-1){comp=false;break;}}if(comp)return false;}return true;}
 private static long mulMod(long a,long b,long m){return java.math.BigInteger.valueOf(a).multiply(java.math.BigInteger.valueOf(b)).mod(java.math.BigInteger.valueOf(m)).longValue();}
 private static long powMod(long a,long e,long m){long r=1%m;while(e>0){if((e&1)!=0)r=mulMod(r,a,m);a=mulMod(a,a,m);e>>=1;}return r;}
}
