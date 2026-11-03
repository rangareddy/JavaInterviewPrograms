package com.ranga.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * A network has {@code n} nodes labeled {@code 1} to {@code n}. Given directed travel times {@code times[i] = [u, v, w]} (a signal sent from {@code u} reaches {@code v} after {@code w} time units) and a starting node {@code k}, return the minimum time for all nodes to receive a signal sent from {@code k}, or -1 if it is impossible.
 *
 * <p><b>Algorithm Strategy (Dijkstra's Shortest Paths):</b>
 * <ol>
 *   <li>The time for all nodes to receive the signal is the largest shortest-path distance from {@code k}.</li>
 *   <li>Build adjacency lists. Keep {@code dist[]} (initially infinity, 0 for {@code k}) and a min-heap of {@code (distance, node)}.</li>
 *   <li>Repeatedly take the node with the smallest tentative distance; skip stale heap entries. For each outgoing edge {@code (u, v, w)}, relax: if {@code dist[u] + w < dist[v]}, update {@code dist[v]} and push it.</li>
 *   <li>If any node was never reached the answer is -1; otherwise it is the maximum distance.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code times = [[2,1,1],[2,3,1],[3,4,1]]}, {@code n = 4}, {@code k = 2}: distances are 1 (node 1), 0 (node 2), 1 (node 3), 2 (node 4).</li>
 *   <li>Result: {@code 2}. Node 1 cannot be reached in {@code ([[1,2,1]], 2, 2)}: {@code -1}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(E log V)} — Each edge may push one heap entry.</li>
 *   <li><b>Space Complexity:</b> {@code O(V + E)} — Adjacency lists, distances and heap.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class NetworkDelayTime {

    public static void main(String[] args) {
        int[][][] times = {{{2, 1, 1}, {2, 3, 1}, {3, 4, 1}}, {{1, 2, 1}}, {{1, 2, 1}}, {{1, 2, 1}, {2, 3, 2}, {1, 3, 4}}};
        int[] ns = {4, 2, 2, 3};
        int[] ks = {2, 1, 2, 1};
        for (int i = 0; i < ns.length; i++) {
            System.out.println("Input  : times = " + Arrays.deepToString(times[i]) + ", n = " + ns[i] + ", k = " + ks[i]);
            System.out.println("Output : " + networkDelayTime(times[i], ns[i], ks[i]));
            System.out.println();
        }
    }

    public static int networkDelayTime(int[][] times, int n, int k) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : times) {
            graph.get(edge[0]).add(new int[]{edge[1], edge[2]});
        }

        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;
        PriorityQueue<int[]> heap = new PriorityQueue<>(Comparator.comparingInt((int[] a) -> a[1]));       // {node, distance}
        heap.add(new int[]{k, 0});
        while (!heap.isEmpty()) {
            int[] current = heap.poll();
            int node = current[0];
            if (current[1] > dist[node]) {
                continue;                           // stale entry
            }
            for (int[] edge : graph.get(node)) {
                int newDistance = dist[node] + edge[1];
                if (newDistance < dist[edge[0]]) {
                    dist[edge[0]] = newDistance;
                    heap.add(new int[]{edge[0], newDistance});
                }
            }
        }

        int answer = 0;
        for (int node = 1; node <= n; node++) {
            if (dist[node] == Integer.MAX_VALUE) {
                return -1;
            }
            answer = Math.max(answer, dist[node]);
        }
        return answer;
    }
}
