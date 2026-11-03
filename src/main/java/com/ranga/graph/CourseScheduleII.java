package com.ranga.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * There are {@code numCourses} courses labeled {@code 0} to {@code numCourses - 1}. A prerequisite {@code [a, b]} means course {@code b} must be taken before course {@code a}. Return an ordering of the courses that lets you finish all of them, or an empty array if that is impossible (the prerequisites contain a cycle).
 *
 * <p><b>Algorithm Strategy (Topological Sort (Kahn's Algorithm)):</b>
 * <ol>
 *   <li>Build the graph with an edge {@code b -> a} for each prerequisite {@code [a, b]} and count the in-degree of every course.</li>
 *   <li>Put all courses with in-degree 0 into a queue; they can be taken first.</li>
 *   <li>Remove a course from the queue, append it to the order and decrease the in-degree of the courses that depend on it; courses reaching in-degree 0 join the queue.</li>
 *   <li>If the order contains all {@code numCourses} courses it is a valid schedule; otherwise a cycle blocked the remaining courses, so return an empty array.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For 4 courses and {@code [[1,0],[2,0],[3,1],[3,2]]}: course 0 first, then 1 and 2 (their only prerequisite is done), and finally 3.</li>
 *   <li>Result: {@code [0, 1, 2, 3]} ({@code [0, 2, 1, 3]} is also valid).</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(V + E)} — Every course and prerequisite is processed once.</li>
 *   <li><b>Space Complexity:</b> {@code O(V + E)} — Graph, in-degrees and queue.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
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
