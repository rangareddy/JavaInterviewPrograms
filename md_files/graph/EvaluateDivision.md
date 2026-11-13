# Evaluate Division

You are given equations `A / B = k` as variable pairs with values, and queries `C / D`. Return the answer to each query as a `double`, or `-1.0` if it cannot be determined (an unknown variable or two variables that are not connected by the equations).

## Algorithm Strategy (Weighted Graph and DFS)

1. Model each variable as a node. The equation `A / B = k` gives an edge `A -> B` with weight `k` and an edge `B -> A` with weight `1 / k`.
2. A query `C / D` is the product of the edge weights along any path from `C` to `D`, because `C / D = (C / X) * (X / Y) * ... * (Z / D)`.
3. For each query run a DFS from `C`, multiplying the weights, until `D` is reached. Track visited nodes to avoid cycles.
4. If either variable does not appear in the graph, or `D` is not reachable from `C`, the answer is `-1.0`. A variable divided by itself is `1.0` when it exists.

## Example walkthrough

- For `a / b = 2.0` and `b / c = 3.0`: the edges are `a -> b = 2`, `b -> a = 0.5`, `b -> c = 3`, `c -> b = 1/3`.
- Query `a / c`: `2 * 3 = 6.0`. Query `c / a`: `(1/3) * 0.5 = 0.16667`. Query `a / e`: `e` is unknown, so `-1.0`.

## Complexity Analysis

- **Time Complexity:** `O(Q * (V + E))` — One DFS per query over a graph with `V` variables and `E` equations.
- **Space Complexity:** `O(V + E)` — The graph and the recursion stack.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Weighted Union-Find

Keep for each variable its root and the ratio `variable / root`. Two variables are connected when they share a root, and their ratio is the quotient of the two stored ratios. Path compression keeps the ratios correct.

```java
public static double[] calcEquationUnionFind(List<List<String>> equations, double[] values, List<List<String>> queries) {
    Map<String, String> parent = new HashMap<>();
    Map<String, Double> ratio = new HashMap<>();       // ratio.get(x) = x / parent.get(x)
    for (int i = 0; i < equations.size(); i++) {
        String a = equations.get(i).get(0), b = equations.get(i).get(1);
        parent.putIfAbsent(a, a); ratio.putIfAbsent(a, 1.0);
        parent.putIfAbsent(b, b); ratio.putIfAbsent(b, 1.0);
        String ra = find(parent, ratio, a), rb = find(parent, ratio, b);
        if (!ra.equals(rb)) {
            parent.put(ra, rb);
            ratio.put(ra, values[i] * ratio.get(b) / ratio.get(a));
        }
    }
    double[] answers = new double[queries.size()];
    for (int i = 0; i < queries.size(); i++) {
        String a = queries.get(i).get(0), b = queries.get(i).get(1);
        if (!parent.containsKey(a) || !parent.containsKey(b)) { answers[i] = -1.0; continue; }
        String ra = find(parent, ratio, a), rb = find(parent, ratio, b);
        answers[i] = ra.equals(rb) ? ratio.get(a) / ratio.get(b) : -1.0;
    }
    return answers;
}

private static String find(Map<String, String> parent, Map<String, Double> ratio, String x) {
    String p = parent.get(x);
    if (p.equals(x)) return x;
    String root = find(parent, ratio, p);
    ratio.put(x, ratio.get(x) * ratio.get(p));
    parent.put(x, root);
    return root;
}
```

- **Time Complexity:** O((E + Q) * alpha(V))
- **Space Complexity:** O(V)

### Approach 2: Weighted Graph with DFS (Optimized)

Store `A / B` and `B / A` as edges and multiply the weights along a DFS path for each query. This is the version implemented in the repository.

```java
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
```

- **Time Complexity:** O(Q * (V + E))
- **Space Complexity:** O(V + E)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Weighted Union-Find | O((E + Q) * alpha(V)) | O(V) |
| 2 | Weighted Graph with DFS (Optimized) | O(Q * (V + E)) | O(V + E) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### EvaluateDivision.java

```java
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
```
