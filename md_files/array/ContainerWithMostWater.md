# Container With Most Water

Given an array `height` where `height[i]` is the height of a vertical line at position `i`, find two lines that together with the x-axis hold the most water, and return that amount.

## Algorithm Strategy (Two Pointers from Both Ends)

1. The water held by lines `i < j` is `(j - i) * min(height[i], height[j])`.
2. Start with the widest container: `left = 0`, `right = n - 1`, and record its area.
3. Move the pointer at the shorter line inward. Moving the taller one can never help, because the width shrinks and the height is still limited by the shorter line.
4. Repeat until the pointers meet and return the largest area seen.

## Example walkthrough

- For `[1, 8, 6, 2, 5, 4, 8, 3, 7]`: the first area is `8 * min(1, 7) = 8`; moving past the short 1 eventually pairs the 8 at index 1 with the 7 at index 8.
- That container has area `7 * min(8, 7) = 49`, the maximum.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each pointer moves inward at most `n` times in total.
- **Space Complexity:** `O(1)` — Two pointers and the best area.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

Compute the area of every pair of lines.

```java
public static int maxAreaBrute(int[] height) {
    int best = 0;
    for (int i = 0; i < height.length; i++) {
        for (int j = i + 1; j < height.length; j++) {
            best = Math.max(best, (j - i) * Math.min(height[i], height[j]));
        }
    }
    return best;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Sort the Lines by Height

Process the lines from the tallest to the shortest. When a line is the shorter wall of a container, its best partner is the farthest of all taller-or-equal lines, so only the smallest and the largest index among the lines already processed have to be remembered.

```java
public static int maxAreaSorted(int[] height) {
    int n = height.length;
    Integer[] order = new Integer[n];
    for (int i = 0; i < n; i++) order[i] = i;
    Arrays.sort(order, (a, b) -> height[b] - height[a]);        // tallest first

    int minIndex = order[0], maxIndex = order[0], best = 0;
    for (int k = 1; k < n; k++) {
        int i = order[k];
        best = Math.max(best, height[i] * Math.max(Math.abs(i - minIndex), Math.abs(i - maxIndex)));
        minIndex = Math.min(minIndex, i);
        maxIndex = Math.max(maxIndex, i);
    }
    return best;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 3: Candidate Walls (Prefix and Suffix Maxima)

A line can only be the optimal left wall if it is taller than every line before it, and the optimal right wall only if it is taller than every line after it (otherwise a line further out is at least as good). Check only the pairs of these candidates.

```java
public static int maxAreaCandidates(int[] height) {
    int n = height.length;
    List<Integer> lefts = new ArrayList<>(), rights = new ArrayList<>();
    int max = -1;
    for (int i = 0; i < n; i++) if (height[i] > max) { max = height[i]; lefts.add(i); }
    max = -1;
    for (int i = n - 1; i >= 0; i--) if (height[i] > max) { max = height[i]; rights.add(i); }

    int best = 0;
    for (int l : lefts) {
        for (int r : rights) {
            if (r <= l) break;                    // rights are in decreasing index order
            best = Math.max(best, (r - l) * Math.min(height[l], height[r]));
        }
    }
    return best;
}
```

- **Time Complexity:** O(n) typically, O(n^2) in the worst case
- **Space Complexity:** O(n)

### Approach 4: Two Pointers with Skipping

The same two-pointer sweep, but after evaluating a pair it also skips every following line that is not taller than the shorter wall just used (those lines can only give smaller containers). Fewer area computations on noisy data.

```java
public static int maxAreaSkipping(int[] height) {
    int left = 0, right = height.length - 1, best = 0;
    while (left < right) {
        int h = Math.min(height[left], height[right]);
        best = Math.max(best, (right - left) * h);
        while (left < right && height[left] <= h) left++;
        while (left < right && height[right] <= h) right--;
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 5: Two Pointers (Optimized)

Start with the widest container and always move the pointer at the shorter line inwards, since the taller line can only help with a shorter line replaced. This is the version implemented in the repository.

```java
public static int maxArea(int[] height) {
    int left = 0, right = height.length - 1, best = 0;
    while (left < right) {
        best = Math.max(best, (right - left) * Math.min(height[left], height[right]));
        if (height[left] < height[right]) left++; else right--;
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) |
| 2 | Sort the Lines by Height | O(n log n) | O(n) |
| 3 | Candidate Walls (Prefix and Suffix Maxima) | O(n) typically, O(n^2) in the worst case | O(n) |
| 4 | Two Pointers with Skipping | O(n) | O(1) |
| 5 | Two Pointers (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 5 above, which is also the version implemented in this repository.

## Source Code

### ContainerWithMostWater.java

```java
import java.util.Arrays;

public class ContainerWithMostWater {

    public static void main(String[] args) {
        int[][] inputs = {{1, 8, 6, 2, 5, 4, 8, 3, 7}, {1, 1}};
        for (int[] height : inputs) {
            System.out.println("Input  : height = " + Arrays.toString(height));
            System.out.println("Output : " + maxArea(height));
            System.out.println();
        }
    }

    public static int maxArea(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }

        int left = 0;
        int right = height.length - 1;
        int best = 0;
        while (left < right) {
            int area = (right - left) * Math.min(height[left], height[right]);
            best = Math.max(best, area);
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return best;
    }
}
```
