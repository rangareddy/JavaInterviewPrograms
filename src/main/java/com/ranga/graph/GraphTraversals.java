package com.ranga.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

/**
 * Traverse an undirected graph given as a list of edges, starting from a source vertex, with breadth-first search (visit the vertices level by level) and depth-first search (go as deep as possible before backtracking). Also compute the number of edges on the shortest path from the source to every vertex.
 *
 * <p><b>Algorithm Strategy (Queue for BFS, Recursion or Stack for DFS):</b>
 * <ol>
 *   <li>Build an adjacency list from the edges so that the neighbours of a vertex can be listed quickly.</li>
 *   <li>BFS: put the source in a queue and mark it visited; repeatedly remove a vertex, record it and add all unvisited neighbours (marking them when they are added). In an unweighted graph the first time a vertex is reached is also the shortest path, so store {@code distance[v] = distance[u] + 1}.</li>
 *   <li>DFS (recursive): visit a vertex, mark it, then recurse into each unvisited neighbour. The iterative version replaces the recursion with a stack.</li>
 *   <li>Vertices that are not connected to the source are never reached: their distance stays {@code -1}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For 6 vertices and edges {@code 0-1, 0-2, 1-3, 2-3, 3-4} (vertex 5 is isolated), BFS from 0 visits {@code [0, 1, 2, 3, 4]} with distances {@code [0, 1, 1, 2, 3, -1]}.</li>
 *   <li>DFS from 0 visits {@code [0, 1, 3, 2, 4]}: it follows 0 -> 1 -> 3 -> 2 before coming back to 4.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(V + E)} — Every vertex and every edge is processed a constant number of times.</li>
 *   <li><b>Space Complexity:</b> {@code O(V + E)} — The adjacency list plus the queue / stack and the visited flags.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class GraphTraversals {

    public static void main(String[] args) {
        int vertices = 6;
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {2, 3}, {3, 4}};
        List<List<Integer>> graph = buildGraph(vertices, edges);

        System.out.println("Input  : vertices = " + vertices + ", edges = " + Arrays.deepToString(edges) + ", source = 0");
        System.out.println("Output : BFS order = " + bfs(graph, 0));
        System.out.println("         BFS distances = " + Arrays.toString(bfsDistances(graph, 0)));
        System.out.println("         DFS recursive = " + dfs(graph, 0));
        System.out.println("         DFS iterative = " + dfsIterative(graph, 0));
    }

    public static List<List<Integer>> buildGraph(int vertices, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        return graph;
    }

    public static List<Integer> bfs(List<List<Integer>> graph, int source) {
        List<Integer> order = new ArrayList<>();
        boolean[] visited = new boolean[graph.size()];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(source);
        visited[source] = true;
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            order.add(vertex);
            for (int next : graph.get(vertex)) {
                if (!visited[next]) {
                    visited[next] = true;
                    queue.add(next);
                }
            }
        }
        return order;
    }

    public static int[] bfsDistances(List<List<Integer>> graph, int source) {
        int[] distance = new int[graph.size()];
        Arrays.fill(distance, -1);
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(source);
        distance[source] = 0;
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            for (int next : graph.get(vertex)) {
                if (distance[next] == -1) {
                    distance[next] = distance[vertex] + 1;
                    queue.add(next);
                }
            }
        }
        return distance;
    }

    public static List<Integer> dfs(List<List<Integer>> graph, int source) {
        List<Integer> order = new ArrayList<>();
        dfs(graph, source, new boolean[graph.size()], order);
        return order;
    }

    public static List<Integer> dfsIterative(List<List<Integer>> graph, int source) {
        List<Integer> order = new ArrayList<>();
        boolean[] visited = new boolean[graph.size()];
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(source);
        while (!stack.isEmpty()) {
            int vertex = stack.pop();
            if (visited[vertex]) {
                continue;
            }
            visited[vertex] = true;
            order.add(vertex);
            List<Integer> neighbours = graph.get(vertex);
            for (int i = neighbours.size() - 1; i >= 0; i--) {      // reversed, so the first neighbour is visited first
                if (!visited[neighbours.get(i)]) {
                    stack.push(neighbours.get(i));
                }
            }
        }
        return order;
    }

    private static void dfs(List<List<Integer>> graph, int vertex, boolean[] visited, List<Integer> order) {
        visited[vertex] = true;
        order.add(vertex);
        for (int next : graph.get(vertex)) {
            if (!visited[next]) {
                dfs(graph, next, visited, order);
            }
        }
    }
}
