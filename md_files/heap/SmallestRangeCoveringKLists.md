# Smallest Range Covering Elements from K Lists

Given `k` lists of integers, each sorted in non-decreasing order, find the smallest range `[a, b]` that includes at least one number from each of the `k` lists. A range `[a, b]` is smaller than `[c, d]` if `b - a < d - c`, or `a < c` when the lengths are equal.

## Algorithm Strategy (Min-Heap of the Current Front of Each List)

1. Take the first element of every list. The range that covers them all is `[min, max]` where `max` is tracked separately and `min` is the top of a min-heap.
2. Record the current range if it is smaller than the best so far (strictly smaller, so the earliest start wins a tie).
3. Pop the smallest element: this list is the one that limits the range from the left. Advance in that list to its next element, push it and update `max` if needed. If the list has no next element, stop, because a range can no longer cover all lists.
4. Repeat. Each step moves the left boundary to the next-smallest candidate and every window of `k` elements (one from each list) is considered.

## Example walkthrough

- For `[[4, 10, 15, 24, 26], [0, 9, 12, 20], [5, 18, 22, 30]]`: the first window is `4, 0, 5` with range `[0, 5]`, length 5.
- The window `24, 20, 22` gives `[20, 24]` with length 4, the smallest range found. Result: `[20, 24]`.

## Complexity Analysis

- **Time Complexity:** `O(N log k)` — `N` is the total number of elements; each is pushed and popped once from a heap of size `k`.
- **Space Complexity:** `O(k)` — The heap.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Merge All Elements, Then Sliding Window

Put every `(value, list index)` pair in one array sorted by value, then slide a window over it that contains at least one element of each list, shrinking from the left whenever possible.

```java
public static int[] smallestRangeWindow(List<List<Integer>> nums) {
    List<int[]> all = new ArrayList<>();
    for (int i = 0; i < nums.size(); i++) {
        for (int v : nums.get(i)) all.add(new int[]{v, i});
    }
    all.sort((a, b) -> Integer.compare(a[0], b[0]));
    int k = nums.size(), covered = 0, left = 0;
    int[] counts = new int[k];
    int[] best = {all.get(0)[0], all.get(all.size() - 1)[0]};
    for (int right = 0; right < all.size(); right++) {
        if (counts[all.get(right)[1]]++ == 0) covered++;
        while (covered == k) {
            int lo = all.get(left)[0], hi = all.get(right)[0];
            if (hi - lo < best[1] - best[0]) { best[0] = lo; best[1] = hi; }
            if (--counts[all.get(left)[1]] == 0) covered--;
            left++;
        }
    }
    return best;
}
```

- **Time Complexity:** O(N log N)
- **Space Complexity:** O(N)

### Approach 2: Min-Heap over the List Fronts (Optimized)

Keep one pointer per list in a min-heap and track the maximum, so the heap never holds more than `k` elements. This is the version implemented in the repository.

```java
public static int[] smallestRange(List<List<Integer>> nums) {
    PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < nums.size(); i++) {
        int value = nums.get(i).get(0);
        heap.add(new int[]{value, i, 0});
        max = Math.max(max, value);
    }
    int[] best = {heap.peek()[0], max};
    while (true) {
        int[] top = heap.poll();
        if (max - top[0] < best[1] - best[0]) {
            best[0] = top[0];
            best[1] = max;
        }
        List<Integer> list = nums.get(top[1]);
        if (top[2] + 1 == list.size()) {
            return best;
        }
        int next = list.get(top[2] + 1);
        heap.add(new int[]{next, top[1], top[2] + 1});
        max = Math.max(max, next);
    }
}
```

- **Time Complexity:** O(N log k)
- **Space Complexity:** O(k)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Merge All Elements, Then Sliding Window | O(N log N) | O(N) |
| 2 | Min-Heap over the List Fronts (Optimized) | O(N log k) | O(k) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### SmallestRangeCoveringKLists.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class SmallestRangeCoveringKLists {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{4, 10, 15, 24, 26}, {0, 9, 12, 20}, {5, 18, 22, 30}},
                {{1, 2, 3}, {1, 2, 3}, {1, 2, 3}},
                {{10, 10}, {11, 11}}
        };
        for (int[][] arrays : inputs) {
            List<List<Integer>> lists = new ArrayList<>();
            for (int[] a : arrays) {
                List<Integer> list = new ArrayList<>();
                for (int v : a) {
                    list.add(v);
                }
                lists.add(list);
            }
            System.out.println("Input  : nums = " + lists);
            System.out.println("Output : " + Arrays.toString(smallestRange(lists)));
            System.out.println();
        }
    }

    public static int[] smallestRange(List<List<Integer>> nums) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.size(); i++) {
            int value = nums.get(i).get(0);
            heap.add(new int[]{value, i, 0});
            max = Math.max(max, value);
        }
        int[] best = {heap.peek()[0], max};
        while (true) {
            int[] top = heap.poll();
            if (max - top[0] < best[1] - best[0]) {
                best[0] = top[0];
                best[1] = max;
            }
            List<Integer> list = nums.get(top[1]);
            if (top[2] + 1 == list.size()) {
                return best;
            }
            int next = list.get(top[2] + 1);
            heap.add(new int[]{next, top[1], top[2] + 1});
            max = Math.max(max, next);
        }
    }
}
```
