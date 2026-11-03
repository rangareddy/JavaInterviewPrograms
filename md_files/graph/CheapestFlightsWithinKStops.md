# Cheapest Flights Within K Stops

There are `n` cities connected by flights `[from, to, price]`. Given a source `src`, a destination `dst` and an integer `k`, return the cheapest price from `src` to `dst` with at most `k` stops (so at most `k + 1` flights), or -1 if there is no such route.

## Algorithm Strategy (Bellman-Ford Limited to k + 1 Rounds)

1. Keep `prices[v]`, the cheapest known price to reach city `v` (infinity at first, 0 for `src`).
2. A route with at most `k` stops uses at most `k + 1` flights, so run exactly `k + 1` rounds; round `r` finds the best prices that use at most `r` flights.
3. In each round work on a copy of the previous prices: for every flight `(u, v, w)` with a finite `prices[u]`, try `prices[u] + w` for `v`. Using the copy prevents a round from chaining two flights.
4. After the rounds, `prices[dst]` is the answer (or -1 if it is still infinite).

## Example walkthrough

- For 4 cities, flights `[[0,1,100],[1,2,100],[2,0,100],[1,3,600],[2,3,200]]`, from 0 to 3 with `k = 1`: the route 0 `->` 1 `->` 3 costs 700.
- The cheaper route 0-1-2-3 (cost 400) needs 2 stops, so it is not allowed: `700`.

## Complexity Analysis

- **Time Complexity:** `O(k * E)` — `k + 1` passes over all flights.
- **Space Complexity:** `O(n)` — Two price arrays.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: BFS by Number of Flights

Expand the routes level by level, one flight per level, for at most `k + 1` levels. Keep the best known price per city and only continue a route when it improves that price.

```java
public static int findCheapestPriceBfs(int n, int[][] flights, int src, int dst, int k) {
    List<List<int[]>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    for (int[] f : flights) graph.get(f[0]).add(new int[]{f[1], f[2]});
    int[] best = new int[n];
    Arrays.fill(best, Integer.MAX_VALUE);
    Queue<int[]> queue = new LinkedList<>();
    queue.add(new int[]{src, 0});
    best[src] = 0;
    for (int level = 0; level <= k && !queue.isEmpty(); level++) {
        for (int size = queue.size(); size > 0; size--) {
            int[] current = queue.poll();
            for (int[] edge : graph.get(current[0])) {
                int price = current[1] + edge[1];
                if (price < best[edge[0]]) { best[edge[0]] = price; queue.add(new int[]{edge[0], price}); }
            }
        }
    }
    return best[dst] == Integer.MAX_VALUE ? -1 : best[dst];
}
```

- **Time Complexity:** O(k * E)
- **Space Complexity:** O(V + E)

### Approach 2: Dijkstra over (city, flights used) States

Run Dijkstra on states `(city, number of flights used)`. A state is only expanded while it has used at most `k + 1` flights, and the first time the destination is popped its price is optimal.

```java
public static int findCheapestPriceDijkstra(int n, int[][] flights, int src, int dst, int k) {
    List<List<int[]>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    for (int[] f : flights) graph.get(f[0]).add(new int[]{f[1], f[2]});
    int[][] best = new int[n][k + 2];
    for (int[] row : best) Arrays.fill(row, Integer.MAX_VALUE);
    PriorityQueue<int[]> heap = new PriorityQueue<>(Comparator.comparingInt((int[] s) -> s[0]));      // {price, city, flights used}
    heap.add(new int[]{0, src, 0});
    while (!heap.isEmpty()) {
        int[] state = heap.poll();
        if (state[1] == dst) return state[0];
        if (state[2] > k || state[0] > best[state[1]][state[2]]) continue;
        for (int[] edge : graph.get(state[1])) {
            int price = state[0] + edge[1];
            if (price < best[edge[0]][state[2] + 1]) { best[edge[0]][state[2] + 1] = price; heap.add(new int[]{price, edge[0], state[2] + 1}); }
        }
    }
    return -1;
}
```

- **Time Complexity:** O(k * E log(k * V))
- **Space Complexity:** O(k * V)

### Approach 3: Memoized DFS on (city, remaining flights)

