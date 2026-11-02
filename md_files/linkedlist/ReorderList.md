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

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Store the Nodes in a List

Put all nodes into an `ArrayList` and relink them taking one node from the front and one from the back alternately.

```java
public static void reorderListArray(ListNode head) {
    if (head == null) return;
    List<ListNode> nodes = new ArrayList<>();
    for (ListNode curr = head; curr != null; curr = curr.next) nodes.add(curr);
    int left = 0, right = nodes.size() - 1;
    while (left < right) {
        nodes.get(left).next = nodes.get(right);
        left++;
        if (left == right) break;
        nodes.get(right).next = nodes.get(left);
        right--;
    }
    nodes.get(left).next = null;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 2: Deque of Nodes

Put the nodes into a deque and alternately take the first and the last node.

```java
public static void reorderListDeque(ListNode head) {
    if (head == null) return;
    Deque<ListNode> deque = new ArrayDeque<>();
    for (ListNode curr = head.next; curr != null; curr = curr.next) deque.add(curr);
    ListNode tail = head;
    boolean takeLast = true;
    while (!deque.isEmpty()) {
        tail.next = takeLast ? deque.pollLast() : deque.pollFirst();
        tail = tail.next;
        takeLast = !takeLast;
    }
    tail.next = null;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 3: Find the Middle, Reverse the Second Half, Interleave (Optimized)

Split the list in the middle, reverse the second half in place and merge the two halves alternately. This is the version implemented in the repository.

```java
public static void reorderList(ListNode head) {
    if (head == null || head.next == null) return;
    ListNode slow = head, fast = head;
    while (fast.next != null && fast.next.next != null) { slow = slow.next; fast = fast.next.next; }

    ListNode prev = null, curr = slow.next;
    slow.next = null;
    while (curr != null) { ListNode next = curr.next; curr.next = prev; prev = curr; curr = next; }

    ListNode first = head, second = prev;
    while (second != null) {
        ListNode firstNext = first.next, secondNext = second.next;
        first.next = second;
        second.next = firstNext;
        first = firstNext;
        second = secondNext;
    }
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Store the Nodes in a List | O(n) | O(n) |
| 2 | Deque of Nodes | O(n) | O(n) |
| 3 | Find the Middle, Reverse the Second Half, Interleave (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

Uses the shared helper [`ListNode`](../../src/main/java/com/ranga/linkedlist/ListNode.java) (a small `ListNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### ReorderList.java

```java
import com.ranga.linkedlist.ListNode;

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
