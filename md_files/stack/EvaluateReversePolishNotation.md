# Evaluate Reverse Polish Notation

Evaluate an arithmetic expression given in Reverse Polish Notation (postfix). The valid operators are `+`, `-`, `*` and `/`; division between two integers truncates toward zero, and every expression is valid.

## Algorithm Strategy (Stack of Operands)

1. Scan the tokens from left to right with a stack of integers.
2. A number is pushed on the stack.
3. An operator pops the two top operands (`b` first, then `a`), applies `a op b` (note the order for `-` and `/`) and pushes the result.
4. After the last token the stack holds exactly one value: the result.

## Example walkthrough

- For `["4", "13", "5", "/", "+"]`: push 4, 13, 5; `/` pops 5 and 13 and pushes `13 / 5 = 2`; `+` pops 2 and 4 and pushes 6.
- Result: `6`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each token is handled once.
- **Space Complexity:** `O(n)` — The stack may hold all operands.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Repeated Reduction (No Stack)

Keep the tokens in a list. Repeatedly find the first operator, replace it and the two operands before it by their result, until one token is left. Quadratic, but needs no stack.

```java
public static int evalRPNReduce(String[] tokens) {
    List<String> list = new ArrayList<>(Arrays.asList(tokens));
    while (list.size() > 1) {
        int i = 0;
        while (!isOperator(list.get(i))) i++;
        int a = Integer.parseInt(list.get(i - 2)), b = Integer.parseInt(list.get(i - 1));
        int result = apply(list.get(i), a, b);
        list.subList(i - 2, i + 1).clear();
        list.add(i - 2, String.valueOf(result));
    }
    return Integer.parseInt(list.get(0));
}

private static boolean isOperator(String token) {
    return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
}

private static int apply(String op, int a, int b) {
    switch (op) {
        case "+": return a + b;
        case "-": return a - b;
        case "*": return a * b;
        default: return a / b;
    }
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 2: Recursion from the Last Token

The last token of a valid expression is its root operator; its right operand ends right before it and its left operand ends where the right operand begins. A recursive function consumes the tokens from the end.

```java
public static int evalRPNRecursive(String[] tokens) {
    int[] position = {tokens.length - 1};
    return evaluate(tokens, position);
}

private static int evaluate(String[] tokens, int[] position) {
    String token = tokens[position[0]--];
    if (!isOperator(token)) return Integer.parseInt(token);
    int right = evaluate(tokens, position);
    int left = evaluate(tokens, position);
    return apply(token, left, right);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n) recursion stack

### Approach 3: int[] Array as the Stack

The standard stack algorithm with a plain `int[]` and an index instead of a `Deque<Integer>`: no boxing, fixed size known from the number of tokens.

```java
public static int evalRPNArray(String[] tokens) {
    int[] stack = new int[tokens.length];
    int top = 0;
    for (String token : tokens) {
        if (isOperator(token)) {
            int b = stack[--top], a = stack[--top];
            stack[top++] = apply(token, a, b);
        } else {
            stack[top++] = Integer.parseInt(token);
        }
    }
    return stack[0];
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Stack of Operands (Optimized)

Push numbers; an operator pops two operands and pushes the result. This is the version implemented in the repository.

```java
public static int evalRPN(String[] tokens) {
    Deque<Integer> stack = new ArrayDeque<>();
    for (String token : tokens) {
        if (isOperator(token)) {
            int b = stack.pop(), a = stack.pop();
            stack.push(apply(token, a, b));
        } else {
            stack.push(Integer.parseInt(token));
        }
    }
    return stack.pop();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Repeated Reduction (No Stack) | O(n^2) | O(n) |
| 2 | Recursion from the Last Token | O(n) | O(n) recursion stack |
| 3 | int[] Array as the Stack | O(n) | O(n) |
| 4 | Stack of Operands (Optimized) | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### EvaluateReversePolishNotation.java

```java
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class EvaluateReversePolishNotation {

    public static void main(String[] args) {
        String[][] inputs = {
                {"2", "1", "+", "3", "*"},
                {"4", "13", "5", "/", "+"},
                {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"},
                {"3", "-4", "+"}
        };
        for (String[] tokens : inputs) {
            System.out.println("Input  : tokens = " + Arrays.toString(tokens));
            System.out.println("Output : " + evalRPN(tokens));
            System.out.println();
        }
    }

    public static int evalRPN(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (String token : tokens) {
            switch (token) {
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-": {
                    int b = stack.pop();
                    stack.push(stack.pop() - b);
                    break;
                }
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/": {
                    int b = stack.pop();
                    stack.push(stack.pop() / b);
                    break;
                }
                default:
                    stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }
}
```
