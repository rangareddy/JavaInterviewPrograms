package com.ranga.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * A directed graph of {@code n} nodes becomes a tree if the directions are ignored. Given the directed edges, for each node {@code i} return the minimum number of edge reversals so that node {@code i} can reach every other node by following directed edges.
 *
 * <p><b>Algorithm Strategy (Rerooting DP):</b>
 * <ol>
 *   <li>Build an undirected adjacency list where each edge stores a cost: 0 if the edge already points away from the current node, 1 if it points towards it (needs a reversal).</li>
 *   <li>Do one DFS from node 0 to compute {@code answer[0]}: the sum of the costs of all tree edges traversed.</li>
 *   <li>Rerooting from a parent {@code u} to its child {@code v} along an edge of cost {@code w}: {@code answer[v] = answer[u] + (w == 0 ? 1 : -1)}. If the edge originally pointed {@code u -> v} (cost 0 going down), re-rooting at {@code v} now needs a reversal; if it pointed {@code v -> u} (cost 1), it no longer does.</li>
 *   <li>Run a second DFS (iteratively, to avoid deep recursion) filling {@code answer} for every node.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 4} and edges {@code [[2,0],[2,1],[1,3]]}: from node 0 the edges 0-2 (must reverse), 2-1 and 1-3 cost 1 + 0 + 0.</li>
 *   <li>Result for node 0 is {@code 1}; the full answer is {@code [1, 1, 0, 2]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Two traversals of the tree.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The adjacency list and the answer.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MinimumEdgeReversalsSoEveryNodeIsReachable {

    public static void main(String[] args) {
        int[] ns = {4, 3, 3};
        int[][][] edges = {{{2, 0}, {2, 1}, {1, 3}}, {{1, 2}, {2, 0}}, {{0, 1}, {2, 1}}};
        for (int i = 0; i < ns.length; i++) {
            System.out.println("Input  : n = " + ns[i] + ", edges = " + Arrays.deepToString(edges[i]));
            System.out.println("Output : " + Arrays.toString(minEdgeReversals(ns[i], edges[i])));
            System.out.println();
        }
    }

    public static int[] minEdgeReversals(int n, int[][] edges) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] e : edges) {
            graph.get(e[0]).add(new int[]{e[1], 0});
            graph.get(e[1]).add(new int[]{e[0], 1});
        }
        int[] answer = new int[n];
        Deque<int[]> stack = new ArrayDeque<>();
        boolean[] seen = new boolean[n];
        stack.push(new int[]{0, -1});
        seen[0] = true;
        while (!stack.isEmpty()) {
            int[] top = stack.pop();
            for (int[] next : graph.get(top[0])) {
                if (!seen[next[0]]) {
                    seen[next[0]] = true;
                    answer[0] += next[1];
                    stack.push(new int[]{next[0], top[0]});
                }
            }
        }
        Arrays.fill(seen, false);
        stack.push(new int[]{0, -1});
        seen[0] = true;
        while (!stack.isEmpty()) {
            int[] top = stack.pop();
            for (int[] next : graph.get(top[0])) {
                if (!seen[next[0]]) {
                    seen[next[0]] = true;
                    answer[next[0]] = answer[top[0]] + (next[1] == 0 ? 1 : -1);
                    stack.push(new int[]{next[0], top[0]});
                }
            }
        }
        return answer;
    }
}
