package com.ranga.tree;

import java.util.Arrays;

/**
 * Given an integer array sorted in ascending order, convert it to a height-balanced binary search tree, one in which the depths of the two subtrees of every node never differ by more than one.
 *
 * <p><b>Algorithm Strategy (Midpoint Recursion):</b>
 * <ol>
 *   <li>The middle element of a sorted range is the best root: half of the values are smaller, half are larger.</li>
 *   <li>Pick {@code mid = lo + (hi - lo) / 2} as the root of the range {@code [lo, hi]}.</li>
 *   <li>Build the left subtree from {@code [lo, mid - 1]} and the right subtree from {@code [mid + 1, hi]} recursively.</li>
 *   <li>An empty range returns {@code null}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [-10, -3, 0, 5, 9]}: the root is 0, the left range {@code [-10, -3]} gives -10 with -3 as its right child, the right range {@code [5, 9]} gives 5 with 9 as its right child.</li>
 *   <li>Level order: {@code [0, -10, 5, null, -3, null, 9]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Every element becomes exactly one node.</li>
 *   <li><b>Space Complexity:</b> {@code O(log n)} — Recursion depth equals the height of the balanced tree.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ConvertSortedArrayToBST {

    public static void main(String[] args) {
        int[][] inputs = {{-10, -3, 0, 5, 9}, {1, 3}, {7}, {}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + TreeNode.toString(sortedArrayToBST(nums)));
            System.out.println();
        }
    }

    public static TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        return build(nums, 0, nums.length - 1);
    }

    private static TreeNode build(int[] nums, int lo, int hi) {
        if (lo > hi) {
            return null;
        }
        int mid = lo + (hi - lo) / 2;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = build(nums, lo, mid - 1);
        node.right = build(nums, mid + 1, hi);
        return node;
    }
}
