# Majority Element II

Given an integer array `nums` of size `n`, return all elements that appear more than `n / 3` times.

## Algorithm Strategy (Extended Boyer-Moore Voting)

1. At most two values can appear more than `n / 3` times, because three such values would need more than `n` slots.
2. Keep two candidates with a counter each. A value equal to a candidate increments that counter.
3. A value that matches neither candidate takes over a candidate whose counter is 0; if both counters are positive it decrements both (three different values cancel out).
4. The two survivors are only candidates. A second pass counts them and keeps the ones above `n / 3`.

## Example walkthrough

- For `[1, 1, 1, 3, 3, 2, 2, 2]` the threshold is `8 / 3 = 2`.
- Voting pass: 1 reaches count 3, 3 takes the second slot (count 2), then each 2 cancels one vote from both until the second slot is re-taken by 2.
- Candidates 1 and 2 both occur 3 times, which is more than 2. Result: `[1, 2]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Two linear passes over the array.
- **Space Complexity:** `O(1)` — Two candidates and two counters.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Hash Map of Counts

Count every value and keep those whose count exceeds `n / 3`.

```java
public static List<Integer> majorityElementMap(int[] nums) {
    Map<Integer, Integer> counts = new HashMap<>();
    for (int num : nums) {
        counts.merge(num, 1, Integer::sum);
    }
    List<Integer> result = new ArrayList<>();
    for (Map.Entry<Integer, Integer> e : counts.entrySet()) {
        if (e.getValue() > nums.length / 3) {
            result.add(e.getKey());
        }
    }
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Sorting and Run Lengths

Sort a copy and measure the length of every run of equal values.

```java
public static List<Integer> majorityElementSort(int[] nums) {
    int[] sorted = nums.clone();
    Arrays.sort(sorted);
    List<Integer> result = new ArrayList<>();
    int i = 0;
    while (i < sorted.length) {
        int j = i;
        while (j < sorted.length && sorted[j] == sorted[i]) j++;
        if (j - i > sorted.length / 3) result.add(sorted[i]);
        i = j;
    }
    return result;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n) for the copy

### Approach 3: Extended Boyer-Moore (Optimized)

Track two candidates with counters, then verify both with a second pass. This is the version implemented in the repository.

```java
public static List<Integer> majorityElement(int[] nums) {
    List<Integer> result = new ArrayList<>();
    if (nums.length == 0) {
        return result;
    }
    int candidate1 = nums[0];
    int candidate2 = nums[0];
    int count1 = 0;
    int count2 = 0;
    for (int num : nums) {
        if (num == candidate1) {
            count1++;
        } else if (num == candidate2) {
            count2++;
        } else if (count1 == 0) {
            candidate1 = num;
            count1 = 1;
        } else if (count2 == 0) {
            candidate2 = num;
            count2 = 1;
        } else {
            count1--;
            count2--;
        }
    }
    count1 = 0;
    count2 = 0;
    for (int num : nums) {
        if (num == candidate1) {
            count1++;
        } else if (num == candidate2) {
            count2++;
        }
    }
    if (count1 > nums.length / 3) {
        result.add(candidate1);
    }
    if (count2 > nums.length / 3 && candidate2 != candidate1) {
        result.add(candidate2);
    }
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Hash Map of Counts | O(n) | O(n) |
| 2 | Sorting and Run Lengths | O(n log n) | O(n) for the copy |
| 3 | Extended Boyer-Moore (Optimized) | O(n) | O(1) |

## Source Code

### MajorityElementII.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MajorityElementII {

    public static void main(String[] args) {
        int[][] inputs = {{3, 2, 3}, {1}, {1, 2}, {1, 1, 1, 3, 3, 2, 2, 2}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + majorityElement(nums));
            System.out.println();
        }
    }

    public static List<Integer> majorityElement(int[] nums) {
        List<Integer> result = new ArrayList<>();
        if (nums.length == 0) {
            return result;
        }
        int candidate1 = nums[0];
        int candidate2 = nums[0];
        int count1 = 0;
        int count2 = 0;
        for (int num : nums) {
            if (num == candidate1) {
                count1++;
            } else if (num == candidate2) {
                count2++;
            } else if (count1 == 0) {
                candidate1 = num;
                count1 = 1;
            } else if (count2 == 0) {
                candidate2 = num;
                count2 = 1;
            } else {
                count1--;
                count2--;
            }
        }
        count1 = 0;
        count2 = 0;
        for (int num : nums) {
            if (num == candidate1) {
                count1++;
            } else if (num == candidate2) {
                count2++;
            }
        }
        if (count1 > nums.length / 3) {
            result.add(candidate1);
        }
        if (count2 > nums.length / 3 && candidate2 != candidate1) {
            result.add(candidate2);
        }
        return result;
    }
}
```

## Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

The idea (extended Boyer-Moore): an element appearing more than `n/3` times is at most two values, so keep two candidates with two counters. A number equal to a candidate adds a vote; otherwise it takes a free slot (counter `0`); if both slots are busy it cancels one vote from each candidate (three different numbers wiped out together). The survivors are only candidates, so a second pass counts them and keeps those with count above `n/3`.

**Input:** `nums = [1, 1, 1, 3, 3, 2, 2, 2]` (`n = 8`, threshold `8 / 3 = 2`). Start: `candidate1 = candidate2 = 1`, both counts `0`.

| Step | `num` | Rule used | `candidate1` / `count1` | `candidate2` / `count2` |
|------|-------|-----------|--------------------------|--------------------------|
| 1 | 1 | equals candidate1 | 1 / 1 | 1 / 0 |
| 2 | 1 | equals candidate1 | 1 / 2 | 1 / 0 |
| 3 | 1 | equals candidate1 | 1 / 3 | 1 / 0 |
| 4 | 3 | count2 is 0, take slot 2 | 1 / 3 | 3 / 1 |
| 5 | 3 | equals candidate2 | 1 / 3 | 3 / 2 |
| 6 | 2 | both busy, cancel one vote each | 1 / 2 | 3 / 1 |
| 7 | 2 | both busy, cancel one vote each | 1 / 1 | 3 / 0 |
| 8 | 2 | count2 is 0, take slot 2 | 1 / 1 | 2 / 1 |

Verification pass with candidates `1` and `2`: `1` occurs 3 times and `2` occurs 3 times, both greater than `2`, so both are added. Candidate `3` was dropped; it occurs only twice anyway.

**Edge cases.** An empty array returns `[]`. `[1]` returns `[1]`; `[1, 2]` returns `[1, 2]` (threshold `0`). The check `candidate2 != candidate1` stops the same value being added twice when both slots hold it (both start as `nums[0]`).

**Result:** `[1, 2]`, matching the class output.
