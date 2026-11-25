package com.ranga.dp;

import java.util.Arrays;

/**
 * You are given envelopes {@code [width, height]}. One envelope fits inside another only if both its width and its height are strictly smaller. Return the maximum number of envelopes you can nest (Russian doll style). Rotation is not allowed.
 *
 * <p><b>Algorithm Strategy (Sort by Width, then LIS on Heights):</b>
 * <ol>
 *   <li>Sort the envelopes by width ascending, and for equal widths by height descending.</li>
 *   <li>After sorting, widths never decrease, and the descending height order for equal widths means two envelopes with the same width cannot both be chosen in an increasing height sequence.</li>
 *   <li>So the problem becomes the longest strictly increasing subsequence of the heights.</li>
 *   <li>Compute it with the {@code O(n log n)} tails array and binary search.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[5,4],[6,4],[6,7],[2,3]]}: sorted by width then height descending: {@code [2,3],[5,4],[6,7],[6,4]}; the heights are {@code 3, 4, 7, 4}.</li>
 *   <li>The longest strictly increasing subsequence is {@code 3, 4, 7}: length {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n log n)} — Sorting plus a binary search per envelope.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The tails array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RussianDollEnvelopes {

    public static void main(String[] args) {
        int[][][] inputs = {{{5, 4}, {6, 4}, {6, 7}, {2, 3}}, {{1, 1}, {1, 1}, {1, 1}}, {{4, 5}, {4, 6}, {6, 7}, {2, 3}, {1, 1}}, {{1, 3}, {3, 5}, {6, 7}, {6, 8}, {8, 4}, {9, 5}}};
        for (int[][] envelopes : inputs) {
            System.out.println("Input  : envelopes = " + Arrays.deepToString(envelopes));
            System.out.println("Output : " + maxEnvelopes(envelopes.clone()));
            System.out.println();
        }
    }

    public static int maxEnvelopes(int[][] envelopes) {
        if (envelopes == null || envelopes.length == 0) {
            return 0;
        }
        Arrays.sort(envelopes, (a, b) -> a[0] != b[0] ? a[0] - b[0] : b[1] - a[1]);
        int[] tails = new int[envelopes.length];
        int size = 0;
        for (int[] envelope : envelopes) {
            int height = envelope[1];
            int lo = 0;
            int hi = size;
            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;
                if (tails[mid] < height) {
                    lo = mid + 1;
                } else {
                    hi = mid;
                }
            }
            tails[lo] = height;
            if (lo == size) {
                size++;
            }
        }
        return size;
    }
}
