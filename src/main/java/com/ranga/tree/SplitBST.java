package com.ranga.tree;

/**
 * Given the root of a binary search tree and an integer {@code target}, split the tree into two subtrees: one holds every node with a value less than or equal to {@code target}, the other holds every node with a larger value. Keep the original parent-child structure where possible, and return the two roots as an array.
 *
 * <p><b>Algorithm Strategy (Recursive Split):</b>
 * <ol>
 *   <li>If the node is {@code null}, both parts are {@code null}.</li>
 *   <li>If {@code root.val <= target}, the root and its whole left subtree belong to the smaller part. Split the right subtree; attach its smaller part as the new right child of {@code root}, and return {@code [root, larger part]}.</li>
 *   <li>Otherwise the root and its right subtree belong to the larger part. Split the left subtree; attach its larger part as the new left child of {@code root}, and return {@code [smaller part, root]}.</li>
 *   <li>Only one path from the root is followed, so the work is proportional to the height.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [4,2,6,1,3,5,7]} and {@code target = 2}: the root 4 is larger, so split its left subtree {@code [2,1,3]}; node 2 stays small and its right child 3 moves to the larger side.</li>
 *   <li>Result: {@code [[2,1],[4,3,6,null,null,5,7]]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(h)} — One root-to-leaf path is followed.</li>
 *   <li><b>Space Complexity:</b> {@code O(h)} — Recursion depth equals the tree height.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SplitBST {

    public static void main(String[] args) {
        Object[][] inputs = {
            {new Integer[]{4, 2, 6, 1, 3, 5, 7}, 2},
            {new Integer[]{1}, 1},
            {new Integer[]{4, 2, 6, 1, 3, 5, 7}, 10},
            {new Integer[]{}, 3}
        };
        for (Object[] in : inputs) {
            TreeNode root = TreeNode.of((Integer[]) in[0]);
            int target = (Integer) in[1];
            System.out.println("Input  : root = " + TreeNode.toString(root) + ", target = " + target);
            TreeNode[] parts = splitBST(root, target);
            System.out.println("Output : [" + TreeNode.toString(parts[0]) + ", " + TreeNode.toString(parts[1]) + "]");
            System.out.println();
        }
    }

    public static TreeNode[] splitBST(TreeNode root, int target) {
        if (root == null) {
            return new TreeNode[]{null, null};
        }
        if (root.val <= target) {
            TreeNode[] right = splitBST(root.right, target);
            root.right = right[0];
            return new TreeNode[]{root, right[1]};
        }
        TreeNode[] left = splitBST(root.left, target);
        root.left = left[1];
        return new TreeNode[]{left[0], root};
    }
}
