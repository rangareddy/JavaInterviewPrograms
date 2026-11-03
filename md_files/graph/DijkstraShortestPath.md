# Dijkstra's Shortest Path

Given a weighted directed graph with non-negative edge weights, find the shortest distance from a source vertex to every other vertex and reconstruct the shortest path to a chosen target.

## Algorithm Strategy (Greedy Relaxation with a Min-Heap)

1. Keep `dist[v]`, the best known distance from the source, initialised to infinity except `dist[source] = 0`, and `parent[v]` to rebuild the path.
2. Put `(0, source)` into a min-heap ordered by distance.
3. Remove the closest unfinished vertex `u`. If the removed distance is larger than `dist[u]`, it is a stale entry: skip it.
4. For every edge `u -> v` with weight `w`, if `dist[u] + w < dist[v]` update `dist[v]` and `parent[v]` and add `(dist[v], v)` to the heap. The path to a target is read by following `parent` back to the source.

## Example walkthrough

- For edges `0->1 (4), 0->2 (1), 2->1 (2), 1->3 (1), 2->3 (5)` from vertex 0: the distances are `[0, 3, 1, 4]`.
- The shortest path to vertex 3 is `0 -> 2 -> 1 -> 3` with cost 4 (the direct route `0 -> 1 -> 3` costs 5).

## Complexity Analysis

- **Time Complexity:** `O((V + E) log V)` — Each edge may add one entry to the heap, and every heap operation costs O(log V).
- **Space Complexity:** `O(V + E)` — Distance and parent arrays, adjacency list and the heap.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Bellman-Ford

Relax every edge `V - 1` times. Slower, but it also handles negative weights.

```java
public static int[] bellmanFord(int n, int[][] edges, int source) {
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[source] = 0;
    for (int round = 1; round < n; round++)
        for (int[] e : edges)
            if (dist[e[0]] != Integer.MAX_VALUE && dist[e[0]] + e[2] < dist[e[1]]) dist[e[1]] = dist[e[0]] + e[2];
    return dist;
}
```

- **Time Complexity:** O(V * E)
- **Space Complexity:** O(V)

### Approach 2: Array Scan Dijkstra

Pick the closest unfinished vertex by scanning an array instead of a heap; good for dense graphs.

```java
public static int[] dijkstraArray(int n, int[][] edges, int source) {
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[source] = 0;
    boolean[] done = new boolean[n];
    for (int step = 0; step < n; step++) {
        int u = -1;
        for (int v = 0; v < n; v++) if (!done[v] && (u < 0 || dist[v] < dist[u])) u = v;
        if (u < 0 || dist[u] == Integer.MAX_VALUE) break;
        done[u] = true;
        for (int[] e : edges) if (e[0] == u && dist[u] + e[2] < dist[e[1]]) dist[e[1]] = dist[u] + e[2];
    }
    return dist;
}
```

- **Time Complexity:** O(V^2 + V * E)
- **Space Complexity:** O(V)

### Approach 3: TreeSet Dijkstra

Use a sorted set of `(distance, vertex)` pairs so that an improved distance can be removed and re-inserted instead of leaving stale entries.

```java
public static int[] dijkstraTreeSet(int n, int[][] edges, int source) {
    List<List<int[]>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    for (int[] e : edges) graph.get(e[0]).add(new int[]{e[1], e[2]});
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[source] = 0;
    TreeSet<long[]> set = new TreeSet<>((a, b) -> a[0] != b[0] ? Long.compare(a[0], b[0]) : Long.compare(a[1], b[1]));
    set.add(new long[]{0, source});
    while (!set.isEmpty()) {
        long[] top = set.pollFirst();
        int u = (int) top[1];
        for (int[] edge : graph.get(u)) {
            int v = edge[0], candidate = dist[u] + edge[1];
            if (candidate < dist[v]) {
                if (dist[v] != Integer.MAX_VALUE) set.remove(new long[]{dist[v], v});
                dist[v] = candidate;
                set.add(new long[]{candidate, v});
            }
        }
    }
    return dist;
}
```

