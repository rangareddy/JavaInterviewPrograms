package com.ranga.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * There are {@code n} servers numbered {@code 0..n-1} connected by undirected links {@code connections[i] = [a, b]}, and every server can reach every other one. A connection is critical if removing it makes some servers unable to reach others. Return all critical connections (the bridges of the graph) in any order.
 *
 * <p><b>Algorithm Strategy (Tarjan's Bridge-Finding Algorithm (Discovery and Low-Link Times)):</b>
 * <ol>
 *   <li>Run a DFS and give each vertex a discovery time {@code disc[v]}. Also compute {@code low[v]}, the smallest discovery time reachable from the subtree of {@code v} using tree edges plus at most one back edge.</li>
 *   <li>For a tree edge {@code v -> w}, after the recursive call set {@code low[v] = min(low[v], low[w])}. For an edge to an already-visited vertex (a back edge, not the edge back to the parent) set {@code low[v] = min(low[v], disc[w])}.</li>
 *   <li>The edge {@code v -> w} is a bridge exactly when {@code low[w] > disc[v]}: the subtree of {@code w} has no way back to {@code v} or above except through this edge.</li>
 *   <li>Skip the edge to the parent once so the tree edge itself is not mistaken for a back edge. Parallel edges are not part of this problem.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 4} and connections {@code [[0,1],[1,2],[2,0],[1,3]]}: the cycle {@code 0-1-2} gives low values of 0 for vertices 0, 1, 2.</li>
 *   <li>Vertex 3 hangs from 1 with {@code low[3] = 3 > disc[1] = 1}, so {@code [1, 3]} is the only bridge.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(V + E)} — A single DFS.</li>
 *   <li><b>Space Complexity:</b> {@code O(V + E)} — The adjacency list plus the recursion stack.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class CriticalConnections {

    public static void main(String[] args) {
        List<List<Integer>> connections = Arrays.asList(
                Arrays.asList(0, 1), Arrays.asList(1, 2), Arrays.asList(2, 0), Arrays.asList(1, 3));
        System.out.println("Input  : n = 4, connections = " + connections);
        System.out.println("Output : " + criticalConnections(4, connections));
        System.out.println();

        List<List<Integer>> single = Arrays.asList(Arrays.asList(0, 1));
        System.out.println("Input  : n = 2, connections = " + single);
        System.out.println("Output : " + criticalConnections(2, single));
    }

    public static List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (List<Integer> edge : connections) {
            graph.get(edge.get(0)).add(edge.get(1));
            graph.get(edge.get(1)).add(edge.get(0));
        }
        int[] disc = new int[n];
        int[] low = new int[n];
        Arrays.fill(disc, -1);
        List<List<Integer>> bridges = new ArrayList<>();
        dfs(0, -1, graph, disc, low, new int[]{0}, bridges);
        return bridges;
    }

    private static void dfs(int v, int parent, List<List<Integer>> graph, int[] disc, int[] low,
                            int[] timer, List<List<Integer>> bridges) {
        disc[v] = low[v] = timer[0]++;
        for (int w : graph.get(v)) {
            if (w == parent) {
                continue;
            }
            if (disc[w] == -1) {
                dfs(w, v, graph, disc, low, timer, bridges);
                low[v] = Math.min(low[v], low[w]);
                if (low[w] > disc[v]) {
                    bridges.add(Arrays.asList(v, w));
                }
            } else {
                low[v] = Math.min(low[v], disc[w]);
            }
        }
    }
}
