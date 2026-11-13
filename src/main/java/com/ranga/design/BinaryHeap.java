package com.ranga.design;

import java.util.Arrays;

/**
 * Implement a binary heap from scratch that can work as a min-heap or a max-heap, with {@code add(value)}, {@code peek()}, {@code poll()}, {@code size()} and {@code isEmpty()}. {@code add} and {@code poll} must run in {@code O(log n)} and {@code peek} in {@code O(1)}.
 *
 * <p><b>Algorithm Strategy (Array-Backed Complete Binary Tree with Sift Up and Sift Down):</b>
 * <ol>
 *   <li>Store a complete binary tree in an array: the children of index {@code i} are {@code 2i + 1} and {@code 2i + 2}, and its parent is {@code (i - 1) / 2}. No pointers are needed.</li>
 *   <li>The heap property says every parent is ordered before its children (smaller for a min-heap, larger for a max-heap), so the root is always the extreme element.</li>
 *   <li>{@code add} appends the value at the end and sifts it up: while it is ordered before its parent, swap them.</li>
 *   <li>{@code poll} takes the root, moves the last element to the root and sifts it down: swap with the better of its two children until the heap property holds again.</li>
 *   <li>One class serves both kinds by using a single comparison, {@code before(a, b)}, that is {@code a < b} for a min-heap and {@code a > b} for a max-heap.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>A min-heap after adding 5, 3, 8, 1 is the array {@code [1, 3, 8, 5]}: 5 is placed first, 3 swaps above it, 8 is a right child, and 1 is added below 3 and sifts up past 3 to the root, pushing 3 down.</li>
 *   <li>{@code poll()} returns 1, moves the last element 5 to the root and sifts down: {@code [3, 5, 8]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log n)} — {@code add} and {@code poll}; {@code peek} is {@code O(1)} and building by repeated {@code add} costs {@code O(n log n)}.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The backing array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BinaryHeap {

    private int[] data = new int[8];
    private int size;
    private final boolean minHeap;

    public BinaryHeap(boolean minHeap) {
        this.minHeap = minHeap;
    }

    public static void main(String[] args) {
        int[] values = {5, 3, 8, 1, 9, 2};
        BinaryHeap min = new BinaryHeap(true);
        BinaryHeap max = new BinaryHeap(false);
        for (int v : values) {
            min.add(v);
            max.add(v);
        }
        System.out.println("Input  : add " + Arrays.toString(values));
        System.out.println("Output : min peek = " + min.peek() + ", max peek = " + max.peek());
        System.out.println();
        StringBuilder minOrder = new StringBuilder();
        StringBuilder maxOrder = new StringBuilder();
        while (!min.isEmpty()) {
            minOrder.append(minOrder.length() == 0 ? "" : ", ").append(min.poll());
            maxOrder.append(maxOrder.length() == 0 ? "" : ", ").append(max.poll());
        }
        System.out.println("Input  : poll until empty");
        System.out.println("Output : min order = [" + minOrder + "], max order = [" + maxOrder + "]");
    }

    public void add(int value) {
        if (size == data.length) {
            data = Arrays.copyOf(data, size * 2);
        }
        data[size] = value;
        siftUp(size++);
    }

    public int peek() {
        if (size == 0) {
            throw new IllegalStateException("heap is empty");
        }
        return data[0];
    }

    public int poll() {
        int top = peek();
        data[0] = data[--size];
        if (size > 0) {
            siftDown(0);
        }
        return top;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private boolean before(int a, int b) {
        return minHeap ? a < b : a > b;
    }

    private void siftUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (!before(data[i], data[parent])) {
                break;
            }
            swap(i, parent);
            i = parent;
        }
    }

    private void siftDown(int i) {
        while (true) {
            int best = i;
            int left = 2 * i + 1;
            int right = left + 1;
            if (left < size && before(data[left], data[best])) {
                best = left;
            }
            if (right < size && before(data[right], data[best])) {
                best = right;
            }
            if (best == i) {
                return;
            }
            swap(i, best);
            i = best;
        }
    }

    private void swap(int a, int b) {
        int temp = data[a];
        data[a] = data[b];
        data[b] = temp;
    }
}
