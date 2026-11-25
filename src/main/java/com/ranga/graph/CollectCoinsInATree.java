package com.ranga.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

/**
 * An undirected tree has {@code n} nodes numbered {@code 0} to {@code n - 1}, given by {@code edges}, and an array {@code coins} where {@code coins[i]} is 1 if node {@code i} holds a coin. You may start at any node. In one move you travel along an edge, and from your current node you can collect all coins within distance 2 for free. Return the minimum number of edges you must traverse to collect all coins and return to the starting node.
 *
 * <p><b>Algorithm Strategy (Peel Leaves, then Two Layers):</b>
 * <ol>
 *   <li>Repeatedly remove leaves that hold no coin (they are never worth visiting). This is a BFS from the leaves, using degrees.</li>
 *   <li>After that, every remaining leaf holds a coin. Because you can collect within distance 2, remove two more layers of leaves from the remaining tree.</li>
 *   <li>The remaining nodes form a tree that must be walked and returned from: it costs {@code 2 * (remaining nodes - 1)} edges.</li>
 *   <li>If at most one node remains, the answer is 0.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the path {@code 0 - 1 - 2 - 3 - 4 - 5} with coins at nodes 0 and 5: every middle node is on the way, and removing two layers from each end leaves two nodes.</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each node and edge is processed once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — Adjacency lists, degrees and a queue.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CollectCoinsInATree {

    public static void main(String[] args) {
        int[][] coins = {{1, 0, 0, 0, 0, 1}, {0, 0, 0, 1, 1, 0, 0, 1}, {1}, {0, 0, 0}};
        int[][][] edges = {{{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}}, {{0, 1}, {0, 2}, {1, 3}, {1, 4}, {2, 5}, {5, 6}, {5, 7}}, {}, {{0, 1}, {1, 2}}};
        for (int i = 0; i < coins.length; i++) {
            System.out.println("Input  : coins = " + Arrays.toString(coins[i]) + ", edges = " + Arrays.deepToString(edges[i]));
            System.out.println("Output : " + collectTheCoins(coins[i], edges[i]));
            System.out.println();
        }
    }

    public static int collectTheCoins(int[] coins, int[][] edges) {
        int n = coins.length;
        List<List<Integer>> graph = new ArrayList<>();
        int[] degree = new int[n];
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] e : edges) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
            degree[e[0]]++;
            degree[e[1]]++;
        }
        boolean[] removed = new boolean[n];
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (degree[i] <= 1 && coins[i] == 0) {
                queue.add(i);
            }
        }
        int remaining = n;
        while (!queue.isEmpty()) {
            int leaf = queue.poll();
            if (removed[leaf]) {
                continue;
            }
            removed[leaf] = true;
            remaining--;
            for (int next : graph.get(leaf)) {
                if (!removed[next] && --degree[next] <= 1 && coins[next] == 0) {
                    queue.add(next);
                }
            }
        }
        for (int layer = 0; layer < 2; layer++) {
            List<Integer> leaves = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (!removed[i] && degree[i] <= 1) {
                    leaves.add(i);
                }
            }
            for (int leaf : leaves) {
                removed[leaf] = true;
                remaining--;
                for (int next : graph.get(leaf)) {
                    if (!removed[next]) {
                        degree[next]--;
                    }
                }
            }
        }
        return Math.max(0, 2 * (remaining - 1));
    }
}
