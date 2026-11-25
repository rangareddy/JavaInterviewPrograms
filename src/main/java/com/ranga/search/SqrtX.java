package com.ranga.search;

/**
 * Given a non-negative integer {@code x}, return the integer square root of {@code x}: the largest integer {@code r} such that {@code r * r <= x}. Do not use a built-in exponent or square root function.
 *
 * <p><b>Algorithm Strategy (Binary Search on the Root):</b>
 * <ol>
 *   <li>The root lies in {@code [0, x]}; narrowing to {@code [1, x / 2 + 1]} is enough for {@code x >= 2}.</li>
 *   <li>Check the midpoint {@code mid}: compare {@code mid} with {@code x / mid} to avoid overflow in {@code mid * mid}.</li>
 *   <li>If {@code mid <= x / mid}, {@code mid} is a candidate: remember it and look higher.</li>
 *   <li>Otherwise look lower. The last remembered candidate is the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code x = 8}: candidates 4 (16 > 8, too high), 2 (4 <= 8, candidate), 3 (9 > 8, too high).</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log x)} — The range halves each step.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A few integers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SqrtX {

    public static void main(String[] args) {
        int[] inputs = {4, 8, 0, 1, 2147395599, Integer.MAX_VALUE};
        for (int x : inputs) {
            System.out.println("Input  : x = " + x);
            System.out.println("Output : " + mySqrt(x));
            System.out.println();
        }
    }

    public static int mySqrt(int x) {
        if (x < 2) {
            return x;
        }
        int lo = 1;
        int hi = x / 2 + 1;
        int answer = 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (mid <= x / mid) {
                answer = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return answer;
    }
}
