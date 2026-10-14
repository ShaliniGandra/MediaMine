package mediamine.m5_np;
import java.util.*;
/** Small SAT/3-SAT solver used to model content-selection constraints. */
public final class SAT3 {
 public static final class Literal { public final int var; public final boolean positive; public Literal(int var,boolean positive){this.var=var;this.positive=positive;} public boolean eval(boolean[] a){return positive?a[var]:!a[var];} public String toString(){return (positive?"x":"¬x")+(var+1);} }
 public static final class Clause { public final Literal[] lits; public Clause(Literal... lits){if(lits.length!=3)throw new IllegalArgumentException("3-SAT clause needs 3 literals");this.lits=lits;} public boolean eval(boolean[] a){return lits[0].eval(a)||lits[1].eval(a)||lits[2].eval(a);} public String toString(){return "("+lits[0]+" ∨ "+lits[1]+" ∨ "+lits[2]+")";} }
 private SAT3(){}
 public static boolean[] solve(int variables,List<Clause> clauses){
  if(variables>24)throw new IllegalArgumentException("Demo solver is exhaustive; use <=24 variables");
  long limit=1L<<variables;
  for(long mask=0;mask<limit;mask++){boolean[] a=new boolean[variables];for(int i=0;i<variables;i++)a[i]=(mask&(1L<<i))!=0;boolean ok=true;for(Clause c:clauses)if(!c.eval(a)){ok=false;break;}if(ok)return a;} return null;
 }
}
