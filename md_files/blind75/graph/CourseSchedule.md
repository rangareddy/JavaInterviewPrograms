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
