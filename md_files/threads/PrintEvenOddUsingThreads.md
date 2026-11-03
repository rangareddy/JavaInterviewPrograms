# Print Even and Odd Numbers Using Two Threads

Utility class to print numbers in order with one thread printing the odd numbers and another printing the even numbers.

## Algorithm Strategy (Inter-thread Communication with wait/notify)

1. Both threads share a single `Printer` object, which acts as the monitor (lock) they coordinate on.
2. The `Printer` keeps a `volatile boolean isOdd` flag that records whose turn it is: `false` means an odd number must be printed next, `true` means an even number.
3. `printOdd` and `printEven` are `synchronized`, so only one thread runs inside them at a time.
4. A thread that finds it is not its turn calls `wait()` inside a `while` loop (the loop protects against spurious wake-ups) and releases the lock.
5. When it is its turn, the thread prints its number, flips the flag and calls `notify()` to wake the other thread.
6. The odd thread prints `1, 3, 5, ...` and the even thread prints `2, 4, 6, ...` until `max`. Because the turns strictly alternate, the output is `1 2 3 4 ...`.

## Example walkthrough for `max = 4`

- Even thread starts first, finds it is not its turn and waits.
- Odd thread prints `1`, flips the flag, notifies; its next call (`3`) now waits.
- Even thread wakes, prints `2`, flips the flag, notifies; and so on until `1 2 3 4` is printed.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each number from 1 to `max` is printed exactly once.
- **Space Complexity:** `O(1)` — Only the shared flag and the two threads.

## Approaches

The same problem can be solved in several ways. They are listed from the simplest to the most efficient.

### Approach 1: wait() / notify() with a Shared Monitor

Both threads share one lock object and a flag that says whose turn it is. A thread waits in a `while` loop until it is its turn, prints, flips the flag and calls `notify()`. This is the version implemented in the repository.

```java
static class WaitNotifyPrinter {
    private boolean oddTurn = true;

    synchronized void print(int number, boolean odd) throws InterruptedException {
        while (oddTurn != odd) wait();
        out.append(number).append(' ');
        oddTurn = !oddTurn;
        notifyAll();
    }
}

public static String waitNotify(int max) throws InterruptedException {
    out.setLength(0);
    WaitNotifyPrinter printer = new WaitNotifyPrinter();
    Thread odd = new Thread(() -> run(printer, max, 1));
    Thread even = new Thread(() -> run(printer, max, 2));
    odd.start(); even.start(); odd.join(); even.join();
    return out.toString().trim();
}

private static void run(WaitNotifyPrinter p, int max, int start) {
    try {
        for (int i = start; i <= max; i += 2) p.print(i, start == 1);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 2: ReentrantLock + Condition

The same hand-over as `wait/notify`, but with an explicit lock and a `Condition`, which is more flexible (timeouts, multiple conditions, fairness).

```java
public static String lockCondition(int max) throws InterruptedException {
    out.setLength(0);
    ReentrantLock lock = new ReentrantLock();
    Condition turn = lock.newCondition();
    boolean[] oddTurn = {true};

    Runnable odd = () -> printWithLock(lock, turn, oddTurn, max, 1);
    Runnable even = () -> printWithLock(lock, turn, oddTurn, max, 2);
    Thread t1 = new Thread(odd), t2 = new Thread(even);
    t1.start(); t2.start(); t1.join(); t2.join();
    return out.toString().trim();
}

