package com.ranga.stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * A single-threaded CPU executes {@code n} functions, each with an id from 0 to {@code n - 1}. Given log lines like {@code "0:start:3"} and {@code "1:end:4"} (function id, event, timestamp), return the exclusive time of each function: the time spent in the function itself, not counting the time spent in functions it called. A function's {@code end} timestamp is the end of that unit of time, so {@code start:0} followed by {@code end:1} is 2 units.
 *
 * <p><b>Algorithm Strategy (Stack of Running Functions):</b>
 * <ol>
 *   <li>Keep a stack of function ids and the timestamp {@code prev} when the current function last started or resumed.</li>
 *   <li>On {@code start}: if the stack is not empty, add {@code time - prev} to the function on top (it ran until now), then push the new function and set {@code prev = time}.</li>
 *   <li>On {@code end}: pop the function, add {@code time - prev + 1} to it, and set {@code prev = time + 1} so the caller resumes after this unit.</li>
 *   <li>The totals per function id are the answer.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code ["0:start:0","1:start:2","1:end:5","0:end:6"]}: function 1 runs 2 to 5 (4 units); function 0 runs 0 to 1 and 6 (3 units).</li>
 *   <li>Result: {@code [3, 4]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(m)} — {@code m} is the number of log lines.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The stack and the result.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ExclusiveTimeOfFunctions {

    public static void main(String[] args) {
        int[] ns = {2, 1, 2};
        String[][] logs = {{"0:start:0", "1:start:2", "1:end:5", "0:end:6"}, {"0:start:0", "0:start:2", "0:end:5", "0:start:6", "0:end:6", "0:end:7"}, {"0:start:0", "0:start:2", "0:end:5", "1:start:6", "1:end:6", "0:end:7"}};
        for (int i = 0; i < ns.length; i++) {
            System.out.println("Input  : n = " + ns[i] + ", logs = " + Arrays.toString(logs[i]));
            System.out.println("Output : " + Arrays.toString(exclusiveTime(ns[i], Arrays.asList(logs[i]))));
            System.out.println();
        }
    }

    public static int[] exclusiveTime(int n, List<String> logs) {
        int[] result = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        int prev = 0;
        for (String log : logs) {
            String[] parts = log.split(":");
            int id = Integer.parseInt(parts[0]);
            int time = Integer.parseInt(parts[2]);
            if (parts[1].equals("start")) {
                if (!stack.isEmpty()) {
                    result[stack.peek()] += time - prev;
                }
                stack.push(id);
                prev = time;
            } else {
                result[stack.pop()] += time - prev + 1;
                prev = time + 1;
            }
        }
        return result;
    }
}
