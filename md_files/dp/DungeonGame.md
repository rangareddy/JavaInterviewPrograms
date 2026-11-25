# Dungeon Game

A knight starts in the top-left cell of an `m x n` dungeon and must reach the bottom-right cell where a princess waits. Each cell holds an integer: a negative value drains health when entered, a positive value restores it. The knight dies if his health reaches 0 or below at any time. He moves only right or down. Return the minimum initial health required.

## Algorithm Strategy (Backward DP)

1. Let `need[i][j]` be the minimum health needed when entering cell `(i, j)` so that the knight can still reach the end.
2. At the princess's cell: `need = max(1, 1 - dungeon[i][j])`.
3. Elsewhere: `need = max(1, min(need[i+1][j], need[i][j+1]) - dungeon[i][j])`, taking the cheaper of the two ways onward.
4. The answer is `need[0][0]`. A single row of values is enough.

## Example walkthrough

- For `[[-2,-3,3],[-5,-10,1],[10,30,-5]]`: the best path is right, right, down, down, needing 7 health at the start.
- Result: `7`.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Each cell once.
- **Space Complexity:** `O(n)` — One row.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion over All Paths

Try going right and going down and keep the cheaper requirement. Exponential.

```java
public static int recursive(int[][] d) { return go(d, 0, 0); }
private static int go(int[][] d, int i, int j) {
    int m = d.length, n = d[0].length; if (i == m - 1 && j == n - 1) return Math.max(1, 1 - d[i][j]);
    int best = Integer.MAX_VALUE; if (i + 1 < m) best = Math.min(best, go(d, i + 1, j)); if (j + 1 < n) best = Math.min(best, go(d, i, j + 1));
    return Math.max(1, best - d[i][j]);
}
```

- **Time Complexity:** O(2^(m+n))
- **Space Complexity:** O(m + n)

### Approach 2: Full DP Table

Store `need` for every cell, filled from the bottom-right.

```java
public static int table(int[][] d) {
    int m = d.length, n = d[0].length; int[][] h = new int[m][n];
    for (int i = m - 1; i >= 0; i--) for (int j = n - 1; j >= 0; j--) {
        if (i == m - 1 && j == n - 1) h[i][j] = Math.max(1, 1 - d[i][j]);
        else { int best = Integer.MAX_VALUE; if (i + 1 < m) best = Math.min(best, h[i + 1][j]); if (j + 1 < n) best = Math.min(best, h[i][j + 1]); h[i][j] = Math.max(1, best - d[i][j]); }
    }
    return h[0][0];
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Approach 3: Rolling Row Backward DP (Optimized)

Keep one row of requirements. This is the version implemented in the repository.

```java
public static int rolling(int[][] d) {
    int m = d.length, n = d[0].length; int[] need = new int[n + 1]; java.util.Arrays.fill(need, Integer.MAX_VALUE); need[n - 1] = 1;
    for (int i = m - 1; i >= 0; i--) { for (int j = n - 1; j >= 0; j--) need[j] = Math.max(1, Math.min(need[j], need[j + 1]) - d[i][j]); need[n] = Integer.MAX_VALUE; }
    return need[0];
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion over All Paths | O(2^(m+n)) | O(m + n) |
| 2 | Full DP Table | O(m * n) | O(m * n) |
| 3 | Rolling Row Backward DP (Optimized) | O(m * n) | O(n) |

## Source Code

### DungeonGame.java

```java
import java.util.Arrays;

public class DungeonGame {

    public static void main(String[] args) {
        int[][][] dungeons = {{{-2, -3, 3}, {-5, -10, 1}, {10, 30, -5}}, {{0}}, {{100}}, {{-3, 5}}};
        for (int[][] dungeon : dungeons) {
            System.out.println("Input  : dungeon = " + Arrays.deepToString(dungeon));
            System.out.println("Output : " + calculateMinimumHP(dungeon));
            System.out.println();
        }
    }

    public static int calculateMinimumHP(int[][] dungeon) {
        if (dungeon == null || dungeon.length == 0 || dungeon[0].length == 0) {
            return 1;
        }
        int m = dungeon.length;
        int n = dungeon[0].length;
        int[] need = new int[n + 1];
        Arrays.fill(need, Integer.MAX_VALUE);
        need[n - 1] = 1;
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int onward = Math.min(need[j], need[j + 1]);
                need[j] = Math.max(1, onward - dungeon[i][j]);
            }
            need[n] = Integer.MAX_VALUE;
        }
        return need[0];
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: a forward DP does not work, because the best path so far may leave the knight too weak later. Going backward from the princess, each cell knows the health it needs on entering, which is exactly what the previous cell has to guarantee.

**Input:** `[[-2, -3, 3], [-5, -10, 1], [10, 30, -5]]`.

| Row (from the bottom) | `need` values for columns 0, 1, 2 |
|-----------------------|-----------------------------------|
| 2 | 1, 1, 6 |
| 1 | 6, 11, 5 |
| 0 | 7, 6, 2 |

For the princess cell: `max(1, 1 - (-5)) = 6`. For cell (0, 0): `max(1, min(6, 6) - (-2)) = 7`.

**Output:** `7`.
