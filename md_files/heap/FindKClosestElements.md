# Find K Closest Elements

Given a sorted integer array `arr`, two integers `k` and `x`, return the `k` closest integers to `x` in the array, sorted in ascending order. An integer `a` is closer than `b` if `|a - x| < |b - x|`, or the distances are equal and `a < b`.

## Algorithm Strategy (Binary Search for the Window Start)

1. The answer is a window of `k` consecutive elements, so only its start index `left` in `[0, n - k]` needs to be found.
2. Binary search `left`: compare `x - arr[mid]` with `arr[mid + k] - x`.
3. If `x - arr[mid] > arr[mid + k] - x`, the window should move right: `lo = mid + 1`; otherwise `hi = mid`.
4. Return `arr[lo .. lo + k - 1]`.

## Example walkthrough

- For `[1, 2, 3, 4, 5]`, `k = 4`, `x = 3`: the window starting at 0 (`1..4`) is as close as the one starting at 1, and ties favour the smaller values.
- Result: `[1, 2, 3, 4]`.

## Complexity Analysis

- **Time Complexity:** `O(log(n - k) + k)` — The search plus copying the window.
- **Space Complexity:** `O(1)` — Apart from the output.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Sort by Distance

Sort a copy by distance to `x` (ties by value), take `k` and sort them.

```java
public static java.util.List<Integer> sortByDistance(int[] a, int k, int x) {
    java.util.List<Integer> l = new java.util.ArrayList<>(); for (int v : a) l.add(v);
    l.sort((p, q) -> Math.abs(p - x) != Math.abs(q - x) ? Math.abs(p - x) - Math.abs(q - x) : p - q);
    java.util.List<Integer> out = new java.util.ArrayList<>(l.subList(0, k)); java.util.Collections.sort(out); return out;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 2: Shrink a Two-Pointer Window

Start with the whole array and drop the farther end until `k` remain.

```java
public static java.util.List<Integer> shrink(int[] a, int k, int x) {
    int l = 0, r = a.length - 1;
    while (r - l + 1 > k) { if (x - a[l] <= a[r] - x) r--; else l++; }
    java.util.List<Integer> out = new java.util.ArrayList<>(); for (int i = l; i <= r; i++) out.add(a[i]); return out;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 3: Binary Search for the Window Start (Optimized)

Binary search the left edge of the window. This is the version implemented in the repository.

```java
public static java.util.List<Integer> binary(int[] a, int k, int x) {
    int lo = 0, hi = a.length - k;
    while (lo < hi) { int mid = lo + (hi - lo) / 2; if (x - a[mid] > a[mid + k] - x) lo = mid + 1; else hi = mid; }
    java.util.List<Integer> out = new java.util.ArrayList<>(); for (int i = lo; i < lo + k; i++) out.add(a[i]); return out;
}
```

- **Time Complexity:** O(log(n - k) + k)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Sort by Distance | O(n log n) | O(n) |
| 2 | Shrink a Two-Pointer Window | O(n) | O(1) |
| 3 | Binary Search for the Window Start (Optimized) | O(log(n - k) + k) | O(1) |

## Source Code

### FindKClosestElements.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindKClosestElements {

    public static void main(String[] args) {
        int[][] arrs = {{1, 2, 3, 4, 5}, {1, 1, 2, 3, 4, 5}, {1, 3}, {1, 2, 3, 4, 5}};
        int[] ks = {4, 4, 1, 2};
        int[] xs = {3, -1, 2, 6};
        for (int i = 0; i < arrs.length; i++) {
            System.out.println("Input  : arr = " + Arrays.toString(arrs[i]) + ", k = " + ks[i] + ", x = " + xs[i]);
            System.out.println("Output : " + findClosestElements(arrs[i], ks[i], xs[i]));
            System.out.println();
        }
    }

    public static List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> result = new ArrayList<>();
        if (arr == null || k <= 0 || k > arr.length) {
            return result;
        }
        int lo = 0;
        int hi = arr.length - k;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (x - arr[mid] > arr[mid + k] - x) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        for (int i = lo; i < lo + k; i++) {
            result.add(arr[i]);
        }
        return result;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: the `k` closest values in a sorted array always form a contiguous window, so the problem reduces to finding the window's start. Comparing the two elements at the window's edges tells which direction to move.

**Input:** `arr = [1, 2, 3, 4, 5]`, `k = 4`, `x = 3`. The start lies in `[0, 1]`.

| `lo` | `hi` | `mid` | `x - arr[mid]` | `arr[mid+k] - x` | Decision |
|------|------|-------|----------------|-------------------|----------|
| 0 | 1 | 0 | 2 | 2 | not greater, `hi = 0` |

`lo == hi == 0`, so the window is `arr[0..3]`.

**Output:** `[1, 2, 3, 4]`.
