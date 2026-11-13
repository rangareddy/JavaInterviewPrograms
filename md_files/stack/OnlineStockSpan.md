# Online Stock Span

Design a class `StockSpanner` with a method `next(price)` that returns the span of the stock price for the current day: the number of consecutive days (going backwards, including today) on which the price was less than or equal to today's price.

## Algorithm Strategy (Monotonic Stack of (Price, Span) Pairs)

1. Keep a stack of `(price, span)` pairs with strictly decreasing prices from bottom to top. Each pair stands for a run of consecutive days whose span has already been computed.
2. On `next(price)`, start with `span = 1` for today.
3. While the top pair has a price less than or equal to today's price, pop it and add its span: all those days are part of today's run.
4. Push `(price, span)` and return the span. Because pairs are merged when popped, each day is pushed and popped once, so `next` is amortized `O(1)`.

## Example walkthrough

- For prices `100, 80, 60, 70, 60, 75, 85`: spans are `1, 1, 1, 2, 1, 4, 6`.
- Just before `75` the stack holds `(100, 1), (80, 1), (70, 2), (60, 1)`. On `75` it pops `(60, 1)` and `(70, 2)`, stops at `80`, and returns `1 + 1 + 2 = 4`.

## Complexity Analysis

- **Time Complexity:** `O(1) amortized` — Each price is pushed once and popped at most once over all calls.
- **Space Complexity:** `O(n)` — The stack.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Scan Back Through the History

Store every price and, on each call, walk backwards while the earlier prices are not greater.

```java
class HistoryStockSpanner {
    private final List<Integer> prices = new ArrayList<>();

    public int next(int price) {
        prices.add(price);
        int span = 0;
        for (int i = prices.size() - 1; i >= 0 && prices.get(i) <= price; i--) span++;
        return span;
    }
}
```

- **Time Complexity:** O(n) per call, O(n^2) for n calls
- **Space Complexity:** O(n)

### Approach 2: Monotonic Stack (Optimized)

Merge the spans of all earlier prices that are not greater than today's into one stack entry, so a price is never rescanned. This is the version implemented in the repository.

```java
private final Deque<int[]> stack = new ArrayDeque<>();

public int next(int price) {
    int span = 1;
    while (!stack.isEmpty() && stack.peek()[0] <= price) {
        span += stack.pop()[1];
    }
    stack.push(new int[]{price, span});
    return span;
}
```

- **Time Complexity:** O(1) amortized per call
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Scan Back Through the History | O(n) per call, O(n^2) for n calls | O(n) |
| 2 | Monotonic Stack (Optimized) | O(1) amortized per call | O(n) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### OnlineStockSpan.java

```java
import java.util.ArrayDeque;
import java.util.Deque;

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
```
