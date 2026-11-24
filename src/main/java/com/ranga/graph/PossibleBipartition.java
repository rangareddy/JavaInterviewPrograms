package com.ranga.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

/**
 * Given {@code n} people labelled {@code 1} to {@code n} and an array {@code dislikes} where {@code dislikes[i] = [a, b]} means person {@code a} and person {@code b} dislike each other, return {@code true} if it is possible to split everyone into two groups so that no two people who dislike each other are in the same group.
 *
 * <p><b>Algorithm Strategy (Build the Dislike Graph, then Two-Colour):</b>
 * <ol>
 *   <li>Build an adjacency list with an edge for every dislike pair.</li>
 *   <li>Colour people with BFS: an uncoloured person gets colour 1 and their dislikes get the opposite colour.</li>
 *   <li>If a person and someone they dislike receive the same colour, the split is impossible.</li>
 *   <li>Start a new BFS for every uncoloured person, since the graph can be disconnected.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 4} and {@code [[1,2],[1,3],[2,4]]}: put 1 and 4 in one group and 2 and 3 in the other.</li>
 *   <li>Result: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n + e)} — Every person and every dislike pair is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n + e)} — The graph and the colours.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PossibleBipartition {

    public static void main(String[] args) {
        int[] ns = {4, 3, 5, 1};
        int[][][] dislikes = {{{1, 2}, {1, 3}, {2, 4}}, {{1, 2}, {1, 3}, {2, 3}}, {{1, 2}, {2, 3}, {3, 4}, {4, 5}, {1, 5}}, {}};
        for (int i = 0; i < ns.length; i++) {
            System.out.println("Input  : n = " + ns[i] + ", dislikes = " + Arrays.deepToString(dislikes[i]));
            System.out.println("Output : " + possibleBipartition(ns[i], dislikes[i]));
            System.out.println();
        }
    }

    public static boolean possibleBipartition(int n, int[][] dislikes) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        if (dislikes != null) {
            for (int[] d : dislikes) {
                graph.get(d[0]).add(d[1]);
                graph.get(d[1]).add(d[0]);
            }
        }
        int[] color = new int[n + 1];
        for (int start = 1; start <= n; start++) {
            if (color[start] != 0) {
                continue;
            }
            color[start] = 1;
            Queue<Integer> queue = new ArrayDeque<>();
            queue.add(start);
            while (!queue.isEmpty()) {
                int person = queue.poll();
                for (int other : graph.get(person)) {
                    if (color[other] == 0) {
                        color[other] = 3 - color[person];
                        queue.add(other);
                    } else if (color[other] == color[person]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
