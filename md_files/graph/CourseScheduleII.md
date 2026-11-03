# Course Schedule II

There are `numCourses` courses labeled `0` to `numCourses - 1`. A prerequisite `[a, b]` means course `b` must be taken before course `a`. Return an ordering of the courses that lets you finish all of them, or an empty array if that is impossible (the prerequisites contain a cycle).

## Algorithm Strategy (Topological Sort (Kahn's Algorithm))

1. Build the graph with an edge `b -> a` for each prerequisite `[a, b]` and count the in-degree of every course.
2. Put all courses with in-degree 0 into a queue; they can be taken first.
3. Remove a course from the queue, append it to the order and decrease the in-degree of the courses that depend on it; courses reaching in-degree 0 join the queue.
4. If the order contains all `numCourses` courses it is a valid schedule; otherwise a cycle blocked the remaining courses, so return an empty array.

## Example walkthrough

- For 4 courses and `[[1,0],[2,0],[3,1],[3,2]]`: course 0 first, then 1 and 2 (their only prerequisite is done), and finally 3.
- Result: `[0, 1, 2, 3]` (`[0, 2, 1, 3]` is also valid).

## Complexity Analysis

- **Time Complexity:** `O(V + E)` — Every course and prerequisite is processed once.
- **Space Complexity:** `O(V + E)` — Graph, in-degrees and queue.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: DFS Post-order with Cycle Detection

Run a DFS with three colors (new, in progress, done). A course is appended to the order after all courses that depend on it; reversing that order gives a valid schedule. Meeting an in-progress course again means a cycle: return an empty array.

```java
public static int[] findOrderDfs(int numCourses, int[][] prerequisites) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());
    for (int[] p : prerequisites) graph.get(p[1]).add(p[0]);
    int[] state = new int[numCourses];
    List<Integer> post = new ArrayList<>();
    for (int course = 0; course < numCourses; course++) {
        if (!visit(graph, state, post, course)) return new int[0];
    }
    int[] order = new int[numCourses];
    for (int i = 0; i < numCourses; i++) order[i] = post.get(numCourses - 1 - i);
    return order;
}

private static boolean visit(List<List<Integer>> graph, int[] state, List<Integer> post, int course) {
    if (state[course] == 1) return false;
    if (state[course] == 2) return true;
    state[course] = 1;
    for (int next : graph.get(course)) if (!visit(graph, state, post, next)) return false;
    state[course] = 2;
    post.add(course);
    return true;
}
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V + E)

### Approach 2: Iterative DFS with an Explicit Stack

The same post-order DFS without recursion, so long prerequisite chains cannot overflow the call stack.

```java
public static int[] findOrderIterative(int numCourses, int[][] prerequisites) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());
    for (int[] p : prerequisites) graph.get(p[1]).add(p[0]);
    int[] state = new int[numCourses];
    int[] order = new int[numCourses];
    int filled = numCourses;
    for (int start = 0; start < numCourses; start++) {
        if (state[start] != 0) continue;
        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{start, 0});
        state[start] = 1;
        while (!stack.isEmpty()) {
            int[] top = stack.peek();
            List<Integer> next = graph.get(top[0]);
            if (top[1] < next.size()) {
                int child = next.get(top[1]++);
                if (state[child] == 1) return new int[0];
                if (state[child] == 0) { state[child] = 1; stack.push(new int[]{child, 0}); }
            } else {
                state[top[0]] = 2;
                order[--filled] = top[0];            // post-order, filled from the back
                stack.pop();
            }
        }
    }
    return order;
}
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V + E)

### Approach 3: Repeated Scan for Free Courses

Repeatedly scan all courses and take any course whose prerequisites have all been taken, until none is left. The naive form of Kahn's algorithm.

```java
public static int[] findOrderScan(int numCourses, int[][] prerequisites) {
    boolean[] taken = new boolean[numCourses];
    int[] order = new int[numCourses];
    int count = 0;
    boolean progress = true;
    while (progress) {
        progress = false;
        for (int course = 0; course < numCourses; course++) {
            if (taken[course]) continue;
            boolean ready = true;
            for (int[] p : prerequisites) if (p[0] == course && !taken[p[1]]) { ready = false; break; }
            if (ready) { taken[course] = true; order[count++] = course; progress = true; }
        }
    }
    return count == numCourses ? order : new int[0];
}
```

- **Time Complexity:** O(V * (V + E))
- **Space Complexity:** O(V)

### Approach 4: Kahn's Algorithm (Optimized)

Repeatedly take a course with in-degree zero and lower the in-degree of its dependants. This is the version implemented in the repository.

```java
public static int[] findOrder(int numCourses, int[][] prerequisites) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());
    int[] inDegree = new int[numCourses];
    for (int[] p : prerequisites) { graph.get(p[1]).add(p[0]); inDegree[p[0]]++; }
    Queue<Integer> queue = new LinkedList<>();
    for (int course = 0; course < numCourses; course++) if (inDegree[course] == 0) queue.add(course);
    int[] order = new int[numCourses];
    int taken = 0;
    while (!queue.isEmpty()) {
        int course = queue.poll();
        order[taken++] = course;
        for (int next : graph.get(course)) if (--inDegree[next] == 0) queue.add(next);
    }
    return taken == numCourses ? order : new int[0];
}
```

- **Time Complexity:** O(V + E)
- **Space Complexity:** O(V + E)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | DFS Post-order with Cycle Detection | O(V + E) | O(V + E) |
| 2 | Iterative DFS with an Explicit Stack | O(V + E) | O(V + E) |
| 3 | Repeated Scan for Free Courses | O(V * (V + E)) | O(V) |
| 4 | Kahn's Algorithm (Optimized) | O(V + E) | O(V + E) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### CourseScheduleII.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CourseScheduleII {

    public static void main(String[] args) {
        int[] courses = {2, 4, 1, 2};
        int[][][] prerequisites = {{{1, 0}}, {{1, 0}, {2, 0}, {3, 1}, {3, 2}}, {}, {{1, 0}, {0, 1}}};
        for (int i = 0; i < courses.length; i++) {
            System.out.println("Input  : numCourses = " + courses[i] + ", prerequisites = " + Arrays.deepToString(prerequisites[i]));
            System.out.println("Output : " + Arrays.toString(findOrder(courses[i], prerequisites[i])));
            System.out.println();
        }
    }

    public static int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        int[] inDegree = new int[numCourses];
        for (int[] prerequisite : prerequisites) {
            graph.get(prerequisite[1]).add(prerequisite[0]);
            inDegree[prerequisite[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int course = 0; course < numCourses; course++) {
            if (inDegree[course] == 0) {
                queue.add(course);
            }
        }

        int[] order = new int[numCourses];
        int taken = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            order[taken++] = course;
            for (int next : graph.get(course)) {
                if (--inDegree[next] == 0) {
                    queue.add(next);
                }
            }
        }
        return taken == numCourses ? order : new int[0];
    }
}
```
