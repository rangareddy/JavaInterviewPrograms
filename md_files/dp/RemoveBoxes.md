# Remove Boxes

You are given several boxes with different colours represented by positive integers. In each round you remove a group of consecutive boxes of the same colour; if the group has `k` boxes you earn `k * k` points. Return the maximum number of points you can get by removing all the boxes.

## Algorithm Strategy (Interval DP with a Count of Attached Boxes)

1. Let `f(l, r, k)` be the best score for the boxes `l..r` when there are `k` extra boxes of the same colour as `boxes[l]` attached on the left (already joined to it).
2. Option 1: remove `boxes[l]` together with those `k` boxes now: `(k + 1)^2 + f(l + 1, r, 0)`.
3. Option 2: for every `m` in `(l, r]` with `boxes[m] == boxes[l]`, first clear everything between them and then join: `f(l + 1, m - 1, 0) + f(m, r, k + 1)`.
4. Cache the results in a 3D table and return `f(0, n - 1, 0)`.

## Example walkthrough

- For `[1, 3, 2, 2, 2, 3, 4, 3, 1]`: remove the 2s (9 points), then the 4 (1), then join the 3s (16 points), then the 1s (4)... the best total is 23.
- Result: `23`.

## Complexity Analysis

- **Time Complexity:** `O(n^4)` — `O(n^3)` states, each trying up to `n` partners.
- **Space Complexity:** `O(n^3)` — The memo table.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Plain Recursion over Groups

Try removing every group of equal consecutive boxes. Exponential.

```java
public static int recursive(int[] b) { return go(java.util.Arrays.stream(b).boxed().collect(java.util.stream.Collectors.toList())); }
private static int go(java.util.List<Integer> l) {
    if (l.isEmpty()) return 0; int best = 0;
    for (int i = 0; i < l.size(); ) { int j = i; while (j < l.size() && l.get(j).equals(l.get(i))) j++; java.util.List<Integer> r = new java.util.ArrayList<>(l.subList(0, i)); r.addAll(l.subList(j, l.size())); best = Math.max(best, (j - i) * (j - i) + go(r)); i = j; }
    return best;
}
```

- **Time Complexity:** exponential
- **Space Complexity:** O(n)

### Approach 2: Interval DP with Attached Boxes (Optimized)

Memoise `f(l, r, k)`. This is the version implemented in the repository.

```java
public static int intervalDp(int[] b) { int n = b.length; return n == 0 ? 0 : f(b, 0, n - 1, 0, new int[n][n][n]); }
private static int f(int[] b, int l, int r, int k, int[][][] m) {
    if (l > r) return 0; if (m[l][r][k] != 0) return m[l][r][k]; while (l + 1 <= r && b[l + 1] == b[l]) { l++; k++; }
    int best = (k + 1) * (k + 1) + f(b, l + 1, r, 0, m); for (int x = l + 1; x <= r; x++) if (b[x] == b[l]) best = Math.max(best, f(b, l + 1, x - 1, 0, m) + f(b, x, r, k + 1, m));
    return m[l][r][k] = best;
}
```

- **Time Complexity:** O(n^4)
- **Space Complexity:** O(n^3)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Plain Recursion over Groups | exponential | O(n) |
| 2 | Interval DP with Attached Boxes (Optimized) | O(n^4) | O(n^3) |

## Source Code

### RemoveBoxes.java

```java
import java.util.Arrays;

public class RemoveBoxes {

    public static void main(String[] args) {
        int[][] inputs = {{1, 3, 2, 2, 2, 3, 4, 3, 1}, {1, 1, 1}, {1}, {1, 2, 1}};
        for (int[] boxes : inputs) {
            System.out.println("Input  : boxes = " + Arrays.toString(boxes));
            System.out.println("Output : " + removeBoxes(boxes));
            System.out.println();
        }
    }

    public static int removeBoxes(int[] boxes) {
        if (boxes == null || boxes.length == 0) {
            return 0;
        }
        int n = boxes.length;
        int[][][] memo = new int[n][n][n];
        return solve(boxes, 0, n - 1, 0, memo);
    }

    private static int solve(int[] boxes, int l, int r, int k, int[][][] memo) {
        if (l > r) {
            return 0;
        }
        if (memo[l][r][k] != 0) {
            return memo[l][r][k];
        }
        while (l + 1 <= r && boxes[l + 1] == boxes[l]) {
            l++;
            k++;
        }
        int best = (k + 1) * (k + 1) + solve(boxes, l + 1, r, 0, memo);
        for (int m = l + 1; m <= r; m++) {
            if (boxes[m] == boxes[l]) {
                best = Math.max(best, solve(boxes, l + 1, m - 1, 0, memo) + solve(boxes, m, r, k + 1, memo));
            }
        }
        memo[l][r][k] = best;
        return best;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: removing boxes in the middle can bring equal boxes together, and joined boxes score more (k squared). The state remembers how many boxes of the left end's colour are already attached to it, so the choice "clear the middle first, then join" can be compared with "remove now".

**Input:** `boxes = [1, 3, 2, 2, 2, 3, 4, 3, 1]`.

| Step | Removed | Points |
|------|---------|--------|
| 1 | the three 2s | 9 |
| 2 | the 4 | 1 |
| 3 | the three 3s (joined) | 9 |
| 4 | the two 1s (joined) | 4 |

That sequence earns 23 points, which the DP confirms is the maximum.

**Output:** `23`.
