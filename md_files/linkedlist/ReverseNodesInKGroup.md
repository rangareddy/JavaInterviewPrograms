# Reverse Nodes in k-Group

Given the head of a linked list, reverse the nodes of the list `k` at a time and return the modified list. `k` is a positive integer not greater than the length of the list; if the number of nodes is not a multiple of `k`, the left-out nodes at the end stay as they are. Only the nodes themselves may be changed, not their values.

## Algorithm Strategy (Reverse Each Complete Group In Place)

1. Use a `dummy` node before the head and `groupPrev`, the node just before the current group.
2. Find the `k`-th node `kth` from `groupPrev`. If fewer than `k` nodes remain, stop: the rest stays unchanged.
3. Reverse the `k` nodes between `groupPrev` and `kth` with the usual three-pointer reversal, starting with `prev` set to the node after the group so the reversed group is already linked to the rest.
4. The old first node of the group is now its last node: it becomes the new `groupPrev`. Repeat for the next group.

## Example walkthrough

- For `[1, 2, 3, 4, 5]` and `k = 2`: the groups `(1,2)` and `(3,4)` are reversed, node 5 has no partner.
- Result: `[2, 1, 4, 3, 5]`. For `k = 3`: `[3, 2, 1, 4, 5]`.

## Complexity Analysis

- **Time Complexity:** `O(n)` — Every node is visited a constant number of times.
- **Space Complexity:** `O(1)` — Only pointers are changed.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Recursion

If at least `k` nodes remain, reverse the first `k` of them and attach the recursively processed rest; otherwise return the list unchanged.

```java
public static ListNode reverseKGroupRecursive(ListNode head, int k) {
    ListNode check = head;
    for (int i = 0; i < k; i++) {
        if (check == null) return head;                 // fewer than k nodes: leave as is
        check = check.next;
    }
    ListNode prev = reverseKGroupRecursive(check, k), curr = head;
    for (int i = 0; i < k; i++) {
        ListNode next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    return prev;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n / k) recursion stack

### Approach 2: Stack per Group

Push the next `k` nodes on a stack. If there are fewer than `k`, stop; otherwise pop them off to link them in reverse order. A readable variant that needs `O(k)` extra space.

```java
public static ListNode reverseKGroupStack(ListNode head, int k) {
    ListNode dummy = new ListNode(0), tail = dummy, curr = head;
    Deque<ListNode> stack = new ArrayDeque<>();
    while (curr != null) {
        ListNode groupStart = curr;
        while (curr != null && stack.size() < k) { stack.push(curr); curr = curr.next; }
        if (stack.size() < k) { tail.next = groupStart; return dummy.next; }
        while (!stack.isEmpty()) { tail.next = stack.pop(); tail = tail.next; }
        tail.next = curr;
    }
    return dummy.next;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(k)

### Approach 3: Array of Nodes, Reverse Ranges

Copy the nodes into an array, reverse every complete block of `k` nodes in the array and relink the nodes in the new order.

```java
public static ListNode reverseKGroupArray(ListNode head, int k) {
    List<ListNode> nodes = new ArrayList<>();
    for (ListNode curr = head; curr != null; curr = curr.next) nodes.add(curr);
    for (int start = 0; start + k <= nodes.size(); start += k) Collections.reverse(nodes.subList(start, start + k));
    ListNode dummy = new ListNode(0), tail = dummy;
    for (ListNode node : nodes) { tail.next = node; tail = node; }
    tail.next = null;
    return dummy.next;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(n)

### Approach 4: Iterative In-Place Reversal (Optimized)

Find the `k`-th node of each group, reverse the group with the three-pointer technique and reconnect it to the previous group and to the rest. Constant space. This is the version implemented in the repository.

```java
public static ListNode reverseKGroup(ListNode head, int k) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode groupPrev = dummy;
    while (true) {
        ListNode kth = groupPrev;
        for (int i = 0; i < k && kth != null; i++) kth = kth.next;
        if (kth == null) break;
        ListNode groupNext = kth.next, prev = groupNext, curr = groupPrev.next;
        while (curr != groupNext) { ListNode next = curr.next; curr.next = prev; prev = curr; curr = next; }
        ListNode oldFirst = groupPrev.next;
        groupPrev.next = kth;
        groupPrev = oldFirst;
    }
    return dummy.next;
}
```

- **Time Complexity:** O(n)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Recursion | O(n) | O(n / k) recursion stack |
| 2 | Stack per Group | O(n) | O(k) |
| 3 | Array of Nodes, Reverse Ranges | O(n) | O(n) |
| 4 | Iterative In-Place Reversal (Optimized) | O(n) | O(1) |

### Optimized Solution

The optimized solution is Approach 4 above, which is also the version implemented in this repository.

## Source Code

Uses the shared helper [`ListNode`](../../src/main/java/com/ranga/linkedlist/ListNode.java) (a small `ListNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### ReverseNodesInKGroup.java

```java
import com.ranga.linkedlist.ListNode;

public class ReverseNodesInKGroup {

    public static void main(String[] args) {
        int[][] inputs = {{1, 2, 3, 4, 5}, {1, 2, 3, 4, 5}, {1, 2}, {1, 2, 3, 4, 5, 6}};
        int[] ks = {2, 3, 1, 3};
        for (int i = 0; i < inputs.length; i++) {
            ListNode head = ListNode.of(inputs[i]);
            System.out.println("Input  : head = " + ListNode.toString(head) + ", k = " + ks[i]);
            System.out.println("Output : " + ListNode.toString(reverseKGroup(head, ks[i])));
            System.out.println();
        }
    }

    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode groupPrev = dummy;

        while (true) {
            ListNode kth = groupPrev;
            for (int i = 0; i < k && kth != null; i++) {
                kth = kth.next;
            }
            if (kth == null) {
                break;                              // fewer than k nodes left
            }

            ListNode groupNext = kth.next;
            ListNode prev = groupNext;
            ListNode curr = groupPrev.next;
            while (curr != groupNext) {             // reverse the k nodes of this group
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }

            ListNode oldFirst = groupPrev.next;     // now the last node of the group
            groupPrev.next = kth;
            groupPrev = oldFirst;
        }
        return dummy.next;
    }
}
```
