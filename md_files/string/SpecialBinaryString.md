# Special Binary String

A special binary string has an equal number of `0`s and `1`s, and every prefix has at least as many `1`s as `0`s. A move picks two consecutive, non-empty special substrings and swaps them. Return the lexicographically largest string you can get after any number of moves.

## Algorithm Strategy (Split into Primitive Pieces, Sort Descending)

1. Split the string into primitive special strings: scan with a balance counter (+1 for `1`, -1 for `0`) and cut each time it returns to zero.
2. Each primitive string has the form `1 inner 0`; recursively make `inner` as large as possible.
3. Rebuild each piece as `1 + best(inner) + 0`.
4. Sort the pieces in descending order and concatenate them: the largest piece first gives the largest string.

## Example walkthrough

- For `11011000`: it is one primitive piece `1 10 11 000`-style; its inner part `101100` splits into `10` and `1100`, which sort to `1100`, `10`.
- Result: `11100100`.

## Complexity Analysis

- **Time Complexity:** `O(n^2)` — Each recursion level scans and sorts the pieces.
- **Space Complexity:** `O(n)` — The recursion and the pieces.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force Swapping

Repeatedly try every swap of two adjacent special substrings and keep improving. Exponential in the worst case.

```java
public static String brute(String s) {
    String best = s; boolean improved = true;
    while (improved) { improved = false; java.util.List<int[]> sp = new java.util.ArrayList<>(); int n = best.length();
        for (int i = 0; i < n; i++) { int b = 0; for (int j = i; j < n; j++) { b += best.charAt(j) == '1' ? 1 : -1; if (b < 0) break; if (b == 0) sp.add(new int[]{i, j + 1}); } }
        outer: for (int[] a : sp) for (int[] c : sp) if (a[1] == c[0]) { String t = best.substring(0, a[0]) + best.substring(c[0], c[1]) + best.substring(a[0], a[1]) + best.substring(c[1]); if (t.compareTo(best) > 0) { best = t; improved = true; break outer; } } }
    return best;
}
```

- **Time Complexity:** exponential
- **Space Complexity:** O(n)

### Approach 2: Recursive Decomposition and Sort (Optimized)

Maximise each primitive piece's inner part, then order the pieces descending. This is the version implemented in the repository.

```java
public static String decompose(String s) {
    if (s.length() <= 2) return s; java.util.List<String> parts = new java.util.ArrayList<>(); int bal = 0, st = 0;
    for (int i = 0; i < s.length(); i++) { bal += s.charAt(i) == '1' ? 1 : -1; if (bal == 0) { parts.add("1" + decompose(s.substring(st + 1, i)) + "0"); st = i + 1; } }
    java.util.Collections.sort(parts, java.util.Collections.reverseOrder()); return String.join("", parts);
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force Swapping | exponential | O(n) |
| 2 | Recursive Decomposition and Sort (Optimized) | O(n^2) | O(n) |

## Source Code

### SpecialBinaryString.java

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpecialBinaryString {

    public static void main(String[] args) {
        String[] inputs = {"11011000", "10", "1100", "101100"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + makeLargestSpecial(s));
            System.out.println();
        }
    }

    public static String makeLargestSpecial(String s) {
        if (s == null || s.length() <= 2) {
            return s == null ? "" : s;
        }
        List<String> pieces = new ArrayList<>();
        int balance = 0;
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            balance += s.charAt(i) == '1' ? 1 : -1;
            if (balance == 0) {
                pieces.add("1" + makeLargestSpecial(s.substring(start + 1, i)) + "0");
                start = i + 1;
            }
        }
        Collections.sort(pieces, Collections.reverseOrder());
        return String.join("", pieces);
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a special string is a Dyck word (balanced brackets with `1` as an opening bracket). Swapping adjacent special substrings reorders the primitive pieces at any level, and the biggest result puts the biggest pieces first, with every piece's interior made as large as possible recursively.

**Input:** `s = "11011000"`.

| Level | Pieces | Action |
|-------|--------|--------|
| top | `11011000` (one primitive piece) | strip the outer `1` and `0`, the inner part is `101100` |
| inner | `10`, `1100` | sort descending: `1100`, `10` |
| rebuild | `1` + `110010` + `0` | `11100100` |

**Output:** `11100100`.
