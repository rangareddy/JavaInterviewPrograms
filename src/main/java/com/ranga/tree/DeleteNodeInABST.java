package com.ranga.tree;

/**
 * Given the root of a binary search tree and a {@code key}, delete the node with that key if it exists, keep the tree a valid BST, and return the (possibly new) root.
 *
 * <p><b>Algorithm Strategy (Search, then Replace by the Successor):</b>
 * <ol>
 *   <li>Search for the key using the BST property: go left if the key is smaller, right if it is larger.</li>
 *   <li>If the node is a leaf, return {@code null}; if it has one child, return that child.</li>
 *   <li>If it has two children, find the smallest node of the right subtree (the in-order successor).</li>
 *   <li>Copy the successor's value into the node and delete the successor from the right subtree.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [5,3,6,2,4,null,7]} and key 3: node 3 has two children, so it takes 4 (the smallest value in its right subtree) and 4's old position is removed.</li>
 *   <li>Result: {@code [5, 4, 6, 2, null, null, 7]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(h)} — One root-to-leaf search plus one successor search.</li>
 *   <li><b>Space Complexity:</b> {@code O(h)} — The recursion stack.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class DeleteNodeInABST {

    public static void main(String[] args) {
        Object[][] inputs = {
            {new Integer[]{5, 3, 6, 2, 4, null, 7}, 3},
            {new Integer[]{5, 3, 6, 2, 4, null, 7}, 0},
            {new Integer[]{5, 3, 6, 2, 4, null, 7}, 5},
            {new Integer[]{}, 0}
        };
        for (Object[] in : inputs) {
            TreeNode root = TreeNode.of((Integer[]) in[0]);
            int key = (Integer) in[1];
            System.out.println("Input  : root = " + TreeNode.toString(root) + ", key = " + key);
            System.out.println("Output : " + TreeNode.toString(deleteNode(root, key)));
            System.out.println();
        }
    }

    public static TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else {
            if (root.left == null) {
                return root.right;
            }
            if (root.right == null) {
                return root.left;
            }
            TreeNode successor = root.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            root.val = successor.val;
            root.right = deleteNode(root.right, successor.val);
        }
        return root;
    }
}
