package com.ranga.greedy;

import java.util.Arrays;

/**
 * Given the weights of people in {@code people} and a boat that carries at most two people with a total weight of at most {@code limit}, return the minimum number of boats needed to carry everyone. Each person weighs at most {@code limit}.
 *
 * <p><b>Algorithm Strategy (Two Pointers on Sorted Weights):</b>
 * <ol>
 *   <li>Sort the weights.</li>
 *   <li>The heaviest person needs a boat anyway, so try to pair them with the lightest person.</li>
 *   <li>If {@code people[light] + people[heavy] <= limit}, both ride together: move {@code light} up.</li>
 *   <li>In either case the heavy person is done: move {@code heavy} down and count a boat.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [3, 2, 2, 1]} and limit 3: sorted {@code [1, 2, 2, 3]}: 3 goes alone, 2 pairs with 1, then 2 goes alone.</li>
 *   <li>Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} — Dominated by the sort.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Apart from sorting.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BoatsToSavePeople {

    public static void main(String[] args) {
        int[][] people = {{3, 2, 2, 1}, {1, 2}, {3, 5, 3, 4}, {5}};
        int[] limits = {3, 3, 5, 5};
        for (int i = 0; i < people.length; i++) {
            System.out.println("Input  : people = " + Arrays.toString(people[i]) + ", limit = " + limits[i]);
            System.out.println("Output : " + numRescueBoats(people[i].clone(), limits[i]));
            System.out.println();
        }
    }

    public static int numRescueBoats(int[] people, int limit) {
        if (people == null || people.length == 0) {
            return 0;
        }
        Arrays.sort(people);
        int light = 0;
        int heavy = people.length - 1;
        int boats = 0;
        while (light <= heavy) {
            if (people[light] + people[heavy] <= limit) {
                light++;
            }
            heavy--;
            boats++;
        }
        return boats;
    }
}
