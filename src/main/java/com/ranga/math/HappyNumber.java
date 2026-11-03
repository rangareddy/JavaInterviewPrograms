package com.ranga.math;

/**
 * A number is happy if repeatedly replacing it with the sum of the squares of its digits eventually reaches 1. If the process loops forever in a cycle that does not contain 1, the number is not happy. Return {@code true} if {@code n} is happy.
 *
 * <p><b>Algorithm Strategy (Cycle Detection with Floyd's Tortoise and Hare):</b>
 * <ol>
 *   <li>Define {@code next(n)} as the sum of the squares of the digits of {@code n}.</li>
 *   <li>The sequence either reaches 1 (and stays there) or enters a cycle, so it is a linked list that may have a loop.</li>
 *   <li>Move {@code slow} one step and {@code fast} two steps at a time.</li>
 *   <li>If {@code fast} reaches 1, the number is happy; if {@code slow == fast} before that, a cycle without 1 exists: not happy.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For 19: {@code 1 + 81 = 82}, {@code 64 + 4 = 68}, {@code 36 + 64 = 100}, {@code 1}.</li>
 *   <li>Result: {@code true}. For 2 the sequence reaches the cycle {@code 4 -> 16 -> 37 -> 58 -> 89 -> 145 -> 42 -> 20 -> 4}: {@code false}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log n)} — The sequence stays small after the first step and has bounded length.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two pointers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class HappyNumber {

    public static void main(String[] args) {
        int[] inputs = {19, 2, 1, 7, 4, 100};
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
