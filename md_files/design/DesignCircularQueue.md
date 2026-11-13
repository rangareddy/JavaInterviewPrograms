# Design Circular Queue

Design a circular queue of fixed capacity `k` (a ring buffer) with `enQueue(value)`, `deQueue()`, `Front()`, `Rear()`, `isEmpty()` and `isFull()`. `enQueue` and `deQueue` return whether they succeeded, and `Front`/`Rear` return `-1` when the queue is empty. No new space is allocated after construction.

## Algorithm Strategy (Array with a Head Index and a Size)

1. Allocate an array of `k` slots, an index `head` of the front element and a counter `size`.
2. The rear element is at `(head + size - 1) % k`, and the next free slot is `(head + size) % k`. The modulo makes the array wrap around, so freed slots at the front are reused.
3. `enQueue` fails if `size == k`, otherwise writes at `(head + size) % k` and increments `size`.
4. `deQueue` fails if `size == 0`, otherwise moves `head` to `(head + 1) % k` and decrements `size`.
5. Keeping `size` separately avoids the classic ambiguity of using only head and tail indices, where "empty" and "full" look the same.

## Example walkthrough

- Capacity 3: `enQueue(1)`, `enQueue(2)`, `enQueue(3)` fill the slots; `enQueue(4)` fails; `Rear()` is 3 and `isFull()` is true.
- `deQueue()` frees slot 0, so `enQueue(4)` now writes there (wrapping around) and `Rear()` returns 4.

## Complexity Analysis

- **Time Complexity:** `O(1)` — Every operation.
- **Space Complexity:** `O(k)` — The fixed array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Singly Linked List with a Size Limit

Keep head and tail nodes and a count; allocate a node per `enQueue`. It does not reuse memory the way a ring buffer does, but the logic is simple.

```java
class LinkedCircularQueue {
    private static class Node { int val; Node next; Node(int val) { this.val = val; } }
    private final int capacity;
    private int size;
    private Node head, tail;

    LinkedCircularQueue(int k) { capacity = k; }

    boolean enQueue(int value) {
        if (size == capacity) return false;
        Node node = new Node(value);
        if (tail == null) head = tail = node; else { tail.next = node; tail = node; }
        size++;
        return true;
    }

    boolean deQueue() {
        if (size == 0) return false;
        head = head.next;
        if (head == null) tail = null;
        size--;
        return true;
    }

    int front() { return size == 0 ? -1 : head.val; }
    int rear() { return size == 0 ? -1 : tail.val; }
    boolean isEmpty() { return size == 0; }
    boolean isFull() { return size == capacity; }
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(k) with an allocation per element

### Approach 2: Ring Buffer (Optimized)

Use `head` and `size` with modulo arithmetic so slots are reused and nothing is allocated after construction. This is the version implemented in the repository.

```java
private final int[] data;
private int head;
private int size;

public DesignCircularQueue(int k) {
    data = new int[k];
}

public boolean enQueue(int value) {
    if (isFull()) {
        return false;
    }
    data[(head + size) % data.length] = value;
    size++;
    return true;
}

public boolean deQueue() {
    if (isEmpty()) {
        return false;
    }
    head = (head + 1) % data.length;
    size--;
    return true;
}

public int front() {
    return isEmpty() ? -1 : data[head];
}

public int rear() {
    return isEmpty() ? -1 : data[(head + size - 1) % data.length];
}

public boolean isEmpty() {
    return size == 0;
}

public boolean isFull() {
    return size == data.length;
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(k)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Singly Linked List with a Size Limit | O(1) | O(k) with an allocation per element |
| 2 | Ring Buffer (Optimized) | O(1) | O(k) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### DesignCircularQueue.java

```java
public class DesignCircularQueue {

    public static void main(String[] args) {
        DesignCircularQueue queue = new DesignCircularQueue(3);
        System.out.println("Input  : DesignCircularQueue(3), enQueue(1), enQueue(2), enQueue(3), enQueue(4), Rear(), isFull(), deQueue(), enQueue(4), Rear()");
        boolean a = queue.enQueue(1);
        boolean b = queue.enQueue(2);
        boolean c = queue.enQueue(3);
        boolean d = queue.enQueue(4);
        int rear1 = queue.rear();
        boolean full = queue.isFull();
        boolean e = queue.deQueue();
        boolean f = queue.enQueue(4);
        int rear2 = queue.rear();
        System.out.println("Output : " + a + ", " + b + ", " + c + ", " + d + ", " + rear1 + ", " + full + ", " + e + ", " + f + ", " + rear2);
    }

    private final int[] data;
    private int head;
    private int size;

    public DesignCircularQueue(int k) {
        data = new int[k];
    }

    public boolean enQueue(int value) {
        if (isFull()) {
            return false;
        }
        data[(head + size) % data.length] = value;
        size++;
        return true;
    }

    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }
        head = (head + 1) % data.length;
        size--;
        return true;
    }

    public int front() {
        return isEmpty() ? -1 : data[head];
    }

    public int rear() {
        return isEmpty() ? -1 : data[(head + size - 1) % data.length];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == data.length;
    }
}
```
