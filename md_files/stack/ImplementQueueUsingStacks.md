# Implement Queue Using Stacks

Implement a first-in-first-out queue with `push`, `pop`, `peek` and `empty` using only two stacks.

## Algorithm Strategy (Input Stack and Output Stack (Amortized O(1)))

1. Pushes always go on the `input` stack, which keeps the newest element on top.
2. Pops and peeks read from the `output` stack. When `output` is empty, move every element from `input` to `output` one by one; this reverses the order so the oldest element is now on top.
3. Elements are moved only when `output` is empty, so each element is moved at most once in its lifetime.
4. A single operation can cost `O(n)` (the one that triggers the transfer), but any sequence of `m` operations costs `O(m)` in total, which is the amortized `O(1)` guarantee.

## Example walkthrough

- `push(1)`, `push(2)`: `input = [2, 1]` (top first), `output = []`.
- `peek()` finds `output` empty, transfers to get `output = [1, 2]` and returns 1. `pop()` returns 1; `push(3)` goes to `input`; the next `pop()` returns 2 from `output` without any transfer.

## Complexity Analysis

- **Time Complexity:** `O(1) amortized` — `push` and `empty` are constant; `pop` and `peek` are constant amortized over a sequence of operations.
- **Space Complexity:** `O(n)` — The elements are held in the two stacks.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Costly Push

Keep the oldest element on top of a single stack by moving everything to a helper stack and back on every `push`.

```java
class CostlyPushQueue {
    private final Deque<Integer> stack = new ArrayDeque<>();
    private final Deque<Integer> helper = new ArrayDeque<>();

    public void push(int x) {
        while (!stack.isEmpty()) helper.push(stack.pop());
        stack.push(x);
        while (!helper.isEmpty()) stack.push(helper.pop());
    }

    public int pop() { return stack.pop(); }
    public int peek() { return stack.peek(); }
    public boolean empty() { return stack.isEmpty(); }
}
```

- **Time Complexity:** push O(n), pop/peek/empty O(1)
- **Space Complexity:** O(n)

### Approach 2: Two Stacks with Lazy Transfer (Optimized)

Reverse the elements only when the output stack runs dry, so every element is moved at most once. This is the version implemented in the repository.

```java
private final Deque<Integer> input = new ArrayDeque<>();
private final Deque<Integer> output = new ArrayDeque<>();

public void push(int x) {
    input.push(x);
}

public int pop() {
    shiftIfNeeded();
    return output.pop();
}

public int peek() {
    shiftIfNeeded();
    return output.peek();
}

public boolean empty() {
    return input.isEmpty() && output.isEmpty();
}

private void shiftIfNeeded() {
    if (output.isEmpty()) {
        while (!input.isEmpty()) {
            output.push(input.pop());
        }
    }
}
```

- **Time Complexity:** O(1) amortized per operation
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Costly Push | push O(n), pop/peek/empty O(1) | O(n) |
| 2 | Two Stacks with Lazy Transfer (Optimized) | O(1) amortized per operation | O(n) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### ImplementQueueUsingStacks.java

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class ImplementQueueUsingStacks {

    public static void main(String[] args) {
        ImplementQueueUsingStacks queue = new ImplementQueueUsingStacks();
        System.out.println("Input  : push(1), push(2), peek(), pop(), empty()");
        queue.push(1);
        queue.push(2);
        int peeked = queue.peek();
        int popped = queue.pop();
        System.out.println("Output : " + peeked + ", " + popped + ", " + queue.empty());
        System.out.println();
        System.out.println("Input  : push(3), pop(), pop(), empty()");
        queue.push(3);
        int first = queue.pop();
        int second = queue.pop();
        System.out.println("Output : " + first + ", " + second + ", " + queue.empty());
    }

    private final Deque<Integer> input = new ArrayDeque<>();
    private final Deque<Integer> output = new ArrayDeque<>();

    public void push(int x) {
        input.push(x);
    }

    public int pop() {
        shiftIfNeeded();
        return output.pop();
    }

    public int peek() {
        shiftIfNeeded();
        return output.peek();
    }

    public boolean empty() {
        return input.isEmpty() && output.isEmpty();
    }

    private void shiftIfNeeded() {
        if (output.isEmpty()) {
            while (!input.isEmpty()) {
                output.push(input.pop());
            }
        }
    }
}
```
