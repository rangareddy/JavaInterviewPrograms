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

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: instead of searching `nums2` for every query value, solve "next greater element" for all of `nums2` in one pass and store the answers in a map. A stack keeps values still waiting for a greater one (decreasing from bottom to top). When a new value `x` is larger than the top, `x` is the next greater element of the top, so it is recorded in the map and popped. Then `x` is pushed. Afterwards each element of `nums1` is looked up in the map, defaulting to `-1`. O(n + m) time.

**Input:** `nums1 = [4, 1, 2]`, `nums2 = [1, 3, 4, 2]`. The stack is written bottom to top.

| Step | x (from nums2) | What happens | Stack | Map |
|------|----------------|--------------|-------|-----|
| 1 | 1 | stack empty, push | 1 | {} |
| 2 | 3 | 1 < 3: pop 1, record 1 → 3; push 3 | 3 | {1→3} |
| 3 | 4 | 3 < 4: pop 3, record 3 → 4; push 4 | 4 | {1→3, 3→4} |
| 4 | 2 | 4 < 2 is false; push 2 | 4 2 | {1→3, 3→4} |

Values left on the stack (4 and 2) have no greater element to their right, so they are not in the map.

Lookup for `nums1`: 4 → not in map → -1; 1 → 3; 2 → not in map → -1.

**Edge cases.** The code assumes the values are distinct (as the problem states), because the map is keyed by value. A value of `nums1` with nothing greater after it in `nums2` falls back to `-1`. For `nums1 = [2, 4]`, `nums2 = [1, 2, 3, 4]` the map is {1→2, 2→3, 3→4} and the answer is `[3, -1]`. Elements are only compared to the right of their position in `nums2`, which is exactly what the single left-to-right pass guarantees.

**Result:** `[-1, 3, -1]`, matching the class output.
