# Collect Coins in a Tree

An undirected tree has `n` nodes numbered `0` to `n - 1`, given by `edges`, and an array `coins` where `coins[i]` is 1 if node `i` holds a coin. You may start at any node. In one move you travel along an edge, and from your current node you can collect all coins within distance 2 for free. Return the minimum number of edges you must traverse to collect all coins and return to the starting node.

## Algorithm Strategy (Peel Leaves, then Two Layers)

1. Repeatedly remove leaves that hold no coin (they are never worth visiting). This is a BFS from the leaves, using degrees.
2. After that, every remaining leaf holds a coin. Because you can collect within distance 2, remove two more layers of leaves from the remaining tree.
3. The remaining nodes form a tree that must be walked and returned from: it costs `2 * (remaining nodes - 1)` edges.
4. If at most one node remains, the answer is 0.

## Example walkthrough

- For the path `0 - 1 - 2 - 3 - 4 - 5` with coins at nodes 0 and 5: every middle node is on the way, and removing two layers from each end leaves two nodes.
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each node and edge is processed once.
- **Space Complexity:** `O(n)` — Adjacency lists, degrees and a queue.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Try Every Starting Node (Simulation)

For each start, walk the pruned tree with a DFS that decides which child subtrees are worth entering. Quadratic.

```java
public static int simulate(int[] coins, int[][] edges) {
    int n = coins.length; java.util.List<java.util.List<Integer>> g = new java.util.ArrayList<>(); for (int i = 0; i < n; i++) g.add(new java.util.ArrayList<>()); for (int[] e : edges) { g.get(e[0]).add(e[1]); g.get(e[1]).add(e[0]); }
    int best = Integer.MAX_VALUE; for (int s = 0; s < n; s++) { int[] cost = {0}; need(g, coins, s, -1, cost); best = Math.min(best, cost[0]); } return best == Integer.MAX_VALUE ? 0 : best;
}
private static int need(java.util.List<java.util.List<Integer>> g, int[] coins, int u, int p, int[] cost) {
    int depthNeeded = coins[u] == 1 ? 0 : -1; boolean enters = false;
    for (int v : g.get(u)) if (v != p) { int d = need(g, coins, v, u, cost); if (d >= 0) { if (d + 1 >= 2) { cost[0] += 2; } depthNeeded = Math.max(depthNeeded, d + 1); } }
    return depthNeeded;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 2: Peel Leaves then Two Layers (Optimized)

Remove coin-less leaves, then two layers of leaves, and double the remaining edges. This is the version implemented in the repository.

```java
public static int peel(int[] coins, int[][] edges) {
    int n = coins.length; java.util.List<java.util.List<Integer>> g = new java.util.ArrayList<>(); int[] deg = new int[n]; for (int i = 0; i < n; i++) g.add(new java.util.ArrayList<>()); for (int[] e : edges) { g.get(e[0]).add(e[1]); g.get(e[1]).add(e[0]); deg[e[0]]++; deg[e[1]]++; }
    boolean[] rm = new boolean[n]; java.util.Queue<Integer> q = new java.util.ArrayDeque<>(); for (int i = 0; i < n; i++) if (deg[i] <= 1 && coins[i] == 0) q.add(i); int left = n;
    while (!q.isEmpty()) { int l = q.poll(); if (rm[l]) continue; rm[l] = true; left--; for (int x : g.get(l)) if (!rm[x] && --deg[x] <= 1 && coins[x] == 0) q.add(x); }
    for (int layer = 0; layer < 2; layer++) { java.util.List<Integer> leaves = new java.util.ArrayList<>(); for (int i = 0; i < n; i++) if (!rm[i] && deg[i] <= 1) leaves.add(i); for (int l : leaves) { rm[l] = true; left--; for (int x : g.get(l)) if (!rm[x]) deg[x]--; } }
    return Math.max(0, 2 * (left - 1));
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Try Every Starting Node (Simulation) | O(n^2) | O(n) |
| 2 | Peel Leaves then Two Layers (Optimized) | O(n) | O(n) |

## Source Code

### CollectCoinsInATree.java

```java
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class CollectCoinsInATree {

    public static void main(String[] args) {
        int[][] coins = {{1, 0, 0, 0, 0, 1}, {0, 0, 0, 1, 1, 0, 0, 1}, {1}, {0, 0, 0}};
        int[][][] edges = {{{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}}, {{0, 1}, {0, 2}, {1, 3}, {1, 4}, {2, 5}, {5, 6}, {5, 7}}, {}, {{0, 1}, {1, 2}}};
        for (int i = 0; i < coins.length; i++) {
            System.out.println("Input  : coins = " + Arrays.toString(coins[i]) + ", edges = " + Arrays.deepToString(edges[i]));
            System.out.println("Output : " + collectTheCoins(coins[i], edges[i]));
            System.out.println();
        }
    }

    public static int collectTheCoins(int[] coins, int[][] edges) {
        int n = coins.length;
        List<List<Integer>> graph = new ArrayList<>();
        int[] degree = new int[n];
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] e : edges) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
            degree[e[0]]++;
            degree[e[1]]++;
        }
        boolean[] removed = new boolean[n];
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (degree[i] <= 1 && coins[i] == 0) {
                queue.add(i);
            }
        }
        int remaining = n;
        while (!queue.isEmpty()) {
            int leaf = queue.poll();
            if (removed[leaf]) {
                continue;
            }
            removed[leaf] = true;
            remaining--;
            for (int next : graph.get(leaf)) {
                if (!removed[next] && --degree[next] <= 1 && coins[next] == 0) {
                    queue.add(next);
                }
            }
        }
        for (int layer = 0; layer < 2; layer++) {
            List<Integer> leaves = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (!removed[i] && degree[i] <= 1) {
                    leaves.add(i);
                }
            }
            for (int leaf : leaves) {
                removed[leaf] = true;
                remaining--;
                for (int next : graph.get(leaf)) {
                    if (!removed[next]) {
                        degree[next]--;
                    }
                }
            }
        }
        return Math.max(0, 2 * (remaining - 1));
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository. (Approach 1 is only a rough sketch of the idea; the repository checks the implementation against Approach 2.)

The idea: leaves without coins are useless, so remove them. Being able to collect coins within distance 2 means the last two layers of the remaining tree never have to be entered: standing at their ancestors is enough. What is left must be walked there and back, once per edge in each direction.

**Input:** a path `0 - 1 - 2 - 3 - 4 - 5` with coins at nodes 0 and 5.

| Step | Remaining nodes |
|------|-----------------|
| prune coin-less leaves | 0, 1, 2, 3, 4, 5 (both ends have coins) |
| remove one layer of leaves | 1, 2, 3, 4 |
| remove a second layer | 2, 3 |

Two nodes remain, so one edge must be walked and returned.

**Output:** `2 * (2 - 1) = 2`.
