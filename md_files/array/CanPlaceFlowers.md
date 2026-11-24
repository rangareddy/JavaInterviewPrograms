# Can Place Flowers

A flowerbed is an array of `0`s (empty) and `1`s (planted). Flowers cannot be planted in adjacent plots. Given the flowerbed and an integer `n`, return `true` if `n` new flowers can be planted without breaking the rule.

## Algorithm Strategy (Greedy Left-to-Right)

1. Scan the plots from the left.
2. A plot is plantable if it is empty and both neighbours are empty (a missing neighbour at either end counts as empty).
3. Plant greedily: set the plot to 1 and count it, which also blocks the next plot.
4. Return `true` once `n` flowers have been placed, or after the scan if the count is at least `n`.

## Example walkthrough

- For `[1, 0, 0, 0, 1]` and `n = 1`: the middle plot has empty neighbours, so one flower fits.
- Result: `true` for `n = 1`, and `false` for `n = 2`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — In place on a copy.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Try Every Placement (Backtracking)

Try planting at each free plot and recurse. Exponential.

```java
public static boolean backtrack(int[] bed, int n) { return go(bed.clone(), 0, n); }
private static boolean go(int[] b, int i, int n) {
    if (n == 0) return true;
    for (int j = i; j < b.length; j++) {
        boolean l = j == 0 || b[j - 1] == 0, r = j == b.length - 1 || b[j + 1] == 0;
        if (b[j] == 0 && l && r) { b[j] = 1; if (go(b, j + 2, n - 1)) return true; b[j] = 0; }
    }
    return false;
}
```

- **Time Complexity:** O(2^n)
- **Space Complexity:** O(n)

### Approach 2: Count Gaps

Each run of `z` zeros between ones can hold `(z - 1) / 2` flowers, with the ends padded by an extra zero.

```java
public static boolean gaps(int[] bed, int n) {
    int count = 0, zeros = 1;
    for (int x : bed) { if (x == 0) zeros++; else { count += (zeros - 1) / 2; zeros = 0; } }
    count += zeros / 2;
    return count >= n;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 3: Greedy Left-to-Right (Optimized)

Plant in the first plot that fits. This is the version implemented in the repository.

```java
public static boolean greedy(int[] bed, int n) {
    int[] b = bed.clone(); int planted = 0;
    for (int i = 0; i < b.length && planted < n; i++) {
        boolean l = i == 0 || b[i - 1] == 0, r = i == b.length - 1 || b[i + 1] == 0;
        if (b[i] == 0 && l && r) { b[i] = 1; planted++; }
    }
    return planted >= n;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Try Every Placement (Backtracking) | O(2^n) | O(n) |
| 2 | Count Gaps | O(n) | O(1) |
| 3 | Greedy Left-to-Right (Optimized) | O(n) | O(1) |

## Source Code

### CanPlaceFlowers.java

```java
import java.util.Arrays;

public class CanPlaceFlowers {

    public static void main(String[] args) {
        int[][] beds = {{1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {0, 0, 0}, {1}};
        int[] ns = {1, 2, 2, 0};
        for (int i = 0; i < beds.length; i++) {
            System.out.println("Input  : flowerbed = " + Arrays.toString(beds[i]) + ", n = " + ns[i]);
            System.out.println("Output : " + canPlaceFlowers(beds[i].clone(), ns[i]));
            System.out.println();
        }
    }

    public static boolean canPlaceFlowers(int[] flowerbed, int n) {
        if (n <= 0) {
            return true;
        }
        if (flowerbed == null) {
            return false;
        }
        int planted = 0;
        for (int i = 0; i < flowerbed.length && planted < n; i++) {
            boolean leftFree = i == 0 || flowerbed[i - 1] == 0;
            boolean rightFree = i == flowerbed.length - 1 || flowerbed[i + 1] == 0;
            if (flowerbed[i] == 0 && leftFree && rightFree) {
                flowerbed[i] = 1;
                planted++;
            }
        }
        return planted >= n;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: planting in the earliest possible plot never hurts, because it leaves the most room to the right. Mark each planted plot so the next plot is blocked.

**Input:** `flowerbed = [1, 0, 0, 0, 1]`, `n = 1`.

| `i` | Plot | Left free | Right free | Action | Planted |
|-----|------|-----------|------------|--------|---------|
| 0 | 1 | | | skip | 0 |
| 1 | 0 | no | yes | skip | 0 |
| 2 | 0 | yes | yes | plant | 1 |

**Output:** `true`.
