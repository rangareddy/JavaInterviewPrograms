# Valid Parentheses

Given a string containing only the characters `( ) { } [ ]`, determine whether it is valid: every open bracket is closed by the same type of bracket, in the correct order.

## Algorithm Strategy (Stack of Open Brackets)

1. Scan the string from left to right with a stack.
2. Push every opening bracket (or, as done here, push the closing bracket that it expects).
3. On a closing bracket the stack must be non-empty and its top must be that same bracket; otherwise the string is invalid.
4. After the scan the stack must be empty, otherwise some bracket was never closed.

## Example walkthrough

- For `"([)]"`: push `)`, push `]`; the next character `)` does not match the top `]`, so the result is `false`.
- For `"()[]{}"` every closing bracket matches the top of the stack and the stack ends empty: `true`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each character is pushed and popped at most once.
- **Space Complexity:** `O(n)` — The stack may hold all opening brackets.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Repeated Replacement

Keep removing the adjacent pairs `()`, `[]` and `{}` until nothing changes; the string is valid exactly when it becomes empty. Simple, but every pass rescans the string.

```java
public static boolean isValidReplace(String s) {
    String previous;
    do {
        previous = s;
        s = s.replace("()", "").replace("[]", "").replace("{}", "");
    } while (!s.equals(previous));
    return s.isEmpty();
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 2: Stack with a Matching Map

Push opening brackets; on a closing bracket the top of the stack must be its matching opening bracket (looked up in a map).

```java
public static boolean isValidMap(String s) {
    Map<Character, Character> pairs = new HashMap<>();
    pairs.put(')', '('); pairs.put(']', '['); pairs.put('}', '{');
    Deque<Character> stack = new ArrayDeque<>();
    for (char c : s.toCharArray()) {
        if (pairs.containsKey(c)) {
            if (stack.isEmpty() || stack.pop() != pairs.get(c)) return false;
        } else {
            stack.push(c);
        }
    }
    return stack.isEmpty();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Stack of Expected Closing Brackets (Optimized)

Push the closing bracket that is expected for every opening bracket, so a closing bracket is checked with one comparison and no map. An odd length can be rejected immediately. This is the version implemented in the repository.

```java
public static boolean isValid(String s) {
    if (s.length() % 2 != 0) return false;
    Deque<Character> stack = new ArrayDeque<>();
    for (char c : s.toCharArray()) {
        if (c == '(') stack.push(')');
        else if (c == '{') stack.push('}');
        else if (c == '[') stack.push(']');
        else if (stack.isEmpty() || stack.pop() != c) return false;
    }
    return stack.isEmpty();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Repeated Replacement | O(n^2) | O(n) |
| 2 | Stack with a Matching Map | O(n) | O(n) |
| 3 | Stack of Expected Closing Brackets (Optimized) | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### ValidParentheses.java

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class ValidParentheses {

    public static void main(String[] args) {
        String[] inputs = {"()", "()[]{}", "(]", "([)]", "{[]}"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + isValid(s));
            System.out.println();
        }
    }

    public static boolean isValid(String s) {
        if (s == null || s.length() % 2 != 0) {
            return false;
        }

        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(')');
            } else if (c == '{') {
                stack.push('}');
            } else if (c == '[') {
                stack.push(']');
            } else if (stack.isEmpty() || stack.pop() != c) {
                return false;
            }
        }
        return stack.isEmpty();
    }
}
```
