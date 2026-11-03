# Intersection of Two Arrays

Given two integer arrays `nums1` and `nums2`, return their intersection. Each element in the result must be unique (version I) or must appear as many times as it shows in both arrays (version II). The result may be in any order.

## Algorithm Strategy (Hash Set for Unique Values, Hash Map of Counts for Duplicates)

1. Unique version: put all values of `nums1` into a `HashSet`.
2. Scan `nums2`; every value that is in the set belongs to the intersection. Remove it from the set so it is reported only once.
3. Duplicates version: count the values of the smaller array in a `HashMap`.
4. Scan the other array; whenever a value has a positive count, add it to the result and decrement the count.

## Example walkthrough

- For `[1, 2, 2, 1]` and `[2, 2]`: the unique intersection is `[2]`, the one with duplicates is `[2, 2]`.
- For `[4, 9, 5]` and `[9, 4, 9, 8, 4]`: unique `[9, 4]`, with duplicates `[9, 4]`.

## Complexity Analysis

- **Time Complexity:** `O(m + n)` — One pass over each array.
- **Space Complexity:** `O(min(m, n))` — The map or set of the smaller array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Nested Loops

Check every element of `nums1` against every element of `nums2`, using a used-flag array so that each match is consumed once.

```java
public static int[] intersectionWithDuplicatesBrute(int[] nums1, int[] nums2) {
    boolean[] used = new boolean[nums2.length];
    List<Integer> result = new ArrayList<>();
    for (int a : nums1)
        for (int j = 0; j < nums2.length; j++)
            if (!used[j] && nums2[j] == a) { used[j] = true; result.add(a); break; }
    return result.stream().mapToInt(Integer::intValue).toArray();
}
```

- **Time Complexity:** O(m * n)
- **Space Complexity:** O(n)

### Approach 2: Sort and Two Pointers

Sort both arrays and advance two pointers; equal values are matched pairwise (suits arrays that are already sorted or stored on disk).

```java
public static int[] intersectionWithDuplicatesSorted(int[] nums1, int[] nums2) {
    int[] a = nums1.clone(), b = nums2.clone();
    Arrays.sort(a); Arrays.sort(b);
    List<Integer> result = new ArrayList<>();
    int i = 0, j = 0;
    while (i < a.length && j < b.length) {
        if (a[i] < b[j]) i++;
        else if (a[i] > b[j]) j++;
        else { result.add(a[i]); i++; j++; }
    }
    return result.stream().mapToInt(Integer::intValue).toArray();
}
```

- **Time Complexity:** O(m log m + n log n)
- **Space Complexity:** O(1) extra besides the result

### Approach 3: Stream Filter on a Set (unique values)

Put one array in a set and filter the other one with a stream; `distinct()` keeps each value once.

```java
public static int[] intersectionUniqueStream(int[] nums1, int[] nums2) {
    Set<Integer> set = Arrays.stream(nums1).boxed().collect(Collectors.toSet());
    return Arrays.stream(nums2).filter(set::contains).distinct().toArray();
}
```

- **Time Complexity:** O(m + n)
- **Space Complexity:** O(m)

### Approach 4: Hash Set and Hash Map (Optimized)

A set gives the unique intersection, a map of counts gives the version with duplicates. This is the version implemented in the repository.

```java
public static int[] intersectionUnique(int[] nums1, int[] nums2) {
    Set<Integer> seen = new HashSet<>();
    for (int num : nums1) seen.add(num);
    List<Integer> result = new ArrayList<>();
    for (int num : nums2) if (seen.remove(num)) result.add(num);
    return result.stream().mapToInt(Integer::intValue).toArray();
}

public static int[] intersectionWithDuplicates(int[] nums1, int[] nums2) {
    if (nums1.length > nums2.length) return intersectionWithDuplicates(nums2, nums1);
    Map<Integer, Integer> counts = new HashMap<>();
    for (int num : nums1) counts.merge(num, 1, Integer::sum);
    List<Integer> result = new ArrayList<>();
    for (int num : nums2) {
        int count = counts.getOrDefault(num, 0);
        if (count > 0) { result.add(num); counts.put(num, count - 1); }
    }
    return result.stream().mapToInt(Integer::intValue).toArray();
}
```

- **Time Complexity:** O(m + n)
- **Space Complexity:** O(min(m, n))

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Nested Loops | O(m * n) | O(n) |
| 2 | Sort and Two Pointers | O(m log m + n log n) | O(1) extra besides the result |
| 3 | Stream Filter on a Set (unique values) | O(m + n) | O(m) |
| 4 | Hash Set and Hash Map (Optimized) | O(m + n) | O(min(m, n)) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### IntersectionOfTwoArrays.java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IntersectionOfTwoArrays {

    public static void main(String[] args) {
        int[][] first = {{1, 2, 2, 1}, {4, 9, 5}, {1, 2, 3}};
        int[][] second = {{2, 2}, {9, 4, 9, 8, 4}, {4, 5, 6}};
        for (int i = 0; i < first.length; i++) {
            System.out.println("Input  : nums1 = " + Arrays.toString(first[i]) + ", nums2 = " + Arrays.toString(second[i]));
            System.out.println("Output : unique = " + Arrays.toString(intersectionUnique(first[i], second[i]))
                    + ", with duplicates = " + Arrays.toString(intersectionWithDuplicates(first[i], second[i])));
            System.out.println();
        }
    }

    public static int[] intersectionUnique(int[] nums1, int[] nums2) {
        Set<Integer> seen = new HashSet<>();
        for (int num : nums1) {
            seen.add(num);
        }

        List<Integer> result = new ArrayList<>();
        for (int num : nums2) {
            if (seen.remove(num)) {
                result.add(num);
            }
        }
        return toArray(result);
    }

    public static int[] intersectionWithDuplicates(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            return intersectionWithDuplicates(nums2, nums1);
        }

        Map<Integer, Integer> counts = new HashMap<>();
        for (int num : nums1) {
            counts.merge(num, 1, Integer::sum);
        }

        List<Integer> result = new ArrayList<>();
        for (int num : nums2) {
            int count = counts.getOrDefault(num, 0);
            if (count > 0) {
                result.add(num);
                counts.put(num, count - 1);
            }
        }
        return toArray(result);
    }

    private static int[] toArray(List<Integer> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }
}
```
