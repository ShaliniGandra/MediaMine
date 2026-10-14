package mediamine.m4_flow;
import java.util.*;
/** Module 4: media-distribution capacity and content-to-channel assignment. */
public class FlowModuleDemo {
 private static FlowNetwork buildNetwork(){
  FlowNetwork g=new FlowNetwork(8); String[] n={"SOURCE","NEWSROOM","VIDEO","CDN-A","CDN-B","CHANNEL-1","CHANNEL-2","SINK"};
  for(int i=0;i<n.length;i++)g.setName(i,n[i]);
  g.addEdge(0,1,80);g.addEdge(0,2,70);g.addEdge(1,3,45);g.addEdge(1,4,30);g.addEdge(2,3,25);g.addEdge(2,4,40);g.addEdge(3,5,50);g.addEdge(3,6,20);g.addEdge(4,5,20);g.addEdge(4,6,50);g.addEdge(5,7,65);g.addEdge(6,7,55); return g;
 }
 public static void run(){
  System.out.println("=========== MODULE 4: NETWORK FLOW ==========="); FlowNetwork base=buildNetwork();
  System.out.println("[Ford-Fulkerson] max flow = "+FordFulkerson.maxFlow(base.copy(),0,7));
  System.out.println("[Edmonds-Karp] max flow = "+EdmondsKarp.maxFlow(base.copy(),0,7));
  FlowNetwork d=base.copy(); System.out.println("[Dinic] max flow = "+Dinic.maxFlow(d,0,7)); System.out.print(d.flowSummary());
  MinCut.Result cut=MinCut.find(base,0,7); System.out.println("[Min-Cut] capacity = "+cut.value+"; critical links = "+cut.cutEdges);
  BipartiteMatching bm=new BipartiteMatching(4,4); // four media items -> eligible channels
  bm.addEdge(0,0);bm.addEdge(0,1);bm.addEdge(1,1);bm.addEdge(1,2);bm.addEdge(2,0);bm.addEdge(2,3);bm.addEdge(3,2);bm.addEdge(3,3);
  System.out.println("[Bipartite Matching] maximum content-channel assignments = "+bm.maxMatching());
  System.out.println("  match(left) = "+Arrays.toString(bm.matchOfLeft()));
 }
 public static void main(String[] args){run();}
}
