# Meeting Scheduler

Given the availability slots `slots1` and `slots2` of two people (each slot `[start, end]`, with no overlap inside one person's list) and a meeting duration, return the earliest time slot that works for both and has exactly that duration, or an empty array if there is none.

## Algorithm Strategy (Sort Both Lists and Intersect with Two Pointers)

1. Sort both lists by start time.
2. Compare the current slot of each person: the common window is `[max(start1, start2), min(end1, end2)]`.
3. If the common window is at least `duration` long, return `[start, start + duration]`.
4. Otherwise advance the pointer of the slot that ends first.

## Example walkthrough

- For `slots1 = [[10,50],[60,120],[140,210]]`, `slots2 = [[0,15],[60,70]]` and duration 8: the first common window of 8 minutes or more is `[60, 70]`.
- Result: `[60, 68]`.

## Complexity Analysis

- **Time Complexity:** `O(m log m + n log n)` — Sorting dominates; the scan is linear.
- **Space Complexity:** `O(1)` — Apart from sorting.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Compare Every Pair of Slots

Intersect each slot of one person with each slot of the other and keep the earliest that fits.

```java
public static int[] allPairs(int[][] a, int[][] b, int d) {
    int[] best = new int[0];
    for (int[] x : a) for (int[] y : b) { int s = Math.max(x[0], y[0]), e = Math.min(x[1], y[1]); if (e - s >= d && (best.length == 0 || s < best[0])) best = new int[]{s, s + d}; }
    return best;
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(1)

### Approach 2: Sort and Two Pointers (Optimized)

Walk both sorted lists and advance the slot that ends first. This is the version implemented in the repository.

```java
public static int[] twoPointers(int[][] in1, int[][] in2, int d) {
    int[][] a = in1.clone(), b = in2.clone(); java.util.Arrays.sort(a, (x, y) -> x[0] - y[0]); java.util.Arrays.sort(b, (x, y) -> x[0] - y[0]); int i = 0, j = 0;
    while (i < a.length && j < b.length) { int s = Math.max(a[i][0], b[j][0]), e = Math.min(a[i][1], b[j][1]); if (e - s >= d) return new int[]{s, s + d}; if (a[i][1] < b[j][1]) i++; else j++; }
    return new int[0];
}
```

- **Time Complexity:** O(m log m + n log n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Compare Every Pair of Slots | O(m * n) | O(1) |
| 2 | Sort and Two Pointers (Optimized) | O(m log m + n log n) | O(1) |

## Source Code

### MeetingScheduler.java

```java
import java.util.Arrays;

public class MeetingScheduler {

    public static void main(String[] args) {
        int[][][] first = {{{10, 50}, {60, 120}, {140, 210}}, {{10, 50}, {60, 120}, {140, 210}}, {{5, 9}}, {{1, 4}}};
        int[][][] second = {{{0, 15}, {60, 70}}, {{0, 15}, {60, 70}}, {{1, 4}}, {{1, 4}}};
        int[] durations = {8, 12, 2, 3};
        for (int i = 0; i < first.length; i++) {
            System.out.println("Input  : slots1 = " + Arrays.deepToString(first[i]) + ", slots2 = " + Arrays.deepToString(second[i]) + ", duration = " + durations[i]);
            System.out.println("Output : " + Arrays.toString(minAvailableDuration(first[i].clone(), second[i].clone(), durations[i])));
            System.out.println();
        }
    }

    public static int[] minAvailableDuration(int[][] slots1, int[][] slots2, int duration) {
        if (slots1 == null || slots2 == null) {
            return new int[0];
        }
        Arrays.sort(slots1, (a, b) -> a[0] - b[0]);
        Arrays.sort(slots2, (a, b) -> a[0] - b[0]);
        int i = 0;
        int j = 0;
        while (i < slots1.length && j < slots2.length) {
            int start = Math.max(slots1[i][0], slots2[j][0]);
            int end = Math.min(slots1[i][1], slots2[j][1]);
            if (end - start >= duration) {
                return new int[]{start, start + duration};
            }
            if (slots1[i][1] < slots2[j][1]) {
                i++;
            } else {
                j++;
            }
        }
        return new int[0];
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: two people can meet only inside the overlap of their slots. Sorted by start time, one pointer per person walks the slots, and the slot that ends first can never overlap anything later, so it is dropped.

**Input:** `slots1 = [[10, 50], [60, 120], [140, 210]]`, `slots2 = [[0, 15], [60, 70]]`, `duration = 8`.

| Slot 1 | Slot 2 | Overlap | Length | Fits 8? |
|--------|--------|---------|--------|---------|
| [10,50] | [0,15] | [10,15] | 5 | no, advance slot 2 |
| [10,50] | [60,70] | none | | no, advance slot 1 |
| [60,120] | [60,70] | [60,70] | 10 | yes |

**Output:** `[60, 68]`.
