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
