# Kth Largest Element in an Array

Given an integer array `nums` and an integer `k`, return the `k`-th largest element in sorted order (not the k-th distinct element). Try to solve it without fully sorting the array.

## Algorithm Strategy (Quickselect with a Random Pivot)

1. The `k`-th largest element is the element that would be at index `n - k` if the array were sorted in ascending order.
2. Partition the array around a random pivot with a three-way partition: elements smaller than, equal to and larger than the pivot.
3. If the target index lies in the "equal" block, the pivot is the answer. Otherwise continue only in the side that contains the target index and ignore the other side.
4. On average each round halves the search range, so the total work is linear; the random pivot makes the quadratic worst case extremely unlikely.

## Example walkthrough

- For `[3, 2, 1, 5, 6, 4]` and `k = 2` the target index is `6 - 2 = 4` in sorted order `[1, 2, 3, 4, 5, 6]`.
- Partitioning narrows the range to the element at index 4: `5`.

## Complexity Analysis

- **Time Complexity:** `O(n) average` — O(n^2) in the worst case with unlucky pivots.
- **Space Complexity:** `O(1)` — In-place partitioning on a copy of the array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Sort the Array

Sort a copy in ascending order and read the element `k` places from the end.

```java
public static int findKthLargestSort(int[] nums, int k) {
    int[] sorted = nums.clone();
    Arrays.sort(sorted);
    return sorted[sorted.length - k];
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 2: Min-Heap of Size k

Keep the `k` largest numbers seen so far in a min-heap; its top is the k-th largest. Good when `k` is small or the data arrives as a stream.

```java
public static int findKthLargestHeap(int[] nums, int k) {
    PriorityQueue<Integer> heap = new PriorityQueue<>();
    for (int num : nums) {
        heap.add(num);
        if (heap.size() > k) heap.poll();
    }
    return heap.peek();
}
```

- **Time Complexity:** O(n log k)
- **Space Complexity:** O(k)

### Approach 3: Counting Array for a Bounded Range

When the values lie in a small range, count how often each value occurs and walk down from the largest value until `k` elements have been passed. No comparison sorting at all.

```java
public static int findKthLargestCounting(int[] nums, int k) {
    int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
    for (int num : nums) { min = Math.min(min, num); max = Math.max(max, num); }
    int[] counts = new int[max - min + 1];
    for (int num : nums) counts[num - min]++;
    for (int value = counts.length - 1; value >= 0; value--) {
        k -= counts[value];
        if (k <= 0) return value + min;
    }
    throw new IllegalArgumentException("k is larger than the array");
}
```

- **Time Complexity:** O(n + R) where R is the value range
- **Space Complexity:** O(R)

### Approach 4: Quickselect with a Three-Way Partition (Optimized)

Partition around a random pivot and continue only in the part that contains the target position. Expected linear time, array copied once. This is the version implemented in the repository.

```java
public static int findKthLargest(int[] nums, int k) {
    int[] a = nums.clone();
    int target = a.length - k, low = 0, high = a.length - 1;
    Random random = new Random();
    while (low < high) {
        int pivot = a[low + random.nextInt(high - low + 1)];
        int lt = low, i = low, gt = high;
        while (i <= gt) {
            if (a[i] < pivot) swap(a, lt++, i++);
            else if (a[i] > pivot) swap(a, i, gt--);
            else i++;
        }
        if (target < lt) high = lt - 1;
        else if (target > gt) low = gt + 1;
        else return pivot;
    }
    return a[target];
}

private static void swap(int[] a, int i, int j) { int t = a[i]; a[i] = a[j]; a[j] = t; }
```

- **Time Complexity:** O(n) expected
- **Space Complexity:** O(n) for the copy

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Sort the Array | O(n log n) | O(n) |
| 2 | Min-Heap of Size k | O(n log k) | O(k) |
| 3 | Counting Array for a Bounded Range | O(n + R) where R is the value range | O(R) |
| 4 | Quickselect with a Three-Way Partition (Optimized) | O(n) expected | O(n) for the copy |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

#### Optimized solution explained

The idea: the k-th largest is the element at index `n - k` of the sorted array, and quickselect finds it without sorting everything. Pick a random pivot and do a three-way partition into `< pivot`, `== pivot`, `> pivot`. If the target index lies in the `==` block, the pivot is the answer; otherwise continue only in the side that contains the target. Equal values sit together in the middle, so many duplicates do not slow it down. The class works on a clone, so the input is not changed. Average time is `O(n)`.

**Input:** `nums = [3, 2, 1, 5, 6, 4]`, `k = 2`, so `target = 6 - 2 = 4` (sorted would be `1 2 3 4 5 6`, index 4 is `5`). The pivot is random in the real class; the trace below uses pivots `3` and then `5`, which is one possible run (any pivot ends with the same answer).

| Round | Range `low..high` | Pivot | Array after partition | `lt` | `gt` | Decision |
|-------|-------------------|-------|-----------------------|------|------|----------|
| 1 | 0..5 | 3 | `[2, 1, 3, 6, 4, 5]` | 2 | 2 | `target 4 > gt 2`, so `low = 3` |
| 2 | 3..5 | 5 | `[2, 1, 3, 4, 5, 6]` | 4 | 4 | `lt <= 4 <= gt`, return the pivot `5` |

```text
index:     0  1  2 | 3  4  5
round 1:   2  1  3 | 6  4  5      <3 | ==3 | >3   (target 4 lies on the right)
round 2:               4  5  6    <5 | ==5 | >5   (index 4 is the ==5 block)
```

**Edge cases:** a single element makes `low < high` false, so `a[target]` is returned directly. With `[7, 7, 7, 7]` and `k = 3` the whole array is the `==` block after one partition and `7` is returned.

**Result:** `5`, the same as the class output (verified by running the partition steps above with the fixed pivots; the real class gives `5` for every random choice).

## Source Code

### KthLargestElementInAnArray.java

```java
import java.util.Arrays;
import java.util.Random;

public class KthLargestElementInAnArray {

    public static void main(String[] args) {
        int[][] inputs = {{3, 2, 1, 5, 6, 4}, {3, 2, 3, 1, 2, 4, 5, 5, 6}, {1}, {7, 7, 7, 7}};
        int[] ks = {2, 4, 1, 3};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : nums = " + Arrays.toString(inputs[i]) + ", k = " + ks[i]);
            System.out.println("Output : " + findKthLargest(inputs[i], ks[i]));
            System.out.println();
        }
    }

    public static int findKthLargest(int[] nums, int k) {
        int[] a = nums.clone();                     // the input array stays unchanged
        int target = a.length - k;                  // index of the answer in ascending order
        int low = 0;
        int high = a.length - 1;
        Random random = new Random();
        while (low < high) {
            int pivot = a[low + random.nextInt(high - low + 1)];

            // three-way partition: [low, lt) < pivot, [lt, gt] == pivot, (gt, high] > pivot
            int lt = low;
            int i = low;
            int gt = high;
            while (i <= gt) {
                if (a[i] < pivot) {
                    swap(a, lt++, i++);
                } else if (a[i] > pivot) {
                    swap(a, i, gt--);
                } else {
                    i++;
                }
            }

            if (target < lt) {
                high = lt - 1;
            } else if (target > gt) {
                low = gt + 1;
            } else {
                return pivot;
            }
        }
        return a[target];
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
```
