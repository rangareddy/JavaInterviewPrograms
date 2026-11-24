# Contiguous Array

Given a binary array `nums`, return the maximum length of a contiguous subarray with an equal number of `0` and `1`.

## Algorithm Strategy (Balance Prefix with First Occurrence)

1. Treat each `0` as -1 and each `1` as +1, and keep a running `balance`.
2. A subarray with equal zeros and ones has total change 0, so its start and end prefix balances are equal.
3. Store the first index at which each balance appears in a map (the balance 0 appears at index -1).
4. Whenever the current balance was seen before at index `j`, the subarray `(j, i]` is balanced: update the best length with `i - j`.

## Example walkthrough

- For `[0, 1]`: the balances are -1, 0; balance 0 was seen at index -1, so the length is `1 - (-1) = 2`.
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(n)` — The map.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

Count zeros and ones in every subarray.

```java
public static int bruteForce(int[] a) {
    int best = 0;
    for (int i = 0; i < a.length; i++) { int bal = 0; for (int j = i; j < a.length; j++) { bal += a[j] == 1 ? 1 : -1; if (bal == 0) best = Math.max(best, j - i + 1); } }
    return best;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Balance with a HashMap (Optimized)

Remember where each balance first appeared. This is the version implemented in the repository.

```java
public static int balance(int[] a) {
    java.util.Map<Integer, Integer> m = new java.util.HashMap<>(); m.put(0, -1); int bal = 0, best = 0;
    for (int i = 0; i < a.length; i++) { bal += a[i] == 1 ? 1 : -1; Integer s = m.putIfAbsent(bal, i); if (s != null) best = Math.max(best, i - s); }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) |
| 2 | Balance with a HashMap (Optimized) | O(n) | O(n) |

## Source Code

### ContiguousArray.java

```java
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ContiguousArray {

    public static void main(String[] args) {
        int[][] inputs = {{0, 1}, {0, 1, 0}, {0, 0, 1, 0, 0, 0, 1, 1}, {1, 1, 1}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + findMaxLength(nums));
            System.out.println();
        }
    }

    public static int findMaxLength(int[] nums) {
        if (nums == null) {
            return 0;
        }
        Map<Integer, Integer> firstIndex = new HashMap<>();
        firstIndex.put(0, -1);
        int balance = 0;
        int best = 0;
        for (int i = 0; i < nums.length; i++) {
            balance += nums[i] == 1 ? 1 : -1;
            Integer seen = firstIndex.putIfAbsent(balance, i);
            if (seen != null) {
                best = Math.max(best, i - seen);
            }
        }
        return best;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: with 0 counted as -1, a balanced subarray is one whose prefix balance is the same at both ends. The earliest index of each balance gives the longest such subarray ending at the current index.

**Input:** `nums = [0, 0, 1, 0, 0, 0, 1, 1]`.

| Index | Value | Balance | First seen at | Length | Best |
|-------|-------|---------|---------------|--------|------|
| 0 | 0 | -1 | new | | 0 |
| 1 | 0 | -2 | new | | 0 |
| 2 | 1 | -1 | 0 | 2 | 2 |
| 3 | 0 | -2 | 1 | 2 | 2 |
| 4 | 0 | -3 | new | | 2 |
| 5 | 0 | -4 | new | | 2 |
| 6 | 1 | -3 | 4 | 2 | 2 |
| 7 | 1 | -2 | 1 | 6 | 6 |

**Output for this array:** `6`. For the first sample, `[0, 1]`, the answer is `2`.
