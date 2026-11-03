# Network Delay Time

A network has `n` nodes labeled `1` to `n`. Given directed travel times `times[i] = [u, v, w]` (a signal sent from `u` reaches `v` after `w` time units) and a starting node `k`, return the minimum time for all nodes to receive a signal sent from `k`, or -1 if it is impossible.

## Algorithm Strategy (Dijkstra's Shortest Paths)

1. The time for all nodes to receive the signal is the largest shortest-path distance from `k`.
2. Build adjacency lists. Keep `dist[]` (initially infinity, 0 for `k`) and a min-heap of `(distance, node)`.
3. Repeatedly take the node with the smallest tentative distance; skip stale heap entries. For each outgoing edge `(u, v, w)`, relax: if `dist[u] + w < dist[v]`, update `dist[v]` and push it.
4. If any node was never reached the answer is -1; otherwise it is the maximum distance.

## Example walkthrough

- For `times = [[2,1,1],[2,3,1],[3,4,1]]`, `n = 4`, `k = 2`: distances are 1 (node 1), 0 (node 2), 1 (node 3), 2 (node 4).
- Result: `2`. Node 1 cannot be reached in `([[1,2,1]], 2, 2)`: `-1`.

## Complexity Analysis

- **Time Complexity:** `O(E log V)` — Each edge may push one heap entry.
- **Space Complexity:** `O(V + E)` — Adjacency lists, distances and heap.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Bellman-Ford

Relax every edge up to `n - 1` times (stopping early when nothing changes). Slower than Dijkstra but simple, and it also works with negative weights.

```java
public static int networkDelayTimeBellmanFord(int[][] times, int n, int k) {
    int[] dist = new int[n + 1];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[k] = 0;
    for (int round = 1; round < n; round++) {
        boolean changed = false;
        for (int[] edge : times) {
            if (dist[edge[0]] != Integer.MAX_VALUE && dist[edge[0]] + edge[2] < dist[edge[1]]) { dist[edge[1]] = dist[edge[0]] + edge[2]; changed = true; }
        }
        if (!changed) break;
    }
    int answer = 0;
    for (int node = 1; node <= n; node++) {
        if (dist[node] == Integer.MAX_VALUE) return -1;
        answer = Math.max(answer, dist[node]);
    }
    return answer;
}
```

- **Time Complexity:** O(V * E)
- **Space Complexity:** O(V)

### Approach 2: Floyd-Warshall

Compute the shortest distance between all pairs of nodes with three nested loops and read the row of the source. Cubic, so only for small `n`, but it answers every source at once.

```java
public static int networkDelayTimeFloyd(int[][] times, int n, int k) {
    final int INF = 1_000_000_000;
    int[][] dist = new int[n + 1][n + 1];
    for (int[] row : dist) Arrays.fill(row, INF);
    for (int i = 1; i <= n; i++) dist[i][i] = 0;
    for (int[] edge : times) dist[edge[0]][edge[1]] = Math.min(dist[edge[0]][edge[1]], edge[2]);
    for (int via = 1; via <= n; via++)
        for (int from = 1; from <= n; from++)
            for (int to = 1; to <= n; to++)
                if (dist[from][via] + dist[via][to] < dist[from][to]) dist[from][to] = dist[from][via] + dist[via][to];
    int answer = 0;
    for (int node = 1; node <= n; node++) {
        if (dist[k][node] >= INF) return -1;
        answer = Math.max(answer, dist[k][node]);
    }
    return answer;
}
```

- **Time Complexity:** O(V^3)
- **Space Complexity:** O(V^2)

### Approach 3: SPFA (Queue-Based Bellman-Ford)

Only nodes whose distance improved are re-examined: keep them in a queue and relax their outgoing edges. Usually much faster than plain Bellman-Ford, with the same worst case.

