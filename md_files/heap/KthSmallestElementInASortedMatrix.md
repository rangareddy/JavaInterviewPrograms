# Kth Smallest Element in a Sorted Matrix

Given an `n x n` matrix where each row and each column is sorted in ascending order, return the `k`-th smallest element in the matrix (the `k`-th smallest in sorted order, not the `k`-th distinct one).

## Algorithm Strategy (Binary Search on the Value Range)

1. The answer lies between `matrix[0][0]` and `matrix[n-1][n-1]`.
2. For a candidate `mid`, count how many elements are `<= mid` by walking from the bottom-left corner: move up when the cell is too large, right otherwise.
3. If the count is at least `k`, the answer is `<= mid`: `hi = mid`; otherwise `lo = mid + 1`.
4. When `lo == hi`, that value is the `k`-th smallest.

## Example walkthrough

- For `[[1,5,9],[10,11,13],[12,13,15]]` and `k = 8`: the sorted values are 1, 5, 9, 10, 11, 12, 13, 13, 15.
- The 8th smallest is `13`.

## Complexity Analysis

- **Time Complexity:** `O(n log(max - min))` — A binary search over the value range, with an O(n) count per step.
- **Space Complexity:** `O(1)` — Only counters.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Flatten and Sort

Put every element in an array and sort it.

```java
public static int flattenSort(int[][] m, int k) {
    int n = m.length; int[] a = new int[n * n]; int i = 0; for (int[] row : m) for (int x : row) a[i++] = x; java.util.Arrays.sort(a); return a[k - 1];
}
```

- **Time Complexity:** O(n^2 log n)
- **Space Complexity:** O(n^2)

### Approach 2: Min-Heap of Row Heads

Pop the smallest of the current row heads `k` times, pushing the next element of that row.

```java
public static int heap(int[][] m, int k) {
    int n = m.length; java.util.PriorityQueue<int[]> pq = new java.util.PriorityQueue<>((x, y) -> x[0] - y[0]);
    for (int r = 0; r < Math.min(n, k); r++) pq.add(new int[]{m[r][0], r, 0});
    int v = 0; for (int i = 0; i < k; i++) { int[] c = pq.poll(); v = c[0]; if (c[2] + 1 < n) pq.add(new int[]{m[c[1]][c[2] + 1], c[1], c[2] + 1}); }
    return v;
}
```

- **Time Complexity:** O(k log n)
- **Space Complexity:** O(n)

### Approach 3: Binary Search on the Value (Optimized)

Binary search the value and count the elements at most `mid` by a staircase walk. This is the version implemented in the repository.

```java
public static int valueSearch(int[][] m, int k) {
    int n = m.length, lo = m[0][0], hi = m[n - 1][n - 1];
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2, row = n - 1, col = 0, cnt = 0;
        while (row >= 0 && col < n) { if (m[row][col] <= mid) { cnt += row + 1; col++; } else row--; }
        if (cnt >= k) hi = mid; else lo = mid + 1;
    }
    return lo;
}
```

- **Time Complexity:** O(n log(max - min))
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Flatten and Sort | O(n^2 log n) | O(n^2) |
| 2 | Min-Heap of Row Heads | O(k log n) | O(n) |
| 3 | Binary Search on the Value (Optimized) | O(n log(max - min)) | O(1) |

## Source Code

### KthSmallestElementInASortedMatrix.java

```java
import java.util.Arrays;

public class KthSmallestElementInASortedMatrix {

    public static void main(String[] args) {
        int[][][] matrices = {{{1, 5, 9}, {10, 11, 13}, {12, 13, 15}}, {{-5}}, {{1, 2}, {1, 3}}};
        int[] ks = {8, 1, 2};
        for (int i = 0; i < matrices.length; i++) {
            System.out.println("Input  : matrix = " + Arrays.deepToString(matrices[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + kthSmallest(matrices[i], ks[i]));
            System.out.println();
        }
    }

    public static int kthSmallest(int[][] matrix, int k) {
        if (matrix == null || matrix.length == 0) {
            return -1;
        }
        int n = matrix.length;
        int lo = matrix[0][0];
        int hi = matrix[n - 1][n - 1];
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (countAtMost(matrix, mid) >= k) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    private static int countAtMost(int[][] matrix, int value) {
        int n = matrix.length;
        int row = n - 1;
        int col = 0;
        int count = 0;
        while (row >= 0 && col < n) {
            if (matrix[row][col] <= value) {
                count += row + 1;
                col++;
            } else {
                row--;
            }
        }
        return count;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: rather than searching positions, search values. For a guess `mid`, the staircase walk counts the elements at most `mid` in O(n) because rows and columns are sorted.

**Input:** `matrix = [[1, 5, 9], [10, 11, 13], [12, 13, 15]]`, `k = 8`.

| `lo` | `hi` | `mid` | Count of elements `<= mid` | Decision |
|------|------|-------|-----------------------------|----------|
| 1 | 15 | 8 | 2 | too few, `lo = 9` |
| 9 | 15 | 12 | 6 | too few, `lo = 13` |
| 13 | 15 | 14 | 8 | enough, `hi = 14` |
| 13 | 14 | 13 | 8 | enough, `hi = 13` |

`lo == hi == 13`.

**Output:** `13`.
