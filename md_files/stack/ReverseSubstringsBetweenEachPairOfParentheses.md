# Reverse Substrings Between Each Pair of Parentheses

You are given a string `s` that consists of lower case English letters and brackets. Reverse the strings in each pair of matching parentheses, starting from the innermost one. The result should not contain any brackets.

## Algorithm Strategy (Teleport Through Matching Parentheses)

1. With a stack, precompute for every parenthesis the index of its partner.
2. Walk the string with a direction (`+1` or `-1`): start at index 0 moving right.
3. When you meet a parenthesis, jump to its partner and reverse the direction; otherwise append the character and step.
4. This writes every character exactly once, in the final order.

## Example walkthrough

- For `(abcd)`: the first `(` jumps to its partner at index 5 and walks left, writing `dcba`.
- Result: `dcba`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Pairing and walking are both linear.
- **Space Complexity:** `O(n)` — The pair table and the stack.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Stack of Partial Strings

Push the current text at `(`, and at `)` reverse it and append it to the text before the bracket.

```java
public static String partials(String s) { java.util.Deque<StringBuilder> st = new java.util.ArrayDeque<>(); StringBuilder cur = new StringBuilder(); for (char c : s.toCharArray()) { if (c == '(') { st.push(cur); cur = new StringBuilder(); } else if (c == ')') { cur.reverse(); StringBuilder prev = st.pop(); prev.append(cur); cur = prev; } else cur.append(c); } return cur.toString(); }
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 2: Paired-Index Walk (Optimized)

Precompute matching brackets and walk, reversing direction at each bracket. This is the version implemented in the repository.

```java
public static String walk(String s) { int n = s.length(); int[] p = new int[n]; java.util.Deque<Integer> st = new java.util.ArrayDeque<>(); for (int i = 0; i < n; i++) { if (s.charAt(i) == '(') st.push(i); else if (s.charAt(i) == ')') { int j = st.pop(); p[i] = j; p[j] = i; } } StringBuilder sb = new StringBuilder(); int d = 1; for (int i = 0; i < n; i += d) { char c = s.charAt(i); if (c == '(' || c == ')') { i = p[i]; d = -d; } else sb.append(c); } return sb.toString(); }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Stack of Partial Strings | O(n^2) | O(n) |
| 2 | Paired-Index Walk (Optimized) | O(n) | O(n) |

## Source Code

### ReverseSubstringsBetweenEachPairOfParentheses.java

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class ReverseSubstringsBetweenEachPairOfParentheses {

    public static void main(String[] args) {
        String[] inputs = {"(abcd)", "(u(love)i)", "(ed(et(oc))el)", "a(bcdefghijkl(mno)p)q", "abc"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + reverseParentheses(s));
            System.out.println();
        }
    }

    public static String reverseParentheses(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        int n = s.length();
        int[] pair = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else if (s.charAt(i) == ')') {
                int j = stack.pop();
                pair[i] = j;
                pair[j] = i;
            }
        }
        StringBuilder result = new StringBuilder();
        int direction = 1;
        for (int i = 0; i < n; i += direction) {
            char c = s.charAt(i);
            if (c == '(' || c == ')') {
                i = pair[i];
                direction = -direction;
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: reversing the text inside nested parentheses is the same as walking through it backwards when you pass a bracket. Jumping to the matching bracket and flipping the direction does exactly that, and nested reversals cancel out or stack up automatically through repeated flips. Every letter is written once.

**Input:** `s = "(abcd)"`.

| `i` | Char | Action | Direction |
|-----|------|--------|-----------|
| 0 | `(` | jump to the partner at 5, flip | -1 |
| 4 | d | write `d` | -1 |
| 3 | c | write `c` | -1 |
| 2 | b | write `b` | -1 |
| 1 | a | write `a` | -1 |
| 0 | `(` | jump to 5, flip, then `i` steps past the end | +1 |

**Output:** `dcba`.
