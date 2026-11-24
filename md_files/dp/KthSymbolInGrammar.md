# K-th Symbol in Grammar

Build a table of `n` rows. Row 1 is `0`. Each following row is made from the previous one by replacing every `0` with `01` and every `1` with `10`. Return the `k`-th symbol (1-indexed) of row `n`.

## Algorithm Strategy (Parent Lookup)

1. Row `n` has `2^(n-1)` symbols, and each symbol of row `n - 1` produces two children: positions `2p - 1` and `2p`.
2. So the parent of position `k` is `(k + 1) / 2`.
3. If `k` is odd, the symbol equals its parent. If `k` is even, it is the parent flipped.
4. Recurse from row `n` to row 1, where the answer is `0`.

## Example walkthrough

- For `n = 4`, `k = 5`: row 4 is `01101001`, whose 5th symbol is 1.
- Walk up: position 5 -> 3 -> 2 -> 1, with flips at the even steps, giving 1.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One step per row.
- **Space Complexity:** `O(1)` — The loop version keeps a flip counter only.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Build the Rows

Generate each row as a string and read the `k`-th character. The row doubles each time, so only small `n` works.

```java
public static int buildRows(int n, int k) {
    StringBuilder row = new StringBuilder("0");
    for (int i = 2; i <= n; i++) {
        StringBuilder next = new StringBuilder();
        for (int j = 0; j < row.length(); j++) next.append(row.charAt(j) == '0' ? "01" : "10");
        row = next;
    }
    return row.charAt(k - 1) - '0';
}
```

- **Time Complexity:** O(2^n)
- **Space Complexity:** O(2^n)

### Approach 2: Recursion on the Parent

Flip the parent's symbol when `k` is even.

```java
public static int recursive(int n, int k) {
    if (n == 1) return 0;
    int parent = recursive(n - 1, (k + 1) / 2);
    return k % 2 == 1 ? parent : 1 - parent;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Count Flips Iteratively (Optimized)

Walk from row `n` to row 1 and count the even positions, then take the parity. This is the version implemented in the repository.

```java
public static int countFlips(int n, int k) {
    int flips = 0;
    while (n > 1) { if (k % 2 == 0) flips++; k = (k + 1) / 2; n--; }
    return flips % 2;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Build the Rows | O(2^n) | O(2^n) |
| 2 | Recursion on the Parent | O(n) | O(n) |
| 3 | Count Flips Iteratively (Optimized) | O(n) | O(1) |

## Source Code

### KthSymbolInGrammar.java

```java
public class KthSymbolInGrammar {

    public static void main(String[] args) {
        int[][] inputs = {{1, 1}, {2, 1}, {2, 2}, {4, 5}, {30, 434991989}};
        for (int[] in : inputs) {
            System.out.println("Input  : n = " + in[0] + ", k = " + in[1]);
            System.out.println("Output : " + kthGrammar(in[0], in[1]));
            System.out.println();
        }
    }

    public static int kthGrammar(int n, int k) {
        int flips = 0;
        while (n > 1) {
            if (k % 2 == 0) {
                flips++;
            }
            k = (k + 1) / 2;
            n--;
        }
        return flips % 2;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: every symbol comes from exactly one parent in the previous row, and it is either the same as the parent (odd position) or its opposite (even position). Walking back to row 1 therefore only needs the number of even positions visited.

**Input:** `n = 4`, `k = 5`.

| Row | Position `k` | Even? | Flips so far | Parent position |
|-----|--------------|-------|--------------|-----------------|
| 4 | 5 | no | 0 | 3 |
| 3 | 3 | no | 0 | 2 |
| 2 | 2 | yes | 1 | 1 |
| 1 | 1 | stop | 1 | |

The flips are odd, so the answer is the opposite of row 1's `0`.

**Output:** `1`.
