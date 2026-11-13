package com.ranga.threads;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Implement a read-write lock: any number of threads may hold the read lock at the same time, but the write lock is exclusive, so a writer runs alone with no reader and no other writer.
 *
 * <p><b>Algorithm Strategy (State Counters Guarded by a Monitor):</b>
 * <ol>
 *   <li>Track three pieces of state under one monitor: {@code activeReaders}, {@code writerActive} and {@code waitingWriters}.</li>
 *   <li>{@code lockRead} waits while a writer is active or a writer is waiting, then increments {@code activeReaders}. Many readers can pass at once because readers never wait for each other.</li>
 *   <li>{@code lockWrite} registers as waiting, then waits until there is no active writer and no active reader, and finally sets {@code writerActive}.</li>
 *   <li>{@code unlockRead} decrements {@code activeReaders} and wakes everyone when the last reader leaves; {@code unlockWrite} clears {@code writerActive} and wakes everyone.</li>
 *   <li>Making new readers wait while any writer is queued gives writers priority, so a steady stream of readers cannot starve a writer. The price is that readers can wait behind a writer. The lock is not reentrant: a thread that holds it must not lock it again.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>Two readers call {@code lockRead}: both pass, {@code activeReaders = 2}. A writer calls {@code lockWrite}: {@code waitingWriters = 1}, it waits because readers are active.</li>
 *   <li>A third reader now waits too, because a writer is queued. When the two readers call {@code unlockRead}, the last one wakes the writer.</li>
 *   <li>The writer runs alone, calls {@code unlockWrite}, and the waiting reader proceeds.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — Per lock or unlock call, not counting waiting.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Three state fields.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
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
