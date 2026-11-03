# Find the Duplicate Number

Given an array `nums` of `n + 1` integers where each integer is in the range `[1, n]`, exactly one number is repeated (possibly more than twice). Return it without modifying the array and using only constant extra space.

## Algorithm Strategy (Floyd's Cycle Detection on the Index Graph)

1. Treat the array as a function graph: index `i` points to index `nums[i]`. Since the values are in `[1, n]`, nothing points to index 0, so walking from index 0 never leaves the array.
2. Two indexes pointing to the same value (the duplicate) make two edges enter the same node, so the walk must contain a cycle and the entrance of the cycle is the duplicate number.
3. Phase 1: move `slow` one step and `fast` two steps from index 0 until they meet inside the cycle.
4. Phase 2: restart `slow` from index 0 and move both pointers one step at a time; they meet at the entrance of the cycle, which is the duplicate.

## Example walkthrough

- For `[1, 3, 4, 2, 2]` the walk from index 0 is `0 -> 1 -> 3 -> 2 -> 4 -> 2 -> ...`; the cycle is entered at index 2.
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Both phases visit each node a constant number of times.
- **Space Complexity:** `O(1)` — Two pointers.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Sort a Copy

Sort a copy of the array; the duplicate is the first value equal to its neighbour.

```java
public static int findDuplicateSort(int[] nums) {
    int[] sorted = nums.clone();
    Arrays.sort(sorted);
    for (int i = 1; i < sorted.length; i++) if (sorted[i] == sorted[i - 1]) return sorted[i];
    return -1;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 2: Hash Set

Insert the numbers into a set; the first number that is already present is the duplicate.

```java
public static int findDuplicateSet(int[] nums) {
    Set<Integer> seen = new HashSet<>();
    for (int num : nums) if (!seen.add(num)) return num;
    return -1;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Binary Search on the Value Range (Pigeonhole)

If more than `mid` numbers are `<= mid`, the duplicate is in `[1, mid]` (pigeonhole principle); otherwise it is in `[mid + 1, n]`. Binary-search the value range, counting in one pass per step. Constant space and the array is not modified.

```java
public static int findDuplicateCounting(int[] nums) {
    int low = 1, high = nums.length - 1;
    while (low < high) {
        int mid = low + (high - low) / 2, count = 0;
        for (int num : nums) if (num <= mid) count++;
        if (count > mid) high = mid; else low = mid + 1;
    }
    return low;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(1)

### Approach 4: Floyd's Cycle Detection (Optimized)

View `i -> nums[i]` as a linked list; the duplicate is the entrance of its cycle, found with the tortoise and hare. Linear time, constant space, array unchanged. This is the version implemented in the repository.

```java
public static int findDuplicate(int[] nums) {
    int slow = 0, fast = 0;
    do {
        slow = nums[slow];
        fast = nums[nums[fast]];
    } while (slow != fast);
    slow = 0;
    while (slow != fast) {
        slow = nums[slow];
        fast = nums[fast];
    }
    return slow;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Sort a Copy | O(n log n) | O(n) |
| 2 | Hash Set | O(n) | O(n) |
| 3 | Binary Search on the Value Range (Pigeonhole) | O(n log n) | O(1) |
| 4 | Floyd's Cycle Detection (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### FindTheDuplicateNumber.java

```java
import java.util.Arrays;

public class FindTheDuplicateNumber {

    public static void main(String[] args) {
        int[][] inputs = {{1, 3, 4, 2, 2}, {3, 1, 3, 4, 2}, {3, 3, 3, 3, 3}, {1, 1}, {2, 5, 9, 6, 9, 3, 8, 9, 7, 1}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + findDuplicate(nums));
            System.out.println();
        }
    }

    public static int findDuplicate(int[] nums) {
        int slow = 0;
        int fast = 0;

        // Phase 1: find a meeting point inside the cycle
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        // Phase 2: the cycle entrance is the duplicate
        slow = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }
}
```
