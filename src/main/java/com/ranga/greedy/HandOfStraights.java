package com.ranga.greedy;

import java.util.Arrays;
import java.util.TreeMap;

/**
 * Alice has a {@code hand} of cards and wants to rearrange them into groups of {@code groupSize} consecutive cards. Return {@code true} if this is possible.
 *
 * <p><b>Algorithm Strategy (Greedy with a Sorted Frequency Map):</b>
 * <ol>
 *   <li>If {@code hand.length} is not divisible by {@code groupSize}, return {@code false}.</li>
 *   <li>Count the cards in a {@code TreeMap} (sorted by value).</li>
 *   <li>Repeatedly take the smallest remaining card: it must start a group, because nothing smaller is left to extend it.</li>
 *   <li>Remove one card of each value {@code start .. start + groupSize - 1}; if any is missing, return {@code false}. Drop values whose count reaches zero. If all cards are used, return {@code true}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code hand = [1, 2, 3, 6, 2, 3, 4, 7, 8]} and {@code groupSize = 3}: groups {@code [1,2,3]}, {@code [2,3,4]} and {@code [6,7,8]}.</li>
 *   <li>Result: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} — Sorted map operations for every card.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The frequency map.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class HandOfStraights {

    public static void main(String[] args) {
        int[][] hands = {{1, 2, 3, 6, 2, 3, 4, 7, 8}, {1, 2, 3, 4, 5}, {1, 1, 2, 2, 3, 3}, {5}};
        int[] sizes = {3, 4, 3, 1};
        for (int i = 0; i < hands.length; i++) {
            System.out.println("Input  : hand = " + Arrays.toString(hands[i]) + ", groupSize = " + sizes[i]);
            System.out.println("Output : " + isNStraightHand(hands[i], sizes[i]));
            System.out.println();
        }
    }

    public static boolean isNStraightHand(int[] hand, int groupSize) {
        if (hand.length % groupSize != 0) {
            return false;
        }

        TreeMap<Integer, Integer> counts = new TreeMap<>();
        for (int card : hand) {
            counts.merge(card, 1, Integer::sum);
        }

        while (!counts.isEmpty()) {
            int start = counts.firstKey();
            for (int card = start; card < start + groupSize; card++) {
                Integer count = counts.get(card);
                if (count == null) {
                    return false;
                }
                if (count == 1) {
                    counts.remove(card);
                } else {
                    counts.put(card, count - 1);
                }
            }
        }
        return true;
    }
}
