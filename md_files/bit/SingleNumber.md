# Single Number

Given a non-empty array of integers `nums` in which every element appears twice except for one, find the element that appears only once. The solution must use linear time and constant extra space.

## Algorithm Strategy (XOR of All Elements)

1. XOR has three useful properties: `a ^ a = 0`, `a ^ 0 = a`, and it is commutative and associative.
2. XOR all the numbers together. Every pair cancels to 0, whatever its position in the array.
3. The only value left is the number that appears once.

## Example walkthrough

- For `[4, 1, 2, 1, 2]`: `4 ^ 1 ^ 2 ^ 1 ^ 2 = 4 ^ (1 ^ 1) ^ (2 ^ 2) = 4 ^ 0 ^ 0 = 4`.
- Result: `4`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — A single integer.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Nested Loop Counting

Count the occurrences of every element by scanning the array.

```java
public static int singleNumberBrute(int[] nums) {
    for (int i = 0; i < nums.length; i++) {
        int count = 0;
        for (int j = 0; j < nums.length; j++) if (nums[i] == nums[j]) count++;
        if (count == 1) return nums[i];
    }
    return -1;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: HashMap Counts

Count each value and return the one whose count is 1.

```java
public static int singleNumberMap(int[] nums) {
    Map<Integer, Integer> counts = new HashMap<>();
    for (int n : nums) counts.merge(n, 1, Integer::sum);
    for (Map.Entry<Integer, Integer> e : counts.entrySet()) if (e.getValue() == 1) return e.getKey();
    return -1;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: HashSet Toggle with Sums

Twice the sum of the distinct values minus the sum of all values equals the single number.

```java
public static int singleNumberSet(int[] nums) {
    Set<Long> distinct = new HashSet<>();
    long all = 0;
    for (int n : nums) { distinct.add((long) n); all += n; }
    long distinctSum = 0;
    for (long v : distinct) distinctSum += v;
    return (int) (2 * distinctSum - all);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Sort and Compare Neighbours

After sorting, the single number is the first element that differs from both neighbours.

```java
public static int singleNumberSorted(int[] nums) {
    int[] sorted = nums.clone();
    Arrays.sort(sorted);
    for (int i = 0; i < sorted.length; i += 2)
        if (i == sorted.length - 1 || sorted[i] != sorted[i + 1]) return sorted[i];
    return -1;
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(n)

### Approach 5: XOR of All Elements (Optimized)

Pairs cancel under XOR. This is the version implemented in the repository.

```java
public static int singleNumber(int[] nums) {
    int result = 0;
    for (int num : nums) result ^= num;
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Nested Loop Counting | O(n^2) | O(1) |
| 2 | HashMap Counts | O(n) | O(n) |
| 3 | HashSet Toggle with Sums | O(n) | O(n) |
| 4 | Sort and Compare Neighbours | O(n log n) | O(n) |
| 5 | XOR of All Elements (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 5 above, which is also the version implemented in this repository.

## Source Code

### SingleNumber.java

```java
import java.util.Arrays;

public class SingleNumber {

    public static void main(String[] args) {
        int[][] inputs = {{2, 2, 1}, {4, 1, 2, 1, 2}, {1}, {-1, -1, -3}, {7, 3, 5, 3, 5}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + singleNumber(nums));
            System.out.println();
        }
    }

    public static int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }
}
```
