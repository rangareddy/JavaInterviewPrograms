# 3Sum

Given an integer array `nums`, return all unique triplets `[nums[i], nums[j], nums[k]]` with distinct indices whose sum is 0. The result must not contain duplicate triplets.

## Algorithm Strategy (Sort + Two Pointers)

1. Sort the array so that duplicates are adjacent and two pointers can be used.
2. Fix the first number `nums[i]`. If it is greater than 0, stop: all remaining numbers are positive, so no triplet can sum to 0. Skip it if it equals the previous first number.
3. Place `left = i + 1` and `right = n - 1`. Compute `sum = nums[i] + nums[left] + nums[right]`.
4. If the sum is 0, record the triplet, move both pointers, and skip over equal values on both sides to avoid duplicate triplets. If the sum is negative increase `left`, if positive decrease `right`.

## Example walkthrough

- Sorted `[-4, -1, -1, 0, 1, 2]`: for i = 1 (-1), left = -1 and right = 2 give 0, so `[-1, -1, 2]`; then 0 and 1 give `[-1, 0, 1]`.
- The second -1 is skipped as a first number. Result: `[[-1, -1, 2], [-1, 0, 1]]`.

## Complexity Analysis

- **Time Complexity:** `O(n^2)` — Sorting is `O(n log n)` and each first number does an `O(n)` two-pointer scan.
- **Space Complexity:** `O(1)` — Ignoring the output list and the sort space.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

Try all triplets and use a set of sorted triplets to drop duplicates.

```java
public static List<List<Integer>> threeSumBrute(int[] nums) {
    Set<List<Integer>> result = new LinkedHashSet<>();
    int[] sorted = nums.clone();
    Arrays.sort(sorted);
    for (int i = 0; i < sorted.length; i++) {
        for (int j = i + 1; j < sorted.length; j++) {
            for (int k = j + 1; k < sorted.length; k++) {
                if (sorted[i] + sorted[j] + sorted[k] == 0) {
                    result.add(Arrays.asList(sorted[i], sorted[j], sorted[k]));
                }
            }
        }
    }
    return new ArrayList<>(result);
}
```

- **Time Complexity:** O(n^3)
- **Space Complexity:** O(n) for the set

### Approach 2: Hash Set for the Third Number

Fix the first number, then for each second number look up the required third number in a set of the numbers seen between them.

```java
public static List<List<Integer>> threeSumHash(int[] nums) {
    Set<List<Integer>> result = new LinkedHashSet<>();
    int[] sorted = nums.clone();
    Arrays.sort(sorted);
    for (int i = 0; i < sorted.length - 2; i++) {
        Set<Integer> seen = new HashSet<>();
        for (int j = i + 1; j < sorted.length; j++) {
            int third = -sorted[i] - sorted[j];
            if (seen.contains(third)) {
                result.add(Arrays.asList(sorted[i], third, sorted[j]));
            }
            seen.add(sorted[j]);
        }
    }
    return new ArrayList<>(result);
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(n)

### Approach 3: Sort + Two Pointers (Optimized)

Sort once, fix the first number and sweep two pointers; skipping equal values avoids duplicates without a set. This is the version implemented in the repository.

```java
public static List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    int[] sorted = nums.clone();
    Arrays.sort(sorted);
    int n = sorted.length;
    for (int i = 0; i < n - 2 && sorted[i] <= 0; i++) {
        if (i > 0 && sorted[i] == sorted[i - 1]) continue;
        int left = i + 1, right = n - 1;
        while (left < right) {
            int sum = sorted[i] + sorted[left] + sorted[right];
            if (sum == 0) {
                result.add(Arrays.asList(sorted[i], sorted[left], sorted[right]));
                left++; right--;
                while (left < right && sorted[left] == sorted[left - 1]) left++;
                while (left < right && sorted[right] == sorted[right + 1]) right--;
            } else if (sum < 0) left++;
            else right--;
        }
    }
    return result;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1) auxiliary

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^3) | O(n) for the set |
| 2 | Hash Set for the Third Number | O(n^2) | O(n) |
| 3 | Sort + Two Pointers (Optimized) | O(n^2) | O(1) auxiliary |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### ThreeSum.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSum {

    public static void main(String[] args) {
        int[][] inputs = {{-1, 0, 1, 2, -1, -4}, {0, 1, 1}, {0, 0, 0}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + threeSum(nums));
            System.out.println();
        }
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return result;
        }

        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        int n = sorted.length;

        for (int i = 0; i < n - 2; i++) {
            if (sorted[i] > 0) {
                break;
            }
            if (i > 0 && sorted[i] == sorted[i - 1]) {
                continue;       // skip duplicate first numbers
            }

            int left = i + 1;
            int right = n - 1;
            while (left < right) {
                int sum = sorted[i] + sorted[left] + sorted[right];
                if (sum == 0) {
                    result.add(Arrays.asList(sorted[i], sorted[left], sorted[right]));
                    left++;
                    right--;
                    while (left < right && sorted[left] == sorted[left - 1]) {
                        left++;
                    }
                    while (left < right && sorted[right] == sorted[right + 1]) {
                        right--;
                    }
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return result;
    }
}
```
