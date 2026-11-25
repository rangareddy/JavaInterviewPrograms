# Most Stones Removed with Same Row or Column

On a 2D plane, `n` stones are placed at integer coordinates, at most one per point. A stone can be removed if another stone remains in the same row or the same column. Return the largest number of stones that can be removed.

## Algorithm Strategy (Connected Components of Rows and Columns)

1. Treat each row and each column as a node. A stone at `(x, y)` joins the row `x` with the column `y`.
2. Use Union-Find: for every stone, union `x` with `y + OFFSET` (the offset keeps column ids apart from row ids).
3. In every connected group, all stones but one can be removed, by taking them from the leaves inward.
4. The answer is `stones - number of connected groups`.

## Example walkthrough

- For the six stones `[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]` everything is connected through shared rows and columns, so there is one group.
- Result: `6 - 1 = 5`.

## Complexity Analysis

- **Time Complexity:** `O(n * alpha(n))` — Nearly linear with path compression.
- **Space Complexity:** `O(n)` — The parent map.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: DFS over Stones

Treat each stone as a node connected to stones in the same row or column and count components with DFS. Quadratic in the number of stones.

```java
public static int dfs(int[][] s) {
    int n = s.length; boolean[] seen = new boolean[n]; int comps = 0;
    for (int i = 0; i < n; i++) if (!seen[i]) { comps++; java.util.Deque<Integer> st = new java.util.ArrayDeque<>(); st.push(i); seen[i] = true; while (!st.isEmpty()) { int u = st.pop(); for (int v = 0; v < n; v++) if (!seen[v] && (s[u][0] == s[v][0] || s[u][1] == s[v][1])) { seen[v] = true; st.push(v); } } }
    return n - comps;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 2: Union-Find on Rows and Columns (Optimized)

Union the row and the column of each stone and count groups. This is the version implemented in the repository.

```java
public static int unionFind(int[][] stones) {
    java.util.Map<Integer, Integer> p = new java.util.HashMap<>(); for (int[] s : stones) { int a = find(p, s[0]), b = find(p, s[1] + 10001); p.put(a, b); }
    java.util.Set<Integer> roots = new java.util.HashSet<>(); for (int k : new java.util.ArrayList<>(p.keySet())) roots.add(find(p, k)); return stones.length - roots.size();
}
private static int find(java.util.Map<Integer, Integer> p, int x) { p.putIfAbsent(x, x); while (p.get(x) != x) { p.put(x, p.get(p.get(x))); x = p.get(x); } return x; }
```

- **Time Complexity:** O(n * alpha(n))
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | DFS over Stones | O(n^2) | O(n) |
| 2 | Union-Find on Rows and Columns (Optimized) | O(n * alpha(n)) | O(n) |

## Source Code

### MostStonesRemovedWithSameRowOrColumn.java

```java
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MostStonesRemovedWithSameRowOrColumn {

    public static void main(String[] args) {
        int[][][] inputs = {{{0, 0}, {0, 1}, {1, 0}, {1, 2}, {2, 1}, {2, 2}}, {{0, 0}, {0, 2}, {1, 1}, {2, 0}, {2, 2}}, {{0, 0}}, {}};
        for (int[][] stones : inputs) {
            System.out.println("Input  : stones = " + Arrays.deepToString(stones));
            System.out.println("Output : " + removeStones(stones));
            System.out.println();
        }
    }

    public static int removeStones(int[][] stones) {
        if (stones == null || stones.length == 0) {
            return 0;
        }
        Map<Integer, Integer> parent = new HashMap<>();
        for (int[] stone : stones) {
            union(parent, stone[0], stone[1] + 10001);
        }
        Set<Integer> roots = new HashSet<>();
        for (int node : parent.keySet()) {
            roots.add(find(parent, node));
        }
        return stones.length - roots.size();
    }

    private static int find(Map<Integer, Integer> parent, int x) {
        parent.putIfAbsent(x, x);
        int root = x;
        while (parent.get(root) != root) {
            root = parent.get(root);
        }
        while (parent.get(x) != root) {
            int next = parent.get(x);
            parent.put(x, root);
            x = next;
        }
        return root;
    }

    private static void union(Map<Integer, Integer> parent, int a, int b) {
        parent.put(find(parent, a), find(parent, b));
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: stones sharing a row or column form a group, and one stone per group must stay, because the last stone of a group has nobody left to share a line with. Joining the row node and the column node of every stone makes the groups the connected components of that row-column graph, with no pairwise comparison of stones.

**Input:** `stones = [[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]]`.

| Stone | Union |
|-------|-------|
| (0,0) | row 0 with col 0 |
| (0,1) | row 0 with col 1 |
| (1,0) | row 1 with col 0 |
| (1,2) | row 1 with col 2 |
| (2,1) | row 2 with col 1 |
| (2,2) | row 2 with col 2 |

All rows and columns end in one component, so one stone stays.

**Output:** `6 - 1 = 5`.
