# Combination Sum

Given an array of distinct integers `candidates` and a `target`, return all unique combinations of candidates that sum to the target (a candidate may be used any number of times). A second method also solves the variant printed in the Blind 75 PDF, which only counts the ordered ways to reach the target.

## Algorithm Strategy (Backtracking (and DP for the Counting Variant))

1. Sort the candidates so the search can stop as soon as a candidate exceeds the remaining target.
2. Backtrack with a start index: at each step try every candidate from `start` onwards, add it to the current combination and recurse with the reduced remaining target. Passing the same index again allows reusing a candidate; never going back to smaller indexes prevents duplicate combinations in a different order.
3. When the remaining target is 0, record a copy of the current combination; when a candidate is larger than the remaining target, stop the loop.
4. Counting variant (ordered sequences, e.g. 1+1+2 and 2+1+1 count separately): `ways[t] = sum(ways[t - c])` over all candidates `c <= t`, with `ways[0] = 1`.

## Example walkthrough

- For `[2, 3, 6, 7]` and target 7: the combinations are `[2, 2, 3]` and `[7]`.
- For `[1, 2, 3]` and target 4 the counting variant gives `7` ordered sequences.

## Complexity Analysis

- **Time Complexity:** `O(N^(T/M))` — Backtracking: `N` candidates, target `T`, smallest candidate `M`; the counting variant is `O(T * N)`.
- **Space Complexity:** `O(T/M)` — Recursion depth (the counting variant uses `O(T)`).

## Source Code

### CombinationSum.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationSum {

    public static void main(String[] args) {
        int[][] candidates = {{2, 3, 6, 7}, {2, 3, 5}, {2}};
        int[] targets = {7, 8, 1};
        for (int i = 0; i < candidates.length; i++) {
            System.out.println("Input  : candidates = " + Arrays.toString(candidates[i]) + ", target = " + targets[i]);
            System.out.println("Output : " + combinationSum(candidates[i], targets[i]));
            System.out.println();
        }

        int[] nums = {1, 2, 3};
        System.out.println("Input  : nums = " + Arrays.toString(nums) + ", target = 4 (count ordered combinations)");
        System.out.println("Output : " + combinationSum4(nums, 4));
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        int[] sorted = candidates.clone();
        Arrays.sort(sorted);
        backtrack(sorted, target, 0, new ArrayList<>(), result);
        return result;
    }

    private static void backtrack(int[] candidates, int remaining, int start, List<Integer> current, List<List<Integer>> result) {
        if (remaining == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i < candidates.length && candidates[i] <= remaining; i++) {
            current.add(candidates[i]);
            backtrack(candidates, remaining - candidates[i], i, current, result);   // i again: reuse allowed
            current.remove(current.size() - 1);
        }
    }

    /** Counts the ordered sequences of numbers that add up to the target. */
    public static int combinationSum4(int[] nums, int target) {
        int[] ways = new int[target + 1];
        ways[0] = 1;
        for (int t = 1; t <= target; t++) {
            for (int num : nums) {
                if (num <= t) {
                    ways[t] += ways[t - num];
                }
            }
        }
        return ways[target];
    }
}
```
