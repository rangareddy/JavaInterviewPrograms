# Subsets II

Given an integer array `nums` that may contain duplicates, return all possible subsets (the power set). The solution set must not contain duplicate subsets.

## Algorithm Strategy (Sort, Then Skip Equal Elements at the Same Level)

1. Sort the array so that equal numbers are adjacent.
2. Use the same backtracking as for Subsets: record the current subset, then extend it with the elements `start .. n - 1`.
3. To avoid duplicates, inside one loop skip an element that equals the previous one (`i > start && nums[i] == nums[i - 1]`): choosing the same value twice at the same position would build the same subset again.
4. An equal value is still allowed at a deeper level (a later position), so subsets such as `[2, 2]` are produced.

## Example walkthrough

- For `[1, 2, 2]`: the subsets are `[]`, `[1]`, `[1,2]`, `[1,2,2]`, `[2]`, `[2,2]`.
- The second 2 is skipped as a first choice at each level, which avoids the duplicates `[1,2]` and `[2]`.

## Complexity Analysis

- **Time Complexity:** `O(n * 2^n)` — At most `2^n` subsets, each copied in `O(n)`.
- **Space Complexity:** `O(n)` — Recursion depth (excluding the output).

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Generate All, Deduplicate with a Set

Generate every subset of the sorted array (as if all elements were distinct) and remove duplicates by storing them in a `LinkedHashSet`. Correct but builds `2^n` subsets even when most are duplicates.

```java
public static List<List<Integer>> subsetsWithDupSet(int[] nums) {
    int[] sorted = nums.clone();
    Arrays.sort(sorted);
    Set<List<Integer>> unique = new LinkedHashSet<>();
    for (int mask = 0; mask < (1 << sorted.length); mask++) {
        List<Integer> subset = new ArrayList<>();
        for (int i = 0; i < sorted.length; i++) if ((mask & (1 << i)) != 0) subset.add(sorted[i]);
        unique.add(subset);
    }
    return new ArrayList<>(unique);
}
```

- **Time Complexity:** O(n * 2^n)
- **Space Complexity:** O(n * 2^n)

### Approach 2: Cascading that Extends Only the Latest Batch

The doubling construction, but when the current number equals the previous one it is only appended to the subsets created in the previous step; appending it to the older subsets would recreate duplicates.

```java
public static List<List<Integer>> subsetsWithDupCascade(int[] nums) {
    int[] sorted = nums.clone();
    Arrays.sort(sorted);
    List<List<Integer>> result = new ArrayList<>();
    result.add(new ArrayList<>());
    int start = 0;
    for (int i = 0; i < sorted.length; i++) {
        int from = (i > 0 && sorted[i] == sorted[i - 1]) ? start : 0;       // only extend the latest batch
        start = result.size();
        for (int j = from; j < start; j++) {
            List<Integer> extended = new ArrayList<>(result.get(j));
            extended.add(sorted[i]);
            result.add(extended);
        }
    }
    return result;
}
```

- **Time Complexity:** O(n * 2^n)
- **Space Complexity:** O(1) besides the output

### Approach 3: Choose How Many Copies of Each Distinct Value

Count the occurrences of every distinct value and decide, value by value, how many copies (0 to its count) go into the subset. Duplicates cannot occur because a multiset is determined by its counts.

```java
public static List<List<Integer>> subsetsWithDupCounts(int[] nums) {
    TreeMap<Integer, Integer> counts = new TreeMap<>();
    for (int num : nums) counts.merge(num, 1, Integer::sum);
    List<int[]> values = new ArrayList<>();
    for (Map.Entry<Integer, Integer> e : counts.entrySet()) values.add(new int[]{e.getKey(), e.getValue()});
    List<List<Integer>> result = new ArrayList<>();
    choose(values, 0, new ArrayList<>(), result);
    return result;
}

private static void choose(List<int[]> values, int index, List<Integer> current, List<List<Integer>> result) {
    if (index == values.size()) { result.add(new ArrayList<>(current)); return; }
    int value = values.get(index)[0], count = values.get(index)[1];
    for (int copies = 0; copies <= count; copies++) {
        for (int i = 0; i < copies; i++) current.add(value);
        choose(values, index + 1, current, result);
        for (int i = 0; i < copies; i++) current.remove(current.size() - 1);
    }
}
```

- **Time Complexity:** O(n * 2^n)
- **Space Complexity:** O(n)

### Approach 4: Sorted Backtracking Skipping Equal Siblings (Optimized)

Sort the array and skip an element equal to the previous one inside the same loop; it would create the same subset again. This is the version implemented in the repository.

```java
public static List<List<Integer>> subsetsWithDup(int[] nums) {
    int[] sorted = nums.clone();
    Arrays.sort(sorted);
    List<List<Integer>> result = new ArrayList<>();
    backtrack(sorted, 0, new ArrayList<>(), result);
    return result;
}

private static void backtrack(int[] nums, int start, List<Integer> current, List<List<Integer>> result) {
    result.add(new ArrayList<>(current));
    for (int i = start; i < nums.length; i++) {
        if (i > start && nums[i] == nums[i - 1]) continue;
        current.add(nums[i]);
        backtrack(nums, i + 1, current, result);
        current.remove(current.size() - 1);
    }
}
```

- **Time Complexity:** O(n * 2^n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Generate All, Deduplicate with a Set | O(n * 2^n) | O(n * 2^n) |
| 2 | Cascading that Extends Only the Latest Batch | O(n * 2^n) | O(1) besides the output |
| 3 | Choose How Many Copies of Each Distinct Value | O(n * 2^n) | O(n) |
| 4 | Sorted Backtracking Skipping Equal Siblings (Optimized) | O(n * 2^n) | O(n) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### SubsetsII.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubsetsII {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 2}, {0}, {4, 4, 4, 1, 4}, {2, 1, 2}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + subsetsWithDup(nums));
            System.out.println();
        }
    }

    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        List<List<Integer>> result = new ArrayList<>();
        backtrack(sorted, 0, new ArrayList<>(), result);
        return result;
    }

    private static void backtrack(int[] nums, int start, List<Integer> current, List<List<Integer>> result) {
        result.add(new ArrayList<>(current));
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) {
                continue;                                   // same value already tried at this position
            }
            current.add(nums[i]);
            backtrack(nums, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
}
```
