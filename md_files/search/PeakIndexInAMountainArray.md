# Peak Index in a Mountain Array

An array `arr` is a mountain if it strictly increases to a peak and then strictly decreases. Given a mountain array, return the index `i` of the peak, in `O(log n)` time.

## Algorithm Strategy (Binary Search on Whether the Slope Is Rising)

1. Search between `lo = 0` and `hi = n - 1`.
2. At `mid`, compare `arr[mid]` with `arr[mid + 1]`.
3. If `arr[mid] < arr[mid + 1]` the peak lies to the right: `lo = mid + 1`; otherwise it is at `mid` or to the left: `hi = mid`.
4. When `lo == hi`, that index is the peak.

## Example walkthrough

- For `[0, 10, 5, 2]`: `mid = 1`, `arr[1] = 10 > arr[2] = 5`, so `hi = 1`; then `mid = 0`, `0 < 10`, so `lo = 1`.
- Result: `1`.

## Complexity Analysis

- **Time Complexity:** `O(log n)` — The range halves each step.
- **Space Complexity:** `O(1)` — Two indexes.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Linear Scan

Walk until the next element is smaller.

```java
public static int linear(int[] a) { int i = 0; while (i + 1 < a.length && a[i] < a[i + 1]) i++; return i; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 2: Binary Search on the Slope (Optimized)

Move toward the rising side. This is the version implemented in the repository.

```java
public static int binary(int[] a) { int lo = 0, hi = a.length - 1; while (lo < hi) { int m = (lo + hi) / 2; if (a[m] < a[m + 1]) lo = m + 1; else hi = m; } return lo; }
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Linear Scan | O(n) | O(1) |
| 2 | Binary Search on the Slope (Optimized) | O(log n) | O(1) |

## Source Code

### PeakIndexInAMountainArray.java

```java
import java.util.Arrays;

public class PeakIndexInAMountainArray {

    public static void main(String[] args) {
        int[][] inputs = {{0, 10, 5, 2}, {0, 1, 0}, {3, 4, 5, 1}, {24, 69, 100, 99, 79, 78, 67, 36, 26, 19}, {1, 3, 2}};
        for (int[] arr : inputs) {
            System.out.println("Input  : arr = " + Arrays.toString(arr));
            System.out.println("Output : " + peakIndexInMountainArray(arr));
            System.out.println();
        }
    }

    public static int peakIndexInMountainArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int lo = 0;
        int hi = arr.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (arr[mid] < arr[mid + 1]) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: at any position, comparing a value with its right neighbour tells which side of the peak it is on. If the array is still rising, the peak is further right; if it is falling, the current index could be the peak or the peak is to the left. That is a monotone yes/no test, which binary search can use.

**Input:** `arr = [0, 10, 5, 2]`.

| `lo` | `hi` | `mid` | Compare | Move |
|------|------|-------|---------|------|
| 0 | 3 | 1 | 10 > 5, falling | `hi = 1` |
| 0 | 1 | 0 | 0 < 10, rising | `lo = 1` |
| 1 | 1 | | done | |

**Output:** `1`.
