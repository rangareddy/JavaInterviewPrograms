# Print Numbers in Sequence with Three Threads

Three threads must print the numbers from 1 to `n` in order, taking turns: thread 1 prints 1, thread 2 prints 2, thread 3 prints 3, thread 1 prints 4 and so on.

## Algorithm Strategy (Turn Variable with wait / notifyAll)

1. Keep a shared counter `next` (the number to print next) and a shared lock object.
2. Thread `t` (0, 1 or 2) is responsible for the numbers where `(number - 1) % 3 == t`.
3. In a loop, a thread holds the lock and calls `wait()` while it is not its turn (`(next - 1) % 3 != t`) and the work is not finished.
4. When it is its turn, it prints `next`, increments it and calls `notifyAll()` so the next thread can run. Stop when `next > n`.

## Example walkthrough

- For `n = 7`: the threads print `T1:1, T2:2, T3:3, T1:4, T2:5, T3:6, T1:7`.
- No matter how the scheduler interleaves the threads, a thread waiting for its turn can never print out of order.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each number is printed once; each print wakes all waiting threads.
- **Space Complexity:** `O(1)` — A counter and a lock.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: wait / notifyAll on a Turn Counter (Optimized)

One shared counter decides whose turn it is; the others wait. This is the version implemented in the repository.

```java
static List<String> runWaitNotify(int limit, int threadCount) throws InterruptedException {
    Object lock = new Object();
    int[] next = {1};
    List<String> printed = new ArrayList<>();
    Thread[] threads = new Thread[threadCount];
    for (int t = 0; t < threadCount; t++) {
        final int id = t;
        threads[t] = new Thread(() -> {
            synchronized (lock) {
                while (next[0] <= limit) {
                    if ((next[0] - 1) % threadCount == id) { printed.add("T" + (id + 1) + ":" + next[0]++); lock.notifyAll(); }
                    else { try { lock.wait(); } catch (InterruptedException e) { return; } }
                }
                lock.notifyAll();
            }
        });
        threads[t].start();
    }
    for (Thread thread : threads) thread.join();
    return printed;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 2: Semaphores in a Ring

Thread `i` waits on semaphore `i` and releases semaphore `i + 1`; only the first one starts with a permit.

```java
static List<String> runSemaphores(int limit, int threadCount) throws InterruptedException {
    Semaphore[] turns = new Semaphore[threadCount];
    for (int i = 0; i < threadCount; i++) turns[i] = new Semaphore(i == 0 ? 1 : 0);
    List<String> printed = Collections.synchronizedList(new ArrayList<>());
    Thread[] threads = new Thread[threadCount];
    for (int t = 0; t < threadCount; t++) {
        final int id = t;
        threads[t] = new Thread(() -> {
            try {
                for (int number = id + 1; number <= limit; number += threadCount) {
                    turns[id].acquire();
                    printed.add("T" + (id + 1) + ":" + number);
                    turns[(id + 1) % threadCount].release();
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        threads[t].start();
    }
    for (Thread thread : threads) thread.join();
    return printed;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(threads)

### Approach 3: ReentrantLock with Conditions

One `Condition` per thread so that only the thread whose turn it is gets signalled.

```java
static List<String> runConditions(int limit, int threadCount) throws InterruptedException {
    ReentrantLock lock = new ReentrantLock();
    Condition[] turn = new Condition[threadCount];
    for (int i = 0; i < threadCount; i++) turn[i] = lock.newCondition();
    int[] next = {1};
    List<String> printed = new ArrayList<>();
    Thread[] threads = new Thread[threadCount];
    for (int t = 0; t < threadCount; t++) {
        final int id = t;
        threads[t] = new Thread(() -> {
            lock.lock();
            try {
                while (next[0] <= limit) {
                    if ((next[0] - 1) % threadCount == id) {
                        printed.add("T" + (id + 1) + ":" + next[0]++);
                        turn[(next[0] - 1) % threadCount].signal();
                    } else {
                        turn[id].awaitUninterruptibly();
                    }
                }
                for (Condition c : turn) c.signalAll();
            } finally { lock.unlock(); }
        });
        threads[t].start();
    }
    for (Thread thread : threads) thread.join();
    return printed;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(threads)

### Approach 4: Atomic Counter with Spinning

Each thread spins (yielding the CPU) until the shared `AtomicInteger` reaches a number it owns. Simple, but burns CPU while waiting.

```java
static List<String> runSpin(int limit, int threadCount) throws InterruptedException {
    AtomicInteger next = new AtomicInteger(1);
    List<String> printed = Collections.synchronizedList(new ArrayList<>());
    Thread[] threads = new Thread[threadCount];
    for (int t = 0; t < threadCount; t++) {
        final int id = t;
        threads[t] = new Thread(() -> {
            for (int number = id + 1; number <= limit; number += threadCount) {
                while (next.get() != number) Thread.yield();
                printed.add("T" + (id + 1) + ":" + number);
                next.incrementAndGet();
            }
        });
        threads[t].start();
    }
    for (Thread thread : threads) thread.join();
    return printed;
}
```

- **Time Complexity:** O(n) plus spinning
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | wait / notifyAll on a Turn Counter (Optimized) | O(n) | O(1) |
| 2 | Semaphores in a Ring | O(n) | O(threads) |
| 3 | ReentrantLock with Conditions | O(n) | O(threads) |
| 4 | Atomic Counter with Spinning | O(n) plus spinning | O(1) |

### Optimized Solution

The optimized solution is Approach 1 above, which is also the version implemented in this repository.

## Source Code

### PrintNumbersWithThreeThreads.java

```java
import java.util.ArrayList;
import java.util.List;

public class PrintNumbersWithThreeThreads {

    private final int limit;
    private final int threadCount;
    private final List<String> printed = new ArrayList<>();
    private int next = 1;

    public PrintNumbersWithThreeThreads(int limit, int threadCount) {
        this.limit = limit;
        this.threadCount = threadCount;
    }

    public static void main(String[] args) throws InterruptedException {
        int n = 10;
        PrintNumbersWithThreeThreads printer = new PrintNumbersWithThreeThreads(n, 3);
        System.out.println("Input  : n = " + n + ", threads = 3");
        System.out.println("Output : " + printer.run());
    }

    /** Runs the threads and returns the printed sequence such as {@code [T1:1, T2:2, T3:3]}. */
    public List<String> run() throws InterruptedException {
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            final int id = i;
            threads[i] = new Thread(() -> work(id), "T" + (id + 1));
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        return printed;
    }

    private void work(int id) {
        synchronized (this) {
            while (next <= limit) {
                if ((next - 1) % threadCount == id) {
                    printed.add("T" + (id + 1) + ":" + next);
                    next++;
                    notifyAll();
                } else {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
            notifyAll();
        }
    }
}
```
