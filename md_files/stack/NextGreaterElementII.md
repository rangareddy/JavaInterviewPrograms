# Next Greater Element II

Given a circular integer array `nums` (the next element of the last element is the first element), return for each element its next greater element, searching circularly, or `-1` if no greater element exists. Duplicates are allowed.

## Algorithm Strategy (Monotonic Stack over Two Laps)

1. A circular array can be handled by walking the indices `0 .. 2n - 1` and reading the element at `i % n`, which lets every element see the elements before it as well.
2. Keep a stack of indices whose next greater element is not known yet (their values decrease from bottom to top).
3. For every position `i`, while the value at the top index is smaller than `nums[i % n]`, pop it and set its answer to `nums[i % n]`.
4. Push an index only during the first lap (`i < n`); the second lap exists only to resolve indices that were left on the stack. Indices still on the stack at the end keep the default `-1`.

## Example walkthrough

- For `[1, 2, 1]`: during the first lap index 0 is resolved by `2`, and indices 1 and 2 stay on the stack.
- In the second lap the value 1 at `i = 3` does not resolve index 2 (not greater), but the value 2 at `i = 4` does. Nothing is greater than the 2 at index 1. Result: `[2, -1, 2]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each index is pushed once and popped once across the two laps.
- **Space Complexity:** `O(n)` — The stack and the result.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

For each index scan the next `n - 1` positions circularly.

```java
public static int[] nextGreaterElementsBrute(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    for (int i = 0; i < n; i++) {
        result[i] = -1;
        for (int step = 1; step < n; step++) {
            int candidate = nums[(i + step) % n];
            if (candidate > nums[i]) { result[i] = candidate; break; }
        }
    }
    return result;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1) besides the output

### Approach 2: Monotonic Stack over Two Laps (Optimized)

Walk the array twice with a decreasing stack of indices so each element is compared with the elements wrapping around it. This is the version implemented in the repository.

```java
public static int[] nextGreaterElements(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    Arrays.fill(result, -1);
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = 0; i < 2 * n; i++) {
        int value = nums[i % n];
        while (!stack.isEmpty() && nums[stack.peek()] < value) {
            result[stack.pop()] = value;
        }
        if (i < n) {
            stack.push(i);
        }
    }
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) besides the output |
| 2 | Monotonic Stack over Two Laps (Optimized) | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### NextGreaterElementII.java

```java
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class NextGreaterElementII {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 1}, {1, 2, 3, 4, 3}, {5, 4, 3, 2, 1}, {2, 2, 2}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + Arrays.toString(nextGreaterElements(nums)));
            System.out.println();
        }
    }

    public static int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        Arrays.fill(result, -1);
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < 2 * n; i++) {
            int value = nums[i % n];
            while (!stack.isEmpty() && nums[stack.peek()] < value) {
                result[stack.pop()] = value;
            }
            if (i < n) {
                stack.push(i);
            }
        }
        return result;
    }
}
```
