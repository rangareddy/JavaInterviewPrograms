package com.ranga.greedy;

import java.util.Arrays;

/**
 * At a lemonade stand each lemonade costs {@code $5}. Customers pay with a {@code $5}, {@code $10} or {@code $20} bill, in the order given by {@code bills}, and you must give each the correct change. You start with no change. Return {@code true} if you can give every customer the correct change.
 *
 * <p><b>Algorithm Strategy (Count Fives and Tens, Prefer Large Bills for Change):</b>
 * <ol>
 *   <li>Track how many {@code $5} and {@code $10} bills you hold. A {@code $20} is never used as change.</li>
 *   <li>On a {@code $5} bill, keep it.</li>
 *   <li>On a {@code $10} bill, give back one {@code $5}; fail if none.</li>
 *   <li>On a {@code $20} bill, prefer giving {@code $10 + $5} (a {@code $10} is useless otherwise), else {@code $5 + $5 + $5}; fail if neither is possible.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [5, 5, 5, 10, 20]}: three fives, then the 10 takes one five, then the 20 takes a ten and a five.</li>
 *   <li>Result: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass over the bills.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two counters.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class LemonadeChange {

    public static void main(String[] args) {
        int[][] inputs = {{5, 5, 5, 10, 20}, {5, 5, 10, 10, 20}, {5, 10}, {10}, {}};
        for (int[] bills : inputs) {
            System.out.println("Input  : bills = " + Arrays.toString(bills));
            System.out.println("Output : " + lemonadeChange(bills));
            System.out.println();
        }
    }

    public static boolean lemonadeChange(int[] bills) {
        int fives = 0;
        int tens = 0;
        for (int bill : bills) {
            if (bill == 5) {
                fives++;
            } else if (bill == 10) {
                if (fives == 0) {
                    return false;
                }
                fives--;
                tens++;
            } else {
                if (tens > 0 && fives > 0) {
                    tens--;
                    fives--;
                } else if (fives >= 3) {
                    fives -= 3;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
