# Median of Two Sorted Arrays

Given two sorted arrays `nums1` and `nums2` of sizes `m` and `n`, return the median of the two sorted arrays. The overall run time complexity should be `O(log(m + n))`.

## Algorithm Strategy (Binary Search for the Partition)

1. Without merging, the median splits all `m + n` numbers into a left half of `(m + n + 1) / 2` numbers and a right half. Binary-search how many numbers `i` of the shorter array `A` go to the left half; the remaining `j = half - i` come from the longer array `B`.
2. A partition is correct when `A[i-1] <= B[j]` and `B[j-1] <= A[i]`: everything on the left is not larger than everything on the right. Use `-infinity` / `+infinity` for missing neighbours at the ends.
3. If `A[i-1] > B[j]`, too many numbers were taken from `A`: search smaller `i`. If `B[j-1] > A[i]`, take more from `A`: search larger `i`.
4. For an odd total the median is the largest number of the left half; for an even total it is the average of that number and the smallest number of the right half.

## Example walkthrough

- For `[1, 2]` and `[3, 4]`: the left half must hold 2 numbers; taking `i = 2` from the first array leaves `j = 0`, and `2 <= 3` and nothing is on the left of B, so the partition is valid.
- Median = `(max(2) + min(3)) / 2 = 2.5`.

## Complexity Analysis

- **Time Complexity:** `O(log(min(m, n)))` — The binary search runs on the shorter array.
- **Space Complexity:** `O(1)` — Index variables only.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Merge and Take the Middle

Merge the two sorted arrays into one array and read the median from the middle. Simple, but linear in time and memory.

```java
public static double findMedianMerge(int[] nums1, int[] nums2) {
    int[] merged = new int[nums1.length + nums2.length];
    int i = 0, j = 0, k = 0;
    while (i < nums1.length && j < nums2.length) merged[k++] = nums1[i] <= nums2[j] ? nums1[i++] : nums2[j++];
    while (i < nums1.length) merged[k++] = nums1[i++];
    while (j < nums2.length) merged[k++] = nums2[j++];
    int n = merged.length;
    return n % 2 == 1 ? merged[n / 2] : (merged[n / 2 - 1] + (double) merged[n / 2]) / 2.0;
}
```

- **Time Complexity:** O(m + n)
- **Space Complexity:** O(m + n)

### Approach 2: Two Pointers up to the Middle

Merge only as far as the middle element(s), keeping the last two merged values instead of the merged array.

```java
public static double findMedianPointers(int[] nums1, int[] nums2) {
    int total = nums1.length + nums2.length, i = 0, j = 0;
    int previous = 0, current = 0;
    for (int step = 0; step <= total / 2; step++) {
        previous = current;
        if (i < nums1.length && (j >= nums2.length || nums1[i] <= nums2[j])) current = nums1[i++];
        else current = nums2[j++];
    }
    return total % 2 == 1 ? current : (previous + (double) current) / 2.0;
}
```

- **Time Complexity:** O(m + n)
- **Space Complexity:** O(1)

### Approach 3: k-th Smallest Element by Halving k

The median is the k-th smallest of the union for `k = (m + n + 1) / 2` (and `k + 1` for an even total). To find the k-th smallest, compare the `k/2`-th elements of both arrays: the smaller one and all elements before it cannot be the answer, so discard them and look for the `(k - k/2)`-th smallest of the rest.

```java
public static double findMedianKth(int[] nums1, int[] nums2) {
    int total = nums1.length + nums2.length;
    if (total % 2 == 1) return kth(nums1, 0, nums2, 0, total / 2 + 1);
    return (kth(nums1, 0, nums2, 0, total / 2) + kth(nums1, 0, nums2, 0, total / 2 + 1)) / 2.0;
}

private static int kth(int[] a, int aStart, int[] b, int bStart, int k) {
    if (aStart >= a.length) return b[bStart + k - 1];
    if (bStart >= b.length) return a[aStart + k - 1];
    if (k == 1) return Math.min(a[aStart], b[bStart]);
    int half = k / 2;
    int aKey = aStart + half - 1 < a.length ? a[aStart + half - 1] : Integer.MAX_VALUE;
    int bKey = bStart + half - 1 < b.length ? b[bStart + half - 1] : Integer.MAX_VALUE;
    if (aKey < bKey) return kth(a, aStart + half, b, bStart, k - half);
    return kth(a, aStart, b, bStart + half, k - half);
}
```