private static void printWithLock(ReentrantLock lock, Condition turn, boolean[] oddTurn, int max, int start) {
    for (int i = start; i <= max; i += 2) {
        lock.lock();
        try {
            while (oddTurn[0] != (start == 1)) turn.awaitUninterruptibly();
            out.append(i).append(' ');
            oddTurn[0] = !oddTurn[0];
            turn.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 3: BlockingQueue Token Passing

Give each thread its own `BlockingQueue` and let a single token travel between them: a thread blocks on `take()` of its queue, prints its number, and hands the token to the other thread with `put()`. The queues do all the waiting and signalling.

```java
public static String blockingQueues(int max) throws InterruptedException {
    out.setLength(0);
    BlockingQueue<Integer> oddTurn = new ArrayBlockingQueue<>(1), evenTurn = new ArrayBlockingQueue<>(1);
    Thread odd = new Thread(() -> {
        try {
            for (int i = 1; i <= max; i += 2) {
                oddTurn.take();
                out.append(i).append(' ');
                evenTurn.put(1);
            }
        } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    });
    Thread even = new Thread(() -> {
        try {
            for (int i = 2; i <= max; i += 2) {
                evenTurn.take();
                out.append(i).append(' ');
                oddTurn.put(1);
            }
        } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    });
    oddTurn.put(1);                              // the odd thread starts
    odd.start(); even.start(); odd.join(); even.join();
    return out.toString().trim();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 4: Volatile Turn Flag with Spinning

Both threads watch a `volatile` variable that holds the next number to print and busy-wait (yielding the CPU) until it is their turn. No locks at all, but the waiting thread burns CPU, so it only suits very short waits.

```java
private static volatile int next;

public static String spinning(int max) throws InterruptedException {
    out.setLength(0);
    next = 1;
    Thread odd = new Thread(() -> printWhenTurn(1, max));
    Thread even = new Thread(() -> printWhenTurn(2, max));
    odd.start(); even.start(); odd.join(); even.join();
    return out.toString().trim();
}

private static void printWhenTurn(int first, int max) {
    for (int i = first; i <= max; i += 2) {
        while (next != i) Thread.yield();           // busy-wait until it is this number's turn
        out.append(i).append(' ');
        next = i + 1;
    }
}
```

- **Time Complexity:** O(n) (plus busy waiting)
- **Space Complexity:** O(1)

### Approach 5: LockSupport park / unpark

Each thread parks itself (`LockSupport.park`) while it is not its turn and unparks the other thread after printing. This is the low-level building block under locks and conditions; the loop around `park` guards against spurious wake-ups.

```java
private static volatile int turnNumber;
private static Thread oddThread, evenThread;

public static String parking(int max) throws InterruptedException {
    out.setLength(0);
    turnNumber = 1;
    oddThread = new Thread(() -> parkedPrint(1, max, true));
    evenThread = new Thread(() -> parkedPrint(2, max, false));
    oddThread.start(); evenThread.start(); oddThread.join(); evenThread.join();
    return out.toString().trim();
}

private static void parkedPrint(int first, int max, boolean isOdd) {
    for (int i = first; i <= max; i += 2) {
        while (turnNumber != i) LockSupport.park();
        out.append(i).append(' ');
        turnNumber = i + 1;
        LockSupport.unpark(isOdd ? evenThread : oddThread);
    }
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 6: CompletableFuture Chain on Two Single-Thread Executors

Describe the whole run as a chain of tasks: task `i` runs after task `i - 1` and is executed by the odd or the even single-thread executor. The ordering is guaranteed by the chain itself, so no thread ever waits on a lock or a flag.

```java
public static String futures(int max) {
    out.setLength(0);
    ExecutorService odd = Executors.newSingleThreadExecutor(), even = Executors.newSingleThreadExecutor();
    CompletableFuture<Void> chain = CompletableFuture.completedFuture(null);
    for (int i = 1; i <= max; i++) {
        final int number = i;
        chain = chain.thenRunAsync(() -> out.append(number).append(' '), number % 2 == 1 ? odd : even);
    }
    chain.join();
    odd.shutdown();
    even.shutdown();
    return out.toString().trim();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n) for the chained futures

### Approach 7: Two Semaphores (Optimized)

Each thread owns a permit: the odd thread starts with one permit, the even thread with none. A thread acquires its own semaphore, prints and releases the other one. There is no shared flag, no `while` loop and no spurious-wakeup handling, so it is the simplest and least error-prone version.

```java
public static String semaphores(int max) throws InterruptedException {
    out.setLength(0);
    Semaphore oddSemaphore = new Semaphore(1);
    Semaphore evenSemaphore = new Semaphore(0);

    Thread odd = new Thread(() -> {
        for (int i = 1; i <= max; i += 2) {
            oddSemaphore.acquireUninterruptibly();
            out.append(i).append(' ');
            evenSemaphore.release();
        }
    });
    Thread even = new Thread(() -> {
        for (int i = 2; i <= max; i += 2) {
            evenSemaphore.acquireUninterruptibly();
            out.append(i).append(' ');
            oddSemaphore.release();
        }
    });
    odd.start(); even.start(); odd.join(); even.join();
    return out.toString().trim();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | wait() / notify() with a Shared Monitor | O(n) | O(1) |
| 2 | ReentrantLock + Condition | O(n) | O(1) |
| 3 | BlockingQueue Token Passing | O(n) | O(1) |
| 4 | Volatile Turn Flag with Spinning | O(n) (plus busy waiting) | O(1) |
| 5 | LockSupport park / unpark | O(n) | O(1) |
| 6 | CompletableFuture Chain on Two Single-Thread Executors | O(n) | O(n) for the chained futures |
| 7 | Two Semaphores (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 7 above. The version implemented in this repository is Approach 1.

## Source Code

### PrintEvenOddUsingThreads.java

```java
public class PrintEvenOddUsingThreads {

    public static void main(String[] args) throws InterruptedException {
        Printer printer = new Printer();
        int maxNum = 10;
        boolean isEvenNumber = true;
        Thread oddThread = new Thread(new EvenOrOdd(printer, maxNum, !isEvenNumber), "Odd");
        Thread evenThread = new Thread(new EvenOrOdd(printer, maxNum, isEvenNumber), "Even");

        System.out.println("Input  : max = " + maxNum);
        System.out.print("Output : ");
        oddThread.start();
        evenThread.start();

        // Wait for both threads so the output line is complete before the program ends
        oddThread.join();
        evenThread.join();
        System.out.println();
    }
}

/**
 * Task run by each thread: prints either the odd or the even numbers up to {@code max} through the shared {@link Printer}.
 */
class EvenOrOdd implements Runnable {

    private final Printer printer;
    private final int max;
    private final boolean isEvenNumber;

    public EvenOrOdd(Printer printer, int max, boolean isEvenNumber) {
        this.printer = printer;
        this.max = max;
        this.isEvenNumber = isEvenNumber;
    }

    @Override
    public void run() {
        int number = isEvenNumber ? 2 : 1;
        while (number <= max) {
            if (isEvenNumber) {
                printer.printEven(number);
            } else {
                printer.printOdd(number);
            }
            number += 2;
        }
    }
}

/**
 * Shared monitor that makes the odd and even threads take turns.
 */
class Printer {

    // false -> odd number is printed next, true -> even number is printed next
    private volatile boolean isOdd;

    synchronized void printEven(int number) {
        while (!isOdd) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.print(number + " ");
        isOdd = false;
        notify();
    }

    synchronized void printOdd(int number) {
        while (isOdd) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.print(number + " ");
        isOdd = true;
        notify();
    }
}
```
