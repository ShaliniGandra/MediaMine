package mediamine.m6_parallel;
/** Brent's theorem estimate: T_p <= W/p + O(S), where W is work and S is span. */
public final class BrentTheorem {
 private BrentTheorem(){}
 public static double upperBound(long work,long span,int processors){if(processors<=0)throw new IllegalArgumentException();return (double)work/processors+span;}
 public static String explain(long work,long span,int processors){return String.format("W=%d, S=%d, p=%d -> bound <= W/p + S = %.2f",work,span,processors,upperBound(work,span,processors));}
}
