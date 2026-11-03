package com.ranga.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Given a list of airline tickets {@code [from, to]}, reconstruct the itinerary in order. The itinerary must begin with {@code "JFK"}, every ticket must be used exactly once, and if several valid itineraries exist, the one with the smallest lexical order should be returned. A valid itinerary always exists.
 *
 * <p><b>Algorithm Strategy (Hierholzer's Algorithm on a Lexically Ordered Graph):</b>
 * <ol>
 *   <li>An itinerary that uses every ticket exactly once is an Eulerian path. Build the graph with each airport's destinations in a min-heap (smallest name first).</li>
 *   <li>Run a DFS from {@code JFK}: while the current airport has unused tickets, take the smallest destination (removing the ticket) and recurse.</li>
 *   <li>Add an airport to the route only when it has no unused tickets left (post-order), at the front of the route list. Dead ends therefore end up at the back of the itinerary.</li>
 *   <li>The route built this way is the lexically smallest valid itinerary.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[MUC,LHR],[JFK,MUC],[SFO,SJC],[LHR,SFO]]}: JFK {@code ->} MUC {@code ->} LHR {@code ->} SFO {@code ->} SJC is the only way to use all tickets.</li>
 *   <li>For {@code [[JFK,SFO],[JFK,ATL],[SFO,ATL],[ATL,JFK],[ATL,SFO]]} the result is {@code [JFK, ATL, JFK, SFO, ATL, SFO]}: going to ATL first is smaller, and the dead end SFO is only visited at the end.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(E log E)} — Each ticket is polled once from a heap.</li>
 *   <li><b>Space Complexity:</b> {@code O(E)} — The graph and the recursion stack.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ReconstructItinerary {

    public static void main(String[] args) {
        String[][][] inputs = {
                {{"MUC", "LHR"}, {"JFK", "MUC"}, {"SFO", "SJC"}, {"LHR", "SFO"}},
                {{"JFK", "SFO"}, {"JFK", "ATL"}, {"SFO", "ATL"}, {"ATL", "JFK"}, {"ATL", "SFO"}},
                {{"JFK", "KUL"}, {"JFK", "NRT"}, {"NRT", "JFK"}}
        };
        for (String[][] tickets : inputs) {
            List<List<String>> list = new ArrayList<>();
            for (String[] ticket : tickets) {
                list.add(Arrays.asList(ticket));
            }
            System.out.println("Input  : tickets = " + list);
            System.out.println("Output : " + findItinerary(list));
            System.out.println();
        }
    }

    public static List<String> findItinerary(List<List<String>> tickets) {
        Map<String, PriorityQueue<String>> graph = new HashMap<>();
        for (List<String> ticket : tickets) {
            graph.computeIfAbsent(ticket.get(0), k -> new PriorityQueue<>()).add(ticket.get(1));
        }

        LinkedList<String> route = new LinkedList<>();
        visit("JFK", graph, route);
        return route;
    }

    private static void visit(String airport, Map<String, PriorityQueue<String>> graph, LinkedList<String> route) {
        PriorityQueue<String> destinations = graph.get(airport);
        while (destinations != null && !destinations.isEmpty()) {
            visit(destinations.poll(), graph, route);       // use the smallest unused ticket
        }
        route.addFirst(airport);                            // post-order: dead ends go to the back
    }
}
