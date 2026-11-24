# ZigZag Conversion

Write the string `s` in a zigzag pattern on `numRows` rows, going down the rows and then diagonally up, and then read the rows one after another. Return the resulting string.

## Algorithm Strategy (Row Buckets with a Direction Flag)

1. Create one `StringBuilder` per row.
2. Walk the characters of `s`, appending each to the current row.
3. Flip the direction when the current row reaches the first or the last row.
4. Concatenate the rows. If `numRows` is 1 (or at least the string length), return `s` unchanged.

## Example walkthrough

- For `PAYPALISHIRING` with 3 rows the rows are `PAHN`, `APLSIIG` and `YIR`.
- Result: `PAHNAPLSIIGYIR`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each character is appended once.
- **Space Complexity:** `O(n)` — The rows together hold all characters.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Simulate the Grid

Place each character in a 2D grid following the zigzag, then read the grid row by row.

```java
public static String grid(String s, int numRows) {
    if (numRows <= 1 || numRows >= s.length()) return s;
    int cols = s.length(); char[][] g = new char[numRows][cols];
    int r = 0, c = 0; boolean down = true;
    for (int i = 0; i < s.length(); i++) {
        g[r][c] = s.charAt(i);
        if (down) { if (r == numRows - 1) { down = false; r--; c++; } else r++; }
        else { if (r == 0) { down = true; r++; } else { r--; c++; } }
    }
    StringBuilder sb = new StringBuilder();
    for (char[] row : g) for (char ch : row) if (ch != 0) sb.append(ch);
    return sb.toString();
}
```

- **Time Complexity:** O(n * numRows)
- **Space Complexity:** O(n * numRows)

### Approach 2: Row Buckets with a Direction Flag (Optimized)

One builder per row and a step that flips at the edges. This is the version implemented in the repository.

```java
public static String buckets(String s, int numRows) {
    if (numRows <= 1 || numRows >= s.length()) return s;
    StringBuilder[] rows = new StringBuilder[numRows];
    for (int i = 0; i < numRows; i++) rows[i] = new StringBuilder();
    int row = 0, step = 1;
    for (int i = 0; i < s.length(); i++) {
        rows[row].append(s.charAt(i));
        if (row == 0) step = 1; else if (row == numRows - 1) step = -1;
        row += step;
    }
    StringBuilder out = new StringBuilder();
    for (StringBuilder b : rows) out.append(b);
    return out.toString();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Jump by the Cycle Length

Row `r` is read at indices `r + k * (2 * numRows - 2)`, with an extra index on the diagonal.

```java
public static String cycle(String s, int numRows) {
    if (numRows <= 1 || numRows >= s.length()) return s;
    int cyc = 2 * numRows - 2; StringBuilder sb = new StringBuilder();
    for (int r = 0; r < numRows; r++)
        for (int j = r; j < s.length(); j += cyc) {
            sb.append(s.charAt(j));
            int diag = j + cyc - 2 * r;
            if (r != 0 && r != numRows - 1 && diag < s.length()) sb.append(s.charAt(diag));
        }
    return sb.toString();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Simulate the Grid | O(n * numRows) | O(n * numRows) |
| 2 | Row Buckets with a Direction Flag (Optimized) | O(n) | O(n) |
| 3 | Jump by the Cycle Length | O(n) | O(n) |

## Source Code

### ZigZagConversion.java

```java
public class ZigZagConversion {

    public static void main(String[] args) {
        String[] inputs = {"PAYPALISHIRING", "PAYPALISHIRING", "A", "AB"};
        int[] rows = {3, 4, 1, 1};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : s = \"" + inputs[i] + "\", numRows = " + rows[i]);
            System.out.println("Output : " + convert(inputs[i], rows[i]));
            System.out.println();
        }
    }

    public static String convert(String s, int numRows) {
        if (s == null || numRows <= 1 || numRows >= s.length()) {
            return s;
        }
        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = new StringBuilder();
        }
        int row = 0;
        int step = 1;
        for (int i = 0; i < s.length(); i++) {
            rows[row].append(s.charAt(i));
            if (row == 0) {
                step = 1;
            } else if (row == numRows - 1) {
                step = -1;
            }
            row += step;
        }
        StringBuilder result = new StringBuilder();
        for (StringBuilder sb : rows) {
            result.append(sb);
        }
        return result.toString();
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the zigzag only decides which row each character lands in. Rows go 0, 1, 2, ..., last, ..., 1, 0, so a single index with a direction flag finds the row for every character, and reading the rows in order gives the answer.

**Input:** `s = "PAYPALISHIRING"`, `numRows = 3`.

| Row | Characters |
|-----|------------|
| 0 | P A H N |
| 1 | A P L S I I G |
| 2 | Y I R |

```text
P   A   H   N
A P L S I I G
Y   I   R
```

**Output:** `PAHNAPLSIIGYIR`.
