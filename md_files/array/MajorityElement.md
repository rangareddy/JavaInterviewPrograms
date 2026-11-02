# Majority Element

Utility class to find the majority element in an array using Boyer-Moore Voting Algorithm.

## Algorithm Strategy (Boyer-Moore Voting)

1. A majority element appears more than `n / 2` times, so it outnumbers all other elements combined.
2. Maintain a `candidate` element and a `count`, both starting from the first element.
3. Iterate through the array. When `count` is 0, adopt the current element as the new candidate.
4. If the current element equals the candidate, increment `count`; otherwise decrement it (a pair of different elements cancels out).
5. Because the majority element has more occurrences than all the others together, it cannot be fully cancelled and is the final candidate. (This assumes a majority element exists; otherwise verify with a second counting pass.)

## Example walkthrough

- For `[2, 2, 1, 2, 1, 1, 2]`:
- candidate/count after each element: 2/1, 2/2, 2/1, 2/2, 2/1, 2/0, then count is 0 so the last element 2 becomes the candidate with count 1.
- Result: `2`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Single linear pass over the array.
- **Space Complexity:** `O(1)` — Constant extra space used for pointers and counters.

## Approaches

The same problem can be solved in several ways. They are listed from the simplest to the most efficient.

### Approach 1: Brute Force (Count Every Element)

For every element count its occurrences with an inner loop; the first one that appears more than `n / 2` times is the majority element.

```java
public static int majorityBrute(int[] nums) {
    for (int num : nums) {
        int count = 0;
        for (int other : nums) {
            if (other == num) count++;
        }
        if (count > nums.length / 2) return num;
    }
    throw new IllegalArgumentException("No majority element");
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Hash Map of Counts

Count the occurrences of each value in a map and return the value whose count exceeds `n / 2`.

```java
public static int majorityMap(int[] nums) {
    Map<Integer, Integer> counts = new HashMap<>();
    for (int num : nums) {
        int count = counts.merge(num, 1, Integer::sum);
        if (count > nums.length / 2) return num;
    }
    throw new IllegalArgumentException("No majority element");
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Sorting

If a value appears more than `n / 2` times, then after sorting it must occupy the middle position `n / 2`.

```java
public static int majoritySort(int[] nums) {
    int[] copy = nums.clone();
    Arrays.sort(copy);
    return copy[copy.length / 2];
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n) (O(1) if the input may be modified)

### Approach 4: Boyer-Moore Voting (Optimized)

Pair off different elements; since the majority element outnumbers all others combined, it is the one left. The version implemented in the repository.

```java
public static int majorityElement(int[] nums) {
    int count = 0;
    int candidate = nums[0];
    for (int num : nums) {
        if (count == 0) candidate = num;
        count += (num == candidate) ? 1 : -1;
    }
    return candidate;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force (Count Every Element) | O(n^2) | O(1) |
| 2 | Hash Map of Counts | O(n) | O(n) |
| 3 | Sorting | O(n log n) | O(n) (O(1) if the input may be modified) |
| 4 | Boyer-Moore Voting (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### MajorityElement.java

```java
import java.util.Arrays;

public class MajorityElement {

    public static void main(String[] args) {
        int[] input1 = {3, 2, 3};
        System.out.println("Input  : " + Arrays.toString(input1));
        System.out.println("Output : " + majorityElement(input1)); // Output: 3

        System.out.println();

        int[] input2 = {2, 2, 1, 2, 1, 1, 2};
        System.out.println("Input  : " + Arrays.toString(input2));
        System.out.println("Output : " + majorityElement(input2)); // Output: 2
    }

    /**
     * Finds the majority element that appears more than {@code n / 2} times in the array.
     * Assumes that a majority element always exists in the input array.
     *
     * @param nums the input integer array
     * @return the majority element
     * @throws IllegalArgumentException if the array is null or empty
     */
    public static int majorityElement(int[] nums) {
        // Guard Clause: prevent ArrayIndexOutOfBoundsException on empty or null arrays
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty.");
        }

        // Phase 1: Find the candidate using Boyer-Moore Voting
        int count = 0;
        int candidate = nums[0];

        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate) ? 1 : -1;
        }

        return candidate;
    }
}
```
