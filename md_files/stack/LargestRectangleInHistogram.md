# Largest Rectangle in Histogram

Given an array `heights` representing the bar heights of a histogram where every bar has width 1, return the area of the largest rectangle in the histogram.

## Algorithm Strategy (Monotonic Increasing Stack of Indices)

1. Keep a stack of bar indices whose heights are increasing. A bar stays on the stack while the rectangle it starts can still grow to the right.
2. Process the bars from left to right (plus a virtual bar of height 0 at the end). When the current bar is lower than the bar on top, that top bar cannot extend any further: pop it.
3. The popped bar of height `h` extends to the left until the new top of the stack (exclusive) and to the right until the current index (exclusive), so its width is `i - newTop - 1` (or `i` if the stack is empty). Its best rectangle is `h * width`.
4. Push the current index and keep the largest area found.

## Example walkthrough

- For `[2, 1, 5, 6, 2, 3]`: when the bar of height 2 arrives, the bars 6 and 5 are popped giving areas `6 * 1 = 6` and `5 * 2 = 10`.
- The maximum area is `10`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each bar is pushed and popped once.
- **Space Complexity:** `O(n)` — The stack.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force (Expand from Every Bar)

For every bar, extend to the left and to the right while the neighbours are at least as tall; the area is the bar's height times the width reached.

```java
public static int largestRectangleAreaBrute(int[] heights) {
    int best = 0;
    for (int i = 0; i < heights.length; i++) {
        int left = i, right = i;
        while (left > 0 && heights[left - 1] >= heights[i]) left--;
        while (right < heights.length - 1 && heights[right + 1] >= heights[i]) right++;
        best = Math.max(best, heights[i] * (right - left + 1));
    }
    return best;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Divide and Conquer on the Lowest Bar

The widest rectangle that uses the lowest bar spans the whole range. The best rectangle is either that one, or lies entirely left or entirely right of the lowest bar, so recurse on both sides. Degenerates to quadratic on sorted input.

```java
public static int largestRectangleAreaDivide(int[] heights) {
    return solve(heights, 0, heights.length - 1);
}

private static int solve(int[] heights, int low, int high) {
    if (low > high) return 0;
    int minIndex = low;
    for (int i = low + 1; i <= high; i++) if (heights[i] < heights[minIndex]) minIndex = i;
    int whole = heights[minIndex] * (high - low + 1);
    return Math.max(whole, Math.max(solve(heights, low, minIndex - 1), solve(heights, minIndex + 1, high)));
}
```

- **Time Complexity:** O(n log n) average, O(n^2) worst
- **Space Complexity:** O(log n) average

### Approach 3: Nearest Smaller Bars on Both Sides

For each bar find the nearest smaller bar to its left and to its right (computed with jumps over the already known answers). The bar is the lowest in the range between them, so its best rectangle is `height * (right - left - 1)`.

```java
public static int largestRectangleAreaSmaller(int[] heights) {
    int n = heights.length;
    int[] left = new int[n], right = new int[n];
    for (int i = 0; i < n; i++) {
        int j = i - 1;
        while (j >= 0 && heights[j] >= heights[i]) j = left[j];
        left[i] = j;
    }
    for (int i = n - 1; i >= 0; i--) {
        int j = i + 1;
        while (j < n && heights[j] >= heights[i]) j = right[j];
        right[i] = j;
    }
    int best = 0;
    for (int i = 0; i < n; i++) best = Math.max(best, heights[i] * (right[i] - left[i] - 1));
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Single Monotonic Stack (Optimized)

Keep bars with increasing heights on a stack; a lower bar pops the taller ones and finishes their rectangles at that moment. One pass. This is the version implemented in the repository.

```java
public static int largestRectangleArea(int[] heights) {
    Deque<Integer> stack = new ArrayDeque<>();
    int best = 0;
    for (int i = 0; i <= heights.length; i++) {
        int current = (i == heights.length) ? 0 : heights[i];
        while (!stack.isEmpty() && current < heights[stack.peek()]) {
            int height = heights[stack.pop()];
            int width = stack.isEmpty() ? i : i - stack.peek() - 1;
            best = Math.max(best, height * width);
        }
        stack.push(i);
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force (Expand from Every Bar) | O(n^2) | O(1) |
| 2 | Divide and Conquer on the Lowest Bar | O(n log n) average, O(n^2) worst | O(log n) average |
| 3 | Nearest Smaller Bars on Both Sides | O(n) | O(n) |
| 4 | Single Monotonic Stack (Optimized) | O(n) | O(n) |

## Source Code

### LargestRectangleInHistogram.java

```java
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class LargestRectangleInHistogram {

    public static void main(String[] args) {
        int[][] inputs = {{2, 1, 5, 6, 2, 3}, {2, 4}, {1}, {6, 2, 5, 4, 5, 1, 6}, {}};
        for (int[] heights : inputs) {
            System.out.println("Input  : heights = " + Arrays.toString(heights));
            System.out.println("Output : " + largestRectangleArea(heights));
            System.out.println();
        }
    }

    public static int largestRectangleArea(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        int best = 0;
        for (int i = 0; i <= heights.length; i++) {
            int current = (i == heights.length) ? 0 : heights[i];       // virtual bar of height 0 flushes the stack
            while (!stack.isEmpty() && current < heights[stack.peek()]) {
                int height = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                best = Math.max(best, height * width);
            }
            stack.push(i);
        }
        return best;
    }
}
```

## Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

The idea: for each bar, the widest rectangle using it as the lowest bar stretches left and right until a lower bar. A stack of indexes with increasing heights finds both limits at once: when a bar lower than the top arrives, the top bar's right limit is the current index `i`, and its left limit is the new top below it (or the start of the array). So the popped bar of height `h` gets width `i - below - 1` (or `i` if nothing is below). A virtual bar of height 0 at the end empties the stack. Each index is pushed and popped once: O(n).

**Input:** `heights = [2, 1, 5, 6, 2, 3]`. The stack holds indexes, bottom to top.

| i | Current height | Pops (height x width = area) | Stack after push | best |
|---|----------------|------------------------------|------------------|------|
| 0 | 2 | none | 0 | 0 |
| 1 | 1 | bar 0: 2 x 1 = 2 (stack empty, width = i = 1) | 1 | 2 |
| 2 | 5 | none | 1 2 | 2 |
| 3 | 6 | none | 1 2 3 | 2 |
| 4 | 2 | bar 3: 6 x (4-2-1 = 1) = 6; bar 2: 5 x (4-1-1 = 2) = 10 | 1 4 | 10 |
| 5 | 3 | none | 1 4 5 | 10 |
| 6 | 0 (virtual) | bar 5: 3 x (6-4-1 = 1) = 3; bar 4: 2 x (6-1-1 = 4) = 8; bar 1: 1 x 6 = 6 | 6 | 10 |

```text
6 |       #
5 |     # #
4 |     # #
3 |     # #   #
2 | #   # # # #
1 | # # # # # #
  +-------------
    2 1 5 6 2 3      best rectangle: the bars 5 and 6, height 5 x width 2 = 10
```

**Edge cases.** An empty array has only the virtual bar (`i = 0`), nothing to pop, and returns `0`. A single bar `[1]` is popped by the virtual bar with width 1, giving `1`. Equal heights do not pop each other (strict `<`), which is fine because the later bar still computes the full width. The virtual zero guarantees no bar is left on the stack unprocessed.

**Result:** `10`, matching the class output.
