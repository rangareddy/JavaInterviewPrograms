# Basic Calculator

Evaluate a string expression made of non-negative integers, `+`, `-`, parentheses and spaces. The `-` can also be a unary operator (for example `-(2+3)`), and the expression is always valid. Do not use `eval`.

## Algorithm Strategy (Running Sum with a Stack of Signs)

1. Keep a running `result`, the current `sign` (`+1` or `-1`) applying to the next number, and a `number` being read digit by digit.
2. On `+` or `-`, add `sign * number` to `result`, reset `number` and set `sign` to the new operator.
3. On `(`, push the current `result` and `sign` onto a stack, then restart with `result = 0` and `sign = 1` to evaluate the inside of the parentheses as its own expression.
4. On `)`, finish the pending number, then combine: `result = savedResult + savedSign * result`. At the end of the string add the last number.
5. A leading `-` or a `-` right after `(` works without special cases, because `result` is 0 at that point.

## Example walkthrough

- For `1 - (4 - (5 - 1))`: the innermost group is `5 - 1 = 4`, the next is `4 - 4 = 0`, and the whole expression is `1 - 0 = 1`.
- For `(1+(4+5+2)-3)+(6+8)`: the groups evaluate to 9 and 14, and the result is `23`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass over the characters.
- **Space Complexity:** `O(n)` — The stack depth is the nesting depth of the parentheses.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursive Descent

Parse with the grammar `expr = term (("+" | "-") term)*` where a term is a number, a parenthesised expression or a `-` followed by a term.

```java
private static int pos;

public static int calculateRecursive(String s) {
    pos = 0;
    return parseExpr(s.replace(" ", ""));
}

private static int parseExpr(String s) {
    int value = parseTerm(s);
    while (pos < s.length() && (s.charAt(pos) == '+' || s.charAt(pos) == '-')) {
        char op = s.charAt(pos++);
        int rhs = parseTerm(s);
        value = op == '+' ? value + rhs : value - rhs;
    }
    return value;
}

private static int parseTerm(String s) {
    char c = s.charAt(pos);
    if (c == '-') { pos++; return -parseTerm(s); }
    if (c == '(') {
        pos++;
        int value = parseExpr(s);
        pos++; // skip ')'
        return value;
    }
    int number = 0;
    while (pos < s.length() && Character.isDigit(s.charAt(pos))) number = number * 10 + (s.charAt(pos++) - '0');
    return number;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n) recursion depth

### Approach 2: Iterative with a Stack (Optimized)

Evaluate left to right, saving the running result and sign on a stack at each `(` and restoring them at the matching `)`. This is the version implemented in the repository.

```java
public static int calculate(String s) {
    Deque<Integer> stack = new ArrayDeque<>();
    int result = 0;
    int number = 0;
    int sign = 1;
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (Character.isDigit(c)) {
            number = number * 10 + (c - '0');
        } else if (c == '+' || c == '-') {
            result += sign * number;
            number = 0;
            sign = c == '+' ? 1 : -1;
        } else if (c == '(') {
            stack.push(result);
            stack.push(sign);
            result = 0;
            sign = 1;
        } else if (c == ')') {
            result += sign * number;
            number = 0;
            result = stack.pop() * result + stack.pop();
        }
    }
    return result + sign * number;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursive Descent | O(n) | O(n) recursion depth |
| 2 | Iterative with a Stack (Optimized) | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### BasicCalculator.java

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class BasicCalculator {

    public static void main(String[] args) {
        String[] inputs = {"1 + 1", " 2-1 + 2 ", "(1+(4+5+2)-3)+(6+8)", "1 - (4 - (5 - 1))", "-(2 + 3)"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + calculate(s));
            System.out.println();
        }
    }

    public static int calculate(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        int result = 0;
        int number = 0;
        int sign = 1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                number = number * 10 + (c - '0');
            } else if (c == '+' || c == '-') {
                result += sign * number;
                number = 0;
                sign = c == '+' ? 1 : -1;
            } else if (c == '(') {
                stack.push(result);
                stack.push(sign);
                result = 0;
                sign = 1;
            } else if (c == ')') {
                result += sign * number;
                number = 0;
                result = stack.pop() * result + stack.pop();
            }
        }
        return result + sign * number;
    }
}
```
