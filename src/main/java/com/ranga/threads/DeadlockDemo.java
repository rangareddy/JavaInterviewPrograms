package com.ranga.threads;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Show how two threads can deadlock when they acquire two locks in opposite order, how to detect that situation, and how to avoid it with a consistent lock ordering or a timed {@code tryLock}.
 *
 * <p><b>Algorithm Strategy (Consistent Lock Ordering):</b>
 * <ol>
 *   <li>A deadlock needs four conditions: mutual exclusion, hold and wait, no pre-emption and a circular wait. Breaking any one of them prevents it.</li>
 *   <li>The classic deadlock: thread 1 locks A then waits for B while thread 2 locks B then waits for A. Each holds what the other needs, forever.</li>
 *   <li>Fix 1 (break the circular wait): every thread acquires the locks in the same global order, for example always A then B.</li>
 *   <li>Fix 2 (break hold and wait): acquire with {@code ReentrantLock.tryLock(timeout)}; if the second lock cannot be had, release the first one and retry later. The demo detects a real deadlock with {@code ThreadMXBean.findDeadlockedThreads()}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>Thread 1 takes A then B and thread 2 takes B then A: with a short pause between the two acquisitions both block and the monitor reports 2 deadlocked threads.</li>
 *   <li>When both threads take A then B, they finish normally and no thread is reported as deadlocked.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — Locking is constant work (excluding the waiting time).</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two locks and two threads.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
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
