# Single Number

In a non-empty array where every element appears exactly twice except for one element that appears once, find that single element. Use linear time and constant extra space.

## Algorithm Strategy (XOR of All Elements)

1. XOR has three properties that make this work: `a ^ a = 0`, `a ^ 0 = a`, and it is commutative and associative, so the order does not matter.
2. XOR every element together. Each value that appears twice cancels itself out to 0.
3. What remains is the XOR of the single value with 0, which is the single value itself.

## Example walkthrough

- For `[4, 1, 2, 1, 2]`: `4 ^ 1 ^ 2 ^ 1 ^ 2 = 4 ^ (1 ^ 1) ^ (2 ^ 2) = 4 ^ 0 ^ 0 = 4`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — One integer.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: HashSet

Add a value the first time it is seen and remove it the second time; the one left is the answer.

```java
public static int singleNumberSet(int[] nums) {
    Set<Integer> seen = new HashSet<>();
    for (int x : nums) {
        if (!seen.remove(x)) seen.add(x);
    }
    return seen.iterator().next();
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Sum of Distinct Values

Twice the sum of the distinct values minus the sum of all values equals the single number.

```java
public static int singleNumberMath(int[] nums) {
    Set<Integer> distinct = new HashSet<>();
    long all = 0;
    for (int x : nums) { distinct.add(x); all += x; }
    long distinctSum = 0;
    for (int x : distinct) distinctSum += x;
    return (int) (2 * distinctSum - all);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: XOR (Optimized)

XOR cancels pairs, so folding the array with `^` leaves the unpaired element in constant space. This is the version implemented in the repository.

```java
public static int singleNumber(int[] nums) {
    int result = 0;
    for (int x : nums) {
        result ^= x;
    }
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | HashSet | O(n) | O(n) |
| 2 | Sum of Distinct Values | O(n) | O(n) |
| 3 | XOR (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### SingleNumber.java

```java
import java.util.Arrays;

public class SingleNumber {

    public static void main(String[] args) {
        int[][] inputs = {{2, 2, 1}, {4, 1, 2, 1, 2}, {1}, {-1, 5, -1}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + singleNumber(nums));
            System.out.println();
        }
    }

    public static int singleNumber(int[] nums) {
        int result = 0;
        for (int x : nums) {
            result ^= x;
        }
        return result;
    }
}
```
