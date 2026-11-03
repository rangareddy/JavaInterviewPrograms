package com.ranga.greedy;

import java.util.Arrays;

/**
 * There are {@code n} gas stations on a circular route. Station {@code i} offers {@code gas[i]} units of fuel and driving to the next station costs {@code cost[i]}. Starting with an empty tank, return the index of the station from which you can complete the circuit once clockwise, or {@code -1} if it is impossible. If a solution exists it is unique.
 *
 * <p><b>Algorithm Strategy (Single Pass Greedy):</b>
 * <ol>
 *   <li>If the total gas is less than the total cost, no start works: return {@code -1}.</li>
 *   <li>Otherwise a valid start exists. Scan the stations while keeping the {@code tank} balance since the candidate start.</li>
 *   <li>If the tank drops below zero at station {@code i}, none of the stations between the candidate and {@code i} can be the start (each would have arrived with at least as much fuel as the candidate). Make {@code i + 1} the new candidate and reset the tank.</li>
 *   <li>The final candidate is the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code gas = [1, 2, 3, 4, 5]} and {@code cost = [3, 4, 5, 1, 2]}: the tank is negative at stations 0, 1 and 2, so the candidate moves to 3; from 3 the balance stays non-negative.</li>
 *   <li>Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A few variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class GasStation {

    public static void main(String[] args) {
        int[][] gas = {{1, 2, 3, 4, 5}, {2, 3, 4}, {5}, {5, 1, 2, 3, 4}};
        int[][] cost = {{3, 4, 5, 1, 2}, {3, 4, 3}, {4}, {4, 4, 1, 5, 1}};
        for (int i = 0; i < gas.length; i++) {
            System.out.println("Input  : gas = " + Arrays.toString(gas[i]) + ", cost = " + Arrays.toString(cost[i]));
            System.out.println("Output : " + canCompleteCircuit(gas[i], cost[i]));
            System.out.println();
        }
    }

    public static int canCompleteCircuit(int[] gas, int[] cost) {
        int total = 0;          // overall balance of the whole circuit
        int tank = 0;           // balance since the candidate start
        int start = 0;
        for (int i = 0; i < gas.length; i++) {
            int diff = gas[i] - cost[i];
            total += diff;
            tank += diff;
            if (tank < 0) {
                start = i + 1;
                tank = 0;
            }
        }
        return total >= 0 ? start : -1;
    }
}
