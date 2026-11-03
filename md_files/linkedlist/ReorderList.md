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

### Approach 3: Recursion Matching Head and Tail While Unwinding

Recurse inwards two nodes at a time; on the way back out, each call has the matching node from the tail end available and links it right after its own node. No extra data structure, but the recursion depth is half the list length.

```java
public static void reorderListRecursive(ListNode head) {
    int length = 0;
    for (ListNode curr = head; curr != null; curr = curr.next) length++;
    if (length > 2) helper(head, length);
}

// reorders the 'length' nodes starting at 'front' and returns the node that follows them in the original list
private static ListNode helper(ListNode front, int length) {
    if (length == 1) { ListNode tail = front.next; front.next = null; return tail; }
    if (length == 2) { ListNode tail = front.next.next; front.next.next = null; return tail; }
    ListNode tail = helper(front.next, length - 2);
    ListNode second = front.next;
    front.next = tail;
    ListNode afterTail = tail.next;
    tail.next = second;
    return afterTail;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n) recursion stack

### Approach 4: Stack for the Second Half

Find the middle, push the nodes of the second half on a stack (so they come out in reverse order) and weave them between the nodes of the first half.

```java
public static void reorderListStack(ListNode head) {
    if (head == null || head.next == null) return;
    ListNode slow = head, fast = head;
    while (fast.next != null && fast.next.next != null) { slow = slow.next; fast = fast.next.next; }
    Deque<ListNode> stack = new ArrayDeque<>();
    for (ListNode curr = slow.next; curr != null; curr = curr.next) stack.push(curr);
    slow.next = null;
    ListNode curr = head;
    while (!stack.isEmpty()) {
        ListNode next = curr.next;
        curr.next = stack.pop();
        curr.next.next = next;
        curr = next;
    }
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 5: Move the Last Node Repeatedly (Brute Force)

Walk to the last node, cut it off and insert it behind the current node; then move two nodes ahead and repeat. Only pointers are changed, but finding the last node every time makes it quadratic.

```java
public static void reorderListMoveLast(ListNode head) {
    ListNode curr = head;
    while (curr != null && curr.next != null && curr.next.next != null) {
        ListNode prev = curr;
        while (prev.next.next != null) prev = prev.next;       // second-to-last node
        ListNode last = prev.next;
        prev.next = null;                                       // cut the last node off
        last.next = curr.next;
        curr.next = last;
        curr = last.next;
    }
}
```

- **Time Complexity:** O(n^2)
- **Space Complexity:** O(1)

### Approach 6: Reverse the Second Half, Then Merge Recursively

The same split-and-reverse idea as the optimized version, but the two halves are woven together with a small recursive function instead of a loop.

```java
public static void reorderListRecursiveMerge(ListNode head) {
    if (head == null || head.next == null) return;
    ListNode slow = head, fast = head;
    while (fast.next != null && fast.next.next != null) { slow = slow.next; fast = fast.next.next; }
    ListNode second = slow.next;
    slow.next = null;
    ListNode prev = null;
    while (second != null) { ListNode next = second.next; second.next = prev; prev = second; second = next; }
    weave(head, prev);
}

private static void weave(ListNode a, ListNode b) {
    if (a == null || b == null) return;
    ListNode aNext = a.next, bNext = b.next;
    a.next = b;
    b.next = aNext;
    weave(aNext, bNext);
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n) recursion stack

### Approach 7: Find the Middle, Reverse the Second Half, Interleave (Optimized)

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
| 3 | Recursion Matching Head and Tail While Unwinding | O(n) | O(n) recursion stack |
| 4 | Stack for the Second Half | O(n) | O(n) |
| 5 | Move the Last Node Repeatedly (Brute Force) | O(n^2) | O(1) |
| 6 | Reverse the Second Half, Then Merge Recursively | O(n) | O(n) recursion stack |
| 7 | Find the Middle, Reverse the Second Half, Interleave (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

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
