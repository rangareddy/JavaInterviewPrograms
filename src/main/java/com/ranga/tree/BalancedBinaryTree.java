package com.ranga.tree;

/**
 * Given a binary tree, determine whether it is height-balanced: for every node, the heights of its left and right subtrees differ by at most 1.
 *
 * <p><b>Algorithm Strategy (Post-order Height with an Early Exit):</b>
 * <ol>
 *   <li>A tree is balanced if both subtrees are balanced and their heights differ by at most 1.</li>
 *   <li>Compute the height bottom-up with one recursive function that returns the height of a balanced subtree and the special value {@code -1} as soon as an imbalance is found.</li>
 *   <li>At a node: if either child returned {@code -1}, or the heights differ by more than 1, return {@code -1}; otherwise return {@code 1 + max(left, right)}.</li>
 *   <li>The tree is balanced exactly when the root does not return {@code -1}. Every node is visited once.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [3, 9, 20, null, null, 15, 7]}: the subtree of 20 has height 2, the subtree of 9 has height 1, difference 1: {@code true}.</li>
 *   <li>For {@code [1, 2, 2, 3, 3, null, null, 4, 4]} the left subtree has height 4 and the right subtree height 2: {@code false}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Every node is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(h)} — Recursion depth equals the tree height.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BalancedBinaryTree {

    public static void main(String[] args) {
        Integer[][] inputs = {{3, 9, 20, null, null, 15, 7}, {1, 2, 2, 3, 3, null, null, 4, 4}, {}, {1, 2, null, 3}, {1, 2, 3}};
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + isBalanced(root));
            System.out.println();
        }
    }

    public static boolean isBalanced(TreeNode root) {
        return height(root) != -1;
    }

    // height of the subtree, or -1 if it is not balanced
    private static int height(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int left = height(node.left);
        if (left == -1) {
            return -1;
        }
        int right = height(node.right);
        if (right == -1 || Math.abs(left - right) > 1) {
            return -1;
        }
        return 1 + Math.max(left, right);
    }
}
