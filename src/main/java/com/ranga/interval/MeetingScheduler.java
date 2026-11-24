package com.ranga.interval;

import java.util.Arrays;

/**
 * Given the availability slots {@code slots1} and {@code slots2} of two people (each slot {@code [start, end]}, with no overlap inside one person's list) and a meeting duration, return the earliest time slot that works for both and has exactly that duration, or an empty array if there is none.
 *
 * <p><b>Algorithm Strategy (Sort Both Lists and Intersect with Two Pointers):</b>
 * <ol>
 *   <li>Sort both lists by start time.</li>
 *   <li>Compare the current slot of each person: the common window is {@code [max(start1, start2), min(end1, end2)]}.</li>
 *   <li>If the common window is at least {@code duration} long, return {@code [start, start + duration]}.</li>
 *   <li>Otherwise advance the pointer of the slot that ends first.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code slots1 = [[10,50],[60,120],[140,210]]}, {@code slots2 = [[0,15],[60,70]]} and duration 8: the first common window of 8 minutes or more is {@code [60, 70]}.</li>
 *   <li>Result: {@code [60, 68]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m log m + n log n)} — Sorting dominates; the scan is linear.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Apart from sorting.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MeetingScheduler {

    public static void main(String[] args) {
        int[][][] first = {{{10, 50}, {60, 120}, {140, 210}}, {{10, 50}, {60, 120}, {140, 210}}, {{5, 9}}, {{1, 4}}};
        int[][][] second = {{{0, 15}, {60, 70}}, {{0, 15}, {60, 70}}, {{1, 4}}, {{1, 4}}};
        int[] durations = {8, 12, 2, 3};
        for (int i = 0; i < first.length; i++) {
            System.out.println("Input  : slots1 = " + Arrays.deepToString(first[i]) + ", slots2 = " + Arrays.deepToString(second[i]) + ", duration = " + durations[i]);
            System.out.println("Output : " + Arrays.toString(minAvailableDuration(first[i].clone(), second[i].clone(), durations[i])));
            System.out.println();
        }
    }

    public static int[] minAvailableDuration(int[][] slots1, int[][] slots2, int duration) {
        if (slots1 == null || slots2 == null) {
            return new int[0];
        }
        Arrays.sort(slots1, (a, b) -> a[0] - b[0]);
        Arrays.sort(slots2, (a, b) -> a[0] - b[0]);
        int i = 0;
        int j = 0;
        while (i < slots1.length && j < slots2.length) {
            int start = Math.max(slots1[i][0], slots2[j][0]);
            int end = Math.min(slots1[i][1], slots2[j][1]);
            if (end - start >= duration) {
                return new int[]{start, start + duration};
            }
            if (slots1[i][1] < slots2[j][1]) {
                i++;
            } else {
                j++;
            }
        }
        return new int[0];
    }
}
