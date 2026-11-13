package com.ranga.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Flatten a binary tree in place into a "linked list" that uses the {@code right} pointers: the list must follow the preorder traversal of the tree and every {@code left} pointer must be {@code null}.
 *
 * <p><b>Algorithm Strategy (Splice the Left Subtree into the Right (Constant Extra Space)):</b>
 * <ol>
 *   <li>Walk down from the root with a pointer {@code curr}. If {@code curr} has no left child, just move to {@code curr.right}.</li>
 *   <li>Otherwise find the rightmost node of the left subtree, {@code tail}. In preorder, the right subtree comes right after everything in the left subtree, so attach {@code curr.right} to {@code tail.right}.</li>
 *   <li>Then move the left subtree to the right: {@code curr.right = curr.left} and {@code curr.left = null}.</li>
 *   <li>Continue with {@code curr = curr.right}. Each node is on the path of a "rightmost" search at most once, so the total work is linear and no stack or recursion is needed.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 5, 3, 4, null, 6]}: at 1 the rightmost node of the left subtree (2, 3, 4) is 4, so 5 hangs from 4; 2 becomes the right child of 1.</li>
 *   <li>At 2, the rightmost of its left subtree (3) is 3, so 4 hangs from 3. Result: {@code 1, 2, 3, 4, 5, 6}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Every node is visited a constant number of times.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — No recursion and no extra collections.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class FlattenBinaryTreeToLinkedList {

    public static void main(String[] args) {
        Integer[][] inputs = {{1, 2, 5, 3, 4, null, 6}, {}, {0}, {1, null, 2}};
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            flatten(root);
            System.out.println("Output : " + toList(root));
            System.out.println();
        }
    }

    public static void flatten(TreeNode root) {
        TreeNode curr = root;
        while (curr != null) {
            if (curr.left != null) {
                TreeNode tail = curr.left;
                while (tail.right != null) {
                    tail = tail.right;
                }
                tail.right = curr.right;
                curr.right = curr.left;
                curr.left = null;
            }
            curr = curr.right;
        }
    }

    private static List<Integer> toList(TreeNode root) {
        List<Integer> values = new ArrayList<>();
        for (TreeNode node = root; node != null; node = node.right) {
            values.add(node.val);
        }
        return values;
    }
}
