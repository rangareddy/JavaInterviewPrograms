# Kth Missing Positive Number

Given a strictly increasing array `arr` of positive integers and an integer `k`, return the `k`-th positive integer that is missing from the array.

## Algorithm Strategy (Binary Search on How Many Numbers Are Missing Before Each Index)

1. At index `i` the number of missing positives before `arr[i]` is `arr[i] - (i + 1)`.
2. Binary search for the first index `lo` where this missing count is at least `k`.
3. The `k`-th missing number then lies before `arr[lo]`, and its value is `k + lo` (there are `lo` array elements below it).
4. If no such index exists, the answer is `k + arr.length`.

## Example walkthrough

- For `[2, 3, 4, 7, 11]` and `k = 5`: missing counts are 1, 1, 1, 3, 6. The first index with at least 5 missing is 4.
- Result: `5 + 4 = 9`.

## Complexity Analysis

- **Time Complexity:** `O(log n)` — Binary search.
- **Space Complexity:** `O(1)` — Two indexes.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Linear Scan

Count the missing numbers one by one.

```java
public static int linear(int[] a, int k) { int cur = 1, i = 0; while (true) { if (i < a.length && a[i] == cur) i++; else if (--k == 0) return cur; cur++; } }
```

- **Time Complexity:** O(n + k)
- **Space Complexity:** O(1)

### Approach 2: Binary Search on Missing Counts (Optimized)

Find the first index with at least `k` missing numbers before it. This is the version implemented in the repository.

```java
public static int binary(int[] a, int k) { int lo = 0, hi = a.length; while (lo < hi) { int m = (lo + hi) / 2; if (a[m] - (m + 1) >= k) hi = m; else lo = m + 1; } return lo + k; }
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Linear Scan | O(n + k) | O(1) |
| 2 | Binary Search on Missing Counts (Optimized) | O(log n) | O(1) |

## Source Code

### KthMissingPositiveNumber.java

```java
import java.util.Arrays;

public class KthMissingPositiveNumber {

    public static void main(String[] args) {
        int[][] arrs = {{2, 3, 4, 7, 11}, {1, 2, 3, 4}, {}, {5}};
        int[] ks = {5, 2, 3, 1};
        for (int i = 0; i < arrs.length; i++) {
            System.out.println("Input  : arr = " + Arrays.toString(arrs[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + findKthPositive(arrs[i], ks[i]));
            System.out.println();
        }
    }

    public static int findKthPositive(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return k;
        }
        int lo = 0;
        int hi = arr.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (arr[mid] - (mid + 1) >= k) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo + k;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: if nothing were missing, `arr[i]` would equal `i + 1`. So `arr[i] - (i + 1)` is how many positives are missing before it, and that count never decreases along the array, which makes it binary searchable. The `k`-th missing number sits just before the first element with at least `k` missing, and all `lo` elements below it shift its value by `lo`.

**Input:** `arr = [2, 3, 4, 7, 11]`, `k = 5`.

| Index | `arr[i]` | Missing before it |
|-------|----------|-------------------|
| 0 | 2 | 1 |
| 1 | 3 | 1 |
| 2 | 4 | 1 |
| 3 | 7 | 3 |
| 4 | 11 | 6 |

The first index with at least 5 missing is 4, so the answer is `4 + 5`.

**Output:** `9`.
