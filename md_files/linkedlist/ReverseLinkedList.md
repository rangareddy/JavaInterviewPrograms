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

### Approach 3: Pull the Next Node to the Front

Keep the original head as a fixed pivot. Repeatedly take the node after it and move it to the front of the list (behind a dummy head). After the last node has been moved the list is reversed.

```java
public static ListNode reverseListPull(ListNode head) {
    if (head == null) return null;
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    while (head.next != null) {
        ListNode moved = head.next;
        head.next = moved.next;
        moved.next = dummy.next;
        dummy.next = moved;
    }
    return dummy.next;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Approach 4: Tail Recursion with an Accumulator

Pass the already reversed part down as a parameter; when the original list is empty the accumulator is the answer. A tail call, so a language with tail-call elimination would need no stack.

```java
public static ListNode reverseListAccumulator(ListNode head) {
    return reverse(head, null);
}

private static ListNode reverse(ListNode curr, ListNode reversed) {
    if (curr == null) return reversed;
    ListNode next = curr.next;
    curr.next = reversed;
    return reverse(next, curr);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n) recursion stack in Java

### Approach 5: Array of Nodes, Relinked Backwards

Store the nodes in an `ArrayList` and set each node's `next` to its predecessor in the array.

```java
public static ListNode reverseListArray(ListNode head) {
    List<ListNode> nodes = new ArrayList<>();
    for (ListNode curr = head; curr != null; curr = curr.next) nodes.add(curr);
    for (int i = nodes.size() - 1; i > 0; i--) nodes.get(i).next = nodes.get(i - 1);
    if (nodes.isEmpty()) return null;
    nodes.get(0).next = null;
    return nodes.get(nodes.size() - 1);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 6: Collections.reverse on the Values

Copy the values into a list, let the library reverse it and write the values back into the nodes. The nodes themselves keep their links, so this is only acceptable when changing the values is allowed.

```java
public static ListNode reverseListValues(ListNode head) {
    List<Integer> values = new ArrayList<>();
    for (ListNode curr = head; curr != null; curr = curr.next) values.add(curr.val);
    Collections.reverse(values);
    int i = 0;
    for (ListNode curr = head; curr != null; curr = curr.next) curr.val = values.get(i++);
    return head;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 7: Iterative Pointer Reversal (Optimized)

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
| 3 | Pull the Next Node to the Front | O(n) | O(1) |
| 4 | Tail Recursion with an Accumulator | O(n) | O(n) recursion stack in Java |
| 5 | Array of Nodes, Relinked Backwards | O(n) | O(n) |
| 6 | Collections.reverse on the Values | O(n) | O(n) |
| 7 | Iterative Pointer Reversal (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

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
