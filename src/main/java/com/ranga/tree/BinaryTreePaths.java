package com.ranga.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Given the root of a binary tree, return all root-to-leaf paths in any order. A leaf is a node with no children.
 *
 * <p><b>Algorithm Strategy (DFS with the Current Path):</b>
 * <ol>
 *   <li>Do a DFS from the root, carrying the path string so far.</li>
 *   <li>At each node append its value (with {@code ->} before it when the path is not empty).</li>
 *   <li>When the node is a leaf, add the path to the result.</li>
 *   <li>Otherwise recurse into the left and right children.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 3, null, 5]}: the path {@code 1->2} continues to the leaf 5, and the right child 3 is a leaf.</li>
 *   <li>Result: {@code [1->2->5, 1->3]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each node once, plus the cost of building the strings.</li>
 *   <li><b>Space Complexity:</b> {@code O(h)} — The recursion depth.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class BinaryTreePaths {

    public static void main(String[] args) {
        Integer[][] inputs = {{1, 2, 3, null, 5}, {1}, {1, 2}, {}};
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + binaryTreePaths(root));
            System.out.println();
        }
    }

    public static List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        collect(root, "", paths);
        return paths;
    }

    private static void collect(TreeNode node, String prefix, List<String> paths) {
        if (node == null) {
            return;
        }
        String path = prefix + node.val;
        if (node.left == null && node.right == null) {
            paths.add(path);
            return;
        }
        collect(node.left, path + "->", paths);
        collect(node.right, path + "->", paths);
    }
}
