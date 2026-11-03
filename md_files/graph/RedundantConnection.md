# Redundant Connection

A tree with `n` nodes labeled `1` to `n` had one extra edge added, so the graph now has exactly one cycle. Given the `n` edges, return an edge that can be removed so that the remaining graph is a tree. If there are several answers, return the one that occurs last in the input.

## Algorithm Strategy (Union-Find: The Edge that Closes a Cycle)

1. Start with every node in its own set.
2. Process the edges in the given order. For edge `[u, v]` find the roots of both endpoints.
3. If the roots are equal, `u` and `v` are already connected, so this edge closes the cycle: it is redundant, return it.
4. Otherwise union the two sets. Since the edges are processed in order, the first edge that closes a cycle is the last edge of that cycle in the input, which is the required answer.

## Example walkthrough

- For `[[1,2],[1,3],[2,3]]`: edges 1-2 and 1-3 join {1, 2, 3}; the edge 2-3 connects two nodes of the same set.
- Result: `[2, 3]`.

## Complexity Analysis

- **Time Complexity:** `O(n * alpha(n))` — Nearly linear with path compression.
- **Space Complexity:** `O(n)` — The parent array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: DFS Before Adding Each Edge

Add the edges one by one to an adjacency list. Before adding `[u, v]`, run a DFS to see whether `u` and `v` are already connected; if so, this edge closes the cycle. Each check costs `O(n)`.

```java
public static int[] findRedundantConnectionDfs(int[][] edges) {
    int n = edges.length;
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());
    for (int[] edge : edges) {
        if (connected(graph, edge[0], edge[1], new boolean[n + 1])) return edge;
        graph.get(edge[0]).add(edge[1]);
        graph.get(edge[1]).add(edge[0]);
    }
    return new int[0];
}

private static boolean connected(List<List<Integer>> graph, int from, int to, boolean[] visited) {
    if (from == to) return true;
    visited[from] = true;
    for (int next : graph.get(from)) if (!visited[next] && connected(graph, next, to, visited)) return true;
    return false;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 2: Peel the Leaves, Then Take the Last Cycle Edge

Repeatedly remove nodes of degree one (they cannot be part of the cycle). The nodes left form the cycle; the answer is the last edge of the input whose two endpoints both remain.

```java
public static int[] findRedundantConnectionPeel(int[][] edges) {
    int n = edges.length;
    int[] degree = new int[n + 1];
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());
    for (int[] edge : edges) {
        graph.get(edge[0]).add(edge[1]);
        graph.get(edge[1]).add(edge[0]);
        degree[edge[0]]++;
        degree[edge[1]]++;
    }
    Queue<Integer> leaves = new LinkedList<>();
    for (int node = 1; node <= n; node++) if (degree[node] == 1) leaves.add(node);
    boolean[] removed = new boolean[n + 1];
    while (!leaves.isEmpty()) {
        int leaf = leaves.poll();
        removed[leaf] = true;
        for (int neighbor : graph.get(leaf)) if (!removed[neighbor] && --degree[neighbor] == 1) leaves.add(neighbor);
    }
    for (int i = n - 1; i >= 0; i--) if (!removed[edges[i][0]] && !removed[edges[i][1]]) return edges[i];
    return new int[0];
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Union-Find with Union by Size

The union-find solution where the smaller tree is hung below the larger one, which keeps the trees shallow.

```java
public static int[] findRedundantConnectionBySize(int[][] edges) {
    int[] parent = new int[edges.length + 1], size = new int[edges.length + 1];
    for (int i = 0; i < parent.length; i++) { parent[i] = i; size[i] = 1; }
    for (int[] edge : edges) {
        int a = find(parent, edge[0]), b = find(parent, edge[1]);
        if (a == b) return edge;
        if (size[a] < size[b]) { int t = a; a = b; b = t; }
        parent[b] = a;
        size[a] += size[b];
    }
    return new int[0];
}

private static int find(int[] parent, int x) {
    while (parent[x] != x) { parent[x] = parent[parent[x]]; x = parent[x]; }
    return x;
}
```

- **Time Complexity:** O(n * alpha(n))
- **Space Complexity:** O(n)

### Approach 4: Union-Find (Optimized)

The first edge that joins two nodes of the same set closes the cycle. This is the version implemented in the repository.

```java
public static int[] findRedundantConnection(int[][] edges) {
    int[] parent = new int[edges.length + 1];
    for (int i = 0; i < parent.length; i++) parent[i] = i;
    for (int[] edge : edges) {
        int rootA = findRoot(parent, edge[0]), rootB = findRoot(parent, edge[1]);
        if (rootA == rootB) return edge;
        parent[rootA] = rootB;
    }
    return new int[0];
}

private static int findRoot(int[] parent, int node) {
    while (parent[node] != node) { parent[node] = parent[parent[node]]; node = parent[node]; }
    return node;
}
```

- **Time Complexity:** O(n * alpha(n))
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | DFS Before Adding Each Edge | O(n^2) | O(n) |
| 2 | Peel the Leaves, Then Take the Last Cycle Edge | O(n) | O(n) |
| 3 | Union-Find with Union by Size | O(n * alpha(n)) | O(n) |
| 4 | Union-Find (Optimized) | O(n * alpha(n)) | O(n) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### RedundantConnection.java

```java
import java.util.Arrays;

public class RedundantConnection {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{1, 2}, {1, 3}, {2, 3}},
                {{1, 2}, {2, 3}, {3, 4}, {1, 4}, {1, 5}},
                {{1, 2}, {1, 2}},
                {{1, 4}, {3, 4}, {1, 3}, {1, 2}, {4, 5}}
        };
        for (int[][] edges : inputs) {
            System.out.println("Input  : edges = " + Arrays.deepToString(edges));
            System.out.println("Output : " + Arrays.toString(findRedundantConnection(edges)));
            System.out.println();
        }
    }

    public static int[] findRedundantConnection(int[][] edges) {
        int[] parent = new int[edges.length + 1];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        for (int[] edge : edges) {
            int rootA = find(parent, edge[0]);
            int rootB = find(parent, edge[1]);
            if (rootA == rootB) {
                return edge;                        // already connected: this edge closes a cycle
            }
            parent[rootA] = rootB;
        }
        return new int[0];
    }

    private static int find(int[] parent, int node) {
        while (parent[node] != node) {
            parent[node] = parent[parent[node]];    // path compression (halving)
            node = parent[node];
        }
        return node;
    }
}
```
