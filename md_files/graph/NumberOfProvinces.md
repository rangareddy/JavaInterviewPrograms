# Number of Provinces

There are `n` cities. Given an `n x n` matrix `isConnected` where `isConnected[i][j] = 1` if city `i` and city `j` are directly connected, and 0 otherwise, return the number of provinces. A province is a group of directly or indirectly connected cities with no connection to cities outside the group.

## Algorithm Strategy (Union-Find over Connected Cities)

1. Start with `n` provinces, one per city, each city being its own parent.
2. For every pair `i < j` with `isConnected[i][j] == 1`, find the roots of both cities.
3. If the roots differ, merge them and decrease the province count by one.
4. Return the count (use path compression so `find` stays almost constant).

## Example walkthrough

- For `[[1,1,0],[1,1,0],[0,0,1]]`: cities 0 and 1 merge, city 2 stays alone.
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n^2 * alpha(n))` — Every matrix cell is inspected once.
- **Space Complexity:** `O(n)` — The parent array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: DFS from Every Unvisited City

Start a DFS at each unvisited city and count the starts.

```java
public static int dfs(int[][] m) {
    int n = m.length, c = 0; boolean[] seen = new boolean[n]; for (int i = 0; i < n; i++) if (!seen[i]) { c++; visit(m, i, seen); } return c;
}
private static void visit(int[][] m, int u, boolean[] seen) { seen[u] = true; for (int v = 0; v < m.length; v++) if (m[u][v] == 1 && !seen[v]) visit(m, v, seen); }
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 2: Union-Find (Optimized)

Merge connected cities and count the roots. This is the version implemented in the repository.

```java
public static int unionFind(int[][] m) {
    int n = m.length; int[] p = new int[n]; for (int i = 0; i < n; i++) p[i] = i; int c = n;
    for (int i = 0; i < n; i++) for (int j = i + 1; j < n; j++) if (m[i][j] == 1) { int a = find(p, i), b = find(p, j); if (a != b) { p[a] = b; c--; } }
    return c;
}
private static int find(int[] p, int x) { while (p[x] != x) { p[x] = p[p[x]]; x = p[x]; } return x; }
```

- **Time Complexity:** O(n^2 * alpha(n))
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | DFS from Every Unvisited City | O(n^2) | O(n) |
| 2 | Union-Find (Optimized) | O(n^2 * alpha(n)) | O(n) |

## Source Code

### NumberOfProvinces.java

```java
import java.util.Arrays;

public class NumberOfProvinces {

    public static void main(String[] args) {
        int[][][] inputs = {{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}}, {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}}, {{1}}, {}};
        for (int[][] matrix : inputs) {
            System.out.println("Input  : isConnected = " + Arrays.deepToString(matrix));
            System.out.println("Output : " + findCircleNum(matrix));
            System.out.println();
        }
    }

    public static int findCircleNum(int[][] isConnected) {
        if (isConnected == null || isConnected.length == 0) {
            return 0;
        }
        int n = isConnected.length;
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        int provinces = n;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (isConnected[i][j] == 1) {
                    int a = find(parent, i);
                    int b = find(parent, j);
                    if (a != b) {
                        parent[a] = b;
                        provinces--;
                    }
                }
            }
        }
        return provinces;
    }

    private static int find(int[] parent, int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: every merge of two different groups reduces the number of provinces by exactly one, and merging two cities already in the same group changes nothing. Starting from `n` groups and merging along every connection leaves the number of provinces.

**Input:** `[[1,1,0],[1,1,0],[0,0,1]]`.

| Pair | Connected? | Roots differ? | Provinces |
|------|-----------|---------------|-----------|
| (0, 1) | yes | yes, merge | 3 -> 2 |
| (0, 2) | no | | 2 |
| (1, 2) | no | | 2 |

**Output:** `2`.
