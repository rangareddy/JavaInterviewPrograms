package com.ranga.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * There is an integer array {@code nums} of {@code n} unique elements, which you forgot. You are given {@code adjacentPairs}, where each {@code [u, v]} says that {@code u} and {@code v} are adjacent in {@code nums} (in any order). Return the original array; if several answers exist, any of them is accepted.
 *
 * <p><b>Algorithm Strategy (Find an End and Walk the Chain):</b>
 * <ol>
 *   <li>Build an adjacency map: each value maps to its (at most two) neighbours.</li>
 *   <li>The two end elements of {@code nums} are the only values with exactly one neighbour. Pick one as the start.</li>
 *   <li>Walk: from the current value move to the neighbour that is not the previous value.</li>
 *   <li>Collect the {@code n} values in order.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For pairs {@code [2,1],[3,4],[3,2]}: neighbours are 1:{2}, 2:{1,3}, 3:{4,2}, 4:{3}. Start at 1.</li>
 *   <li>Walking gives {@code 1, 2, 3, 4}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass to build the map and one to walk.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The adjacency map.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RestoreTheArrayFromAdjacentPairs {

    public static void main(String[] args) {
        int[][][] inputs = {{{2, 1}, {3, 4}, {3, 2}}, {{4, -2}, {1, 4}, {-3, 1}}, {{100000, -100000}}};
        for (int[][] pairs : inputs) {
            int[] restored = restoreArray(pairs);
            if (restored.length > 1 && restored[0] > restored[restored.length - 1]) {
                for (int i = 0, j = restored.length - 1; i < j; i++, j--) {
                    int temp = restored[i];
                    restored[i] = restored[j];
                    restored[j] = temp;
                }
            }
            System.out.println("Input  : adjacentPairs = " + Arrays.deepToString(pairs));
            System.out.println("Output : " + Arrays.toString(restored));
            System.out.println();
        }
    }

    public static int[] restoreArray(int[][] adjacentPairs) {
        if (adjacentPairs == null || adjacentPairs.length == 0) {
            return new int[0];
        }
        Map<Integer, List<Integer>> neighbours = new HashMap<>();
        for (int[] pair : adjacentPairs) {
            neighbours.computeIfAbsent(pair[0], k -> new ArrayList<>()).add(pair[1]);
            neighbours.computeIfAbsent(pair[1], k -> new ArrayList<>()).add(pair[0]);
        }
        int start = 0;
        for (Map.Entry<Integer, List<Integer>> entry : neighbours.entrySet()) {
            if (entry.getValue().size() == 1) {
                start = entry.getKey();
                break;
            }
        }
        int[] result = new int[adjacentPairs.length + 1];
        result[0] = start;
        int previous = Integer.MIN_VALUE;
        for (int i = 1; i < result.length; i++) {
            List<Integer> next = neighbours.get(result[i - 1]);
            result[i] = next.get(0) != previous ? next.get(0) : next.get(1);
            previous = result[i - 1];
        }
        return result;
    }
}
