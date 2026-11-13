package com.ranga.design;

import java.util.Arrays;

/**
 * Design a class {@code NumArray} over an integer array that supports {@code update(index, val)} to set one element and {@code sumRange(left, right)} to return the sum of the elements between the two indices, inclusive. Both operations must be faster than {@code O(n)}.
 *
 * <p><b>Algorithm Strategy (Fenwick Tree (Binary Indexed Tree)):</b>
 * <ol>
 *   <li>A Fenwick tree stores partial sums in an array {@code tree[1..n]} where {@code tree[i]} holds the sum of the last {@code lowbit(i)} elements ending at {@code i}, and {@code lowbit(i) = i & -i}.</li>
 *   <li>To get the prefix sum up to {@code i}, add {@code tree[i]}, then move to {@code i - lowbit(i)} and repeat until {@code i} is 0. This visits at most {@code log n} entries.</li>
 *   <li>To add {@code delta} to element {@code i}, add it to {@code tree[i]}, then move to {@code i + lowbit(i)} and repeat until past {@code n}; those are exactly the entries that cover {@code i}.</li>
 *   <li>{@code update(index, val)} is {@code add(index, val - nums[index])} plus storing the new value. {@code sumRange(l, r)} is {@code prefix(r) - prefix(l - 1)}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 3, 5]} the tree is built with 1-based indices: {@code tree = [_, 1, 4, 5]} ({@code tree[2]} covers elements 1 and 2).</li>
 *   <li>{@code sumRange(0, 2)} = {@code prefix(3) - prefix(0)} = {@code tree[3] + tree[2]} = {@code 5 + 4 = 9}. After {@code update(1, 2)} the delta is {@code -1}, so the sum becomes {@code 8}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log n)} — Both {@code update} and {@code sumRange} touch at most {@code log n} entries; construction is {@code O(n log n)} here.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The tree and a copy of the array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
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
