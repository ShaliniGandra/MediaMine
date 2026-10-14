package mediamine.m6_parallel;
import java.nio.charset.StandardCharsets; import java.util.*;
/** Bloom filter for fast probabilistic membership checks on media identifiers. */
public class BloomFilter {
 private final BitSet bits; private final int k; private final int size;
 public BloomFilter(int size,int hashes){if(size<=0||hashes<=0)throw new IllegalArgumentException();this.size=size;this.k=hashes;this.bits=new BitSet(size);}
 private long h(String s,int seed){byte[] b=s.getBytes(StandardCharsets.UTF_8);long x=1469598103934665603L^seed;for(byte v:b){x^=v;x*=1099511628211L;}return x^(x>>>33);}
 public void add(String s){for(int i=0;i<k;i++)bits.set((int)Math.floorMod(h(s,i),size));}
 public boolean mightContain(String s){for(int i=0;i<k;i++)if(!bits.get((int)Math.floorMod(h(s,i),size)))return false;return true;}
}
