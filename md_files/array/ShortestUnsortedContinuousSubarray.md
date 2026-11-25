# Shortest Unsorted Continuous Subarray

Given an integer array `nums`, find one continuous subarray such that sorting only this subarray in ascending order makes the whole array sorted. Return the length of the shortest such subarray.

## Algorithm Strategy (Two Scans for the Boundaries)

1. Scan left to right keeping the running maximum. Any element smaller than it is out of place, so the last such index is the right boundary `end`.
2. Scan right to left keeping the running minimum. Any element larger than it is out of place, so the last such index (the smallest one) is the left boundary `start`.
3. If no element was out of place the array is already sorted and the answer is `0`.
4. Otherwise the answer is `end - start + 1`.

## Example walkthrough

- For `[2, 6, 4, 8, 10, 9, 15]`: moving right, 4 is below the running max 6 and 9 is below 10, so `end = 5`.
- Moving right to left, the running minimum becomes 4 at index 2, and 6 at index 1 is larger than it, so `start = 1`.
- Result: `5 - 1 + 1 = 5`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Two linear scans.
- **Space Complexity:** `O(1)` — A few variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Sort and Compare

Sort a copy and find the first and last positions that differ.

```java
public static int sorted(int[] a) { int[] b = a.clone(); java.util.Arrays.sort(b); int s = 0, e = a.length - 1; while (s < a.length && a[s] == b[s]) s++; if (s == a.length) return 0; while (a[e] == b[e]) e--; return e - s + 1; }
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 2: Running Max and Min (Optimized)

Find the boundaries with two scans and no sorting. This is the version implemented in the repository.

```java
public static int scans(int[] a) { int n = a.length; if (n < 2) return 0; int max = a[0], end = -1; for (int i = 1; i < n; i++) { if (a[i] < max) end = i; else max = a[i]; } int min = a[n - 1], start = 0; for (int i = n - 2; i >= 0; i--) { if (a[i] > min) start = i; else min = a[i]; } return end == -1 ? 0 : end - start + 1; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Sort and Compare | O(n log n) | O(n) |
| 2 | Running Max and Min (Optimized) | O(n) | O(1) |

## Source Code

### ShortestUnsortedContinuousSubarray.java

```java
import java.util.Arrays;

public class ShortestUnsortedContinuousSubarray {

    public static void main(String[] args) {
        int[][] inputs = {{2, 6, 4, 8, 10, 9, 15}, {1, 2, 3, 4}, {1}, {2, 1}, {1, 3, 2, 2, 2}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + findUnsortedSubarray(nums));
            System.out.println();
        }
    }

    public static int findUnsortedSubarray(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        int n = nums.length;
        int max = nums[0];
        int end = -1;
        for (int i = 1; i < n; i++) {
            if (nums[i] < max) {
                end = i;
            } else {
                max = nums[i];
            }
        }
        int min = nums[n - 1];
        int start = 0;
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] > min) {
                start = i;
            } else {
                min = nums[i];
            }
        }
        return end == -1 ? 0 : end - start + 1;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: an element is out of place on the right side if something larger came before it, and out of place on the left side if something smaller comes after it. The extreme such positions mark the smallest segment that must be sorted.

**Input:** `nums = [2, 6, 4, 8, 10, 9, 15]`.

| Scan | Position | Running value | Out of place? |
|------|----------|---------------|---------------|
| left to right | 4 (index 2) | max 6 | yes, `end = 2` |
| left to right | 9 (index 5) | max 10 | yes, `end = 5` |
| right to left | 6 (index 1) | min 4 | yes, `start = 1` |
| right to left | 2 (index 0) | min 2 | no |

The segment is indexes 1 to 5.

**Output:** `5`.
