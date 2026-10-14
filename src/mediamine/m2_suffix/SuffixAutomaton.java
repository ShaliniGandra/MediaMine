package mediamine.m2_suffix;
import java.util.*;
/** Suffix automaton for substring-based retrieval over a transcript/content corpus. */
public class SuffixAutomaton {
 private static final class State { int link=-1,len=0; Map<Character,Integer> next=new HashMap<>(); }
 private final List<State> st=new ArrayList<>(); private int last;
 public SuffixAutomaton(String s){st.add(new State());last=0;for(char c:s.toCharArray())extend(c);}
 private void extend(char c){int cur=st.size();st.add(new State());st.get(cur).len=st.get(last).len+1;int p=last;while(p!=-1&&!st.get(p).next.containsKey(c)){st.get(p).next.put(c,cur);p=st.get(p).link;}if(p==-1)st.get(cur).link=0;else{int q=st.get(p).next.get(c);if(st.get(p).len+1==st.get(q).len)st.get(cur).link=q;else{int clone=st.size();State cp=new State();cp.len=st.get(p).len+1;cp.link=st.get(q).link;cp.next.putAll(st.get(q).next);st.add(cp);while(p!=-1&&st.get(p).next.getOrDefault(c,-1)==q){st.get(p).next.put(c,clone);p=st.get(p).link;}st.get(q).link=st.get(cur).link=clone;}}last=cur;}
 public boolean contains(String pattern){int v=0;for(char c:pattern.toCharArray()){Integer n=st.get(v).next.get(c);if(n==null)return false;v=n;}return true;}
 public int stateCount(){return st.size();}
}
