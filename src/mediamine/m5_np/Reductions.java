package mediamine.m5_np;
import java.util.*;
/** Educational reductions: 3-SAT -> CLIQUE, and the CLIQUE/Independent-Set/Vertex-Cover relationship. */
public final class Reductions {
 public static final class Graph { public final int n; public final boolean[][] e; public Graph(int n){this.n=n;e=new boolean[n][n];} public void addEdge(int a,int b){if(a!=b)e[a][b]=e[b][a]=true;} public boolean hasEdge(int a,int b){return e[a][b];} }
 private Reductions(){}
 /** Builds the standard 3-SAT-to-CLIQUE graph: one vertex per literal occurrence; edges join compatible literals from different clauses. */
 public static Graph threeSatToClique(List<SAT3.Clause> clauses){
  int n=clauses.size()*3; Graph g=new Graph(n);
  for(int i=0;i<clauses.size();i++) for(int a=0;a<3;a++) for(int j=i+1;j<clauses.size();j++) for(int b=0;b<3;b++){
   SAT3.Literal x=clauses.get(i).lits[a], y=clauses.get(j).lits[b];
   if(x.var!=y.var || x.positive==y.positive) g.addEdge(i*3+a,j*3+b);
  }
  return g;
 }
 public static boolean isClique(Graph g,int[] vertices){for(int i=0;i<vertices.length;i++)for(int j=i+1;j<vertices.length;j++)if(!g.hasEdge(vertices[i],vertices[j]))return false;return true;}
 public static int maxCliqueSize(Graph g){
  int best=0, total=1<<g.n; if(g.n>25)throw new IllegalArgumentException("Demo brute force limited to 25 vertices");
  for(int mask=0;mask<total;mask++){int k=Integer.bitCount(mask);if(k<=best)continue;boolean ok=true;outer:for(int i=0;i<g.n;i++)if((mask&(1<<i))!=0)for(int j=i+1;j<g.n;j++)if((mask&(1<<j))!=0&&!g.hasEdge(i,j)){ok=false;break outer;}if(ok)best=k;}return best;
 }
 /** Standard greedy 2-approximation for minimum vertex cover. */
 public static Set<Integer> vertexCover2Approx(Graph g){
  Set<Integer> c=new LinkedHashSet<>(); boolean[][] used=new boolean[g.n][g.n];
  for(int u=0;u<g.n;u++)for(int v=u+1;v<g.n;v++)if(g.hasEdge(u,v)&&!used[u][v]){c.add(u);c.add(v);for(int a=0;a<g.n;a++)for(int b=a+1;b<g.n;b++)if((a==u||a==v||b==u||b==v)&&g.hasEdge(a,b))used[a][b]=true;}
  return c;
 }
}
