# MediaMine - Intelligent Media Analytics Platform

A Java 17, no-external-library DSA project matching the six modules shown in the MediaMine project description.
It is ready for **VS Code** or **Eclipse**.

## Run in VS Code
1. Extract the `MediaMine.zip` folder.
2. Open the **MediaMine** folder in VS Code.
3. Install **Extension Pack for Java** if prompted.
4. Open `src/mediamine/Main.java` and press **Run**.
5. Choose modules from the menu, or run with argument `all` to execute all six modules.
6. `src/mediamine/SelfTest.java` runs smoke tests for the algorithms.

### Terminal
```bash
javac -d bin $(find src -name "*.java")
java -cp bin mediamine.Main
java -cp bin mediamine.Main all
java -cp bin mediamine.SelfTest
```

## Module -> algorithm map
| Module | Algorithms | MediaMine use |
|---|---|---|
| M1 (CO1) | KMP, Z-Function, Rabin-Karp, Aho-Corasick | keyword search, repeated phrases, media identifiers, multiple topics |
| M2 (CO2) | Suffix Array, SA-IS, LCP/Kasai, Suffix Tree/Suffix Automaton | transcript/news indexing and similarity |
| M3 (CO3) | Levenshtein/Damerau-Levenshtein, Weighted Edit Distance, Bitmask DP, Matrix-Chain | query correction, category combinations, media-processing order |
| M4 (CO4) | Ford-Fulkerson, Edmonds-Karp, Dinic, Bipartite Matching, Min-Cut | media-distribution capacity, content-channel assignment, bottlenecks |
| M5 (CO5) | SAT/3-SAT, 3-SAT -> CLIQUE, CLIQUE/Independent Set/Vertex Cover, Vertex-Cover 2-approx | content-selection constraints and content conflicts |
| M6 (CO6) | Randomized QuickSort, Reservoir Sampling, Miller-Rabin, Bloom Filter, Parallel Reduce, Brent's theorem | ranking, continuous event sampling, numerical processing, media statistics, parallel processing |

## Domain model
`mediamine.model` contains `MediaItem`, `MediaDocument`, `Caption`, `SearchQuery`, `ContentCategory`, and `MediaEvent`.

## Complexity quick reference
KMP O(n+m) | Z O(n) | Rabin-Karp expected O(n+m) | Aho-Corasick O(n+L+matches) |
SA-IS O(n) | Kasai O(n) | Suffix-tree queries O(m) after construction |
Levenshtein O(nm) | Held-Karp O(n^2 2^n) | Matrix-chain O(n^3) | OBST O(n^3) |
Ford-Fulkerson O(Ef) | Edmonds-Karp O(VE^2) | Dinic O(V^2E) |
Vertex-Cover 2-approx: 2-approximation | Randomized QuickSort expected O(n log n) |
Reservoir Sampling O(n) time, O(k) space | Miller-Rabin logarithmic in exponent per base | Bloom filter O(k) per operation |
Parallel Reduce: O(n/p + log n) idealized span/work model | Brent: T_p <= W/p + O(S).
