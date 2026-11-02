package com.ranga.blind75.dp;

/**
 * You are climbing a staircase with {@code n} steps. Each time you can climb 1 or 2 steps. In how many distinct ways can you reach the top?
 *
 * <p><b>Algorithm Strategy (Dynamic Programming (Fibonacci Recurrence)):</b>
 * <ol>
 *   <li>To stand on step {@code i}, you came either from step {@code i - 1} (one step) or from step {@code i - 2} (two steps), so {@code ways(i) = ways(i - 1) + ways(i - 2)}.</li>
 *   <li>Base cases: there is 1 way to stay at the ground ({@code ways(0) = 1}) and 1 way to reach step 1.</li>
 *   <li>Only the last two values are needed, so keep two variables instead of a table.</li>
 *   <li>Roll them forward {@code n} times; the last value is the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 5}: ways are 1, 1, 2, 3, 5, 8 for steps 0 to 5.</li>
 *   <li>Result: {@code 8}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — A single loop.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two variables.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ClimbingStairs {

    public static void main(String[] args) {
        int[] inputs = {2, 3, 5, 1};
        for (int n : inputs) {
            System.out.println("Input  : n = " + n);
            System.out.println("Output : " + climbStairs(n));
            System.out.println();
        }
    }

    public static int climbStairs(int n) {
        if (n <= 1) {
            return 1;
        }

        int previous = 1;       // ways to reach step i - 2
        int current = 1;        // ways to reach step i - 1
        for (int i = 2; i <= n; i++) {
            int next = previous + current;
            previous = current;
            current = next;
        }
        return current;
    }
}
