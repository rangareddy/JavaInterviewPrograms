# Permutation Sequence

The set `[1, 2, 3, ..., n]` contains `n!` unique permutations. Listing them in order and numbering from 1, return the `k`-th permutation sequence as a string.

## Algorithm Strategy (Pick Each Digit with Factorials)

1. Precompute the factorials and keep the list of unused digits `1..n`.
2. Use zero-based `k = k - 1`. For the first position there are `(n - 1)!` permutations per leading digit, so the index of the digit is `k / (n - 1)!`.
3. Take that digit out of the list, append it, set `k %= (n - 1)!` and repeat with `n - 1` positions left.
4. The result is built in `n` steps without generating other permutations.

## Example walkthrough

- For `n = 3`, `k = 3`: `k = 2`; `2 / 2! = 1` picks the digit 2; the rest `k = 0` gives the smallest arrangement `13`.
- Result: `213`.

## Complexity Analysis

- **Time Complexity:** `O(n^2)` — Removing from the list is O(n) per step.
- **Space Complexity:** `O(n)` — The digit list.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Generate All Permutations

Generate permutations in lexicographic order and stop at the `k`-th. Exponential.

```java
public static String all(int n, int k) { int[] cnt = {0}; String[] ans = {""}; go(n, new StringBuilder(), new boolean[n + 1], k, cnt, ans); return ans[0]; }
private static void go(int n, StringBuilder cur, boolean[] used, int k, int[] cnt, String[] ans) { if (cur.length() == n) { if (++cnt[0] == k) ans[0] = cur.toString(); return; } for (int d = 1; d <= n && ans[0].isEmpty(); d++) if (!used[d]) { used[d] = true; cur.append(d); go(n, cur, used, k, cnt, ans); cur.setLength(cur.length() - 1); used[d] = false; } }
```

- **Time Complexity:** O(n! * n)
- **Space Complexity:** O(n)

### Approach 2: Factorial Number System (Optimized)

Choose each digit by dividing by factorials. This is the version implemented in the repository.

```java
public static String factoradic(int n, int k) { int[] f = new int[n + 1]; f[0] = 1; java.util.List<Integer> d = new java.util.ArrayList<>(); for (int i = 1; i <= n; i++) { f[i] = f[i - 1] * i; d.add(i); } k--; StringBuilder sb = new StringBuilder(); for (int p = n; p >= 1; p--) { sb.append(d.remove(k / f[p - 1])); k %= f[p - 1]; } return sb.toString(); }
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Generate All Permutations | O(n! * n) | O(n) |
| 2 | Factorial Number System (Optimized) | O(n^2) | O(n) |

## Source Code

### PermutationSequence.java

```java
import java.util.ArrayList;
import java.util.List;

public class PermutationSequence {

    public static void main(String[] args) {
        int[][] inputs = {{3, 3}, {4, 9}, {3, 1}, {1, 1}, {9, 362880}};
        for (int[] in : inputs) {
            System.out.println("Input  : n = " + in[0] + ", k = " + in[1]);
            System.out.println("Output : " + getPermutation(in[0], in[1]));
            System.out.println();
        }
    }

    public static String getPermutation(int n, int k) {
        if (n <= 0 || k <= 0) {
            return "";
        }
        int[] factorial = new int[n + 1];
        factorial[0] = 1;
        List<Integer> digits = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            factorial[i] = factorial[i - 1] * i;
            digits.add(i);
        }
        k--;
        StringBuilder result = new StringBuilder();
        for (int position = n; position >= 1; position--) {
            int index = k / factorial[position - 1];
            result.append(digits.remove(index));
            k %= factorial[position - 1];
        }
        return result.toString();
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: in the sorted list of permutations, all permutations that share the first digit form one block of `(n - 1)!` entries, in the order of the digits. So the block that contains the `k`-th permutation tells the first digit, and the position inside the block is a smaller problem of the same kind. This is the factorial number system.

**Input:** `n = 3`, `k = 3` (zero-based `k = 2`).

| Position | Digits left | Block size | `k / block` | Digit | `k` after |
|----------|-------------|------------|-------------|-------|-----------|
| 1 | 1 2 3 | 2 | 1 | 2 | 0 |
| 2 | 1 3 | 1 | 0 | 1 | 0 |
| 3 | 3 | 1 | 0 | 3 | 0 |

**Output:** `213`.
