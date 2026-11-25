# Count Number of Nice Subarrays

Given an array of integers `nums` and an integer `k`, a continuous subarray is nice if it contains exactly `k` odd numbers. Return the number of nice subarrays.

## Algorithm Strategy (Prefix Odd Count with a Frequency Table)

1. Let `odd` be the number of odd values seen so far. A subarray ending at the current index has exactly `k` odd numbers when some earlier prefix had `odd - k` odd numbers.
2. Keep an array `freq` where `freq[x]` is the number of prefixes with `x` odd numbers; start with `freq[0] = 1` (the empty prefix).
3. For every element, update `odd`, add `freq[odd - k]` (if non-negative) to the answer, then increase `freq[odd]`.
4. Return the total.

## Example walkthrough

- For `[1, 1, 2, 1, 1]` and `k = 3`: the nice subarrays are `[1,1,2,1]` and `[1,2,1,1]`.
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(n)` — The frequency array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Check Every Subarray

Count the odd numbers of every subarray.

```java
public static int brute(int[] a, int k) { int c = 0; for (int i = 0; i < a.length; i++) { int odd = 0; for (int j = i; j < a.length; j++) { odd += a[j] & 1; if (odd == k) c++; } } return c; }
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Prefix Count and Frequency Table (Optimized)

Count prefixes with `odd - k` odd numbers. This is the version implemented in the repository.

```java
public static int prefix(int[] a, int k) { int[] f = new int[a.length + 1]; f[0] = 1; int odd = 0, c = 0; for (int x : a) { odd += x & 1; if (odd >= k) c += f[odd - k]; f[odd]++; } return c; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Check Every Subarray | O(n^2) | O(1) |
| 2 | Prefix Count and Frequency Table (Optimized) | O(n) | O(n) |

## Source Code

### CountNumberOfNiceSubarrays.java

```java
import java.util.Arrays;

public class CountNumberOfNiceSubarrays {

    public static void main(String[] args) {
        int[][] nums = {{1, 1, 2, 1, 1}, {2, 4, 6}, {2, 2, 2, 1, 2, 2, 1, 2, 2, 2}, {1}};
        int[] ks = {3, 1, 2, 1};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + numberOfSubarrays(nums[i], ks[i]));
            System.out.println();
        }
    }

    public static int numberOfSubarrays(int[] nums, int k) {
        if (nums == null || k < 0) {
            return 0;
        }
        int[] freq = new int[nums.length + 1];
        freq[0] = 1;
        int odd = 0;
        int count = 0;
        for (int num : nums) {
            odd += num & 1;
            if (odd >= k) {
                count += freq[odd - k];
            }
            freq[odd]++;
        }
        return count;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the number of odd values in `nums[i..j]` is `odd[j] - odd[i - 1]`, where `odd[x]` counts the odd values in the first `x` elements. So for each end index, the nice subarrays are the earlier prefixes whose count is exactly `k` smaller, and a frequency table counts them instantly.

**Input:** `nums = [1, 1, 2, 1, 1]`, `k = 3`.

| Element | `odd` | `freq[odd - k]` | Added | `freq` after |
|---------|-------|-----------------|-------|--------------|
| 1 | 1 | | 0 | {0:1, 1:1} |
| 1 | 2 | | 0 | {0:1, 1:1, 2:1} |
| 2 | 2 | | 0 | {0:1, 1:1, 2:2} |
| 1 | 3 | freq[0] = 1 | 1 | {..., 3:1} |
| 1 | 4 | freq[1] = 1 | 1 | |

**Output:** `2`.
