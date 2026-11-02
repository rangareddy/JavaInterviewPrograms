# Product of Array Except Self

Utility class to compute the product of array except self without using division.

## Algorithm Strategy (Prefix & Suffix Products)

1. Without division, the product of all elements except `nums[i]` equals (product of everything to its left) x (product of everything to its right).
2. Left pass: keep a running `prefix` (starting at 1). For each `i`, store `answer[i] = prefix`, then multiply `prefix` by `nums[i]`.
3. Right pass: keep a running `suffix` (starting at 1). Iterate `i` from the end; multiply `answer[i]` by `suffix`, then multiply `suffix` by `nums[i]`.
4. The output array is reused to hold the prefix products, so no extra arrays are needed; zeros are handled naturally.

## Example walkthrough

- For `[1, 2, 3, 4]`:
- After the left pass: `answer = [1, 1, 2, 6]`.
- Right pass multiplies by suffixes 1, 4, 12, 24 (from the right): `[24, 12, 8, 6]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Two linear passes over the array.
- **Space Complexity:** `O(1)` auxiliary space (excluding the output array).

## Approaches

The same problem can be solved in several ways. They are listed from the simplest to the most efficient.

### Approach 1: Brute Force

For every index multiply all the other elements together.

```java
public static int[] productBrute(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    for (int i = 0; i < n; i++) {
        int product = 1;
        for (int j = 0; j < n; j++) {
            if (i != j) product *= nums[j];
        }
        result[i] = product;
    }
    return result;
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1) auxiliary

### Approach 2: Division (Handling Zeros)

Compute the product of all non-zero elements and count the zeros. With two or more zeros every answer is 0; with exactly one zero only the position of that zero is non-zero; otherwise divide the total by `nums[i]`. Not allowed when division is forbidden and it needs special cases.

```java
public static int[] productDivision(int[] nums) {
    int product = 1, zeros = 0;
    for (int v : nums) {
        if (v == 0) zeros++; else product *= v;
    }
    int[] result = new int[nums.length];
    for (int i = 0; i < nums.length; i++) {
        if (zeros > 1) result[i] = 0;
        else if (zeros == 1) result[i] = (nums[i] == 0) ? product : 0;
        else result[i] = product / nums[i];
    }
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1) auxiliary

### Approach 3: Left and Right Product Arrays

Build `left[i]` (product of everything before `i`) and `right[i]` (product of everything after `i`) in two arrays and multiply them.

```java
public static int[] productLeftRight(int[] nums) {
    int n = nums.length;
    int[] left = new int[n], right = new int[n], result = new int[n];
    left[0] = 1;
    for (int i = 1; i < n; i++) left[i] = left[i - 1] * nums[i - 1];
    right[n - 1] = 1;
    for (int i = n - 2; i >= 0; i--) right[i] = right[i + 1] * nums[i + 1];
    for (int i = 0; i < n; i++) result[i] = left[i] * right[i];
    return result;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Prefix and Suffix in the Output Array (Optimized)

Store the prefix products directly in the output array, then multiply by a running suffix product from the right. No division and no extra arrays; implemented in the repository.

```java
public static int[] getProductOfArray(int[] nums) {
    int n = nums.length;
    int[] answer = new int[n];

    int prefix = 1;
    for (int i = 0; i < n; i++) {
        answer[i] = prefix;
        prefix *= nums[i];
    }

    int suffix = 1;
    for (int i = n - 1; i >= 0; i--) {
        answer[i] *= suffix;
        suffix *= nums[i];
    }
    return answer;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1) auxiliary (excluding the output)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Brute Force | O(n^2) | O(1) auxiliary |
| 2 | Division (Handling Zeros) | O(n) | O(1) auxiliary |
| 3 | Left and Right Product Arrays | O(n) | O(n) |
| 4 | Prefix and Suffix in the Output Array (Optimized) | O(n) | O(1) auxiliary (excluding the output) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

### ProductOfArrayExceptSelf.java

```java
import java.util.Arrays;

public class ProductOfArrayExceptSelf {

    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3, 4};
        System.out.println("Input  : " + Arrays.toString(arr1));
        System.out.println("Output : " + Arrays.toString(getProductOfArray(arr1)));

        System.out.println();

        int[] arr2 = {1, 2, 3, 4, 0};
        System.out.println("Input  : " + Arrays.toString(arr2));
        System.out.println("Output : " + Arrays.toString(getProductOfArray(arr2)));
    }

    /**
     * Computes an array such that {@code result[i]} is equal to the product of all
     * the elements of {@code arr} except {@code arr[i]}.
     *
     * @param nums the input integer array
     * @return an array containing the product of array except self
     */
    private static int[] getProductOfArray(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return nums;
        }
        int n = nums.length;
        int[] answer = new int[n];

        // 1. Left pass: store prefix products in 'answer'
        int prefix = 1;
        for (int i = 0; i < n; i++) {
            answer[i] = prefix;
            prefix *= nums[i];
        }

        // 2. Right pass: multiply suffix products with existing prefix products
        int suffix = 1;
        for (int i = n - 1; i >= 0; i--) {
            answer[i] *= suffix;
            suffix *= nums[i];
        }

        return answer;
    }

}
```
