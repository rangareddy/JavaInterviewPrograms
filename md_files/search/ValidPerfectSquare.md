# Valid Perfect Square

Given a positive integer `num`, return `true` if `num` is a perfect square, otherwise `false`. Do not use any built-in library function such as `sqrt`.

## Algorithm Strategy (Binary Search for the Root)

1. If `num` is a perfect square, its root lies between 1 and `num` (and in fact at most about 46341).
2. Binary search `mid` in `[1, num]`, comparing `mid * mid` (computed as `long`) with `num`.
3. Equal means a perfect square; smaller means search the right half; larger means search the left half.
4. If the range empties, there is no such root.

## Example walkthrough

- For `16`: `mid = 8` gives 64, too big; `mid = 4` gives 16, equal.
- Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(log n)` — The search range halves each step.
- **Space Complexity:** `O(1)` — A few variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Try Every Root

Increase `i` until `i * i` reaches or passes `num`.

```java
public static boolean linear(int num) { if (num < 1) return false; for (long i = 1; i * i <= num; i++) if (i * i == num) return true; return false; }
```

- **Time Complexity:** O(sqrt n)
- **Space Complexity:** O(1)

### Approach 2: Binary Search (Optimized)

Halve the candidate range at every step. This is the version implemented in the repository.

```java
public static boolean binary(int num) { if (num < 1) return false; long lo = 1, hi = num; while (lo <= hi) { long mid = lo + (hi - lo) / 2, sq = mid * mid; if (sq == num) return true; if (sq < num) lo = mid + 1; else hi = mid - 1; } return false; }
```

- **Time Complexity:** O(log n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Try Every Root | O(sqrt n) | O(1) |
| 2 | Binary Search (Optimized) | O(log n) | O(1) |

## Source Code

### ValidPerfectSquare.java

```java
public class ValidPerfectSquare {

    public static void main(String[] args) {
        int[] inputs = {16, 14, 1, 808201, 2147395600, 2147483647};
        for (int num : inputs) {
            System.out.println("Input  : num = " + num);
            System.out.println("Output : " + isPerfectSquare(num));
            System.out.println();
        }
    }

    public static boolean isPerfectSquare(int num) {
        if (num < 1) {
            return false;
        }
        long low = 1;
        long high = num;
        while (low <= high) {
            long mid = low + (high - low) / 2;
            long square = mid * mid;
            if (square == num) {
                return true;
            }
            if (square < num) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return false;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: `mid * mid` grows with `mid`, so comparing it with `num` tells which half to keep. Using `long` avoids overflow for large inputs.

**Input:** `num = 16`.

| `low` | `high` | `mid` | `mid * mid` | Action |
|-------|--------|-------|-------------|--------|
| 1 | 16 | 8 | 64 | too big, `high = 7` |
| 1 | 7 | 4 | 16 | equal |

**Output:** `true`.
