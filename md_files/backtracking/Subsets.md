# Subsets

Given an integer array `nums` of unique elements, return all possible subsets (the power set). The solution set must not contain duplicate subsets.

## Algorithm Strategy (Backtracking: Add the Current Subset, Then Extend It)

1. Build subsets incrementally in a list `current`. Every state of `current` is itself a valid subset, so record a copy of it at the start of each call.
2. To extend the subset, loop over the remaining elements `start .. n - 1`: add the element, recurse with `start = i + 1`, then remove the element again (backtrack).
3. Only moving forward (`i + 1`) guarantees that each subset is generated exactly once, in the order of the original array.
4. There are `2^n` subsets in total.

## Example walkthrough

- For `[1, 2, 3]` the recursion records `[]`, `[1]`, `[1,2]`, `[1,2,3]`, `[1,3]`, `[2]`, `[2,3]`, `[3]`.
- That is the power set with 8 subsets.

## Complexity Analysis

- **Time Complexity:** `O(n * 2^n)` — `2^n` subsets, each copied in `O(n)`.
- **Space Complexity:** `O(n)` — Recursion depth and the current subset (excluding the output).

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Iterative Doubling (Cascading)

Start with the empty subset. For every number, copy all existing subsets and add the number to the copies: the number of subsets doubles with every element.

```java
public static List<List<Integer>> subsetsCascade(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    result.add(new ArrayList<>());
    for (int num : nums) {
        int size = result.size();
        for (int i = 0; i < size; i++) {
            List<Integer> extended = new ArrayList<>(result.get(i));
            extended.add(num);
            result.add(extended);
        }
    }
    return result;
}
```

- **Time Complexity:** O(n * 2^n)
- **Space Complexity:** O(1) besides the output

### Approach 2: Bit-Mask Enumeration

Every number from `0` to `2^n - 1` is a subset: bit `i` says whether `nums[i]` is included. Works for arrays of up to 30 or so elements.

```java
public static List<List<Integer>> subsetsMask(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    for (int mask = 0; mask < (1 << nums.length); mask++) {
        List<Integer> subset = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) if ((mask & (1 << i)) != 0) subset.add(nums[i]);
        result.add(subset);
    }
    return result;
}
```

- **Time Complexity:** O(n * 2^n)
- **Space Complexity:** O(1) besides the output

### Approach 3: Include / Exclude Decision Tree

At every element make two recursive choices: skip it, or take it. The leaves of this binary decision tree are the subsets.

```java
public static List<List<Integer>> subsetsDecision(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    decide(nums, 0, new ArrayList<>(), result);
    return result;
}

private static void decide(int[] nums, int index, List<Integer> current, List<List<Integer>> result) {
    if (index == nums.length) { result.add(new ArrayList<>(current)); return; }
    decide(nums, index + 1, current, result);              // exclude nums[index]
    current.add(nums[index]);
    decide(nums, index + 1, current, result);              // include nums[index]
    current.remove(current.size() - 1);
}
```

- **Time Complexity:** O(n * 2^n)
- **Space Complexity:** O(n)

### Approach 4: Backtracking: Record Every State (Optimized)

Every state of the growing subset is a valid subset, so record it at the start of each call and extend it with the elements that come later. This is the version implemented in the repository.

```java
public static List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(nums, 0, new ArrayList<>(), result);
    return result;
}

private static void backtrack(int[] nums, int start, List<Integer> current, List<List<Integer>> result) {
    result.add(new ArrayList<>(current));
    for (int i = start; i < nums.length; i++) {
        current.add(nums[i]);
        backtrack(nums, i + 1, current, result);
        current.remove(current.size() - 1);
    }
}
```

- **Time Complexity:** O(n * 2^n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Iterative Doubling (Cascading) | O(n * 2^n) | O(1) besides the output |
| 2 | Bit-Mask Enumeration | O(n * 2^n) | O(1) besides the output |
| 3 | Include / Exclude Decision Tree | O(n * 2^n) | O(n) |
| 4 | Backtracking: Record Every State (Optimized) | O(n * 2^n) | O(n) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### Subsets.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Subsets {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3}, {0}, {}, {5, 7}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + subsets(nums));
            System.out.println();
        }
    }

    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, 0, new ArrayList<>(), result);
        return result;
    }

    private static void backtrack(int[] nums, int start, List<Integer> current, List<List<Integer>> result) {
        result.add(new ArrayList<>(current));
        for (int i = start; i < nums.length; i++) {
            current.add(nums[i]);
            backtrack(nums, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
}
```
