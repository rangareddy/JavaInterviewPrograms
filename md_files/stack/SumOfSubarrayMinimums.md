# Sum of Subarray Minimums

Given an array of integers `arr`, find the sum of `min(b)` over every contiguous subarray `b` of `arr`. Because the answer may be large, return it modulo `10^9 + 7`.

## Algorithm Strategy (Contribution of Each Element as the Minimum)

1. Each element `arr[i]` is the minimum of a number of subarrays. Count them as `left * right`.
2. `left` is the number of positions to the left until a strictly smaller element (so the subarray can start there) and `right` is the number to the right until a smaller-or-equal element, which avoids counting ties twice.
3. Find both distances with one monotonic stack pass each.
4. The answer is `sum(arr[i] * left * right) mod (10^9 + 7)`.

## Example walkthrough

- For `[3, 1, 2, 4]`: the element 1 is the minimum of 6 subarrays, 3 of 1 subarray, 2 of 2 subarrays, and 4 of 1.
- Total: `3 + 6 + 4 + 4 = 17`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each element is pushed and popped once.
- **Space Complexity:** `O(n)` — The stack and the distances.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force over Subarrays

Keep the running minimum of every subarray.

```java
public static int bruteForce(int[] a) {
    long s = 0; for (int i = 0; i < a.length; i++) { int mn = Integer.MAX_VALUE; for (int j = i; j < a.length; j++) { mn = Math.min(mn, a[j]); s += mn; } } return (int) (s % 1_000_000_007L);
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Monotonic Stack with Contributions (Optimized)

Count how many subarrays each element is the minimum of. This is the version implemented in the repository.

```java
public static int stack(int[] a) {
    int n = a.length; int[] l = new int[n], r = new int[n]; java.util.Deque<Integer> st = new java.util.ArrayDeque<>();
    for (int i = 0; i < n; i++) { while (!st.isEmpty() && a[st.peek()] > a[i]) st.pop(); l[i] = st.isEmpty() ? i + 1 : i - st.peek(); st.push(i); }
    st.clear(); for (int i = n - 1; i >= 0; i--) { while (!st.isEmpty() && a[st.peek()] >= a[i]) st.pop(); r[i] = st.isEmpty() ? n - i : st.peek() - i; st.push(i); }
    long s = 0; for (int i = 0; i < n; i++) s = (s + (long) a[i] * l[i] * r[i]) % 1_000_000_007L; return (int) s;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force over Subarrays | O(n^2) | O(1) |
| 2 | Monotonic Stack with Contributions (Optimized) | O(n) | O(n) |

## Source Code

### SumOfSubarrayMinimums.java

```java
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class SumOfSubarrayMinimums {

    public static void main(String[] args) {
        int[][] inputs = {{3, 1, 2, 4}, {11, 81, 94, 43, 3}, {1}, {2, 2, 2}};
        for (int[] arr : inputs) {
            System.out.println("Input  : arr = " + Arrays.toString(arr));
            System.out.println("Output : " + sumSubarrayMins(arr));
            System.out.println();
        }
    }

    private static final int MOD = 1_000_000_007;

    public static int sumSubarrayMins(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int n = arr.length;
        int[] left = new int[n];
        int[] right = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                stack.pop();
            }
            left[i] = stack.isEmpty() ? i + 1 : i - stack.peek();
            stack.push(i);
        }
        stack.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            right[i] = stack.isEmpty() ? n - i : stack.peek() - i;
            stack.push(i);
        }
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum = (sum + (long) arr[i] * left[i] * right[i]) % MOD;
        }
        return (int) sum;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: instead of looking at every subarray, ask how many subarrays each element is the minimum of. If it can extend `left` positions to the left and `right` positions to the right while staying the minimum, then `left * right` subarrays have it as their minimum.

**Input:** `arr = [3, 1, 2, 4]`.

| Element | `left` | `right` | Subarrays | Contribution |
|---------|--------|---------|-----------|--------------|
| 3 | 1 | 1 | 1 | 3 |
| 1 | 2 | 3 | 6 | 6 |
| 2 | 1 | 2 | 2 | 4 |
| 4 | 1 | 1 | 1 | 4 |

**Output:** `3 + 6 + 4 + 4 = 17`.
