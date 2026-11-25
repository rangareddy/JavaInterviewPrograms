# Longest Subarray of 1's After Deleting One Element

Given a binary array `nums`, you should delete exactly one element from it. Return the size of the longest non-empty subarray containing only `1`s in the resulting array, or `0` if there is none.

## Algorithm Strategy (Window with at Most One Zero)

1. Slide a window `[left, right]` that contains at most one zero.
2. Extend `right`; when the window holds two zeros, move `left` forward until only one remains.
3. The window length minus one (the element that must be deleted) is a candidate answer.
4. Return the best candidate; an array of all ones still loses one element.

## Example walkthrough

- For `[1, 1, 0, 1]` the whole array is a window with one zero: length 4, minus the deleted zero gives 3.
- Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each index enters and leaves the window once.
- **Space Complexity:** `O(1)` — Two indexes and a zero counter.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Try Deleting Every Element

Delete each element and find the longest run of ones in what is left.

```java
public static int brute(int[] a) { int best = 0; for (int d = 0; d < a.length; d++) { int run = 0; for (int i = 0; i < a.length; i++) { if (i == d) continue; if (a[i] == 1) { run++; best = Math.max(best, run); } else run = 0; } } return best; }
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Sliding Window with One Zero (Optimized)

Keep at most one zero in the window and subtract the deleted element. This is the version implemented in the repository.

```java
public static int window(int[] a) { int l = 0, z = 0, best = 0; for (int r = 0; r < a.length; r++) { if (a[r] == 0) z++; while (z > 1) if (a[l++] == 0) z--; best = Math.max(best, r - l); } return best; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Try Deleting Every Element | O(n^2) | O(1) |
| 2 | Sliding Window with One Zero (Optimized) | O(n) | O(1) |

## Source Code

### LongestSubarrayOf1sAfterDeletingOneElement.java

```java
import java.util.Arrays;

public class LongestSubarrayOf1sAfterDeletingOneElement {

    public static void main(String[] args) {
        int[][] inputs = {{1, 1, 0, 1}, {0, 1, 1, 1, 0, 1, 1, 0, 1}, {1, 1, 1}, {0, 0}, {}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + longestSubarray(nums));
            System.out.println();
        }
    }

    public static int longestSubarray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int left = 0;
        int zeros = 0;
        int best = 0;
        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0) {
                zeros++;
            }
            while (zeros > 1) {
                if (nums[left++] == 0) {
                    zeros--;
                }
            }
            best = Math.max(best, right - left);
        }
        return best;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the element to delete can always be taken to be a zero inside the window (or any element if the window has none), so the answer is the longest window with at most one zero, minus one for the deleted element. A sliding window finds it in one pass.

**Input:** `nums = [1, 1, 0, 1]`.

| `right` | Window | Zeros | `right - left` |
|---------|--------|-------|----------------|
| 0 | [1] | 0 | 0 |
| 1 | [1, 1] | 0 | 1 |
| 2 | [1, 1, 0] | 1 | 2 |
| 3 | [1, 1, 0, 1] | 1 | 3 |

**Output:** `3`.
