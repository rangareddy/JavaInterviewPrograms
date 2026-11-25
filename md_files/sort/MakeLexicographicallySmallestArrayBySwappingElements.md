# Make Lexicographically Smallest Array by Swapping Elements

Given a 0-indexed array of positive integers `nums` and a positive integer `limit`, you may swap `nums[i]` and `nums[j]` any number of times as long as `|nums[i] - nums[j]| <= limit`. Return the lexicographically smallest array you can get.

## Algorithm Strategy (Group by Value Chains, Refill in Index Order)

1. Sort the indexes by value. Walk the sorted values and start a new group whenever the gap to the previous value exceeds `limit` (values within a group can reach one another through chains of swaps).
2. For each group, collect its values (already sorted) and the original indexes of those values.
3. Sort the indexes of the group and assign the sorted values to them in order.
4. Smaller values go to smaller indexes inside each group, which is the best possible.

## Example walkthrough

- For `[1, 5, 3, 9, 8]` and `limit = 2`: sorted values 1, 3, 5, 8, 9 form two groups (1, 3, 5) and (8, 9).
- Result: `[1, 3, 5, 8, 9]`.

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — Sorting.
- **Space Complexity:** `O(n)` — The index array and groups.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Repeated Swapping (Bubble)

Keep swapping any out-of-order pair whose values are within `limit`, until no swap helps.

```java
public static int[] bubble(int[] in, int limit) {
    int[] a = in.clone(); boolean changed = true;
    while (changed) { changed = false; for (int i = 0; i < a.length; i++) for (int j = i + 1; j < a.length; j++) if (a[i] > a[j] && a[i] - a[j] <= limit) { int t = a[i]; a[i] = a[j]; a[j] = t; changed = true; } }
    return a;
}
```

- **Time Complexity:** O(n^3)
- **Space Complexity:** O(1)

### Approach 2: Group by Value Chains and Refill by Index (Optimized)

Sort values, cut into groups at gaps above `limit` and refill each group's positions in order. This is the version implemented in the repository.

```java
public static int[] groups(int[] nums, int limit) {
    int n = nums.length; Integer[] o = new Integer[n]; for (int i = 0; i < n; i++) o[i] = i; java.util.Arrays.sort(o, (a, b) -> nums[a] - nums[b]); int[] r = new int[n]; int s = 0;
    while (s < n) { int e = s + 1; while (e < n && nums[o[e]] - nums[o[e - 1]] <= limit) e++; java.util.List<Integer> idx = new java.util.ArrayList<>(); for (int k = s; k < e; k++) idx.add(o[k]); java.util.Collections.sort(idx); for (int k = 0; k < idx.size(); k++) r[idx.get(k)] = nums[o[s + k]]; s = e; }
    return r;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Repeated Swapping (Bubble) | O(n^3) | O(1) |
| 2 | Group by Value Chains and Refill by Index (Optimized) | O(n log n) | O(n) |

## Source Code

### MakeLexicographicallySmallestArrayBySwappingElements.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MakeLexicographicallySmallestArrayBySwappingElements {

    public static void main(String[] args) {
        int[][] nums = {{1, 5, 3, 9, 8}, {1, 7, 6, 18, 2, 1}, {1, 7, 28, 19, 10}, {5}};
        int[] limits = {2, 3, 3, 1};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", limit = " + limits[i]);
            System.out.println("Output : " + Arrays.toString(lexicographicallySmallestArray(nums[i].clone(), limits[i])));
            System.out.println();
        }
    }

    public static int[] lexicographicallySmallestArray(int[] nums, int limit) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }
        int n = nums.length;
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) {
            order[i] = i;
        }
        Arrays.sort(order, (a, b) -> nums[a] - nums[b]);
        int[] result = new int[n];
        int start = 0;
        while (start < n) {
            int end = start + 1;
            while (end < n && nums[order[end]] - nums[order[end - 1]] <= limit) {
                end++;
            }
            List<Integer> indexes = new ArrayList<>();
            for (int k = start; k < end; k++) {
                indexes.add(order[k]);
            }
            java.util.Collections.sort(indexes);
            for (int k = 0; k < indexes.size(); k++) {
                result[indexes.get(k)] = nums[order[start + k]];
            }
            start = end;
        }
        return result;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: two values can be exchanged directly or through a chain if consecutive sorted values are within `limit`, so the sorted values split into independent groups. Inside a group any arrangement is reachable, and the best one puts the smallest values at the earliest positions.

**Input:** `nums = [1, 5, 3, 9, 8]`, `limit = 2`.

| Group (sorted values) | Original indexes | Sorted indexes | Refilled |
|-----------------------|------------------|----------------|----------|
| 1, 3, 5 | 0, 2, 1 | 0, 1, 2 | positions 0, 1, 2 get 1, 3, 5 |
| 8, 9 | 4, 3 | 3, 4 | positions 3, 4 get 8, 9 |

**Output:** `[1, 3, 5, 8, 9]`.
