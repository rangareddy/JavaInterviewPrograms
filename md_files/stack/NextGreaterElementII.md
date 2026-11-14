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

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the array is circular, so the next greater element of a value may be found by wrapping around to the front. The code walks `2 * n` positions (index `i % n`), which visits every element twice. The stack holds indexes whose answer is not yet known. A pushed index waits until a bigger value arrives; in the second lap values are only read, never pushed, so each index is pushed once. Each index is pushed and popped at most once: O(n).

**Input:** `nums = [1, 2, 1]` (n = 3, result starts as `[-1, -1, -1]`). The stack holds indexes, bottom to top.

| i | value = nums[i % 3] | What happens | Stack | result |
|---|---------------------|--------------|-------|--------|
| 0 | 1 | nothing to pop; i < n so push 0 | 0 | `[-1,-1,-1]` |
| 1 | 2 | nums[0] = 1 < 2: pop 0, result[0] = 2; push 1 | 1 | `[2,-1,-1]` |
| 2 | 1 | nums[1] = 2 < 1 false; push 2 | 1 2 | `[2,-1,-1]` |
| 3 | 1 (index 0 again) | nums[2] = 1 < 1 false; second lap, no push | 1 2 | `[2,-1,-1]` |
| 4 | 2 (index 1 again) | nums[2] = 1 < 2: pop 2, result[2] = 2; nums[1] = 2 < 2 false | 1 | `[2,-1,2]` |
| 5 | 1 (index 2 again) | nums[1] = 2 < 1 false | 1 | `[2,-1,2]` |

Index 1 (the maximum) stays on the stack, so its answer remains `-1`.

**Edge cases.** The comparison is strict `<`, so equal values do not count: `[2, 2, 2]` gives `[-1, -1, -1]`. A decreasing array such as `[5, 4, 3, 2, 1]` gives `[-1, 5, 5, 5, 5]`, because the later elements find `5` only in the second lap. The maximum element, and only it (when unique), keeps `-1`. A single element returns `[-1]`.

**Result:** `[2, -1, 2]`, matching the class output.
