# Maximum Product of Three Numbers

Given an integer array `nums`, find three numbers whose product is maximum and return the maximum product.

## Algorithm Strategy (Track the Three Largest and Two Smallest)

1. The best product is either the three largest numbers, or the two smallest (most negative) numbers times the largest number.
2. Walk the array once and keep `max1 >= max2 >= max3` and `min1 <= min2`.
3. Compare `max1 * max2 * max3` with `min1 * min2 * max1`.
4. Return the larger one (use `long` internally to avoid overflow in intermediate values).

## Example walkthrough

- For `[-10, -10, 5, 2]`: the top three are 5, 2, -10 (product -100) but the two smallest -10, -10 with the largest 5 give 500.
- Result: `500`. For `[1, 2, 3, 4]` it is `24`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — Five variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Try Every Triple

Compute the product of all triples.

```java
public static int brute(int[] a) { long best = Long.MIN_VALUE; for (int i = 0; i < a.length; i++) for (int j = i + 1; j < a.length; j++) for (int k = j + 1; k < a.length; k++) best = Math.max(best, (long) a[i] * a[j] * a[k]); return (int) best; }
```

- **Time Complexity:** O(n^3)
- **Space Complexity:** O(1)

### Approach 2: Sort

Sort and compare the last three with the first two and the last.

```java
public static int sort(int[] in) { int[] a = in.clone(); java.util.Arrays.sort(a); int n = a.length; return (int) Math.max((long) a[n - 1] * a[n - 2] * a[n - 3], (long) a[0] * a[1] * a[n - 1]); }
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(1)

### Approach 3: Single Pass Tracking (Optimized)

Track the three largest and two smallest values. This is the version implemented in the repository.

```java
public static int track(int[] a) {
    int m1 = Integer.MIN_VALUE, m2 = m1, m3 = m1, n1 = Integer.MAX_VALUE, n2 = n1;
    for (int x : a) { if (x > m1) { m3 = m2; m2 = m1; m1 = x; } else if (x > m2) { m3 = m2; m2 = x; } else if (x > m3) m3 = x; if (x < n1) { n2 = n1; n1 = x; } else if (x < n2) n2 = x; }
    return (int) Math.max((long) m1 * m2 * m3, (long) n1 * n2 * m1);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Try Every Triple | O(n^3) | O(1) |
| 2 | Sort | O(n log n) | O(1) |
| 3 | Single Pass Tracking (Optimized) | O(n) | O(1) |

## Source Code

### MaximumProductOfThreeNumbers.java

```java
import java.util.Arrays;

public class MaximumProductOfThreeNumbers {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3, 4}, {-10, -10, 5, 2}, {-1, -2, -3}, {1, 2, 3}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + maximumProduct(nums));
            System.out.println();
        }
    }

    public static int maximumProduct(int[] nums) {
        if (nums == null || nums.length < 3) {
            return 0;
        }
        int max1 = Integer.MIN_VALUE;
        int max2 = Integer.MIN_VALUE;
        int max3 = Integer.MIN_VALUE;
        int min1 = Integer.MAX_VALUE;
        int min2 = Integer.MAX_VALUE;
        for (int num : nums) {
            if (num > max1) {
                max3 = max2;
                max2 = max1;
                max1 = num;
            } else if (num > max2) {
                max3 = max2;
                max2 = num;
            } else if (num > max3) {
                max3 = num;
            }
            if (num < min1) {
                min2 = min1;
                min1 = num;
            } else if (num < min2) {
                min2 = num;
            }
        }
        return (int) Math.max((long) max1 * max2 * max3, (long) min1 * min2 * max1);
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: the product of three numbers is largest either when all three are the largest numbers, or when two negative numbers (which multiply to a positive) are combined with the largest positive number. No other combination can win, so only the three largest and the two smallest values matter, and one pass can track them.

**Input:** `nums = [-10, -10, 5, 2]`.

| Candidate | Value |
|-----------|-------|
| three largest: 5, 2, -10 | -100 |
| two smallest times the largest: -10, -10, 5 | 500 |

**Output for this input:** `500`. For the first sample in `main`, `[1, 2, 3, 4]`, the output is `24`.
