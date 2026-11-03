package com.ranga.greedy;

import java.util.Arrays;

/**
 * A triplet is an array of three integers. You are given a list {@code triplets} and a {@code target} triplet. In one operation you pick two triplets and replace the second with the element-wise maximum of both. Return {@code true} if {@code target} can become an element of {@code triplets} after any number of operations.
 *
 * <p><b>Algorithm Strategy (Greedy Filter and Match):</b>
 * <ol>
 *   <li>An operation only ever raises values, so any triplet with a value above the corresponding target value can never be used: skip it.</li>
 *   <li>Among the remaining ("safe") triplets, the element-wise maximum is the best we can build.</li>
 *   <li>Track which of the three target positions has been matched exactly by some safe triplet.</li>
 *   <li>The answer is {@code true} when all three positions are matched.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code triplets = [[2,5,3],[1,8,4],[1,7,5]]} and {@code target = [2,7,5]}: {@code [1,8,4]} is unsafe (8 > 7); merging {@code [2,5,3]} and {@code [1,7,5]} gives {@code [2,7,5]}.</li>
 *   <li>Result: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass over the triplets.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Three flags.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MergeTripletsToFormTargetTriplet {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{2, 5, 3}, {1, 8, 4}, {1, 7, 5}},
                {{3, 4, 5}, {4, 5, 6}},
                {{2, 5, 3}, {2, 3, 4}, {1, 2, 5}, {5, 2, 3}},
                {{1, 1, 1}}
        };
        int[][] targets = {{2, 7, 5}, {3, 2, 5}, {5, 5, 5}, {1, 1, 1}};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : triplets = " + Arrays.deepToString(inputs[i]) + ", target = " + Arrays.toString(targets[i]));
            System.out.println("Output : " + mergeTriplets(inputs[i], targets[i]));
            System.out.println();
        }
    }

    public static boolean mergeTriplets(int[][] triplets, int[] target) {
        boolean[] matched = new boolean[3];
        for (int[] triplet : triplets) {
            if (triplet[0] > target[0] || triplet[1] > target[1] || triplet[2] > target[2]) {
                continue;
            }
            for (int k = 0; k < 3; k++) {
                if (triplet[k] == target[k]) {
                    matched[k] = true;
                }
            }
        }
        return matched[0] && matched[1] && matched[2];
    }
}
