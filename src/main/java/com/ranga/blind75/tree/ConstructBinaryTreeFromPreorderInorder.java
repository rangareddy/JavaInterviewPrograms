package com.ranga.blind75.tree;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given the pre-order and in-order traversals of a binary tree (with unique values), construct and return the tree.
 *
 * <p><b>Algorithm Strategy (Root from Pre-order, Split by In-order):</b>
 * <ol>
 *   <li>The first element of the pre-order range is the root of that subtree.</li>
 *   <li>Find the root in the in-order array: everything to its left belongs to the left subtree and everything to its right to the right subtree. Store the in-order positions in a {@code HashMap} for {@code O(1)} lookups.</li>
 *   <li>The number of nodes in the left subtree tells where the left part of the pre-order range ends; recurse on the left ranges, then on the right ranges.</li>
 *   <li>A range that is empty returns {@code null}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For preorder {@code [3, 9, 20, 15, 7]} and inorder {@code [9, 3, 15, 20, 7]}: the root is 3; in-order has one node (9) on its left, so 9 is the left subtree.</li>
 *   <li>The rest {@code [20, 15, 7]} / {@code [15, 20, 7]} gives root 20 with children 15 and 7: {@code [3, 9, 20, null, null, 15, 7]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each node is created once and each lookup is {@code O(1)}.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The index map and the recursion stack.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class ConstructBinaryTreeFromPreorderInorder {

    public static void main(String[] args) {
        int[][] preorders = {{3, 9, 20, 15, 7}, {-1}};
        int[][] inorders = {{9, 3, 15, 20, 7}, {-1}};
        for (int i = 0; i < preorders.length; i++) {
            System.out.println("Input  : preorder = " + Arrays.toString(preorders[i]) + ", inorder = " + Arrays.toString(inorders[i]));
            System.out.println("Output : " + TreeNode.toString(buildTree(preorders[i], inorders[i])));
            System.out.println();
        }
    }

    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length) {
            return null;
        }

        Map<Integer, Integer> inorderIndex = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderIndex.put(inorder[i], i);
        }
        return build(preorder, 0, preorder.length - 1, 0, inorderIndex);
    }

    private static TreeNode build(int[] preorder, int preStart, int preEnd, int inStart, Map<Integer, Integer> inorderIndex) {
        if (preStart > preEnd) {
            return null;
        }

        TreeNode root = new TreeNode(preorder[preStart]);
        int rootIndex = inorderIndex.get(root.val);
        int leftSize = rootIndex - inStart;

        root.left = build(preorder, preStart + 1, preStart + leftSize, inStart, inorderIndex);
        root.right = build(preorder, preStart + leftSize + 1, preEnd, rootIndex + 1, inorderIndex);
        return root;
    }
}
