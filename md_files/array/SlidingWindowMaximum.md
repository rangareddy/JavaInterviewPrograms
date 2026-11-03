# Sliding Window Maximum

Given an integer array `nums` and a window size `k`, the window slides from the far left to the far right one position at a time. Return the maximum of every window.

## Algorithm Strategy (Monotonic Decreasing Deque of Indices)

1. Keep a deque of indices whose values are in decreasing order; the front always holds the index of the maximum of the current window.
2. For each new index `i`, remove indices from the back while their values are not larger than `nums[i]` (they can never be a maximum again), then add `i` at the back.
3. Remove the front index when it has slid out of the window (`front <= i - k`).
4. Once the first window is complete (`i >= k - 1`), the value at the front is the maximum of the window; record it.

## Example walkthrough

- For `[1, 3, -1, -3, 5, 3, 6, 7]` and `k = 3`: the deque holds the indices of 3, -1 (after the first three elements), so the first maximum is 3.
- Result: `[3, 3, 5, 5, 6, 7]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each index enters and leaves the deque at most once.
- **Space Complexity:** `O(k)` — The deque holds at most `k` indices.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

Scan every window of `k` elements for its maximum.

```java
public static int[] maxSlidingWindowBrute(int[] nums, int k) {
    int[] result = new int[nums.length - k + 1];
    for (int i = 0; i + k <= nums.length; i++) {
        int max = nums[i];
        for (int j = i + 1; j < i + k; j++) max = Math.max(max, nums[j]);
        result[i] = max;
    }
    return result;
}
```

- **Time Complexity:** O(n * k)
- **Space Complexity:** O(1) besides the output

### Approach 2: Max-Heap with Lazy Deletion

Keep `(value, index)` pairs in a max-heap. The top may have slid out of the window; such entries are discarded only when they reach the top, instead of being removed eagerly.

```java
public static int[] maxSlidingWindowHeap(int[] nums, int k) {
    PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> b[0] - a[0]);
    int[] result = new int[nums.length - k + 1];
    for (int i = 0; i < nums.length; i++) {
        heap.add(new int[]{nums[i], i});
        if (i >= k - 1) {
            while (heap.peek()[1] <= i - k) heap.poll();
            result[i - k + 1] = heap.peek()[0];
        }
    }
    return result;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 3: Block Prefix and Suffix Maxima

Split the array into blocks of size `k`. Every window covers a suffix of one block and a prefix of the next, so its maximum is `max(suffixMax[i], prefixMax[i + k - 1])` with both arrays precomputed per block. Linear time without a deque.

```java
public static int[] maxSlidingWindowBlocks(int[] nums, int k) {
    int n = nums.length;
    int[] prefix = new int[n], suffix = new int[n];
    for (int i = 0; i < n; i++) prefix[i] = (i % k == 0) ? nums[i] : Math.max(prefix[i - 1], nums[i]);
    for (int i = n - 1; i >= 0; i--) suffix[i] = (i == n - 1 || (i + 1) % k == 0) ? nums[i] : Math.max(suffix[i + 1], nums[i]);
    int[] result = new int[n - k + 1];
    for (int i = 0; i + k <= n; i++) result[i] = Math.max(suffix[i], prefix[i + k - 1]);
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Monotonic Deque (Optimized)

Keep the indices of useful candidates in a deque with decreasing values; the front is always the maximum of the window. Each index enters and leaves once. This is the version implemented in the repository.

```java
public static int[] maxSlidingWindow(int[] nums, int k) {
    int n = nums.length;
    int[] result = new int[n - k + 1];
    Deque<Integer> deque = new ArrayDeque<>();
    for (int i = 0; i < n; i++) {
        while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) deque.pollLast();
        deque.addLast(i);
        if (deque.peekFirst() <= i - k) deque.pollFirst();
        if (i >= k - 1) result[i - k + 1] = nums[deque.peekFirst()];
    }
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(k)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n * k) | O(1) besides the output |
| 2 | Max-Heap with Lazy Deletion | O(n log n) | O(n) |
| 3 | Block Prefix and Suffix Maxima | O(n) | O(n) |
| 4 | Monotonic Deque (Optimized) | O(n) | O(k) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### SlidingWindowMaximum.java

```java
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class SlidingWindowMaximum {

    public static void main(String[] args) {
        int[][] inputs = {{1, 3, -1, -3, 5, 3, 6, 7}, {1}, {9, 11}, {4, 3, 2, 1}};
        int[] ks = {3, 1, 2, 2};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(inputs[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + Arrays.toString(maxSlidingWindow(inputs[i], ks[i])));
            System.out.println();
        }
    }

    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return new int[0];
        }

        int n = nums.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();      // indices, values in decreasing order
        for (int i = 0; i < n; i++) {
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }
            deque.addLast(i);
            if (deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        return result;
    }
}
```
