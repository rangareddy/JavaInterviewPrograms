# Random Pick with Weight

Given an array `w` of positive integers where `w[i]` is the weight of index `i`, implement `pickIndex()` which returns an index with probability proportional to its weight.

## Algorithm Strategy (Prefix Sums and Binary Search)

1. Build prefix sums of the weights: `prefix[i] = w[0] + ... + w[i]`. The last value is the total weight.
2. Draw a random integer `target` in `[1, total]`.
3. Binary search the first index whose prefix sum is `>= target`.
4. That index owns the interval `(prefix[i-1], prefix[i]]`, whose size equals its weight, so the probability is proportional to it.

## Example walkthrough

- For `w = [1, 3]`: the prefix sums are `[1, 4]`; targets 1 maps to index 0, targets 2 to 4 map to index 1.
- Index 1 is picked with probability 3/4.

## Complexity Analysis

- **Time Complexity:** `O(log n) per pick` — Binary search on the prefix array; the build is O(n).
- **Space Complexity:** `O(n)` — The prefix sum array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Expand into a Big Array

Repeat each index `w[i]` times in a list and pick a random element. Memory grows with the total weight.

```java
public static int[] expand(int[] w) {
    int total = 0; for (int x : w) total += x;
    int[] out = new int[total]; int k = 0;
    for (int i = 0; i < w.length; i++) for (int c = 0; c < w[i]; c++) out[k++] = i;
    return out;
}
```

- **Time Complexity:** O(sum of weights) build, O(1) pick
- **Space Complexity:** O(sum of weights)

### Approach 2: Prefix Sums with a Linear Scan

Find the interval by scanning the prefix sums from the start.

```java
public static int linearPick(int[] prefix, int target) {
    for (int i = 0; i < prefix.length; i++) if (prefix[i] >= target) return i;
    return prefix.length - 1;
}
```

- **Time Complexity:** O(n) per pick
- **Space Complexity:** O(n)

### Approach 3: Prefix Sums with Binary Search (Optimized)

Search the prefix array for the first sum at least the target. This is the version implemented in the repository.

```java
public static int binaryPick(int[] prefix, int target) {
    int lo = 0, hi = prefix.length - 1;
    while (lo < hi) { int mid = lo + (hi - lo) / 2; if (prefix[mid] >= target) hi = mid; else lo = mid + 1; }
    return lo;
}
```

- **Time Complexity:** O(log n) per pick
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Expand into a Big Array | O(sum of weights) build, O(1) pick | O(sum of weights) |
| 2 | Prefix Sums with a Linear Scan | O(n) per pick | O(n) |
| 3 | Prefix Sums with Binary Search (Optimized) | O(log n) per pick | O(n) |

## Source Code

### RandomPickWithWeight.java

```java
import java.util.Arrays;
import java.util.Random;

public class RandomPickWithWeight {

    public static void main(String[] args) {
        int[] weights = {1, 3};
        RandomPickWithWeight picker = new RandomPickWithWeight(weights, new Random(42));
        int[] counts = new int[weights.length];
        int picks = 10000;
        for (int i = 0; i < picks; i++) {
            counts[picker.pickIndex()]++;
        }
        System.out.println("Input  : w = " + Arrays.toString(weights) + ", picks = " + picks);
        System.out.println("Output : counts = " + Arrays.toString(counts));
    }

    private final int[] prefix;
    private final Random random;

    public RandomPickWithWeight(int[] w) {
        this(w, new Random());
    }

    public RandomPickWithWeight(int[] w, Random random) {
        this.random = random;
        prefix = new int[w.length];
        int sum = 0;
        for (int i = 0; i < w.length; i++) {
            sum += w[i];
            prefix[i] = sum;
        }
    }

    public int pickIndex() {
        if (prefix.length == 0) {
            return -1;
        }
        int target = random.nextInt(prefix[prefix.length - 1]) + 1;
        int lo = 0;
        int hi = prefix.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (prefix[mid] >= target) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea: lay the weights end to end on a number line. A random point on that line falls in index `i`'s segment with probability proportional to its length, and the prefix sums are the segment ends.

**Input:** `w = [1, 3, 2]`.

| Index | Weight | Prefix sum | Targets that pick it |
|-------|--------|-----------|----------------------|
| 0 | 1 | 1 | 1 |
| 1 | 3 | 4 | 2, 3, 4 |
| 2 | 2 | 6 | 5, 6 |

A random `target` between 1 and 6 is mapped with a binary search for the first prefix sum that is at least the target, so for `target = 3` the search returns index 1.

**Output:** index 1 is returned with probability 3/6 = 50%.
