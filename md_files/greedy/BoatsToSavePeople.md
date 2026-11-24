# Boats to Save People

Given the weights of people in `people` and a boat that carries at most two people with a total weight of at most `limit`, return the minimum number of boats needed to carry everyone. Each person weighs at most `limit`.

## Algorithm Strategy (Two Pointers on Sorted Weights)

1. Sort the weights.
2. The heaviest person needs a boat anyway, so try to pair them with the lightest person.
3. If `people[light] + people[heavy] <= limit`, both ride together: move `light` up.
4. In either case the heavy person is done: move `heavy` down and count a boat.

## Example walkthrough

- For `[3, 2, 2, 1]` and limit 3: sorted `[1, 2, 2, 3]`: 3 goes alone, 2 pairs with 1, then 2 goes alone.
- Result: `3`.

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — Dominated by the sort.
- **Space Complexity:** `O(1)` — Apart from sorting.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Greedy Matching without Sorting Pointers

Repeatedly take the heaviest remaining person and the heaviest partner who still fits, from a sorted list.

```java
public static int partner(int[] p, int limit) {
    java.util.List<Integer> l = new java.util.ArrayList<>(); for (int x : p) l.add(x); java.util.Collections.sort(l);
    int boats = 0;
    while (!l.isEmpty()) {
        int heavy = l.remove(l.size() - 1); boats++;
        for (int i = l.size() - 1; i >= 0; i--) if (l.get(i) + heavy <= limit) { l.remove(i); break; }
    }
    return boats;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 2: Two Pointers after Sorting (Optimized)

Pair the lightest with the heaviest when they fit. This is the version implemented in the repository.

```java
public static int pointers(int[] p, int limit) {
    int[] a = p.clone(); java.util.Arrays.sort(a);
    int l = 0, h = a.length - 1, boats = 0;
    while (l <= h) { if (a[l] + a[h] <= limit) l++; h--; boats++; }
    return boats;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Greedy Matching without Sorting Pointers | O(n^2) | O(n) |
| 2 | Two Pointers after Sorting (Optimized) | O(n log n) | O(1) |

## Source Code

### BoatsToSavePeople.java

```java
import java.util.Arrays;

public class BoatsToSavePeople {

    public static void main(String[] args) {
        int[][] people = {{3, 2, 2, 1}, {1, 2}, {3, 5, 3, 4}, {5}};
        int[] limits = {3, 3, 5, 5};
        for (int i = 0; i < people.length; i++) {
            System.out.println("Input  : people = " + Arrays.toString(people[i]) + ", limit = " + limits[i]);
            System.out.println("Output : " + numRescueBoats(people[i].clone(), limits[i]));
            System.out.println();
        }
    }

    public static int numRescueBoats(int[] people, int limit) {
        if (people == null || people.length == 0) {
            return 0;
        }
        Arrays.sort(people);
        int light = 0;
        int heavy = people.length - 1;
        int boats = 0;
        while (light <= heavy) {
            if (people[light] + people[heavy] <= limit) {
                light++;
            }
            heavy--;
            boats++;
        }
        return boats;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the heaviest person always needs a boat. The best companion is the lightest person, because if the lightest cannot fit, nobody can. Pairing them and moving on is never worse.

**Input:** `people = [3, 2, 2, 1]`, `limit = 3`. Sorted: `[1, 2, 2, 3]`.

| `light` | `heavy` | Sum | Action | Boats |
|---------|---------|-----|--------|-------|
| 1 | 3 | 4 | 3 alone | 1 |
| 1 | 2 | 3 | 1 and 2 together | 2 |
| 2 | 2 | 4 | the last 2 alone | 3 |

**Output:** `3`.
