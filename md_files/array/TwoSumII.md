# Two Sum II - Input Array Is Sorted

Given a 1-indexed array of integers `numbers` sorted in non-decreasing order, find two numbers that add up to `target` and return their 1-based indices `[index1, index2]` with `index1 < index2`. Exactly one solution exists, and the same element cannot be used twice. Use only constant extra space.

## Algorithm Strategy (Two Pointers from Both Ends)

1. Put one pointer at the start (`left = 0`) and one at the end (`right = n - 1`) of the sorted array.
2. Compute `sum = numbers[left] + numbers[right]`. If it equals the target, return the 1-based indices.
3. If the sum is too small, the only way to increase it is to move `left` to a larger number, so increment `left`.
4. If the sum is too large, move `right` to a smaller number, so decrement `right`. Each step discards one number that cannot be part of the answer, so the pointers meet at the solution.

## Example walkthrough

- For `[2, 7, 11, 15]` and target 9: `2 + 15 = 17` is too large, so `right` moves; `2 + 11 = 13` is too large; `2 + 7 = 9`.
- Result: `[1, 2]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each pointer moves at most `n` times in total.
- **Space Complexity:** `O(1)` — Two index variables.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Brute Force

Try every pair of positions and return the first pair that adds up to the target. Ignores the sorted order.

```java
public static int[] twoSumBrute(int[] numbers, int target) {
    for (int i = 0; i < numbers.length; i++) {
        for (int j = i + 1; j < numbers.length; j++) {
            if (numbers[i] + numbers[j] == target) return new int[]{i + 1, j + 1};
        }
    }
    return new int[]{-1, -1};
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 2: Binary Search for the Complement

For every number binary-search its complement `target - numbers[i]` in the part of the array after it. Uses the sorted order but still needs a search per element.

```java
public static int[] twoSumBinary(int[] numbers, int target) {
    for (int i = 0; i < numbers.length; i++) {
        int j = Arrays.binarySearch(numbers, i + 1, numbers.length, target - numbers[i]);
        if (j >= 0) return new int[]{i + 1, j + 1};
    }
    return new int[]{-1, -1};
}
```

- **Time Complexity:** O(n log n)
- **Space Complexity:** O(1)

### Approach 3: Hash Map

The classic Two Sum solution, which ignores the sorted order and uses `O(n)` extra space (the problem asks for constant space).

```java
public static int[] twoSumMap(int[] numbers, int target) {
    Map<Integer, Integer> seen = new HashMap<>();
    for (int i = 0; i < numbers.length; i++) {
        Integer j = seen.get(target - numbers[i]);
        if (j != null) return new int[]{j + 1, i + 1};
        seen.put(numbers[i], i);
    }
    return new int[]{-1, -1};
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Two Pointers (Optimized)

Move a left pointer up when the sum is too small and a right pointer down when it is too large. Linear time and constant space. This is the version implemented in the repository.

```java
public static int[] twoSum(int[] numbers, int target) {
    int left = 0, right = numbers.length - 1;
    while (left < right) {
        int sum = numbers[left] + numbers[right];
        if (sum == target) return new int[]{left + 1, right + 1};
        if (sum < target) left++; else right--;
    }
    return new int[]{-1, -1};
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) |
| 2 | Binary Search for the Complement | O(n log n) | O(1) |
| 3 | Hash Map | O(n) | O(n) |
| 4 | Two Pointers (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### TwoSumII.java

```java
import java.util.Arrays;

public class TwoSumII {

    public static void main(String[] args) {
        int[][] inputs = {{2, 7, 11, 15}, {2, 3, 4}, {-1, 0}, {1, 2, 3, 4, 4, 9, 56, 90}};
        int[] targets = {9, 6, -1, 8};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : numbers = " + Arrays.toString(inputs[i]) + ", target = " + targets[i]);
            System.out.println("Output : " + Arrays.toString(twoSum(inputs[i], targets[i])));
            System.out.println();
        }
    }

    public static int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) {
                return new int[]{left + 1, right + 1};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[]{-1, -1};
    }
}
```
