package com.ranga.tree;

/**
 * Merge two binary trees by overlapping them. Where both nodes exist, the merged node's value is the sum of the two. Where only one exists, that node is used as it is.
 *
 * <p><b>Algorithm Strategy (Parallel Recursion):</b>
 * <ol>
 *   <li>If one tree is {@code null}, the other tree is the answer for that position.</li>
 *   <li>Otherwise create a node with {@code t1.val + t2.val}.</li>
 *   <li>Recurse on the two left children and the two right children to build the merged subtrees.</li>
 *   <li>Return the new node. The inputs are left unmodified.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1,3,2,5]} and {@code [2,1,3,null,4,null,7]} the roots add to 3, the left children to 4, the right children to 5.</li>
 *   <li>Result: {@code [3,4,5,5,4,null,7]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(min(n, m))} — Only positions present in both trees are recursed into.</li>
 *   <li><b>Space Complexity:</b> {@code O(min(h1, h2))} — Recursion depth is bounded by the shallower tree height.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MergeTwoBinaryTrees {

    public static void main(String[] args) {
        Integer[][][] inputs = {
            {{1, 3, 2, 5}, {2, 1, 3, null, 4, null, 7}},
            {{1}, {1, 2}},
            {{}, {1}}
        };
        for (Integer[][] pair : inputs) {
            TreeNode a = TreeNode.of(pair[0]);
            TreeNode b = TreeNode.of(pair[1]);
            System.out.println("Input  : t1 = " + TreeNode.toString(a) + ", t2 = " + TreeNode.toString(b));
            System.out.println("Output : " + TreeNode.toString(mergeTrees(a, b)));
            System.out.println();
        }
    }

    public static TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }
        TreeNode merged = new TreeNode(t1.val + t2.val);
        merged.left = mergeTrees(t1.left, t2.left);
        merged.right = mergeTrees(t1.right, t2.right);
        return merged;
    }
}
