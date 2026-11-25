package com.ranga.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * There is a forest with an unknown number of rabbits. We asked {@code n} rabbits "How many other rabbits have the same colour as you?" and collected the answers in {@code answers}, where {@code answers[i]} is the answer of the {@code i}-th rabbit. Return the minimum number of rabbits that could be in the forest.
 *
 * <p><b>Algorithm Strategy (Group Same Answers into Colour Groups):</b>
 * <ol>
 *   <li>A rabbit that answers {@code x} belongs to a colour group of {@code x + 1} rabbits.</li>
 *   <li>Count how many rabbits gave each answer.</li>
 *   <li>For an answer {@code x} given by {@code c} rabbits, they need {@code ceil(c / (x + 1))} groups of size {@code x + 1} to be consistent.</li>
 *   <li>Sum {@code groups * (x + 1)} over all answers.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 1, 2]}: two rabbits say 1, so one group of 2; one rabbit says 2, so one group of 3.</li>
 *   <li>Result: {@code 2 + 3 = 5}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass to count.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The counts.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RabbitsInForest {

    public static void main(String[] args) {
        int[][] inputs = {{1, 1, 2}, {10, 10, 10}, {0, 0, 1, 1, 1}, {}};
        for (int[] answers : inputs) {
            System.out.println("Input  : answers = " + Arrays.toString(answers));
            System.out.println("Output : " + numRabbits(answers));
            System.out.println();
        }
    }

    public static int numRabbits(int[] answers) {
        if (answers == null) {
            return 0;
        }
        Map<Integer, Integer> counts = new HashMap<>();
        for (int answer : answers) {
            counts.merge(answer, 1, Integer::sum);
        }
        int total = 0;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            int groupSize = entry.getKey() + 1;
            int groups = (entry.getValue() + groupSize - 1) / groupSize;
            total += groups * groupSize;
        }
        return total;
    }
}
