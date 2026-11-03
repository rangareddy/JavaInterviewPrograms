package com.ranga.stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Evaluate an arithmetic expression given in Reverse Polish Notation (postfix). The valid operators are {@code +}, {@code -}, {@code *} and {@code /}; division between two integers truncates toward zero, and every expression is valid.
 *
 * <p><b>Algorithm Strategy (Stack of Operands):</b>
 * <ol>
 *   <li>Scan the tokens from left to right with a stack of integers.</li>
 *   <li>A number is pushed on the stack.</li>
 *   <li>An operator pops the two top operands ({@code b} first, then {@code a}), applies {@code a op b} (note the order for {@code -} and {@code /}) and pushes the result.</li>
 *   <li>After the last token the stack holds exactly one value: the result.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code ["4", "13", "5", "/", "+"]}: push 4, 13, 5; {@code /} pops 5 and 13 and pushes {@code 13 / 5 = 2}; {@code +} pops 2 and 4 and pushes 6.</li>
 *   <li>Result: {@code 6}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each token is handled once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The stack may hold all operands.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class EvaluateReversePolishNotation {

    public static void main(String[] args) {
        String[][] inputs = {
                {"2", "1", "+", "3", "*"},
                {"4", "13", "5", "/", "+"},
                {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"},
                {"3", "-4", "+"}
        };
        for (String[] tokens : inputs) {
            System.out.println("Input  : tokens = " + Arrays.toString(tokens));
            System.out.println("Output : " + evalRPN(tokens));
            System.out.println();
        }
    }

    public static int evalRPN(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (String token : tokens) {
            switch (token) {
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-": {
                    int b = stack.pop();
                    stack.push(stack.pop() - b);
                    break;
                }
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/": {
                    int b = stack.pop();
                    stack.push(stack.pop() / b);
                    break;
                }
                default:
                    stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }
}
