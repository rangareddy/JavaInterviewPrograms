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
