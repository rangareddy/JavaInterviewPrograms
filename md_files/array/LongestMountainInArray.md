# Longest Mountain in Array

A mountain subarray has at least three elements, strictly increases to a peak and then strictly decreases. Given an integer array `arr`, return the length of the longest mountain subarray, or `0` if there is none.

## Algorithm Strategy (Single Pass over Peaks)

1. Scan with an index `i`. A peak is an index with `arr[i - 1] < arr[i] > arr[i + 1]`.
2. For each peak, extend `left` while the values keep decreasing to the left and `right` while they keep decreasing to the right.
3. The mountain length is `right - left + 1`; record the best.
4. Continue the scan from `right + 1` so that each element is visited a constant number of times.

## Example walkthrough

- For `[2, 1, 4, 7, 3, 2, 5]`: the peak 7 has `1, 4, 7, 3, 2` around it.
- Result: `5`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each element is part of at most one expansion.
- **Space Complexity:** `O(1)` — A few indexes.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Check Every Subarray

Test every subarray for the mountain shape.

```java
public static int brute(int[] a) { int best = 0; for (int i = 0; i < a.length; i++) for (int j = i + 2; j < a.length; j++) { int k = i; while (k + 1 <= j && a[k] < a[k + 1]) k++; if (k == i || k == j) continue; while (k + 1 <= j && a[k] > a[k + 1]) k++; if (k == j) best = Math.max(best, j - i + 1); } return best; }
```

- **Time Complexity:** O(n^3)
- **Space Complexity:** O(1)

### Approach 2: Up and Down Length Arrays

Precompute how far the ascent reaches left and the descent reaches right of each index.

```java
public static int arrays(int[] a) { int n = a.length; int[] up = new int[n], down = new int[n]; for (int i = 1; i < n; i++) if (a[i] > a[i - 1]) up[i] = up[i - 1] + 1; for (int i = n - 2; i >= 0; i--) if (a[i] > a[i + 1]) down[i] = down[i + 1] + 1; int best = 0; for (int i = 0; i < n; i++) if (up[i] > 0 && down[i] > 0) best = Math.max(best, up[i] + down[i] + 1); return best; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Expand Around Each Peak (Optimized)

Walk out from every peak and skip past the mountain. This is the version implemented in the repository.

```java
public static int expand(int[] a) { int best = 0, i = 1; while (i < a.length - 1) { if (a[i - 1] < a[i] && a[i] > a[i + 1]) { int l = i - 1; while (l > 0 && a[l - 1] < a[l]) l--; int r = i + 1; while (r < a.length - 1 && a[r] > a[r + 1]) r++; best = Math.max(best, r - l + 1); i = r + 1; } else i++; } return best; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Check Every Subarray | O(n^3) | O(1) |
| 2 | Up and Down Length Arrays | O(n) | O(n) |
| 3 | Expand Around Each Peak (Optimized) | O(n) | O(1) |

## Source Code

### LongestMountainInArray.java

```java
import java.util.Arrays;

public class LongestMountainInArray {

    public static void main(String[] args) {
        int[][] inputs = {{2, 1, 4, 7, 3, 2, 5}, {2, 2, 2}, {0, 1, 2, 3, 4, 5, 4, 3, 2, 1, 0}, {1, 2}, {}};
        for (int[] arr : inputs) {
            System.out.println("Input  : arr = " + Arrays.toString(arr));
            System.out.println("Output : " + longestMountain(arr));
            System.out.println();
        }
    }

    public static int longestMountain(int[] arr) {
        if (arr == null || arr.length < 3) {
            return 0;
        }
        int best = 0;
        int i = 1;
        while (i < arr.length - 1) {
            if (arr[i - 1] < arr[i] && arr[i] > arr[i + 1]) {
                int left = i - 1;
                while (left > 0 && arr[left - 1] < arr[left]) {
                    left--;
                }
                int right = i + 1;
                while (right < arr.length - 1 && arr[right] > arr[right + 1]) {
                    right++;
                }
                best = Math.max(best, right - left + 1);
                i = right + 1;
            } else {
                i++;
            }
        }
        return best;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: a mountain is fully determined by its peak, and two mountains cannot share a slope in a way that matters, because the next mountain can only start at the bottom of the previous one. So find a peak, extend down both sides, record the length and jump past the right side.

**Input:** `arr = [2, 1, 4, 7, 3, 2, 5]`.

| Peak | Left foot | Right foot | Length |
|------|-----------|------------|--------|
| 7 (index 3) | 1 (index 1) | 2 (index 5) | 5 |

The jump to index 6 finds no other peak.

**Output:** `5`.
