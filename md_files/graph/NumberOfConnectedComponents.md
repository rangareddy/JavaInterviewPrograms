# Number of Connected Components in an Undirected Graph

Given `n` nodes labeled `0` to `n - 1` and a list of undirected edges, return the number of connected components in the graph.

## Algorithm Strategy (Union-Find (Disjoint Set))

1. Start with `n` components: every node is in its own set, so `components = n`.
2. For every edge, find the roots of the two endpoints.
3. If the roots differ, the edge merges two components: union the sets and decrement `components`.
4. After all edges are processed, `components` is the answer.

## Example walkthrough

- For `n = 5` and edges `[[0,1],[1,2],[3,4]]`: start with 5 components; edges 0-1, 1-2 and 3-4 each merge two components.
- Result: `5 - 3 = 2`.

## Complexity Analysis

- **Time Complexity:** `O(n + E * alpha(n))` — Nearly linear with path compression.
- **Space Complexity:** `O(n)` — The parent array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: DFS

Build the adjacency lists and start a DFS from every unvisited node; each start is a new component.

```java
public static int countComponentsDfs(int n, int[][] edges) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    for (int[] e : edges) { graph.get(e[0]).add(e[1]); graph.get(e[1]).add(e[0]); }
    boolean[] visited = new boolean[n];
    int components = 0;
    for (int i = 0; i < n; i++) {
        if (visited[i]) continue;
        components++;
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(i);
        visited[i] = true;
        while (!stack.isEmpty()) {
            for (int next : graph.get(stack.pop())) {
                if (!visited[next]) { visited[next] = true; stack.push(next); }
            }
        }
    }
    return components;
}
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V + E)

### Approach 2: BFS

The same count with a queue.

```java
public static int countComponentsBfs(int n, int[][] edges) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    for (int[] e : edges) { graph.get(e[0]).add(e[1]); graph.get(e[1]).add(e[0]); }
    boolean[] visited = new boolean[n];
    int components = 0;
    for (int i = 0; i < n; i++) {
        if (visited[i]) continue;
        components++;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(i);
        visited[i] = true;
        while (!queue.isEmpty()) {
            for (int next : graph.get(queue.poll())) {
                if (!visited[next]) { visited[next] = true; queue.add(next); }
            }
        }
    }
    return components;
}
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V + E)

### Approach 3: Recursive DFS

Count the components with a recursive DFS started from every unvisited node.

```java
public static int countComponentsRecursive(int n, int[][] edges) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    for (int[] e : edges) { graph.get(e[0]).add(e[1]); graph.get(e[1]).add(e[0]); }
    boolean[] visited = new boolean[n];
    int components = 0;
    for (int i = 0; i < n; i++) {
        if (!visited[i]) { components++; dfs(graph, i, visited); }
    }
    return components;
}

private static void dfs(List<List<Integer>> graph, int node, boolean[] visited) {
    visited[node] = true;
    for (int next : graph.get(node)) if (!visited[next]) dfs(graph, next, visited);
}
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V + E)

### Approach 4: Union-Find with Union by Size

Attach the smaller tree below the larger one so the trees stay shallow; every successful union reduces the component count by one.

```java
public static int countComponentsBySize(int n, int[][] edges) {
    int[] parent = new int[n], size = new int[n];
    for (int i = 0; i < n; i++) { parent[i] = i; size[i] = 1; }
    int components = n;
    for (int[] e : edges) {
        int a = find(parent, e[0]), b = find(parent, e[1]);
        if (a == b) continue;
        if (size[a] < size[b]) { int t = a; a = b; b = t; }
        parent[b] = a;
        size[a] += size[b];
        components--;
    }
    return components;
}

