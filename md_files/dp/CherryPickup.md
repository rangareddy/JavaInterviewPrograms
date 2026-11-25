# Cherry Pickup

An `n x n` grid has cells `0` (empty), `1` (a cherry) and `-1` (a thorn that blocks the way). Start at `(0, 0)`, walk to `(n - 1, n - 1)` moving only right or down, then walk back to `(0, 0)` moving only left or up. Each cherry you pass is picked and the cell becomes empty. Return the maximum number of cherries you can pick, or `0` if there is no valid path.

## Algorithm Strategy (Two Walkers Moving Down-Right Together)

1. The return trip reversed is another down-right path, so the problem is two paths from `(0, 0)` to `(n - 1, n - 1)` picking as many distinct cherries as possible.
2. Move both walkers step by step. After `t` steps, walker one is at `(r1, t - r1)` and walker two at `(r2, t - r2)`.
3. DP over `(t, r1, r2)`: take the best of the four ways the walkers could have arrived, add the cherries on their cells (counting a shared cell once), and block thorns.
4. The answer is `max(0, dp[2n - 2][n - 1][n - 1])`.

## Example walkthrough

- For `[[0,1,-1],[1,0,-1],[1,1,1]]` one walker goes down the left column and the other along the top row and the bottom.
- Result: `5`.

## Complexity Analysis

- **Time Complexity:** `O(n^3)` — `2n - 1` steps times `n^2` position pairs.
- **Space Complexity:** `O(n^2)` — A rolling layer of the DP.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Greedy Two Trips (Incorrect in General)

Take the best path there, clear it, then take the best path back. This can miss the optimum, which is why the problem needs the two-walker DP.

```java
public static int greedyTwoTrips(int[][] in) {
    int n = in.length; int[][] g = new int[n][]; for (int i = 0; i < n; i++) g[i] = in[i].clone(); int total = 0;
    for (int trip = 0; trip < 2; trip++) { int[][] d = new int[n][n]; for (int[] row : d) java.util.Arrays.fill(row, Integer.MIN_VALUE); d[0][0] = g[0][0]; if (g[0][0] == -1) return 0;
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) { if ((i == 0 && j == 0) || g[i][j] == -1) continue; int b = Integer.MIN_VALUE; if (i > 0) b = Math.max(b, d[i - 1][j]); if (j > 0) b = Math.max(b, d[i][j - 1]); if (b != Integer.MIN_VALUE) d[i][j] = b + g[i][j]; }
        if (d[n - 1][n - 1] == Integer.MIN_VALUE) return 0; total += d[n - 1][n - 1]; int i = n - 1, j = n - 1; g[i][j] = g[i][j] == 1 ? 0 : g[i][j];
        while (i > 0 || j > 0) { if (i > 0 && d[i - 1][j] != Integer.MIN_VALUE && d[i][j] - g[i][j] == d[i - 1][j] || (j == 0)) i--; else j--; if (g[i][j] == 1) g[i][j] = 0; } }
    return total;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n^2)

### Approach 2: Memoised Recursion on Two Walkers

`f(r1, c1, r2)` is the best total from the walkers' positions to the end.

```java
public static int memo(int[][] g) { int n = g.length; int[][][] m = new int[n][n][n]; for (int[][] a : m) for (int[] b : a) java.util.Arrays.fill(b, -2); return Math.max(0, go(g, 0, 0, 0, m)); }
private static int go(int[][] g, int r1, int c1, int r2, int[][][] m) {
    int n = g.length, c2 = r1 + c1 - r2; if (r1 >= n || c1 >= n || r2 >= n || c2 >= n || g[r1][c1] == -1 || g[r2][c2] == -1) return Integer.MIN_VALUE;
    if (r1 == n - 1 && c1 == n - 1) return g[r1][c1]; if (m[r1][c1][r2] != -2) return m[r1][c1][r2];
    int best = Math.max(Math.max(go(g, r1 + 1, c1, r2 + 1, m), go(g, r1, c1 + 1, r2, m)), Math.max(go(g, r1 + 1, c1, r2, m), go(g, r1, c1 + 1, r2 + 1, m)));
    if (best == Integer.MIN_VALUE) return m[r1][c1][r2] = Integer.MIN_VALUE; int cher = g[r1][c1] + (r1 == r2 ? 0 : g[r2][c2]); return m[r1][c1][r2] = best + cher;
}
```

- **Time Complexity:** O(n^3)
- **Space Complexity:** O(n^3)

### Approach 3: Bottom-Up DP over Steps and Rows (Optimized)

Advance both walkers one step at a time with a rolling layer. This is the version implemented in the repository.

```java
public static int rolling(int[][] g) {
    if (g[0][0] == -1) return 0; int n = g.length; int[][] dp = new int[n][n]; for (int[] r : dp) java.util.Arrays.fill(r, Integer.MIN_VALUE); dp[0][0] = g[0][0];
    for (int t = 1; t <= 2 * n - 2; t++) { int[][] nx = new int[n][n]; for (int[] r : nx) java.util.Arrays.fill(r, Integer.MIN_VALUE);
        for (int r1 = Math.max(0, t - n + 1); r1 <= Math.min(n - 1, t); r1++) for (int r2 = Math.max(0, t - n + 1); r2 <= Math.min(n - 1, t); r2++) { int c1 = t - r1, c2 = t - r2; if (g[r1][c1] == -1 || g[r2][c2] == -1) continue;
            int b = dp[r1][r2]; if (r1 > 0) b = Math.max(b, dp[r1 - 1][r2]); if (r2 > 0) b = Math.max(b, dp[r1][r2 - 1]); if (r1 > 0 && r2 > 0) b = Math.max(b, dp[r1 - 1][r2 - 1]); if (b == Integer.MIN_VALUE) continue; nx[r1][r2] = b + g[r1][c1] + (r1 == r2 ? 0 : g[r2][c2]); }
        dp = nx; }
    return Math.max(0, dp[n - 1][n - 1]);
}
```

- **Time Complexity:** O(n^3)
- **Space Complexity:** O(n^2)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Greedy Two Trips (Incorrect in General) | O(n^2) | O(n^2) |
| 2 | Memoised Recursion on Two Walkers | O(n^3) | O(n^3) |
| 3 | Bottom-Up DP over Steps and Rows (Optimized) | O(n^3) | O(n^2) |

## Source Code

### CherryPickup.java

```java
import java.util.Arrays;

