# Fruit Into Baskets

Trees stand in a row and `fruits[i]` is the type of fruit on tree `i`. You have two baskets, each holding one type of fruit, and you must pick exactly one fruit from every tree while moving right from any starting tree, stopping when a fruit does not fit. Return the maximum number of fruits you can pick.

## Algorithm Strategy (Sliding Window with a Count Map)

1. The task is the longest subarray with at most two distinct values.
2. Extend `right` and add its fruit to a count map.
3. While the map has more than two types, remove `fruits[left]` from the map (deleting its key when the count hits 0) and advance `left`.
4. Track the maximum window length.

## Example walkthrough

- For `[1, 2, 3, 2, 2]`: the window `[2, 3, 2, 2]` has two types and length 4.
- The sample `[1, 2, 1]` gives 3.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each index enters and leaves once.
- **Space Complexity:** `O(1)` — At most three keys in the map.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

For every start, extend while at most two types appear.

```java
public static int bruteForce(int[] f) {
    int best = 0;
    for (int i = 0; i < f.length; i++) { java.util.Set<Integer> s = new java.util.HashSet<>(); int j = i; while (j < f.length && (s.contains(f[j]) || s.size() < 2)) { s.add(f[j]); j++; } best = Math.max(best, j - i); }
    return best;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Sliding Window with a Count Map (Optimized)

Shrink while more than two types are in the window. This is the version implemented in the repository.

```java
public static int window(int[] f) {
    java.util.Map<Integer, Integer> m = new java.util.HashMap<>(); int l = 0, best = 0;
    for (int r = 0; r < f.length; r++) {
        m.merge(f[r], 1, Integer::sum);
        while (m.size() > 2) { int t = f[l++]; if (m.merge(t, -1, Integer::sum) == 0) m.remove(t); }
        best = Math.max(best, r - l + 1);
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) |
| 2 | Sliding Window with a Count Map (Optimized) | O(n) | O(1) |

## Source Code

### FruitIntoBaskets.java

```java
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FruitIntoBaskets {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 1}, {0, 1, 2, 2}, {1, 2, 3, 2, 2}, {3, 3, 3, 1, 2, 1, 1, 2, 3, 3, 4}, {}};
        for (int[] fruits : inputs) {
            System.out.println("Input  : fruits = " + Arrays.toString(fruits));
            System.out.println("Output : " + totalFruit(fruits));
            System.out.println();
        }
    }

    public static int totalFruit(int[] fruits) {
        if (fruits == null || fruits.length == 0) {
            return 0;
        }
        Map<Integer, Integer> basket = new HashMap<>();
        int left = 0;
        int best = 0;
        for (int right = 0; right < fruits.length; right++) {
            basket.merge(fruits[right], 1, Integer::sum);
            while (basket.size() > 2) {
                int type = fruits[left++];
                if (basket.merge(type, -1, Integer::sum) == 0) {
                    basket.remove(type);
                }
            }
            best = Math.max(best, right - left + 1);
        }
        return best;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the fruit you can collect from a starting tree is the longest stretch with at most two types, so the problem is a variable-size window whose only constraint is the number of distinct values.

**Input:** `fruits = [1, 2, 1]`.

| `right` | Fruit | Basket after | Window | Best |
|---------|-------|--------------|--------|------|
| 0 | 1 | {1:1} | [1] | 1 |
| 1 | 2 | {1:1, 2:1} | [1, 2] | 2 |
| 2 | 1 | {1:2, 2:1} | [1, 2, 1] | 3 |

**Output:** `3`.
