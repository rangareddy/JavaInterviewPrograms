# Merge Triplets to Form Target Triplet

A triplet is an array of three integers. You are given a list `triplets` and a `target` triplet. In one operation you pick two triplets and replace the second with the element-wise maximum of both. Return `true` if `target` can become an element of `triplets` after any number of operations.

## Algorithm Strategy (Greedy Filter and Match)

1. An operation only ever raises values, so any triplet with a value above the corresponding target value can never be used: skip it.
2. Among the remaining ("safe") triplets, the element-wise maximum is the best we can build.
3. Track which of the three target positions has been matched exactly by some safe triplet.
4. The answer is `true` when all three positions are matched.

## Example walkthrough

- For `triplets = [[2,5,3],[1,8,4],[1,7,5]]` and `target = [2,7,5]`: `[1,8,4]` is unsafe (8 > 7); merging `[2,5,3]` and `[1,7,5]` gives `[2,7,5]`.
- Result: `true`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass over the triplets.
- **Space Complexity:** `O(1)` — Three flags.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force Merge Closure

Keep a set of every triplet that can be built (bounded by the target) and repeatedly merge pairs until nothing new appears.

```java
public static boolean mergeTripletsBrute(int[][] triplets, int[] target) {
    Set<List<Integer>> reachable = new HashSet<>();
    for (int[] t : triplets)
        if (t[0] <= target[0] && t[1] <= target[1] && t[2] <= target[2]) reachable.add(Arrays.asList(t[0], t[1], t[2]));
    boolean changed = true;
    while (changed) {
        changed = false;
        for (List<Integer> a : new ArrayList<>(reachable))
            for (List<Integer> b : new ArrayList<>(reachable)) {
                List<Integer> merged = Arrays.asList(Math.max(a.get(0), b.get(0)), Math.max(a.get(1), b.get(1)), Math.max(a.get(2), b.get(2)));
                if (reachable.add(merged)) changed = true;
            }
    }
    return reachable.contains(Arrays.asList(target[0], target[1], target[2]));
}
```

- **Time Complexity:** exponential in the worst case
- **Space Complexity:** O(states)

### Approach 2: Filter, Then Merge All Safe Triplets

Discard triplets that exceed the target and take the element-wise maximum of the rest.

```java
public static boolean mergeTripletsMerge(int[][] triplets, int[] target) {
    int[] best = new int[3];
    boolean any = false;
    for (int[] t : triplets) {
        if (t[0] > target[0] || t[1] > target[1] || t[2] > target[2]) continue;
        any = true;
        for (int k = 0; k < 3; k++) best[k] = Math.max(best[k], t[k]);
    }
    return any && Arrays.equals(best, target);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 3: Per-Position Search

For each target position, look for a safe triplet whose value there equals the target.

```java
public static boolean mergeTripletsPositions(int[][] triplets, int[] target) {
    for (int k = 0; k < 3; k++) {
        boolean found = false;
        for (int[] t : triplets) {
            if (t[0] <= target[0] && t[1] <= target[1] && t[2] <= target[2] && t[k] == target[k]) { found = true; break; }
        }
        if (!found) return false;
    }
    return true;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 4: Single Pass with Flags (Optimized)

One pass records which positions have been matched by a safe triplet. This is the version implemented in the repository.

```java
public static boolean mergeTriplets(int[][] triplets, int[] target) {
    boolean[] matched = new boolean[3];
    for (int[] triplet : triplets) {
        if (triplet[0] > target[0] || triplet[1] > target[1] || triplet[2] > target[2]) continue;
        for (int k = 0; k < 3; k++) if (triplet[k] == target[k]) matched[k] = true;
    }
    return matched[0] && matched[1] && matched[2];
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force Merge Closure | exponential in the worst case | O(states) |
| 2 | Filter, Then Merge All Safe Triplets | O(n) | O(1) |
| 3 | Per-Position Search | O(n) | O(1) |
| 4 | Single Pass with Flags (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### MergeTripletsToFormTargetTriplet.java

```java
import java.util.Arrays;

public class MergeTripletsToFormTargetTriplet {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{2, 5, 3}, {1, 8, 4}, {1, 7, 5}},
                {{3, 4, 5}, {4, 5, 6}},
                {{2, 5, 3}, {2, 3, 4}, {1, 2, 5}, {5, 2, 3}},
                {{1, 1, 1}}
        };
        int[][] targets = {{2, 7, 5}, {3, 2, 5}, {5, 5, 5}, {1, 1, 1}};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : triplets = " + Arrays.deepToString(inputs[i]) + ", target = " + Arrays.toString(targets[i]));
            System.out.println("Output : " + mergeTriplets(inputs[i], targets[i]));
            System.out.println();
        }
    }

    public static boolean mergeTriplets(int[][] triplets, int[] target) {
        boolean[] matched = new boolean[3];
        for (int[] triplet : triplets) {
            if (triplet[0] > target[0] || triplet[1] > target[1] || triplet[2] > target[2]) {
                continue;
            }
            for (int k = 0; k < 3; k++) {
                if (triplet[k] == target[k]) {
                    matched[k] = true;
                }
            }
        }
        return matched[0] && matched[1] && matched[2];
    }
}
```
