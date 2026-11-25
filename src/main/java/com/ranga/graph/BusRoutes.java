package com.ranga.graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;

/**
 * Given {@code routes}, where {@code routes[i]} is the list of stops that bus {@code i} cycles through forever, you start at stop {@code source} and want to reach {@code target}, travelling only by bus. Return the least number of buses you must take, or {@code -1} if it is impossible.
 *
 * <p><b>Algorithm Strategy (BFS over Buses):</b>
 * <ol>
 *   <li>Build a map from each stop to the buses that serve it.</li>
 *   <li>BFS where a node is a bus: start with all buses at {@code source} (count 1) and track visited buses and visited stops.</li>
 *   <li>When processing a bus, look at all its stops; if one is {@code target}, return the number of buses used.</li>
 *   <li>Otherwise enqueue every unvisited bus serving those stops with count + 1.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code routes = [[1,2,7],[3,6,7]]}, {@code source = 1}, {@code target = 6}: take bus 0 to stop 7, then bus 1 to stop 6.</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(total stops)} — Each stop and each bus is processed once.</li>
 *   <li><b>Space Complexity:</b> {@code O(total stops)} — The stop-to-buses map.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BusRoutes {

    public static void main(String[] args) {
        int[][][] routes = {{{1, 2, 7}, {3, 6, 7}}, {{7, 12}, {4, 5, 15}, {6}, {15, 19}, {9, 12, 13}}, {{1, 2}}, {{1, 2, 7}, {3, 6, 7}}};
        int[] sources = {1, 15, 1, 6};
        int[] targets = {6, 12, 2, 6};
        for (int i = 0; i < routes.length; i++) {
            System.out.println("Input  : routes = " + Arrays.deepToString(routes[i]) + ", source = " + sources[i] + ", target = " + targets[i]);
            System.out.println("Output : " + numBusesToDestination(routes[i], sources[i], targets[i]));
            System.out.println();
        }
    }

    public static int numBusesToDestination(int[][] routes, int source, int target) {
        if (source == target) {
            return 0;
        }
        if (routes == null) {
            return -1;
        }
        Map<Integer, List<Integer>> busesAt = new HashMap<>();
        for (int bus = 0; bus < routes.length; bus++) {
            for (int stop : routes[bus]) {
                busesAt.computeIfAbsent(stop, k -> new ArrayList<>()).add(bus);
            }
        }
        Queue<Integer> queue = new ArrayDeque<>();
        Set<Integer> usedBuses = new HashSet<>();
        Set<Integer> seenStops = new HashSet<>();
        seenStops.add(source);
        for (int bus : busesAt.getOrDefault(source, new ArrayList<>())) {
            queue.add(bus);
            usedBuses.add(bus);
        }
        int taken = 1;
        while (!queue.isEmpty()) {
            for (int size = queue.size(); size > 0; size--) {
                int bus = queue.poll();
                for (int stop : routes[bus]) {
                    if (stop == target) {
                        return taken;
                    }
                    if (seenStops.add(stop)) {
                        for (int next : busesAt.getOrDefault(stop, new ArrayList<>())) {
                            if (usedBuses.add(next)) {
                                queue.add(next);
                            }
                        }
                    }
                }
            }
            taken++;
        }
        return -1;
    }
}
