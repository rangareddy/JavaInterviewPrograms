# Merge Two Sorted Lists

Merge two sorted linked lists into one sorted list by splicing together the nodes of the two lists, and return the head of the merged list.

## Algorithm Strategy (Dummy Head and Two Pointers)

1. Create a `dummy` node and a `tail` pointer that starts at it; the merged list is built after `tail`.
2. While both lists have nodes, link the smaller of `list1.val` and `list2.val` after `tail`, advance that list and advance `tail`.
3. When one list is exhausted, link the remaining nodes of the other list (it is already sorted).
4. Return `dummy.next`. The dummy node removes the special case of an empty result head.

## Example walkthrough

- For `[1, 2, 4]` and `[1, 3, 4]`: take 1 (list1), 1 (list2), 2, 3, 4 (list1), then the remaining 4 of list2.
- Result: `[1, 1, 2, 3, 4, 4]`.

## Complexity Analysis

- **Time Complexity:** `O(n + m)` — Every node is linked once.
- **Space Complexity:** `O(1)` — Nodes are reused, no new nodes are created.

## Source Code

Uses the shared helper [`ListNode`](../../../src/main/java/com/ranga/blind75/linkedlist/ListNode.java) (a small `ListNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### MergeTwoSortedLists.java

```java
import com.ranga.blind75.linkedlist.ListNode;

public class MergeTwoSortedLists {

    public static void main(String[] args) {
        ListNode[][] inputs = {
                {ListNode.of(1, 2, 4), ListNode.of(1, 3, 4)},
                {ListNode.of(), ListNode.of()},
                {ListNode.of(), ListNode.of(0)}
        };
        for (ListNode[] pair : inputs) {
            System.out.println("Input  : list1 = " + ListNode.toString(pair[0]) + ", list2 = " + ListNode.toString(pair[1]));
            System.out.println("Output : " + ListNode.toString(mergeTwoLists(pair[0], pair[1])));
            System.out.println();
        }
    }

    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                tail.next = list1;
                list1 = list1.next;
            } else {
                tail.next = list2;
                list2 = list2.next;
            }
            tail = tail.next;
        }
        tail.next = (list1 != null) ? list1 : list2;
        return dummy.next;
    }
}
```
