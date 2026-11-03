package com.ranga.threads;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Implement the producer-consumer problem: a producer thread puts items into a bounded buffer and a consumer thread takes them out. The producer must wait when the buffer is full and the consumer must wait when it is empty.
 *
 * <p><b>Algorithm Strategy (Bounded Buffer with wait / notify):</b>
 * <ol>
 *   <li>Share a bounded queue (capacity {@code N}) between the threads and guard it with one lock (the buffer object itself).</li>
 *   <li>Producer: while the queue is full, call {@code wait()}; then add the item and call {@code notifyAll()} so a waiting consumer wakes up.</li>
 *   <li>Consumer: while the queue is empty, call {@code wait()}; then remove the item and call {@code notifyAll()} so a waiting producer wakes up.</li>
 *   <li>Always test the condition in a {@code while} loop (not {@code if}) to protect against spurious wake-ups. The same behaviour is available ready-made as {@code BlockingQueue}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>A producer creating {@code 1..10} and a consumer with a buffer of size 3: the producer blocks whenever 3 items are waiting, the consumer blocks when the buffer is empty.</li>
 *   <li>The consumer receives all ten items in the order they were produced, and the buffer never holds more than 3 items.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — Per put or take (excluding the waiting time).</li>
 *   <li><b>Space Complexity:</b> {@code O(N)} — The buffer holds at most {@code N} items.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ProducerConsumer {

    public static void main(String[] args) throws InterruptedException {
        BoundedBuffer buffer = new BoundedBuffer(3);
        int items = 10;
        List<Integer> consumed = new ArrayList<>();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= items; i++) {
                    buffer.put(i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= items; i++) {
                    consumed.add(buffer.take());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        System.out.println("Input  : producer creates 1.." + items + ", buffer capacity = 3");
        System.out.println("Output : consumed = " + consumed + ", max buffered = " + buffer.maxSize());
    }

    /** A bounded buffer that blocks producers when full and consumers when empty. */
    static class BoundedBuffer {
        private final Queue<Integer> queue = new LinkedList<>();
        private final int capacity;
        private int maxSize;

        BoundedBuffer(int capacity) {
            this.capacity = capacity;
        }

        synchronized void put(int item) throws InterruptedException {
            while (queue.size() == capacity) {
                wait();
            }
            queue.add(item);
            maxSize = Math.max(maxSize, queue.size());
            notifyAll();
        }

        synchronized int take() throws InterruptedException {
            while (queue.isEmpty()) {
                wait();
            }
            int item = queue.remove();
            notifyAll();
            return item;
        }

        synchronized int maxSize() {
            return maxSize;
        }
    }
}
