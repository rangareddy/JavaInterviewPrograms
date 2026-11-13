# Next Greater Element I

Given two arrays of distinct integers `nums1` and `nums2`, where `nums1` is a subset of `nums2`, return for each `nums1[i]` the first element to its right in `nums2` that is greater than it, or `-1` if there is none.

## Algorithm Strategy (Monotonic Decreasing Stack and a Lookup Map)

1. Compute the next greater element for every value in `nums2` once, and answer each query of `nums1` from a map.
2. Scan `nums2` left to right with a stack of values that are still waiting for a greater element (decreasing from bottom to top).
3. For each `x`, while the top of the stack is smaller than `x`, pop it and record `x` as its next greater element.
4. Push `x`. Values left on the stack at the end have no greater element, so a lookup defaults to `-1`.

## Example walkthrough

- For `nums2 = [1, 3, 4, 2]`: 3 resolves 1, 4 resolves 3, and 4 and 2 stay on the stack.
- The map is `{1: 3, 3: 4}`. For `nums1 = [4, 1, 2]` the answer is `[-1, 3, -1]`.

## Complexity Analysis

- **Time Complexity:** `O(m + n)` — One pass over `nums2` and one over `nums1`, where each value is pushed and popped once.
- **Space Complexity:** `O(n)` — The stack and the map.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

For each query find its position in `nums2` and scan to the right for the first larger value.

```java
public static int[] nextGreaterElementBrute(int[] nums1, int[] nums2) {
    int[] result = new int[nums1.length];
    for (int i = 0; i < nums1.length; i++) {
        int pos = 0;
        while (nums2[pos] != nums1[i]) pos++;
        result[i] = -1;
        for (int j = pos + 1; j < nums2.length; j++) {
            if (nums2[j] > nums1[i]) { result[i] = nums2[j]; break; }
        }
    }
    return result;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(1) besides the output

### Approach 2: Monotonic Stack with a Map (Optimized)

Resolve every element of `nums2` once with a decreasing stack and answer the queries from a hash map. This is the version implemented in the repository.

```java
public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
    Map<Integer, Integer> nextGreater = new HashMap<>();
    Deque<Integer> stack = new ArrayDeque<>();
    for (int x : nums2) {
        while (!stack.isEmpty() && stack.peek() < x) {
            nextGreater.put(stack.pop(), x);
        }
        stack.push(x);
    }
    int[] result = new int[nums1.length];
    for (int i = 0; i < nums1.length; i++) {
        result[i] = nextGreater.getOrDefault(nums1[i], -1);
    }
    return result;
}
```

- **Time Complexity:** O(m + n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(m * n) | O(1) besides the output |
| 2 | Monotonic Stack with a Map (Optimized) | O(m + n) | O(n) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### NextGreaterElementI.java

```java
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class NextGreaterElementI {

    public static void main(String[] args) {
        int[][][] inputs = {{{4, 1, 2}, {1, 3, 4, 2}}, {{2, 4}, {1, 2, 3, 4}}, {{1}, {1}}};
        for (int[][] in : inputs) {
            System.out.println("Input  : nums1 = " + Arrays.toString(in[0]) + ", nums2 = " + Arrays.toString(in[1]));
            System.out.println("Output : " + Arrays.toString(nextGreaterElement(in[0], in[1])));
            System.out.println();
        }
    }

    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> nextGreater = new HashMap<>();
        Deque<Integer> stack = new ArrayDeque<>();
        for (int x : nums2) {
            while (!stack.isEmpty() && stack.peek() < x) {
                nextGreater.put(stack.pop(), x);
            }
            stack.push(x);
        }
        int[] result = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            result[i] = nextGreater.getOrDefault(nums1[i], -1);
        }
        return result;
    }
}
```
