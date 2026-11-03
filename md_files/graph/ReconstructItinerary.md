# Reconstruct Itinerary

Given a list of airline tickets `[from, to]`, reconstruct the itinerary in order. The itinerary must begin with `"JFK"`, every ticket must be used exactly once, and if several valid itineraries exist, the one with the smallest lexical order should be returned. A valid itinerary always exists.

## Algorithm Strategy (Hierholzer's Algorithm on a Lexically Ordered Graph)

1. An itinerary that uses every ticket exactly once is an Eulerian path. Build the graph with each airport's destinations in a min-heap (smallest name first).
2. Run a DFS from `JFK`: while the current airport has unused tickets, take the smallest destination (removing the ticket) and recurse.
3. Add an airport to the route only when it has no unused tickets left (post-order), at the front of the route list. Dead ends therefore end up at the back of the itinerary.
4. The route built this way is the lexically smallest valid itinerary.

## Example walkthrough

- For `[[MUC,LHR],[JFK,MUC],[SFO,SJC],[LHR,SFO]]`: JFK `->` MUC `->` LHR `->` SFO `->` SJC is the only way to use all tickets.
- For `[[JFK,SFO],[JFK,ATL],[SFO,ATL],[ATL,JFK],[ATL,SFO]]` the result is `[JFK, ATL, JFK, SFO, ATL, SFO]`: going to ATL first is smaller, and the dead end SFO is only visited at the end.

## Complexity Analysis

- **Time Complexity:** `O(E log E)` — Each ticket is polled once from a heap.
- **Space Complexity:** `O(E)` — The graph and the recursion stack.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Backtracking over Tickets in Lexical Order

Try the tickets from the current airport in alphabetical order, mark one as used and recurse; if the route gets stuck before all tickets are used, undo the choice and try the next ticket. The first complete route found is the lexically smallest. Can be exponential on adversarial input.

```java
public static List<String> findItineraryBacktracking(List<List<String>> tickets) {
    List<List<String>> sorted = new ArrayList<>(tickets);
    sorted.sort((a, b) -> a.get(0).equals(b.get(0)) ? a.get(1).compareTo(b.get(1)) : a.get(0).compareTo(b.get(0)));
    boolean[] used = new boolean[sorted.size()];
    List<String> route = new ArrayList<>();
    route.add("JFK");
    return search(sorted, used, route, sorted.size()) ? route : new ArrayList<>();
}

private static boolean search(List<List<String>> tickets, boolean[] used, List<String> route, int remaining) {
    if (remaining == 0) return true;
    String from = route.get(route.size() - 1);
    for (int i = 0; i < tickets.size(); i++) {
        if (used[i] || !tickets.get(i).get(0).equals(from)) continue;
        used[i] = true;
        route.add(tickets.get(i).get(1));
        if (search(tickets, used, route, remaining - 1)) return true;
        route.remove(route.size() - 1);
        used[i] = false;
    }
    return false;
}
```

- **Time Complexity:** O(E^d) worst case
- **Space Complexity:** O(E)

### Approach 2: Hierholzer with Sorted Lists and Pointers

The same Eulerian-path algorithm, but the destinations of every airport are kept in a sorted list with an index pointer (the next unused ticket) instead of a heap.

```java
public static List<String> findItineraryPointers(List<List<String>> tickets) {
    Map<String, List<String>> graph = new HashMap<>();
    for (List<String> ticket : tickets) graph.computeIfAbsent(ticket.get(0), k -> new ArrayList<>()).add(ticket.get(1));
    for (List<String> destinations : graph.values()) Collections.sort(destinations);
    Map<String, Integer> next = new HashMap<>();
    LinkedList<String> route = new LinkedList<>();
    walk("JFK", graph, next, route);
    return route;
}

private static void walk(String airport, Map<String, List<String>> graph, Map<String, Integer> next, LinkedList<String> route) {
    List<String> destinations = graph.getOrDefault(airport, Collections.emptyList());
    while (next.getOrDefault(airport, 0) < destinations.size()) {
        int index = next.getOrDefault(airport, 0);
        next.put(airport, index + 1);
        walk(destinations.get(index), graph, next, route);
    }
    route.addFirst(airport);
}
```

- **Time Complexity:** O(E log E)
- **Space Complexity:** O(E)

### Approach 3: Iterative Hierholzer with an Explicit Stack

Keep the current path on a stack; when the airport on top has no unused ticket it is final, so pop it into the route. The route is reversed at the end. No recursion.

```java
public static List<String> findItineraryIterative(List<List<String>> tickets) {
    Map<String, PriorityQueue<String>> graph = new HashMap<>();
    for (List<String> ticket : tickets) graph.computeIfAbsent(ticket.get(0), k -> new PriorityQueue<>()).add(ticket.get(1));
    Deque<String> stack = new ArrayDeque<>();
    LinkedList<String> route = new LinkedList<>();
    stack.push("JFK");
    while (!stack.isEmpty()) {
        PriorityQueue<String> destinations = graph.get(stack.peek());
        if (destinations != null && !destinations.isEmpty()) stack.push(destinations.poll());
        else route.addFirst(stack.pop());
    }
    return route;
}
```

- **Time Complexity:** O(E log E)
- **Space Complexity:** O(E)

### Approach 4: Hierholzer with a Priority Queue per Airport (Optimized)

Recursive Hierholzer where each airport's destinations are a min-heap; an airport is added to the front of the route when it has no tickets left. This is the version implemented in the repository.

```java
public static List<String> findItinerary(List<List<String>> tickets) {
    Map<String, PriorityQueue<String>> graph = new HashMap<>();
    for (List<String> ticket : tickets) graph.computeIfAbsent(ticket.get(0), k -> new PriorityQueue<>()).add(ticket.get(1));
    LinkedList<String> route = new LinkedList<>();
    visit("JFK", graph, route);
    return route;
}

private static void visit(String airport, Map<String, PriorityQueue<String>> graph, LinkedList<String> route) {
    PriorityQueue<String> destinations = graph.get(airport);
    while (destinations != null && !destinations.isEmpty()) visit(destinations.poll(), graph, route);
    route.addFirst(airport);
}
```

- **Time Complexity:** O(E log E)
- **Space Complexity:** O(E)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Backtracking over Tickets in Lexical Order | O(E^d) worst case | O(E) |
| 2 | Hierholzer with Sorted Lists and Pointers | O(E log E) | O(E) |
| 3 | Iterative Hierholzer with an Explicit Stack | O(E log E) | O(E) |
| 4 | Hierholzer with a Priority Queue per Airport (Optimized) | O(E log E) | O(E) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### ReconstructItinerary.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

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
```
