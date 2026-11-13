# Flood Fill

An `image` is an `m x n` grid of pixel colors. Starting at pixel `(sr, sc)`, change the color of that pixel and of every pixel connected to it 4-directionally through pixels of the same original color to `color`. Return the modified image.

## Algorithm Strategy (Depth-First Search from the Start Pixel)

1. Remember the original color of the start pixel. If it is already `color`, return immediately; otherwise the fill would revisit pixels forever, because a recolored pixel would look "unvisited".
2. Recolor the current pixel, then visit its four neighbours (up, down, left, right).
3. A neighbour is visited only if it is inside the grid and still has the original color. Recoloring a pixel on entry doubles as the "visited" mark.
4. The recursion ends when every connected pixel of the original color has been recolored.

## Example walkthrough

- For `[[1, 1, 1], [1, 1, 0], [1, 0, 1]]`, `(sr, sc) = (1, 1)`, `color = 2`: the connected region of 1s is the five pixels around the center.
- Result: `[[2, 2, 2], [2, 2, 0], [2, 0, 1]]`; the bottom-right 1 is not connected 4-directionally, so it keeps its color.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — Each pixel is recolored at most once.
- **Space Complexity:** `O(m * n)` — Recursion depth in the worst case (a snake-shaped region).

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Breadth-First Search with a Queue

Use a queue instead of recursion, which avoids deep call stacks on large regions.

```java
public static int[][] floodFillBfs(int[][] image, int sr, int sc, int color) {
    int original = image[sr][sc];
    if (original == color) return image;
    Queue<int[]> queue = new LinkedList<>();
    image[sr][sc] = color;
    queue.add(new int[]{sr, sc});
    int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    while (!queue.isEmpty()) {
        int[] cell = queue.remove();
        for (int[] d : dirs) {
            int r = cell[0] + d[0], c = cell[1] + d[1];
            if (r >= 0 && c >= 0 && r < image.length && c < image[0].length && image[r][c] == original) {
                image[r][c] = color;
                queue.add(new int[]{r, c});
            }
        }
    }
    return image;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(min(m, n)) for the frontier in a grid, up to O(m * n)

### Approach 2: Recursive DFS (Optimized)

Recolor the pixel and recurse into the four neighbours that still have the original color. This is the version implemented in the repository.

```java
public static int[][] floodFill(int[][] image, int sr, int sc, int color) {
    int original = image[sr][sc];
    if (original != color) {
        fill(image, sr, sc, original, color);
    }
    return image;
}

private static void fill(int[][] image, int r, int c, int original, int color) {
    if (r < 0 || c < 0 || r >= image.length || c >= image[0].length || image[r][c] != original) {
        return;
    }
    image[r][c] = color;
    fill(image, r + 1, c, original, color);
    fill(image, r - 1, c, original, color);
    fill(image, r, c + 1, original, color);
    fill(image, r, c - 1, original, color);
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n) recursion in the worst case

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Breadth-First Search with a Queue | O(m * n) | O(min(m, n)) for the frontier in a grid, up to O(m * n) |
| 2 | Recursive DFS (Optimized) | O(m * n) | O(m * n) recursion in the worst case |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### FloodFill.java

```java
import java.util.Arrays;

public class FloodFill {

    public static void main(String[] args) {
        int[][] image = {{1, 1, 1}, {1, 1, 0}, {1, 0, 1}};
        System.out.println("Input  : image = " + Arrays.deepToString(image) + ", sr = 1, sc = 1, color = 2");
        System.out.println("Output : " + Arrays.deepToString(floodFill(image, 1, 1, 2)));
        System.out.println();

        int[][] same = {{0, 0, 0}, {0, 0, 0}};
        System.out.println("Input  : image = " + Arrays.deepToString(same) + ", sr = 0, sc = 0, color = 0");
        System.out.println("Output : " + Arrays.deepToString(floodFill(same, 0, 0, 0)));
    }

    public static int[][] floodFill(int[][] image, int sr, int sc, int color) {
        int original = image[sr][sc];
        if (original != color) {
            fill(image, sr, sc, original, color);
        }
        return image;
    }

    private static void fill(int[][] image, int r, int c, int original, int color) {
        if (r < 0 || c < 0 || r >= image.length || c >= image[0].length || image[r][c] != original) {
            return;
        }
        image[r][c] = color;
        fill(image, r + 1, c, original, color);
        fill(image, r - 1, c, original, color);
        fill(image, r, c + 1, original, color);
        fill(image, r, c - 1, original, color);
    }
}
```
