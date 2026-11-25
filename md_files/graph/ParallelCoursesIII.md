# Parallel Courses III

There are `n` courses numbered `1` to `n`. `relations[i] = [prev, next]` means course `prev` must be completed before course `next`. `time[i]` is the number of months course `i + 1` takes. You can take any number of courses at the same time as long as their prerequisites are done. Return the minimum number of months to finish all courses.

## Algorithm Strategy (Topological Sort with Earliest Finish Times)

1. Build the graph and the in-degree of each course.
2. Start with courses that have no prerequisites: they finish at `time[i]`.
3. Process courses in topological order. For each edge `u -> v`, `finish[v] = max(finish[v], finish[u] + time[v])`.
4. The answer is the maximum finish time over all courses (the longest path in the DAG).

## Example walkthrough

- For `n = 3`, `relations = [[1,3],[2,3]]` and `time = [3,2,5]`: course 3 starts after both 1 (month 3) and 2 (month 2) finish, so it finishes at month 8.
- Result: `8`.

## Complexity Analysis

- **Time Complexity:** `O(n + e)` — Each course and relation is processed once.
- **Space Complexity:** `O(n + e)` — The graph and the finish times.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: DFS with Memoisation of the Longest Path

Compute `finish(v) = time[v] + max(finish(prereq))` recursively with a cache.

```java
public static int dfs(int n, int[][] rel, int[] time) {
    java.util.List<java.util.List<Integer>> prev = new java.util.ArrayList<>(); for (int i = 0; i <= n; i++) prev.add(new java.util.ArrayList<>()); for (int[] r : rel) prev.get(r[1]).add(r[0]);
    int[] memo = new int[n + 1]; int best = 0; for (int c = 1; c <= n; c++) best = Math.max(best, go(c, prev, time, memo)); return best;
}
private static int go(int c, java.util.List<java.util.List<Integer>> prev, int[] time, int[] memo) { if (memo[c] > 0) return memo[c]; int m = 0; for (int p : prev.get(c)) m = Math.max(m, go(p, prev, time, memo)); return memo[c] = m + time[c - 1]; }
```

- **Time Complexity:** O(n + e)
- **Space Complexity:** O(n + e)

### Approach 2: Topological Sort with Finish Times (Optimized)

Process courses when their prerequisites are done, keeping the maximum finish time. This is the version implemented in the repository.

```java
public static int topo(int n, int[][] rel, int[] time) {
    java.util.List<java.util.List<Integer>> nx = new java.util.ArrayList<>(); for (int i = 0; i <= n; i++) nx.add(new java.util.ArrayList<>()); int[] in = new int[n + 1]; for (int[] r : rel) { nx.get(r[0]).add(r[1]); in[r[1]]++; }
    int[] fin = new int[n + 1]; java.util.Queue<Integer> q = new java.util.ArrayDeque<>(); for (int c = 1; c <= n; c++) if (in[c] == 0) { fin[c] = time[c - 1]; q.add(c); } int ans = 0;
    while (!q.isEmpty()) { int c = q.poll(); ans = Math.max(ans, fin[c]); for (int a : nx.get(c)) { fin[a] = Math.max(fin[a], fin[c] + time[a - 1]); if (--in[a] == 0) q.add(a); } }
    return ans;
}
```

- **Time Complexity:** O(n + e)
- **Space Complexity:** O(n + e)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | DFS with Memoisation of the Longest Path | O(n + e) | O(n + e) |
| 2 | Topological Sort with Finish Times (Optimized) | O(n + e) | O(n + e) |

## Source Code

### ParallelCoursesIII.java

```java
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class ParallelCoursesIII {

    public static void main(String[] args) {
        int[] ns = {3, 5, 1};
        int[][][] relations = {{{1, 3}, {2, 3}}, {{1, 5}, {2, 5}, {3, 5}, {3, 4}, {4, 5}}, {}};
        int[][] times = {{3, 2, 5}, {1, 2, 3, 4, 5}, {7}};
        for (int i = 0; i < ns.length; i++) {
            System.out.println("Input  : n = " + ns[i] + ", relations = " + Arrays.deepToString(relations[i]) + ", time = " + Arrays.toString(times[i]));
            System.out.println("Output : " + minimumTime(ns[i], relations[i], times[i]));
            System.out.println();
        }
    }

    public static int minimumTime(int n, int[][] relations, int[] time) {
        List<List<Integer>> next = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            next.add(new ArrayList<>());
        }
        int[] indegree = new int[n + 1];
        for (int[] r : relations) {
            next.get(r[0]).add(r[1]);
            indegree[r[1]]++;
        }
        int[] finish = new int[n + 1];
        Queue<Integer> queue = new ArrayDeque<>();
        for (int course = 1; course <= n; course++) {
            if (indegree[course] == 0) {
                finish[course] = time[course - 1];
                queue.add(course);
            }
        }
        int answer = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            answer = Math.max(answer, finish[course]);
            for (int after : next.get(course)) {
                finish[after] = Math.max(finish[after], finish[course] + time[after - 1]);
                if (--indegree[after] == 0) {
                    queue.add(after);
                }
            }
        }
        return answer;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: with unlimited parallelism, a course starts as soon as its last prerequisite finishes, so each course's finish time is its own duration plus the latest finish among its prerequisites. The total time is the longest path in the prerequisite graph.

**Input:** `n = 3`, `relations = [[1, 3], [2, 3]]`, `time = [3, 2, 5]`.

| Course | Starts after | Finish month |
|--------|--------------|--------------|
| 1 | nothing | 3 |
| 2 | nothing | 2 |
| 3 | max(3, 2) = 3 | 3 + 5 = 8 |

**Output:** `8`.
