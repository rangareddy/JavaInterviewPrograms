package com.ranga.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Given a directed graph with {@code n} vertices (numbered from 0) and a list of directed edges {@code [from, to]}, return an ordering of the vertices in which every edge goes from an earlier vertex to a later one. If the graph contains a cycle no such ordering exists and an empty list is returned. When several orderings are possible, return the one with the smallest vertex number first.
 *
 * <p><b>Algorithm Strategy (Kahn's Algorithm with a Min-Heap):</b>
 * <ol>
 *   <li>Count the incoming edges (in-degree) of every vertex and build the adjacency list.</li>
 *   <li>Put every vertex with in-degree 0 into a min-heap: those vertices have no unfinished prerequisites.</li>
 *   <li>Repeatedly remove the smallest vertex from the heap, append it to the order and decrease the in-degree of its successors; any successor that reaches 0 joins the heap.</li>
 *   <li>If the order contains all {@code n} vertices it is valid. Otherwise the remaining vertices are part of (or behind) a cycle: return an empty list.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 6} and edges {@code 5->2, 5->0, 4->0, 4->1, 2->3, 3->1}: the in-degree-0 vertices are 4 and 5; taking the smallest first gives {@code [4, 5, 0, 2, 3, 1]}.</li>
 *   <li>For the edges {@code 0->1, 1->2, 2->0} every vertex has an incoming edge, so the order stays empty: a cycle.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O((V + E) log V)} — Every vertex goes through the heap once and every edge is relaxed once; with a plain queue it is O(V + E).</li>
 *   <li><b>Space Complexity:</b> {@code O(V + E)} — Adjacency list, in-degrees and the heap.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class TopologicalSort {

    public static void main(String[] args) {
        int[] sizes = {6, 3, 4, 1};
        int[][][] graphs = {
                {{5, 2}, {5, 0}, {4, 0}, {4, 1}, {2, 3}, {3, 1}},
                {{0, 1}, {1, 2}, {2, 0}},
                {{0, 1}, {0, 2}, {1, 3}, {2, 3}},
                {}
        };
        for (int i = 0; i < sizes.length; i++) {
            System.out.println("Input  : n = " + sizes[i] + ", edges = " + Arrays.deepToString(graphs[i]));
            List<Integer> order = topologicalSort(sizes[i], graphs[i]);
            System.out.println("Output : " + (order.isEmpty() ? "cycle detected (no ordering)" : order.toString()));
            System.out.println();
        }
    }

    public static List<Integer> topologicalSort(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        int[] inDegree = new int[n];
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            inDegree[edge[1]]++;
        }

        PriorityQueue<Integer> ready = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                ready.add(i);
            }
        }

        List<Integer> order = new ArrayList<>();
        while (!ready.isEmpty()) {
            int vertex = ready.poll();
            order.add(vertex);
            for (int next : graph.get(vertex)) {
                if (--inDegree[next] == 0) {
                    ready.add(next);
                }
            }
        }
        return order.size() == n ? order : new ArrayList<>();
    }
}
