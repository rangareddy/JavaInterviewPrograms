# Maximum Subarray

Given an integer array `nums`, find the contiguous subarray with the largest sum and return that sum.

## Algorithm Strategy (Kadane's Algorithm)

1. Keep `current`, the best sum of a subarray that ends at the current index, and `best`, the best sum seen anywhere.
2. At each element decide whether to extend the previous subarray or start a new one: `current = max(num, current + num)`.
3. A negative running sum can never help a later subarray, which is exactly why the "start new" option is taken in that case.
4. Update `best = max(best, current)` and return it after the last element.

## Example walkthrough

- For `[-2, 1, -3, 4, -1, 2, 1, -5, 4]`: `current` is -2, 1, -2, 4, 3, 5, 6, 1, 5.
- The maximum is `6`, the subarray `[4, -1, 2, 1]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — A single pass.
- **Space Complexity:** `O(1)` — Two variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

Compute the sum of every subarray with a running sum for each start index.

```java
public static int maxSubArrayBrute(int[] nums) {
    int best = Integer.MIN_VALUE;
    for (int i = 0; i < nums.length; i++) {
        int sum = 0;
        for (int j = i; j < nums.length; j++) {
            sum += nums[j];
            best = Math.max(best, sum);
        }
    }
    return best;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Divide and Conquer

The best subarray lies entirely in the left half, entirely in the right half, or crosses the middle. The crossing case is the best suffix sum of the left half plus the best prefix sum of the right half.

```java
public static int maxSubArrayDivide(int[] nums) {
    return solve(nums, 0, nums.length - 1);
}

private static int solve(int[] nums, int low, int high) {
    if (low == high) return nums[low];
    int mid = (low + high) / 2;
    int left = solve(nums, low, mid), right = solve(nums, mid + 1, high);

    int sum = 0, bestLeft = Integer.MIN_VALUE;
    for (int i = mid; i >= low; i--) { sum += nums[i]; bestLeft = Math.max(bestLeft, sum); }
    sum = 0;
    int bestRight = Integer.MIN_VALUE;
    for (int i = mid + 1; i <= high; i++) { sum += nums[i]; bestRight = Math.max(bestRight, sum); }
    return Math.max(Math.max(left, right), bestLeft + bestRight);
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(log n)

### Approach 3: Prefix Sums with the Smallest Earlier Prefix

The sum of a subarray is the difference of two prefix sums, so for every end the best start is the smallest prefix sum before it.

```java
public static int maxSubArrayPrefix(int[] nums) {
    int prefix = 0, minPrefix = 0, best = Integer.MIN_VALUE;
    for (int num : nums) {
        prefix += num;
        best = Math.max(best, prefix - minPrefix);
        minPrefix = Math.min(minPrefix, prefix);
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 4: DP Array

`dp[i]` is the best sum of a subarray that ends at `i`; the answer is the maximum entry. It is Kadane's algorithm with the table written out.

```java
public static int maxSubArrayDp(int[] nums) {
    int[] dp = new int[nums.length];
    dp[0] = nums[0];
    int best = dp[0];
    for (int i = 1; i < nums.length; i++) {
        dp[i] = Math.max(nums[i], dp[i - 1] + nums[i]);
        best = Math.max(best, dp[i]);
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 5: Segment Tuples Combined with a Stream Reduce

Represent every element as a tuple `{sum, bestPrefix, bestSuffix, best}`; two neighbouring tuples combine associatively. Reducing all tuples (which can also run in parallel) gives the answer.

```java
public static int maxSubArrayReduce(int[] nums) {
    return Arrays.stream(nums).mapToObj(x -> new int[]{x, x, x, x}).reduce((a, b) -> combine(a, b)).get()[3];
}

private static int[] combine(int[] a, int[] b) {
    return new int[]{a[0] + b[0],
            Math.max(a[1], a[0] + b[1]),
            Math.max(b[2], b[0] + a[2]),
            Math.max(Math.max(a[3], b[3]), a[2] + b[1])};
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1) (plus tuples)

### Approach 6: Kadane's Algorithm Returning the Subarray

Track the start index of the current subarray so the algorithm returns not only the best sum but also where the best subarray begins and ends.

```java
public static int[] maxSubArrayRange(int[] nums) {          // {sum, start, end}
    int best = nums[0], bestStart = 0, bestEnd = 0, current = nums[0], start = 0;
    for (int i = 1; i < nums.length; i++) {
        if (current < 0) { current = nums[i]; start = i; }
        else current += nums[i];
        if (current > best) { best = current; bestStart = start; bestEnd = i; }
    }
    return new int[]{best, bestStart, bestEnd};
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 7: Kadane's Algorithm (Optimized)

Keep the best sum of a subarray ending at the current index: extend the previous one or start fresh. This is the version implemented in the repository.

```java
public static int maxSubArray(int[] nums) {
    int current = nums[0], best = nums[0];
    for (int i = 1; i < nums.length; i++) {
        current = Math.max(nums[i], current + nums[i]);
        best = Math.max(best, current);
    }
    return best;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) |
| 2 | Divide and Conquer | O(n log n) | O(log n) |
| 3 | Prefix Sums with the Smallest Earlier Prefix | O(n) | O(1) |
| 4 | DP Array | O(n) | O(n) |
| 5 | Segment Tuples Combined with a Stream Reduce | O(n) | O(1) (plus tuples) |
| 6 | Kadane's Algorithm Returning the Subarray | O(n) | O(1) |
| 7 | Kadane's Algorithm (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

## Source Code

### MaximumSubarray.java

```java
import java.util.Arrays;

public class MaximumSubarray {

    public static void main(String[] args) {
        int[][] inputs = {{-2, 1, -3, 4, -1, 2, 1, -5, 4}, {1}, {5, 4, -1, 7, 8}, {-3, -2, -5}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + maxSubArray(nums));
            System.out.println();
        }
    }

    public static int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty.");
        }

        int current = nums[0];
        int best = nums[0];
        for (int i = 1; i < nums.length; i++) {
            current = Math.max(nums[i], current + nums[i]);
            best = Math.max(best, current);
        }
        return best;
    }
}
```
