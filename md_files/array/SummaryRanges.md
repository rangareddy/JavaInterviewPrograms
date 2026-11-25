# Summary Ranges

Given a sorted array of unique integers `nums`, return the smallest sorted list of ranges that cover all the numbers exactly. Each range `[a, b]` is written as `"a->b"` if `a != b` and as `"a"` if `a == b`.

## Algorithm Strategy (Scan for Breaks in Consecutiveness)

1. Walk the array with an index `i` marking the start of a range.
2. Advance `j` while `nums[j + 1] == nums[j] + 1`; use `long` arithmetic to avoid overflow near the integer limits.
3. Add `nums[i]` alone when the run has one number, or `nums[i]->nums[j]` otherwise.
4. Continue from `j + 1`.

## Example walkthrough

- For `[0, 1, 2, 4, 5, 7]`: runs are 0 to 2, 4 to 5 and 7.
- Result: `[0->2, 4->5, 7]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each number is visited once.
- **Space Complexity:** `O(1)` — Apart from the output.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Mark Starts then Build

First find all range starts and ends in two lists, then build the strings.

```java
public static java.util.List<String> twoPass(int[] a) {
    java.util.List<String> out = new java.util.ArrayList<>(); java.util.List<Integer> st = new java.util.ArrayList<>(), en = new java.util.ArrayList<>();
    for (int i = 0; i < a.length; i++) { if (i == 0 || (long) a[i] != (long) a[i - 1] + 1) st.add(a[i]); if (i == a.length - 1 || (long) a[i + 1] != (long) a[i] + 1) en.add(a[i]); }
    for (int k = 0; k < st.size(); k++) out.add(st.get(k).equals(en.get(k)) ? String.valueOf(st.get(k)) : st.get(k) + "->" + en.get(k)); return out;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Single Pass with Two Indexes (Optimized)

Extend a run until the next number breaks it. This is the version implemented in the repository.

```java
public static java.util.List<String> onePass(int[] a) {
    java.util.List<String> out = new java.util.ArrayList<>(); int i = 0; while (i < a.length) { int j = i; while (j + 1 < a.length && (long) a[j + 1] == (long) a[j] + 1) j++; out.add(i == j ? "" + a[i] : a[i] + "->" + a[j]); i = j + 1; } return out;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Mark Starts then Build | O(n) | O(n) |
| 2 | Single Pass with Two Indexes (Optimized) | O(n) | O(1) |

## Source Code

### SummaryRanges.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SummaryRanges {

    public static void main(String[] args) {
        int[][] inputs = {{0, 1, 2, 4, 5, 7}, {0, 2, 3, 4, 6, 8, 9}, {}, {-1}, {Integer.MAX_VALUE - 1, Integer.MAX_VALUE}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + summaryRanges(nums));
            System.out.println();
        }
    }

    public static List<String> summaryRanges(int[] nums) {
        List<String> ranges = new ArrayList<>();
        if (nums == null) {
            return ranges;
        }
        int i = 0;
        while (i < nums.length) {
            int j = i;
            while (j + 1 < nums.length && (long) nums[j + 1] == (long) nums[j] + 1) {
                j++;
            }
            ranges.add(i == j ? String.valueOf(nums[i]) : nums[i] + "->" + nums[j]);
            i = j + 1;
        }
        return ranges;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: because the numbers are sorted and unique, a run of consecutive values is simply a stretch where each number is one more than the previous. Extend the stretch greedily, write it down, and restart after it.

**Input:** `nums = [0, 1, 2, 4, 5, 7]`.

| Start `i` | Run end `j` | Range written |
|-----------|-------------|---------------|
| 0 | 2 | `0->2` |
| 3 | 4 | `4->5` |
| 5 | 5 | `7` |

**Output:** `[0->2, 4->5, 7]`.
