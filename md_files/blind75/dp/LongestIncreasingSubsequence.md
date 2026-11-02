# Longest Increasing Subsequence

Given an integer array `nums`, return the length of the longest strictly increasing subsequence (elements in order, not necessarily adjacent).

## Algorithm Strategy (Patience Sorting with Binary Search)

1. Keep an array `tails` where `tails[i]` is the smallest possible last value of an increasing subsequence of length `i + 1` found so far. It is always sorted.
2. For each number, binary-search the first position in `tails` whose value is `>= num`.
3. If the position is at the end, the number extends the longest subsequence: append it. Otherwise replace that entry, which keeps a smaller tail for the same length and so leaves more room for later numbers.
4. The length of `tails` is the length of the longest increasing subsequence. (The array is not itself the subsequence.)

## Example walkthrough

- For `[10, 9, 2, 5, 3, 7, 101, 18]`: tails evolve as [10], [9], [2], [2,5], [2,3], [2,3,7], [2,3,7,101], [2,3,7,18].
- Result: `4`.

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — One binary search per number.
- **Space Complexity:** `O(n)` — The `tails` array.

## Source Code

### LongestIncreasingSubsequence.java

```java
import java.util.Arrays;

public class LongestIncreasingSubsequence {

    public static void main(String[] args) {
        int[][] inputs = {{10, 9, 2, 5, 3, 7, 101, 18}, {0, 1, 0, 3, 2, 3}, {7, 7, 7, 7}, {1, 2, 3, 4}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + lengthOfLIS(nums));
            System.out.println();
        }
    }

    public static int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int[] tails = new int[nums.length];
        int size = 0;
        for (int num : nums) {
            // find the first tail >= num
            int low = 0;
            int high = size;
            while (low < high) {
                int mid = low + (high - low) / 2;
                if (tails[mid] < num) {
                    low = mid + 1;
                } else {
                    high = mid;
                }
            }
            tails[low] = num;
            if (low == size) {
                size++;
            }
        }
        return size;
    }
}
```
