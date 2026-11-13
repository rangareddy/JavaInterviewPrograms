# Range Sum Query - Mutable

Design a class `NumArray` over an integer array that supports `update(index, val)` to set one element and `sumRange(left, right)` to return the sum of the elements between the two indices, inclusive. Both operations must be faster than `O(n)`.

## Algorithm Strategy (Fenwick Tree (Binary Indexed Tree))

1. A Fenwick tree stores partial sums in an array `tree[1..n]` where `tree[i]` holds the sum of the last `lowbit(i)` elements ending at `i`, and `lowbit(i) = i & -i`.
2. To get the prefix sum up to `i`, add `tree[i]`, then move to `i - lowbit(i)` and repeat until `i` is 0. This visits at most `log n` entries.
3. To add `delta` to element `i`, add it to `tree[i]`, then move to `i + lowbit(i)` and repeat until past `n`; those are exactly the entries that cover `i`.
4. `update(index, val)` is `add(index, val - nums[index])` plus storing the new value. `sumRange(l, r)` is `prefix(r) - prefix(l - 1)`.

## Example walkthrough

- For `[1, 3, 5]` the tree is built with 1-based indices: `tree = [_, 1, 4, 5]` (`tree[2]` covers elements 1 and 2).
- `sumRange(0, 2)` = `prefix(3) - prefix(0)` = `tree[3] + tree[2]` = `5 + 4 = 9`. After `update(1, 2)` the delta is `-1`, so the sum becomes `8`.

## Complexity Analysis

- **Time Complexity:** `O(log n)` — Both `update` and `sumRange` touch at most `log n` entries; construction is `O(n log n)` here.
- **Space Complexity:** `O(n)` — The tree and a copy of the array.

## Approaches

The same problem can be solved in several ways. The approach marked (Optimized) is the one to use in practice.

### Approach 1: Plain Array

Store the array; `update` is a single write and `sumRange` adds up the elements.

```java
class PlainNumArray {
    private final int[] nums;

    PlainNumArray(int[] nums) { this.nums = nums.clone(); }

    void update(int index, int val) { nums[index] = val; }

    int sumRange(int left, int right) {
        int sum = 0;
        for (int i = left; i <= right; i++) sum += nums[i];
        return sum;
    }
}
```

- **Time Complexity:** update O(1), sumRange O(n)
- **Space Complexity:** O(n)

### Approach 2: Segment Tree (Iterative, Bottom-Up)

Store the elements at the leaves of an array of size `2n` and the sum of each pair of children in the parent. Updates walk up from a leaf; queries combine the nodes covering the range from both ends.

```java
class SegmentTreeNumArray {
    private final int n;
    private final int[] tree;

    SegmentTreeNumArray(int[] nums) {
        n = nums.length;
        tree = new int[2 * n];
        for (int i = 0; i < n; i++) tree[n + i] = nums[i];
        for (int i = n - 1; i > 0; i--) tree[i] = tree[2 * i] + tree[2 * i + 1];
    }

    void update(int index, int val) {
        int i = index + n;
        tree[i] = val;
        while (i > 1) {
            i /= 2;
            tree[i] = tree[2 * i] + tree[2 * i + 1];
        }
    }

    int sumRange(int left, int right) {
        int sum = 0;
        int l = left + n, r = right + n + 1;
        while (l < r) {
            if ((l & 1) == 1) sum += tree[l++];
            if ((r & 1) == 1) sum += tree[--r];
            l /= 2;
            r /= 2;
        }
        return sum;
    }
}
```

- **Time Complexity:** O(log n) for both operations
- **Space Complexity:** O(n)

### Approach 3: Fenwick Tree (Optimized)

Keep partial sums indexed by the lowest set bit so both operations touch at most `log n` entries, with less memory and code than a segment tree. This is the version implemented in the repository.

```java
private final int[] nums;
private final int[] tree;

public RangeSumQueryMutable(int[] nums) {
    this.nums = new int[nums.length];
    this.tree = new int[nums.length + 1];
    for (int i = 0; i < nums.length; i++) {
        update(i, nums[i]);
    }
}

public void update(int index, int val) {
    int delta = val - nums[index];
    nums[index] = val;
    for (int i = index + 1; i < tree.length; i += i & -i) {
        tree[i] += delta;
    }
}

public int sumRange(int left, int right) {
    return prefix(right + 1) - prefix(left);
}

private int prefix(int count) {
    int sum = 0;
    for (int i = count; i > 0; i -= i & -i) {
        sum += tree[i];
    }
    return sum;
}
```

- **Time Complexity:** O(log n) per operation
- **Space Complexity:** O(n)

### Comparison

| # | Approach | Time | Space |
|---|----------|------|-------|
| 1 | Plain Array | update O(1), sumRange O(n) | O(n) |
| 2 | Segment Tree (Iterative, Bottom-Up) | O(log n) for both operations | O(n) |
| 3 | Fenwick Tree (Optimized) | O(log n) per operation | O(n) |

### Optimized Solution

The optimized solution is Approach 3 above, which is also the version implemented in this repository.

## Source Code

### RangeSumQueryMutable.java

```java
import java.util.Arrays;

public class RangeSumQueryMutable {

    public static void main(String[] args) {
        int[] nums = {1, 3, 5};
        RangeSumQueryMutable numArray = new RangeSumQueryMutable(nums);
        System.out.println("Input  : nums = " + Arrays.toString(nums) + ", sumRange(0, 2)");
        System.out.println("Output : " + numArray.sumRange(0, 2));
        System.out.println();
        System.out.println("Input  : update(1, 2), sumRange(0, 2)");
        numArray.update(1, 2);
        System.out.println("Output : " + numArray.sumRange(0, 2));
    }

    private final int[] nums;
    private final int[] tree;

    public RangeSumQueryMutable(int[] nums) {
        this.nums = new int[nums.length];
        this.tree = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            update(i, nums[i]);
        }
    }

    public void update(int index, int val) {
        int delta = val - nums[index];
        nums[index] = val;
        for (int i = index + 1; i < tree.length; i += i & -i) {
            tree[i] += delta;
        }
    }

    public int sumRange(int left, int right) {
        return prefix(right + 1) - prefix(left);
    }

    private int prefix(int count) {
        int sum = 0;
        for (int i = count; i > 0; i -= i & -i) {
            sum += tree[i];
        }
        return sum;
    }
}
```
