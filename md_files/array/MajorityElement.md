# Majority Element

Utility class to find the majority element in an array using Boyer-Moore Voting Algorithm.

### Algorithm Strategy (Boyer-Moore Voting)

1. Maintain a `candidate` element and a `count`.
2. Iterate through the array. When `count` reaches 0, choose the current element as the new candidate.
3. If the current element matches the candidate, increment `count`; otherwise, decrement `count`.
4. Due to the definition of a majority element (appearing &gt; n/2 times), the true majority element will always survive as the final candidate.

### Complexity Analysis

- **Time Complexity:** `O(n)` — Single linear pass over the array.
- **Space Complexity:** `O(1)` — Constant extra space used for pointers and counters.

### MajorityElement.java

```java
import java.util.Arrays;

/**
 * Utility class to find the majority element in an array using
 * Boyer-Moore Voting Algorithm.
 *
 * <p><b>Algorithm Strategy (Boyer-Moore Voting):</b>
 * <ol>
 *   <li>Maintain a {@code candidate} element and a {@code count}.</li>
 *   <li>Iterate through the array. When {@code count} reaches 0, choose the
 *       current element as the new candidate.</li>
 *   <li>If the current element matches the candidate, increment {@code count};
 *       otherwise, decrement {@code count}.</li>
 *   <li>Due to the definition of a majority element (appearing &gt; n/2 times),
 *       the true majority element will always survive as the final candidate.</li>
 * </ol>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Single linear pass over the array.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Constant extra space used for pointers and counters.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MajorityElement {

    public static void main(String[] args) {
        int[] input1 = {3, 2, 3};
        System.out.println("Input: " + Arrays.toString(input1));
        System.out.println("Output: " + majorityElement(input1)); // Output: 3

        System.out.println();

        int[] input2 = {2, 2, 1, 2, 1, 1, 2};
        System.out.println("Input: " + Arrays.toString(input2));
        System.out.println("Output: " + majorityElement(input2)); // Output: 2
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
