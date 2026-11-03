# Trapping Rain Water

Given `n` non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it can trap after raining.

## Algorithm Strategy (Two Pointers with Running Maxima)

1. The water above a bar is `min(highest bar to its left, highest bar to its right) - height of the bar`, never negative.
2. Keep two pointers `left` and `right` at the ends and the highest bars seen so far from each side, `leftMax` and `rightMax`.
3. Always move the pointer on the side with the smaller maximum: the water above that bar is limited by that smaller maximum (the other side has an even taller wall), so it can be added immediately as `max - height`.
4. Update the maximum when a taller bar is met and continue until the pointers meet.

## Example walkthrough

- For `[4, 2, 0, 3, 2, 5]`: from the left, bars 2, 0, 3, 2 are lower than the left wall 4 and trap 2 + 4 + 1 + 2 = 9 units as long as the right wall (5) is taller.
- Result: `9`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each bar is visited once.
- **Space Complexity:** `O(1)` — A few variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

The water above a bar is `min(highest bar on its left, highest bar on its right) - height`. Compute both maxima by scanning the array for every bar.

```java
public static int trapBrute(int[] height) {
    int water = 0;
    for (int i = 0; i < height.length; i++) {
        int leftMax = 0, rightMax = 0;
        for (int j = 0; j <= i; j++) leftMax = Math.max(leftMax, height[j]);
        for (int j = i; j < height.length; j++) rightMax = Math.max(rightMax, height[j]);
        water += Math.min(leftMax, rightMax) - height[i];
    }
    return water;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Prefix and Suffix Maximum Arrays

Precompute the highest bar to the left and to the right of every position in two arrays, then add up `min(left, right) - height`.

```java
public static int trapArrays(int[] height) {
    int n = height.length;
    if (n == 0) return 0;
    int[] leftMax = new int[n], rightMax = new int[n];
    leftMax[0] = height[0];
    for (int i = 1; i < n; i++) leftMax[i] = Math.max(leftMax[i - 1], height[i]);
    rightMax[n - 1] = height[n - 1];
    for (int i = n - 2; i >= 0; i--) rightMax[i] = Math.max(rightMax[i + 1], height[i]);
    int water = 0;
    for (int i = 0; i < n; i++) water += Math.min(leftMax[i], rightMax[i]) - height[i];
    return water;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Monotonic Stack (Layer by Layer)

Keep a stack of decreasing bars. When a taller bar arrives, the bars popped from the stack form a "valley": its water is bounded by the new bar and the bar left on the stack, and is added horizontally, layer by layer.

```java
public static int trapStack(int[] height) {
    Deque<Integer> stack = new ArrayDeque<>();
    int water = 0;
    for (int i = 0; i < height.length; i++) {
        while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
            int bottom = stack.pop();
            if (stack.isEmpty()) break;
            int width = i - stack.peek() - 1;
            int boundedHeight = Math.min(height[i], height[stack.peek()]) - height[bottom];
            water += width * boundedHeight;
        }
        stack.push(i);
    }
    return water;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Two Pointers (Optimized)

Walk inwards from both ends and always advance the side with the smaller wall; its water is limited by its own running maximum. Constant space. This is the version implemented in the repository.

```java
public static int trap(int[] height) {
    int left = 0, right = height.length - 1, leftMax = 0, rightMax = 0, water = 0;
    while (left < right) {
        if (height[left] < height[right]) {
            leftMax = Math.max(leftMax, height[left]);
            water += leftMax - height[left++];
        } else {
            rightMax = Math.max(rightMax, height[right]);
            water += rightMax - height[right--];
        }
    }
    return water;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) |
| 2 | Prefix and Suffix Maximum Arrays | O(n) | O(n) |
| 3 | Monotonic Stack (Layer by Layer) | O(n) | O(n) |
| 4 | Two Pointers (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### TrappingRainWater.java

```java
import java.util.Arrays;

public class TrappingRainWater {

    public static void main(String[] args) {
        int[][] inputs = {{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}, {4, 2, 0, 3, 2, 5}, {3}, {}};
        for (int[] height : inputs) {
            System.out.println("Input  : height = " + Arrays.toString(height));
            System.out.println("Output : " + trap(height));
            System.out.println();
        }
    }

    public static int trap(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }

        int left = 0;
        int right = height.length - 1;
        int leftMax = 0;
        int rightMax = 0;
        int water = 0;
        while (left < right) {
            if (height[left] < height[right]) {
                leftMax = Math.max(leftMax, height[left]);
                water += leftMax - height[left];
                left++;
            } else {
                rightMax = Math.max(rightMax, height[right]);
                water += rightMax - height[right];
                right--;
            }
        }
        return water;
    }
}
```
