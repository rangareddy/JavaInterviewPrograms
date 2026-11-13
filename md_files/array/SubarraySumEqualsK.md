# Subarray Sum Equals K

Given an array of integers `nums` and an integer `k`, return the total number of contiguous subarrays whose sum equals `k`. The array may contain negative numbers.

## Algorithm Strategy (Prefix Sum with a HashMap)

1. Let `prefix[i]` be the sum of the first `i` elements. A subarray `(j, i]` sums to `k` exactly when `prefix[i] - prefix[j] = k`.
2. Walk through the array keeping the running sum and a map from "prefix sum value" to "how many times it has occurred". Start the map with `{0: 1}` so that subarrays starting at index 0 are counted.
3. At each element add `map[runningSum - k]` to the answer: that is the number of earlier positions that make a subarray ending here sum to `k`.
4. Then record the current running sum in the map. A sliding window does not work here because negative numbers break the "grow or shrink" monotonicity.

## Example walkthrough

- For `[1, 2, 3]` with `k = 3`: running sums are 1, 3, 6.
- At sum 3 the map holds 0, so `3 - 3 = 0` gives one match (`[1, 2]`). At sum 6, `6 - 3 = 3` is in the map once (`[3]`).
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass with constant-time map operations.
- **Space Complexity:** `O(n)` — The map holds up to `n + 1` prefix sums.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

Try every start index and extend the end index, keeping a running sum.

```java
public static int subarraySumBrute(int[] nums, int k) {
    int count = 0;
    for (int i = 0; i < nums.length; i++) {
        int sum = 0;
        for (int j = i; j < nums.length; j++) {
            sum += nums[j];
            if (sum == k) count++;
        }
    }
    return count;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Prefix Sum with HashMap (Optimized)

Count how many earlier prefix sums equal `runningSum - k`. This is the version implemented in the repository.

```java
public static int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> seen = new HashMap<>();
    seen.put(0, 1);
    int sum = 0;
    int count = 0;
    for (int x : nums) {
        sum += x;
        count += seen.getOrDefault(sum - k, 0);
        seen.merge(sum, 1, Integer::sum);
    }
    return count;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) |
| 2 | Prefix Sum with HashMap (Optimized) | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### SubarraySumEqualsK.java

```java
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SubarraySumEqualsK {

    public static void main(String[] args) {
        int[][] inputs = {{1, 1, 1}, {1, 2, 3}, {1, -1, 0}, {3, 4, 7, 2, -3, 1, 4, 2}};
        int[] ks = {2, 3, 0, 7};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(inputs[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + subarraySum(inputs[i], ks[i]));
            System.out.println();
        }
    }

    public static int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> seen = new HashMap<>();
        seen.put(0, 1);
        int sum = 0;
        int count = 0;
        for (int x : nums) {
            sum += x;
            count += seen.getOrDefault(sum - k, 0);
            seen.merge(sum, 1, Integer::sum);
        }
        return count;
    }
}
```
