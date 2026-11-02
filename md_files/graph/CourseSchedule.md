# Course Schedule

There are `numCourses` courses labeled `0` to `numCourses - 1`. A prerequisite `[a, b]` means course `b` must be taken before course `a`. Return `true` if all courses can be finished, i.e. the prerequisite graph has no cycle.

## Algorithm Strategy (Topological Sort (Kahn's Algorithm))

1. Build a directed graph with an edge `b -> a` for every prerequisite `[a, b]` and compute the in-degree (number of prerequisites) of every course.
2. Put all courses with in-degree 0 (no prerequisites) in a queue.
3. Repeatedly remove a course from the queue, count it as taken and decrease the in-degree of the courses that depend on it; courses whose in-degree becomes 0 join the queue.
4. If all `numCourses` courses were taken, there is no cycle and the answer is `true`; otherwise some courses wait for each other forever.

## Example walkthrough

- For `numCourses = 2` and `[[1, 0]]`: course 0 has in-degree 0, taking it frees course 1, so both are taken: `true`.
- For `[[1, 0], [0, 1]]` no course has in-degree 0, nothing can be taken: `false`.

## Complexity Analysis

- **Time Complexity:** `O(V + E)` — Every course and prerequisite is processed once.
- **Space Complexity:** `O(V + E)` — The graph, the in-degrees and the queue.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Repeated Scan (Naive Topological Removal)

Keep taking any course whose prerequisites have all been taken, scanning the whole prerequisite list for each course, until no more courses can be taken. Same idea as Kahn's algorithm without the in-degree bookkeeping, so each round costs a full scan.

```java
public static boolean canFinishScan(int numCourses, int[][] prerequisites) {
    boolean[] taken = new boolean[numCourses];
    int count = 0;
    boolean progress = true;
    while (progress) {
        progress = false;
        for (int course = 0; course < numCourses; course++) {
            if (taken[course]) continue;
            boolean ready = true;
            for (int[] p : prerequisites) {
                if (p[0] == course && !taken[p[1]]) { ready = false; break; }
            }
            if (ready) { taken[course] = true; count++; progress = true; }
        }
    }
    return count == numCourses;
}
```

- **Time Complexity:** O(V * (V + E))
- **Space Complexity:** O(V)

### Approach 2: DFS with Three Colors (Cycle Detection)

Mark each course as unvisited (0), in progress (1) or done (2). Reaching an in-progress course again means a cycle (the courses wait for each other).

```java
public static boolean canFinishDfs(int numCourses, int[][] prerequisites) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());
    for (int[] p : prerequisites) graph.get(p[1]).add(p[0]);
    int[] state = new int[numCourses];
    for (int course = 0; course < numCourses; course++) {
        if (hasCycle(graph, state, course)) return false;
    }
    return true;
}

private static boolean hasCycle(List<List<Integer>> graph, int[] state, int course) {
    if (state[course] == 1) return true;
    if (state[course] == 2) return false;
    state[course] = 1;
    for (int next : graph.get(course)) {
        if (hasCycle(graph, state, next)) return true;
    }
    state[course] = 2;
    return false;
}
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V + E)

### Approach 3: Iterative DFS with Colors

The three-color cycle detection with an explicit stack of `(course, next neighbor index)` pairs instead of recursion, which avoids stack overflow on long prerequisite chains.

```java
public static boolean canFinishIterativeDfs(int numCourses, int[][] prerequisites) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());
    for (int[] p : prerequisites) graph.get(p[1]).add(p[0]);

    int[] state = new int[numCourses];                  // 0 = new, 1 = on the current path, 2 = done
    for (int start = 0; start < numCourses; start++) {
        if (state[start] != 0) continue;
        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{start, 0});
        state[start] = 1;
        while (!stack.isEmpty()) {
            int[] top = stack.peek();
            List<Integer> neighbors = graph.get(top[0]);
            if (top[1] < neighbors.size()) {
                int next = neighbors.get(top[1]++);
                if (state[next] == 1) return false;     // back edge: cycle
                if (state[next] == 0) { state[next] = 1; stack.push(new int[]{next, 0}); }
            } else {
                state[top[0]] = 2;
                stack.pop();
            }
        }
    }
    return true;
}
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V + E)

