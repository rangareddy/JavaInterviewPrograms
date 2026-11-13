package com.ranga.threads;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implement a bounded, thread-safe blocking queue. {@code put} blocks while the queue is full and {@code take} blocks while it is empty, and any number of producer and consumer threads may use it at the same time.
 *
 * <p><b>Algorithm Strategy (ReentrantLock with Two Conditions):</b>
 * <ol>
 *   <li>Store the items in a circular array of fixed capacity with {@code head}, {@code tail} and {@code count} fields, all guarded by one {@code ReentrantLock}.</li>
 *   <li>Create two {@code Condition} objects from that lock: {@code notFull} (producers wait on it) and {@code notEmpty} (consumers wait on it).</li>
 *   <li>{@code put} takes the lock, waits on {@code notFull} in a {@code while} loop while {@code count == capacity}, stores the item and signals {@code notEmpty}.</li>
 *   <li>{@code take} takes the lock, waits on {@code notEmpty} in a {@code while} loop while {@code count == 0}, removes the item and signals {@code notFull}.</li>
 *   <li>The condition is re-checked in a loop because a woken thread may find that another thread already used the free slot or item (and spurious wake-ups are allowed). Two separate conditions mean a signal only wakes a thread that can actually make progress.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>Capacity 2 and a consumer that starts first: {@code take} finds {@code count == 0} and waits on {@code notEmpty}.</li>
 *   <li>A producer calls {@code put(1)}, stores it, signals {@code notEmpty} and the consumer wakes up with item {@code 1}. Two more {@code put} calls fill the queue; a third {@code put} waits on {@code notFull} until the next {@code take} signals it.</li>
 *   <li>Items leave in the order they entered (FIFO) and {@code size()} never exceeds the capacity.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — Per {@code put} or {@code take}, not counting the time spent waiting.</li>
 *   <li><b>Space Complexity:</b> {@code O(capacity)} — The circular array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
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
