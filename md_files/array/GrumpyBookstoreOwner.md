# Grumpy Bookstore Owner

A bookstore owner has a store open for `n` minutes. `customers[i]` customers enter at the start of minute `i`, and `grumpy[i]` is 1 if the owner is grumpy during minute `i` (the customers entering are unsatisfied) and 0 otherwise. The owner knows a technique to stay calm for `minutes` consecutive minutes, only once. Return the maximum number of satisfied customers.

## Algorithm Strategy (Base Satisfied Plus the Best Window of Grumpy Customers)

1. Count the customers that are satisfied anyway (minutes with `grumpy[i] == 0`).
2. Slide a window of `minutes` minutes and sum the customers that would be turned from unsatisfied to satisfied (minutes with `grumpy[i] == 1` inside the window).
3. Keep the largest window sum.
4. Return `base + best window`.

## Example walkthrough

- For `customers = [1,0,1,2,1,1,7,5]`, `grumpy = [0,1,0,1,0,1,0,1]`, `minutes = 3`: the base is 1+1+1+7 = 10; the best window `[5, 6, 7]` adds 1 + 5.
- Result: `16`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass with a sliding window.
- **Space Complexity:** `O(1)` — A few counters.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Try Every Window Start

For each start, recompute the satisfied customers with that window calm.

```java
public static int brute(int[] c, int[] g, int m) { int best = 0; for (int s = 0; s < c.length; s++) { int t = 0; for (int i = 0; i < c.length; i++) if (g[i] == 0 || (i >= s && i < s + m)) t += c[i]; best = Math.max(best, t); } return best; }
```

- **Time Complexity:** O(n * n)
- **Space Complexity:** O(1)

### Approach 2: Base plus Sliding Window (Optimized)

Add the best window of recoverable customers to the always-satisfied ones. This is the version implemented in the repository.

```java
public static int window(int[] c, int[] g, int m) { int base = 0, w = 0, best = 0; for (int i = 0; i < c.length; i++) { if (g[i] == 0) base += c[i]; else w += c[i]; if (i >= m && g[i - m] == 1) w -= c[i - m]; best = Math.max(best, w); } return base + best; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Try Every Window Start | O(n * n) | O(1) |
| 2 | Base plus Sliding Window (Optimized) | O(n) | O(1) |

## Source Code

### GrumpyBookstoreOwner.java

```java
import java.util.Arrays;

public class GrumpyBookstoreOwner {

    public static void main(String[] args) {
        int[][] customers = {{1, 0, 1, 2, 1, 1, 7, 5}, {1}, {4, 10, 10}};
        int[][] grumpy = {{0, 1, 0, 1, 0, 1, 0, 1}, {0}, {1, 1, 0}};
        int[] minutes = {3, 1, 2};
        for (int i = 0; i < customers.length; i++) {
            System.out.println("Input  : customers = " + Arrays.toString(customers[i]) + ", grumpy = " + Arrays.toString(grumpy[i]) + ", minutes = " + minutes[i]);
            System.out.println("Output : " + maxSatisfied(customers[i], grumpy[i], minutes[i]));
            System.out.println();
        }
    }

    public static int maxSatisfied(int[] customers, int[] grumpy, int minutes) {
        if (customers == null || grumpy == null || customers.length == 0) {
            return 0;
        }
        int base = 0;
        int window = 0;
        int bestWindow = 0;
        for (int i = 0; i < customers.length; i++) {
            if (grumpy[i] == 0) {
                base += customers[i];
            } else {
                window += customers[i];
            }
            if (i >= minutes && grumpy[i - minutes] == 1) {
                window -= customers[i - minutes];
            }
            bestWindow = Math.max(bestWindow, window);
        }
        return base + bestWindow;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the customers in calm minutes are satisfied no matter what, and the technique can only help in grumpy minutes. So the only decision is which window of `minutes` consecutive minutes recovers the most grumpy customers, a classic fixed-size sliding window.

**Input:** `customers = [1, 0, 1, 2, 1, 1, 7, 5]`, `grumpy = [0, 1, 0, 1, 0, 1, 0, 1]`, `minutes = 3`.

| Quantity | Value |
|----------|-------|
| satisfied anyway (calm minutes) | 1 + 1 + 1 + 7 = 10 |
| best window of 3 minutes (minutes 5 to 7) | 1 + 0 + 5 = 6 |

**Output:** `10 + 6 = 16`.
