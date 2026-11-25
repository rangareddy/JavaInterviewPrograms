# Possible Bipartition

Given `n` people labelled `1` to `n` and an array `dislikes` where `dislikes[i] = [a, b]` means person `a` and person `b` dislike each other, return `true` if it is possible to split everyone into two groups so that no two people who dislike each other are in the same group.

## Algorithm Strategy (Build the Dislike Graph, then Two-Colour)

1. Build an adjacency list with an edge for every dislike pair.
2. Colour people with BFS: an uncoloured person gets colour 1 and their dislikes get the opposite colour.
3. If a person and someone they dislike receive the same colour, the split is impossible.
4. Start a new BFS for every uncoloured person, since the graph can be disconnected.

## Example walkthrough

- For `n = 4` and `[[1,2],[1,3],[2,4]]`: put 1 and 4 in one group and 2 and 3 in the other.
- Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(n + e)` — Every person and every dislike pair is visited once.
- **Space Complexity:** `O(n + e)` — The graph and the colours.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Backtracking Over Assignments

Try both groups for every person and check the dislikes. Exponential.

```java
public static boolean backtrack(int n, int[][] d) { return go(n, d, new int[n + 1], 1); }
private static boolean go(int n, int[][] d, int[] g, int p) {
    if (p > n) return true;
    for (int grp = 1; grp <= 2; grp++) {
        boolean ok = true; for (int[] e : d) { int o = e[0] == p ? e[1] : e[1] == p ? e[0] : -1; if (o > 0 && g[o] == grp) { ok = false; break; } }
        if (ok) { g[p] = grp; if (go(n, d, g, p + 1)) return true; g[p] = 0; }
    }
    return false;
}
```

- **Time Complexity:** O(2^n * e)
- **Space Complexity:** O(n)

### Approach 2: BFS Two-Colouring (Optimized)

Colour the dislike graph level by level. This is the version implemented in the repository.

```java
public static boolean bfs(int n, int[][] d) {
    java.util.List<java.util.List<Integer>> g = new java.util.ArrayList<>(); for (int i = 0; i <= n; i++) g.add(new java.util.ArrayList<>());
    for (int[] e : d) { g.get(e[0]).add(e[1]); g.get(e[1]).add(e[0]); }
    int[] c = new int[n + 1];
    for (int s = 1; s <= n; s++) { if (c[s] != 0) continue; c[s] = 1; java.util.Queue<Integer> q = new java.util.ArrayDeque<>(); q.add(s);
        while (!q.isEmpty()) { int u = q.poll(); for (int v : g.get(u)) { if (c[v] == 0) { c[v] = 3 - c[u]; q.add(v); } else if (c[v] == c[u]) return false; } } }
    return true;
}
```

- **Time Complexity:** O(n + e)
- **Space Complexity:** O(n + e)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Backtracking Over Assignments | O(2^n * e) | O(n) |
| 2 | BFS Two-Colouring (Optimized) | O(n + e) | O(n + e) |

## Source Code

### PossibleBipartition.java

```java
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class PossibleBipartition {

    public static void main(String[] args) {
        int[] ns = {4, 3, 5, 1};
        int[][][] dislikes = {{{1, 2}, {1, 3}, {2, 4}}, {{1, 2}, {1, 3}, {2, 3}}, {{1, 2}, {2, 3}, {3, 4}, {4, 5}, {1, 5}}, {}};
        for (int i = 0; i < ns.length; i++) {
            System.out.println("Input  : n = " + ns[i] + ", dislikes = " + Arrays.deepToString(dislikes[i]));
            System.out.println("Output : " + possibleBipartition(ns[i], dislikes[i]));
            System.out.println();
        }
    }

    public static boolean possibleBipartition(int n, int[][] dislikes) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        if (dislikes != null) {
            for (int[] d : dislikes) {
                graph.get(d[0]).add(d[1]);
                graph.get(d[1]).add(d[0]);
            }
        }
        int[] color = new int[n + 1];
        for (int start = 1; start <= n; start++) {
            if (color[start] != 0) {
                continue;
            }
            color[start] = 1;
            Queue<Integer> queue = new ArrayDeque<>();
            queue.add(start);
            while (!queue.isEmpty()) {
                int person = queue.poll();
                for (int other : graph.get(person)) {
                    if (color[other] == 0) {
                        color[other] = 3 - color[person];
                        queue.add(other);
                    } else if (color[other] == color[person]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: "split into two groups with no dislike inside a group" is exactly "two-colour the dislike graph".

**Input:** `n = 4`, `dislikes = [[1, 2], [1, 3], [2, 4]]`.

| Step | Person | Colour | Neighbours coloured |
|------|--------|--------|---------------------|
| 1 | 1 | 1 | 2 and 3 get colour 2 |
| 2 | 2 | 2 | 4 gets colour 1 |
| 3 | 3 | 2 | no new neighbours |
| 4 | 4 | 1 | its neighbour 2 has colour 2: ok |

**Output:** `true`.
