# Merge k Sorted Lists

Given an array of `k` linked lists, each sorted in ascending order, merge them into one sorted linked list and return it.

## Algorithm Strategy (Min-Heap of List Heads)

1. Put the head node of every non-empty list into a `PriorityQueue` ordered by node value; the heap always holds at most `k` nodes.
2. Repeatedly remove the smallest node from the heap and append it to the result list (after a dummy head).
3. After removing a node, push its `next` node (if any) into the heap, so the next candidate from the same list is available.
4. When the heap is empty all nodes have been merged; return `dummy.next`.

## Example walkthrough

- For `[[1,4,5],[1,3,4],[2,6]]` the heap starts with the heads 1, 1, 2.
- Pops: 1 (push 4), 1 (push 3), 2 (push 6), 3, 4, 4, 5, 6. Result: `[1, 1, 2, 3, 4, 4, 5, 6]`.

## Complexity Analysis

- **Time Complexity:** `O(N log k)` — `N` nodes in total, each heap operation costs `O(log k)`.
- **Space Complexity:** `O(k)` — The heap holds one node per list.

## Source Code

Uses the shared helper [`ListNode`](../../../src/main/java/com/ranga/blind75/linkedlist/ListNode.java) (a small `ListNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### MergeKSortedLists.java

```java
import com.ranga.blind75.linkedlist.ListNode;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MergeKSortedLists {

    public static void main(String[] args) {
        ListNode[][] inputs = {
                {ListNode.of(1, 4, 5), ListNode.of(1, 3, 4), ListNode.of(2, 6)},
                {},
                {ListNode.of()}
        };
        for (ListNode[] lists : inputs) {
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < lists.length; i++) {
                sb.append(i > 0 ? ", " : "").append(ListNode.toString(lists[i]));
            }
            System.out.println("Input  : lists = " + sb.append("]"));
            System.out.println("Output : " + ListNode.toString(mergeKLists(lists)));
            System.out.println();
        }
    }

    public static ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        PriorityQueue<ListNode> heap = new PriorityQueue<>(Comparator.comparingInt((ListNode node) -> node.val));
        for (ListNode head : lists) {
            if (head != null) {
                heap.add(head);
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while (!heap.isEmpty()) {
            ListNode smallest = heap.poll();
            tail.next = smallest;
            tail = tail.next;
            if (smallest.next != null) {
                heap.add(smallest.next);
            }
        }
        return dummy.next;
    }
}
```
