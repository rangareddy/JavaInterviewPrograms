# Product of the Last K Numbers

Design a class that keeps a stream of integers and can return the product of its last `k` numbers. `add(num)` appends a number to the stream; `getProduct(k)` returns the product of the last `k` numbers. There are always at least `k` numbers when `getProduct(k)` is called, and the product fits in a 32-bit integer.

## Algorithm Strategy (Prefix Products Restarted After Every Zero)

1. Keep a list `prefix` of running products, starting with `[1]`.
2. On `add(num)`: if `num` is 0, clear the list back to `[1]` (every product that spans this zero is 0); otherwise append `last * num`.
3. On `getProduct(k)`: if `k` is larger than the number of values since the last zero (`prefix.size() - 1`), the window contains a zero, so return 0.
4. Otherwise return `prefix[last] / prefix[last - k]`.

## Example walkthrough

- Adding 3, 0, 2, 5, 4 builds prefix `[1, 2, 10, 40]` after the zero.
- `getProduct(2)` = 40 / 2 = 20, `getProduct(3)` = 40 / 1 = 40, and `getProduct(4)` is 0 because the window includes the zero.

## Complexity Analysis

- **Time Complexity:** `O(1) per call` — One list operation or a division.
- **Space Complexity:** `O(n)` — The prefix products.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Store Numbers and Multiply on Demand

Keep every number and multiply the last `k` on each call.

```java
public static class Naive { private final java.util.List<Integer> a = new java.util.ArrayList<>(); public void add(int x) { a.add(x); } public int getProduct(int k) { int p = 1; for (int i = a.size() - k; i < a.size(); i++) p *= a.get(i); return p; } }
```

- **Time Complexity:** O(k) per call
- **Space Complexity:** O(n)

### Approach 2: Prefix Products Reset on Zero (Optimized)

Divide two prefix products to get any window product. This is the version implemented in the repository.

```java
public static class Prefix { private final java.util.List<Integer> p = new java.util.ArrayList<>(); public Prefix() { p.add(1); } public void add(int x) { if (x == 0) { p.clear(); p.add(1); } else p.add(p.get(p.size() - 1) * x); } public int getProduct(int k) { int s = p.size(); return k >= s ? 0 : p.get(s - 1) / p.get(s - 1 - k); } }
```

- **Time Complexity:** O(1) per call
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Store Numbers and Multiply on Demand | O(k) per call | O(n) |
| 2 | Prefix Products Reset on Zero (Optimized) | O(1) per call | O(n) |

## Source Code

### ProductOfTheLastKNumbers.java

```java
import java.util.ArrayList;
import java.util.List;

public class ProductOfTheLastKNumbers {

    public static void main(String[] args) {
        ProductOfTheLastKNumbers numbers = new ProductOfTheLastKNumbers();
        int[] adds = {3, 0, 2, 5, 4};
        for (int value : adds) {
            numbers.add(value);
        }
        System.out.println("Input  : add(3), add(0), add(2), add(5), add(4), getProduct(2)");
        System.out.println("Output : " + numbers.getProduct(2));
        System.out.println();
        System.out.println("Input  : getProduct(3)");
        System.out.println("Output : " + numbers.getProduct(3));
        System.out.println();
        System.out.println("Input  : getProduct(4)");
        System.out.println("Output : " + numbers.getProduct(4));
        System.out.println();
        numbers.add(8);
        System.out.println("Input  : add(8), getProduct(2)");
        System.out.println("Output : " + numbers.getProduct(2));
    }

    private final List<Integer> prefix = new ArrayList<>();

    public ProductOfTheLastKNumbers() {
        prefix.add(1);
    }

    public void add(int num) {
        if (num == 0) {
            prefix.clear();
            prefix.add(1);
        } else {
            prefix.add(prefix.get(prefix.size() - 1) * num);
        }
    }

    public int getProduct(int k) {
        int size = prefix.size();
        if (k >= size) {
            return 0;
        }
        return prefix.get(size - 1) / prefix.get(size - 1 - k);
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: with prefix products, the product of the last `k` numbers is the last prefix divided by the prefix `k` positions earlier. The only trouble is zero, which would make every later prefix 0. But a zero just means that any window reaching it has product 0, so the list is simply restarted after a zero and a too-long `k` is answered with 0.

**Sequence:** `add(3), add(0), add(2), add(5), add(4)`.

| Call | `prefix` | Result |
|------|----------|--------|
| `add(3)` | [1, 3] | |
| `add(0)` | [1] | |
| `add(2)`, `add(5)`, `add(4)` | [1, 2, 10, 40] | |
| `getProduct(2)` | | 40 / 2 = 20 |
| `getProduct(3)` | | 40 / 1 = 40 |
| `getProduct(4)` | | 0 (reaches the zero) |

**First output in `main`:** `20`.
