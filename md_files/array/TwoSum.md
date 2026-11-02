# Two Sum

Utility class to solve the Two Sum problem using a Hash Map.

## Algorithm Strategy (Single-Pass Hash Map)

1. Create an empty `Map<Integer, Integer>` that maps a value to the index where it was seen.
2. Iterate through the array once. For the element `nums[i]` compute its complement: `complement = target - nums[i]`.
3. If the complement is already in the map, the earlier index and `i` form the answer; return them immediately.
4. Otherwise store `nums[i] -> i` in the map so later elements can find it, and continue.
5. If the loop finishes without a match, return `{-1, -1}`. Storing the value only after the lookup guarantees the same element is never used twice.

## Example walkthrough

- For `nums = [2, 7, 11, 15]` and `target = 9`:
- `i = 0`: value 2, complement 7 is not in the map, store `2 -> 0`.
- `i = 1`: value 7, complement 2 is in the map at index 0, return `[0, 1]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Single linear pass over the array with `O(1)` average map lookups.
- **Space Complexity:** `O(n)` — Space required to store up to `n` elements in the hash map.

## Approaches

The same problem can be solved in several ways. They are listed from the simplest to the most efficient.

### Approach 1: Brute Force (Nested Loops)

Check every pair of indices `(i, j)` with `i < j` and return the first pair whose sum equals the target.

```java
public static int[] twoSumBrute(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) {
        for (int j = i + 1; j < nums.length; j++) {
            if (nums[i] + nums[j] == target) {
                return new int[]{i, j};
            }
        }
    }
    return new int[]{-1, -1};
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Sort + Two Pointers

Sort the indices by value, then move a left and a right pointer towards each other: if the sum is too small move the left pointer, if too large move the right pointer. Sorting loses the original positions, so the indices (not the values) are sorted.

```java
public static int[] twoSumSorted(int[] nums, int target) {
    Integer[] idx = new Integer[nums.length];
    for (int i = 0; i < idx.length; i++) idx[i] = i;
    Arrays.sort(idx, Comparator.comparingInt(i -> nums[i]));

    int left = 0, right = nums.length - 1;
    while (left < right) {
        int sum = nums[idx[left]] + nums[idx[right]];
        if (sum == target) {
            return new int[]{Math.min(idx[left], idx[right]), Math.max(idx[left], idx[right])};
        } else if (sum < target) {
            left++;
        } else {
            right--;
        }
    }
    return new int[]{-1, -1};
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 3: Two-Pass Hash Map

First pass: store every `value -> index` in a map. Second pass: for each element look up its complement and make sure it is not the same element.

```java
public static int[] twoSumTwoPass(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        map.put(nums[i], i);
    }
    for (int i = 0; i < nums.length; i++) {
        Integer j = map.get(target - nums[i]);
        if (j != null && j != i) {
            return new int[]{i, j};
        }
    }
    return new int[]{-1, -1};
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Single-Pass Hash Map (Optimized)

Look up the complement while building the map, so each element is visited once and the same element can never be used twice. This is the version implemented in the repository.

```java
public static int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> valueToIndex = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        Integer j = valueToIndex.get(target - nums[i]);
        if (j != null) {
            return new int[]{j, i};
        }
        valueToIndex.put(nums[i], i);
    }
    return new int[]{-1, -1};
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force (Nested Loops) | O(n^2) | O(1) |
| 2 | Sort + Two Pointers | O(n log n) | O(n) |
| 3 | Two-Pass Hash Map | O(n) | O(n) |
| 4 | Single-Pass Hash Map (Optimized) | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### TwoSum.java

```java
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {

    public static void main(String[] args) {
        int[] nums1 = {2, 7, 11, 15};
        int target1 = 9;
        System.out.println("Input 1 : " + Arrays.toString(nums1) + ", Target: " + target1);
        System.out.println("Output  : " + Arrays.toString(twoSum(nums1, target1)));

        System.out.println();

        int[] nums2 = {3, 2, 4};
        int target2 = 6;
        System.out.println("Input 2 : " + Arrays.toString(nums2) + ", Target: " + target2);
        System.out.println("Output  : " + Arrays.toString(twoSum(nums2, target2)));
    }

    /**
     * Finds the indices of two numbers in the array that add up to the target sum.
     *
     * @param nums   the input integer array
     * @param target the target sum to achieve
     * @return a primitive int array containing the two indices, or {-1, -1} if no match is found
     */
    public static int[] twoSum(int[] nums, int target) {
        // Guard Clause: a valid pair requires at least 2 elements
        if (nums == null || nums.length < 2) {
            return new int[]{-1, -1};
        }

        // Map to store value -> index mapping
        Map<Integer, Integer> valueToIndexMap = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int currentVal = nums[i];
            int complement = target - currentVal;

            // If the complement exists in our map, we've found the matching pair
            if (valueToIndexMap.containsKey(complement)) {
                return new int[]{valueToIndexMap.get(complement), i};
            }

            // Otherwise, store current value and its index for future lookups
            valueToIndexMap.put(currentVal, i);
        }

        // Fallback return if no matching pair exists
        return new int[]{-1, -1};
    }
}
```
