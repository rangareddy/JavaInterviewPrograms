package com.ranga.string;

/**
 * Given the binary representation of an integer as a string {@code s}, return the number of steps to reduce it to {@code 1}. If the current number is even, divide it by {@code 2}; if it is odd, add {@code 1} to it. It is guaranteed that you can always reach {@code 1}.
 *
 * <p><b>Algorithm Strategy (Process Bits from the Right with a Carry):</b>
 * <ol>
 *   <li>Dividing an even number by 2 removes the last bit, which is {@code 0}. That is one step.</li>
 *   <li>An odd number needs one addition (which turns the trailing {@code 1}s into {@code 0}s and creates a carry) and then a division: two steps.</li>
 *   <li>Scan from the last bit to the second bit, adding any pending carry to each bit.</li>
 *   <li>A bit value of {@code 1} costs 2 steps and sets the carry; {@code 0} or {@code 2} costs 1 step, and the carry stays if the value was 2. After the scan, a leftover carry costs one more step.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 1101} (13): bit 1 costs 2 (carry 1); the next bit 0 plus carry is 1, cost 2 (carry 1); the next bit 1 plus carry is 2, cost 1 (carry 1).</li>
 *   <li>Total 5, plus 1 for the final carry.</li>
 *   <li>Result: {@code 6}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass over the bits.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — A step counter and a carry.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class NumberOfStepsToReduceABinaryNumberToOne {

    public static void main(String[] args) {
        String[] inputs = {"1101", "10", "1", "1111", "100000"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + numSteps(s));
            System.out.println();
        }
    }

    public static int numSteps(String s) {
        int steps = 0;
        int carry = 0;
        for (int i = s.length() - 1; i > 0; i--) {
            int bit = (s.charAt(i) - '0') + carry;
            if (bit == 1) {
                steps += 2;
                carry = 1;
            } else {
                steps += 1;
                carry = bit == 2 ? 1 : 0;
            }
        }
        return steps + carry;
    }
}
