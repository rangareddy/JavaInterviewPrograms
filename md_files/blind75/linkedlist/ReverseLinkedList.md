# Reverse Linked List

Given the head of a singly linked list, reverse the list and return the new head.

## Algorithm Strategy (Iterative Pointer Reversal)

1. Keep `prev` (initially `null`) and `curr` (initially the head).
2. For each node, save `curr.next` in `next`, then point `curr.next` back to `prev`.
3. Advance: `prev = curr` and `curr = next`.
4. When `curr` becomes `null`, `prev` is the last node of the original list, i.e. the new head.

## Example walkthrough

- For `1 -> 2 -> 3`: after step 1 the list is `1 -> null`, then `2 -> 1 -> null`, then `3 -> 2 -> 1 -> null`.
- Result: `[3, 2, 1]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Each node is visited once.
- **Space Complexity:** `O(1)` — Only three pointers.

## Source Code

Uses the shared helper [`ListNode`](../../../src/main/java/com/ranga/blind75/linkedlist/ListNode.java) (a small `ListNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### ReverseLinkedList.java

```java
import com.ranga.blind75.linkedlist.ListNode;

public class ReverseLinkedList {

    public static void main(String[] args) {
        ListNode[] inputs = {ListNode.of(1, 2, 3, 4, 5), ListNode.of(1, 2), ListNode.of()};
        for (ListNode head : inputs) {
            System.out.println("Input  : head = " + ListNode.toString(head));
            System.out.println("Output : " + ListNode.toString(reverseList(head)));
            System.out.println();
        }
    }

    public static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
}
```
