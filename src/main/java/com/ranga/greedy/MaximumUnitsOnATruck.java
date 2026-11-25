package com.ranga.greedy;

import java.util.Arrays;

/**
 * You are assigned to put some amount of boxes onto one truck. {@code boxTypes[i] = [numberOfBoxes, unitsPerBox]} means there are {@code numberOfBoxes} boxes of type {@code i}, each containing {@code unitsPerBox} units. The truck can hold at most {@code truckSize} boxes. Return the maximum total number of units that can be put on the truck.
 *
 * <p><b>Algorithm Strategy (Sort Box Types by Units per Box):</b>
 * <ol>
 *   <li>Every box takes one slot, so the best use of each slot is the box with the most units.</li>
 *   <li>Sort the box types by {@code unitsPerBox} in descending order.</li>
 *   <li>Take as many boxes as possible from each type until the truck is full.</li>
 *   <li>Add {@code taken * unitsPerBox} to the total.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[1, 3], [2, 2], [3, 1]]} and {@code truckSize = 4}: take 1 box of 3 units, 2 boxes of 2 units, and 1 box of 1 unit.</li>
 *   <li>Total: {@code 3 + 4 + 1 = 8}.</li>
 *   <li>Result: {@code 8}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m log m)} — Sorting {@code m} box types.</li>
 *   <li><b>Space Complexity:</b> {@code O(m)} — Sorted copy of the input.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MaximumUnitsOnATruck {

    public static void main(String[] args) {
        int[][][] inputs = {{{1, 3}, {2, 2}, {3, 1}}, {{5, 10}, {2, 5}, {4, 7}, {3, 9}}, {{1, 1}}, {}};
        int[] sizes = {4, 10, 5, 3};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : boxTypes = " + Arrays.deepToString(inputs[i]) + ", truckSize = " + sizes[i]);
            System.out.println("Output : " + maximumUnits(inputs[i], sizes[i]));
            System.out.println();
        }
    }

    public static int maximumUnits(int[][] boxTypes, int truckSize) {
        if (boxTypes == null) {
            return 0;
        }
        int[][] sorted = boxTypes.clone();
        Arrays.sort(sorted, (a, b) -> Integer.compare(b[1], a[1]));
        int units = 0;
        int remaining = truckSize;
        for (int[] type : sorted) {
            if (remaining == 0) {
                break;
            }
            int take = Math.min(remaining, type[0]);
            units += take * type[1];
            remaining -= take;
        }
        return units;
    }
}
