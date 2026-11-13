# Minimum Spanning Tree (Kruskal and Prim)

Given a connected, undirected, weighted graph with `n` vertices numbered `0..n-1` and an edge list `[u, v, weight]`, return the total weight of a minimum spanning tree (MST): the cheapest set of `n - 1` edges that connects every vertex. Return `-1` if the graph is not connected.

## Algorithm Strategy (Kruskal with Union-Find)

1. Sort all edges by weight, cheapest first.
2. Keep a union-find structure over the vertices. Go through the sorted edges and take an edge only if its endpoints are in different sets (taking it cannot create a cycle), then union the sets.
3. The cut property justifies the greedy choice: the cheapest edge crossing any partition of the vertices belongs to some MST, and the cheapest edge between two different components is such an edge.
4. Stop after `n - 1` edges are taken. If fewer were found, the graph is disconnected and there is no spanning tree.

## Example walkthrough

- For 4 vertices and edges `(0,1,1), (1,2,2), (0,2,3), (2,3,4), (1,3,5)`: sorted, the first two edges are taken (weights 1 and 2).
- `(0,2,3)` closes a cycle and is skipped; `(2,3,4)` connects vertex 3. Total weight `1 + 2 + 4 = 7`.

## Complexity Analysis

- **Time Complexity:** `O(E log E)` — Sorting the edges dominates; union-find operations are almost constant.
- **Space Complexity:** `O(V)` — The union-find parent array (plus the sorted copy of the edges).

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force over Edge Subsets

Try every subset of `n - 1` edges and keep the cheapest one that connects all vertices. Only usable for tiny graphs, but it is an independent check.

```java
public static int minimumSpanningTreeBrute(int n, int[][] edges) {
    int best = Integer.MAX_VALUE;
    for (int mask = 0; mask < (1 << edges.length); mask++) {
        if (Integer.bitCount(mask) != n - 1) continue;
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
        int total = 0, joined = 0;
        for (int e = 0; e < edges.length; e++) {
            if ((mask & (1 << e)) == 0) continue;
            int a = root(parent, edges[e][0]), b = root(parent, edges[e][1]);
            if (a != b) { parent[a] = b; joined++; }
            total += edges[e][2];
        }
        if (joined == n - 1) best = Math.min(best, total);
    }
    return best == Integer.MAX_VALUE ? -1 : best;
}

private static int root(int[] parent, int x) {
    while (parent[x] != x) x = parent[x];
    return x;
}
```

- **Time Complexity:** O(2^E * E)
- **Space Complexity:** O(V)

### Approach 2: Prim with a Priority Queue

Grow a single tree from vertex 0: repeatedly take the cheapest edge that leaves the tree and add the new vertex. It is better than Kruskal on dense graphs.

```java
public static int minimumSpanningTreePrim(int n, int[][] edges) {
    List<List<int[]>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    for (int[] e : edges) {
        graph.get(e[0]).add(new int[]{e[1], e[2]});
        graph.get(e[1]).add(new int[]{e[0], e[2]});
    }
    boolean[] inTree = new boolean[n];
    PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
    heap.add(new int[]{0, 0});
    int total = 0, taken = 0;
    while (!heap.isEmpty()) {
        int[] top = heap.poll();
        if (inTree[top[0]]) continue;
        inTree[top[0]] = true;
        total += top[1];
        taken++;
        for (int[] next : graph.get(top[0])) {
            if (!inTree[next[0]]) heap.add(next);
        }
    }
    return taken == n ? total : -1;
}
```

- **Time Complexity:** O(E log V)
- **Space Complexity:** O(V + E)

### Approach 3: Kruskal with Union-Find (Optimized)

Sort the edges and take each one that joins two different components; it is short and works well on sparse graphs. This is the version implemented in the repository.

```java
public static int minimumSpanningTree(int n, int[][] edges) {
    int[][] sorted = edges.clone();
    Arrays.sort(sorted, (a, b) -> Integer.compare(a[2], b[2]));
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) {
        parent[i] = i;
    }
    int total = 0;
    int taken = 0;
    for (int[] edge : sorted) {
        int a = find(parent, edge[0]);
        int b = find(parent, edge[1]);
        if (a != b) {
            parent[a] = b;
            total += edge[2];
            taken++;
        }
    }
    return taken == n - 1 ? total : -1;
}

private static int find(int[] parent, int x) {
    while (parent[x] != x) {
        parent[x] = parent[parent[x]];
        x = parent[x];
    }
    return x;
}
```

- **Time Complexity:** O(E log E)
- **Space Complexity:** O(V)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force over Edge Subsets | O(2^E * E) | O(V) |
| 2 | Prim with a Priority Queue | O(E log V) | O(V + E) |
| 3 | Kruskal with Union-Find (Optimized) | O(E log E) | O(V) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### MinimumSpanningTree.java

```java
import java.util.Arrays;

public class MinimumSpanningTree {

    public static void main(String[] args) {
        int[][] edges = {{0, 1, 1}, {1, 2, 2}, {0, 2, 3}, {2, 3, 4}, {1, 3, 5}};
        System.out.println("Input  : n = 4, edges = " + Arrays.deepToString(edges));
        System.out.println("Output : " + minimumSpanningTree(4, edges));
        System.out.println();

        int[][] disconnected = {{0, 1, 1}, {2, 3, 1}};
        System.out.println("Input  : n = 4, edges = " + Arrays.deepToString(disconnected));
        System.out.println("Output : " + minimumSpanningTree(4, disconnected));
    }

    public static int minimumSpanningTree(int n, int[][] edges) {
        int[][] sorted = edges.clone();
        Arrays.sort(sorted, (a, b) -> Integer.compare(a[2], b[2]));
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        int total = 0;
        int taken = 0;
        for (int[] edge : sorted) {
            int a = find(parent, edge[0]);
            int b = find(parent, edge[1]);
            if (a != b) {
                parent[a] = b;
                total += edge[2];
                taken++;
            }
        }
        return taken == n - 1 ? total : -1;
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
