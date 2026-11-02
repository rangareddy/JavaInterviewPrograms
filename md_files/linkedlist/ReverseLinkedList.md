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

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Copy the Values onto a Stack

Push all values on a stack and write them back while walking the list again. Easy, but it needs `O(n)` extra space.

```java
public static ListNode reverseListStack(ListNode head) {
    Deque<Integer> stack = new ArrayDeque<>();
    for (ListNode curr = head; curr != null; curr = curr.next) stack.push(curr.val);
    for (ListNode curr = head; curr != null; curr = curr.next) curr.val = stack.pop();
    return head;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Recursion

Reverse the rest of the list first, then make the next node point back to the current one.

```java
public static ListNode reverseListRecursive(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode newHead = reverseListRecursive(head.next);
    head.next.next = head;
    head.next = null;
    return newHead;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n) recursion stack

### Approach 3: Iterative Pointer Reversal (Optimized)

Walk once and point every node to its predecessor. This is the version implemented in the repository.

```java
public static ListNode reverseList(ListNode head) {
    ListNode prev = null, curr = head;
    while (curr != null) {
        ListNode next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    return prev;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Copy the Values onto a Stack | O(n) | O(n) |
| 2 | Recursion | O(n) | O(n) recursion stack |
| 3 | Iterative Pointer Reversal (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

Uses the shared helper [`ListNode`](../../src/main/java/com/ranga/linkedlist/ListNode.java) (a small `ListNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### ReverseLinkedList.java

```java
import com.ranga.linkedlist.ListNode;

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
