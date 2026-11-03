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

### Approach 3: char[] Array as the Stack

The same expected-closer stack implemented with a plain `char[]` and an index, which avoids boxing and the `Deque` overhead.

```java
public static boolean isValidArray(String s) {
    char[] stack = new char[s.length()];
    int top = 0;
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (c == '(') stack[top++] = ')';
        else if (c == '{') stack[top++] = '}';
        else if (c == '[') stack[top++] = ']';
        else if (top == 0 || stack[--top] != c) return false;
    }
    return top == 0;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Recursive Descent Parser

View the string as a grammar: a sequence of bracket pairs, each containing a sequence. A recursive function parses a sequence until it meets the closing bracket it expects. The recursion replaces the explicit stack.

```java
public static boolean isValidRecursive(String s) {
    int[] position = {0};
    return parse(s, position, '\0') && position[0] == s.length();
}

// parses a sequence of balanced groups; consumes the 'expectedCloser' (if any) and returns true when it is found
private static boolean parse(String s, int[] position, char expectedCloser) {
    while (position[0] < s.length()) {
        char c = s.charAt(position[0]);
        char closer = c == '(' ? ')' : c == '{' ? '}' : c == '[' ? ']' : '\0';
        if (closer != '\0') {
            position[0]++;
            if (!parse(s, position, closer)) return false;
        } else if (c == expectedCloser) {
            position[0]++;
            return true;
        } else {
            return false;
        }
    }
    return expectedCloser == '\0';
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n) recursion stack

### Approach 5: Interval Dynamic Programming

`valid[i][j]` says whether the substring `s[i..j)` is balanced: it is empty, or its first character is opened and closed by a matching bracket at some position `k` such that the inside `s[i+1..k)` and the rest `s[k+1..j)` are both balanced. Cubic, but it shows the recursive structure of the language.

```java
public static boolean isValidDp(String s) {
    int n = s.length();
    if (n % 2 != 0) return false;
    boolean[][] valid = new boolean[n + 1][n + 1];
    for (int i = 0; i <= n; i++) valid[i][i] = true;
    for (int length = 2; length <= n; length += 2) {
        for (int i = 0; i + length <= n; i++) {
            int j = i + length;
            for (int k = i + 1; k < j; k += 2) {
                if (matches(s.charAt(i), s.charAt(k)) && valid[i + 1][k] && valid[k + 1][j]) { valid[i][j] = true; break; }
            }
        }
    }
    return valid[0][n];
}

private static boolean matches(char open, char close) {
    return (open == '(' && close == ')') || (open == '{' && close == '}') || (open == '[' && close == ']');
}
```

- **Time Complexity:** O(n^3)
- **Space Complexity:** O(n^2)

### Approach 6: StringBuilder as the Stack

Use a `StringBuilder` as the stack: append the expected closer on an opening bracket, compare with and delete the last character on a closing bracket.

```java
public static boolean isValidBuilder(String s) {
    StringBuilder stack = new StringBuilder();
    for (char c : s.toCharArray()) {
        if (c == '(') stack.append(')');
        else if (c == '{') stack.append('}');
        else if (c == '[') stack.append(']');
        else {
            if (stack.length() == 0 || stack.charAt(stack.length() - 1) != c) return false;
            stack.setLength(stack.length() - 1);
        }
    }
    return stack.length() == 0;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 7: Stack of Expected Closing Brackets (Optimized)

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
| 3 | char[] Array as the Stack | O(n) | O(n) |
| 4 | Recursive Descent Parser | O(n) | O(n) recursion stack |
| 5 | Interval Dynamic Programming | O(n^3) | O(n^2) |
| 6 | StringBuilder as the Stack | O(n) | O(n) |
| 7 | Stack of Expected Closing Brackets (Optimized) | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

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
