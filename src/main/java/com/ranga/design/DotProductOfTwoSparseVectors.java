package com.ranga.design;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Implement a {@code SparseVector} class built from an integer array that mostly holds zeros. It must store the vector efficiently and offer {@code dotProduct(other)}, the dot product of this vector and another sparse vector.
 *
 * <p><b>Algorithm Strategy (Index to Value Map of Non-Zeros):</b>
 * <ol>
 *   <li>In the constructor keep only the non-zero entries as {@code (index, value)} pairs.</li>
 *   <li>{@code dotProduct} iterates over the smaller of the two non-zero sets.</li>
 *   <li>For each entry look the index up in the other vector and multiply when present.</li>
 *   <li>The sum of those products is the dot product; zero entries never contribute.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 0, 0, 2, 3]} and {@code [0, 3, 0, 4, 0]} the only shared non-zero index is 3, giving {@code 2 * 4}.</li>
 *   <li>Result: {@code 8}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(min(a, b))} — {@code a} and {@code b} are the numbers of non-zero entries.</li>
 *   <li><b>Space Complexity:</b> {@code O(non-zeros)} — Only non-zero entries are stored.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class DotProductOfTwoSparseVectors {

    public static void main(String[] args) {
        int[][][] pairs = {{{1, 0, 0, 2, 3}, {0, 3, 0, 4, 0}}, {{0, 1, 0, 0, 0}, {0, 0, 0, 0, 2}}, {{0, 1, 0, 0, 2, 0, 0}, {1, 0, 0, 0, 3, 0, 4}}};
        for (int[][] pair : pairs) {
            System.out.println("Input  : nums1 = " + Arrays.toString(pair[0]) + ", nums2 = " + Arrays.toString(pair[1]));
            System.out.println("Output : " + new DotProductOfTwoSparseVectors(pair[0]).dotProduct(new DotProductOfTwoSparseVectors(pair[1])));
            System.out.println();
        }
    }

    private final Map<Integer, Integer> entries = new HashMap<>();

    public DotProductOfTwoSparseVectors(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                entries.put(i, nums[i]);
            }
        }
    }

    public int dotProduct(DotProductOfTwoSparseVectors other) {
        Map<Integer, Integer> small = entries.size() <= other.entries.size() ? entries : other.entries;
        Map<Integer, Integer> large = small == entries ? other.entries : entries;
        int sum = 0;
        for (Map.Entry<Integer, Integer> e : small.entrySet()) {
            Integer value = large.get(e.getKey());
            if (value != null) {
                sum += e.getValue() * value;
            }
        }
        return sum;
    }
}
