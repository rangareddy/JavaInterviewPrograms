package com.ranga.tree;

/**
 * You are given the root of a binary tree containing digits from 0 to 9 only. Each root-to-leaf path represents a number (for example, {@code 1 -> 2 -> 3} is {@code 123}). Return the total sum of all root-to-leaf numbers.
 *
 * <p><b>Algorithm Strategy (DFS Carrying the Number Built So Far):</b>
 * <ol>
 *   <li>Pass {@code current} down the recursion: at each node compute {@code current * 10 + node.val}.</li>
 *   <li>At a leaf, return that number.</li>
 *   <li>At an inner node, return the sum of the results of its children.</li>
 *   <li>The answer is the result for the root.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 2, 3]} the paths give 12 and 13.</li>
 *   <li>Result: {@code 12 + 13 = 25}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Each node once.</li>
 *   <li><b>Space Complexity:</b> {@code O(h)} — The recursion depth.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class SumRootToLeafNumbers {

    public static void main(String[] args) {
        Integer[][] inputs = {{1, 2, 3}, {4, 9, 0, 5, 1}, {0}, {}};
        for (Integer[] values : inputs) {
            TreeNode root = TreeNode.of(values);
            System.out.println("Input  : root = " + TreeNode.toString(root));
            System.out.println("Output : " + sumNumbers(root));
            System.out.println();
        }
    }

    public static int sumNumbers(TreeNode root) {
        return sum(root, 0);
    }

    private static int sum(TreeNode node, int current) {
        if (node == null) {
            return 0;
        }
        current = current * 10 + node.val;
        if (node.left == null && node.right == null) {
            return current;
        }
        return sum(node.left, current) + sum(node.right, current);
    }
}
