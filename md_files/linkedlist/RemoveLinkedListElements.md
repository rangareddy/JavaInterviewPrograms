# Remove Linked List Elements

Given the head of a linked list and an integer `val`, remove all the nodes of the linked list that have `Node.val == val`, and return the new head.

## Algorithm Strategy (Dummy Head and One Pass)

1. Put a dummy node in front of the head so that removing the head needs no special case.
2. Walk with a pointer `cur` starting at the dummy.
3. If `cur.next.val == val`, unlink it with `cur.next = cur.next.next` and stay on `cur`; otherwise advance `cur`.
4. Return `dummy.next`.

## Example walkthrough

- For `1 -> 2 -> 6 -> 3 -> 4 -> 5 -> 6` and `val = 6`: the two 6 nodes are skipped.
- Result: `[1, 2, 3, 4, 5]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — One pass.
- **Space Complexity:** `O(1)` — Only the dummy node.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion

Solve the rest of the list first, then decide whether the head stays. Short, but uses stack space proportional to the length.

```java
public static ListNode recursive(ListNode head, int val) { if (head == null) return null; head.next = recursive(head.next, val); return head.val == val ? head.next : head; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Dummy Head and Pointer (Optimized)

Unlink matching nodes in one pass with constant extra space. This is the version implemented in the repository.

```java
public static ListNode iterative(ListNode head, int val) { ListNode d = new ListNode(0); d.next = head; ListNode c = d; while (c.next != null) { if (c.next.val == val) c.next = c.next.next; else c = c.next; } return d.next; }
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion | O(n) | O(n) |
| 2 | Dummy Head and Pointer (Optimized) | O(n) | O(1) |

## Source Code

### RemoveLinkedListElements.java

```java
public class RemoveLinkedListElements {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 6, 3, 4, 5, 6}, {7, 7, 7, 7}, {}, {6, 1, 6, 2}, {1, 2, 3}};
        int[] values = {6, 7, 1, 6, 5};
        for (int i = 0; i < inputs.length; i++) {
            ListNode head = ListNode.of(inputs[i]);
            System.out.println("Input  : head = " + ListNode.toString(head) + ", val = " + values[i]);
            System.out.println("Output : " + ListNode.toString(removeElements(head, values[i])));
            System.out.println();
        }
    }

    public static ListNode removeElements(ListNode head, int val) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode cur = dummy;
        while (cur.next != null) {
            if (cur.next.val == val) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }
        return dummy.next;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: the only nodes that need special care are the ones at the front. A dummy node makes the head look like every other node, so a single loop that either unlinks `cur.next` or moves on handles all cases.

**Input:** `head = 1 -> 2 -> 6 -> 3 -> 4 -> 5 -> 6`, `val = 6`.

| `cur` | `cur.next` | Action |
|-------|------------|--------|
| dummy | 1 | keep, advance |
| 1 | 2 | keep, advance |
| 2 | 6 | unlink |
| 2 | 3 | keep, advance |
| 3 | 4 | keep, advance |
| 4 | 5 | keep, advance |
| 5 | 6 | unlink |

**Output:** `[1, 2, 3, 4, 5]`.
