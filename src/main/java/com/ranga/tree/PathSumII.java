package com.ranga.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Given the root of a binary tree and an integer {@code targetSum}, return all root-to-leaf paths where the sum of the node values equals {@code targetSum}. Each path is a list of node values, not node references.
 *
 * <p><b>Algorithm Strategy (DFS with Backtracking):</b>
 * <ol>
 *   <li>Walk the tree depth first, keeping the current path in one shared list and the remaining sum.</li>
 *   <li>On entering a node add its value to the path and subtract it from the remaining sum.</li>
 *   <li>If the node is a leaf and the remaining sum is now 0, copy the path into the result (the copy matters because the shared list keeps changing).</li>
 *   <li>Visit the children, then remove the node from the path before returning (backtrack) so that the sibling subtree starts from the correct path.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [5, 4, 8, 11, null, 13, 4, 7, 2, null, null, 5, 1]} with {@code targetSum = 22}: the path {@code 5, 4, 11, 2} sums to 22 and so does {@code 5, 8, 4, 5}.</li>
 *   <li>Result: {@code [[5, 4, 11, 2], [5, 8, 4, 5]]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2)} — Visiting is {@code O(n)}, and copying a path of length up to {@code h} for each leaf can add up to {@code O(n * h)}.</li>
 *   <li><b>Space Complexity:</b> {@code O(h)} — The path and the recursion stack, not counting the output.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PathSumII {

    public static void main(String[] args) {
        Integer[][] inputs = {
                {5, 4, 8, 11, null, 13, 4, 7, 2, null, null, 5, 1},
                {1, 2, 3},
                {1, 2}
        };
        int[] targets = {22, 5, 0};
        for (int i = 0; i < inputs.length; i++) {
            TreeNode root = TreeNode.of(inputs[i]);
            System.out.println("Input  : root = " + TreeNode.toString(root) + ", targetSum = " + targets[i]);
            System.out.println("Output : " + pathSum(root, targets[i]));
            System.out.println();
        }
    }

    public static List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(root, targetSum, new ArrayList<>(), result);
        return result;
    }

    private static void dfs(TreeNode node, int remaining, List<Integer> path, List<List<Integer>> result) {
        if (node == null) {
            return;
        }
        path.add(node.val);
        remaining -= node.val;
        if (node.left == null && node.right == null && remaining == 0) {
            result.add(new ArrayList<>(path));
        }
        dfs(node.left, remaining, path, result);
        dfs(node.right, remaining, path, result);
        path.remove(path.size() - 1);
    }
}
