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
