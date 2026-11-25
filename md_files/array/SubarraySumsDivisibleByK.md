# Subarray Sums Divisible by K

Given an integer array `nums` and an integer `k`, return the number of non-empty subarrays that have a sum divisible by `k`.

## Algorithm Strategy (Count Equal Prefix Remainders)

1. A subarray `nums[i..j]` has a sum divisible by `k` when the prefix sums before `i` and up to `j` leave the same remainder modulo `k`.
2. Keep an array `count[k]` of how many prefixes had each remainder; start with `count[0] = 1` for the empty prefix.
3. For each element, update the running sum and compute its remainder, made non-negative with `((sum % k) + k) % k`.
4. Add `count[remainder]` to the answer, then increase it.

## Example walkthrough

- For `[4, 5, 0, -2, -3, 1]` and `k = 5` the prefix remainders are 4, 4, 4, 2, 4... leading to 7 qualifying subarrays.
- Result: `7`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(k)` — The remainder counts.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Check Every Subarray

Sum every subarray and test divisibility.

```java
public static int brute(int[] a, int k) { int c = 0; for (int i = 0; i < a.length; i++) { int s = 0; for (int j = i; j < a.length; j++) { s += a[j]; if (s % k == 0) c++; } } return c; }
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Prefix Remainder Counts (Optimized)

Count pairs of prefixes with the same remainder. This is the version implemented in the repository.

```java
public static int remainders(int[] a, int k) { int[] c = new int[k]; c[0] = 1; int s = 0, r = 0; for (int x : a) { s = ((s + x) % k + k) % k; r += c[s]++; } return r; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(k)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Check Every Subarray | O(n^2) | O(1) |
| 2 | Prefix Remainder Counts (Optimized) | O(n) | O(k) |

## Source Code

### SubarraySumsDivisibleByK.java

```java
import java.util.Arrays;

public class SubarraySumsDivisibleByK {

    public static void main(String[] args) {
        int[][] nums = {{4, 5, 0, -2, -3, 1}, {5}, {-1, 2, 9}, {}};
        int[] ks = {5, 9, 2, 3};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + subarraysDivByK(nums[i], ks[i]));
            System.out.println();
        }
    }

    public static int subarraysDivByK(int[] nums, int k) {
        if (nums == null || k <= 0) {
            return 0;
        }
        int[] count = new int[k];
        count[0] = 1;
        int sum = 0;
        int result = 0;
        for (int num : nums) {
            sum = ((sum + num) % k + k) % k;
            result += count[sum]++;
        }
        return result;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the sum of `nums[i..j]` is the difference of two prefix sums. That difference is divisible by `k` exactly when both prefix sums leave the same remainder modulo `k`. So the problem becomes counting pairs of equal remainders, which a count array does as the array is scanned. Negative sums need a non-negative remainder, hence `((x % k) + k) % k`.

**Input:** `nums = [4, 5, 0, -2, -3, 1]`, `k = 5`.

| Element | Prefix remainder | Matching earlier prefixes | Total |
|---------|-----------------|----------------------------|-------|
| (start) | 0 | | 0 |
| 4 | 4 | 0 | 0 |
| 5 | 4 | 1 | 1 |
| 0 | 4 | 2 | 3 |
| -2 | 2 | 0 | 3 |
| -3 | 4 | 3 | 6 |
| 1 | 0 | 1 | 7 |

**Output:** `7`.
