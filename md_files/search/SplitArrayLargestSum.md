# Split Array Largest Sum

Given an integer array `nums` and an integer `k`, split `nums` into `k` non-empty contiguous subarrays so that the largest sum of any subarray is minimized. Return that minimized largest sum.

## Algorithm Strategy (Binary Search on the Answer)

1. The answer lies between `max(nums)` (each element alone) and `sum(nums)` (one subarray).
2. For a candidate limit `cap`, greedily fill each subarray until adding the next value would exceed `cap`, then start a new one and count the pieces.
3. If the pieces needed are at most `k`, `cap` is feasible, so try a smaller limit; otherwise try a larger one.
4. The smallest feasible `cap` is the answer.

## Example walkthrough

- For `[7, 2, 5, 10, 8]` and `k = 2`: with `cap = 18` the greedy split is `[7,2,5]` and `[10,8]`, two pieces, so 18 is feasible; 17 needs three pieces.
- Result: `18`.

## Complexity Analysis

- **Time Complexity:** `O(n log(sum))` — A binary search over the sum range with an O(n) check per step.
- **Space Complexity:** `O(1)` — Only counters.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursive Brute Force

Try every position for the first cut and recurse on the rest. Exponential.

```java
public static int bruteForce(int[] a, int k) { return solve(a, 0, k); }
private static int solve(int[] a, int start, int k) {
    if (k == 1) { int s = 0; for (int i = start; i < a.length; i++) s += a[i]; return s; }
    int best = Integer.MAX_VALUE, sum = 0;
    for (int i = start; i <= a.length - k; i++) { sum += a[i]; best = Math.min(best, Math.max(sum, solve(a, i + 1, k - 1))); }
    return best;
}
```

- **Time Complexity:** O(C(n, k) * n)
- **Space Complexity:** O(k)

### Approach 2: DP over Prefix Sums

`dp[j][i]` is the best answer for the first `i` numbers in `j` pieces.

```java
public static int dp(int[] a, int k) {
    int n = a.length; int[] pre = new int[n + 1];
    for (int i = 0; i < n; i++) pre[i + 1] = pre[i] + a[i];
    int[][] d = new int[k + 1][n + 1];
    for (int[] row : d) java.util.Arrays.fill(row, Integer.MAX_VALUE);
    d[0][0] = 0;
    for (int j = 1; j <= k; j++) for (int i = j; i <= n; i++)
        for (int p = j - 1; p < i; p++) if (d[j - 1][p] != Integer.MAX_VALUE) d[j][i] = Math.min(d[j][i], Math.max(d[j - 1][p], pre[i] - pre[p]));
    return d[k][n];
}
```

- **Time Complexity:** O(k * n^2)
- **Space Complexity:** O(k * n)

### Approach 3: Binary Search on the Answer (Optimized)

Search the smallest limit that fits in `k` pieces. This is the version implemented in the repository.

```java
public static int binarySearch(int[] a, int k) {
    int lo = 0, hi = 0; for (int x : a) { lo = Math.max(lo, x); hi += x; }
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2, pieces = 1, sum = 0;
        for (int x : a) { if (sum + x > mid) { pieces++; sum = 0; } sum += x; }
        if (pieces <= k) hi = mid; else lo = mid + 1;
    }
    return lo;
}
```

- **Time Complexity:** O(n log(sum))
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursive Brute Force | O(C(n, k) * n) | O(k) |
| 2 | DP over Prefix Sums | O(k * n^2) | O(k * n) |
| 3 | Binary Search on the Answer (Optimized) | O(n log(sum)) | O(1) |

## Source Code

### SplitArrayLargestSum.java

```java
import java.util.Arrays;

public class SplitArrayLargestSum {

    public static void main(String[] args) {
        int[][] nums = {{7, 2, 5, 10, 8}, {1, 2, 3, 4, 5}, {1, 4, 4}, {5}};
        int[] ks = {2, 2, 3, 1};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + splitArray(nums[i], ks[i]));
            System.out.println();
        }
    }

    public static int splitArray(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return 0;
        }
        int lo = 0;
        int hi = 0;
        for (int x : nums) {
            lo = Math.max(lo, x);
            hi += x;
        }
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (piecesNeeded(nums, mid) <= k) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    private static int piecesNeeded(int[] nums, int cap) {
        int pieces = 1;
        int sum = 0;
        for (int x : nums) {
            if (sum + x > cap) {
                pieces++;
                sum = 0;
            }
            sum += x;
        }
        return pieces;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: instead of choosing the cuts, guess the answer. Checking one guess is easy (greedy fill), and feasibility is monotonic, so a binary search finds the smallest guess that works.

**Input:** `nums = [7, 2, 5, 10, 8]`, `k = 2`. Search range: `[10, 32]`.

| `lo` | `hi` | `mid` | Pieces with `cap = mid` | Decision |
|------|------|-------|--------------------------|----------|
| 10 | 32 | 21 | 2 | feasible, `hi = 21` |
| 10 | 21 | 15 | 3 | too small, `lo = 16` |
| 16 | 21 | 18 | 2 | feasible, `hi = 18` |
| 16 | 18 | 17 | 3 | too small, `lo = 18` |

`lo == hi == 18`.

**Output:** `18`.
