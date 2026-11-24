package com.ranga.tree;

/**
 * Given the root of a binary tree and an integer {@code targetSum}, return {@code true} if the tree has a root-to-leaf path whose node values add up to {@code targetSum}, and {@code false} otherwise. A leaf is a node with no children.
 *
 * <p><b>Algorithm Strategy (DFS with Remaining Sum):</b>
 * <ol>
 *   <li>Subtract each node's value from the target as you walk down.</li>
 *   <li>At a leaf, the path matches exactly when the remaining target equals the leaf's value.</li>
 *   <li>For an inner node, the answer is true if either child subtree can reach the reduced target.</li>
 *   <li>An empty tree has no path, so it returns {@code false}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the tree {@code [5,4,8,11,null,13,4,7,2,null,null,null,1]} and target 22: the path 5 -> 4 -> 11 -> 2 adds to 22.</li>
 *   <li>Result: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Every node is visited at most once.</li>
 *   <li><b>Space Complexity:</b> {@code O(h)} — Recursion depth equals the tree height.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PathSum {

    public static void main(String[] args) {
        Object[][] inputs = {
            {new Integer[]{5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1}, 22},
            {new Integer[]{1, 2, 3}, 5},
            {new Integer[]{1, 2}, 1},
            {new Integer[]{}, 0}
        };
        for (Object[] in : inputs) {
            TreeNode root = TreeNode.of((Integer[]) in[0]);
            int target = (Integer) in[1];
            System.out.println("Input  : root = " + TreeNode.toString(root) + ", targetSum = " + target);
            System.out.println("Output : " + hasPathSum(root, target));
            System.out.println();
        }
    }

    public static boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        int remaining = targetSum - root.val;
        if (root.left == null && root.right == null) {
            return remaining == 0;
        }
        return hasPathSum(root.left, remaining) || hasPathSum(root.right, remaining);
    }
}