- **Time Complexity:** O((V + E) log V)
- **Space Complexity:** O(V + E)

### Approach 4: Min-Heap with Lazy Deletion (Optimized)

A `PriorityQueue` of `(distance, vertex)`; outdated entries are skipped when polled. This is the version implemented in the repository.

```java
public static int[] shortestDistances(int n, int[][] edges, int source, int[] parent) {
    List<List<int[]>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    for (int[] edge : edges) graph.get(edge[0]).add(new int[]{edge[1], edge[2]});
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    Arrays.fill(parent, -1);
    dist[source] = 0;
    PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
    heap.add(new int[]{0, source});
    while (!heap.isEmpty()) {
        int[] current = heap.poll();
        int u = current[1];
        if (current[0] > dist[u]) continue;
        for (int[] edge : graph.get(u)) {
            int v = edge[0], candidate = dist[u] + edge[1];
            if (candidate < dist[v]) { dist[v] = candidate; parent[v] = u; heap.add(new int[]{candidate, v}); }
        }
    }
    return dist;
}
```

- **Time Complexity:** O((V + E) log V)
- **Space Complexity:** O(V + E)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Bellman-Ford | O(V * E) | O(V) |
| 2 | Array Scan Dijkstra | O(V^2 + V * E) | O(V) |
| 3 | TreeSet Dijkstra | O((V + E) log V) | O(V + E) |
| 4 | Min-Heap with Lazy Deletion (Optimized) | O((V + E) log V) | O(V + E) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### DijkstraShortestPath.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraShortestPath {

    private static final int INFINITY = Integer.MAX_VALUE;

    public static void main(String[] args) {
        int vertices = 5;
        int[][] edges = {{0, 1, 4}, {0, 2, 1}, {2, 1, 2}, {1, 3, 1}, {2, 3, 5}};
        System.out.println("Input  : vertices = " + vertices + ", edges (from, to, weight) = " + Arrays.deepToString(edges) + ", source = 0");
        int[] parent = new int[vertices];
        int[] dist = shortestDistances(vertices, edges, 0, parent);
        System.out.println("Output : distances = " + describe(dist));
        System.out.println("         path to 3 = " + path(parent, 0, 3) + " (cost " + dist[3] + ")");
        System.out.println("         path to 4 = " + path(parent, 0, 4));
    }

    /** Returns the distances from {@code source}; {@code Integer.MAX_VALUE} marks unreachable vertices. */
    public static int[] shortestDistances(int n, int[][] edges, int source, int[] parent) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(new int[]{edge[1], edge[2]});
        }

        int[] dist = new int[n];
        Arrays.fill(dist, INFINITY);
        Arrays.fill(parent, -1);
        dist[source] = 0;

        // heap entries are {distance, vertex}
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        heap.add(new int[]{0, source});
        while (!heap.isEmpty()) {
            int[] current = heap.poll();
            int u = current[1];
            if (current[0] > dist[u]) {
                continue;
            }
            for (int[] edge : graph.get(u)) {
                int v = edge[0];
                int candidate = dist[u] + edge[1];
                if (candidate < dist[v]) {
                    dist[v] = candidate;
                    parent[v] = u;
                    heap.add(new int[]{candidate, v});
                }
            }
        }
        return dist;
    }

    /** Rebuilds the source-to-target path from the parent array, or an empty list if unreachable. */
    public static List<Integer> path(int[] parent, int source, int target) {
        LinkedList<Integer> path = new LinkedList<>();
        for (int v = target; v != -1; v = parent[v]) {
            path.addFirst(v);
        }
        return path.getFirst() == source ? path : new LinkedList<>();
    }

    private static String describe(int[] dist) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < dist.length; i++) {
            sb.append(i > 0 ? ", " : "").append(dist[i] == INFINITY ? "unreachable" : String.valueOf(dist[i]));
        }
        return sb.append(']').toString();
    }
}
```
