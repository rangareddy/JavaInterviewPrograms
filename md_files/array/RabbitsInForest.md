# Rabbits in Forest

There is a forest with an unknown number of rabbits. We asked `n` rabbits "How many other rabbits have the same colour as you?" and collected the answers in `answers`, where `answers[i]` is the answer of the `i`-th rabbit. Return the minimum number of rabbits that could be in the forest.

## Algorithm Strategy (Group Same Answers into Colour Groups)

1. A rabbit that answers `x` belongs to a colour group of `x + 1` rabbits.
2. Count how many rabbits gave each answer.
3. For an answer `x` given by `c` rabbits, they need `ceil(c / (x + 1))` groups of size `x + 1` to be consistent.
4. Sum `groups * (x + 1)` over all answers.

## Example walkthrough

- For `[1, 1, 2]`: two rabbits say 1, so one group of 2; one rabbit says 2, so one group of 3.
- Result: `2 + 3 = 5`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass to count.
- **Space Complexity:** `O(n)` — The counts.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Greedy Simulation with Open Groups

Keep a map of how many more rabbits each open group can take and open a new group when none is free.

```java
public static int sim(int[] a) { java.util.Map<Integer, Integer> open = new java.util.HashMap<>(); int total = 0; for (int x : a) { int left = open.getOrDefault(x, 0); if (left > 0) open.put(x, left - 1); else { total += x + 1; open.put(x, x); } } return total; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Counting Groups by Answer (Optimized)

Round the count of each answer up to whole groups. This is the version implemented in the repository.

```java
public static int groups(int[] a) { java.util.Map<Integer, Integer> c = new java.util.HashMap<>(); for (int x : a) c.merge(x, 1, Integer::sum); int t = 0; for (java.util.Map.Entry<Integer, Integer> e : c.entrySet()) { int g = e.getKey() + 1; t += (e.getValue() + g - 1) / g * g; } return t; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Greedy Simulation with Open Groups | O(n) | O(n) |
| 2 | Counting Groups by Answer (Optimized) | O(n) | O(n) |

## Source Code

### RabbitsInForest.java

```java
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RabbitsInForest {

    public static void main(String[] args) {
        int[][] inputs = {{1, 1, 2}, {10, 10, 10}, {0, 0, 1, 1, 1}, {}};
        for (int[] answers : inputs) {
            System.out.println("Input  : answers = " + Arrays.toString(answers));
            System.out.println("Output : " + numRabbits(answers));
            System.out.println();
        }
    }

    public static int numRabbits(int[] answers) {
        if (answers == null) {
            return 0;
        }
        Map<Integer, Integer> counts = new HashMap<>();
        for (int answer : answers) {
            counts.merge(answer, 1, Integer::sum);
        }
        int total = 0;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            int groupSize = entry.getKey() + 1;
            int groups = (entry.getValue() + groupSize - 1) / groupSize;
            total += groups * groupSize;
        }
        return total;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a rabbit answering `x` sits in a colour group of exactly `x + 1` rabbits, and only rabbits with the same answer can share a group. If more than `x + 1` rabbits give the same answer, they cannot all be one group, so extra groups are needed. The minimum number of rabbits is therefore the count of answers rounded up to a multiple of `x + 1`, for every answer.

**Input:** `answers = [1, 1, 2]`.

| Answer | Rabbits saying it | Group size | Groups | Rabbits |
|--------|-------------------|------------|--------|---------|
| 1 | 2 | 2 | 1 | 2 |
| 2 | 1 | 3 | 1 | 3 |

**Output:** `5`.
