# Min Stack

Design a stack that supports `push`, `pop`, `top` and retrieving the minimum element, each in constant time.

## Algorithm Strategy (Store the Minimum Together with Every Element)

1. Keep a single stack of pairs `(value, minimum so far)`.
2. `push(x)`: the new minimum is `min(x, minimum of the current top)` (or `x` for an empty stack); push the pair.
3. `pop()` and `top()` simply remove or read the top pair's value.
4. `getMin()` returns the minimum stored in the top pair. Because every pair remembers the minimum of everything below it, the minimum is correct again right after a `pop`.

## Example walkthrough

- push(-2), push(0), push(-3) stores the pairs (-2,-2), (0,-2), (-3,-3); `getMin()` is -3.
- After `pop()` the top pair is (0,-2): `top()` is 0 and `getMin()` is -2.

## Complexity Analysis

- **Time Complexity:** `O(1)` — for every operation.
- **Space Complexity:** `O(n)` — One pair per stored element.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Two Stacks (Values and Minimums)

One stack holds the values, a second stack holds the running minimum at each depth.

```java
static class TwoStackMin {
    private final Deque<Integer> values = new ArrayDeque<>(), minimums = new ArrayDeque<>();

    public void push(int value) {
        values.push(value);
        minimums.push(minimums.isEmpty() ? value : Math.min(value, minimums.peek()));
    }
    public void pop() { values.pop(); minimums.pop(); }
    public int top() { return values.peek(); }
    public int getMin() { return minimums.peek(); }
}
```

- **Time Complexity:** O(1) per operation
- **Space Complexity:** O(n)

### Approach 2: Single Stack of (value, minimum) Pairs (Optimized)

Store each element together with the minimum at that moment in one stack. This is the version implemented in the repository.

```java
static class PairMin {
    private final Deque<int[]> stack = new ArrayDeque<>();

    public void push(int value) { stack.push(new int[]{value, stack.isEmpty() ? value : Math.min(value, stack.peek()[1])}); }
    public void pop() { stack.pop(); }
    public int top() { return stack.peek()[0]; }
    public int getMin() { return stack.peek()[1]; }
}
```

- **Time Complexity:** O(1) per operation
- **Space Complexity:** O(n)

### Approach 3: Linked Nodes that Remember the Minimum

A hand-written singly linked stack: each node stores its value, the minimum of the stack at that point and the node below it. Pushing and popping only move the head pointer; no library collection is used.

```java
static class LinkedMin {
    private static class Node {
        final int value, min;
        final Node next;
        Node(int value, int min, Node next) { this.value = value; this.min = min; this.next = next; }
    }
    private Node head;

    public void push(int value) { head = new Node(value, head == null ? value : Math.min(value, head.min), head); }
    public void pop() { head = head.next; }
    public int top() { return head.value; }
    public int getMin() { return head.min; }
}
```

- **Time Complexity:** O(1) per operation
- **Space Complexity:** O(n)

### Approach 4: Single Stack Storing Differences from the Minimum

Keep only one `long` per element: `value - min` at the time of the push. A negative difference marks a push that created a new minimum, from which the previous minimum can be restored on `pop`. Only one extra variable (`min`) is needed.

```java
static class DifferenceMin {
    private final Deque<Long> stack = new ArrayDeque<>();
    private long min;

    public void push(int value) {
        if (stack.isEmpty()) { stack.push(0L); min = value; return; }
        stack.push(value - min);
        if (value < min) min = value;
    }

    public void pop() {
        long diff = stack.pop();
        if (diff < 0) min = min - diff;          // the popped element was the minimum: restore the previous one
    }

    public int top() {
        long diff = stack.peek();
        return (int) (diff > 0 ? min + diff : min);
    }

    public int getMin() { return (int) min; }
}
```

- **Time Complexity:** O(1) per operation
- **Space Complexity:** O(n) (one long per element)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Two Stacks (Values and Minimums) | O(1) per operation | O(n) |
| 2 | Single Stack of (value, minimum) Pairs (Optimized) | O(1) per operation | O(n) |
| 3 | Linked Nodes that Remember the Minimum | O(1) per operation | O(n) |
| 4 | Single Stack Storing Differences from the Minimum | O(1) per operation | O(n) (one long per element) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### MinStack.java

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class MinStack {

    private final Deque<int[]> stack = new ArrayDeque<>();      // {value, minimum of the stack up to this value}

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        System.out.println("Input  : push(-2), push(0), push(-3)");
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println("Output : getMin() = " + minStack.getMin());
        minStack.pop();
        System.out.println("Input  : pop()");
        System.out.println("Output : top() = " + minStack.top() + ", getMin() = " + minStack.getMin());
    }

    public void push(int value) {
        int min = stack.isEmpty() ? value : Math.min(value, stack.peek()[1]);
        stack.push(new int[]{value, min});
    }

    public void pop() {
        stack.pop();
    }

    public int top() {
        return stack.peek()[0];
    }

    public int getMin() {
        return stack.peek()[1];
    }
}
```
