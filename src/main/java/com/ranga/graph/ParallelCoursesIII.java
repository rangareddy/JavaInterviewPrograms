package com.ranga.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

/**
 * There are {@code n} courses numbered {@code 1} to {@code n}. {@code relations[i] = [prev, next]} means course {@code prev} must be completed before course {@code next}. {@code time[i]} is the number of months course {@code i + 1} takes. You can take any number of courses at the same time as long as their prerequisites are done. Return the minimum number of months to finish all courses.
 *
 * <p><b>Algorithm Strategy (Topological Sort with Earliest Finish Times):</b>
 * <ol>
 *   <li>Build the graph and the in-degree of each course.</li>
 *   <li>Start with courses that have no prerequisites: they finish at {@code time[i]}.</li>
 *   <li>Process courses in topological order. For each edge {@code u -> v}, {@code finish[v] = max(finish[v], finish[u] + time[v])}.</li>
 *   <li>The answer is the maximum finish time over all courses (the longest path in the DAG).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code n = 3}, {@code relations = [[1,3],[2,3]]} and {@code time = [3,2,5]}: course 3 starts after both 1 (month 3) and 2 (month 2) finish, so it finishes at month 8.</li>
 *   <li>Result: {@code 8}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n + e)} — Each course and relation is processed once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n + e)} — The graph and the finish times.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
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
