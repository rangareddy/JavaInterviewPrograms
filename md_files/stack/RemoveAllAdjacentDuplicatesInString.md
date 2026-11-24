# Remove All Adjacent Duplicates In String

Given a string `s`, repeatedly remove two adjacent equal characters until no such pair remains. Return the final string.

## Algorithm Strategy (Stack of Survivors)

1. Scan the characters left to right with a `StringBuilder` used as a stack.
2. If the stack top equals the current character, pop it: the pair cancels.
3. Otherwise push the current character.
4. The stack content is the final string.

## Example walkthrough

- For `abbaca`: `bb` cancels, then the two `a`s become neighbours and cancel too, leaving `ca`.
- Result: `ca`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each character is pushed and popped at most once.
- **Space Complexity:** `O(n)` — The stack.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Repeated Replace

Remove any pair `cc` from the string until nothing changes. Quadratic in the worst case.

```java
public static String replaceLoop(String s) {
    String prev;
    do { prev = s; for (char c = 'a'; c <= 'z'; c++) s = s.replace("" + c + c, ""); } while (!s.equals(prev));
    return s;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 2: Stack (Optimized)

Cancel against the top of a stack. This is the version implemented in the repository.

```java
public static String stack(String s) {
    StringBuilder sb = new StringBuilder();
    for (char c : s.toCharArray()) { int t = sb.length() - 1; if (t >= 0 && sb.charAt(t) == c) sb.deleteCharAt(t); else sb.append(c); }
    return sb.toString();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Repeated Replace | O(n^2) | O(n) |
| 2 | Stack (Optimized) | O(n) | O(n) |

## Source Code

### RemoveAllAdjacentDuplicatesInString.java

```java
public class RemoveAllAdjacentDuplicatesInString {

    public static void main(String[] args) {
        String[] inputs = {"abbaca", "azxxzy", "aaaa", "abc", ""};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : \"" + removeDuplicates(s) + "\"");
            System.out.println();
        }
    }

    public static String removeDuplicates(String s) {
        if (s == null) {
            return "";
        }
        StringBuilder stack = new StringBuilder();
        for (char c : s.toCharArray()) {
            int top = stack.length() - 1;
            if (top >= 0 && stack.charAt(top) == c) {
                stack.deleteCharAt(top);
            } else {
                stack.append(c);
            }
        }
        return stack.toString();
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a removal can expose a new adjacent pair, and a stack handles that naturally because the new neighbour is simply the new top.

**Input:** `s = "abbaca"`.

| Char | Stack before | Action | Stack after |
|------|--------------|--------|-------------|
| a | | push | a |
| b | a | push | ab |
| b | ab | cancels with b | a |
| a | a | cancels with a | |
| c | | push | c |
| a | c | push | ca |

**Output:** `ca`.
