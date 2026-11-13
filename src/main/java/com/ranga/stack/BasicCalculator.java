package com.ranga.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Evaluate a string expression made of non-negative integers, {@code +}, {@code -}, parentheses and spaces. The {@code -} can also be a unary operator (for example {@code -(2+3)}), and the expression is always valid. Do not use {@code eval}.
 *
 * <p><b>Algorithm Strategy (Running Sum with a Stack of Signs):</b>
 * <ol>
 *   <li>Keep a running {@code result}, the current {@code sign} ({@code +1} or {@code -1}) applying to the next number, and a {@code number} being read digit by digit.</li>
 *   <li>On {@code +} or {@code -}, add {@code sign * number} to {@code result}, reset {@code number} and set {@code sign} to the new operator.</li>
 *   <li>On {@code (}, push the current {@code result} and {@code sign} onto a stack, then restart with {@code result = 0} and {@code sign = 1} to evaluate the inside of the parentheses as its own expression.</li>
 *   <li>On {@code )}, finish the pending number, then combine: {@code result = savedResult + savedSign * result}. At the end of the string add the last number.</li>
 *   <li>A leading {@code -} or a {@code -} right after {@code (} works without special cases, because {@code result} is 0 at that point. Valid input never has two operators in a row such as {@code --}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code 1 - (4 - (5 - 1))}: the innermost group is {@code 5 - 1 = 4}, the next is {@code 4 - 4 = 0}, and the whole expression is {@code 1 - 0 = 1}.</li>
 *   <li>For {@code (1+(4+5+2)-3)+(6+8)}: the groups evaluate to 9 and 14, and the result is {@code 23}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass over the characters.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The stack depth is the nesting depth of the parentheses.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BasicCalculator {

    public static void main(String[] args) {
        String[] inputs = {"1 + 1", " 2-1 + 2 ", "(1+(4+5+2)-3)+(6+8)", "1 - (4 - (5 - 1))", "-(2 + 3)"};
        for (String s : inputs) {
            System.out.println("Input  : s = \"" + s + "\"");
            System.out.println("Output : " + calculate(s));
            System.out.println();
        }
    }

    public static int calculate(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        int result = 0;
        int number = 0;
        int sign = 1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                number = number * 10 + (c - '0');
            } else if (c == '+' || c == '-') {
                result += sign * number;
                number = 0;
                sign = c == '+' ? 1 : -1;
            } else if (c == '(') {
                stack.push(result);
                stack.push(sign);
                result = 0;
                sign = 1;
            } else if (c == ')') {
                result += sign * number;
                number = 0;
                result = stack.pop() * result + stack.pop();
            }
        }
        return result + sign * number;
    }
}
