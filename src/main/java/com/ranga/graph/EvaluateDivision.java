package com.ranga.graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * You are given equations {@code A / B = k} as variable pairs with values, and queries {@code C / D}. Return the answer to each query as a {@code double}, or {@code -1.0} if it cannot be determined (an unknown variable or two variables that are not connected by the equations).
 *
 * <p><b>Algorithm Strategy (Weighted Graph and DFS):</b>
 * <ol>
 *   <li>Model each variable as a node. The equation {@code A / B = k} gives an edge {@code A -> B} with weight {@code k} and an edge {@code B -> A} with weight {@code 1 / k}.</li>
 *   <li>A query {@code C / D} is the product of the edge weights along any path from {@code C} to {@code D}, because {@code C / D = (C / X) * (X / Y) * ... * (Z / D)}.</li>
 *   <li>For each query run a DFS from {@code C}, multiplying the weights, until {@code D} is reached. Track visited nodes to avoid cycles.</li>
 *   <li>If either variable does not appear in the graph, or {@code D} is not reachable from {@code C}, the answer is {@code -1.0}. A variable divided by itself is {@code 1.0} when it exists.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code a / b = 2.0} and {@code b / c = 3.0}: the edges are {@code a -> b = 2}, {@code b -> a = 0.5}, {@code b -> c = 3}, {@code c -> b = 1/3}.</li>
 *   <li>Query {@code a / c}: {@code 2 * 3 = 6.0}. Query {@code c / a}: {@code (1/3) * 0.5 = 0.16667}. Query {@code a / e}: {@code e} is unknown, so {@code -1.0}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(Q * (V + E))} — One DFS per query over a graph with {@code V} variables and {@code E} equations.</li>
 *   <li><b>Space Complexity:</b> {@code O(V + E)} — The graph and the recursion stack.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class EvaluateDivision {

    public static void main(String[] args) {
        List<List<String>> equations = Arrays.asList(Arrays.asList("a", "b"), Arrays.asList("b", "c"));
        double[] values = {2.0, 3.0};
        List<List<String>> queries = Arrays.asList(Arrays.asList("a", "c"), Arrays.asList("b", "a"),
                Arrays.asList("a", "e"), Arrays.asList("a", "a"), Arrays.asList("x", "x"));
        System.out.println("Input  : equations = " + equations + ", values = " + Arrays.toString(values));
        System.out.println("         queries = " + queries);
        System.out.println("Output : " + Arrays.toString(calcEquation(equations, values, queries)));
    }

    public static double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, Map<String, Double>> graph = new HashMap<>();
        for (int i = 0; i < equations.size(); i++) {
            String a = equations.get(i).get(0);
            String b = equations.get(i).get(1);
            graph.computeIfAbsent(a, k -> new HashMap<>()).put(b, values[i]);
            graph.computeIfAbsent(b, k -> new HashMap<>()).put(a, 1.0 / values[i]);
        }
        double[] answers = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            String from = queries.get(i).get(0);
            String to = queries.get(i).get(1);
            if (!graph.containsKey(from) || !graph.containsKey(to)) {
                answers[i] = -1.0;
            } else {
                answers[i] = dfs(graph, from, to, new HashSet<>());
            }
        }
        return answers;
    }

    private static double dfs(Map<String, Map<String, Double>> graph, String from, String to, Set<String> visited) {
        if (from.equals(to)) {
            return 1.0;
        }
        visited.add(from);
        for (Map.Entry<String, Double> edge : graph.get(from).entrySet()) {
            if (!visited.contains(edge.getKey())) {
                double rest = dfs(graph, edge.getKey(), to, visited);
                if (rest != -1.0) {
                    return edge.getValue() * rest;
                }
            }
        }
        return -1.0;
    }
}
