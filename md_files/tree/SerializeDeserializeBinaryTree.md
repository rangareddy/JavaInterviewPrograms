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

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Level-Order (BFS) Serialization

Write the nodes level by level with `null` for missing children, and rebuild the tree with a queue the same way LeetCode prints trees.

```java
public static String serializeBfs(TreeNode root) {
    if (root == null) return "";
    StringBuilder sb = new StringBuilder();
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    while (!queue.isEmpty()) {
        TreeNode node = queue.poll();
        if (node == null) { sb.append("null,"); continue; }
        sb.append(node.val).append(',');
        queue.add(node.left);
        queue.add(node.right);
    }
    return sb.toString();
}

public static TreeNode deserializeBfs(String data) {
    if (data.isEmpty()) return null;
    String[] tokens = data.split(",");
    TreeNode root = new TreeNode(Integer.parseInt(tokens[0]));
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    int i = 1;
    while (!queue.isEmpty() && i < tokens.length) {
        TreeNode node = queue.poll();
        if (!tokens[i].equals("null")) { node.left = new TreeNode(Integer.parseInt(tokens[i])); queue.add(node.left); }
        i++;
        if (i < tokens.length && !tokens[i].equals("null")) { node.right = new TreeNode(Integer.parseInt(tokens[i])); queue.add(node.right); }
        i++;
    }
    return root;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Pre-order with Null Markers (Optimized)

A single pre-order traversal with explicit `null` markers is enough to rebuild the structure recursively; the code is shorter than the BFS version. This is the version implemented in the repository.

```java
public static String serialize(TreeNode root) {
    StringBuilder sb = new StringBuilder();
    write(root, sb);
    sb.setLength(sb.length() - 1);
    return sb.toString();
}

private static void write(TreeNode node, StringBuilder sb) {
    if (node == null) { sb.append("null,"); return; }
    sb.append(node.val).append(',');
    write(node.left, sb);
    write(node.right, sb);
}

public static TreeNode deserialize(String data) {
    return read(new LinkedList<>(Arrays.asList(data.split(","))));
}

private static TreeNode read(Queue<String> tokens) {
    String token = tokens.poll();
    if (token == null || token.equals("null")) return null;
    TreeNode node = new TreeNode(Integer.parseInt(token));
    node.left = read(tokens);
    node.right = read(tokens);
    return node;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Pre-order with a Compact Format

Use single characters for the markers (`#` for a missing node, space as separator) and an index into the token array instead of a queue; this makes the string shorter and the decoding allocation-free.

```java
public static String serializeCompact(TreeNode root) {
    if (root == null) return "#";
    return root.val + " " + serializeCompact(root.left) + " " + serializeCompact(root.right);
}

public static TreeNode deserializeCompact(String data) {
    return readCompact(data.split(" "), new int[]{0});
}

private static TreeNode readCompact(String[] tokens, int[] index) {
    String token = tokens[index[0]++];
    if (token.equals("#")) return null;
    TreeNode node = new TreeNode(Integer.parseInt(token));
    node.left = readCompact(tokens, index);
    node.right = readCompact(tokens, index);
    return node;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Level-Order (BFS) Serialization | O(n) | O(n) |
| 2 | Pre-order with Null Markers (Optimized) | O(n) | O(n) |
| 3 | Pre-order with a Compact Format | O(n) | O(n) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

Uses the shared helper [`TreeNode`](../../src/main/java/com/ranga/tree/TreeNode.java) (a small `TreeNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

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
