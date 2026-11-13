package com.ranga.design;

/**
 * Design a circular queue of fixed capacity {@code k} (a ring buffer) with {@code enQueue(value)}, {@code deQueue()}, {@code Front()}, {@code Rear()}, {@code isEmpty()} and {@code isFull()}. {@code enQueue} and {@code deQueue} return whether they succeeded, and {@code Front}/{@code Rear} return {@code -1} when the queue is empty. No new space is allocated after construction.
 *
 * <p><b>Algorithm Strategy (Array with a Head Index and a Size):</b>
 * <ol>
 *   <li>Allocate an array of {@code k} slots, an index {@code head} of the front element and a counter {@code size}.</li>
 *   <li>The rear element is at {@code (head + size - 1) % k}, and the next free slot is {@code (head + size) % k}. The modulo makes the array wrap around, so freed slots at the front are reused.</li>
 *   <li>{@code enQueue} fails if {@code size == k}, otherwise writes at {@code (head + size) % k} and increments {@code size}.</li>
 *   <li>{@code deQueue} fails if {@code size == 0}, otherwise moves {@code head} to {@code (head + 1) % k} and decrements {@code size}.</li>
 *   <li>Keeping {@code size} separately avoids the classic ambiguity of using only head and tail indices, where "empty" and "full" look the same.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>Capacity 3: {@code enQueue(1)}, {@code enQueue(2)}, {@code enQueue(3)} fill the slots; {@code enQueue(4)} fails; {@code Rear()} is 3 and {@code isFull()} is true.</li>
 *   <li>{@code deQueue()} frees slot 0, so {@code enQueue(4)} now writes there (wrapping around) and {@code Rear()} returns 4.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — Every operation.</li>
 *   <li><b>Space Complexity:</b> {@code O(k)} — The fixed array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class DesignCircularQueue {

    public static void main(String[] args) {
        DesignCircularQueue queue = new DesignCircularQueue(3);
        System.out.println("Input  : DesignCircularQueue(3), enQueue(1), enQueue(2), enQueue(3), enQueue(4), Rear(), isFull(), deQueue(), enQueue(4), Rear()");
        boolean a = queue.enQueue(1);
        boolean b = queue.enQueue(2);
        boolean c = queue.enQueue(3);
        boolean d = queue.enQueue(4);
        int rear1 = queue.rear();
        boolean full = queue.isFull();
        boolean e = queue.deQueue();
        boolean f = queue.enQueue(4);
        int rear2 = queue.rear();
        System.out.println("Output : " + a + ", " + b + ", " + c + ", " + d + ", " + rear1 + ", " + full + ", " + e + ", " + f + ", " + rear2);
    }

    private final int[] data;
    private int head;
    private int size;

    public DesignCircularQueue(int k) {
        data = new int[k];
    }

    public boolean enQueue(int value) {
        if (isFull()) {
            return false;
        }
        data[(head + size) % data.length] = value;
        size++;
        return true;
    }

    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }
        head = (head + 1) % data.length;
        size--;
        return true;
    }

    public int front() {
        return isEmpty() ? -1 : data[head];
    }

    public int rear() {
        return isEmpty() ? -1 : data[(head + size - 1) % data.length];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == data.length;
    }
}
