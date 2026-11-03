package com.ranga.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Given a weighted directed graph with non-negative edge weights, find the shortest distance from a source vertex to every other vertex and reconstruct the shortest path to a chosen target.
 *
 * <p><b>Algorithm Strategy (Greedy Relaxation with a Min-Heap):</b>
 * <ol>
 *   <li>Keep {@code dist[v]}, the best known distance from the source, initialised to infinity except {@code dist[source] = 0}, and {@code parent[v]} to rebuild the path.</li>
 *   <li>Put {@code (0, source)} into a min-heap ordered by distance.</li>
 *   <li>Remove the closest unfinished vertex {@code u}. If the removed distance is larger than {@code dist[u]}, it is a stale entry: skip it.</li>
 *   <li>For every edge {@code u -> v} with weight {@code w}, if {@code dist[u] + w < dist[v]} update {@code dist[v]} and {@code parent[v]} and add {@code (dist[v], v)} to the heap. The path to a target is read by following {@code parent} back to the source.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For edges {@code 0->1 (4), 0->2 (1), 2->1 (2), 1->3 (1), 2->3 (5)} from vertex 0: the distances are {@code [0, 3, 1, 4]}.</li>
 *   <li>The shortest path to vertex 3 is {@code 0 -> 2 -> 1 -> 3} with cost 4 (the direct route {@code 0 -> 1 -> 3} costs 5).</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O((V + E) log V)} — Each edge may add one entry to the heap, and every heap operation costs O(log V).</li>
 *   <li><b>Space Complexity:</b> {@code O(V + E)} — Distance and parent arrays, adjacency list and the heap.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class DijkstraShortestPath {

    private static final int INFINITY = Integer.MAX_VALUE;

    public static void main(String[] args) {
        int vertices = 5;
        int[][] edges = {{0, 1, 4}, {0, 2, 1}, {2, 1, 2}, {1, 3, 1}, {2, 3, 5}};
        System.out.println("Input  : vertices = " + vertices + ", edges (from, to, weight) = " + Arrays.deepToString(edges) + ", source = 0");
        int[] parent = new int[vertices];
        int[] dist = shortestDistances(vertices, edges, 0, parent);
        System.out.println("Output : distances = " + describe(dist));
        System.out.println("         path to 3 = " + path(parent, 0, 3) + " (cost " + dist[3] + ")");
        System.out.println("         path to 4 = " + path(parent, 0, 4));
    }

    /** Returns the distances from {@code source}; {@code Integer.MAX_VALUE} marks unreachable vertices. */
    public static int[] shortestDistances(int n, int[][] edges, int source, int[] parent) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(new int[]{edge[1], edge[2]});
        }

        int[] dist = new int[n];
        Arrays.fill(dist, INFINITY);
        Arrays.fill(parent, -1);
        dist[source] = 0;

        // heap entries are {distance, vertex}
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        heap.add(new int[]{0, source});
        while (!heap.isEmpty()) {
            int[] current = heap.poll();
            int u = current[1];
            if (current[0] > dist[u]) {
                continue;
            }
            for (int[] edge : graph.get(u)) {
                int v = edge[0];
                int candidate = dist[u] + edge[1];
                if (candidate < dist[v]) {
                    dist[v] = candidate;
                    parent[v] = u;
                    heap.add(new int[]{candidate, v});
                }
            }
        }
        return dist;
    }

    /** Rebuilds the source-to-target path from the parent array, or an empty list if unreachable. */
    public static List<Integer> path(int[] parent, int source, int target) {
        LinkedList<Integer> path = new LinkedList<>();
        for (int v = target; v != -1; v = parent[v]) {
            path.addFirst(v);
        }
        return path.getFirst() == source ? path : new LinkedList<>();
    }

    private static String describe(int[] dist) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < dist.length; i++) {
            sb.append(i > 0 ? ", " : "").append(dist[i] == INFINITY ? "unreachable" : String.valueOf(dist[i]));
        }
        return sb.append(']').toString();
    }
}
