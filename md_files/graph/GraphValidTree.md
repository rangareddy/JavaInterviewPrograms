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

### Approach 3: DFS Cycle Detection with Parent Tracking

Run a DFS from node 0 remembering the parent of each node. Meeting an already visited node that is not the parent closes a cycle; after the search every node must have been reached (connected). No edge count is needed.

```java
public static boolean validTreeParentDfs(int n, int[][] edges) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    for (int[] e : edges) { graph.get(e[0]).add(e[1]); graph.get(e[1]).add(e[0]); }
    boolean[] visited = new boolean[n];
    if (!acyclic(graph, 0, -1, visited)) return false;
    for (boolean seen : visited) if (!seen) return false;
    return true;
}

private static boolean acyclic(List<List<Integer>> graph, int node, int parent, boolean[] visited) {
    visited[node] = true;
    int parentEdges = 0;
    for (int next : graph.get(node)) {
        if (next == parent && parentEdges++ == 0) continue;     // skip the edge we came from (once)
        if (visited[next] || !acyclic(graph, next, node, visited)) return false;
    }
    return true;
}
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V + E)

### Approach 4: Union-Find with Union by Size

Attach the smaller set below the larger one, which keeps the trees shallow (together with path compression almost constant time per operation). An edge between two nodes of the same set closes a cycle.

```java
public static boolean validTreeBySize(int n, int[][] edges) {
    if (edges.length != n - 1) return false;
    int[] parent = new int[n], size = new int[n];
    for (int i = 0; i < n; i++) { parent[i] = i; size[i] = 1; }
    for (int[] e : edges) {
        int a = find(parent, e[0]), b = find(parent, e[1]);
        if (a == b) return false;
        if (size[a] < size[b]) { int t = a; a = b; b = t; }
        parent[b] = a;
        size[a] += size[b];
    }
    return true;
}

private static int find(int[] parent, int x) {
    while (parent[x] != x) { parent[x] = parent[parent[x]]; x = parent[x]; }
    return x;
}
```

- **Time Complexity:** O(n * alpha(n))
- **Space Complexity:** O(n)

### Approach 5: Edge Count and Component Count

A graph is a tree exactly when it has `n - 1` edges and a single connected component. Count the components with a BFS started from every unvisited node.

```java
public static boolean validTreeComponents(int n, int[][] edges) {
    if (edges.length != n - 1) return false;
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    for (int[] e : edges) { graph.get(e[0]).add(e[1]); graph.get(e[1]).add(e[0]); }
    boolean[] visited = new boolean[n];
    int components = 0;
    for (int start = 0; start < n; start++) {
        if (visited[start]) continue;
        components++;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;
        while (!queue.isEmpty()) {
            for (int next : graph.get(queue.poll())) if (!visited[next]) { visited[next] = true; queue.add(next); }
        }
    }
    return components == 1;
}
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V + E)

### Approach 6: Transitive Closure (Floyd-Warshall)

With `n - 1` edges the graph is a tree if and only if every pair of nodes is connected. Compute the reachability matrix with Floyd-Warshall and check that all entries are true. Cubic, so only for small graphs.

```java
public static boolean validTreeClosure(int n, int[][] edges) {
    if (edges.length != n - 1) return false;
    boolean[][] reach = new boolean[n][n];
    for (int i = 0; i < n; i++) reach[i][i] = true;
    for (int[] e : edges) { reach[e[0]][e[1]] = true; reach[e[1]][e[0]] = true; }
    for (int k = 0; k < n; k++)
        for (int i = 0; i < n; i++)
            if (reach[i][k])
                for (int j = 0; j < n; j++) if (reach[k][j]) reach[i][j] = true;
    for (boolean[] row : reach) for (boolean value : row) if (!value) return false;
    return true;
}
```

- **Time Complexity:** O(V^3)
- **Space Complexity:** O(V^2)

### Approach 7: Union-Find (Optimized)

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
| 3 | DFS Cycle Detection with Parent Tracking | O(V + E) | O(V + E) |
| 4 | Union-Find with Union by Size | O(n * alpha(n)) | O(n) |
| 5 | Edge Count and Component Count | O(V + E) | O(V + E) |
| 6 | Transitive Closure (Floyd-Warshall) | O(V^3) | O(V^2) |
| 7 | Union-Find (Optimized) | O(n * alpha(n)) | O(n) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

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
