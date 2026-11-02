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

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Collect, Sort and Rebuild

Copy every value into one list, sort it and build the result. Ignores the sortedness of the input lists.

```java
public static ListNode mergeKListsSort(ListNode[] lists) {
    List<Integer> values = new ArrayList<>();
    for (ListNode head : lists) {
        for (ListNode curr = head; curr != null; curr = curr.next) values.add(curr.val);
    }
    Collections.sort(values);
    ListNode dummy = new ListNode(0), tail = dummy;
    for (int value : values) { tail.next = new ListNode(value); tail = tail.next; }
    return dummy.next;
}
```

- **Time Complexity:** O(N log N)
- **Space Complexity:** O(N)

### Approach 2: Merge One List After Another

Merge the lists into an accumulated result one at a time with the two-list merge. The accumulated list is re-walked for every list.

```java
public static ListNode mergeKListsSequential(ListNode[] lists) {
    ListNode result = null;
    for (ListNode list : lists) result = merge(result, list);
    return result;
}

private static ListNode merge(ListNode a, ListNode b) {
    ListNode dummy = new ListNode(0), tail = dummy;
    while (a != null && b != null) {
        if (a.val <= b.val) { tail.next = a; a = a.next; } else { tail.next = b; b = b.next; }
        tail = tail.next;
    }
    tail.next = (a != null) ? a : b;
    return dummy.next;
}
```

- **Time Complexity:** O(k * N)
- **Space Complexity:** O(1)

### Approach 3: Divide and Conquer (Optimized)

Merge the lists in pairs, then merge the results in pairs again, like a tournament. Each node takes part in only `log k` merges and no extra memory is needed.

```java
public static ListNode mergeKListsDivide(ListNode[] lists) {
    if (lists.length == 0) return null;
    for (int step = 1; step < lists.length; step *= 2) {
        for (int i = 0; i + step < lists.length; i += 2 * step) {
            lists[i] = merge(lists[i], lists[i + step]);
        }
    }
    return lists[0];
}
```

- **Time Complexity:** O(N log k)
- **Space Complexity:** O(1)

### Approach 4: Min-Heap of the List Heads (Optimized)

Keep the current head of every list in a priority queue and repeatedly take the smallest. This is the version implemented in the repository.

```java
public static ListNode mergeKLists(ListNode[] lists) {
    PriorityQueue<ListNode> heap = new PriorityQueue<>(Comparator.comparingInt((ListNode n) -> n.val));
    for (ListNode head : lists) if (head != null) heap.add(head);
    ListNode dummy = new ListNode(0), tail = dummy;
    while (!heap.isEmpty()) {
        ListNode smallest = heap.poll();
        tail.next = smallest;
        tail = tail.next;
        if (smallest.next != null) heap.add(smallest.next);
    }
    return dummy.next;
}
```

- **Time Complexity:** O(N log k)
- **Space Complexity:** O(k)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Collect, Sort and Rebuild | O(N log N) | O(N) |
| 2 | Merge One List After Another | O(k * N) | O(1) |
| 3 | Divide and Conquer (Optimized) | O(N log k) | O(1) |
| 4 | Min-Heap of the List Heads (Optimized) | O(N log k) | O(k) |

### Optimized Solution

The optimized solution is Approach 3 and 4 above. The version implemented in this repository is Approach 4.

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
