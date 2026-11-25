# Maximum Nesting Depth of the Parentheses

Given a valid parentheses string `s` (it may also contain digits and operators), return the nesting depth of `s`, which is the maximum number of nested parentheses at any position.

## Algorithm Strategy (Track the Current Depth)

1. Keep a counter `depth` for the number of currently open parentheses.
2. Increase it on `(` and record the maximum seen so far.
3. Decrease it on `)`.
4. Ignore every other character.

## Example walkthrough

- For `(1+(2*3)+((8)/4))+1`: the counter climbs to 3 at the `((` before `8`.
- Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — Two integers.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Stack of Open Parentheses

Push on `(` and pop on `)`, tracking the largest stack size.

```java
public static int stack(String s) { java.util.Deque<Character> st = new java.util.ArrayDeque<>(); int best = 0; for (char c : s.toCharArray()) { if (c == '(') { st.push(c); best = Math.max(best, st.size()); } else if (c == ')') st.pop(); } return best; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Depth Counter (Optimized)

Replace the stack with a single integer, since only the size matters. This is the version implemented in the repository.

```java
public static int counter(String s) { int d = 0, best = 0; for (int i = 0; i < s.length(); i++) { char c = s.charAt(i); if (c == '(') best = Math.max(best, ++d); else if (c == ')') d--; } return best; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Stack of Open Parentheses | O(n) | O(n) |
| 2 | Depth Counter (Optimized) | O(n) | O(1) |

## Source Code

### MaximumNestingDepthOfTheParentheses.java

```java
public class MaximumNestingDepthOfTheParentheses {

    public static void main(String[] args) {
        String[] inputs = {"(1+(2*3)+((8)/4))+1", "(1)+((2))+(((3)))", "1+(2*3)/(2-1)", "1", ""};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + maxDepth(s));
            System.out.println();
        }
    }

    public static int maxDepth(String s) {
        if (s == null) {
            return 0;
        }
        int depth = 0;
        int best = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                depth++;
                best = Math.max(best, depth);
            } else if (c == ')') {
                depth--;
            }
        }
        return best;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: only the number of open parentheses matters, not which ones, so a stack can be replaced by a counter. The answer is the highest value the counter reaches.

**Input:** `s = "(1+(2*3)+((8)/4))+1"`.

| Character | Depth after | Best |
|-----------|-------------|------|
| `(` | 1 | 1 |
| `(` (before 2) | 2 | 2 |
| `)` (after 3) | 1 | 2 |
| `(` | 2 | 2 |
| `(` (before 8) | 3 | 3 |
| `)` (after 8) | 2 | 3 |
| `)` (after 4) | 1 | 3 |
| `)` | 0 | 3 |

**Output:** `3`.
