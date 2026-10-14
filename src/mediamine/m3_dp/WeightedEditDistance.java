package mediamine.m3_dp;
/** Weighted edit distance: configurable insertion, deletion and substitution costs. */
public final class WeightedEditDistance {
 private WeightedEditDistance(){}
 public static int distance(String a,String b,int insertCost,int deleteCost,int substituteCost){
  int n=a.length(),m=b.length();int[] prev=new int[m+1],cur=new int[m+1];for(int j=1;j<=m;j++)prev[j]=j*insertCost;
  for(int i=1;i<=n;i++){cur[0]=i*deleteCost;for(int j=1;j<=m;j++){int sub=prev[j-1]+(a.charAt(i-1)==b.charAt(j-1)?0:substituteCost);cur[j]=Math.min(Math.min(prev[j]+deleteCost,cur[j-1]+insertCost),sub);}int[]t=prev;prev=cur;cur=t;}return prev[m];
 }
}
