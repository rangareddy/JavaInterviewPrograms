# Degree of an Array

The degree of an array is the highest frequency of any value in it. Given a non-empty array `nums` of non-negative integers, return the length of the smallest contiguous subarray that has the same degree as `nums`.

## Algorithm Strategy (Frequency, First and Last Index)

1. For each value record its count, the index where it first appears, and the index where it last appears.
2. The degree is the maximum count.
3. For every value whose count equals the degree, the shortest subarray covering all of its occurrences is `last - first + 1`.
4. Return the minimum of those lengths.

## Example walkthrough

- For `[1, 2, 2, 3, 1]`: the degree is 2 (values 1 and 2). Value 1 spans 5 positions, value 2 spans 2.
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass plus one pass over the distinct values.
- **Space Complexity:** `O(n)` — Three maps.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

Try every subarray and compare its degree with the array's degree.

```java
public static int bruteForce(int[] a) {
    int deg = degree(a, 0, a.length - 1), best = a.length;
    for (int i = 0; i < a.length; i++) for (int j = i; j < a.length; j++) if (degree(a, i, j) == deg) best = Math.min(best, j - i + 1);
    return best;
}
private static int degree(int[] a, int lo, int hi) {
    java.util.Map<Integer, Integer> m = new java.util.HashMap<>(); int d = 0;
    for (int i = lo; i <= hi; i++) d = Math.max(d, m.merge(a[i], 1, Integer::sum));
    return d;
}
```

- **Time Complexity:** O(n^3)
- **Space Complexity:** O(n)

### Approach 2: One Pass with First Index (Optimized)

Track the count and first index while scanning, updating the best length as the degree grows. This is the version implemented in the repository.

```java
public static int onePass(int[] a) {
    java.util.Map<Integer, Integer> cnt = new java.util.HashMap<>(), first = new java.util.HashMap<>();
    int degree = 0, best = a.length;
    for (int i = 0; i < a.length; i++) {
        first.putIfAbsent(a[i], i); int c = cnt.merge(a[i], 1, Integer::sum); int len = i - first.get(a[i]) + 1;
        if (c > degree) { degree = c; best = len; } else if (c == degree) best = Math.min(best, len);
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^3) | O(n) |
| 2 | One Pass with First Index (Optimized) | O(n) | O(n) |

## Source Code

### DegreeOfAnArray.java

```java
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DegreeOfAnArray {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 2, 3, 1}, {1, 2, 2, 3, 1, 4, 2}, {7}, {1, 1, 1}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + findShortestSubArray(nums));
            System.out.println();
        }
    }

    public static int findShortestSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Map<Integer, Integer> count = new HashMap<>();
        Map<Integer, Integer> first = new HashMap<>();
        int degree = 0;
        int best = nums.length;
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            first.putIfAbsent(x, i);
            int c = count.merge(x, 1, Integer::sum);
            int length = i - first.get(x) + 1;
            if (c > degree) {
                degree = c;
                best = length;
            } else if (c == degree) {
                best = Math.min(best, length);
            }
        }
        return best;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: only values that reach the highest count can define the answer, and for them the shortest cover runs from the first to the last occurrence.

**Input:** `nums = [1, 2, 2, 3, 1]`.

| Index | Value | Count | First index | Length so far | Degree | Best |
|-------|-------|-------|-------------|---------------|--------|------|
| 0 | 1 | 1 | 0 | 1 | 1 | 1 |
| 1 | 2 | 1 | 1 | 1 | 1 | 1 |
| 2 | 2 | 2 | 1 | 2 | 2 | 2 |
| 3 | 3 | 1 | 3 | 1 | 2 | 2 |
| 4 | 1 | 2 | 0 | 5 | 2 | 2 |

**Output:** `2`.
