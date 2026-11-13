# Find K Pairs with Smallest Sums

Given two integer arrays `nums1` and `nums2` sorted in non-decreasing order and an integer `k`, return the `k` pairs `(u, v)` with the smallest sums, where `u` is from `nums1` and `v` is from `nums2`.

## Algorithm Strategy (Min-Heap Expanding Along Rows)

1. Think of all pairs as a matrix whose entry `(i, j)` is `nums1[i] + nums2[j]`. Every row and every column is sorted, so the smallest unvisited sum is always at the start of a row or just after a visited entry.
2. Seed the heap with `(i, 0)` for the first `min(k, n)` rows: row `i` pairs `nums1[i]` with the smallest element of `nums2`.
3. Pop the smallest sum `(i, j)` and add the pair to the answer. Then push `(i, j + 1)` if it exists, the next candidate in the same row.
4. Stop after `k` pairs or when the heap is empty. Only the first `k` rows can contribute, because row `k` cannot beat the pairs of rows `0..k-1` that use the same element of `nums2`.

## Example walkthrough

- For `nums1 = [1, 7, 11]`, `nums2 = [2, 4, 6]`, `k = 3`: the heap starts with sums 3, 9, 13.
- Pop `(1, 2)`, push `(1, 4)` with sum 5; pop it, push `(1, 6)` with sum 7; pop it. Result: `[[1, 2], [1, 4], [1, 6]]`.

## Complexity Analysis

- **Time Complexity:** `O(k log k)` — At most `k` pops from a heap that never holds more than `k` entries.
- **Space Complexity:** `O(k)` — The heap.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Generate All Pairs and Sort

Build every pair, sort by sum and take the first `k`.

```java
public static List<List<Integer>> kSmallestPairsBrute(int[] nums1, int[] nums2, int k) {
    List<int[]> all = new ArrayList<>();
    for (int a : nums1) for (int b : nums2) all.add(new int[]{a + b, a, b});
    all.sort((x, y) -> Integer.compare(x[0], y[0]));
    List<List<Integer>> result = new ArrayList<>();
    for (int i = 0; i < Math.min(k, all.size()); i++) result.add(Arrays.asList(all.get(i)[1], all.get(i)[2]));
    return result;
}
```

- **Time Complexity:** O(n * m * log(n * m))
- **Space Complexity:** O(n * m)

### Approach 2: Min-Heap over Rows (Optimized)

Keep one candidate per row in a heap and advance only the row that was just used, so at most `k` pairs are ever generated. This is the version implemented in the repository.

```java
public static List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
    List<List<Integer>> result = new ArrayList<>();
    if (nums1.length == 0 || nums2.length == 0 || k <= 0) {
        return result;
    }
    PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
    for (int i = 0; i < Math.min(k, nums1.length); i++) {
        heap.add(new int[]{nums1[i] + nums2[0], i, 0});
    }
    while (!heap.isEmpty() && result.size() < k) {
        int[] top = heap.poll();
        int i = top[1];
        int j = top[2];
        result.add(Arrays.asList(nums1[i], nums2[j]));
        if (j + 1 < nums2.length) {
            heap.add(new int[]{nums1[i] + nums2[j + 1], i, j + 1});
        }
    }
    return result;
}
```

- **Time Complexity:** O(k log k)
- **Space Complexity:** O(k)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Generate All Pairs and Sort | O(n * m * log(n * m)) | O(n * m) |
| 2 | Min-Heap over Rows (Optimized) | O(k log k) | O(k) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### FindKPairsWithSmallestSums.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class FindKPairsWithSmallestSums {

    public static void main(String[] args) {
        int[][][] inputs = {{{1, 7, 11}, {2, 4, 6}}, {{1, 1, 2}, {1, 2, 3}}, {{1, 2}, {3}}};
        int[] ks = {3, 2, 3};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : nums1 = " + Arrays.toString(inputs[i][0]) + ", nums2 = " + Arrays.toString(inputs[i][1]) + ", k = " + ks[i]);
            System.out.println("Output : " + kSmallestPairs(inputs[i][0], inputs[i][1], ks[i]));
            System.out.println();
        }
    }

    public static List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums1.length == 0 || nums2.length == 0 || k <= 0) {
            return result;
        }
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        for (int i = 0; i < Math.min(k, nums1.length); i++) {
            heap.add(new int[]{nums1[i] + nums2[0], i, 0});
        }
        while (!heap.isEmpty() && result.size() < k) {
            int[] top = heap.poll();
            int i = top[1];
            int j = top[2];
            result.add(Arrays.asList(nums1[i], nums2[j]));
            if (j + 1 < nums2.length) {
                heap.add(new int[]{nums1[i] + nums2[j + 1], i, j + 1});
            }
        }
        return result;
    }
}
```
