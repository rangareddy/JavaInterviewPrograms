# Sqrt(x)

Given a non-negative integer `x`, return the integer square root of `x`: the largest integer `r` such that `r * r <= x`. Do not use a built-in exponent or square root function.

## Algorithm Strategy (Binary Search on the Root)

1. The root lies in `[0, x]`; narrowing to `[1, x / 2 + 1]` is enough for `x >= 2`.
2. Check the midpoint `mid`: compare `mid` with `x / mid` to avoid overflow in `mid * mid`.
3. If `mid <= x / mid`, `mid` is a candidate: remember it and look higher.
4. Otherwise look lower. The last remembered candidate is the answer.

## Example walkthrough

- For `x = 8`: candidates 4 (16 > 8, too high), 2 (4 <= 8, candidate), 3 (9 > 8, too high).
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(log x)` — The range halves each step.
- **Space Complexity:** `O(1)` — A few integers.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Linear Search

Increase `r` until `(r + 1)^2 > x`.

```java
public static int linear(int x) {
    long r = 0; while ((r + 1) * (r + 1) <= x) r++; return (int) r;
}
```

- **Time Complexity:** O(sqrt(x))
- **Space Complexity:** O(1)

### Approach 2: Newton's Method

Iterate `r = (r + x / r) / 2` until it stops decreasing.

```java
public static int newton(int x) {
    if (x < 2) return x;
    long r = x; while (r * r > x) r = (r + x / r) / 2; return (int) r;
}
```

- **Time Complexity:** O(log x)
- **Space Complexity:** O(1)

### Approach 3: Binary Search (Optimized)

Search the largest `mid` with `mid <= x / mid`. This is the version implemented in the repository.

```java
public static int binary(int x) {
    if (x < 2) return x;
    int lo = 1, hi = x / 2 + 1, ans = 1;
    while (lo <= hi) { int mid = lo + (hi - lo) / 2; if (mid <= x / mid) { ans = mid; lo = mid + 1; } else hi = mid - 1; }
    return ans;
}
```

- **Time Complexity:** O(log x)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Linear Search | O(sqrt(x)) | O(1) |
| 2 | Newton's Method | O(log x) | O(1) |
| 3 | Binary Search (Optimized) | O(log x) | O(1) |

## Source Code

### SqrtX.java

```java
public class SqrtX {

    public static void main(String[] args) {
        int[] inputs = {4, 8, 0, 1, 2147395599, Integer.MAX_VALUE};
        for (int x : inputs) {
            System.out.println("Input  : x = " + x);
            System.out.println("Output : " + mySqrt(x));
            System.out.println();
        }
    }

    public static int mySqrt(int x) {
        if (x < 2) {
            return x;
        }
        int lo = 1;
        int hi = x / 2 + 1;
        int answer = 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (mid <= x / mid) {
                answer = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return answer;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: `r * r <= x` is true for small `r` and false for large `r`, so a binary search finds the boundary. Comparing `mid <= x / mid` avoids the overflow of `mid * mid`.

**Input:** `x = 8`. Range `[1, 5]`.

| `lo` | `hi` | `mid` | `mid <= 8 / mid`? | `answer` |
|------|------|-------|--------------------|----------|
| 1 | 5 | 3 | 3 <= 2 no | 1 |
| 1 | 2 | 1 | 1 <= 8 yes | 1 |
| 2 | 2 | 2 | 2 <= 4 yes | 2 |
| 3 | 2 | | stop | 2 |

**Output:** `2`.
