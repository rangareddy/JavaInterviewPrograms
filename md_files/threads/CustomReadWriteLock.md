# Custom Read-Write Lock

Implement a read-write lock: any number of threads may hold the read lock at the same time, but the write lock is exclusive, so a writer runs alone with no reader and no other writer.

## Algorithm Strategy (State Counters Guarded by a Monitor)

1. Track three pieces of state under one monitor: `activeReaders`, `writerActive` and `waitingWriters`.
2. `lockRead` waits while a writer is active or a writer is waiting, then increments `activeReaders`. Many readers can pass at once because readers never wait for each other.
3. `lockWrite` registers as waiting, then waits until there is no active writer and no active reader, and finally sets `writerActive`.
4. `unlockRead` decrements `activeReaders` and wakes everyone when the last reader leaves; `unlockWrite` clears `writerActive` and wakes everyone.
5. Making new readers wait while any writer is queued gives writers priority, so a steady stream of readers cannot starve a writer. The price is that readers can wait behind a writer. The lock is not reentrant: a thread that holds it must not lock it again.

## Example walkthrough

- Two readers call `lockRead`: both pass, `activeReaders = 2`. A writer calls `lockWrite`: `waitingWriters = 1`, it waits because readers are active.
- A third reader now waits too, because a writer is queued. When the two readers call `unlockRead`, the last one wakes the writer.
- The writer runs alone, calls `unlockWrite`, and the waiting reader proceeds.

## Complexity Analysis

- **Time Complexity:** `O(1)` — Per lock or unlock call, not counting waiting.
- **Space Complexity:** `O(1)` — Three state fields.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: One Exclusive Lock

Use a single mutex for readers and writers. Always correct, but readers are serialized, so a read-heavy workload gets no parallelism.

```java
class ExclusiveLock {
    private final ReentrantLock mutex = new ReentrantLock();
    void lockRead() { mutex.lock(); }
    void unlockRead() { mutex.unlock(); }
    void lockWrite() { mutex.lock(); }
    void unlockWrite() { mutex.unlock(); }
}
```

- **Time Complexity:** O(1) per call
- **Space Complexity:** O(1)

### Approach 2: JDK ReentrantReadWriteLock

The production answer: it is reentrant, supports downgrading from write to read, has a fair mode and exposes the reader and writer counts. Use it in real code and write your own only to understand it.

```java
class JdkReadWriteLock {
    private final ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
    void lockRead() { rw.readLock().lock(); }
    void unlockRead() { rw.readLock().unlock(); }
    void lockWrite() { rw.writeLock().lock(); }
    void unlockWrite() { rw.writeLock().unlock(); }
}
```

- **Time Complexity:** O(1) per call
- **Space Complexity:** O(1)

### Approach 3: Reader and Writer Counters with Writer Preference (Optimized)

Keep reader, writer and waiting-writer state under one monitor and block new readers while a writer is queued. This is the version implemented in the repository.

```java
private int activeReaders;
private int waitingWriters;
private boolean writerActive;

public synchronized void lockRead() throws InterruptedException {
    while (writerActive || waitingWriters > 0) {
        wait();
    }
    activeReaders++;
}

public synchronized void unlockRead() {
    if (activeReaders == 0) {
        throw new IllegalMonitorStateException("No read lock is held");
    }
    activeReaders--;
    if (activeReaders == 0) {
        notifyAll();
    }
}

public synchronized void lockWrite() throws InterruptedException {
    waitingWriters++;
    try {
        while (writerActive || activeReaders > 0) {
            wait();
        }
    } catch (InterruptedException e) {
        waitingWriters--;
        notifyAll();
        throw e;
    }
    waitingWriters--;
    writerActive = true;
}

public synchronized void unlockWrite() {
    if (!writerActive) {
        throw new IllegalMonitorStateException("No write lock is held");
    }
    writerActive = false;
    notifyAll();
}
```

- **Time Complexity:** O(1) per call
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | One Exclusive Lock | O(1) per call | O(1) |
| 2 | JDK ReentrantReadWriteLock | O(1) per call | O(1) |
| 3 | Reader and Writer Counters with Writer Preference (Optimized) | O(1) per call | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### CustomReadWriteLock.java

```java
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomReadWriteLock {

    public static void main(String[] args) throws InterruptedException {
        CustomReadWriteLock lock = new CustomReadWriteLock();
        int[] pair = new int[2];
        AtomicBoolean consistent = new AtomicBoolean(true);

        Thread writer = new Thread(() -> {
            try {
                for (int i = 0; i < 1000; i++) {
                    lock.lockWrite();
                    try {
                        pair[0]++;
                        pair[1]++;
                    } finally {
                        lock.unlockWrite();
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread[] readers = new Thread[3];
        for (int r = 0; r < readers.length; r++) {
            readers[r] = new Thread(() -> {
                try {
                    for (int i = 0; i < 1000; i++) {
                        lock.lockRead();
                        try {
                            if (pair[0] != pair[1]) {
                                consistent.set(false);
                            }
                        } finally {
                            lock.unlockRead();
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        writer.start();
        for (Thread t : readers) t.start();
        writer.join();
        for (Thread t : readers) t.join();

        System.out.println("Input  : 1 writer increments a pair 1000 times, 3 readers check pair[0] == pair[1]");
        System.out.println("Output : final pair = [" + pair[0] + ", " + pair[1] + "], readers always saw a consistent pair: " + consistent.get());
    }

    private int activeReaders;
    private int waitingWriters;
    private boolean writerActive;

    public synchronized void lockRead() throws InterruptedException {
        while (writerActive || waitingWriters > 0) {
            wait();
        }
        activeReaders++;
    }

    public synchronized void unlockRead() {
        if (activeReaders == 0) {
            throw new IllegalMonitorStateException("No read lock is held");
        }
        activeReaders--;
        if (activeReaders == 0) {
            notifyAll();
        }
    }

    public synchronized void lockWrite() throws InterruptedException {
        waitingWriters++;
        try {
            while (writerActive || activeReaders > 0) {
                wait();
            }
        } catch (InterruptedException e) {
            waitingWriters--;
            notifyAll();
            throw e;
        }
        waitingWriters--;
        writerActive = true;
    }

    public synchronized void unlockWrite() {
        if (!writerActive) {
            throw new IllegalMonitorStateException("No write lock is held");
        }
        writerActive = false;
        notifyAll();
    }
}
```
