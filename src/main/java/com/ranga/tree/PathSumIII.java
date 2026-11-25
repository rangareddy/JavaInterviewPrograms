package com.ranga.tree;

import java.util.HashMap;
import java.util.Map;

/**
 * Given the root of a binary tree and an integer {@code targetSum}, return the number of paths whose node values sum to {@code targetSum}. A path does not need to start at the root or end at a leaf, but it must go downwards (from parent to child).
 *
 * <p><b>Algorithm Strategy (Prefix Sum Map along the Current Path):</b>
 * <ol>
 *   <li>Do a DFS that carries the running sum from the root to the current node.</li>
 *   <li>Keep a map from prefix sum to how many times it appeared on the path so far (start with {@code {0: 1}}).</li>
 *   <li>At each node, the number of downward paths ending here with the target sum is {@code map[currentSum - targetSum]}.</li>
 *   <li>Recurse into the children, then remove the node's prefix sum from the map when backtracking.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For the tree {@code [10, 5, -3, 3, 2, null, 11, 3, -2, null, 1]} and target 8: the paths are {@code 5 -> 3}, {@code 5 -> 2 -> 1} and {@code -3 -> 11}.</li>
 *   <li>Result: {@code 3}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Every node is visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(h)} — The recursion and the map.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class PathSumIII {

    public static void main(String[] args) {
        Integer[][] inputs = {{10, 5, -3, 3, 2, null, 11, 3, -2, null, 1}, {5, 4, 8, 11, null, 13, 4, 7, 2, null, null, 5, 1}, {1}, {}};
        int[] targets = {8, 22, 1, 0};
        for (int i = 0; i < inputs.length; i++) {
            TreeNode root = TreeNode.of(inputs[i]);
            System.out.println("Input  : root = " + TreeNode.toString(root) + ", targetSum = " + targets[i]);
            System.out.println("Output : " + pathSum(root, targets[i]));
            System.out.println();
        }
    }

    public static int pathSum(TreeNode root, int targetSum) {
        Map<Long, Integer> prefixCounts = new HashMap<>();
        prefixCounts.put(0L, 1);
        return count(root, 0L, targetSum, prefixCounts);
    }

    private static int count(TreeNode node, long current, int target, Map<Long, Integer> prefixCounts) {
        if (node == null) {
            return 0;
        }
        current += node.val;
        int paths = prefixCounts.getOrDefault(current - target, 0);
        prefixCounts.merge(current, 1, Integer::sum);
        paths += count(node.left, current, target, prefixCounts);
        paths += count(node.right, current, target, prefixCounts);
        prefixCounts.merge(current, -1, Integer::sum);
        return paths;
    }
}
