package com.ranga.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Trees stand in a row and {@code fruits[i]} is the type of fruit on tree {@code i}. You have two baskets, each holding one type of fruit, and you must pick exactly one fruit from every tree while moving right from any starting tree, stopping when a fruit does not fit. Return the maximum number of fruits you can pick.
 *
 * <p><b>Algorithm Strategy (Sliding Window with a Count Map):</b>
 * <ol>
 *   <li>The task is the longest subarray with at most two distinct values.</li>
 *   <li>Extend {@code right} and add its fruit to a count map.</li>
 *   <li>While the map has more than two types, remove {@code fruits[left]} from the map (deleting its key when the count hits 0) and advance {@code left}.</li>
 *   <li>Track the maximum window length.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 3, 2, 2]}: the window {@code [2, 3, 2, 2]} has two types and length 4.</li>
 *   <li>The sample {@code [1, 2, 1]} gives 3.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each index enters and leaves once.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — At most three keys in the map.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class FruitIntoBaskets {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 1}, {0, 1, 2, 2}, {1, 2, 3, 2, 2}, {3, 3, 3, 1, 2, 1, 1, 2, 3, 3, 4}, {}};
        for (int[] fruits : inputs) {
            System.out.println("Input  : fruits = " + Arrays.toString(fruits));
            System.out.println("Output : " + totalFruit(fruits));
            System.out.println();
        }
    }

    public static int totalFruit(int[] fruits) {
        if (fruits == null || fruits.length == 0) {
            return 0;
        }
        Map<Integer, Integer> basket = new HashMap<>();
        int left = 0;
        int best = 0;
        for (int right = 0; right < fruits.length; right++) {
            basket.merge(fruits[right], 1, Integer::sum);
            while (basket.size() > 2) {
                int type = fruits[left++];
                if (basket.merge(type, -1, Integer::sum) == 0) {
                    basket.remove(type);
                }
            }
            best = Math.max(best, right - left + 1);
        }
        return best;
    }
}
