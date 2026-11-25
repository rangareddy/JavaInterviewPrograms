# Count Ways To Build Good Strings

Given the integers `zero`, `one`, `low` and `high`, you build a string by repeatedly appending either `'0'` exactly `zero` times or `'1'` exactly `one` times. A good string has a length between `low` and `high` inclusive. Return the number of different good strings, modulo `1_000_000_007`.

## Algorithm Strategy (Count the Ways to Reach Each Length)

1. Let `ways[len]` be the number of distinct strings of exactly length `len`; `ways[0] = 1` (the empty string).
2. For each length from 1 to `high`: add `ways[len - zero]` (if `len >= zero`) and `ways[len - one]` (if `len >= one`), modulo `1_000_000_007`.
3. Every string is built uniquely by its sequence of appended blocks, so no pair is counted twice.
4. Sum `ways[len]` for `len` from `low` to `high`.

## Example walkthrough

- For `low = 3`, `high = 3`, `zero = 1`, `one = 1`: `ways = [1, 2, 4, 8]`, because every length doubles.
- Result: `8`.

## Complexity Analysis

- **Time Complexity:** `O(high)` — One update per length.
- **Space Complexity:** `O(high)` — The `ways` array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Plain Recursion

Extend the string by either block and count it when its length is in range. Recomputes the same lengths again and again.

```java
public static int recursion(int low, int high, int zero, int one) { return (int) (go(0, low, high, zero, one) % 1_000_000_007L); }
static long go(int len, int low, int high, int zero, int one) { if (len > high) return 0; long c = len >= low ? 1 : 0; return (c + go(len + zero, low, high, zero, one) + go(len + one, low, high, zero, one)) % 1_000_000_007L; }
```

- **Time Complexity:** O(2^high)
- **Space Complexity:** O(high)

### Approach 2: Bottom-Up Counting (Optimized)

Count the strings of every length once, from shorter to longer. This is the version implemented in the repository.

```java
public static int table(int low, int high, int zero, int one) { final int M = 1_000_000_007; int[] w = new int[high + 1]; w[0] = 1; int total = 0; for (int len = 1; len <= high; len++) { long c = 0; if (len >= zero) c += w[len - zero]; if (len >= one) c += w[len - one]; w[len] = (int) (c % M); if (len >= low) total = (total + w[len]) % M; } return total; }
```

- **Time Complexity:** O(high)
- **Space Complexity:** O(high)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Plain Recursion | O(2^high) | O(high) |
| 2 | Bottom-Up Counting (Optimized) | O(high) | O(high) |

## Source Code

### CountWaysToBuildGoodStrings.java

```java
public class CountWaysToBuildGoodStrings {

    public static void main(String[] args) {
        int[][] inputs = {{3, 3, 1, 1}, {2, 3, 1, 2}, {1, 1, 1, 1}, {5, 20, 4, 6}, {1, 5, 6, 7}};
        for (int[] in : inputs) {
            System.out.println("Input  : low = " + in[0] + ", high = " + in[1] + ", zero = " + in[2] + ", one = " + in[3]);
            System.out.println("Output : " + countGoodStrings(in[0], in[1], in[2], in[3]));
            System.out.println();
        }
    }

    private static final int MOD = 1_000_000_007;

    public static int countGoodStrings(int low, int high, int zero, int one) {
        int[] ways = new int[high + 1];
        ways[0] = 1;
        int total = 0;
        for (int len = 1; len <= high; len++) {
            long count = 0;
            if (len >= zero) {
                count += ways[len - zero];
            }
            if (len >= one) {
                count += ways[len - one];
            }
            ways[len] = (int) (count % MOD);
            if (len >= low) {
                total = (total + ways[len]) % MOD;
            }
        }
        return total;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a string is identified by its sequence of appended blocks, so the number of strings of length `len` is the number of block sequences that sum to `len`. The last block is either `zero` characters or `one` characters long, so `ways[len] = ways[len - zero] + ways[len - one]`.

**Input:** `low = 3`, `high = 3`, `zero = 1`, `one = 1`.

| `len` | `ways[len]` |
|-------|-------------|
| 0 | 1 |
| 1 | 1 + 1 = 2 |
| 2 | 2 + 2 = 4 |
| 3 | 4 + 4 = 8 |

Only length 3 is in range.

**Output:** `8`.
