# Number of Operations to Make Network Connected

There are `n` computers numbered `0` to `n - 1` connected by Ethernet cables `connections[i] = [a, b]`. In one operation you can take a cable between two directly connected computers and place it between any pair of computers. Return the minimum number of operations needed to connect all the computers, or `-1` if it is impossible.

## Algorithm Strategy (Count Components with Union-Find)

1. Connecting `n` computers needs at least `n - 1` cables. With fewer, return -1.
2. Union the computers of every cable and count the number of connected components.
3. Joining `c` components needs `c - 1` cables, and there are enough spare cables because `connections.length >= n - 1`.
4. The answer is `components - 1`.

## Example walkthrough

- For `n = 4` and `[[0,1],[0,2],[1,2]]`: the cables connect 0, 1, 2, and computer 3 is alone, so there are 2 components.
- Result: `1` (move the redundant cable).

## Complexity Analysis

- **Time Complexity:** `O(n + e * alpha(n))` — Union-Find with path compression.
- **Space Complexity:** `O(n)` — The parent array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: DFS Count of Components

Count components with a depth-first search over an adjacency list.

```java
public static int dfsCount(int n, int[][] c) {
    if (c.length < n - 1) return -1;
    java.util.List<java.util.List<Integer>> g = new java.util.ArrayList<>(); for (int i = 0; i < n; i++) g.add(new java.util.ArrayList<>());
    for (int[] e : c) { g.get(e[0]).add(e[1]); g.get(e[1]).add(e[0]); }
    boolean[] seen = new boolean[n]; int comps = 0;
    for (int i = 0; i < n; i++) if (!seen[i]) { comps++; java.util.Deque<Integer> st = new java.util.ArrayDeque<>(); st.push(i); seen[i] = true; while (!st.isEmpty()) { int u = st.pop(); for (int v : g.get(u)) if (!seen[v]) { seen[v] = true; st.push(v); } } }
    return comps - 1;
}
```

- **Time Complexity:** O(n + e)
- **Space Complexity:** O(n + e)

### Approach 2: Union-Find (Optimized)

Merge the endpoints of each cable and count the sets. This is the version implemented in the repository.

```java
public static int unionFind(int n, int[][] c) {
    if (c.length < n - 1) return -1;
    int[] p = new int[n]; for (int i = 0; i < n; i++) p[i] = i; int comps = n;
    for (int[] e : c) { int a = f(p, e[0]), b = f(p, e[1]); if (a != b) { p[a] = b; comps--; } }
    return comps - 1;
}
private static int f(int[] p, int x) { while (p[x] != x) { p[x] = p[p[x]]; x = p[x]; } return x; }
```

- **Time Complexity:** O(n + e * alpha(n))
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | DFS Count of Components | O(n + e) | O(n + e) |
| 2 | Union-Find (Optimized) | O(n + e * alpha(n)) | O(n) |

## Source Code

### NumberOfOperationsToMakeNetworkConnected.java

```java
import java.util.Arrays;

public class NumberOfOperationsToMakeNetworkConnected {

    public static void main(String[] args) {
        int[] ns = {4, 6, 6, 5};
        int[][][] cables = {{{0, 1}, {0, 2}, {1, 2}}, {{0, 1}, {0, 2}, {0, 3}, {1, 2}, {1, 3}}, {{0, 1}, {0, 2}, {0, 3}, {1, 2}}, {{0, 1}, {0, 2}, {3, 4}, {2, 3}}};
        for (int i = 0; i < ns.length; i++) {
            System.out.println("Input  : n = " + ns[i] + ", connections = " + Arrays.deepToString(cables[i]));
            System.out.println("Output : " + makeConnected(ns[i], cables[i]));
            System.out.println();
        }
    }

    public static int makeConnected(int n, int[][] connections) {
        if (connections == null || connections.length < n - 1) {
            return -1;
        }
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        int components = n;
        for (int[] c : connections) {
            int a = find(parent, c[0]);
            int b = find(parent, c[1]);
            if (a != b) {
                parent[a] = b;
                components--;
            }
        }
        return components - 1;
    }

    private static int find(int[] parent, int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: each operation can join two groups, so `c` groups need `c - 1` moves, provided there are at least `n - 1` cables in total (enough cables exist to build a connected network at all).

**Input:** `n = 4`, `connections = [[0, 1], [0, 2], [1, 2]]`.

| Cable | Action | Components |
|-------|--------|------------|
| start | | 4 |
| [0, 1] | union | 3 |
| [0, 2] | union | 2 |
| [1, 2] | already connected, redundant | 2 |

There are 3 cables, enough for `n - 1 = 3`. Two components need one operation.

**Output:** `1`.
