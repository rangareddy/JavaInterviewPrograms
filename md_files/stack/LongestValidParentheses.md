# Longest Valid Parentheses

Given a string containing only `(` and `)`, return the length of the longest valid (well-formed) parentheses substring.

## Algorithm Strategy (Stack of Indices)

1. Keep a stack of indices. Start with `-1` on it: this is the index just before the current candidate substring.
2. On `(`, push its index.
3. On `)`, pop. If the stack becomes empty, this `)` is unmatched, so push its index as the new base. Otherwise the length of the valid run ending here is `i - stack.peek()`, so update the best length.
4. The index left on top of the stack is always the position of the last unmatched character, which is why subtracting it gives the length of the current valid run (including adjacent valid pieces such as `()()`).

## Example walkthrough

- For `")()())"`: the stack starts as `[-1]`; index 0 `)` pops `-1` and pushes 0.
- Index 2 gives length `2 - 0 = 2`, index 4 gives `4 - 0 = 4`, index 5 pops 0 and pushes 5.
- Result: `4`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each index is pushed and popped at most once.
- **Space Complexity:** `O(n)` — The stack.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

Check every even-length substring with a counter that must never go negative and must end at zero.

```java
public static int longestValidParenthesesBrute(String s) {
    int best = 0;
    for (int i = 0; i < s.length(); i++) {
        int balance = 0;
        for (int j = i; j < s.length(); j++) {
            balance += s.charAt(j) == '(' ? 1 : -1;
            if (balance < 0) break;
            if (balance == 0) best = Math.max(best, j - i + 1);
        }
    }
    return best;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Dynamic Programming

`dp[i]` is the length of the longest valid substring ending at `i`; it is non-zero only when `s[i]` is `)`.

```java
public static int longestValidParenthesesDp(String s) {
    int n = s.length(), best = 0;
    int[] dp = new int[n];
    for (int i = 1; i < n; i++) {
        if (s.charAt(i) != ')') continue;
        if (s.charAt(i - 1) == '(') {
            dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
        } else {
            int match = i - dp[i - 1] - 1;
            if (match >= 0 && s.charAt(match) == '(') {
                dp[i] = dp[i - 1] + 2 + (match >= 1 ? dp[match - 1] : 0);
            }
        }
        best = Math.max(best, dp[i]);
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Two Counters, Two Passes

Scan left to right counting `(` and `)`; reset when `)` exceeds `(`, and record `2 * close` when they are equal. Scan right to left the same way to catch the case with extra `(`.

```java
public static int longestValidParenthesesCounters(String s) {
    int best = 0, open = 0, close = 0;
    for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == '(') open++; else close++;
        if (open == close) best = Math.max(best, 2 * close);
        else if (close > open) open = close = 0;
    }
    open = close = 0;
    for (int i = s.length() - 1; i >= 0; i--) {
        if (s.charAt(i) == '(') open++; else close++;
        if (open == close) best = Math.max(best, 2 * open);
        else if (open > close) open = close = 0;
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 4: Stack of Indices (Optimized)

Keep the index of the last unmatched character on the stack so the length of every valid run is a subtraction. This is the version implemented in the repository.

```java
public static int longestValidParentheses(String s) {
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(-1);
    int best = 0;
    for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == '(') {
            stack.push(i);
        } else {
            stack.pop();
            if (stack.isEmpty()) {
                stack.push(i);
            } else {
                best = Math.max(best, i - stack.peek());
            }
        }
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) |
| 2 | Dynamic Programming | O(n) | O(n) |
| 3 | Two Counters, Two Passes | O(n) | O(1) |
| 4 | Stack of Indices (Optimized) | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### LongestValidParentheses.java

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class LongestValidParentheses {

    public static void main(String[] args) {
        String[] inputs = {"(()", ")()())", "", "()(()", "()(())"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + longestValidParentheses(s));
            System.out.println();
        }
    }

    public static int longestValidParentheses(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1);
        int best = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    best = Math.max(best, i - stack.peek());
                }
            }
        }
        return best;
    }
}
```