- **Time Complexity:** O(log(m + n))
- **Space Complexity:** O(log(m + n)) recursion stack

### Approach 4: Partition Binary Search on the Shorter Array (Optimized)

Find how many elements of the shorter array belong to the left half of the merged order by binary search; the median is read from the four elements around the partition. This is the version implemented in the repository.

```java
public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
    if (nums1.length > nums2.length) return findMedianSortedArrays(nums2, nums1);
    int m = nums1.length, n = nums2.length, half = (m + n + 1) / 2, low = 0, high = m;
    while (low <= high) {
        int i = low + (high - low) / 2, j = half - i;
        int left1 = i == 0 ? Integer.MIN_VALUE : nums1[i - 1], right1 = i == m ? Integer.MAX_VALUE : nums1[i];
        int left2 = j == 0 ? Integer.MIN_VALUE : nums2[j - 1], right2 = j == n ? Integer.MAX_VALUE : nums2[j];
        if (left1 <= right2 && left2 <= right1) {
            if ((m + n) % 2 == 1) return Math.max(left1, left2);
            return (Math.max(left1, left2) + (double) Math.min(right1, right2)) / 2.0;
        } else if (left1 > right2) high = i - 1; else low = i + 1;
    }
    throw new IllegalArgumentException("Input arrays are not sorted.");
}
```

- **Time Complexity:** O(log(min(m, n)))
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Merge and Take the Middle | O(m + n) | O(m + n) |
| 2 | Two Pointers up to the Middle | O(m + n) | O(1) |
| 3 | k-th Smallest Element by Halving k | O(log(m + n)) | O(log(m + n)) recursion stack |
| 4 | Partition Binary Search on the Shorter Array (Optimized) | O(log(min(m, n))) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### MedianOfTwoSortedArrays.java

```java
import java.util.Arrays;

public class MedianOfTwoSortedArrays {

    public static void main(String[] args) {
        int[][] first = {{1, 3}, {1, 2}, {0, 0}, {}, {2}, {1, 3, 5, 7, 9}};
        int[][] second = {{2}, {3, 4}, {0, 0}, {1}, {}, {2, 4}};
        for (int i = 0; i < first.length; i++) {
            System.out.println("Input  : nums1 = " + Arrays.toString(first[i]) + ", nums2 = " + Arrays.toString(second[i]));
            System.out.println("Output : " + findMedianSortedArrays(first[i], second[i]));
            System.out.println();
        }
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);        // binary search on the shorter array
        }

        int m = nums1.length;
        int n = nums2.length;
        int half = (m + n + 1) / 2;
        int low = 0;
        int high = m;
        while (low <= high) {
            int i = low + (high - low) / 2;         // numbers taken from nums1 into the left half
            int j = half - i;                       // numbers taken from nums2 into the left half

            int left1 = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int right1 = (i == m) ? Integer.MAX_VALUE : nums1[i];
            int left2 = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int right2 = (j == n) ? Integer.MAX_VALUE : nums2[j];

            if (left1 <= right2 && left2 <= right1) {
                if ((m + n) % 2 == 1) {
                    return Math.max(left1, left2);
                }
                return (Math.max(left1, left2) + (double) Math.min(right1, right2)) / 2.0;
            } else if (left1 > right2) {
                high = i - 1;
            } else {
                low = i + 1;
            }
        }
        throw new IllegalArgumentException("Input arrays are not sorted.");
    }
}
```
