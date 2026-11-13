# Minimum Remove to Make Valid Parentheses

Given a string `s` of `(`, `)` and lowercase letters, remove the minimum number of parentheses (in any positions) so that the resulting string is valid, and return any valid result.

## Algorithm Strategy (Stack of Indices to Remove)

1. Scan left to right with a stack of the indices of unmatched `(`.
2. On `(` push its index. On `)`, if the stack is not empty pop it (a match), otherwise this `)` can never be matched, so mark its index for removal.
3. After the scan, every index still on the stack is an unmatched `(`, so mark those for removal too.
4. Build the answer from the characters whose indices are not marked. Letters are never removed.

## Example walkthrough

- For `"lee(t(c)o)de)"`: the `)` at the end finds an empty stack and is marked.
- Result: `"lee(t(c)o)de"`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass to mark and one pass to build the output.
- **Space Complexity:** `O(n)` — The stack and the removal marks.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Two Passes with a Counter

Left to right, drop a `)` when no `(` is open; right to left, drop a `(` when no `)` is waiting. No stack is needed.

```java
public static String minRemoveTwoPass(String s) {
    StringBuilder first = new StringBuilder();
    int balance = 0;
    for (char c : s.toCharArray()) {
        if (c == '(') balance++;
        else if (c == ')') {
            if (balance == 0) continue;
            balance--;
        }
        first.append(c);
    }
    StringBuilder second = new StringBuilder();
    balance = 0;
    for (int i = first.length() - 1; i >= 0; i--) {
        char c = first.charAt(i);
        if (c == ')') balance++;
        else if (c == '(') {
            if (balance == 0) continue;
            balance--;
        }
        second.append(c);
    }
    return second.reverse().toString();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Stack of Unmatched Indices (Optimized)

Mark every `)` with no partner and every `(` left on the stack, then rebuild the string without them. This is the version implemented in the repository.

```java
public static String minRemoveToMakeValid(String s) {
    boolean[] remove = new boolean[s.length()];
    Deque<Integer> open = new ArrayDeque<>();
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (c == '(') {
            open.push(i);
        } else if (c == ')') {
            if (open.isEmpty()) {
                remove[i] = true;
            } else {
                open.pop();
            }
        }
    }
    while (!open.isEmpty()) {
        remove[open.pop()] = true;
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
        if (!remove[i]) {
            sb.append(s.charAt(i));
        }
    }
    return sb.toString();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Two Passes with a Counter | O(n) | O(n) |
| 2 | Stack of Unmatched Indices (Optimized) | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### MinimumRemoveToMakeValidParentheses.java

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class MinimumRemoveToMakeValidParentheses {

    public static void main(String[] args) {
        String[] inputs = {"lee(t(c)o)de)", "a)b(c)d", "))((", "abc"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : \"" + minRemoveToMakeValid(s) + "\"");
            System.out.println();
        }
    }

    public static String minRemoveToMakeValid(String s) {
        boolean[] remove = new boolean[s.length()];
        Deque<Integer> open = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                open.push(i);
            } else if (c == ')') {
                if (open.isEmpty()) {
                    remove[i] = true;
                } else {
                    open.pop();
                }
            }
        }
        while (!open.isEmpty()) {
            remove[open.pop()] = true;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (!remove[i]) {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }
}
```
