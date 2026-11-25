# Maximum Points You Can Obtain from Cards

Several cards are arranged in a row, each with points `cardPoints[i]`. In one step you take a card from either the beginning or the end of the row. You must take exactly `k` cards. Return the maximum score you can obtain.

## Algorithm Strategy (Fixed Window over the Leftover Cards)

1. Whichever cards you take, the `n - k` cards you leave form one contiguous window in the middle.
2. Maximizing the taken sum is the same as minimizing the leftover window's sum.
3. Compute the minimum sum of a window of length `n - k` with a sliding window.
4. The answer is `total - minWindow` (and the total if `k == n`).

## Example walkthrough

- For `[1, 2, 3, 4, 5, 6, 1]` and `k = 3`: the leftover window has length 4 and its smallest sum is 10 (`[1, 2, 3, 4]`).
- The total is 22, so the answer is `22 - 10 = 12`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — A running sum.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion on Both Ends

Take the left or the right card and recurse with `k - 1`. Exponential.

```java
public static int recursive(int[] a, int k) { return go(a, 0, a.length - 1, k); }
private static int go(int[] a, int l, int r, int k) { if (k == 0) return 0; return Math.max(a[l] + go(a, l + 1, r, k - 1), a[r] + go(a, l, r - 1, k - 1)); }
```

- **Time Complexity:** O(2^k)
- **Space Complexity:** O(k)

### Approach 2: Prefix and Suffix Sums

Try every split: `i` cards from the front and `k - i` from the back.

```java
public static int prefixSuffix(int[] a, int k) {
    int n = a.length, best = 0, front = 0;
    for (int i = 0; i < k; i++) front += a[i];
    best = front; int back = 0;
    for (int i = 1; i <= k; i++) { front -= a[k - i]; back += a[n - i]; best = Math.max(best, front + back); }
    return best;
}
```

- **Time Complexity:** O(k)
- **Space Complexity:** O(1)

### Approach 3: Minimum Leftover Window (Optimized)

Minimise the sum of the untouched middle window. This is the version implemented in the repository.

```java
public static int leftover(int[] a, int k) {
    int n = a.length, total = 0; for (int x : a) total += x;
    if (k >= n) return total;
    int w = n - k, s = 0; for (int i = 0; i < w; i++) s += a[i];
    int min = s; for (int i = w; i < n; i++) { s += a[i] - a[i - w]; min = Math.min(min, s); }
    return total - min;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion on Both Ends | O(2^k) | O(k) |
| 2 | Prefix and Suffix Sums | O(k) | O(1) |
| 3 | Minimum Leftover Window (Optimized) | O(n) | O(1) |

## Source Code

### MaximumPointsYouCanObtainFromCards.java

```java
import java.util.Arrays;

public class MaximumPointsYouCanObtainFromCards {

    public static void main(String[] args) {
        int[][] cards = {{1, 2, 3, 4, 5, 6, 1}, {2, 2, 2}, {9, 7, 7, 9, 7, 7, 9}, {1, 1000, 1}};
        int[] ks = {3, 2, 7, 1};
        for (int i = 0; i < cards.length; i++) {
            System.out.println("Input  : cardPoints = " + Arrays.toString(cards[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + maxScore(cards[i], ks[i]));
            System.out.println();
        }
    }

    public static int maxScore(int[] cardPoints, int k) {
        if (cardPoints == null || k <= 0) {
            return 0;
        }
        int n = cardPoints.length;
        int total = 0;
        for (int x : cardPoints) {
            total += x;
        }
        if (k >= n) {
            return total;
        }
        int window = n - k;
        int sum = 0;
        for (int i = 0; i < window; i++) {
            sum += cardPoints[i];
        }
        int min = sum;
        for (int i = window; i < n; i++) {
            sum += cardPoints[i] - cardPoints[i - window];
            min = Math.min(min, sum);
        }
        return total - min;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: taking `k` cards from the two ends leaves one contiguous block of `n - k` cards, so the best score is the total minus the smallest such block.

**Input:** `cardPoints = [1, 2, 3, 4, 5, 6, 1]`, `k = 3`. Total 22, window length 4.

| Window | Sum | Minimum |
|--------|-----|---------|
| [1, 2, 3, 4] | 10 | 10 |
| [2, 3, 4, 5] | 14 | 10 |
| [3, 4, 5, 6] | 18 | 10 |
| [4, 5, 6, 1] | 16 | 10 |

**Output:** `22 - 10 = 12`.
