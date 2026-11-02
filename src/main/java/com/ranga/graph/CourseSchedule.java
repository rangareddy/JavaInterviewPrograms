package com.ranga.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * There are {@code numCourses} courses labeled {@code 0} to {@code numCourses - 1}. A prerequisite {@code [a, b]} means course {@code b} must be taken before course {@code a}. Return {@code true} if all courses can be finished, i.e. the prerequisite graph has no cycle.
 *
 * <p><b>Algorithm Strategy (Topological Sort (Kahn's Algorithm)):</b>
 * <ol>
 *   <li>Build a directed graph with an edge {@code b -> a} for every prerequisite {@code [a, b]} and compute the in-degree (number of prerequisites) of every course.</li>
 *   <li>Put all courses with in-degree 0 (no prerequisites) in a queue.</li>
 *   <li>Repeatedly remove a course from the queue, count it as taken and decrease the in-degree of the courses that depend on it; courses whose in-degree becomes 0 join the queue.</li>
 *   <li>If all {@code numCourses} courses were taken, there is no cycle and the answer is {@code true}; otherwise some courses wait for each other forever.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code numCourses = 2} and {@code [[1, 0]]}: course 0 has in-degree 0, taking it frees course 1, so both are taken: {@code true}.</li>
 *   <li>For {@code [[1, 0], [0, 1]]} no course has in-degree 0, nothing can be taken: {@code false}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(V + E)} — Every course and prerequisite is processed once.</li>
 *   <li><b>Space Complexity:</b> {@code O(V + E)} — The graph, the in-degrees and the queue.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
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
