# Minimum Edge Reversals So Every Node Is Reachable

A directed graph of `n` nodes becomes a tree if the directions are ignored. Given the directed edges, for each node `i` return the minimum number of edge reversals so that node `i` can reach every other node by following directed edges.

## Algorithm Strategy (Rerooting DP)

1. Build an undirected adjacency list where each edge stores a cost: 0 if the edge already points away from the current node, 1 if it points towards it (needs a reversal).
2. Do one DFS from node 0 to compute `answer[0]`: the sum of the costs of all tree edges traversed.
3. Rerooting from a parent `u` to its child `v` along an edge of cost `w`: `answer[v] = answer[u] + (w == 0 ? 1 : -1)`. If the edge originally pointed `u -> v` (cost 0 going down), re-rooting at `v` now needs a reversal; if it pointed `v -> u` (cost 1), it no longer does.
4. Run a second DFS (iteratively, to avoid deep recursion) filling `answer` for every node.

## Example walkthrough

- For `n = 4` and edges `[[2,0],[2,1],[1,3]]`: from node 0 the edges 0-2 (must reverse), 2-1 and 1-3 cost 1 + 0 + 0.
- Result for node 0 is `1`; the full answer is `[1, 1, 0, 2]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Two traversals of the tree.
- **Space Complexity:** `O(n)` — The adjacency list and the answer.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Run a DFS from Every Node

For each root count the edges that point towards it. Quadratic.

```java
public static int[] everyRoot(int n, int[][] edges) {
    java.util.List<java.util.List<int[]>> g = new java.util.ArrayList<>(); for (int i = 0; i < n; i++) g.add(new java.util.ArrayList<>()); for (int[] e : edges) { g.get(e[0]).add(new int[]{e[1], 0}); g.get(e[1]).add(new int[]{e[0], 1}); }
    int[] ans = new int[n]; for (int root = 0; root < n; root++) { boolean[] seen = new boolean[n]; java.util.Deque<Integer> st = new java.util.ArrayDeque<>(); st.push(root); seen[root] = true; int c = 0;
        while (!st.isEmpty()) { int u = st.pop(); for (int[] nx : g.get(u)) if (!seen[nx[0]]) { seen[nx[0]] = true; c += nx[1]; st.push(nx[0]); } } ans[root] = c; }
    return ans;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 2: Rerooting DP (Optimized)

Compute the answer for node 0, then derive every other node's answer from its parent's in O(1). This is the version implemented in the repository.

```java
public static int[] reroot(int n, int[][] edges) {
    java.util.List<java.util.List<int[]>> g = new java.util.ArrayList<>(); for (int i = 0; i < n; i++) g.add(new java.util.ArrayList<>()); for (int[] e : edges) { g.get(e[0]).add(new int[]{e[1], 0}); g.get(e[1]).add(new int[]{e[0], 1}); }
    int[] ans = new int[n]; java.util.Deque<Integer> st = new java.util.ArrayDeque<>(); boolean[] seen = new boolean[n]; st.push(0); seen[0] = true;
    while (!st.isEmpty()) { int u = st.pop(); for (int[] nx : g.get(u)) if (!seen[nx[0]]) { seen[nx[0]] = true; ans[0] += nx[1]; st.push(nx[0]); } }
    java.util.Arrays.fill(seen, false); st.push(0); seen[0] = true;
    while (!st.isEmpty()) { int u = st.pop(); for (int[] nx : g.get(u)) if (!seen[nx[0]]) { seen[nx[0]] = true; ans[nx[0]] = ans[u] + (nx[1] == 0 ? 1 : -1); st.push(nx[0]); } }
    return ans;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Run a DFS from Every Node | O(n^2) | O(n) |
| 2 | Rerooting DP (Optimized) | O(n) | O(n) |

## Source Code

### MinimumEdgeReversalsSoEveryNodeIsReachable.java

```java
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class MinimumEdgeReversalsSoEveryNodeIsReachable {

    public static void main(String[] args) {
        int[] ns = {4, 3, 3};
        int[][][] edges = {{{2, 0}, {2, 1}, {1, 3}}, {{1, 2}, {2, 0}}, {{0, 1}, {2, 1}}};
        for (int i = 0; i < ns.length; i++) {
            System.out.println("Input  : n = " + ns[i] + ", edges = " + Arrays.deepToString(edges[i]));
            System.out.println("Output : " + Arrays.toString(minEdgeReversals(ns[i], edges[i])));
            System.out.println();
        }
    }

    public static int[] minEdgeReversals(int n, int[][] edges) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] e : edges) {
            graph.get(e[0]).add(new int[]{e[1], 0});
            graph.get(e[1]).add(new int[]{e[0], 1});
        }
        int[] answer = new int[n];
        Deque<int[]> stack = new ArrayDeque<>();
        boolean[] seen = new boolean[n];
        stack.push(new int[]{0, -1});
        seen[0] = true;
        while (!stack.isEmpty()) {
            int[] top = stack.pop();
            for (int[] next : graph.get(top[0])) {
                if (!seen[next[0]]) {
                    seen[next[0]] = true;
                    answer[0] += next[1];
                    stack.push(new int[]{next[0], top[0]});
                }
            }
        }
        Arrays.fill(seen, false);
        stack.push(new int[]{0, -1});
        seen[0] = true;
        while (!stack.isEmpty()) {
            int[] top = stack.pop();
            for (int[] next : graph.get(top[0])) {
                if (!seen[next[0]]) {
                    seen[next[0]] = true;
                    answer[next[0]] = answer[top[0]] + (next[1] == 0 ? 1 : -1);
                    stack.push(new int[]{next[0], top[0]});
                }
            }
        }
        return answer;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: if the tree is rooted at `v`, every edge must point away from `v`. Moving the root across one edge flips what that single edge needs and leaves every other edge unchanged, so a neighbour's answer differs from its parent's by exactly one.

**Input:** `n = 4`, `edges = [[2, 0], [2, 1], [1, 3]]`.

| Node | Reversals needed so that it reaches everything | Answer |
|------|-----------------------------------------------|--------|
| 2 | none: 2 -> 0, 2 -> 1 and 1 -> 3 all point away | 0 |
| 0 | the edge 2 -> 0 must be reversed | 1 |
| 1 | the edge 2 -> 1 must be reversed | 1 |
| 3 | the edges 1 -> 3 and 2 -> 1 must be reversed | 2 |

**Output for the first sample in `main`:** `[1, 1, 0, 2]`.
