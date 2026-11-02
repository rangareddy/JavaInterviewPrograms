# Longest Consecutive Sequence

Given an unsorted array of integers, return the length of the longest sequence of consecutive values (such as 1, 2, 3, 4). The algorithm must run in `O(n)` time.

## Algorithm Strategy (Hash Set and Sequence Starts)

1. Put all numbers into a `HashSet` for `O(1)` membership tests.
2. A number is the start of a sequence only if `num - 1` is not in the set. Ignore all other numbers: they will be visited from their start.
3. From each start, count how long `num + 1`, `num + 2`, ... keep being in the set.
4. Return the longest length found. Every number is part of exactly one sequence and is walked over once, so the total work is linear.

## Example walkthrough

- For `[100, 4, 200, 1, 3, 2]`: the starts are 100, 200 and 1 (because 0 is missing).
- From 1 the sequence 1, 2, 3, 4 has length 4; from 100 and 200 the length is 1. Result: `4`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each number is counted only from the start of its sequence.
- **Space Complexity:** `O(n)` — The hash set.

## Source Code

### LongestConsecutiveSequence.java

```java
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LongestConsecutiveSequence {

    public static void main(String[] args) {
        int[][] inputs = {{100, 4, 200, 1, 3, 2}, {0, 3, 7, 2, 5, 8, 4, 6, 0, 1}, {}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + longestConsecutive(nums));
            System.out.println();
        }
    }

    public static int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int longest = 0;
        for (int num : set) {
            if (!set.contains(num - 1)) {           // num is the start of a sequence
                int current = num;
                int length = 1;
                while (set.contains(current + 1)) {
                    current++;
                    length++;
                }
                longest = Math.max(longest, length);
            }
        }
        return longest;
    }
}
```
