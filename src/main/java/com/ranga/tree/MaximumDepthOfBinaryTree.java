package com.ranga.tree;

/**
 * Given the root of a binary tree, return its maximum depth: the number of nodes along the longest path from the root down to the farthest leaf.
 *
 * <p><b>Algorithm Strategy (Recursion (Depth-First Search)):</b>
 * <ol>
 *   <li>The depth of an empty tree is 0.</li>
 *   <li>The depth of any other tree is 1 (for the root) plus the larger of the depths of its left and right subtrees.</li>
 *   <li>Recurse on both children and combine the results with {@code Math.max}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [3, 9, 20, null, null, 15, 7]}: node 9 has depth 1; node 20 has depth 2 (via 15 or 7).</li>
 *   <li>The root gives {@code 1 + max(1, 2) = 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Every node is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(h)} — Recursion depth equals the height {@code h} of the tree ({@code O(n)} for a skewed tree).</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MaximumDepthOfBinaryTree {

    public static void main(String[] args) {
        TreeNode[] inputs = {TreeNode.of(3, 9, 20, null, null, 15, 7), TreeNode.of(1, null, 2), TreeNode.of()};
        for (TreeNode root : inputs) {
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + maxDepth(root));
            System.out.println();
        }
    }

    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }
}
