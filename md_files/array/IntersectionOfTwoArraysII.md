# Intersection of Two Arrays II

Given two integer arrays `nums1` and `nums2`, return an array of their intersection. Each element in the result must appear as many times as it shows in both arrays, and the result may be in any order.

## Algorithm Strategy (Count One Array, Consume with the Other)

1. Count the values of the smaller array in a `HashMap`.
2. Walk the other array; whenever a value has a positive count, add it to the result and decrease its count.
3. Each value therefore appears `min(count1, count2)` times.
4. Convert the result list to an array.

## Example walkthrough

- For `[1, 2, 2, 1]` and `[2, 2]`: the counts of the first array are `{1: 2, 2: 2}`; both 2s in the second array are matched.
- Result: `[2, 2]`.

## Complexity Analysis

- **Time Complexity:** `O(m + n)` — One pass over each array.
- **Space Complexity:** `O(min(m, n))` — The map of the smaller array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Sort and Two Pointers

Sort both arrays and walk them together.

```java
public static int[] sorted(int[] a, int[] b) {
    int[] x = a.clone(), y = b.clone(); java.util.Arrays.sort(x); java.util.Arrays.sort(y); java.util.List<Integer> out = new java.util.ArrayList<>(); int i = 0, j = 0;
    while (i < x.length && j < y.length) { if (x[i] < y[j]) i++; else if (x[i] > y[j]) j++; else { out.add(x[i]); i++; j++; } }
    int[] r = new int[out.size()]; for (int k = 0; k < r.length; k++) r[k] = out.get(k); return r;
}
```

- **Time Complexity:** O(m log m + n log n)
- **Space Complexity:** O(1) extra

### Approach 2: Frequency Map (Optimized)

Count one array and consume the counts with the other. This is the version implemented in the repository.

```java
public static int[] map(int[] a, int[] b) {
    java.util.Map<Integer, Integer> c = new java.util.HashMap<>(); for (int v : a) c.merge(v, 1, Integer::sum); java.util.List<Integer> out = new java.util.ArrayList<>();
    for (int v : b) { Integer l = c.get(v); if (l != null && l > 0) { out.add(v); c.put(v, l - 1); } } int[] r = new int[out.size()]; for (int k = 0; k < r.length; k++) r[k] = out.get(k); return r;
}
```

- **Time Complexity:** O(m + n)
- **Space Complexity:** O(min(m, n))

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Sort and Two Pointers | O(m log m + n log n) | O(1) extra |
| 2 | Frequency Map (Optimized) | O(m + n) | O(min(m, n)) |

## Source Code

### IntersectionOfTwoArraysII.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntersectionOfTwoArraysII {

    public static void main(String[] args) {
        int[][] first = {{1, 2, 2, 1}, {4, 9, 5}, {1, 2}, {}};
        int[][] second = {{2, 2}, {9, 4, 9, 8, 4}, {3, 4}, {1}};
        for (int i = 0; i < first.length; i++) {
            int[] result = intersect(first[i], second[i]);
            Arrays.sort(result);
            System.out.println("Input  : nums1 = " + Arrays.toString(first[i]) + ", nums2 = " + Arrays.toString(second[i]));
            System.out.println("Output : " + Arrays.toString(result));
            System.out.println();
        }
    }

    public static int[] intersect(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null) {
            return new int[0];
        }
        if (nums1.length > nums2.length) {
            return intersect(nums2, nums1);
        }
        Map<Integer, Integer> counts = new HashMap<>();
        for (int num : nums1) {
            counts.merge(num, 1, Integer::sum);
        }
        List<Integer> result = new ArrayList<>();
        for (int num : nums2) {
            Integer left = counts.get(num);
            if (left != null && left > 0) {
                result.add(num);
                counts.put(num, left - 1);
            }
        }
        int[] answer = new int[result.size()];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = result.get(i);
        }
        return answer;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a value should appear in the result as often as it appears in both arrays, which is the smaller of its two counts. Counting one array and then spending those counts while scanning the other gives exactly that, and building the map from the smaller array keeps memory low.

**Input:** `nums1 = [1, 2, 2, 1]`, `nums2 = [2, 2]`.

| Step | Action |
|------|--------|
| smaller array | `nums2 = [2, 2]`, counts `{2: 2}` |
| scan `1` | not in the counts |
| scan `2` | count 2 > 0: add, count becomes 1 |
| scan `2` | count 1 > 0: add, count becomes 0 |
| scan `1` | not in the counts |

**Output:** `[2, 2]`.
