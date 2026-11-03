package com.ranga.heap;

import java.util.Arrays;

/**
 * Given a list of CPU tasks, each labelled with a letter, and a cooldown {@code n}, return the least number of intervals the CPU needs to finish all tasks. Each interval runs one task or stays idle, and two identical tasks must be separated by at least {@code n} intervals.
 *
 * <p><b>Algorithm Strategy (Greedy Formula from the Most Frequent Task):</b>
 * <ol>
 *   <li>The most frequent task (frequency {@code maxFreq}) dictates the schedule: its executions form {@code maxFreq - 1} gaps that each need at least {@code n} other intervals, so a frame of {@code (maxFreq - 1) * (n + 1)} intervals comes before the last copy.</li>
 *   <li>Add the last row: one interval for every task that has the maximum frequency ({@code countMax}), since they all appear in the final row.</li>
 *   <li>The frame length is therefore {@code (maxFreq - 1) * (n + 1) + countMax}.</li>
 *   <li>If there are so many different tasks that no idle time is needed, the answer is simply the number of tasks, so take {@code max(frame, tasks.length)}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code A A A B B B} and {@code n = 2}: {@code maxFreq = 3}, {@code countMax = 2}, frame {@code (3 - 1) * 3 + 2 = 8}: {@code A B _ A B _ A B}.</li>
 *   <li>The tasks count is 6 (smaller), so the answer is {@code 8}. With {@code n = 0} the frame is 5, and {@code max(5, 6) = 6}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(t)} — {@code t} = number of tasks; counting plus a scan of 26 counters.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A 26-element counter array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class TaskScheduler {

    public static void main(String[] args) {
        char[][] inputs = {
                {'A', 'A', 'A', 'B', 'B', 'B'},
                {'A', 'A', 'A', 'B', 'B', 'B'},
                {'A', 'A', 'A', 'A', 'A', 'A', 'B', 'C', 'D', 'E', 'F', 'G'},
                {'A', 'B', 'C', 'D'}
        };
        int[] cooldowns = {2, 0, 2, 3};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : tasks = " + Arrays.toString(inputs[i]) + ", n = " + cooldowns[i]);
            System.out.println("Output : " + leastInterval(inputs[i], cooldowns[i]));
            System.out.println();
        }
    }

    public static int leastInterval(char[] tasks, int n) {
        int[] counts = new int[26];
        for (char task : tasks) {
            counts[task - 'A']++;
        }

        int maxFreq = 0;
        for (int count : counts) {
            maxFreq = Math.max(maxFreq, count);
        }
        int countMax = 0;
        for (int count : counts) {
            if (count == maxFreq) {
                countMax++;
            }
        }
        return Math.max(tasks.length, (maxFreq - 1) * (n + 1) + countMax);
    }
}
