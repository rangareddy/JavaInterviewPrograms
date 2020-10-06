# Binary Search Tree

```java
import java.util.LinkedList;
import java.util.Queue;

/**
 * User: Ranga Reddy
 * Date: 10/3/2019
 * Time: 11:32 AM
 * Description:
 */

class BinaryNode {

    Integer value;
    BinaryNode left;
    BinaryNode right;

    public BinaryNode(Integer value) {
        this.value = value;
        left = right = null;
    }

    public String toString() {
        return "value: " + value + ", left: " + left + ", right: " + right;
    }
}

class BinaryTree {

    private BinaryNode rootNode;

    public void add(Integer t) {
        rootNode = add(rootNode, t);
    }

    private BinaryNode add(BinaryNode node, Integer t) {
        if (node == null) {
            return new BinaryNode(t);
        }
        if (t < node.value) {
            node.left = add(node.left, t);
        } else if (t > node.value) {
            node.right = add(node.right, t);
        } else {
            // value already exists
            return node;
        }
        return node;
    }

    private void display(BinaryNode node) {
        if (node == null)
            return;

        display(node.left);
        System.out.print(node.value + " ");
        display(node.right);
    }

    public void display() {
        System.out.print("Printing elements : ");
        display(rootNode);
        System.out.println();
    }

    public void mirror() {
        rootNode = mirror(rootNode);
    }

    private BinaryNode mirror(BinaryNode node) {
        if (node == null)
            return node;
        BinaryNode left = mirror(node.left);
        BinaryNode right = mirror(node.right);
        node.left = right;
        node.right = left;
        return node;
    }

    public int maxValue() {
        /* loop down to find the rightmost leaf */
        BinaryNode current = rootNode;
        while (current.right != null) {
            current = current.right;
        }
        return (current.value);
    }

    public boolean isBST() {
        return isBST(rootNode, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public int size() {
        BinaryNode current = rootNode;
        int size = 0;
        Stack<BinaryNode> stack = new Stack<>();
        while (!stack.isEmpty() || current != null) {
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else {
                size++;
                current = stack.pop();
                current = current.right;
            }
        }
        return size;
    }

    public void levelOrderTraversal() {
        Queue<BinaryNode> queue = new LinkedList<>();
        queue.add(rootNode);
        int level = 0;
        while (!queue.isEmpty()) {
            BinaryNode tempNode = queue.poll();
            System.out.printf("%d %d ", tempNode.value, level++);
            if (tempNode.left != null)
                queue.add(tempNode.left);
            if (tempNode.right != null)
                queue.add(tempNode.right);
        }
    }

    private boolean isBST(BinaryNode node, int minValue, int maxValue) {
        if (node == null) {
            return true;
        }

        if (node.value < minValue || node.value > maxValue) {
            return false;
        }

        return isBST(node.left, minValue, node.value - 1) && isBST(node.right, node.value + 1, maxValue);
    }

    public int minValue() {
        /* loop down to find the rightmost leaf */
        BinaryNode current = rootNode;
        while (current.left != null) {
            current = current.left;
        }
        return (current.value);
    }

    public void clear() {
        rootNode = null;
    }
}

public class BinarySearchTree {
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        tree.add(20);
        tree.add(30);
        tree.add(-3);
        tree.add(25);
        tree.add(40);
        System.out.println("Tree size : " + tree.size());
        tree.display();

        // converting tree to mirror tree
        tree.mirror();
        tree.display();

        tree.mirror();
        int minValue = tree.minValue();
        int maxValue = tree.maxValue();
        System.out.println("Min: " + minValue + ", Max: " + maxValue);
        System.out.println("IsBST: " + tree.isBST());

        tree.levelOrderTraversal();
    }
}
```