```java
public static int networkDelayTimeSpfa(int[][] times, int n, int k) {
    List<List<int[]>> graph = new ArrayList<>();
    for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());
    for (int[] edge : times) graph.get(edge[0]).add(new int[]{edge[1], edge[2]});
    int[] dist = new int[n + 1];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[k] = 0;
    Queue<Integer> queue = new LinkedList<>();
    boolean[] queued = new boolean[n + 1];
    queue.add(k);
    queued[k] = true;
    while (!queue.isEmpty()) {
        int node = queue.poll();
        queued[node] = false;
        for (int[] edge : graph.get(node)) {
            if (dist[node] + edge[1] < dist[edge[0]]) {
                dist[edge[0]] = dist[node] + edge[1];
                if (!queued[edge[0]]) { queued[edge[0]] = true; queue.add(edge[0]); }
            }
        }
    }
    int answer = 0;
    for (int node = 1; node <= n; node++) {
        if (dist[node] == Integer.MAX_VALUE) return -1;
        answer = Math.max(answer, dist[node]);
    }
    return answer;
}
```

- **Time Complexity:** O(V * E) worst case, usually O(E)
- **Space Complexity:** O(V + E)

### Approach 4: Dijkstra with a Priority Queue (Optimized)

Settle nodes in order of their distance from the source with a min-heap, relaxing each edge once. This is the version implemented in the repository.

```java
public static int networkDelayTime(int[][] times, int n, int k) {
    List<List<int[]>> graph = new ArrayList<>();
    for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());
    for (int[] edge : times) graph.get(edge[0]).add(new int[]{edge[1], edge[2]});
    int[] dist = new int[n + 1];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[k] = 0;
    PriorityQueue<int[]> heap = new PriorityQueue<>(Comparator.comparingInt((int[] a) -> a[1]));
    heap.add(new int[]{k, 0});
    while (!heap.isEmpty()) {
        int[] current = heap.poll();
        if (current[1] > dist[current[0]]) continue;
        for (int[] edge : graph.get(current[0])) {
            int candidate = dist[current[0]] + edge[1];
            if (candidate < dist[edge[0]]) { dist[edge[0]] = candidate; heap.add(new int[]{edge[0], candidate}); }
        }
    }
    int answer = 0;
    for (int node = 1; node <= n; node++) {
        if (dist[node] == Integer.MAX_VALUE) return -1;
        answer = Math.max(answer, dist[node]);
    }
    return answer;
}
```

- **Time Complexity:** O(E log V)
- **Space Complexity:** O(V + E)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Bellman-Ford | O(V * E) | O(V) |
| 2 | Floyd-Warshall | O(V^3) | O(V^2) |
| 3 | SPFA (Queue-Based Bellman-Ford) | O(V * E) worst case, usually O(E) | O(V + E) |
| 4 | Dijkstra with a Priority Queue (Optimized) | O(E log V) | O(V + E) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### NetworkDelayTime.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class NetworkDelayTime {

    public static void main(String[] args) {
        int[][][] times = {{{2, 1, 1}, {2, 3, 1}, {3, 4, 1}}, {{1, 2, 1}}, {{1, 2, 1}}, {{1, 2, 1}, {2, 3, 2}, {1, 3, 4}}};
        int[] ns = {4, 2, 2, 3};
        int[] ks = {2, 1, 2, 1};
        for (int i = 0; i < ns.length; i++) {
            System.out.println("Input  : times = " + Arrays.deepToString(times[i]) + ", n = " + ns[i] + ", k = " + ks[i]);
            System.out.println("Output : " + networkDelayTime(times[i], ns[i], ks[i]));
            System.out.println();
        }
    }

    public static int networkDelayTime(int[][] times, int n, int k) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : times) {
            graph.get(edge[0]).add(new int[]{edge[1], edge[2]});
        }

        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;
        PriorityQueue<int[]> heap = new PriorityQueue<>(Comparator.comparingInt((int[] a) -> a[1]));       // {node, distance}
        heap.add(new int[]{k, 0});
        while (!heap.isEmpty()) {
            int[] current = heap.poll();
            int node = current[0];
            if (current[1] > dist[node]) {
                continue;                           // stale entry
            }
            for (int[] edge : graph.get(node)) {
                int newDistance = dist[node] + edge[1];
                if (newDistance < dist[edge[0]]) {
                    dist[edge[0]] = newDistance;
                    heap.add(new int[]{edge[0], newDistance});
                }
            }
        }

        int answer = 0;
        for (int node = 1; node <= n; node++) {
            if (dist[node] == Integer.MAX_VALUE) {
                return -1;
            }
            answer = Math.max(answer, dist[node]);
        }
        return answer;
    }
}
```
