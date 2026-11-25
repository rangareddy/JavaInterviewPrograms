# Count and Say

The count-and-say sequence starts with `"1"`, and each term describes the previous one by counting groups of equal digits: `1` is read as `11` (one 1), `11` as `21` (two 1s), `21` as `1211` (one 2, one 1). Given `n`, return the `n`-th term.

## Algorithm Strategy (Iterate Run-Length Encoding)

1. Start with `"1"` as the first term.
2. For each next term, scan the current term and count each run of equal digits.
3. Append the run's count followed by its digit to a `StringBuilder`.
4. Repeat `n - 1` times and return the final string.

## Example walkthrough

- `1` -> `11` -> `21` -> `1211` -> `111221`.
- The 4th term is `1211`.

## Complexity Analysis

- **Time Complexity:** `O(sum of term lengths)` — Each term is scanned once; terms grow by about 30% each step.
- **Space Complexity:** `O(length of the term)` — The current and next term.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion

Build term `n` from the recursive result for `n - 1`.

```java
public static String recursive(int n) {
    if (n == 1) return "1";
    String p = recursive(n - 1); StringBuilder sb = new StringBuilder();
    for (int i = 0; i < p.length(); ) { int j = i; while (j < p.length() && p.charAt(j) == p.charAt(i)) j++; sb.append(j - i).append(p.charAt(i)); i = j; }
    return sb.toString();
}
```

- **Time Complexity:** O(total length)
- **Space Complexity:** O(n + length)

### Approach 2: Iterative Run-Length Encoding (Optimized)

Loop `n - 1` times, encoding each run. This is the version implemented in the repository.

```java
public static String iterative(int n) {
    String t = "1";
    for (int s = 1; s < n; s++) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < t.length(); ) { int j = i; while (j < t.length() && t.charAt(j) == t.charAt(i)) j++; sb.append(j - i).append(t.charAt(i)); i = j; }
        t = sb.toString();
    }
    return t;
}
```

- **Time Complexity:** O(total length)
- **Space Complexity:** O(length)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion | O(total length) | O(n + length) |
| 2 | Iterative Run-Length Encoding (Optimized) | O(total length) | O(length) |

## Source Code

### CountAndSay.java

```java
public class CountAndSay {

    public static void main(String[] args) {
        int[] inputs = {4, 1, 2, 5, 6};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + countAndSay(n));
            System.out.println();
        }
    }

    public static String countAndSay(int n) {
        if (n <= 0) {
            return "";
        }
        String term = "1";
        for (int step = 1; step < n; step++) {
            StringBuilder next = new StringBuilder();
            int i = 0;
            while (i < term.length()) {
                int j = i;
                while (j < term.length() && term.charAt(j) == term.charAt(i)) {
                    j++;
                }
                next.append(j - i).append(term.charAt(i));
                i = j;
            }
            term = next.toString();
        }
        return term;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: each term is the run-length encoding of the previous one, written as "count then digit".

**Input:** `n = 4`.

| Term | Previous | Runs | Description |
|------|----------|------|-------------|
| 1 | | | `1` |
| 2 | 1 | one 1 | `11` |
| 3 | 11 | two 1s | `21` |
| 4 | 21 | one 2, one 1 | `1211` |

**Output:** `1211`.
