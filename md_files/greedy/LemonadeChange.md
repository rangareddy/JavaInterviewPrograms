# Lemonade Change

At a lemonade stand each lemonade costs `$5`. Customers pay with a `$5`, `$10` or `$20` bill, in the order given by `bills`, and you must give each the correct change. You start with no change. Return `true` if you can give every customer the correct change.

## Algorithm Strategy (Count Fives and Tens, Prefer Large Bills for Change)

1. Track how many `$5` and `$10` bills you hold. A `$20` is never used as change.
2. On a `$5` bill, keep it.
3. On a `$10` bill, give back one `$5`; fail if none.
4. On a `$20` bill, prefer giving `$10 + $5` (a `$10` is useless otherwise), else `$5 + $5 + $5`; fail if neither is possible.

## Example walkthrough

- For `[5, 5, 5, 10, 20]`: three fives, then the 10 takes one five, then the 20 takes a ten and a five.
- Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass over the bills.
- **Space Complexity:** `O(1)` — Two counters.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Try Every Way to Give Change

Backtrack over both ways to change a `$20`. Correct, but exponential in the worst case.

```java
public static boolean search(int[] b) { return go(b, 0, 0, 0); }
static boolean go(int[] b, int i, int f, int t) { if (i == b.length) return true; int x = b[i]; if (x == 5) return go(b, i + 1, f + 1, t); if (x == 10) return f > 0 && go(b, i + 1, f - 1, t + 1); if (t > 0 && f > 0 && go(b, i + 1, f - 1, t - 1)) return true; return f >= 3 && go(b, i + 1, f - 3, t); }
```

- **Time Complexity:** O(2^n)
- **Space Complexity:** O(n)

### Approach 2: Greedy Counters (Optimized)

Always spend the `$10` first when changing a `$20`, since a `$10` can only be used for that. This is the version implemented in the repository.

```java
public static boolean greedy(int[] b) { int f = 0, t = 0; for (int x : b) { if (x == 5) f++; else if (x == 10) { if (f == 0) return false; f--; t++; } else if (t > 0 && f > 0) { t--; f--; } else if (f >= 3) f -= 3; else return false; } return true; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Try Every Way to Give Change | O(2^n) | O(n) |
| 2 | Greedy Counters (Optimized) | O(n) | O(1) |

## Source Code

### LemonadeChange.java

```java
import java.util.Arrays;

public class LemonadeChange {

    public static void main(String[] args) {
        int[][] inputs = {{5, 5, 5, 10, 20}, {5, 5, 10, 10, 20}, {5, 10}, {10}, {}};
        for (int[] bills : inputs) {
            System.out.println("Input  : bills = " + Arrays.toString(bills));
            System.out.println("Output : " + lemonadeChange(bills));
            System.out.println();
        }
    }

    public static boolean lemonadeChange(int[] bills) {
        int fives = 0;
        int tens = 0;
        for (int bill : bills) {
            if (bill == 5) {
                fives++;
            } else if (bill == 10) {
                if (fives == 0) {
                    return false;
                }
                fives--;
                tens++;
            } else {
                if (tens > 0 && fives > 0) {
                    tens--;
                    fives--;
                } else if (fives >= 3) {
                    fives -= 3;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a `$5` bill is useful for every kind of change, while a `$10` bill is useful only for a `$20` customer. So when changing a `$20`, spending the `$10` first never hurts, which removes the need to search.

**Input:** `bills = [5, 5, 5, 10, 20]`.

| Bill | Action | Fives | Tens |
|------|--------|-------|------|
| 5 | keep | 1 | 0 |
| 5 | keep | 2 | 0 |
| 5 | keep | 3 | 0 |
| 10 | give one 5 | 2 | 1 |
| 20 | give 10 + 5 | 1 | 0 |

Every customer received the correct change.

**Output:** `true`.
