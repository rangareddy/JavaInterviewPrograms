# Generate Parentheses

Given `n` pairs of parentheses, generate all combinations of well-formed parentheses.

## Algorithm Strategy (Backtracking with Open and Close Counters)

1. Build the string character by character, tracking how many opening (`open`) and closing (`close`) parentheses have been used.
2. An opening parenthesis may be added while `open < n`.
3. A closing parenthesis may be added only while `close < open`, otherwise it would have no partner.
4. When the string has length `2n` it is a valid combination: record it. Trying "(" before ")" produces the results in lexicographic order.

## Example walkthrough

- For `n = 2`: the choices lead to `"(())"` (open, open, close, close) and `"()()"` (open, close, open, close).
- For `n = 3` there are 5 results, the Catalan number `C3`.

## Complexity Analysis

- **Time Complexity:** `O(4^n / sqrt(n))` — The number of valid combinations is the Catalan number `C(n)`, and each has length `2n`.
- **Space Complexity:** `O(n)` — Recursion depth (excluding the output).

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force (Generate All, Keep the Valid Ones)

Generate all `2^(2n)` strings of `(` and `)` and keep those that are balanced. Correct but exponentially wasteful.

```java
public static List<String> generateParenthesisBrute(int n) {
    List<String> result = new ArrayList<>();
    generate(new char[2 * n], 0, result);
    return result;
}

private static void generate(char[] current, int index, List<String> result) {
    if (index == current.length) {
        if (isValid(current)) result.add(new String(current));
        return;
    }
    current[index] = '(';
    generate(current, index + 1, result);
    current[index] = ')';
    generate(current, index + 1, result);
}

private static boolean isValid(char[] s) {
    int balance = 0;
    for (char c : s) {
        balance += c == '(' ? 1 : -1;
        if (balance < 0) return false;
    }
    return balance == 0;
}
```

- **Time Complexity:** O(2^(2n) * n)
- **Space Complexity:** O(n)

### Approach 2: Closure Number (Inductive Construction)

Every non-empty valid string is `"(" + A + ")" + B` where `A` and `B` are valid strings using `k` and `n - 1 - k` pairs. Build the lists for `0..n` pairs bottom-up from the shorter ones.

```java
public static List<String> generateParenthesisClosure(int n) {
    List<List<String>> byPairs = new ArrayList<>();
    byPairs.add(Collections.singletonList(""));
    for (int pairs = 1; pairs <= n; pairs++) {
        List<String> current = new ArrayList<>();
        for (int inside = 0; inside < pairs; inside++) {
            for (String a : byPairs.get(inside)) {
                for (String b : byPairs.get(pairs - 1 - inside)) current.add("(" + a + ")" + b);
            }
        }
        byPairs.add(current);
    }
    return byPairs.get(n);
}
```

- **Time Complexity:** O(4^n / sqrt(n))
- **Space Complexity:** O(4^n / sqrt(n))

### Approach 3: Level-by-Level BFS Expansion

Start with the empty string and extend all strings of length `L` to length `L + 1` by appending `(` when fewer than `n` are open and `)` when it keeps the string valid. Each level is a list of states, no recursion.

```java
public static List<String> generateParenthesisBfs(int n) {
    List<String[]> level = new ArrayList<>();            // {string, open count, close count}
    level.add(new String[]{"", "0", "0"});
    for (int length = 0; length < 2 * n; length++) {
        List<String[]> next = new ArrayList<>();
        for (String[] state : level) {
            int open = Integer.parseInt(state[1]), close = Integer.parseInt(state[2]);
            if (open < n) next.add(new String[]{state[0] + "(", String.valueOf(open + 1), String.valueOf(close)});
            if (close < open) next.add(new String[]{state[0] + ")", String.valueOf(open), String.valueOf(close + 1)});
        }
        level = next;
    }
    List<String> result = new ArrayList<>();
    for (String[] state : level) result.add(state[0]);
    return result;
}
```

- **Time Complexity:** O(4^n / sqrt(n))
- **Space Complexity:** O(4^n / sqrt(n))

### Approach 4: Backtracking with Open and Close Counters (Optimized)

Only add `(` while fewer than `n` are open and `)` while it still has a partner, so invalid prefixes are never built. This is the version implemented in the repository.

```java
public static List<String> generateParenthesis(int n) {
    List<String> result = new ArrayList<>();
    backtrack(result, new StringBuilder(), 0, 0, n);
    return result;
}

private static void backtrack(List<String> result, StringBuilder current, int open, int close, int n) {
    if (current.length() == 2 * n) { result.add(current.toString()); return; }
    if (open < n) {
        current.append('(');
        backtrack(result, current, open + 1, close, n);
        current.deleteCharAt(current.length() - 1);
    }
    if (close < open) {
        current.append(')');
        backtrack(result, current, open, close + 1, n);
        current.deleteCharAt(current.length() - 1);
    }
}
```

- **Time Complexity:** O(4^n / sqrt(n))
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force (Generate All, Keep the Valid Ones) | O(2^(2n) * n) | O(n) |
| 2 | Closure Number (Inductive Construction) | O(4^n / sqrt(n)) | O(4^n / sqrt(n)) |
| 3 | Level-by-Level BFS Expansion | O(4^n / sqrt(n)) | O(4^n / sqrt(n)) |
| 4 | Backtracking with Open and Close Counters (Optimized) | O(4^n / sqrt(n)) | O(n) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### GenerateParentheses.java

```java
import java.util.ArrayList;
import java.util.List;

public class GenerateParentheses {

    public static void main(String[] args) {
        int[] inputs = {3, 1, 2, 4};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + generateParenthesis(n));
            System.out.println();
        }
    }

    public static List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(result, new StringBuilder(), 0, 0, n);
        return result;
    }

    private static void backtrack(List<String> result, StringBuilder current, int open, int close, int n) {
        if (current.length() == 2 * n) {
            result.add(current.toString());
            return;
        }
        if (open < n) {
            current.append('(');
            backtrack(result, current, open + 1, close, n);
            current.deleteCharAt(current.length() - 1);
        }
        if (close < open) {
            current.append(')');
            backtrack(result, current, open, close + 1, n);
            current.deleteCharAt(current.length() - 1);
        }
    }
}
```
