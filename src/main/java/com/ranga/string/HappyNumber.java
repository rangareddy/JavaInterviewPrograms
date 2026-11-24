package com.ranga.string;

/**
 * Starting from a positive integer, repeatedly replace it with the sum of the squares of its digits. The number is happy if this process reaches 1; otherwise it loops forever in a cycle that does not contain 1. Return {@code true} if {@code n} is happy.
 *
 * <p><b>Algorithm Strategy (Fast and Slow Pointers on the Sequence):</b>
 * <ol>
 *   <li>Define {@code next(n)} as the sum of the squares of the digits of {@code n}.</li>
 *   <li>The sequence either reaches 1 (and stays there) or enters a cycle, so it can be treated like a linked list with a possible loop.</li>
 *   <li>Advance {@code slow} one step and {@code fast} two steps per round until they meet or {@code fast} reaches 1.</li>
 *   <li>If the meeting value is 1, the number is happy; otherwise it is in a cycle.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For 19: 19 -> 82 -> 68 -> 100 -> 1, so it is happy.</li>
 *   <li>For 2: 2 -> 4 -> 16 -> 37 -> 58 -> 89 -> 145 -> 42 -> 20 -> 4, a cycle without 1.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log n)} — The values stay small after the first step and the cycle is short.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two numbers, no set of seen values.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class HappyNumber {

    public static void main(String[] args) {
        int[] inputs = {19, 2, 1, 7, 4};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + isHappy(n));
            System.out.println();
        }
    }

    public static boolean isHappy(int n) {
        int slow = n;
        int fast = next(n);
        while (fast != 1 && slow != fast) {
            slow = next(slow);
            fast = next(next(fast));
        }
        return fast == 1;
    }

    private static int next(int n) {
        int sum = 0;
        while (n > 0) {
            int digit = n % 10;
            sum += digit * digit;
            n /= 10;
        }
        return sum;
    }
}
