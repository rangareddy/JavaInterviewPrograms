# Rotating the Box

You are given an `m x n` matrix of characters `box` representing a side-view of a box: `'#'` is a stone, `'*'` is a stationary obstacle and `'.'` is empty. The box is rotated 90 degrees clockwise, causing some stones to fall to the right (before the rotation, in the row's frame) until they hit an obstacle, another stone or the bottom. Return the `n x m` matrix of the box after the rotation, with gravity applied.

## Algorithm Strategy (Drop Stones to the Right in Each Row, Then Rotate)

1. Process each row from right to left with a pointer `empty` to the right-most free slot of the current segment.
2. On `'*'`, the obstacle ends the segment: set `empty = j - 1`. On `'#'`, move the stone to `empty` (put `'.'` where it was) and decrease `empty`.
3. After gravity is applied to each row, rotate the matrix 90 degrees clockwise: the new cell `[j][m - 1 - i]` takes the old `[i][j]`.
4. Return the rotated matrix.

## Example walkthrough

- For the row `# . *  .` stones fall right until the obstacle: the first `#` moves next to the obstacle.
- After the rotation the rows become columns read from the bottom row up.

## Complexity Analysis

- **Time Complexity:** `O(m * n)` — One pass per row and one for the rotation.
- **Space Complexity:** `O(m * n)` — The rotated matrix.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Bubble Stones One Step at a Time

Repeatedly move any stone that has an empty cell to its right, until nothing moves.

```java
public static char[][] bubble(char[][] box) {
    int R = box.length, C = box[0].length; char[][] b = new char[R][]; for (int i = 0; i < R; i++) b[i] = box[i].clone();
    for (char[] row : b) { boolean moved = true; while (moved) { moved = false; for (int j = C - 2; j >= 0; j--) if (row[j] == '#' && row[j + 1] == '.') { row[j] = '.'; row[j + 1] = '#'; moved = true; } } }
    char[][] r = new char[C][R]; for (int i = 0; i < R; i++) for (int j = 0; j < C; j++) r[j][R - 1 - i] = b[i][j]; return r;
}
```

- **Time Complexity:** O(m * n^2)
- **Space Complexity:** O(m * n)

### Approach 2: Right-to-Left Pointer then Rotate (Optimized)

Slide each stone to the free slot found by a pointer, then rotate. This is the version implemented in the repository.

```java
public static char[][] pointer(char[][] box) {
    int R = box.length, C = box[0].length; char[][] r = new char[C][R];
    for (int i = 0; i < R; i++) { int e = C - 1; char[] row = box[i].clone(); for (int j = C - 1; j >= 0; j--) { if (row[j] == '*') e = j - 1; else if (row[j] == '#') { row[j] = '.'; row[e--] = '#'; } } for (int j = 0; j < C; j++) r[j][R - 1 - i] = row[j]; }
    return r;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(m * n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Bubble Stones One Step at a Time | O(m * n^2) | O(m * n) |
| 2 | Right-to-Left Pointer then Rotate (Optimized) | O(m * n) | O(m * n) |

## Source Code

### RotatingTheBox.java

```java
import java.util.Arrays;

public class RotatingTheBox {

    public static void main(String[] args) {
        char[][][] inputs = {
            {{'#', '.', '#'}},
            {{'#', '.', '*', '.'}, {'#', '#', '*', '.'}},
            {{'#', '#', '*', '.', '*', '.'}, {'#', '#', '#', '*', '.', '.'}, {'#', '#', '#', '.', '#', '.'}}
        };
        for (char[][] box : inputs) {
            System.out.println("Input  : box = " + Arrays.deepToString(box));
            System.out.println("Output : " + Arrays.deepToString(rotateTheBox(box)));
            System.out.println();
        }
    }

    public static char[][] rotateTheBox(char[][] box) {
        if (box == null || box.length == 0) {
            return new char[0][0];
        }
        int rows = box.length;
        int cols = box[0].length;
        char[][] rotated = new char[cols][rows];
        for (int i = 0; i < rows; i++) {
            int empty = cols - 1;
            char[] row = box[i].clone();
            for (int j = cols - 1; j >= 0; j--) {
                if (row[j] == '*') {
                    empty = j - 1;
                } else if (row[j] == '#') {
                    row[j] = '.';
                    row[empty--] = '#';
                }
            }
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = row[j];
            }
        }
        return rotated;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: gravity only acts inside a row, and an obstacle splits the row into independent segments. Scanning right to left with a pointer to the right-most free slot lets every stone drop straight to its final place in one move. The rotation is then a plain index mapping.

**Input:** `box = [[#, ., #]]` (one row, three columns).

| Step | Row | Pointer `empty` |
|------|-----|-----------------|
| start | `# . #` | 2 |
| `j = 2`, stone | `# . .` then stone placed at 2: `# . #` | 1 |
| `j = 0`, stone | `. . #` then placed at 1: `. # #` | 0 |

After gravity the row is `. # #`. Rotating a 1x3 box clockwise gives a 3x1 column.

**Output:** `[[.], [#], [#]]`.