private static int find(int[] parent, int x) {
    while (parent[x] != x) { parent[x] = parent[parent[x]]; x = parent[x]; }
    return x;
}
```

- **Time Complexity:** O(n + E * alpha(n))
- **Space Complexity:** O(n)

### Approach 5: Label Propagation until Nothing Changes

Give every node its own label and repeatedly replace the label of both endpoints of every edge by the smaller one. When a full pass changes nothing, nodes with the same label are in the same component; count the distinct labels.

```java
public static int countComponentsLabels(int n, int[][] edges) {
    int[] label = new int[n];
    for (int i = 0; i < n; i++) label[i] = i;
    boolean changed = true;
    while (changed) {
        changed = false;
        for (int[] e : edges) {
            int smaller = Math.min(label[e[0]], label[e[1]]);
            if (label[e[0]] != smaller) { label[e[0]] = smaller; changed = true; }
            if (label[e[1]] != smaller) { label[e[1]] = smaller; changed = true; }
        }
    }
    return (int) Arrays.stream(label).distinct().count();
}
```

- **Time Complexity:** O(V * E) in the worst case
- **Space Complexity:** O(V)

### Approach 6: Transitive Closure (Floyd-Warshall)

Compute which nodes can reach which with the Floyd-Warshall closure. A node is the representative of its component when no smaller node can reach it, so count those nodes. Cubic.

```java
public static int countComponentsClosure(int n, int[][] edges) {
    boolean[][] reach = new boolean[n][n];
    for (int i = 0; i < n; i++) reach[i][i] = true;
    for (int[] e : edges) { reach[e[0]][e[1]] = true; reach[e[1]][e[0]] = true; }
    for (int k = 0; k < n; k++)
        for (int i = 0; i < n; i++)
            if (reach[i][k])
                for (int j = 0; j < n; j++) if (reach[k][j]) reach[i][j] = true;
    int components = 0;
    for (int i = 0; i < n; i++) {
        boolean smallestOfItsComponent = true;
        for (int j = 0; j < i; j++) if (reach[j][i]) smallestOfItsComponent = false;
        if (smallestOfItsComponent) components++;
    }
    return components;
}
```

- **Time Complexity:** O(V^3)
- **Space Complexity:** O(V^2)

### Approach 7: Union-Find (Optimized)

Start with `n` components and subtract one for every edge that joins two different components. No adjacency lists; also works when edges arrive one at a time. This is the version implemented in the repository.

```java
public static int countComponents(int n, int[][] edges) {
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
    int components = n;
    for (int[] edge : edges) {
        int rootA = find(parent, edge[0]), rootB = find(parent, edge[1]);
        if (rootA != rootB) { parent[rootA] = rootB; components--; }
    }
    return components;
}

private static int find(int[] parent, int node) {
    while (parent[node] != node) { parent[node] = parent[parent[node]]; node = parent[node]; }
    return node;
}
```

- **Time Complexity:** O(n + E * alpha(n))
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | DFS | O(V + E) | O(V + E) |
| 2 | BFS | O(V + E) | O(V + E) |
| 3 | Recursive DFS | O(V + E) | O(V + E) |
| 4 | Union-Find with Union by Size | O(n + E * alpha(n)) | O(n) |
| 5 | Label Propagation until Nothing Changes | O(V * E) in the worst case | O(V) |
| 6 | Transitive Closure (Floyd-Warshall) | O(V^3) | O(V^2) |
| 7 | Union-Find (Optimized) | O(n + E * alpha(n)) | O(n) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

## Source Code

### NumberOfConnectedComponents.java

```java
import java.util.Arrays;

public class NumberOfConnectedComponents {

    public static void main(String[] args) {
        int[] ns = {5, 5, 3};
        int[][][] edges = {
                {{0, 1}, {1, 2}, {3, 4}},
                {{0, 1}, {1, 2}, {2, 3}, {3, 4}},
                {}
        };
        for (int i = 0; i < ns.length; i++) {
            System.out.println("Input  : n = " + ns[i] + ", edges = " + Arrays.deepToString(edges[i]));
            System.out.println("Output : " + countComponents(ns[i], edges[i]));
            System.out.println();
        }
    }

    public static int countComponents(int n, int[][] edges) {
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        int components = n;
        for (int[] edge : edges) {
            int rootA = find(parent, edge[0]);
            int rootB = find(parent, edge[1]);
            if (rootA != rootB) {
                parent[rootA] = rootB;
                components--;
            }
        }
        return components;
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
