# Permutations

Given an array `nums` of distinct integers, return all possible permutations in any order.

## Algorithm Strategy (Backtracking with a Used Array)

1. Build a permutation position by position in a list `current`; a boolean array `used` remembers which numbers are already in it.
2. At each position try every number that is not used: mark it, append it, recurse for the next position, then unmark and remove it (backtrack).
3. When `current` contains all `n` numbers it is a complete permutation: record a copy.
4. There are `n!` permutations; the number of choices shrinks by one at every level (`n`, `n-1`, ...).

## Example walkthrough

- For `[1, 2, 3]`: choose 1, then 2, then 3 `-> [1,2,3]`; backtrack and choose 3 then 2 `-> [1,3,2]`; then start with 2, and so on.
- Result: 6 permutations.

## Complexity Analysis

- **Time Complexity:** `O(n * n!)` — `n!` permutations, each copied in `O(n)`.
- **Space Complexity:** `O(n)` — Recursion depth plus the `used` array (excluding the output).

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Swap-Based Backtracking

Permute the array in place: fix position `i` by swapping each remaining element into it, recurse for `i + 1` and swap back. No `used` array.

```java
public static List<List<Integer>> permuteSwap(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    if (nums.length > 0) swapPermute(nums.clone(), 0, result);
    return result;
}

private static void swapPermute(int[] a, int index, List<List<Integer>> result) {
    if (index == a.length) {
        List<Integer> permutation = new ArrayList<>();
        for (int value : a) permutation.add(value);
        result.add(permutation);
        return;
    }
    for (int i = index; i < a.length; i++) {
        int t = a[index]; a[index] = a[i]; a[i] = t;
        swapPermute(a, index + 1, result);
        t = a[index]; a[index] = a[i]; a[i] = t;
    }
}
```

- **Time Complexity:** O(n * n!)
- **Space Complexity:** O(n)

### Approach 2: Iterative Insertion

Start with the permutations of the first element. For each next element, insert it into every possible position of every existing permutation.

```java
public static List<List<Integer>> permuteInsert(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    if (nums.length == 0) return result;
    result.add(new ArrayList<>());
    for (int num : nums) {
        List<List<Integer>> next = new ArrayList<>();
        for (List<Integer> permutation : result) {
            for (int position = 0; position <= permutation.size(); position++) {
                List<Integer> extended = new ArrayList<>(permutation);
                extended.add(position, num);
                next.add(extended);
            }
        }
        result = next;
    }
    return result;
}
```

- **Time Complexity:** O(n * n!)
- **Space Complexity:** O(n * n!)

### Approach 3: Heap's Algorithm

A classic algorithm that generates each permutation from the previous one by a single swap, so no permutation is built from scratch. The element to swap depends on the parity of the current size.

```java
public static List<List<Integer>> permuteHeap(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    if (nums.length > 0) heap(nums.clone(), nums.length, result);
    return result;
}

private static void heap(int[] a, int size, List<List<Integer>> result) {
    if (size == 1) {
        List<Integer> permutation = new ArrayList<>();
        for (int value : a) permutation.add(value);
        result.add(permutation);
        return;
    }
    for (int i = 0; i < size; i++) {
        heap(a, size - 1, result);
        int swapWith = (size % 2 == 0) ? i : 0;
        int t = a[swapWith]; a[swapWith] = a[size - 1]; a[size - 1] = t;
    }
}
```

- **Time Complexity:** O(n * n!)
- **Space Complexity:** O(n)

### Approach 4: Backtracking with a Used Array (Optimized)

Fill the permutation position by position with every number that is not used yet. This is the version implemented in the repository.

```java
public static List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    if (nums.length == 0) return result;
    backtrack(nums, new boolean[nums.length], new ArrayList<>(), result);
    return result;
}

private static void backtrack(int[] nums, boolean[] used, List<Integer> current, List<List<Integer>> result) {
    if (current.size() == nums.length) { result.add(new ArrayList<>(current)); return; }
    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        used[i] = true;
        current.add(nums[i]);
        backtrack(nums, used, current, result);
        current.remove(current.size() - 1);
        used[i] = false;
    }
}
```

- **Time Complexity:** O(n * n!)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Swap-Based Backtracking | O(n * n!) | O(n) |
| 2 | Iterative Insertion | O(n * n!) | O(n * n!) |
| 3 | Heap's Algorithm | O(n * n!) | O(n) |
| 4 | Backtracking with a Used Array (Optimized) | O(n * n!) | O(n) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### Permutations.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permutations {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3}, {0, 1}, {1}, {}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + permute(nums));
            System.out.println();
        }
    }

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums.length == 0) {
            return result;
        }
        backtrack(nums, new boolean[nums.length], new ArrayList<>(), result);
        return result;
    }

    private static void backtrack(int[] nums, boolean[] used, List<Integer> current, List<List<Integer>> result) {
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }
            used[i] = true;
            current.add(nums[i]);
            backtrack(nums, used, current, result);
            current.remove(current.size() - 1);
            used[i] = false;
        }
    }
}
```
