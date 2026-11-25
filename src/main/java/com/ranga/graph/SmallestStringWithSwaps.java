package com.ranga.graph;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * You are given a string {@code s} and an array of index pairs {@code pairs}, where {@code pairs[i] = [a, b]} means the characters at indexes {@code a} and {@code b} can be swapped. You may swap any pair any number of times. Return the lexicographically smallest string that {@code s} can be changed to.
 *
 * <p><b>Algorithm Strategy (Group Positions, Sort Within Each Group):</b>
 * <ol>
 *   <li>Positions linked by swap pairs, directly or through a chain, can be arranged in any order, so they form connected components.</li>
 *   <li>Build the components with Union-Find.</li>
 *   <li>For each component, collect its characters in a min-heap.</li>
 *   <li>Walk the string from left to right and, at each index, place the smallest remaining character of that index's component.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code s = dcab} with pairs {@code [0, 3]} and {@code [1, 2]}: components are {@code {0, 3}} with {@code d, b} and {@code {1, 2}} with {@code c, a}.</li>
 *   <li>Sorted, they place {@code b} at index 0, {@code a} at index 1, {@code c} at index 2, {@code d} at index 3.</li>
 *   <li>Result: {@code bacd}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O((n + p) log n)} — Union-Find over {@code p} pairs plus heap operations for {@code n} characters.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — Parent array and heaps.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SmallestStringWithSwaps {

    public static void main(String[] args) {
        String[] strings = {"dcab", "dcab", "cba"};
        int[][][] pairs = {{{0, 3}, {1, 2}}, {{0, 3}, {1, 2}, {0, 2}}, {{0, 1}, {1, 2}}};
        for (int i = 0; i < strings.length; i++) {
            System.out.println("Input  : s = \"" + strings[i] + "\", pairs = " + Arrays.deepToString(pairs[i]));
            System.out.println("Output : " + smallestStringWithSwaps(strings[i], pairs[i]));
            System.out.println();
        }
    }

    public static String smallestStringWithSwaps(String s, int[][] pairs) {
        int n = s.length();
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        for (int[] pair : pairs) {
            int a = find(parent, pair[0]);
            int b = find(parent, pair[1]);
            if (a != b) {
                parent[a] = b;
            }
        }
        @SuppressWarnings("unchecked")
        PriorityQueue<Character>[] heaps = new PriorityQueue[n];
        for (int i = 0; i < n; i++) {
            int root = find(parent, i);
            if (heaps[root] == null) {
                heaps[root] = new PriorityQueue<>();
            }
            heaps[root].add(s.charAt(i));
        }
        StringBuilder result = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            result.append(heaps[find(parent, i)].poll());
        }
        return result.toString();
    }

    private static int find(int[] parent, int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }
}
