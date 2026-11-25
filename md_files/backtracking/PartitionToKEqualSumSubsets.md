# Partition to K Equal Sum Subsets

Given an integer array `nums` and an integer `k`, return `true` if it is possible to divide this array into `k` non-empty subsets whose sums are all equal.

## Algorithm Strategy (Backtracking, One Bucket at a Time)

1. If the total is not divisible by `k`, or any number exceeds `total / k`, the answer is `false`.
2. Sort the numbers in descending order so that large values are placed first and dead ends show up early.
3. Fill one bucket at a time: try each unused number that keeps the bucket at or below `target`.
4. When a bucket reaches `target`, start the next one. If starting a bucket with the first unused number fails, stop; no other choice can work either.

## Example walkthrough

- For `[4, 3, 2, 3, 5, 2, 1]` and `k = 4`: the total is 20, so the target is 5.
- The buckets `{5}`, `{4, 1}`, `{3, 2}`, `{3, 2}` all sum to 5.
- Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(k * 2^n) worst case` — Each bucket explores subsets of the remaining numbers; pruning keeps it practical.
- **Space Complexity:** `O(n)` — Used flags and recursion.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Bitmask DP over Used Numbers

Track which numbers are used as a bitmask and the sum so far modulo the target. Exact for small arrays, but always visits up to `2^n` states.

```java
public static boolean mask(int[] a, int k) { int n = a.length, sum = 0; if (n == 0 || k <= 0 || k > n) return false; for (int x : a) sum += x; if (sum % k != 0) return false; int t = sum / k; int[] cur = new int[1 << n]; java.util.Arrays.fill(cur, -1); cur[0] = 0; for (int m = 0; m < (1 << n); m++) { if (cur[m] < 0) continue; for (int i = 0; i < n; i++) { if ((m & (1 << i)) != 0 || cur[m] + a[i] > t) continue; int nm = m | (1 << i); cur[nm] = (cur[m] + a[i]) % t; } } return cur[(1 << n) - 1] == 0; }
```

- **Time Complexity:** O(n * 2^n)
- **Space Complexity:** O(2^n)

### Approach 2: Bucket Backtracking with Pruning (Optimized)

Fill one bucket at a time, trying the largest numbers first, and stop early when a fresh bucket cannot be started. This is the version implemented in the repository.

```java
public static boolean fill(int[] in, int k) { int n = in.length, sum = 0; if (n == 0 || k <= 0 || k > n) return false; for (int x : in) sum += x; if (sum % k != 0) return false; int t = sum / k; int[] a = in.clone(); java.util.Arrays.sort(a); if (a[n - 1] > t) return false; return go(a, new boolean[n], k, n - 1, 0, t); }
static boolean go(int[] a, boolean[] u, int left, int start, int cur, int t) { if (left == 1) return true; if (cur == t) return go(a, u, left - 1, a.length - 1, 0, t); for (int i = Math.min(start, a.length - 1); i >= 0; i--) { if (u[i] || cur + a[i] > t) continue; u[i] = true; if (go(a, u, left, i - 1, cur + a[i], t)) return true; u[i] = false; if (cur == 0) return false; } return false; }
```

- **Time Complexity:** O(k * 2^n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Bitmask DP over Used Numbers | O(n * 2^n) | O(2^n) |
| 2 | Bucket Backtracking with Pruning (Optimized) | O(k * 2^n) | O(n) |

## Source Code

### PartitionToKEqualSumSubsets.java

```java
import java.util.Arrays;

public class PartitionToKEqualSumSubsets {

    public static void main(String[] args) {
        int[][] inputs = {{4, 3, 2, 3, 5, 2, 1}, {1, 2, 3, 4}, {2, 2, 2, 2, 3, 4, 5}, {1}, {}};
        int[] ks = {4, 3, 4, 1, 2};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(inputs[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + canPartitionKSubsets(inputs[i], ks[i]));
            System.out.println();
        }
    }

    public static boolean canPartitionKSubsets(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            return false;
        }
        int sum = 0;
        for (int x : nums) {
            sum += x;
        }
        if (sum % k != 0) {
            return false;
        }
        int target = sum / k;
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        if (sorted[sorted.length - 1] > target) {
            return false;
        }
        return fill(sorted, new boolean[sorted.length], k, sorted.length - 1, 0, target);
    }

    private static boolean fill(int[] nums, boolean[] used, int bucketsLeft, int start, int current, int target) {
        if (bucketsLeft == 1) {
            return true;
        }
        if (current == target) {
            return fill(nums, used, bucketsLeft - 1, nums.length - 1, 0, target);
        }
        for (int i = Math.min(start, nums.length - 1); i >= 0; i--) {
            if (used[i] || current + nums[i] > target) {
                continue;
            }
            used[i] = true;
            if (fill(nums, used, bucketsLeft, i - 1, current + nums[i], target)) {
                return true;
            }
            used[i] = false;
            if (current == 0) {
                return false;
            }
        }
        return false;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: if a partition exists, every subset sums to `total / k`. Building one subset completely before starting the next avoids symmetric duplicates, and trying large numbers first makes impossible choices fail quickly.

**Input:** `nums = [4, 3, 2, 3, 5, 2, 1]`, `k = 4`, target `20 / 4 = 5`.

| Bucket | Numbers chosen | Sum |
|--------|----------------|-----|
| 1 | 5 | 5 |
| 2 | 4, 1 | 5 |
| 3 | 3, 2 | 5 |
| 4 | 3, 2 | 5 |

All numbers are used and every bucket reaches the target.

**Output:** `true`.
