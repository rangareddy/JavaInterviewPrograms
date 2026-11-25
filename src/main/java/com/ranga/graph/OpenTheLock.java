package com.ranga.graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * A lock has four circular wheels, each showing a digit from {@code 0} to {@code 9}, starting at {@code "0000"}. One move turns one wheel one step up or down ({@code 9} wraps to {@code 0} and back). Some combinations in {@code deadends} lock the wheels permanently. Given a {@code target}, return the minimum number of moves to reach it, or {@code -1} if it is impossible.
 *
 * <p><b>Algorithm Strategy (BFS over the 10,000 States):</b>
 * <ol>
 *   <li>Put all deadends in a set and mark them as visited; if {@code "0000"} is a deadend return -1.</li>
 *   <li>BFS from {@code "0000"}: each state has up to 8 neighbours (4 wheels, 2 directions).</li>
 *   <li>Skip visited states, add the rest to the queue and count one move per level.</li>
 *   <li>Return the level at which the target is dequeued, or -1 when the queue empties.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code deadends = ["0201","0101","0102","1212","2002"]} and {@code target = "0202"}: a shortest path is {@code 0000 -> 1000 -> 1100 -> 1200 -> 1201 -> 1202 -> 0202}.</li>
 *   <li>Result: {@code 6}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(10^4 * 8)} — At most 10,000 states with 8 neighbours each.</li>
 *   <li><b>Space Complexity:</b> {@code O(10^4)} — The visited set and the queue.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class OpenTheLock {

    public static void main(String[] args) {
        String[][] deadends = {{"0201", "0101", "0102", "1212", "2002"}, {"8888"}, {"8887", "8889", "8878", "8898", "8788", "8988", "7888", "9888"}, {"0000"}};
        String[] targets = {"0202", "0009", "8888", "8888"};
        for (int i = 0; i < deadends.length; i++) {
            System.out.println("Input  : deadends = " + Arrays.toString(deadends[i]) + ", target = " + targets[i]);
            System.out.println("Output : " + openLock(deadends[i], targets[i]));
            System.out.println();
        }
    }

    public static int openLock(String[] deadends, String target) {
        Set<String> visited = new HashSet<>(Arrays.asList(deadends));
        if (visited.contains("0000")) {
            return -1;
        }
        Queue<String> queue = new ArrayDeque<>();
        queue.add("0000");
        visited.add("0000");
        int moves = 0;
        while (!queue.isEmpty()) {
            for (int size = queue.size(); size > 0; size--) {
                String state = queue.poll();
                if (state.equals(target)) {
                    return moves;
                }
                for (int wheel = 0; wheel < 4; wheel++) {
                    for (int delta = -1; delta <= 1; delta += 2) {
                        char[] next = state.toCharArray();
                        next[wheel] = (char) ('0' + (next[wheel] - '0' + delta + 10) % 10);
                        String candidate = new String(next);
                        if (visited.add(candidate)) {
                            queue.add(candidate);
                        }
                    }
                }
            }
            moves++;
        }
        return -1;
    }
}
