package com.ranga.graph;

import java.util.Arrays;

/**
 * There are {@code n} cities connected by flights {@code [from, to, price]}. Given a source {@code src}, a destination {@code dst} and an integer {@code k}, return the cheapest price from {@code src} to {@code dst} with at most {@code k} stops (so at most {@code k + 1} flights), or -1 if there is no such route.
 *
 * <p><b>Algorithm Strategy (Bellman-Ford Limited to k + 1 Rounds):</b>
 * <ol>
 *   <li>Keep {@code prices[v]}, the cheapest known price to reach city {@code v} (infinity at first, 0 for {@code src}).</li>
 *   <li>A route with at most {@code k} stops uses at most {@code k + 1} flights, so run exactly {@code k + 1} rounds; round {@code r} finds the best prices that use at most {@code r} flights.</li>
 *   <li>In each round work on a copy of the previous prices: for every flight {@code (u, v, w)} with a finite {@code prices[u]}, try {@code prices[u] + w} for {@code v}. Using the copy prevents a round from chaining two flights.</li>
 *   <li>After the rounds, {@code prices[dst]} is the answer (or -1 if it is still infinite).</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For 4 cities, flights {@code [[0,1,100],[1,2,100],[2,0,100],[1,3,600],[2,3,200]]}, from 0 to 3 with {@code k = 1}: the route 0 {@code ->} 1 {@code ->} 3 costs 700.</li>
 *   <li>The cheaper route 0-1-2-3 (cost 400) needs 2 stops, so it is not allowed: {@code 700}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(k * E)} — {@code k + 1} passes over all flights.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — Two price arrays.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
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
