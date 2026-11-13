# Design a Min/Max Heap

Implement a binary heap from scratch that can work as a min-heap or a max-heap, with `add(value)`, `peek()`, `poll()`, `size()` and `isEmpty()`. `add` and `poll` must run in `O(log n)` and `peek` in `O(1)`.

## Algorithm Strategy (Array-Backed Complete Binary Tree with Sift Up and Sift Down)

1. Store a complete binary tree in an array: the children of index `i` are `2i + 1` and `2i + 2`, and its parent is `(i - 1) / 2`. No pointers are needed.
2. The heap property says every parent is ordered before its children (smaller for a min-heap, larger for a max-heap), so the root is always the extreme element.
3. `add` appends the value at the end and sifts it up: while it is ordered before its parent, swap them.
4. `poll` takes the root, moves the last element to the root and sifts it down: swap with the better of its two children until the heap property holds again.
5. One class serves both kinds by using a single comparison, `before(a, b)`, that is `a < b` for a min-heap and `a > b` for a max-heap.

## Example walkthrough

- A min-heap after adding 5, 3, 8, 1 is the array `[1, 3, 8, 5]`: 5 is placed first, 3 swaps above it, 8 is a right child, and 1 is added below 3 and sifts up past 3 to the root, pushing 3 down.
- `poll()` returns 1, moves the last element 5 to the root and sifts down: `[3, 5, 8]`.

## Complexity Analysis

- **Time Complexity:** `O(log n)` — `add` and `poll`; `peek` is `O(1)` and building by repeated `add` costs `O(n log n)`.
- **Space Complexity:** `O(n)` — The backing array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Sorted Array

Keep the elements sorted; `peek` and `poll` are cheap but every `add` shifts elements to keep the order.

```java
class SortedArrayHeap {
    private final List<Integer> values = new ArrayList<>();
    private final boolean minHeap;

    SortedArrayHeap(boolean minHeap) { this.minHeap = minHeap; }

    void add(int v) {
        int index = Collections.binarySearch(values, v);
        values.add(index >= 0 ? index : -index - 1, v);
    }

    int peek() { return minHeap ? values.get(0) : values.get(values.size() - 1); }

    int poll() { return minHeap ? values.remove(0) : values.remove(values.size() - 1); }
}
```

- **Time Complexity:** add O(n), peek O(1), poll O(n) for the min end
- **Space Complexity:** O(n)

### Approach 2: Binary Heap in an Array (Optimized)

Keep a complete binary tree in an array and restore the heap property by sifting a single element up or down. This is the version implemented in the repository.

```java
public void add(int value) {
    if (size == data.length) {
        data = Arrays.copyOf(data, size * 2);
    }
    data[size] = value;
    siftUp(size++);
}

public int peek() {
    if (size == 0) {
        throw new IllegalStateException("heap is empty");
    }
    return data[0];
}

public int poll() {
    int top = peek();
    data[0] = data[--size];
    if (size > 0) {
        siftDown(0);
    }
    return top;
}

public int size() {
    return size;
}

public boolean isEmpty() {
    return size == 0;
}

private boolean before(int a, int b) {
    return minHeap ? a < b : a > b;
}

private void siftUp(int i) {
    while (i > 0) {
        int parent = (i - 1) / 2;
        if (!before(data[i], data[parent])) {
            break;
        }
        swap(i, parent);
        i = parent;
    }
}

private void siftDown(int i) {
    while (true) {
        int best = i;
        int left = 2 * i + 1;
        int right = left + 1;
        if (left < size && before(data[left], data[best])) {
            best = left;
        }
        if (right < size && before(data[right], data[best])) {
            best = right;
        }
        if (best == i) {
            return;
        }
        swap(i, best);
        i = best;
    }
}

private void swap(int a, int b) {
    int temp = data[a];
    data[a] = data[b];
    data[b] = temp;
}
```

- **Time Complexity:** add and poll O(log n), peek O(1)
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Sorted Array | add O(n), peek O(1), poll O(n) for the min end | O(n) |
| 2 | Binary Heap in an Array (Optimized) | add and poll O(log n), peek O(1) | O(n) |

### Optimized Solution

The optimized solution is Approach 2 above, which is also the version implemented in this repository.

## Source Code

### BinaryHeap.java

```java
import java.util.Arrays;

public class BinaryHeap {

    private int[] data = new int[8];
    private int size;
    private final boolean minHeap;

    public BinaryHeap(boolean minHeap) {
        this.minHeap = minHeap;
    }

    public static void main(String[] args) {
        int[] values = {5, 3, 8, 1, 9, 2};
        BinaryHeap min = new BinaryHeap(true);
        BinaryHeap max = new BinaryHeap(false);
        for (int v : values) {
            min.add(v);
            max.add(v);
        }
        System.out.println("Input  : add " + Arrays.toString(values));
        System.out.println("Output : min peek = " + min.peek() + ", max peek = " + max.peek());
        System.out.println();
        StringBuilder minOrder = new StringBuilder();
        StringBuilder maxOrder = new StringBuilder();
        while (!min.isEmpty()) {
            minOrder.append(minOrder.length() == 0 ? "" : ", ").append(min.poll());
            maxOrder.append(maxOrder.length() == 0 ? "" : ", ").append(max.poll());
        }
        System.out.println("Input  : poll until empty");
        System.out.println("Output : min order = [" + minOrder + "], max order = [" + maxOrder + "]");
    }

    public void add(int value) {
        if (size == data.length) {
            data = Arrays.copyOf(data, size * 2);
        }
        data[size] = value;
        siftUp(size++);
    }

    public int peek() {
        if (size == 0) {
            throw new IllegalStateException("heap is empty");
        }
        return data[0];
    }

    public int poll() {
        int top = peek();
        data[0] = data[--size];
        if (size > 0) {
            siftDown(0);
        }
        return top;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private boolean before(int a, int b) {
        return minHeap ? a < b : a > b;
    }

    private void siftUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (!before(data[i], data[parent])) {
                break;
            }
            swap(i, parent);
            i = parent;
        }
    }

    private void siftDown(int i) {
        while (true) {
            int best = i;
            int left = 2 * i + 1;
            int right = left + 1;
            if (left < size && before(data[left], data[best])) {
                best = left;
            }
            if (right < size && before(data[right], data[best])) {
                best = right;
            }
            if (best == i) {
                return;
            }
            swap(i, best);
            i = best;
        }
    }

    private void swap(int a, int b) {
        int temp = data[a];
        data[a] = data[b];
        data[b] = temp;
    }
}
```
