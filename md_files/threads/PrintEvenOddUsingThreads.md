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

### Approach 3: Two Semaphores (Optimized)

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
| 3 | Two Semaphores (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above. The version implemented in this repository is Approach 1.

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
