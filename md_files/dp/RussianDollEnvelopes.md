# Russian Doll Envelopes

You are given envelopes `[width, height]`. One envelope fits inside another only if both its width and its height are strictly smaller. Return the maximum number of envelopes you can nest (Russian doll style). Rotation is not allowed.

## Algorithm Strategy (Sort by Width, then LIS on Heights)

1. Sort the envelopes by width ascending, and for equal widths by height descending.
2. After sorting, widths never decrease, and the descending height order for equal widths means two envelopes with the same width cannot both be chosen in an increasing height sequence.
3. So the problem becomes the longest strictly increasing subsequence of the heights.
4. Compute it with the `O(n log n)` tails array and binary search.

## Example walkthrough

- For `[[5,4],[6,4],[6,7],[2,3]]`: sorted by width then height descending: `[2,3],[5,4],[6,7],[6,4]`; the heights are `3, 4, 7, 4`.
- The longest strictly increasing subsequence is `3, 4, 7`: length `3`.

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — Sorting plus a binary search per envelope.
- **Space Complexity:** `O(n)` — The tails array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: DP over Sorted Envelopes

After sorting, `dp[i]` is the longest chain ending at envelope `i`, found by comparing with all earlier ones.

```java
public static int quadratic(int[][] in) {
    int[][] e = new int[in.length][]; for (int i = 0; i < e.length; i++) e[i] = in[i].clone(); java.util.Arrays.sort(e, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
    int[] dp = new int[e.length]; int best = 0;
    for (int i = 0; i < e.length; i++) { dp[i] = 1; for (int j = 0; j < i; j++) if (e[j][0] < e[i][0] && e[j][1] < e[i][1]) dp[i] = Math.max(dp[i], dp[j] + 1); best = Math.max(best, dp[i]); }
    return best;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 2: Sort and LIS with Binary Search (Optimized)

Sort widths ascending (heights descending on ties) and take the strictly increasing subsequence of heights. This is the version implemented in the repository.

```java
public static int lis(int[][] in) {
    int[][] e = new int[in.length][]; for (int i = 0; i < e.length; i++) e[i] = in[i].clone(); java.util.Arrays.sort(e, (a, b) -> a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]);
    int[] t = new int[e.length]; int size = 0;
    for (int[] x : e) { int lo = 0, hi = size; while (lo < hi) { int mid = (lo + hi) / 2; if (t[mid] < x[1]) lo = mid + 1; else hi = mid; } t[lo] = x[1]; if (lo == size) size++; }
    return size;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | DP over Sorted Envelopes | O(n^2) | O(n) |
| 2 | Sort and LIS with Binary Search (Optimized) | O(n log n) | O(n) |

## Source Code

### RussianDollEnvelopes.java

```java
import java.util.Arrays;

public class RussianDollEnvelopes {

    public static void main(String[] args) {
        int[][][] inputs = {{{5, 4}, {6, 4}, {6, 7}, {2, 3}}, {{1, 1}, {1, 1}, {1, 1}}, {{4, 5}, {4, 6}, {6, 7}, {2, 3}, {1, 1}}, {{1, 3}, {3, 5}, {6, 7}, {6, 8}, {8, 4}, {9, 5}}};
        for (int[][] envelopes : inputs) {
            System.out.println("Input  : envelopes = " + Arrays.deepToString(envelopes));
            System.out.println("Output : " + maxEnvelopes(envelopes.clone()));
            System.out.println();
        }
    }

    public static int maxEnvelopes(int[][] envelopes) {
        if (envelopes == null || envelopes.length == 0) {
            return 0;
        }
        Arrays.sort(envelopes, (a, b) -> a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]);
        int[] tails = new int[envelopes.length];
        int size = 0;
        for (int[] envelope : envelopes) {
            int height = envelope[1];
            int lo = 0;
            int hi = size;
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (tails[mid] < height) {
                    lo = mid + 1;
                } else {
                    hi = mid;
                }
            }
            tails[lo] = height;
            if (lo == size) {
                size++;
            }
        }
        return size;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: sorting by width removes one dimension; what remains is a longest-increasing-subsequence problem on heights. Sorting equal widths by descending height stops two envelopes of the same width from being chained.

**Input:** `[[5, 4], [6, 4], [6, 7], [2, 3]]` sorted to `[2, 3], [5, 4], [6, 7], [6, 4]`.

| Height | `tails` after the step | Length |
|--------|------------------------|--------|
| 3 | [3] | 1 |
| 4 | [3, 4] | 2 |
| 7 | [3, 4, 7] | 3 |
| 4 | [3, 4, 7] (replaces the 4) | 3 |

**Output:** `3`.
