package com.ranga.core;

/**
 * Given an hour and a number of minutes, return the smaller angle, in degrees, between the hour hand and the minute hand of an analog clock.
 *
 * <p><b>Algorithm Strategy (Degrees from 12 o'clock):</b>
 * <ol>
 *   <li>The minute hand moves 6 degrees per minute: {@code minuteAngle = minutes * 6}.</li>
 *   <li>The hour hand moves 30 degrees per hour plus 0.5 degrees per minute: {@code hourAngle = (hour % 12) * 30 + minutes * 0.5}.</li>
 *   <li>Take the absolute difference of the two angles.</li>
 *   <li>The smaller angle is {@code min(diff, 360 - diff)}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For 12:30: the hour hand is at {@code 0 + 15 = 15} degrees and the minute hand at 180, a difference of 165.</li>
 *   <li>Result: {@code 165.0}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1)} — A few arithmetic operations.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — No extra memory.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class AngleBetweenHandsOfAClock {

    public static void main(String[] args) {
        int[][] inputs = {{12, 30}, {3, 30}, {3, 15}, {4, 50}, {12, 0}, {6, 0}};
        for (int[] time : inputs) {
            System.out.println("Input  : hour = " + time[0] + ", minutes = " + time[1]);
            System.out.println("Output : " + angleClock(time[0], time[1]));
            System.out.println();
        }
    }

    public static double angleClock(int hour, int minutes) {
        double hourAngle = (hour % 12) * 30 + minutes * 0.5;
        double minuteAngle = minutes * 6;
        double diff = Math.abs(hourAngle - minuteAngle);
        return Math.min(diff, 360 - diff);
    }
}
