# Is Graph Bipartite?

There is an undirected graph with `n` nodes given as an adjacency list `graph`. Return `true` if and only if it is bipartite: its nodes can be split into two sets so that every edge connects a node of one set to a node of the other.

## Algorithm Strategy (BFS Two-Colouring)

1. Give every node a colour: 0 (uncoloured), 1 or 2.
2. For each uncoloured node (the graph may be disconnected), colour it 1 and run a BFS.
3. For every neighbour: if it is uncoloured, give it the opposite colour and enqueue it; if it has the same colour as the current node, an odd cycle exists, so return `false`.
4. If every component is coloured without conflict, return `true`.

## Example walkthrough

- For `[[1,2,3],[0,2],[0,1,3],[0,2]]`: nodes 0, 1 and 2 form a triangle, an odd cycle.
- Result: `false`.

## Complexity Analysis

- **Time Complexity:** `O(V + E)` — Every node and edge is visited once.
- **Space Complexity:** `O(V)` — The colour array and the queue.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: DFS Two-Colouring

Colour recursively and fail on a clash.

```java
public static boolean dfs(int[][] g) {
    int[] c = new int[g.length];
    for (int i = 0; i < g.length; i++) if (c[i] == 0 && !paint(g, c, i, 1)) return false;
    return true;
}
private static boolean paint(int[][] g, int[] c, int u, int col) {
    c[u] = col;
    for (int v : g[u]) { if (c[v] == col) return false; if (c[v] == 0 && !paint(g, c, v, 3 - col)) return false; }
    return true;
}
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V)

### Approach 2: Union-Find

Union all neighbours of a node together; if a node ends up in the same set as one of its neighbours, the graph is not bipartite.

```java
public static boolean unionFind(int[][] g) {
    int[] p = new int[g.length]; for (int i = 0; i < p.length; i++) p[i] = i;
    for (int u = 0; u < g.length; u++) {
        for (int v : g[u]) { if (find(p, u) == find(p, v)) return false; }
        for (int i = 1; i < g[u].length; i++) p[find(p, g[u][0])] = find(p, g[u][i]);
    }
    return true;
}
private static int find(int[] p, int x) { return p[x] == x ? x : (p[x] = find(p, p[x])); }
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V)

### Approach 3: BFS Two-Colouring (Optimized)

Alternate colours level by level. This is the version implemented in the repository.

```java
public static boolean bfs(int[][] g) {
    int[] c = new int[g.length];
    for (int s = 0; s < g.length; s++) {
        if (c[s] != 0) continue; c[s] = 1; java.util.Queue<Integer> q = new java.util.ArrayDeque<>(); q.add(s);
        while (!q.isEmpty()) { int u = q.poll(); for (int v : g[u]) { if (c[v] == 0) { c[v] = 3 - c[u]; q.add(v); } else if (c[v] == c[u]) return false; } }
    }
    return true;
}
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | DFS Two-Colouring | O(V + E) | O(V) |
| 2 | Union-Find | O(V + E) | O(V) |
| 3 | BFS Two-Colouring (Optimized) | O(V + E) | O(V) |

## Source Code

### IsGraphBipartite.java

```java
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class IsGraphBipartite {

    public static void main(String[] args) {
        int[][][] graphs = {
            {{1, 2, 3}, {0, 2}, {0, 1, 3}, {0, 2}},
            {{1, 3}, {0, 2}, {1, 3}, {0, 2}},
            {{}, {2}, {1}, {}},
            {{1}, {0}}
        };
        for (int[][] graph : graphs) {
            System.out.println("Input  : graph = " + Arrays.deepToString(graph));
            System.out.println("Output : " + isBipartite(graph));
            System.out.println();
        }
    }

    public static boolean isBipartite(int[][] graph) {
        if (graph == null) {
            return true;
        }
        int[] color = new int[graph.length];
        for (int start = 0; start < graph.length; start++) {
            if (color[start] != 0) {
                continue;
            }
            color[start] = 1;
            Queue<Integer> queue = new ArrayDeque<>();
            queue.add(start);
            while (!queue.isEmpty()) {
                int node = queue.poll();
                for (int next : graph[node]) {
                    if (color[next] == 0) {
                        color[next] = 3 - color[node];
                        queue.add(next);
                    } else if (color[next] == color[node]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: a graph is bipartite exactly when it has no odd cycle. Colouring neighbours with the opposite colour finds one: an edge between two nodes of the same colour closes an odd cycle.

**Input:** `[[1, 2, 3], [0, 2], [0, 1, 3], [0, 2]]`.

| Step | Node | Colour | Neighbour check |
|------|------|--------|-----------------|
| 1 | 0 | 1 | neighbours 1, 2, 3 get colour 2 |
| 2 | 1 | 2 | neighbour 2 already has colour 2: conflict |

**Output:** `false`.
