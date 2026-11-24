# Frequency of the Most Frequent Element

The frequency of an element is how often it occurs in the array. In one operation you may increment any element by 1. Given an integer array `nums` and an integer `k`, return the maximum possible frequency of an element after performing at most `k` operations.

## Algorithm Strategy (Sort and Slide a Window to the Right End)

1. Sort the array. For a window ending at `right`, the cheapest target value is `nums[right]` (raise everything else to it).
2. The cost to raise the whole window is `nums[right] * windowLength - windowSum`.
3. While that cost exceeds `k`, shrink from the left, subtracting `nums[left]` from the window sum.
4. The best window length is the answer.

## Example walkthrough

- For `[1, 2, 4]` and `k = 5`: sorted `[1, 2, 4]`; raising 1 and 2 to 4 costs 3 + 2 = 5, within `k`.
- Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — Sorting; the window scan is linear.
- **Space Complexity:** `O(1)` — Apart from sorting.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

For every target value, greedily raise the closest smaller elements while the budget lasts.

```java
public static int bruteForce(int[] in, int k) {
    int[] a = in.clone(); java.util.Arrays.sort(a); int best = 1;
    for (int i = 0; i < a.length; i++) { long budget = k; int cnt = 1; for (int j = i - 1; j >= 0; j--) { long c = a[i] - a[j]; if (c > budget) break; budget -= c; cnt++; } best = Math.max(best, cnt); }
    return best;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Sorted Sliding Window (Optimized)

Shrink the window while the cost of raising it to its maximum exceeds `k`. This is the version implemented in the repository.

```java
public static int window(int[] in, int k) {
    int[] a = in.clone(); java.util.Arrays.sort(a); long sum = 0; int l = 0, best = 1;
    for (int r = 0; r < a.length; r++) { sum += a[r]; while ((long) a[r] * (r - l + 1) - sum > k) sum -= a[l++]; best = Math.max(best, r - l + 1); }
    return best;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) |
| 2 | Sorted Sliding Window (Optimized) | O(n log n) | O(1) |

## Source Code

### FrequencyOfTheMostFrequentElement.java

```java
import java.util.Arrays;

public class FrequencyOfTheMostFrequentElement {

    public static void main(String[] args) {
        int[][] nums = {{1, 2, 4}, {1, 4, 8, 13}, {3, 9, 6}, {7}};
        int[] ks = {5, 5, 2, 0};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + maxFrequency(nums[i].clone(), ks[i]));
            System.out.println();
        }
    }

    public static int maxFrequency(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Arrays.sort(nums);
        long windowSum = 0;
        int left = 0;
        int best = 1;
        for (int right = 0; right < nums.length; right++) {
            windowSum += nums[right];
            while ((long) nums[right] * (right - left + 1) - windowSum > k) {
                windowSum -= nums[left++];
            }
            best = Math.max(best, right - left + 1);
        }
        return best;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: to make several elements equal at the lowest cost, raise them all to the largest of them, and the best elements to raise are the closest values, which in a sorted array form a window ending at the target.

**Input:** `nums = [1, 4, 8, 13]`, `k = 5`.

| `right` | Window | Cost to raise to `nums[right]` | Action | Best |
|---------|--------|---------------------------------|--------|------|
| 0 | [1] | 0 | | 1 |
| 1 | [1, 4] | 3 | fits | 2 |
| 2 | [1, 4, 8] | 7 + 4 = 11 | too big, drop 1 -> [4, 8] cost 4 | 2 |
| 3 | [4, 8, 13] | 9 + 5 = 14 | drop 4 -> [8, 13] cost 5 | 2 |

**Output for this input:** `2`. For the first sample, `[1, 2, 4]` with `k = 5`, the output is `3`.