### Approach 4: Transitive Closure (Floyd-Warshall)

Compute which courses are reachable from which with the Floyd-Warshall closure over a boolean matrix; a course that can reach itself lies on a cycle. Cubic, so only sensible for a small number of courses, but it also answers "is course a a (transitive) prerequisite of course b" for free.

```java
public static boolean canFinishClosure(int numCourses, int[][] prerequisites) {
    boolean[][] reach = new boolean[numCourses][numCourses];
    for (int[] p : prerequisites) reach[p[1]][p[0]] = true;
    for (int k = 0; k < numCourses; k++) {
        for (int i = 0; i < numCourses; i++) {
            if (!reach[i][k]) continue;
            for (int j = 0; j < numCourses; j++) {
                if (reach[k][j]) reach[i][j] = true;
            }
        }
    }
    for (int i = 0; i < numCourses; i++) {
        if (reach[i][i]) return false;
    }
    return true;
}
```

- **Time Complexity:** O(V^3)
- **Space Complexity:** O(V^2)

### Approach 5: Kahn's Topological Sort (Optimized)

Repeatedly take a course with no remaining prerequisites; if all courses can be taken there is no cycle. Iterative, so no recursion depth issue. This is the version implemented in the repository.

```java
public static boolean canFinish(int numCourses, int[][] prerequisites) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());
    int[] inDegree = new int[numCourses];
    for (int[] p : prerequisites) { graph.get(p[1]).add(p[0]); inDegree[p[0]]++; }

    Deque<Integer> queue = new ArrayDeque<>();
    for (int i = 0; i < numCourses; i++) if (inDegree[i] == 0) queue.add(i);
    int taken = 0;
    while (!queue.isEmpty()) {
        int course = queue.poll();
        taken++;
        for (int next : graph.get(course)) if (--inDegree[next] == 0) queue.add(next);
    }
    return taken == numCourses;
}
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V + E)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Repeated Scan (Naive Topological Removal) | O(V * (V + E)) | O(V) |
| 2 | DFS with Three Colors (Cycle Detection) | O(V + E) | O(V + E) |
| 3 | Iterative DFS with Colors | O(V + E) | O(V + E) |
| 4 | Transitive Closure (Floyd-Warshall) | O(V^3) | O(V^2) |
| 5 | Kahn's Topological Sort (Optimized) | O(V + E) | O(V + E) |

### Optimized Solution

The optimized solution is Approach 5 above, which is also the version implemented in this repository.

## Source Code

### CourseSchedule.java

```java
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class CourseSchedule {

    public static void main(String[] args) {
        int[] courses = {2, 2, 4};
        int[][][] prerequisites = {{{1, 0}}, {{1, 0}, {0, 1}}, {{1, 0}, {2, 0}, {3, 1}, {3, 2}}};
        for (int i = 0; i < courses.length; i++) {
            System.out.println("Input  : numCourses = " + courses[i] + ", prerequisites = " + Arrays.deepToString(prerequisites[i]));
            System.out.println("Output : " + canFinish(courses[i], prerequisites[i]));
            System.out.println();
        }
    }

    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        int[] inDegree = new int[numCourses];
        for (int[] prerequisite : prerequisites) {
            graph.get(prerequisite[1]).add(prerequisite[0]);
            inDegree[prerequisite[0]]++;
        }

        Deque<Integer> queue = new ArrayDeque<>();
        for (int course = 0; course < numCourses; course++) {
            if (inDegree[course] == 0) {
                queue.add(course);
            }
        }

        int taken = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            taken++;
            for (int next : graph.get(course)) {
                if (--inDegree[next] == 0) {
                    queue.add(next);
                }
            }
        }
        return taken == numCourses;
    }
}
```
