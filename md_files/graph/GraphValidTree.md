# Graph Valid Tree

Given `n` nodes labeled `0` to `n - 1` and a list of undirected edges, check whether the edges form a valid tree: connected and without cycles.

## Algorithm Strategy (Union-Find (Disjoint Set))

1. A tree with `n` nodes has exactly `n - 1` edges. If the number of edges is different, return `false` immediately.
2. Create a union-find structure where every node starts in its own set.
3. For each edge, find the roots of both endpoints. If they are the same, the edge closes a cycle, so return `false`; otherwise union the two sets.
4. With exactly `n - 1` edges and no cycle, the graph must also be connected, so return `true`.

## Example walkthrough

- For `n = 5` and edges `[[0,1],[0,2],[0,3],[1,4]]`: 4 edges, each joins two different sets, so no cycle: `true`.
- For `[[0,1],[1,2],[2,3],[1,3],[1,4]]` the edge `[1,3]` joins nodes that are already connected: `false`.

## Complexity Analysis

- **Time Complexity:** `O(n * alpha(n))` — Nearly linear with path compression (`alpha` is the inverse Ackermann function).
- **Space Complexity:** `O(n)` — The parent array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: DFS Connectivity Check

A graph is a tree when it has exactly `n - 1` edges and is connected. Build the adjacency lists and check with a DFS that every node is reachable from node 0.

```java
public static boolean validTreeDfs(int n, int[][] edges) {
    if (edges.length != n - 1) return false;
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    for (int[] e : edges) { graph.get(e[0]).add(e[1]); graph.get(e[1]).add(e[0]); }
    boolean[] visited = new boolean[n];
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(0);
    visited[0] = true;
    int count = 0;
    while (!stack.isEmpty()) {
        int node = stack.pop();
        count++;
        for (int next : graph.get(node)) {
            if (!visited[next]) { visited[next] = true; stack.push(next); }
        }
    }
    return count == n;
}
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V + E)

### Approach 2: BFS Connectivity Check

The same check with a queue.

```java
public static boolean validTreeBfs(int n, int[][] edges) {
    if (edges.length != n - 1) return false;
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    for (int[] e : edges) { graph.get(e[0]).add(e[1]); graph.get(e[1]).add(e[0]); }
    boolean[] visited = new boolean[n];
    Queue<Integer> queue = new LinkedList<>();
    queue.add(0);
    visited[0] = true;
    int count = 0;
    while (!queue.isEmpty()) {
        int node = queue.poll();
        count++;
        for (int next : graph.get(node)) {
            if (!visited[next]) { visited[next] = true; queue.add(next); }
        }
    }
    return count == n;
}
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V + E)

### Approach 3: Union-Find (Optimized)

With exactly `n - 1` edges the graph is a tree if and only if no edge connects two nodes that are already connected. No adjacency lists are needed. This is the version implemented in the repository.

```java
public static boolean validTree(int n, int[][] edges) {
    if (edges.length != n - 1) return false;
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
    for (int[] edge : edges) {
        int rootA = find(parent, edge[0]), rootB = find(parent, edge[1]);
        if (rootA == rootB) return false;
        parent[rootA] = rootB;
    }
    return true;
}

private static int find(int[] parent, int node) {
    while (parent[node] != node) { parent[node] = parent[parent[node]]; node = parent[node]; }
    return node;
}
```

- **Time Complexity:** O(n * alpha(n))
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | DFS Connectivity Check | O(V + E) | O(V + E) |
| 2 | BFS Connectivity Check | O(V + E) | O(V + E) |
| 3 | Union-Find (Optimized) | O(n * alpha(n)) | O(n) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### GraphValidTree.java

```java
import java.util.Arrays;

public class GraphValidTree {

    public static void main(String[] args) {
        int[] ns = {5, 5, 4};
        int[][][] edges = {
                {{0, 1}, {0, 2}, {0, 3}, {1, 4}},
                {{0, 1}, {1, 2}, {2, 3}, {1, 3}, {1, 4}},
                {{0, 1}, {2, 3}}
        };
        for (int i = 0; i < ns.length; i++) {
            System.out.println("Input  : n = " + ns[i] + ", edges = " + Arrays.deepToString(edges[i]));
            System.out.println("Output : " + validTree(ns[i], edges[i]));
            System.out.println();
        }
    }

    public static boolean validTree(int n, int[][] edges) {
        if (edges.length != n - 1) {
            return false;
        }

        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        for (int[] edge : edges) {
            int rootA = find(parent, edge[0]);
            int rootB = find(parent, edge[1]);
            if (rootA == rootB) {
                return false;           // this edge closes a cycle
            }
            parent[rootA] = rootB;
        }
        return true;
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
