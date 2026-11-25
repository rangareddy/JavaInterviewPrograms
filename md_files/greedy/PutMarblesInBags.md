# Put Marbles in Bags

You have `k` bags and a row of marbles with weights `weights`. Divide the marbles into `k` non-empty bags such that each bag is a contiguous segment. The cost of a bag with marbles from index `i` to `j` is `weights[i] + weights[j]`. The score of a distribution is the sum of the bag costs. Return the difference between the maximum and the minimum possible scores.

## Algorithm Strategy (Sort the Cut Costs)

1. Whatever the cuts, the first and last marbles always count (`weights[0] + weights[n - 1]`), so only the `k - 1` cuts matter.
2. A cut between marbles `i` and `i + 1` adds `weights[i] + weights[i + 1]` to the score.
3. Compute all `n - 1` pair sums and sort them.
4. The maximum score picks the `k - 1` largest cuts and the minimum picks the `k - 1` smallest; the answer is their difference.

## Example walkthrough

- For `weights = [1, 3, 5, 1]` and `k = 2`: the cut costs are 4, 8 and 6.
- Maximum uses 8, minimum uses 4, so the answer is `8 - 4 = 4`.

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — Sorting the pair sums.
- **Space Complexity:** `O(n)` — The pair sums.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Try Every Distribution

Enumerate all ways to place `k - 1` cuts and track the maximum and minimum score. Exponential.

```java
public static long brute(int[] w, int k) {
    int n = w.length; long[] best = {Long.MIN_VALUE, Long.MAX_VALUE}; go(w, k - 1, 0, 0, new boolean[n - 1], best); return n < 2 || k <= 1 ? 0 : best[0] - best[1];
}
private static void go(int[] w, int left, int from, int chosen, boolean[] cut, long[] best) {
    int n = w.length; if (left == 0) { long s = w[0] + w[n - 1]; for (int i = 0; i < n - 1; i++) if (cut[i]) s += w[i] + w[i + 1]; best[0] = Math.max(best[0], s); best[1] = Math.min(best[1], s); return; }
    for (int i = from; i < n - 1; i++) { cut[i] = true; go(w, left - 1, i + 1, chosen + 1, cut, best); cut[i] = false; }
}
```

- **Time Complexity:** O(C(n, k))
- **Space Complexity:** O(n)

### Approach 2: Sort the Cut Costs (Optimized)

Take the `k - 1` largest and smallest pair sums. This is the version implemented in the repository.

```java
public static long sorted(int[] w, int k) {
    if (w.length < 2 || k <= 1) return 0; long[] c = new long[w.length - 1]; for (int i = 0; i < c.length; i++) c[i] = (long) w[i] + w[i + 1]; java.util.Arrays.sort(c); long d = 0;
    for (int i = 0; i < k - 1; i++) d += c[c.length - 1 - i] - c[i]; return d;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Try Every Distribution | O(C(n, k)) | O(n) |
| 2 | Sort the Cut Costs (Optimized) | O(n log n) | O(n) |

## Source Code

### PutMarblesInBags.java

```java
import java.util.Arrays;

public class PutMarblesInBags {

    public static void main(String[] args) {
        int[][] weights = {{1, 3, 5, 1}, {1, 3}, {1, 4, 2, 5, 2}, {7}};
        int[] ks = {2, 2, 3, 1};
        for (int i = 0; i < weights.length; i++) {
            System.out.println("Input  : weights = " + Arrays.toString(weights[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + putMarbles(weights[i], ks[i]));
            System.out.println();
        }
    }

    public static long putMarbles(int[] weights, int k) {
        if (weights == null || weights.length < 2 || k <= 1) {
            return 0;
        }
        int n = weights.length;
        long[] cuts = new long[n - 1];
        for (int i = 0; i < n - 1; i++) {
            cuts[i] = (long) weights[i] + weights[i + 1];
        }
        Arrays.sort(cuts);
        long difference = 0;
        for (int i = 0; i < k - 1; i++) {
            difference += cuts[n - 2 - i] - cuts[i];
        }
        return difference;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the cost of every bag includes its two end marbles, and each cut makes two adjacent marbles into the end of one bag and the start of the next. So the score is a fixed part (the first and last marbles) plus the sum of the chosen cuts' pair sums, and the best and worst choices are the largest and smallest `k - 1` pair sums.

**Input:** `weights = [1, 3, 5, 1]`, `k = 2`.

| Cut between | Pair sum |
|-------------|----------|
| 1 and 3 | 4 |
| 3 and 5 | 8 |
| 5 and 1 | 6 |

One cut: the maximum picks 8 and the minimum picks 4.

**Output:** `8 - 4 = 4`.