public class CherryPickup {

    public static void main(String[] args) {
        int[][][] grids = {{{0, 1, -1}, {1, 0, -1}, {1, 1, 1}}, {{1, 1, -1}, {1, -1, 1}, {-1, 1, 1}}, {{1}}, {{0, 0}, {0, 0}}};
        for (int[][] grid : grids) {
            System.out.println("Input  : grid = " + Arrays.deepToString(grid));
            System.out.println("Output : " + cherryPickup(grid));
            System.out.println();
        }
    }

    public static int cherryPickup(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0][0] == -1) {
            return 0;
        }
        int n = grid.length;
        int[][] dp = new int[n][n];
        for (int[] row : dp) {
            Arrays.fill(row, Integer.MIN_VALUE);
        }
        dp[0][0] = grid[0][0];
        for (int t = 1; t <= 2 * n - 2; t++) {
            int[][] next = new int[n][n];
            for (int[] row : next) {
                Arrays.fill(row, Integer.MIN_VALUE);
            }
            for (int r1 = Math.max(0, t - n + 1); r1 <= Math.min(n - 1, t); r1++) {
                for (int r2 = Math.max(0, t - n + 1); r2 <= Math.min(n - 1, t); r2++) {
                    int c1 = t - r1;
                    int c2 = t - r2;
                    if (grid[r1][c1] == -1 || grid[r2][c2] == -1) {
                        continue;
                    }
                    int best = dp[r1][r2];
                    if (r1 > 0) {
                        best = Math.max(best, dp[r1 - 1][r2]);
                    }
                    if (r2 > 0) {
                        best = Math.max(best, dp[r1][r2 - 1]);
                    }
                    if (r1 > 0 && r2 > 0) {
                        best = Math.max(best, dp[r1 - 1][r2 - 1]);
                    }
                    if (best == Integer.MIN_VALUE) {
                        continue;
                    }
                    int cherries = grid[r1][c1] + (r1 == r2 ? 0 : grid[r2][c2]);
                    next[r1][r2] = best + cherries;
                }
            }
            dp = next;
        }
        return Math.max(0, dp[n - 1][n - 1]);
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository. (The greedy two-trip idea is shown only as a wrong turn; it is not used.)

The idea: the trip back is just another down-right path read backward, so the two trips become two walkers who move one step at a time from the same start. Because both have made the same number of steps, a state is just the step number and the two rows. A cell shared by both walkers counts once.

**Input:** `[[0, 1, -1], [1, 0, -1], [1, 1, 1]]`.

| Step | Walker 1 | Walker 2 | Cherries so far |
|------|----------|----------|-----------------|
| 0 | (0,0) | (0,0) | 0 |
| 1 | (1,0) | (0,1) | 2 |
| 2 | (2,0) | (1,1)... | |
| 4 | (2,2) | (2,2) | 5 |

**Output:** `5`.
