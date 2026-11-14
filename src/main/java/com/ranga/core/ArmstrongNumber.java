package com.ranga.core;

import java.util.ArrayList;
import java.util.List;

/**
 * A number with {@code d} digits is an Armstrong (narcissistic) number if the sum of its digits, each raised to the power {@code d}, equals the number itself. For example {@code 153 = 1^3 + 5^3 + 3^3}. Check a number and list all Armstrong numbers in a range.
 *
 * <p><b>Algorithm Strategy (Digit Extraction with Power Sum):</b>
 * <ol>
 *   <li>Count the digits {@code d} of the number.</li>
 *   <li>Take the digits one by one with {@code n % 10} and drop each with {@code n / 10}.</li>
 *   <li>Add {@code digit^d} to a running {@code sum}. Use integer multiplication rather than {@code Math.pow} to avoid floating point rounding.</li>
 *   <li>The number is an Armstrong number when {@code sum == number}. To list them, test every number in the range.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 153}: {@code d = 3}, {@code 1 + 125 + 27 = 153}: Armstrong.</li>
 *   <li>For {@code 154}: {@code 1 + 125 + 64 = 190 != 154}: not Armstrong. The Armstrong numbers up to 1000 are {@code 1..9, 153, 370, 371, 407}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(d) per number} — {@code d} digits, each raised to the power {@code d} in {@code O(d)} multiplications, so O(d^2) in total.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A few variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ArmstrongNumber {

    public static void main(String[] args) {
        int[] inputs = {153, 154, 9474, 370, 0, 1634};
        for (int number : inputs) {
            System.out.println("Input  : number = " + number);
            System.out.println("Output : " + (isArmstrong(number) ? "Armstrong" : "Not Armstrong"));
            System.out.println();
        }

        System.out.println("Input  : Armstrong numbers from 1 to 1000");
        System.out.println("Output : " + armstrongNumbers(1, 1000));
    }

    public static boolean isArmstrong(int number) {
        if (number < 0) {
            return false;
        }

        int digits = String.valueOf(number).length();
        long sum = 0;
        for (int n = number; n > 0; n /= 10) {
            sum += power(n % 10, digits);
        }
        return sum == number;
    }

    public static List<Integer> armstrongNumbers(int from, int to) {
        List<Integer> result = new ArrayList<>();
        for (int n = from; n <= to; n++) {
            if (isArmstrong(n)) {
                result.add(n);
            }
        }
        return result;
    }

    private static long power(int base, int exponent) {
        long result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }
}
