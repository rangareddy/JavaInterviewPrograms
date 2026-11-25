package com.ranga.graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * There is an undirected graph with {@code n} nodes given as an adjacency list {@code graph}. Return {@code true} if and only if it is bipartite: its nodes can be split into two sets so that every edge connects a node of one set to a node of the other.
 *
 * <p><b>Algorithm Strategy (BFS Two-Colouring):</b>
 * <ol>
 *   <li>Give every node a colour: 0 (uncoloured), 1 or 2.</li>
 *   <li>For each uncoloured node (the graph may be disconnected), colour it 1 and run a BFS.</li>
 *   <li>For every neighbour: if it is uncoloured, give it the opposite colour and enqueue it; if it has the same colour as the current node, an odd cycle exists, so return {@code false}.</li>
 *   <li>If every component is coloured without conflict, return {@code true}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[1,2,3],[0,2],[0,1,3],[0,2]]}: nodes 0, 1 and 2 form a triangle, an odd cycle.</li>
 *   <li>Result: {@code false}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(V + E)} — Every node and edge is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(V)} — The colour array and the queue.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class IsGraphBipartite {

    public static void main(String[] args) {
        int[][][] graphs = {
            {{1, 2, 3}, {0, 2}, {0, 1, 3}, {0, 2}},
            {{1, 3}, {0, 2}, {1, 3}, {0, 2}},
            {{}, {2}, {1}, {}},
            {{1}, {0}}
        };
        for (int[][] graph : graphs) {
            System.out.println("Input  : graph = " + Arrays.deepToString(graph));
            System.out.println("Output : " + isBipartite(graph));
            System.out.println();
        }
    }

    public static boolean isBipartite(int[][] graph) {
        if (graph == null) {
            return true;
        }
        int[] color = new int[graph.length];
        for (int start = 0; start < graph.length; start++) {
            if (color[start] != 0) {
                continue;
            }
            color[start] = 1;
            Queue<Integer> queue = new ArrayDeque<>();
            queue.add(start);
            while (!queue.isEmpty()) {
                int node = queue.poll();
                for (int next : graph[node]) {
                    if (color[next] == 0) {
                        color[next] = 3 - color[node];
                        queue.add(next);
                    } else if (color[next] == color[node]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
