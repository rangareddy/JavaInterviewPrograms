# Serialize and Deserialize Binary Tree

Design an algorithm to serialize a binary tree to a string and deserialize that string back to the original tree structure.

## Algorithm Strategy (Pre-order Traversal with Null Markers)

1. Serialize with a pre-order traversal (node, left, right), writing the value of each node and the marker `null` for every missing child, separated by commas.
2. The null markers make the structure unambiguous, so a single traversal order is enough to rebuild the tree.
3. Deserialize by splitting the string into a queue of tokens and rebuilding recursively: take the next token; if it is `null` return `null`, otherwise create a node and build its left subtree and then its right subtree from the following tokens.
4. Because both directions follow the same pre-order, the tokens are consumed in exactly the order they were produced.

## Example walkthrough

- `[1, 2, 3, null, null, 4, 5]` is serialized as `"1,2,null,null,3,4,null,null,5,null,null"`.
- Deserializing that string produces the same tree again.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each node is written and read once.
- **Space Complexity:** `O(n)` — The serialized string and the recursion stack.

## Source Code

Uses the shared helper [`TreeNode`](../../../src/main/java/com/ranga/blind75/tree/TreeNode.java) (a small `TreeNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### SerializeDeserializeBinaryTree.java

```java
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class SerializeDeserializeBinaryTree {

    public static void main(String[] args) {
        TreeNode[] inputs = {TreeNode.of(1, 2, 3, null, null, 4, 5), TreeNode.of(), TreeNode.of(1, 2)};
        for (TreeNode root : inputs) {
            String data = serialize(root);
            System.out.println("Input      : root = " + TreeNode.toString(root));
            System.out.println("Serialized : " + data);
            System.out.println("Output     : " + TreeNode.toString(deserialize(data)));
            System.out.println();
        }
    }

    public static String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serialize(root, sb);
        sb.setLength(sb.length() - 1);      // remove the trailing comma
        return sb.toString();
    }

    private static void serialize(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("null,");
            return;
        }
        sb.append(node.val).append(',');
        serialize(node.left, sb);
        serialize(node.right, sb);
    }

    public static TreeNode deserialize(String data) {
        Queue<String> tokens = new LinkedList<>(Arrays.asList(data.split(",")));
        return deserialize(tokens);
    }

    private static TreeNode deserialize(Queue<String> tokens) {
        String token = tokens.poll();
        if (token == null || token.equals("null")) {
            return null;
        }
        TreeNode node = new TreeNode(Integer.parseInt(token));
        node.left = deserialize(tokens);
        node.right = deserialize(tokens);
        return node;
    }
}
```
