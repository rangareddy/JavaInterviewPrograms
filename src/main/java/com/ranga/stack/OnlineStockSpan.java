package com.ranga.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Design a class {@code StockSpanner} with a method {@code next(price)} that returns the span of the stock price for the current day: the number of consecutive days (going backwards, including today) on which the price was less than or equal to today's price.
 *
 * <p><b>Algorithm Strategy (Monotonic Stack of (Price, Span) Pairs):</b>
 * <ol>
 *   <li>Keep a stack of {@code (price, span)} pairs with strictly decreasing prices from bottom to top. Each pair stands for a run of consecutive days whose span has already been computed.</li>
 *   <li>On {@code next(price)}, start with {@code span = 1} for today.</li>
 *   <li>While the top pair has a price less than or equal to today's price, pop it and add its span: all those days are part of today's run.</li>
 *   <li>Push {@code (price, span)} and return the span. Because pairs are merged when popped, each day is pushed and popped once, so {@code next} is amortized {@code O(1)}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For prices {@code 100, 80, 60, 70, 60, 75, 85}: spans are {@code 1, 1, 1, 2, 1, 4, 6}.</li>
 *   <li>Just before {@code 75} the stack holds {@code (100, 1), (80, 1), (70, 2), (60, 1)}. On {@code 75} it pops {@code (60, 1)} and {@code (70, 2)}, stops at {@code 80}, and returns {@code 1 + 1 + 2 = 4}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1) amortized} — Each price is pushed once and popped at most once over all calls.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The stack.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class OnlineStockSpan {

    public static void main(String[] args) {
        OnlineStockSpan spanner = new OnlineStockSpan();
        int[] prices = {100, 80, 60, 70, 60, 75, 85};
        StringBuilder input = new StringBuilder();
        StringBuilder output = new StringBuilder();
        for (int price : prices) {
            input.append(input.length() == 0 ? "" : ", ").append(price);
            output.append(output.length() == 0 ? "" : ", ").append(spanner.next(price));
        }
        System.out.println("Input  : next(" + input + ")");
        System.out.println("Output : [" + output + "]");
    }

    private final Deque<int[]> stack = new ArrayDeque<>();

    public int next(int price) {
        int span = 1;
        while (!stack.isEmpty() && stack.peek()[0] <= price) {
            span += stack.pop()[1];
        }
        stack.push(new int[]{price, span});
        return span;
    }
}
