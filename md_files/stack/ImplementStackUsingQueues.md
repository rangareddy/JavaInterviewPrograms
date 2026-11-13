# Implement Stack Using Queues

Implement a last-in-first-out stack with `push`, `pop`, `top` and `empty` using only standard queue operations (add to the back, remove from the front, peek at the front, size, is-empty).

## Algorithm Strategy (Single Queue with Rotation on Push)

1. A queue returns the oldest element first, but a stack must return the newest. Make the newest element the front of the queue.
2. On `push(x)`, add `x` to the back, then rotate the queue: remove the front element and add it to the back, `size - 1` times. This moves every older element behind `x`.
3. `pop`, `top` and `empty` are then plain queue operations on the front.
4. The cost moves to `push`, which is `O(n)`; the other operations are `O(1)`.

## Example walkthrough

- `push(1)`: queue `[1]`. `push(2)`: add gives `[1, 2]`, rotate once gives `[2, 1]`.
- `push(3)`: add gives `[2, 1, 3]`, rotate twice gives `[3, 2, 1]`. `pop()` removes 3, the newest.

## Complexity Analysis

- **Time Complexity:** `O(n)` — `push` rotates the queue; `pop`, `top` and `empty` are `O(1)`.
- **Space Complexity:** `O(n)` — One queue holds all elements.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Two Queues, Costly Pop

Keep the elements in insertion order in one queue; to `pop`, move all but the last element to the second queue, take the last, then swap the queues.

```java
class TwoQueueStack {
    private Queue<Integer> main = new LinkedList<>();
    private Queue<Integer> other = new LinkedList<>();

    public void push(int x) { main.add(x); }

    public int pop() {
        while (main.size() > 1) other.add(main.remove());
        int result = main.remove();
        Queue<Integer> tmp = main; main = other; other = tmp;
        return result;
    }

    public int top() {
        int result = pop();
        main.add(result);
        return result;
    }

    public boolean empty() { return main.isEmpty(); }
}
```

- **Time Complexity:** push O(1), pop/top O(n)
- **Space Complexity:** O(n)

### Approach 2: Single Queue with Rotation (Optimized)

Rotate the queue after each `push` so the newest element is always at the front; all reads are then constant time. This is the version implemented in the repository.

```java
private final Queue<Integer> queue = new LinkedList<>();

public void push(int x) {
    queue.add(x);
    for (int i = 1; i < queue.size(); i++) {
        queue.add(queue.remove());
    }
}

public int pop() {
    return queue.remove();
}

public int top() {
    return queue.peek();
}

public boolean empty() {
    return queue.isEmpty();
}
```

- **Time Complexity:** push O(n), pop/top/empty O(1)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Two Queues, Costly Pop | push O(1), pop/top O(n) | O(n) |
| 2 | Single Queue with Rotation (Optimized) | push O(n), pop/top/empty O(1) | O(n) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### ImplementStackUsingQueues.java

```java
import java.util.LinkedList;
import java.util.Queue;

public class ImplementStackUsingQueues {

    public static void main(String[] args) {
        ImplementStackUsingQueues stack = new ImplementStackUsingQueues();
        System.out.println("Input  : push(1), push(2), push(3), top(), pop(), empty()");
        stack.push(1);
        stack.push(2);
        stack.push(3);
        int top = stack.top();
        int popped = stack.pop();
        System.out.println("Output : " + top + ", " + popped + ", " + stack.empty());
        System.out.println();
        System.out.println("Input  : pop(), pop(), empty()");
        int a = stack.pop();
        int b = stack.pop();
        System.out.println("Output : " + a + ", " + b + ", " + stack.empty());
    }

    private final Queue<Integer> queue = new LinkedList<>();

    public void push(int x) {
        queue.add(x);
        for (int i = 1; i < queue.size(); i++) {
            queue.add(queue.remove());
        }
    }

    public int pop() {
        return queue.remove();
    }

    public int top() {
        return queue.peek();
    }

    public boolean empty() {
        return queue.isEmpty();
    }
}
```
