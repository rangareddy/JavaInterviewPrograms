# 4Sum

Given an integer array `nums` and a `target`, return all the unique quadruplets `[nums[a], nums[b], nums[c], nums[d]]` with distinct indices such that their sum equals `target`.

## Algorithm Strategy (Sort, Fix Two Numbers, Two Pointers for the Rest)

1. Sort the array so equal values are adjacent and a two-pointer scan can move by comparing the sum with the target.
2. Fix the first number with index `i` and the second with index `j > i`. Skip a value that equals the previous choice at the same level; that is what removes duplicate quadruplets.
3. For the remaining two numbers use `left = j + 1` and `right = n - 1`. If the sum is too small move `left`, if too large move `right`, otherwise record the quadruplet and skip the duplicates on both sides.
4. Sum with `long` arithmetic: four values near `Integer.MAX_VALUE` overflow an `int`.

## Example walkthrough

- For `[1, 0, -1, 0, -2, 2]` with `target = 0`, sorted: `[-2, -1, 0, 0, 1, 2]`.
- Result: `[[-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1]]`.

## Complexity Analysis

- **Time Complexity:** `O(n^3)` — Two nested loops for the fixed numbers and a linear two-pointer scan inside.
- **Space Complexity:** `O(1)` — Besides the sort and the output list.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force with a Set

Try every quadruple of indices, sort each match and de-duplicate through a set.

```java
public static List<List<Integer>> fourSumBrute(int[] nums, int target) {
    Set<List<Integer>> found = new TreeSet<>((x, y) -> {
        for (int i = 0; i < 4; i++) {
            if (!x.get(i).equals(y.get(i))) return x.get(i) - y.get(i);
        }
        return 0;
    });
    int n = nums.length;
    for (int a = 0; a < n; a++)
        for (int b = a + 1; b < n; b++)
            for (int c = b + 1; c < n; c++)
                for (int d = c + 1; d < n; d++) {
                    if ((long) nums[a] + nums[b] + nums[c] + nums[d] == target) {
                        List<Integer> q = new ArrayList<>(Arrays.asList(nums[a], nums[b], nums[c], nums[d]));
                        Collections.sort(q);
                        found.add(q);
                    }
                }
    return new ArrayList<>(found);
}
```

- **Time Complexity:** O(n^4)
- **Space Complexity:** O(k) for the k distinct quadruplets

### Approach 2: Sort and Two Pointers (Optimized)

Fix two numbers and sweep the other two from both ends, skipping duplicates at every level. This is the version implemented in the repository.

```java
public static List<List<Integer>> fourSum(int[] nums, int target) {
    List<List<Integer>> result = new ArrayList<>();
    int[] sorted = nums.clone();
    Arrays.sort(sorted);
    int n = sorted.length;
    for (int i = 0; i < n - 3; i++) {
        if (i > 0 && sorted[i] == sorted[i - 1]) continue;
        for (int j = i + 1; j < n - 2; j++) {
            if (j > i + 1 && sorted[j] == sorted[j - 1]) continue;
            int left = j + 1;
            int right = n - 1;
            while (left < right) {
                long sum = (long) sorted[i] + sorted[j] + sorted[left] + sorted[right];
                if (sum < target) {
                    left++;
                } else if (sum > target) {
                    right--;
                } else {
                    result.add(Arrays.asList(sorted[i], sorted[j], sorted[left], sorted[right]));
                    left++;
                    right--;
                    while (left < right && sorted[left] == sorted[left - 1]) left++;
                    while (left < right && sorted[right] == sorted[right + 1]) right--;
                }
            }
        }
    }
    return result;
}
```

- **Time Complexity:** O(n^3)
- **Space Complexity:** O(1) besides the sort and the output

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force with a Set | O(n^4) | O(k) for the k distinct quadruplets |
| 2 | Sort and Two Pointers (Optimized) | O(n^3) | O(1) besides the sort and the output |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### FourSum.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FourSum {

    public static void main(String[] args) {
        int[][] inputs = {{1, 0, -1, 0, -2, 2}, {2, 2, 2, 2, 2}, {1000000000, 1000000000, 1000000000, 1000000000}};
        int[] targets = {0, 8, -294967296};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(inputs[i]) + ", target = " + targets[i]);
            System.out.println("Output : " + fourSum(inputs[i], targets[i]));
            System.out.println();
        }
    }

    public static List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        int n = sorted.length;
        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && sorted[i] == sorted[i - 1]) continue;
            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && sorted[j] == sorted[j - 1]) continue;
                int left = j + 1;
                int right = n - 1;
                while (left < right) {
                    long sum = (long) sorted[i] + sorted[j] + sorted[left] + sorted[right];
                    if (sum < target) {
                        left++;
                    } else if (sum > target) {
                        right--;
                    } else {
                        result.add(Arrays.asList(sorted[i], sorted[j], sorted[left], sorted[right]));
                        left++;
                        right--;
                        while (left < right && sorted[left] == sorted[left - 1]) left++;
                        while (left < right && sorted[right] == sorted[right + 1]) right--;
                    }
                }
            }
        }
        return result;
    }
}
```
