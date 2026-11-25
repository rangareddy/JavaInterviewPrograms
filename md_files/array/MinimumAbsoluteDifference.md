# Minimum Absolute Difference

Given an array of distinct integers `arr`, find all pairs of elements with the minimum absolute difference of any two elements. Return the pairs in ascending order, each pair `[a, b]` with `a < b`.

## Algorithm Strategy (Sort and Scan Neighbours)

1. After sorting, the smallest difference is always between two neighbouring values.
2. Scan the neighbours once keeping the smallest difference `min` seen so far and the pairs that reach it.
3. If a smaller difference is found, clear the list and start again with that pair.
4. If it equals `min`, add the pair.

## Example walkthrough

- For `[4, 2, 1, 3]` the sorted array is `[1, 2, 3, 4]` and every neighbour pair differs by 1.
- Result: `[[1, 2], [2, 3], [3, 4]]`.

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — Dominated by the sort.
- **Space Complexity:** `O(n)` — The output (plus the sort).

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force over All Pairs

Compare every pair, keep the smallest difference and collect the pairs, then sort the pairs.

```java
public static java.util.List<java.util.List<Integer>> allPairs(int[] a) {
    int min = Integer.MAX_VALUE;
    for (int i = 0; i < a.length; i++) for (int j = i + 1; j < a.length; j++) min = Math.min(min, Math.abs(a[i] - a[j]));
    java.util.List<java.util.List<Integer>> out = new java.util.ArrayList<>();
    for (int i = 0; i < a.length; i++) for (int j = 0; j < a.length; j++) if (a[j] - a[i] == min) out.add(java.util.Arrays.asList(a[i], a[j]));
    out.sort((p, q) -> p.get(0) - q.get(0));
    return out;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 2: Sort and Scan Neighbours (Optimized)

Only adjacent values in sorted order can have the smallest difference. This is the version implemented in the repository.

```java
public static java.util.List<java.util.List<Integer>> sortScan(int[] a) {
    int[] s = a.clone(); java.util.Arrays.sort(s);
    java.util.List<java.util.List<Integer>> out = new java.util.ArrayList<>(); int min = Integer.MAX_VALUE;
    for (int i = 1; i < s.length; i++) { int d = s[i] - s[i - 1]; if (d < min) { min = d; out.clear(); } if (d == min) out.add(java.util.Arrays.asList(s[i - 1], s[i])); }
    return out;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force over All Pairs | O(n^2) | O(n) |
| 2 | Sort and Scan Neighbours (Optimized) | O(n log n) | O(n) |

## Source Code

### MinimumAbsoluteDifference.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinimumAbsoluteDifference {

    public static void main(String[] args) {
        int[][] inputs = {{4, 2, 1, 3}, {1, 3, 6, 10, 15}, {3, 8, -10, 23, 19, -4, -14, 27}};
        for (int[] arr : inputs) {
            System.out.println("Input  : arr = " + Arrays.toString(arr));
            System.out.println("Output : " + minimumAbsDifference(arr.clone()));
            System.out.println();
        }
    }

    public static List<List<Integer>> minimumAbsDifference(int[] arr) {
        List<List<Integer>> result = new ArrayList<>();
        if (arr == null || arr.length < 2) {
            return result;
        }
        Arrays.sort(arr);
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < arr.length; i++) {
            int diff = arr[i] - arr[i - 1];
            if (diff < min) {
                min = diff;
                result.clear();
            }
            if (diff == min) {
                result.add(Arrays.asList(arr[i - 1], arr[i]));
            }
        }
        return result;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: once sorted, the closest two values for any element are its neighbours, so only n - 1 differences need to be checked.

**Input:** `arr = [3, 8, -10, 23, 19, -4, -14, 27]`.

| Sorted neighbours | Difference | Minimum | Pairs |
|-------------------|------------|---------|-------|
| -14, -10 | 4 | 4 | [-14, -10] |
| -10, -4 | 6 | 4 | |
| -4, 3 | 7 | 4 | |
| 3, 8 | 5 | 4 | |
| 8, 19 | 11 | 4 | |
| 19, 23 | 4 | 4 | [-14, -10], [19, 23] |
| 23, 27 | 4 | 4 | [-14, -10], [19, 23], [23, 27] |

**Output:** `[[-14, -10], [19, 23], [23, 27]]`.
