# Delete Node in a Linked List

Write a function to delete a node in a singly linked list. You are given only the node to be deleted, not the head. The node is guaranteed not to be the tail. After the deletion the value of the node must not exist in the list, and the number of nodes decreases by one.

## Algorithm Strategy (Overwrite with the Next Node)

1. Without the head, the previous node cannot be reached, so the node itself cannot be unlinked.
2. Instead copy the value of the next node into the given node.
3. Then skip the next node by setting `node.next = node.next.next`.
4. To the rest of the list this is the same as deleting the original node.

## Example walkthrough

- For `4 -> 5 -> 1 -> 9` and the node with value 5: copy 1 into it, then skip the next node.
- Result: `[4, 1, 9]`.

## Complexity Analysis

- **Time Complexity:** `O(1)` — Two assignments.
- **Space Complexity:** `O(1)` — No extra storage.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Shift Every Following Value Left

Copy every later value one node to the left and cut the tail. Works, but touches the whole rest of the list.

```java
public static void shift(ListNode node) { if (node == null || node.next == null) return; ListNode cur = node; while (cur.next.next != null) { cur.val = cur.next.val; cur = cur.next; } cur.val = cur.next.val; cur.next = null; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 2: Overwrite and Skip (Optimized)

Copy the next value and unlink the next node. This is the version implemented in the repository.

```java
public static void overwrite(ListNode node) { if (node == null || node.next == null) return; node.val = node.next.val; node.next = node.next.next; }
```

- **Time Complexity:** O(1)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Shift Every Following Value Left | O(n) | O(1) |
| 2 | Overwrite and Skip (Optimized) | O(1) | O(1) |

## Source Code

### DeleteNodeInALinkedList.java

```java
public class DeleteNodeInALinkedList {

    public static void main(String[] args) {
        int[][] inputs = {{4, 5, 1, 9}, {4, 5, 1, 9}, {1, 2}, {0, 1, 2, 3}};
        int[] targets = {5, 1, 1, 0};
        for (int i = 0; i < inputs.length; i++) {
            ListNode head = ListNode.of(inputs[i]);
            ListNode node = head;
            while (node.val != targets[i]) {
                node = node.next;
            }
            System.out.println("Input  : head = " + ListNode.toString(head) + ", node = " + targets[i]);
            deleteNode(node);
            System.out.println("Output : " + ListNode.toString(head));
            System.out.println();
        }
    }

    public static void deleteNode(ListNode node) {
        if (node == null || node.next == null) {
            return;
        }
        node.val = node.next.val;
        node.next = node.next.next;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: unlinking a node normally needs the node before it, which is not available here. But a node is only a value and a pointer, so the node can become a copy of its successor, and then the successor is the one that is unlinked.

**Input:** `head = 4 -> 5 -> 1 -> 9`, node `5`.

| Step | List |
|------|------|
| start | 4 -> **5** -> 1 -> 9 |
| copy next value into the node | 4 -> **1** -> 1 -> 9 |
| `node.next = node.next.next` | 4 -> 1 -> 9 |

**Output:** `[4, 1, 9]`.
