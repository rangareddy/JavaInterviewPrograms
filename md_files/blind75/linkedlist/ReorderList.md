# Reorder List

Reorder the list `L0 -> L1 -> ... -> Ln-1 -> Ln` into `L0 -> Ln -> L1 -> Ln-1 -> L2 -> Ln-2 -> ...` by changing only the links, not the node values.

## Algorithm Strategy (Find Middle, Reverse Second Half, Interleave)

1. Find the middle of the list with slow and fast pointers; `slow` ends at the last node of the first half.
2. Cut the list after `slow` and reverse the second half in place.
3. Merge the two halves alternately: take one node from the first half, then one from the reversed second half, until the second half is exhausted.
4. The first half is never shorter than the second half, so no nodes are lost.

## Example walkthrough

- For `[1, 2, 3, 4]`: halves `1 -> 2` and `3 -> 4`; the reversed second half is `4 -> 3`.
- Interleaving gives `1 -> 4 -> 2 -> 3`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Three linear passes.
- **Space Complexity:** `O(1)` — Only pointers are changed.

## Source Code

Uses the shared helper [`ListNode`](../../../src/main/java/com/ranga/blind75/linkedlist/ListNode.java) (a small `ListNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### ReorderList.java

```java
import com.ranga.blind75.linkedlist.ListNode;

public class ReorderList {

    public static void main(String[] args) {
        ListNode[] inputs = {ListNode.of(1, 2, 3, 4), ListNode.of(1, 2, 3, 4, 5), ListNode.of(1)};
        for (ListNode head : inputs) {
            System.out.println("Input  : head = " + ListNode.toString(head));
            reorderList(head);
            System.out.println("Output : " + ListNode.toString(head));
            System.out.println();
        }
    }

    public static void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }

        // 1. Find the middle
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. Reverse the second half
        ListNode prev = null;
        ListNode curr = slow.next;
        slow.next = null;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        // 3. Interleave the first half with the reversed second half
        ListNode first = head;
        ListNode second = prev;
        while (second != null) {
            ListNode firstNext = first.next;
            ListNode secondNext = second.next;
            first.next = second;
            second.next = firstNext;
            first = firstNext;
            second = secondNext;
        }
    }
}
```
