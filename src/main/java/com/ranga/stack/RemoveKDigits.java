package com.ranga.stack;

/**
 * Given a string {@code num} representing a non-negative integer and an integer {@code k}, remove {@code k} digits so that the remaining number is the smallest possible. Return it as a string without leading zeros, or {@code "0"} if nothing is left.
 *
 * <p><b>Algorithm Strategy (Monotonic Increasing Stack):</b>
 * <ol>
 *   <li>Scan the digits left to right with a stack. When the current digit is smaller than the digit on top, the top digit is a peak that makes the number bigger, so pop it (using one removal) while removals remain.</li>
 *   <li>Push the current digit.</li>
 *   <li>After the scan, if removals remain, drop digits from the end: the stack is non-decreasing, so the tail is the largest part.</li>
 *   <li>Strip leading zeros and return {@code "0"} for an empty result.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code "1432219"} and {@code k = 3}: 4 is popped when 3 arrives, 3 is popped when the first 2 arrives, and the older 2 is popped when the last 1 arrives, leaving {@code 1219}.</li>
 *   <li>Result: {@code "1219"}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each digit is pushed and popped at most once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The stack.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RemoveKDigits {

    public static void main(String[] args) {
        String[] nums = {"1432219", "10200", "10", "112", "9"};
        int[] ks = {3, 1, 2, 1, 1};
        for (int i = 0; i < nums.length; i++) {
            System.out.println("Input  : num = \"" + nums[i] + "\", k = " + ks[i]);
            System.out.println("Output : \"" + removeKdigits(nums[i], ks[i]) + "\"");
            System.out.println();
        }
    }

    public static String removeKdigits(String num, int k) {
        if (num == null || num.length() <= k) {
            return "0";
        }
        StringBuilder stack = new StringBuilder();
        for (char c : num.toCharArray()) {
            while (k > 0 && stack.length() > 0 && stack.charAt(stack.length() - 1) > c) {
                stack.deleteCharAt(stack.length() - 1);
                k--;
            }
            stack.append(c);
        }
        stack.setLength(stack.length() - k);
        int start = 0;
        while (start < stack.length() - 1 && stack.charAt(start) == '0') {
            start++;
        }
        return stack.substring(start);
    }
}
