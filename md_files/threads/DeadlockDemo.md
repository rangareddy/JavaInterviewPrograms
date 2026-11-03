# Deadlock and How to Avoid It

Show how two threads can deadlock when they acquire two locks in opposite order, how to detect that situation, and how to avoid it with a consistent lock ordering or a timed `tryLock`.

## Algorithm Strategy (Consistent Lock Ordering)

1. A deadlock needs four conditions: mutual exclusion, hold and wait, no pre-emption and a circular wait. Breaking any one of them prevents it.
2. The classic deadlock: thread 1 locks A then waits for B while thread 2 locks B then waits for A. Each holds what the other needs, forever.
3. Fix 1 (break the circular wait): every thread acquires the locks in the same global order, for example always A then B.
4. Fix 2 (break hold and wait): acquire with `ReentrantLock.tryLock(timeout)`; if the second lock cannot be had, release the first one and retry later. The demo detects a real deadlock with `ThreadMXBean.findDeadlockedThreads()`.

## Example walkthrough

- Thread 1 takes A then B and thread 2 takes B then A: with a short pause between the two acquisitions both block and the monitor reports 2 deadlocked threads.
- When both threads take A then B, they finish normally and no thread is reported as deadlocked.

## Complexity Analysis

- **Time Complexity:** `O(1)` — Locking is constant work (excluding the waiting time).
- **Space Complexity:** `O(1)` — Two locks and two threads.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Consistent Lock Ordering (Optimized)

Every thread acquires the locks in the same global order, so a circular wait cannot form. This is the version implemented in the repository.

```java
static void transferOrdered(Object from, Object to, int fromId, int toId, Runnable action) {
    Object first = fromId < toId ? from : to;
    Object second = fromId < toId ? to : from;
    synchronized (first) {
        synchronized (second) {
            action.run();
        }
    }
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Approach 2: tryLock with Timeout and Back-off

Try to take both `ReentrantLock`s; if the second one is unavailable, release the first one, wait a little and retry. This breaks "hold and wait".

```java
static void transferTryLock(ReentrantLock from, ReentrantLock to, Runnable action) throws InterruptedException {
    while (true) {
        if (from.tryLock(10, TimeUnit.MILLISECONDS)) {
            try {
                if (to.tryLock(10, TimeUnit.MILLISECONDS)) {
                    try { action.run(); return; } finally { to.unlock(); }
                }
            } finally { from.unlock(); }
        }
        Thread.sleep(1 + (long) (Math.random() * 5));
    }
}
```

- **Time Complexity:** O(1) expected
- **Space Complexity:** O(1)

### Approach 3: One Coarse Lock

Protect both resources with a single lock. Deadlock is impossible, at the cost of less concurrency.

```java
static final Object GLOBAL_LOCK = new Object();

static void transferGlobal(Runnable action) {
    synchronized (GLOBAL_LOCK) {
        action.run();
    }
}
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Consistent Lock Ordering (Optimized) | O(1) | O(1) |
| 2 | tryLock with Timeout and Back-off | O(1) expected | O(1) |
| 3 | One Coarse Lock | O(1) | O(1) |

### Optimized Solution

The optimized solution is Approach 1 above, which is also the version implemented in this repository.

## Source Code

### DeadlockDemo.java

```java
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class DeadlockDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Input  : two threads lock A and B in opposite order");
        System.out.println("Output : deadlocked threads = " + runOppositeOrder());
        System.out.println();

        System.out.println("Input  : two threads lock A and B in the same order");
        System.out.println("Output : completed = " + runSameOrder());
    }

    /** Starts two daemon threads that lock in opposite order; returns how many the JVM reports as deadlocked. */
    static int runOppositeOrder() throws InterruptedException {
        Object lockA = new Object();
        Object lockB = new Object();
        CountDownLatch bothHoldFirstLock = new CountDownLatch(2);

        Thread first = new Thread(() -> lockInOrder(lockA, lockB, bothHoldFirstLock), "deadlock-1");
        Thread second = new Thread(() -> lockInOrder(lockB, lockA, bothHoldFirstLock), "deadlock-2");
        first.setDaemon(true);
        second.setDaemon(true);
        first.start();
        second.start();

        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        for (int attempt = 0; attempt < 50; attempt++) {
            Thread.sleep(20);
            long[] ids = bean.findDeadlockedThreads();
            if (ids != null) {
                return ids.length;
            }
        }
        return 0;
    }

    /** Both threads use the same lock order (A, then B), so a circular wait is impossible. */
    static boolean runSameOrder() throws InterruptedException {
        Object lockA = new Object();
        Object lockB = new Object();
        CountDownLatch done = new CountDownLatch(2);
        Runnable task = () -> {
            synchronized (lockA) {
                synchronized (lockB) {
                    done.countDown();
                }
            }
        };

        new Thread(task).start();
        new Thread(task).start();
        return done.await(2, TimeUnit.SECONDS);
    }

    private static void lockInOrder(Object firstLock, Object secondLock, CountDownLatch bothHoldFirstLock) {
        synchronized (firstLock) {
            bothHoldFirstLock.countDown();
            try {
                bothHoldFirstLock.await();      // make sure the other thread holds its first lock too
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            synchronized (secondLock) {
                // never reached when the locks are taken in opposite order
            }
        }
    }
}
```
