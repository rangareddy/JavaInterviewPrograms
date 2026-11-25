# Flatten Nested List Iterator

Given a nested list of integers where each element is either an integer or a list of integers (which may contain further lists), implement an iterator that flattens it. `next()` returns the next integer and `hasNext()` tells whether any integer remains.

## Algorithm Strategy (Stack of List Iterators)

1. Keep a stack of iterators: the top iterator is the list currently being read; start with the iterator of the top-level list.
2. `hasNext()` repeatedly looks at the top iterator: if it is exhausted, pop it; if its next element is a list, push that list's iterator; if it is an integer, stop and remember it.
3. `next()` returns the remembered integer (calling `hasNext()` first so it is prepared).
4. Each integer is produced after a constant amount of amortised work.

## Example walkthrough

- For `[[1,1],2,[1,1]]` the iterator yields `1, 1, 2, 1, 1`.
- Empty inner lists are skipped by `hasNext()`.

## Complexity Analysis

- **Time Complexity:** `O(1) amortised per element` — Each list and integer is pushed or visited once.
- **Space Complexity:** `O(depth)` — The stack of iterators.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Flatten Everything in the Constructor

Recursively copy all integers into a list and iterate over it. Simple, but uses O(n) extra memory and does all the work up front.

```java
public static java.util.List<Integer> eager(java.util.List<?> l) { java.util.List<Integer> out = new java.util.ArrayList<>(); go(l, out); return out; }
private static void go(java.util.List<?> l, java.util.List<Integer> out) { for (Object o : l) { if (o instanceof Integer) out.add((Integer) o); else go((java.util.List<?>) o, out); } }
```

- **Time Complexity:** O(n) up front
- **Space Complexity:** O(n)

### Approach 2: Stack of Iterators (Optimized)

Walk lazily with a stack of list iterators. This is the version implemented in the repository.

```java
public static java.util.List<Integer> lazy(java.util.List<?> l) {
    java.util.Deque<java.util.Iterator<?>> st = new java.util.ArrayDeque<>(); st.push(l.iterator()); java.util.List<Integer> out = new java.util.ArrayList<>();
    while (!st.isEmpty()) { java.util.Iterator<?> top = st.peek(); if (!top.hasNext()) { st.pop(); continue; } Object o = top.next(); if (o instanceof Integer) out.add((Integer) o); else st.push(((java.util.List<?>) o).iterator()); }
    return out;
}
```

- **Time Complexity:** O(1) amortised per element
- **Space Complexity:** O(depth)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Flatten Everything in the Constructor | O(n) up front | O(n) |
| 2 | Stack of Iterators (Optimized) | O(1) amortised per element | O(depth) |

## Source Code

### FlattenNestedListIterator.java

```java
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class FlattenNestedListIterator {

    public static void main(String[] args) {
        Object[][] inputs = {
            {Arrays.asList(1, 1), 2, Arrays.asList(1, 1)},
            {1, Arrays.asList(4, Arrays.asList(6))},
            {Arrays.asList(), Arrays.asList(Arrays.asList())},
            {7}
        };
        for (Object[] input : inputs) {
            FlattenNestedListIterator it = new FlattenNestedListIterator(Arrays.asList(input));
            List<Integer> flat = new ArrayList<>();
            while (it.hasNext()) {
                flat.add(it.next());
            }
            System.out.println("Input  : nestedList = " + Arrays.deepToString(input));
            System.out.println("Output : " + flat);
            System.out.println();
        }
    }

    private final Deque<Iterator<?>> stack = new ArrayDeque<>();
    private Integer nextValue;

    /** Each element of the list is either an Integer or a List of such elements. */
    public FlattenNestedListIterator(List<?> nestedList) {
        stack.push(nestedList.iterator());
    }

    public int next() {
        if (!hasNext()) {
            throw new java.util.NoSuchElementException();
        }
        int value = nextValue;
        nextValue = null;
        return value;
    }

    public boolean hasNext() {
        while (nextValue == null && !stack.isEmpty()) {
            Iterator<?> top = stack.peek();
            if (!top.hasNext()) {
                stack.pop();
                continue;
            }
            Object element = top.next();
            if (element instanceof Integer) {
                nextValue = (Integer) element;
            } else {
                stack.push(((List<?>) element).iterator());
            }
        }
        return nextValue != null;
    }
}
```

## Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

The idea: a nested list is a tree, and iterating it lazily is a depth-first walk with an explicit stack. The stack holds one iterator per list that is currently open, so memory is proportional to the nesting depth, not the number of integers.

**Input:** `[[1, 1], 2, [1, 1]]`.

| Step | Stack (top on the right) | Action | Output |
|------|---------------------------|--------|--------|
| 1 | [outer] | read `[1, 1]`, push its iterator | |
| 2 | [outer, inner1] | read 1 | 1 |
| 3 | [outer, inner1] | read 1 | 1 |
| 4 | [outer, inner1] | inner1 exhausted, pop | |
| 5 | [outer] | read 2 | 2 |
| 6 | [outer] | read `[1, 1]`, push, then read 1 and 1 | 1, 1 |

**Output:** `[1, 1, 2, 1, 1]`.
