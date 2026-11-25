# Minimum Value to Get Positive Step by Step Sum

Given an array of integers `nums`, you start with some positive integer `startValue` and add the elements one by one. Return the minimum `startValue` such that the running sum is never less than 1.

## Algorithm Strategy (Minimum Prefix Sum)

1. Compute the running (prefix) sum of `nums` and track its minimum value `minPrefix`.
2. With `startValue = s`, the lowest running total is `s + minPrefix`.
3. That must be at least 1, so `s >= 1 - minPrefix`.
4. The answer is `max(1, 1 - minPrefix)`.

## Example walkthrough

- For `[-3, 2, -3, 4, 2]` the prefix sums are -3, -1, -4, 0, 2, so the minimum is -4.
- Result: `1 - (-4) = 5`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — Two numbers.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Try Every Start Value

Test `startValue = 1, 2, 3, ...` until the running sum stays positive.

```java
public static int tryStarts(int[] a) {
    for (int s = 1; ; s++) { int sum = s; boolean ok = true; for (int x : a) { sum += x; if (sum < 1) { ok = false; break; } } if (ok) return s; }
}
```

- **Time Complexity:** O(n * answer)
- **Space Complexity:** O(1)

### Approach 2: Minimum Prefix Sum (Optimized)

Offset the lowest prefix sum. This is the version implemented in the repository.

```java
public static int minPrefix(int[] a) {
    int min = 0, s = 0; for (int x : a) { s += x; min = Math.min(min, s); } return 1 - min;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Try Every Start Value | O(n * answer) | O(1) |
| 2 | Minimum Prefix Sum (Optimized) | O(n) | O(1) |

## Source Code

### MinimumValueToGetPositiveStepByStepSum.java

```java
import java.util.Arrays;

public class MinimumValueToGetPositiveStepByStepSum {

    public static void main(String[] args) {
        int[][] inputs = {{-3, 2, -3, 4, 2}, {1, 2}, {1, -2, -3}, {}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + minStartValue(nums));
            System.out.println();
        }
    }

    public static int minStartValue(int[] nums) {
        int minPrefix = 0;
        int sum = 0;
        if (nums != null) {
            for (int x : nums) {
                sum += x;
                minPrefix = Math.min(minPrefix, sum);
            }
        }
        return 1 - minPrefix;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the start value only shifts every running total up by the same amount, so the worst moment is the one with the lowest prefix sum, and that is the only one that needs to be lifted to 1.

**Input:** `nums = [-3, 2, -3, 4, 2]`.

| After element | Prefix sum | Minimum |
|---------------|------------|---------|
| -3 | -3 | -3 |
| 2 | -1 | -3 |
| -3 | -4 | -4 |
| 4 | 0 | -4 |
| 2 | 2 | -4 |

**Output:** `1 - (-4) = 5`.
