# Restore the Array From Adjacent Pairs

There is an integer array `nums` of `n` unique elements, which you forgot. You are given `adjacentPairs`, where each `[u, v]` says that `u` and `v` are adjacent in `nums` (in any order). Return the original array; if several answers exist, any of them is accepted.

## Algorithm Strategy (Find an End and Walk the Chain)

1. Build an adjacency map: each value maps to its (at most two) neighbours.
2. The two end elements of `nums` are the only values with exactly one neighbour. Pick one as the start.
3. Walk: from the current value move to the neighbour that is not the previous value.
4. Collect the `n` values in order.

## Example walkthrough

- For pairs `[2,1],[3,4],[3,2]`: neighbours are 1:{2}, 2:{1,3}, 3:{4,2}, 4:{3}. Start at 1.
- Walking gives `1, 2, 3, 4`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass to build the map and one to walk.
- **Space Complexity:** `O(n)` — The adjacency map.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Repeated Search for the Next Pair

Start from an end value and scan all pairs for the next one each time. Quadratic.

```java
public static int[] scan(int[][] p) {
    java.util.Map<Integer, Integer> deg = new java.util.HashMap<>(); for (int[] x : p) { deg.merge(x[0], 1, Integer::sum); deg.merge(x[1], 1, Integer::sum); } int start = 0; for (java.util.Map.Entry<Integer, Integer> e : deg.entrySet()) if (e.getValue() == 1) { start = e.getKey(); break; }
    int[] r = new int[p.length + 1]; r[0] = start; boolean[] used = new boolean[p.length];
    for (int i = 1; i < r.length; i++) for (int k = 0; k < p.length; k++) if (!used[k] && (p[k][0] == r[i - 1] || p[k][1] == r[i - 1])) { used[k] = true; r[i] = p[k][0] == r[i - 1] ? p[k][1] : p[k][0]; break; }
    return r;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 2: Adjacency Map Walk (Optimized)

Walk the chain through a neighbour map from an end value. This is the version implemented in the repository.

```java
public static int[] walk(int[][] p) {
    java.util.Map<Integer, java.util.List<Integer>> g = new java.util.HashMap<>(); for (int[] x : p) { g.computeIfAbsent(x[0], k -> new java.util.ArrayList<>()).add(x[1]); g.computeIfAbsent(x[1], k -> new java.util.ArrayList<>()).add(x[0]); }
    int start = 0; for (java.util.Map.Entry<Integer, java.util.List<Integer>> e : g.entrySet()) if (e.getValue().size() == 1) { start = e.getKey(); break; }
    int[] r = new int[p.length + 1]; r[0] = start; int prev = Integer.MIN_VALUE; for (int i = 1; i < r.length; i++) { java.util.List<Integer> nx = g.get(r[i - 1]); r[i] = nx.get(0) != prev ? nx.get(0) : nx.get(1); prev = r[i - 1]; } return r;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Repeated Search for the Next Pair | O(n^2) | O(n) |
| 2 | Adjacency Map Walk (Optimized) | O(n) | O(n) |

## Source Code

### RestoreTheArrayFromAdjacentPairs.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestoreTheArrayFromAdjacentPairs {

    public static void main(String[] args) {
        int[][][] inputs = {{{2, 1}, {3, 4}, {3, 2}}, {{4, -2}, {1, 4}, {-3, 1}}, {{100000, -100000}}};
        for (int[][] pairs : inputs) {
            int[] restored = restoreArray(pairs);
            if (restored.length > 1 && restored[0] > restored[restored.length - 1]) {
                for (int i = 0, j = restored.length - 1; i < j; i++, j--) {
                    int temp = restored[i];
                    restored[i] = restored[j];
                    restored[j] = temp;
                }
            }
            System.out.println("Input  : adjacentPairs = " + Arrays.deepToString(pairs));
            System.out.println("Output : " + Arrays.toString(restored));
            System.out.println();
        }
    }

    public static int[] restoreArray(int[][] adjacentPairs) {
        if (adjacentPairs == null || adjacentPairs.length == 0) {
            return new int[0];
        }
        Map<Integer, List<Integer>> neighbours = new HashMap<>();
        for (int[] pair : adjacentPairs) {
            neighbours.computeIfAbsent(pair[0], k -> new ArrayList<>()).add(pair[1]);
            neighbours.computeIfAbsent(pair[1], k -> new ArrayList<>()).add(pair[0]);
        }
        int start = 0;
        for (Map.Entry<Integer, List<Integer>> entry : neighbours.entrySet()) {
            if (entry.getValue().size() == 1) {
                start = entry.getKey();
                break;
            }
        }
        int[] result = new int[adjacentPairs.length + 1];
        result[0] = start;
        int previous = Integer.MIN_VALUE;
        for (int i = 1; i < result.length; i++) {
            List<Integer> next = neighbours.get(result[i - 1]);
            result[i] = next.get(0) != previous ? next.get(0) : next.get(1);
            previous = result[i - 1];
        }
        return result;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: in a line of unique values, every inner element has two neighbours and the two ends have one. So an end is easy to find, and from there each step is forced: take the neighbour that is not where you just came from. The result is the array or its reverse, and either is accepted.

**Input:** `adjacentPairs = [[2, 1], [3, 4], [3, 2]]`.

| Value | Neighbours |
|-------|------------|
| 1 | 2 |
| 2 | 1, 3 |
| 3 | 4, 2 |
| 4 | 3 |

Start at 1 (one neighbour), then 2, then 3, then 4.

**Output:** `[1, 2, 3, 4]` (`main` prints the smaller end first so that the output is stable).
