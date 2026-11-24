# Pascal's Triangle

Given an integer `numRows`, return the first `numRows` rows of Pascal's triangle. Each number is the sum of the two numbers directly above it, and each row starts and ends with 1.

## Algorithm Strategy (Row from the Previous Row)

1. Row 0 is `[1]`.
2. A new row has one more element than the previous one: start with 1.
3. Each inner element is `prev[j - 1] + prev[j]`.
4. End the row with 1 and add it to the result.

## Example walkthrough

- From row `[1, 2, 1]`: the next row is `1`, `1 + 2 = 3`, `2 + 1 = 3`, `1`.
- Result: `[1, 3, 3, 1]`.

## Complexity Analysis

- **Time Complexity:** `O(numRows^2)` — The triangle has about numRows^2 / 2 numbers.
- **Space Complexity:** `O(numRows^2)` — The output.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Binomial Coefficients

Each entry is `C(i, j)`, computed incrementally with `C(i, j) = C(i, j - 1) * (i - j + 1) / j`.

```java
public static java.util.List<java.util.List<Integer>> binomial(int n) {
    java.util.List<java.util.List<Integer>> out = new java.util.ArrayList<>();
    for (int i = 0; i < n; i++) {
        java.util.List<Integer> row = new java.util.ArrayList<>(); long c = 1;
        for (int j = 0; j <= i; j++) { row.add((int) c); c = c * (i - j) / (j + 1); }
        out.add(row);
    }
    return out;
}
```

- **Time Complexity:** O(numRows^2)
- **Space Complexity:** O(numRows^2)

### Approach 2: Sum of the Row Above (Optimized)

Build each row from the previous one. This is the version implemented in the repository.

```java
public static java.util.List<java.util.List<Integer>> fromPrevious(int n) {
    java.util.List<java.util.List<Integer>> t = new java.util.ArrayList<>();
    for (int i = 0; i < n; i++) {
        java.util.List<Integer> row = new java.util.ArrayList<>(); row.add(1);
        if (i > 0) { java.util.List<Integer> p = t.get(i - 1); for (int j = 1; j < i; j++) row.add(p.get(j - 1) + p.get(j)); row.add(1); }
        t.add(row);
    }
    return t;
}
```

- **Time Complexity:** O(numRows^2)
- **Space Complexity:** O(numRows^2)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Binomial Coefficients | O(numRows^2) | O(numRows^2) |
| 2 | Sum of the Row Above (Optimized) | O(numRows^2) | O(numRows^2) |

## Source Code

### PascalsTriangle.java

```java
import java.util.ArrayList;
import java.util.List;

public class PascalsTriangle {

    public static void main(String[] args) {
        int[] inputs = {5, 1, 0, 6};
        for (int n : inputs) {
            System.out.println("Input  : numRows = " + n);
            System.out.println("Output : " + generate(n));
            System.out.println();
        }
    }

    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> triangle = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            List<Integer> row = new ArrayList<>();
            row.add(1);
            if (i > 0) {
                List<Integer> prev = triangle.get(i - 1);
                for (int j = 1; j < i; j++) {
                    row.add(prev.get(j - 1) + prev.get(j));
                }
                row.add(1);
            }
            triangle.add(row);
        }
        return triangle;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: every inner number is the sum of the two numbers above it, so each row is derived from the one before.

**Input:** `numRows = 5`.

| Row | Built from | Values |
|-----|------------|--------|
| 0 | start | 1 |
| 1 | ends only | 1 1 |
| 2 | 1 + 1 | 1 2 1 |
| 3 | 1 + 2, 2 + 1 | 1 3 3 1 |
| 4 | 1 + 3, 3 + 3, 3 + 1 | 1 4 6 4 1 |

**Output:** `[[1], [1, 1], [1, 2, 1], [1, 3, 3, 1], [1, 4, 6, 4, 1]]`.
