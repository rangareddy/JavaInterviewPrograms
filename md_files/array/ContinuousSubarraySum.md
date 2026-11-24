# Continuous Subarray Sum

Given an integer array `nums` and an integer `k`, return `true` if `nums` has a subarray of length at least 2 whose elements sum to a multiple of `k`, and `false` otherwise. A multiple of `k` is `n * k` for any integer `n`, including 0.

## Algorithm Strategy (Prefix Sum Remainders in a HashMap)

1. If two prefix sums have the same remainder modulo `k`, the elements between them add up to a multiple of `k`.
2. Walk the array keeping `sum % k` and a map from each remainder to the first index where it was seen (the remainder 0 at index -1).
3. If the current remainder was seen at index `j` and `i - j >= 2`, the subarray `(j, i]` qualifies: return `true`.
4. Otherwise, store the remainder if it is new. Return `false` at the end.

## Example walkthrough

- For `[23, 2, 4, 6, 7]` and `k = 6`: the prefix remainders are 5, 1, 5, ...; the remainder 5 repeats at index 2, two positions after index 0.
- The subarray `[2, 4]` sums to 6. Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(min(n, k))` — The remainder map.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

Sum every subarray of length at least 2.

```java
public static boolean bruteForce(int[] a, int k) {
    for (int i = 0; i < a.length; i++) { int s = a[i]; for (int j = i + 1; j < a.length; j++) { s += a[j]; if (k == 0 ? s == 0 : s % k == 0) return true; } }
    return false;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Prefix Sums with Remainders (Optimized)

Equal remainders of prefix sums mean the part between them is a multiple of `k`. This is the version implemented in the repository.

```java
public static boolean remainders(int[] a, int k) {
    java.util.Map<Integer, Integer> m = new java.util.HashMap<>(); m.put(0, -1); int s = 0;
    for (int i = 0; i < a.length; i++) { s += a[i]; int rem = k == 0 ? s : ((s % k) + k) % k; Integer seen = m.get(rem); if (seen == null) m.put(rem, i); else if (i - seen >= 2) return true; }
    return false;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(min(n, k))

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) |
| 2 | Prefix Sums with Remainders (Optimized) | O(n) | O(min(n, k)) |

## Source Code

### ContinuousSubarraySum.java

```java
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ContinuousSubarraySum {

    public static void main(String[] args) {
        int[][] nums = {{23, 2, 4, 6, 7}, {23, 2, 6, 4, 7}, {23, 2, 6, 4, 7}, {0, 0}, {1, 2}};
        int[] ks = {6, 6, 13, 7, 3};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + checkSubarraySum(nums[i], ks[i]));
            System.out.println();
        }
    }

    public static boolean checkSubarraySum(int[] nums, int k) {
        if (nums == null || nums.length < 2) {
            return false;
        }
        Map<Integer, Integer> firstIndex = new HashMap<>();
        firstIndex.put(0, -1);
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            int remainder = k == 0 ? sum : ((sum % k) + k) % k;
            Integer seen = firstIndex.get(remainder);
            if (seen == null) {
                firstIndex.put(remainder, i);
            } else if (i - seen >= 2) {
                return true;
            }
        }
        return false;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: if the prefix sums before index `j` and up to index `i` leave the same remainder modulo `k`, their difference, the sum of the elements in between, is a multiple of `k`. The earliest index of each remainder gives the longest candidate.

**Input:** `nums = [23, 2, 4, 6, 7]`, `k = 6`.

| Index | Value | Prefix sum | Remainder | Seen at | Action |
|-------|-------|-----------|-----------|---------|--------|
| 0 | 23 | 23 | 5 | new | store 5 -> 0 |
| 1 | 2 | 25 | 1 | new | store 1 -> 1 |
| 2 | 4 | 29 | 5 | 0 | length 2, found |

**Output:** `true`.
