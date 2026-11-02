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

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Two Passes (Compute the Length)

Count the nodes, then walk to the node before the one to delete (`length - n` steps from a dummy head).

```java
public static ListNode removeNthFromEndTwoPass(ListNode head, int n) {
    int length = 0;
    for (ListNode curr = head; curr != null; curr = curr.next) length++;
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode prev = dummy;
    for (int i = 0; i < length - n; i++) prev = prev.next;
    prev.next = prev.next.next;
    return dummy.next;
}
```

- **Time Complexity:** O(L)
- **Space Complexity:** O(1)

### Approach 2: Stack of Nodes

Push all nodes on a stack and pop `n` of them; the node now on top is the predecessor of the node to delete.

```java
public static ListNode removeNthFromEndStack(ListNode head, int n) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    Deque<ListNode> stack = new ArrayDeque<>();
    for (ListNode curr = dummy; curr != null; curr = curr.next) stack.push(curr);
    for (int i = 0; i < n; i++) stack.pop();
    ListNode prev = stack.peek();
    prev.next = prev.next.next;
    return dummy.next;
}
```

- **Time Complexity:** O(L)
- **Space Complexity:** O(L)

### Approach 3: One Pass with Two Pointers (Optimized)

Advance a fast pointer `n + 1` steps ahead, then move both pointers until the fast one falls off the list; the slow pointer is then just before the node to delete. This is the version implemented in the repository.

```java
public static ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode slow = dummy, fast = dummy;
    for (int i = 0; i <= n; i++) fast = fast.next;
    while (fast != null) { slow = slow.next; fast = fast.next; }
    slow.next = slow.next.next;
    return dummy.next;
}
```

- **Time Complexity:** O(L)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Two Passes (Compute the Length) | O(L) | O(1) |
| 2 | Stack of Nodes | O(L) | O(L) |
| 3 | One Pass with Two Pointers (Optimized) | O(L) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

Uses the shared helper [`ListNode`](../../src/main/java/com/ranga/linkedlist/ListNode.java) (a small `ListNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### RemoveNthNodeFromEnd.java

```java
import com.ranga.linkedlist.ListNode;

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
