# Rectangle Area

Given the coordinates of two axis-aligned rectangles in a 2D plane, return the total area covered by the two rectangles. The first rectangle has bottom-left `(ax1, ay1)` and top-right `(ax2, ay2)`; the second has bottom-left `(bx1, by1)` and top-right `(bx2, by2)`.

## Algorithm Strategy (Add Both Areas, Subtract the Overlap)

1. Compute the area of each rectangle.
2. The overlap width is `min(ax2, bx2) - max(ax1, bx1)` and the overlap height is `min(ay2, by2) - max(ay1, by1)`; if either is not positive, the rectangles do not overlap and the overlap area is 0.
3. Return `areaA + areaB - overlap`.
4. Use `long` for the intermediate values to avoid overflow on large coordinates, then cast.

## Example walkthrough

- For `A = (-3, 0, 3, 4)` and `B = (0, -1, 9, 2)`: areas 24 and 27; the overlap is `3 * 2 = 6`.
- Result: `24 + 27 - 6 = 45`.

## Complexity Analysis

- **Time Complexity:** `O(1)` — A fixed number of arithmetic operations.
- **Space Complexity:** `O(1)` — A few variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Mark Unit Cells

Mark every unit cell of both rectangles in a set and count them. Only works for small coordinates.

```java
public static int cells(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) { java.util.Set<Long> s = new java.util.HashSet<>(); for (int x = ax1; x < ax2; x++) for (int y = ay1; y < ay2; y++) s.add(x * 1000L + y); for (int x = bx1; x < bx2; x++) for (int y = by1; y < by2; y++) s.add(x * 1000L + y); return s.size(); }
```

- **Time Complexity:** O(area)
- **Space Complexity:** O(area)

### Approach 2: Inclusion-Exclusion (Optimized)

Add both areas and subtract the overlap. This is the version implemented in the repository.

```java
public static int formula(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) { long a = (long) (ax2 - ax1) * (ay2 - ay1), b = (long) (bx2 - bx1) * (by2 - by1); long w = Math.min((long) ax2, bx2) - Math.max((long) ax1, bx1), h = Math.min((long) ay2, by2) - Math.max((long) ay1, by1); return (int) (a + b - (w > 0 && h > 0 ? w * h : 0)); }
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Mark Unit Cells | O(area) | O(area) |
| 2 | Inclusion-Exclusion (Optimized) | O(1) | O(1) |

## Source Code

### RectangleArea.java

```java
public class RectangleArea {

    public static void main(String[] args) {
        int[][] inputs = {{-3, 0, 3, 4, 0, -1, 9, 2}, {-2, -2, 2, 2, -2, -2, 2, 2}, {0, 0, 1, 1, 5, 5, 6, 6}, {0, 0, 2, 2, 2, 0, 4, 2}};
        for (int[] in : inputs) {
            System.out.println("Input  : A = (" + in[0] + ", " + in[1] + ", " + in[2] + ", " + in[3] + "), B = (" + in[4] + ", " + in[5] + ", " + in[6] + ", " + in[7] + ")");
            System.out.println("Output : " + computeArea(in[0], in[1], in[2], in[3], in[4], in[5], in[6], in[7]));
            System.out.println();
        }
    }

    public static int computeArea(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) {
        long areaA = (long) (ax2 - ax1) * (ay2 - ay1);
        long areaB = (long) (bx2 - bx1) * (by2 - by1);
        long width = Math.min((long) ax2, bx2) - Math.max((long) ax1, bx1);
        long height = Math.min((long) ay2, by2) - Math.max((long) ay1, by1);
        long overlap = width > 0 && height > 0 ? width * height : 0;
        return (int) (areaA + areaB - overlap);
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: adding the two areas counts the overlapping region twice, so subtract it once. The overlap of two axis-aligned rectangles is itself a rectangle: its left edge is the larger of the two left edges, its right edge the smaller of the two right edges, and similarly for the top and bottom. If the width or height comes out zero or negative, there is no overlap.

**Input:** `A = (-3, 0, 3, 4)`, `B = (0, -1, 9, 2)`.

| Quantity | Value |
|----------|-------|
| area of A | 6 * 4 = 24 |
| area of B | 9 * 3 = 27 |
| overlap width | min(3, 9) - max(-3, 0) = 3 |
| overlap height | min(4, 2) - max(0, -1) = 2 |
| overlap | 6 |

**Output:** `24 + 27 - 6 = 45`.
