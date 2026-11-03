package com.ranga.linkedlist;

import java.util.Arrays;

/**
 * Given an array {@code nums} of {@code n + 1} integers where each integer is in the range {@code [1, n]}, exactly one number is repeated (possibly more than twice). Return it without modifying the array and using only constant extra space.
 *
 * <p><b>Algorithm Strategy (Floyd's Cycle Detection on the Index Graph):</b>
 * <ol>
 *   <li>Treat the array as a function graph: index {@code i} points to index {@code nums[i]}. Since the values are in {@code [1, n]}, nothing points to index 0, so walking from index 0 never leaves the array.</li>
 *   <li>Two indexes pointing to the same value (the duplicate) make two edges enter the same node, so the walk must contain a cycle and the entrance of the cycle is the duplicate number.</li>
 *   <li>Phase 1: move {@code slow} one step and {@code fast} two steps from index 0 until they meet inside the cycle.</li>
 *   <li>Phase 2: restart {@code slow} from index 0 and move both pointers one step at a time; they meet at the entrance of the cycle, which is the duplicate.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 3, 4, 2, 2]} the walk from index 0 is {@code 0 -> 1 -> 3 -> 2 -> 4 -> 2 -> ...}; the cycle is entered at index 2.</li>
 *   <li>Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Both phases visit each node a constant number of times.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two pointers.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
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
