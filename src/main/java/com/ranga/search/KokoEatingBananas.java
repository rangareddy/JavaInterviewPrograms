package com.ranga.search;

import java.util.Arrays;

/**
 * Koko has {@code piles[i]} bananas in the i-th pile and {@code h} hours before the guards return. She chooses an eating speed {@code k} (bananas per hour); each hour she eats up to {@code k} bananas from one pile and does not start another pile in the same hour. Return the minimum integer {@code k} that lets her finish all piles within {@code h} hours.
 *
 * <p><b>Algorithm Strategy (Binary Search on the Answer):</b>
 * <ol>
 *   <li>The time needed at speed {@code k} is {@code sum(ceil(pile / k))} and it never increases when {@code k} grows, so the feasible speeds form a range {@code [answer, max pile]}.</li>
 *   <li>Binary-search the speed between 1 and the size of the largest pile (eating one whole pile per hour always works if {@code h >= piles.length}).</li>
 *   <li>For a candidate {@code mid}, compute the hours needed. If they are at most {@code h}, {@code mid} is feasible: remember it and try a smaller speed; otherwise try a larger one.</li>
 *   <li>The smallest feasible speed found is the answer. Compute {@code ceil(p / k)} as {@code (p + k - 1) / k} to stay in integer arithmetic.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [3, 6, 7, 11]} and {@code h = 8}: speed 4 needs {@code 1 + 2 + 2 + 3 = 8} hours (feasible), speed 3 needs {@code 1 + 2 + 3 + 4 = 10} hours (not feasible).</li>
 *   <li>Result: {@code 4}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log M)} — {@code n} piles, {@code M} the size of the largest pile.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A few variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class KokoEatingBananas {

    public static void main(String[] args) {
        int[][] piles = {{3, 6, 7, 11}, {30, 11, 23, 4, 20}, {30, 11, 23, 4, 20}, {1000000000}};
        int[] hours = {8, 5, 6, 2};
        for (int i = 0; i < piles.length; i++) {
            System.out.println("Input  : piles = " + Arrays.toString(piles[i]) + ", h = " + hours[i]);
            System.out.println("Output : " + minEatingSpeed(piles[i], hours[i]));
            System.out.println();
        }
    }

    public static int minEatingSpeed(int[] piles, int h) {
        int low = 1;
        int high = 0;
        for (int pile : piles) {
            high = Math.max(high, pile);
        }

        int answer = high;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (hoursNeeded(piles, mid) <= h) {
                answer = mid;          // feasible: try a slower speed
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return answer;
    }

    private static long hoursNeeded(int[] piles, int speed) {
        long hours = 0;
        for (int pile : piles) {
            hours += (pile + speed - 1) / speed;       // ceil(pile / speed)
        }
        return hours;
    }
}
