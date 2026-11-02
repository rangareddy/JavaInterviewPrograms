# Remove Nth Node From End of List

Given the head of a linked list, remove the `n`-th node from the end of the list and return the head.

## Algorithm Strategy (Two Pointers with a Gap)

1. Add a `dummy` node before the head so removing the first node needs no special case.
2. Move `fast` forward `n + 1` steps from the dummy so that there are exactly `n` nodes between `slow` and `fast`.
3. Move both pointers together until `fast` reaches `null`; now `slow.next` is the node to delete.
4. Delete it with `slow.next = slow.next.next` and return `dummy.next`. This works in one pass without knowing the length.

## Example walkthrough

- For `[1, 2, 3, 4, 5]` and `n = 2`: fast starts 3 nodes ahead; when it falls off the end, slow stands on 3 and `slow.next` is 4.
- Removing it gives `[1, 2, 3, 5]`.

## Complexity Analysis

- **Time Complexity:** `O(L)` — One pass over the list of length `L`.
- **Space Complexity:** `O(1)` — Two pointers.

## Source Code

Uses the shared helper [`ListNode`](../../../src/main/java/com/ranga/blind75/linkedlist/ListNode.java) (a small `ListNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### RemoveNthNodeFromEnd.java

```java
import com.ranga.blind75.linkedlist.ListNode;

public class RemoveNthNodeFromEnd {

    public static void main(String[] args) {
        ListNode[] inputs = {ListNode.of(1, 2, 3, 4, 5), ListNode.of(1), ListNode.of(1, 2)};
        int[] ns = {2, 1, 1};
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("Input  : head = " + ListNode.toString(inputs[i]) + ", n = " + ns[i]);
            System.out.println("Output : " + ListNode.toString(removeNthFromEnd(inputs[i], ns[i])));
            System.out.println();
        }
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode slow = dummy;
        ListNode fast = dummy;

        for (int i = 0; i <= n; i++) {
            fast = fast.next;           // create a gap of n nodes between slow and fast
        }
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;     // delete the n-th node from the end
        return dummy.next;
    }
}
```
