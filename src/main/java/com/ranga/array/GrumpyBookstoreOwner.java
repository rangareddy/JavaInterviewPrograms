package com.ranga.array;

import java.util.Arrays;

/**
 * A bookstore owner has a store open for {@code n} minutes. {@code customers[i]} customers enter at the start of minute {@code i}, and {@code grumpy[i]} is 1 if the owner is grumpy during minute {@code i} (the customers entering are unsatisfied) and 0 otherwise. The owner knows a technique to stay calm for {@code minutes} consecutive minutes, only once. Return the maximum number of satisfied customers.
 *
 * <p><b>Algorithm Strategy (Base Satisfied Plus the Best Window of Grumpy Customers):</b>
 * <ol>
 *   <li>Count the customers that are satisfied anyway (minutes with {@code grumpy[i] == 0}).</li>
 *   <li>Slide a window of {@code minutes} minutes and sum the customers that would be turned from unsatisfied to satisfied (minutes with {@code grumpy[i] == 1} inside the window).</li>
 *   <li>Keep the largest window sum.</li>
 *   <li>Return {@code base + best window}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code customers = [1,0,1,2,1,1,7,5]}, {@code grumpy = [0,1,0,1,0,1,0,1]}, {@code minutes = 3}: the base is 1+1+1+7 = 10; the best window {@code [5, 6, 7]} adds 1 + 5.</li>
 *   <li>Result: {@code 16}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass with a sliding window.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A few counters.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class GrumpyBookstoreOwner {

    public static void main(String[] args) {
        int[][] customers = {{1, 0, 1, 2, 1, 1, 7, 5}, {1}, {4, 10, 10}};
        int[][] grumpy = {{0, 1, 0, 1, 0, 1, 0, 1}, {0}, {1, 1, 0}};
        int[] minutes = {3, 1, 2};
        for (int i = 0; i < customers.length; i++) {
            System.out.println("Input  : customers = " + Arrays.toString(customers[i]) + ", grumpy = " + Arrays.toString(grumpy[i]) + ", minutes = " + minutes[i]);
            System.out.println("Output : " + maxSatisfied(customers[i], grumpy[i], minutes[i]));
            System.out.println();
        }
    }

    public static int maxSatisfied(int[] customers, int[] grumpy, int minutes) {
        if (customers == null || grumpy == null || customers.length == 0) {
            return 0;
        }
        int base = 0;
        int window = 0;
        int bestWindow = 0;
        for (int i = 0; i < customers.length; i++) {
            if (grumpy[i] == 0) {
                base += customers[i];
            } else {
                window += customers[i];
            }
            if (i >= minutes && grumpy[i - minutes] == 1) {
                window -= customers[i - minutes];
            }
            bestWindow = Math.max(bestWindow, window);
        }
        return base + bestWindow;
    }
}
