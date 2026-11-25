package com.ranga.design;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

/**
 * Given a nested list of integers where each element is either an integer or a list of integers (which may contain further lists), implement an iterator that flattens it. {@code next()} returns the next integer and {@code hasNext()} tells whether any integer remains.
 *
 * <p><b>Algorithm Strategy (Stack of List Iterators):</b>
 * <ol>
 *   <li>Keep a stack of iterators: the top iterator is the list currently being read; start with the iterator of the top-level list.</li>
 *   <li>{@code hasNext()} repeatedly looks at the top iterator: if it is exhausted, pop it; if its next element is a list, push that list's iterator; if it is an integer, stop and remember it.</li>
 *   <li>{@code next()} returns the remembered integer (calling {@code hasNext()} first so it is prepared).</li>
 *   <li>Each integer is produced after a constant amount of amortised work.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[1,1],2,[1,1]]} the iterator yields {@code 1, 1, 2, 1, 1}.</li>
 *   <li>Empty inner lists are skipped by {@code hasNext()}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1) amortised per element} — Each list and integer is pushed or visited once.</li>
 *   <li><b>Space Complexity:</b> {@code O(depth)} — The stack of iterators.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
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
