# Paint Fence

A fence has `n` posts and `k` colours. Paint every post so that no three consecutive posts share a colour. Return the number of ways to paint the fence.

## Algorithm Strategy (Same and Different Counts)

1. Track two numbers for the posts painted so far: `same`, the ways where the last two posts have the same colour, and `diff`, the ways where they differ.
2. For post 2: `same = k` and `diff = k * (k - 1)`.
3. For each later post: the new `same` is the previous `diff` (repeat the last colour), and the new `diff` is `(same + diff) * (k - 1)`.
4. The answer is `same + diff`. Handle `n = 0` as 0 and `n = 1` as `k`.

## Example walkthrough

- For `n = 3`, `k = 2`: after two posts `same = 2`, `diff = 2`.
- Third post: `same = 2`, `diff = (2 + 2) * 1 = 4`, so the total is 6.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One update per post.
- **Space Complexity:** `O(1)` — Only two counters are kept.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force Enumeration

Try every colouring and count those without three equal neighbours. Only usable for tiny inputs.

```java
public static int bruteForce(int n, int k) {
    if (n <= 0 || k <= 0) return 0;
    return count(new int[n], 0, k);
}
private static int count(int[] c, int i, int k) {
    if (i == c.length) return 1;
    int total = 0;
    for (int col = 0; col < k; col++) {
        if (i >= 2 && c[i - 1] == col && c[i - 2] == col) continue;
        c[i] = col; total += count(c, i + 1, k);
    }
    return total;
}
```

- **Time Complexity:** O(k^n)
- **Space Complexity:** O(n)

### Approach 2: Recurrence with an Array

`ways[i] = (k - 1) * (ways[i - 1] + ways[i - 2])`, stored for every post.

```java
public static int tabulated(int n, int k) {
    if (n <= 0 || k <= 0) return 0;
    if (n == 1) return k;
    int[] w = new int[n + 1];
    w[1] = k; w[2] = k * k;
    for (int i = 3; i <= n; i++) w[i] = (k - 1) * (w[i - 1] + w[i - 2]);
    return w[n];
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Two Counters (Optimized)

Keep only the `same` and `diff` counts. This is the version implemented in the repository.

```java
public static int twoCounters(int n, int k) {
    if (n <= 0 || k <= 0) return 0;
    if (n == 1) return k;
    int same = k, diff = k * (k - 1);
    for (int i = 3; i <= n; i++) { int s = diff; diff = (same + diff) * (k - 1); same = s; }
    return same + diff;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force Enumeration | O(k^n) | O(n) |
| 2 | Recurrence with an Array | O(n) | O(n) |
| 3 | Two Counters (Optimized) | O(n) | O(1) |

## Source Code

### PaintFence.java

```java
public class PaintFence {

    public static void main(String[] args) {
        int[][] inputs = {{3, 2}, {1, 1}, {7, 2}, {4, 3}, {0, 5}};
        for (int[] in : inputs) {
            System.out.println("Input  : n = " + in[0] + ", k = " + in[1]);
            System.out.println("Output : " + numWays(in[0], in[1]));
            System.out.println();
        }
    }

    public static int numWays(int n, int k) {
        if (n <= 0 || k <= 0) {
            return 0;
        }
        if (n == 1) {
            return k;
        }
        int same = k;
        int diff = k * (k - 1);
        for (int i = 3; i <= n; i++) {
            int newSame = diff;
            int newDiff = (same + diff) * (k - 1);
            same = newSame;
            diff = newDiff;
        }
        return same + diff;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: only the last two posts matter. Split the ways into those ending in a repeated colour (`same`) and those ending in a change (`diff`). A repeat is only allowed after a change, which is why `same` takes the old `diff`.

**Input:** `n = 4`, `k = 3`.

| Post | `same` | `diff` | Total |
|------|--------|--------|-------|
| 2 | 3 | 6 | 9 |
| 3 | 6 | (3 + 6) * 2 = 18 | 24 |
| 4 | 18 | (6 + 18) * 2 = 48 | 66 |

**Output:** `66`.