The cheapest price from a city with `r` flights left is the minimum over its flights of `price + cheapest(next city, r - 1)`. Cache the results per `(city, r)`.

```java
public static int findCheapestPriceMemo(int n, int[][] flights, int src, int dst, int k) {
    List<List<int[]>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    for (int[] f : flights) graph.get(f[0]).add(new int[]{f[1], f[2]});
    long[][] memo = new long[n][k + 2];
    for (long[] row : memo) Arrays.fill(row, -1);
    long result = cheapest(graph, src, dst, k + 1, memo);
    return result >= Integer.MAX_VALUE ? -1 : (int) result;
}

private static long cheapest(List<List<int[]>> graph, int city, int dst, int flightsLeft, long[][] memo) {
    if (city == dst) return 0;
    if (flightsLeft == 0) return Integer.MAX_VALUE;
    if (memo[city][flightsLeft] != -1) return memo[city][flightsLeft];
    long best = Integer.MAX_VALUE;
    for (int[] edge : graph.get(city)) best = Math.min(best, edge[1] + cheapest(graph, edge[0], dst, flightsLeft - 1, memo));
    return memo[city][flightsLeft] = best;
}
```

- **Time Complexity:** O(k * E)
- **Space Complexity:** O(k * V)

### Approach 4: Bellman-Ford Limited to k + 1 Rounds (Optimized)

Relax all flights `k + 1` times using a copy of the previous round's prices, so a round adds at most one flight to any route. This is the version implemented in the repository.

```java
public static int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
    int[] prices = new int[n];
    Arrays.fill(prices, Integer.MAX_VALUE);
    prices[src] = 0;
    for (int round = 0; round <= k; round++) {
        int[] next = prices.clone();
        for (int[] f : flights) {
            if (prices[f[0]] != Integer.MAX_VALUE && prices[f[0]] + f[2] < next[f[1]]) next[f[1]] = prices[f[0]] + f[2];
        }
        prices = next;
    }
    return prices[dst] == Integer.MAX_VALUE ? -1 : prices[dst];
}
```

- **Time Complexity:** O(k * E)
- **Space Complexity:** O(V)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | BFS by Number of Flights | O(k * E) | O(V + E) |
| 2 | Dijkstra over (city, flights used) States | O(k * E log(k * V)) | O(k * V) |
| 3 | Memoized DFS on (city, remaining flights) | O(k * E) | O(k * V) |
| 4 | Bellman-Ford Limited to k + 1 Rounds (Optimized) | O(k * E) | O(V) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### CheapestFlightsWithinKStops.java

```java
import java.util.Arrays;

public class CheapestFlightsWithinKStops {

    public static void main(String[] args) {
        int[] cities = {4, 3, 3};
        int[][][] flights = {
                {{0, 1, 100}, {1, 2, 100}, {2, 0, 100}, {1, 3, 600}, {2, 3, 200}},
                {{0, 1, 100}, {1, 2, 100}, {0, 2, 500}},
                {{0, 1, 100}, {1, 2, 100}, {0, 2, 500}}
        };
        int[] sources = {0, 0, 0};
        int[] destinations = {3, 2, 2};
        int[] stops = {1, 1, 0};
        for (int i = 0; i < cities.length; i++) {
            System.out.println("Input  : n = " + cities[i] + ", flights = " + Arrays.deepToString(flights[i]) + ", src = " + sources[i] + ", dst = " + destinations[i] + ", k = " + stops[i]);
            System.out.println("Output : " + findCheapestPrice(cities[i], flights[i], sources[i], destinations[i], stops[i]));
            System.out.println();
        }
    }

    public static int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        final int INFINITY = Integer.MAX_VALUE;
        int[] prices = new int[n];
        Arrays.fill(prices, INFINITY);
        prices[src] = 0;

        for (int round = 0; round <= k; round++) {
            int[] next = prices.clone();            // read the previous round, write the new one
            for (int[] flight : flights) {
                int from = flight[0];
                int to = flight[1];
                int price = flight[2];
                if (prices[from] != INFINITY && prices[from] + price < next[to]) {
                    next[to] = prices[from] + price;
                }
            }
            prices = next;
        }
        return prices[dst] == INFINITY ? -1 : prices[dst];
    }
}
```
