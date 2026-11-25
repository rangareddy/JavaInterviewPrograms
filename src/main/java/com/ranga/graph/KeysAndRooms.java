package com.ranga.graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * There are {@code n} rooms labeled {@code 0} to {@code n - 1}, and all rooms are locked except room {@code 0}. Each room holds a set of keys, and a key {@code i} opens room {@code i}. Given {@code rooms}, where {@code rooms[i]} is the list of keys in room {@code i}, return {@code true} if you can visit every room.
 *
 * <p><b>Algorithm Strategy (Depth-First Search from Room 0):</b>
 * <ol>
 *   <li>Treat each room as a node and each key as a directed edge to another room.</li>
 *   <li>Start at room 0 and walk with an explicit stack, marking every room the first time it is reached.</li>
 *   <li>Push every unvisited room whose key you find.</li>
 *   <li>When the stack is empty, all rooms were visited exactly when the count of visited rooms equals {@code n}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[1], [2], [3], []]}: room 0 holds key 1, room 1 holds key 2, room 2 holds key 3, so all four rooms are reached.</li>
 *   <li>Result: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n + k)} — Every room and every key is processed once, where {@code k} is the total number of keys.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The visited array and the stack.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class KeysAndRooms {

    public static void main(String[] args) {
        int[][][] inputs = {{{1}, {2}, {3}, {}}, {{1, 3}, {3, 0, 1}, {2}, {0}}, {{}}, {{2}, {}, {1}}};
        for (int[][] rooms : inputs) {
            System.out.println("Input  : rooms = " + Arrays.deepToString(rooms));
            System.out.println("Output : " + canVisitAllRooms(rooms));
            System.out.println();
        }
    }

    public static boolean canVisitAllRooms(int[][] rooms) {
        if (rooms == null || rooms.length == 0) {
            return true;
        }
        boolean[] seen = new boolean[rooms.length];
        Deque<Integer> stack = new ArrayDeque<>();
        seen[0] = true;
        stack.push(0);
        int visited = 1;
        while (!stack.isEmpty()) {
            int room = stack.pop();
            for (int key : rooms[room]) {
                if (!seen[key]) {
                    seen[key] = true;
                    visited++;
                    stack.push(key);
                }
            }
        }
        return visited == rooms.length;
    }
}
