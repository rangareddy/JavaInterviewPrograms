# Topological Sort

Given a directed graph with `n` vertices (numbered from 0) and a list of directed edges `[from, to]`, return an ordering of the vertices in which every edge goes from an earlier vertex to a later one. If the graph contains a cycle no such ordering exists and an empty list is returned. When several orderings are possible, return the one with the smallest vertex number first.

## Algorithm Strategy (Kahn's Algorithm with a Min-Heap)

1. Count the incoming edges (in-degree) of every vertex and build the adjacency list.
2. Put every vertex with in-degree 0 into a min-heap: those vertices have no unfinished prerequisites.
3. Repeatedly remove the smallest vertex from the heap, append it to the order and decrease the in-degree of its successors; any successor that reaches 0 joins the heap.
4. If the order contains all `n` vertices it is valid. Otherwise the remaining vertices are part of (or behind) a cycle: return an empty list.

## Example walkthrough

- For `n = 6` and edges `5->2, 5->0, 4->0, 4->1, 2->3, 3->1`: the in-degree-0 vertices are 4 and 5; taking the smallest first gives `[4, 5, 0, 2, 3, 1]`.
- For the edges `0->1, 1->2, 2->0` every vertex has an incoming edge, so the order stays empty: a cycle.

## Complexity Analysis

- **Time Complexity:** `O((V + E) log V)` — Every vertex goes through the heap once and every edge is relaxed once; with a plain queue it is O(V + E).
- **Space Complexity:** `O(V + E)` — Adjacency list, in-degrees and the heap.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: DFS with a Finishing Stack

Run a DFS and push each vertex after all its successors are finished; the reversed finishing order is a topological order. A vertex found while still on the current path means a cycle.

```java
public static List<Integer> topologicalSortDfs(int n, int[][] edges) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    for (int[] e : edges) graph.get(e[0]).add(e[1]);
    int[] state = new int[n];                    // 0 = new, 1 = on the current path, 2 = done
    LinkedList<Integer> order = new LinkedList<>();
    for (int v = 0; v < n; v++)
        if (state[v] == 0 && !visit(graph, v, state, order)) return new ArrayList<>();
    return order;
}

private static boolean visit(List<List<Integer>> graph, int v, int[] state, LinkedList<Integer> order) {
    state[v] = 1;
    for (int next : graph.get(v)) {
        if (state[next] == 1) return false;
        if (state[next] == 0 && !visit(graph, next, state, order)) return false;
    }
    state[v] = 2;
    order.addFirst(v);
    return true;
}
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V + E)

### Approach 2: Kahn with a Plain Queue

Process vertices with in-degree 0 in the order they become ready. Linear time, but the order is not the smallest-first one.

```java
public static List<Integer> topologicalSortQueue(int n, int[][] edges) {
    List<List<Integer>> graph = new ArrayList<>();
    int[] inDegree = new int[n];
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    for (int[] e : edges) { graph.get(e[0]).add(e[1]); inDegree[e[1]]++; }
    Queue<Integer> queue = new ArrayDeque<>();
    for (int i = 0; i < n; i++) if (inDegree[i] == 0) queue.add(i);
    List<Integer> order = new ArrayList<>();
    while (!queue.isEmpty()) {
        int v = queue.poll();
        order.add(v);
        for (int next : graph.get(v)) if (--inDegree[next] == 0) queue.add(next);
    }
    return order.size() == n ? order : new ArrayList<>();
}
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V + E)

### Approach 3: Repeatedly Remove Sources

Scan for an unused vertex with no remaining incoming edge, remove it, and repeat. Quadratic, but easy to reason about.

```java
public static List<Integer> topologicalSortScan(int n, int[][] edges) {
    int[] inDegree = new int[n];
    for (int[] e : edges) inDegree[e[1]]++;
    boolean[] used = new boolean[n];
    List<Integer> order = new ArrayList<>();
    for (int step = 0; step < n; step++) {
        int pick = -1;
        for (int v = 0; v < n && pick < 0; v++) if (!used[v] && inDegree[v] == 0) pick = v;
        if (pick < 0) return new ArrayList<>();
        used[pick] = true;
        order.add(pick);
        for (int[] e : edges) if (e[0] == pick) inDegree[e[1]]--;
    }
    return order;
}
```

- **Time Complexity:** O(V * (V + E))
- **Space Complexity:** O(V)

### Approach 4: Kahn with a Min-Heap (Optimized)

Always take the smallest ready vertex, which gives a deterministic order. This is the version implemented in the repository.

```java
public static List<Integer> topologicalSort(int n, int[][] edges) {
    List<List<Integer>> graph = new ArrayList<>();
    int[] inDegree = new int[n];
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    for (int[] e : edges) { graph.get(e[0]).add(e[1]); inDegree[e[1]]++; }
    PriorityQueue<Integer> ready = new PriorityQueue<>();
    for (int i = 0; i < n; i++) if (inDegree[i] == 0) ready.add(i);
    List<Integer> order = new ArrayList<>();
    while (!ready.isEmpty()) {
        int v = ready.poll();
        order.add(v);
        for (int next : graph.get(v)) if (--inDegree[next] == 0) ready.add(next);
    }
    return order.size() == n ? order : new ArrayList<>();
}
```

- **Time Complexity:** O((V + E) log V)
- **Space Complexity:** O(V + E)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | DFS with a Finishing Stack | O(V + E) | O(V + E) |
| 2 | Kahn with a Plain Queue | O(V + E) | O(V + E) |
| 3 | Repeatedly Remove Sources | O(V * (V + E)) | O(V) |
| 4 | Kahn with a Min-Heap (Optimized) | O((V + E) log V) | O(V + E) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### TopologicalSort.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class TopologicalSort {

    public static void main(String[] args) {
        int[] sizes = {6, 3, 4, 1};
        int[][][] graphs = {
                {{5, 2}, {5, 0}, {4, 0}, {4, 1}, {2, 3}, {3, 1}},
                {{0, 1}, {1, 2}, {2, 0}},
                {{0, 1}, {0, 2}, {1, 3}, {2, 3}},
                {}
        };
        for (int i = 0; i < sizes.length; i++) {
            System.out.println("Input  : n = " + sizes[i] + ", edges = " + Arrays.deepToString(graphs[i]));
            List<Integer> order = topologicalSort(sizes[i], graphs[i]);
            System.out.println("Output : " + (order.isEmpty() ? "cycle detected (no ordering)" : order.toString()));
            System.out.println();
        }
    }

    public static List<Integer> topologicalSort(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        int[] inDegree = new int[n];
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            inDegree[edge[1]]++;
        }

        PriorityQueue<Integer> ready = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                ready.add(i);
            }
        }

        List<Integer> order = new ArrayList<>();
        while (!ready.isEmpty()) {
            int vertex = ready.poll();
            order.add(vertex);
            for (int next : graph.get(vertex)) {
                if (--inDegree[next] == 0) {
                    ready.add(next);
                }
            }
        }
        return order.size() == n ? order : new ArrayList<>();
    }
}
```
