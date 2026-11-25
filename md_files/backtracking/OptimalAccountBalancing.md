# Optimal Account Balancing

Given transactions `[from, to, amount]` meaning that person `from` gave `amount` to person `to`, return the minimum number of transactions needed to settle all debts, where a transaction can move any amount between any two people.

## Algorithm Strategy (Backtracking over Net Balances)

1. Compute each person's net balance (money received minus money given) and keep only the non-zero balances.
2. Backtrack: take the first non-zero balance `debts[i]` and try to settle it against each later balance with the opposite sign, by transferring everything to that person (`debts[j] += debts[i]`).
3. Recurse on the next non-zero balance, count one transaction, then undo the change.
4. Return the minimum number of transactions over all choices. Skip a branch when the balance of the partner has the same value as one already tried.

## Example walkthrough

- For `[[0,1,10],[2,0,5]]`: person 0 is net -5, person 1 is +10, person 2 is -5.
- Two transactions settle everything: `0 -> 1` (5) and `2 -> 1` (5). Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n!) worst case` — Backtracking over the non-zero balances.
- **Space Complexity:** `O(n)` — The balances and recursion.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Backtracking over Net Balances (Optimized)

Settle the first non-zero balance against every opposite-sign balance and recurse. This is the version implemented in the repository.

```java
public static int backtrack(int[][] tx) {
    java.util.Map<Integer, Integer> b = new java.util.HashMap<>(); for (int[] t : tx) { b.merge(t[0], -t[2], Integer::sum); b.merge(t[1], t[2], Integer::sum); }
    java.util.List<Integer> d = new java.util.ArrayList<>(); for (int v : b.values()) if (v != 0) d.add(v); return go(d, 0);
}
private static int go(java.util.List<Integer> d, int s) { while (s < d.size() && d.get(s) == 0) s++; if (s == d.size()) return 0; int best = Integer.MAX_VALUE;
    for (int i = s + 1; i < d.size(); i++) if (d.get(i) * d.get(s) < 0) { d.set(i, d.get(i) + d.get(s)); best = Math.min(best, 1 + go(d, s + 1)); d.set(i, d.get(i) - d.get(s)); } return best; }
```

- **Time Complexity:** O(n!)
- **Space Complexity:** O(n)

### Approach 2: Bitmask DP over Subsets of Zero Sum

Count the maximum number of disjoint zero-sum groups: `n - groups` transfers are needed.

```java
public static int bitmask(int[][] tx) {
    java.util.Map<Integer, Integer> b = new java.util.HashMap<>(); for (int[] t : tx) { b.merge(t[0], -t[2], Integer::sum); b.merge(t[1], t[2], Integer::sum); }
    java.util.List<Integer> d = new java.util.ArrayList<>(); for (int v : b.values()) if (v != 0) d.add(v); int n = d.size(); if (n == 0) return 0;
    int[] sum = new int[1 << n]; int[] dp = new int[1 << n];
    for (int m = 1; m < (1 << n); m++) { int low = Integer.numberOfTrailingZeros(m); sum[m] = sum[m & (m - 1)] + d.get(low); }
    for (int m = 1; m < (1 << n); m++) { int best = 0; for (int i = 0; i < n; i++) if ((m >> i & 1) == 1) best = Math.max(best, dp[m ^ (1 << i)]); dp[m] = best + (sum[m] == 0 ? 1 : 0); }
    return n - dp[(1 << n) - 1];
}
```

- **Time Complexity:** O(n * 2^n)
- **Space Complexity:** O(2^n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Backtracking over Net Balances (Optimized) | O(n!) | O(n) |
| 2 | Bitmask DP over Subsets of Zero Sum | O(n * 2^n) | O(2^n) |

## Source Code

### OptimalAccountBalancing.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptimalAccountBalancing {

    public static void main(String[] args) {
        int[][][] inputs = {{{0, 1, 10}, {2, 0, 5}}, {{0, 1, 10}, {1, 0, 1}, {1, 2, 5}, {2, 0, 5}}, {{0, 1, 5}, {1, 2, 5}}, {}};
        for (int[][] transactions : inputs) {
            System.out.println("Input  : transactions = " + Arrays.deepToString(transactions));
            System.out.println("Output : " + minTransfers(transactions));
            System.out.println();
        }
    }

    public static int minTransfers(int[][] transactions) {
        if (transactions == null || transactions.length == 0) {
            return 0;
        }
        Map<Integer, Integer> balance = new HashMap<>();
        for (int[] t : transactions) {
            balance.merge(t[0], -t[2], Integer::sum);
            balance.merge(t[1], t[2], Integer::sum);
        }
        List<Integer> debts = new ArrayList<>();
        for (int value : balance.values()) {
            if (value != 0) {
                debts.add(value);
            }
        }
        return settle(debts, 0);
    }

    private static int settle(List<Integer> debts, int start) {
        while (start < debts.size() && debts.get(start) == 0) {
            start++;
        }
        if (start == debts.size()) {
            return 0;
        }
        int best = Integer.MAX_VALUE;
        for (int i = start + 1; i < debts.size(); i++) {
            if (debts.get(i) * debts.get(start) < 0) {
                debts.set(i, debts.get(i) + debts.get(start));
                best = Math.min(best, 1 + settle(debts, start + 1));
                debts.set(i, debts.get(i) - debts.get(start));
            }
        }
        return best;
    }
}
```

## Solution

The optimized solution is Approach 1 above, which is also the version implemented in this repository.

The idea: only each person's net balance matters, and a transfer can settle one person completely. Trying to settle the first unsettled balance against every opposite-sign balance, and recursing, explores all useful ways to chain the settlements.

**Input:** `transactions = [[0, 1, 10], [2, 0, 5]]`.

| Person | Net balance |
|--------|-------------|
| 0 | -10 + 5 = -5 |
| 1 | +10 |
| 2 | -5 |

Settle person 0 against person 1: person 1 now holds +5. Then settle person 2 against person 1.

**Output:** `2`.
