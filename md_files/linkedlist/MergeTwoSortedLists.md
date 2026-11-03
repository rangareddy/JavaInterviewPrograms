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

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Collect, Sort and Rebuild

Copy all values into a list, sort it and build a new linked list. Ignores that the inputs are already sorted.

```java
public static ListNode mergeTwoListsSort(ListNode list1, ListNode list2) {
    List<Integer> values = new ArrayList<>();
    for (ListNode curr = list1; curr != null; curr = curr.next) values.add(curr.val);
    for (ListNode curr = list2; curr != null; curr = curr.next) values.add(curr.val);
    Collections.sort(values);
    ListNode dummy = new ListNode(0), tail = dummy;
    for (int value : values) { tail.next = new ListNode(value); tail = tail.next; }
    return dummy.next;
}
```

- **Time Complexity:** O((n + m) log(n + m))
- **Space Complexity:** O(n + m)

### Approach 2: Recursion

The smaller head becomes the head of the result; its `next` is the merge of the rest.

```java
public static ListNode mergeTwoListsRecursive(ListNode list1, ListNode list2) {
    if (list1 == null) return list2;
    if (list2 == null) return list1;
    if (list1.val <= list2.val) {
        list1.next = mergeTwoListsRecursive(list1.next, list2);
        return list1;
    }
    list2.next = mergeTwoListsRecursive(list1, list2.next);
    return list2;
}
```

- **Time Complexity:** O(n + m)
- **Space Complexity:** O(n + m) recursion stack

### Approach 3: Priority Queue of the Two Heads

Put the two heads into a min-heap and repeatedly take the smallest node, pushing its successor. This is the two-list special case of the k-way heap merge and generalizes to any number of lists.

```java
public static ListNode mergeTwoListsHeap(ListNode list1, ListNode list2) {
    PriorityQueue<ListNode> heap = new PriorityQueue<>(Comparator.comparingInt((ListNode n) -> n.val));
    if (list1 != null) heap.add(list1);
    if (list2 != null) heap.add(list2);
    ListNode dummy = new ListNode(0), tail = dummy;
    while (!heap.isEmpty()) {
        ListNode node = heap.poll();
        tail.next = node;
        tail = node;
        if (node.next != null) heap.add(node.next);
    }
    return dummy.next;
}
```

- **Time Complexity:** O((n + m) log 2) = O(n + m)
- **Space Complexity:** O(1)

### Approach 4: Insert the Nodes of One List into the Other

Take the nodes of the second list one at a time and insert each at its sorted position in the first list. Simple, but quadratic because every insertion walks the first list.

```java
public static ListNode mergeTwoListsInsert(ListNode list1, ListNode list2) {
    if (list1 == null) return list2;
    ListNode head = list1;
    while (list2 != null) {
        ListNode next = list2.next;
        list2.next = null;
        head = insert(head, list2);
        list2 = next;
    }
    return head;
}

private static ListNode insert(ListNode head, ListNode node) {
    if (node.val < head.val) { node.next = head; return node; }
    ListNode curr = head;
    while (curr.next != null && curr.next.val <= node.val) curr = curr.next;
    node.next = curr.next;
    curr.next = node;
    return head;
}
```

- **Time Complexity:** O(n * m)
- **Space Complexity:** O(1)

### Approach 5: Merge Arrays, Then Rebuild the List

Copy both lists into arrays, merge the arrays with two indexes and build a new list from the merged array. Works with any list implementation, but creates new nodes.

```java
public static ListNode mergeTwoListsArrays(ListNode list1, ListNode list2) {
    List<Integer> a = new ArrayList<>(), b = new ArrayList<>();
    for (ListNode c = list1; c != null; c = c.next) a.add(c.val);
    for (ListNode c = list2; c != null; c = c.next) b.add(c.val);
    ListNode dummy = new ListNode(0), tail = dummy;
    int i = 0, j = 0;
    while (i < a.size() || j < b.size()) {
        int value = (j == b.size() || (i < a.size() && a.get(i) <= b.get(j))) ? a.get(i++) : b.get(j++);
        tail.next = new ListNode(value);
        tail = tail.next;
    }
    return dummy.next;
}
```

- **Time Complexity:** O(n + m)
- **Space Complexity:** O(n + m)

### Approach 6: Merge from the Tail

Reverse both lists so that they are in descending order, then repeatedly take the larger head and put it in front of the result. Because each node is prepended, the finished list is ascending.

```java
public static ListNode mergeTwoListsFromTail(ListNode list1, ListNode list2) {
    ListNode a = reverse(list1), b = reverse(list2), result = null;
    while (a != null || b != null) {
        ListNode take;
        if (b == null || (a != null && a.val >= b.val)) { take = a; a = a.next; }
        else { take = b; b = b.next; }
        take.next = result;                     // prepend the larger value
        result = take;
    }
    return result;
}

private static ListNode reverse(ListNode head) {
    ListNode prev = null;
    while (head != null) { ListNode next = head.next; head.next = prev; prev = head; head = next; }
    return prev;
}
```

- **Time Complexity:** O(n + m)
- **Space Complexity:** O(1)

### Approach 7: Iterative with a Dummy Head (Optimized)

Splice the nodes of both lists in order after a dummy node. This is the version implemented in the repository.

```java
public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
    ListNode dummy = new ListNode(0), tail = dummy;
    while (list1 != null && list2 != null) {
        if (list1.val <= list2.val) { tail.next = list1; list1 = list1.next; }
        else { tail.next = list2; list2 = list2.next; }
        tail = tail.next;
    }
    tail.next = (list1 != null) ? list1 : list2;
    return dummy.next;
}
```

- **Time Complexity:** O(n + m)
- **Space Complexity:** O(1)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Collect, Sort and Rebuild | O((n + m) log(n + m)) | O(n + m) |
| 2 | Recursion | O(n + m) | O(n + m) recursion stack |
| 3 | Priority Queue of the Two Heads | O((n + m) log 2) = O(n + m) | O(1) |
| 4 | Insert the Nodes of One List into the Other | O(n * m) | O(1) |
| 5 | Merge Arrays, Then Rebuild the List | O(n + m) | O(n + m) |
| 6 | Merge from the Tail | O(n + m) | O(1) |
| 7 | Iterative with a Dummy Head (Optimized) | O(n + m) | O(1) |

### Optimized Solution

The optimized solution is Approach 7 above, which is also the version implemented in this repository.

## Source Code

Uses the shared helper [`ListNode`](../../src/main/java/com/ranga/linkedlist/ListNode.java) (a small `ListNode` with `of(...)` and `toString(...)` helpers for building and printing test data).

### MergeTwoSortedLists.java

```java
import com.ranga.linkedlist.ListNode;

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
