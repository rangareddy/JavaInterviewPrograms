package com.ranga.heap;

import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * You are given an array of stone weights. In each turn pick the two heaviest stones with weights {@code x <= y} and smash them: if {@code x == y} both stones are destroyed, otherwise the stone of weight {@code x} is destroyed and the other one gets the weight {@code y - x}. Return the weight of the last remaining stone, or 0 if no stone is left.
 *
 * <p><b>Algorithm Strategy (Max-Heap Simulation):</b>
 * <ol>
 *   <li>Put all stone weights into a max-heap, so the two heaviest stones are always available in {@code O(log n)}.</li>
 *   <li>While at least two stones remain, remove the two largest ones, {@code y} then {@code x}.</li>
 *   <li>If they differ, push the difference {@code y - x} back into the heap (equal stones vanish).</li>
 *   <li>At the end the heap holds zero or one stone: return its weight, or 0 for an empty heap.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [2, 7, 4, 1, 8, 1]}: smash 8 and 7 {@code -> 1}; smash 4 and 2 {@code -> 2}; smash 2 and 1 {@code -> 1}; smash 1 and 1 {@code -> 0}.</li>
 *   <li>One stone of weight 1 remains: {@code 1}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} — Each stone is pushed and removed a logarithmic number of times.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The heap.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LastStoneWeight {

    public static void main(String[] args) {
        int[][] inputs = {{2, 7, 4, 1, 8, 1}, {1}, {2, 2}, {3, 7, 2}, {}};
        for (int[] stones : inputs) {
            System.out.println("Input  : stones = " + Arrays.toString(stones));
            System.out.println("Output : " + lastStoneWeight(stones));
            System.out.println();
        }
    }

    public static int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
        for (int stone : stones) {
            heap.add(stone);
        }

        while (heap.size() > 1) {
            int heaviest = heap.poll();
            int second = heap.poll();
            if (heaviest != second) {
                heap.add(heaviest - second);
            }
        }
        return heap.isEmpty() ? 0 : heap.peek();
    }
}
