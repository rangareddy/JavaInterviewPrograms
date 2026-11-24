# Max Consecutive Ones III

Given a binary array `nums` and an integer `k`, return the maximum number of consecutive `1`s in the array if you can flip at most `k` zeros.

## Algorithm Strategy (Sliding Window with a Zero Count)

1. Maintain a window `[left, right]` that contains at most `k` zeros.
2. Extend `right`; if `nums[right]` is 0, increase the zero count.
3. While the zero count exceeds `k`, move `left` forward, decreasing the count when a 0 leaves the window.
4. The window length after each step is a valid answer candidate; keep the maximum.

## Example walkthrough

- For `[1,1,1,0,0,0,1,1,1,1,0]` and `k = 2`: the window from index 5 to 10 holds 6 values with two zeros.
- Result: `6`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each index enters and leaves the window once.
- **Space Complexity:** `O(1)` — Two pointers and a counter.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

For every start, extend while the zero count stays within `k`.

```java
public static int bruteForce(int[] a, int k) {
    int best = 0;
    for (int i = 0; i < a.length; i++) {
        int zeros = 0;
        for (int j = i; j < a.length; j++) { if (a[j] == 0) zeros++; if (zeros > k) break; best = Math.max(best, j - i + 1); }
    }
    return best;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Prefix Zero Counts and Binary Search

With zero prefix counts, binary search the farthest end for each start.

```java
public static int prefixSearch(int[] a, int k) {
    int n = a.length; int[] z = new int[n + 1];
    for (int i = 0; i < n; i++) z[i + 1] = z[i] + (a[i] == 0 ? 1 : 0);
    int best = 0;
    for (int i = 0; i < n; i++) {
        int lo = i, hi = n;
        while (lo < hi) { int mid = (lo + hi + 1) / 2; if (z[mid] - z[i] <= k) lo = mid; else hi = mid - 1; }
        best = Math.max(best, lo - i);
    }
    return best;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 3: Sliding Window (Optimized)

Shrink from the left whenever the window holds more than `k` zeros. This is the version implemented in the repository.

```java
public static int window(int[] a, int k) {
    int best = 0, left = 0, zeros = 0;
    for (int right = 0; right < a.length; right++) {
        if (a[right] == 0) zeros++;
        while (zeros > k) if (a[left++] == 0) zeros--;
        best = Math.max(best, right - left + 1);
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) |
| 2 | Prefix Zero Counts and Binary Search | O(n log n) | O(n) |
| 3 | Sliding Window (Optimized) | O(n) | O(1) |

## Source Code

### MaxConsecutiveOnesIII.java

```java
import java.util.Arrays;

public class MaxConsecutiveOnesIII {

    public static void main(String[] args) {
        int[][] nums = {{1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0}, {0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1}, {0, 0, 0}, {1, 1}};
        int[] ks = {2, 3, 0, 0};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(nums[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + longestOnes(nums[i], ks[i]));
            System.out.println();
        }
    }

    public static int longestOnes(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int best = 0;
        int left = 0;
        int zeros = 0;
        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0) {
                zeros++;
            }
            while (zeros > k) {
                if (nums[left++] == 0) {
                    zeros--;
                }
            }
            best = Math.max(best, right - left + 1);
        }
        return best;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: "longest run with at most `k` flips" is the same as "longest window with at most `k` zeros". The window only moves forward, so each index is handled twice at most.

**Input:** `nums = [1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0]`, `k = 2`.

| `right` | Value | `zeros` | `left` after shrinking | Window length |
|---------|-------|---------|-------------------------|---------------|
| 3 | 0 | 1 | 0 | 4 |
| 4 | 0 | 2 | 0 | 5 |
| 5 | 0 | 3 | 4 | 2 |
| 6 to 9 | 1 | 2 | 4 | 3, 4, 5, 6 |
| 10 | 0 | 3 | 5 | 6 |

The longest window seen is 6.

**Output:** `6`.
