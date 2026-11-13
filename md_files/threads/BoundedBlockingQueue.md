# Thread-Safe Blocking Queue

Implement a bounded, thread-safe blocking queue. `put` blocks while the queue is full and `take` blocks while it is empty, and any number of producer and consumer threads may use it at the same time.

## Algorithm Strategy (ReentrantLock with Two Conditions)

1. Store the items in a circular array of fixed capacity with `head`, `tail` and `count` fields, all guarded by one `ReentrantLock`.
2. Create two `Condition` objects from that lock: `notFull` (producers wait on it) and `notEmpty` (consumers wait on it).
3. `put` takes the lock, waits on `notFull` in a `while` loop while `count == capacity`, stores the item and signals `notEmpty`.
4. `take` takes the lock, waits on `notEmpty` in a `while` loop while `count == 0`, removes the item and signals `notFull`.
5. The condition is re-checked in a loop because a woken thread may find that another thread already used the free slot or item (and spurious wake-ups are allowed). Two separate conditions mean a signal only wakes a thread that can actually make progress.

## Example walkthrough

- Capacity 2 and a consumer that starts first: `take` finds `count == 0` and waits on `notEmpty`.
- A producer calls `put(1)`, stores it, signals `notEmpty` and the consumer wakes up with item `1`. Two more `put` calls fill the queue; a third `put` waits on `notFull` until the next `take` signals it.
- Items leave in the order they entered (FIFO) and `size()` never exceeds the capacity.

## Complexity Analysis

- **Time Complexity:** `O(1)` — Per `put` or `take`, not counting the time spent waiting.
- **Space Complexity:** `O(capacity)` — The circular array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: synchronized with wait / notifyAll

One monitor guards the queue. Producers and consumers wait on the same monitor, so every state change must use `notifyAll()` to be sure the right kind of thread wakes up (a single `notify()` could wake a thread of the wrong kind and lose the signal).

```java
class SynchronizedQueue<T> {
    private final Object[] items;
    private int head, tail, count;

    SynchronizedQueue(int capacity) { items = new Object[capacity]; }

    synchronized void put(T item) throws InterruptedException {
        while (count == items.length) wait();
        items[tail] = item;
        tail = (tail + 1) % items.length;
        count++;
        notifyAll();
    }

    @SuppressWarnings("unchecked")
    synchronized T take() throws InterruptedException {
        while (count == 0) wait();
        T item = (T) items[head];
        items[head] = null;
        head = (head + 1) % items.length;
        count--;
        notifyAll();
        return item;
    }

    synchronized int size() { return count; }
}
```

- **Time Complexity:** O(1) per operation, but every signal wakes all waiters
- **Space Complexity:** O(capacity)

### Approach 2: Two Semaphores and a Mutex

A `slots` semaphore counts free positions, a `filled` semaphore counts stored items and a binary `mutex` guards the array indexes. Correct, but three synchronizers have to be kept consistent by hand and the order of the acquires matters (taking `mutex` before `slots` would deadlock).

```java
class SemaphoreQueue<T> {
    private final Object[] items;
    private int head, tail, count;
    private final Semaphore slots;
    private final Semaphore filled = new Semaphore(0);
    private final Semaphore mutex = new Semaphore(1);

    SemaphoreQueue(int capacity) {
        items = new Object[capacity];
        slots = new Semaphore(capacity);
    }

    void put(T item) throws InterruptedException {
        slots.acquire();
        mutex.acquire();
        items[tail] = item;
        tail = (tail + 1) % items.length;
        count++;
        mutex.release();
        filled.release();
    }

    @SuppressWarnings("unchecked")
    T take() throws InterruptedException {
        filled.acquire();
        mutex.acquire();
        T item = (T) items[head];
        items[head] = null;
        head = (head + 1) % items.length;
        count--;
        mutex.release();
        slots.release();
        return item;
    }

    int size() {
        mutex.acquireUninterruptibly();
        try { return count; } finally { mutex.release(); }
    }
}
```

- **Time Complexity:** O(1) per operation
- **Space Complexity:** O(capacity)

### Approach 3: ReentrantLock with notFull and notEmpty Conditions (Optimized)

Use one lock and two conditions so a `put` only wakes consumers and a `take` only wakes producers. This is the version implemented in the repository.

```java
private final Object[] items;
private int head;
private int tail;
private int count;
private final ReentrantLock lock = new ReentrantLock();
private final Condition notFull = lock.newCondition();
private final Condition notEmpty = lock.newCondition();

public BoundedBlockingQueue(int capacity) {
    if (capacity <= 0) {
        throw new IllegalArgumentException("capacity must be positive");
    }
    items = new Object[capacity];
}

public void put(T item) throws InterruptedException {
    lock.lockInterruptibly();
    try {
        while (count == items.length) {
            notFull.await();
        }
        items[tail] = item;
        tail = (tail + 1) % items.length;
        count++;
        notEmpty.signal();
    } finally {
        lock.unlock();
    }
}

@SuppressWarnings("unchecked")
public T take() throws InterruptedException {
    lock.lockInterruptibly();
    try {
        while (count == 0) {
            notEmpty.await();
        }
        T item = (T) items[head];
        items[head] = null;
        head = (head + 1) % items.length;
        count--;
        notFull.signal();
        return item;
    } finally {
        lock.unlock();
    }
}

public int size() {
    lock.lock();
    try {
        return count;
    } finally {
        lock.unlock();
    }
}
```

- **Time Complexity:** O(1) per operation
- **Space Complexity:** O(capacity)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | synchronized with wait / notifyAll | O(1) per operation, but every signal wakes all waiters | O(capacity) |
| 2 | Two Semaphores and a Mutex | O(1) per operation | O(capacity) |
| 3 | ReentrantLock with notFull and notEmpty Conditions (Optimized) | O(1) per operation | O(capacity) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### BoundedBlockingQueue.java

```java
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBlockingQueue<T> {

    public static void main(String[] args) throws InterruptedException {
        BoundedBlockingQueue<Integer> queue = new BoundedBlockingQueue<>(2);
        AtomicInteger sum = new AtomicInteger();
        AtomicInteger taken = new AtomicInteger();

        Thread[] threads = new Thread[4];
        for (int p = 0; p < 2; p++) {
            threads[p] = new Thread(() -> {
                try {
                    for (int i = 1; i <= 5; i++) {
                        queue.put(i);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        for (int c = 2; c < 4; c++) {
            threads[c] = new Thread(() -> {
                try {
                    for (int i = 0; i < 5; i++) {
                        sum.addAndGet(queue.take());
                        taken.incrementAndGet();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        System.out.println("Input  : 2 producers put 1..5, 2 consumers take 5 items each, capacity = 2");
        System.out.println("Output : taken = " + taken.get() + ", sum = " + sum.get() + ", left in queue = " + queue.size());
    }

    private final Object[] items;
    private int head;
    private int tail;
    private int count;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public BoundedBlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        items = new Object[capacity];
    }

    public void put(T item) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[tail] = item;
            tail = (tail + 1) % items.length;
            count++;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    @SuppressWarnings("unchecked")
    public T take() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            T item = (T) items[head];
            items[head] = null;
            head = (head + 1) % items.length;
            count--;
            notFull.signal();
            return item;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }
}
```
