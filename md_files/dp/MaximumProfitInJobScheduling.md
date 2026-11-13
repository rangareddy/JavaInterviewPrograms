# Maximum Profit in Job Scheduling

There are `n` jobs, where job `i` runs from `startTime[i]` to `endTime[i]` and pays `profit[i]`. Return the maximum profit you can earn by choosing a subset of jobs with no two overlapping in time. A job that ends at time `X` does not overlap a job that starts at time `X`.

## Algorithm Strategy (Sort by End Time, DP with Binary Search)

1. Sort the jobs by end time. Let `dp[i]` be the best profit using only the first `i` jobs in that order.
2. For job `i` there are two choices: skip it (`dp[i - 1]`) or take it, in which case the profit is its own plus the best profit among jobs that end no later than its start time.
3. Because the jobs are sorted by end time, the last compatible job can be found with a binary search over the end times (the largest index whose end time is at most this job's start).
4. `dp[i] = max(dp[i - 1], profit[i] + dp[lastCompatible + 1])`. The answer is the last value of `dp`.

## Example walkthrough

- For start `[1, 2, 3, 3]`, end `[3, 4, 5, 6]`, profit `[50, 10, 40, 70]`: sorted by end time the same order holds.
- Taking job 1 (1 to 3, profit 50) then job 4 (3 to 6, profit 70) gives `120`, which beats taking jobs 1 and 3 (`90`).

## Complexity Analysis

- **Time Complexity:** `O(n log n)` — Sorting plus one binary search per job.
- **Space Complexity:** `O(n)` — The sorted jobs and the `dp` array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force over All Subsets

Try every subset of jobs, check that no two overlap and keep the best total. Only usable for a handful of jobs.

```java
public static int jobSchedulingBrute(int[] startTime, int[] endTime, int[] profit) {
    int n = startTime.length, best = 0;
    for (int mask = 0; mask < (1 << n); mask++) {
        boolean ok = true;
        int total = 0;
        for (int i = 0; i < n && ok; i++) {
            if ((mask & (1 << i)) == 0) continue;
            total += profit[i];
            for (int j = i + 1; j < n; j++) {
                if ((mask & (1 << j)) != 0 && startTime[i] < endTime[j] && startTime[j] < endTime[i]) { ok = false; break; }
            }
        }
        if (ok) best = Math.max(best, total);
    }
    return best;
}
```

- **Time Complexity:** O(2^n * n^2)
- **Space Complexity:** O(1)

### Approach 2: Memoized Recursion over Start Time

Sort by start time; for each job either skip it or take it and jump to the first job that starts at or after its end (found by binary search).

```java
public static int jobSchedulingMemo(int[] startTime, int[] endTime, int[] profit) {
    int n = startTime.length;
    int[][] jobs = new int[n][];
    for (int i = 0; i < n; i++) jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
    Arrays.sort(jobs, (a, b) -> Integer.compare(a[0], b[0]));
    int[] memo = new int[n + 1];
    Arrays.fill(memo, -1);
    return best(jobs, 0, memo);
}

private static int best(int[][] jobs, int i, int[] memo) {
    if (i == jobs.length) return 0;
    if (memo[i] >= 0) return memo[i];
    int lo = i + 1, hi = jobs.length;
    while (lo < hi) {
        int mid = (lo + hi) / 2;
        if (jobs[mid][0] >= jobs[i][1]) hi = mid; else lo = mid + 1;
    }
    return memo[i] = Math.max(best(jobs, i + 1, memo), jobs[i][2] + best(jobs, lo, memo));
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 3: Bottom-Up DP over End Times (Optimized)

Sort by end time and let each job look up, by binary search, the best profit finished before it starts. This is the version implemented in the repository.

```java
public static int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
    int n = startTime.length;
    int[][] jobs = new int[n][];
    for (int i = 0; i < n; i++) {
        jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
    }
    Arrays.sort(jobs, (a, b) -> Integer.compare(a[1], b[1]));
    int[] dp = new int[n + 1];
    for (int i = 1; i <= n; i++) {
        int[] job = jobs[i - 1];
        int lo = 0;
        int hi = i - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo + 1) / 2;
            if (jobs[mid - 1][1] <= job[0]) {
                lo = mid;
            } else {
                hi = mid - 1;
            }
        }
        dp[i] = Math.max(dp[i - 1], job[2] + dp[lo]);
    }
    return dp[n];
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force over All Subsets | O(2^n * n^2) | O(1) |
| 2 | Memoized Recursion over Start Time | O(n log n) | O(n) |
| 3 | Bottom-Up DP over End Times (Optimized) | O(n log n) | O(n) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### MaximumProfitInJobScheduling.java

```java
import java.util.Arrays;

public class MaximumProfitInJobScheduling {

    public static void main(String[] args) {
        int[][][] inputs = {
                {{1, 2, 3, 3}, {3, 4, 5, 6}, {50, 10, 40, 70}},
                {{1, 2, 3, 4, 6}, {3, 5, 10, 6, 9}, {20, 20, 100, 70, 60}},
                {{1, 1, 1}, {2, 3, 4}, {5, 6, 4}}
        };
        for (int[][] in : inputs) {
            System.out.println("Input  : startTime = " + Arrays.toString(in[0]) + ", endTime = " + Arrays.toString(in[1])
                    + ", profit = " + Arrays.toString(in[2]));
            System.out.println("Output : " + jobScheduling(in[0], in[1], in[2]));
            System.out.println();
        }
    }

    public static int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[][] jobs = new int[n][];
        for (int i = 0; i < n; i++) {
            jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
        }
        Arrays.sort(jobs, (a, b) -> Integer.compare(a[1], b[1]));
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int[] job = jobs[i - 1];
            int lo = 0;
            int hi = i - 1;
            while (lo < hi) {
                int mid = lo + (hi - lo + 1) / 2;
                if (jobs[mid - 1][1] <= job[0]) {
                    lo = mid;
                } else {
                    hi = mid - 1;
                }
            }
            dp[i] = Math.max(dp[i - 1], job[2] + dp[lo]);
        }
        return dp[n];
    }
}
```
