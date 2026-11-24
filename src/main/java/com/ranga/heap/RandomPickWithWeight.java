package com.ranga.heap;

import java.util.Arrays;
import java.util.Random;

/**
 * Given an array {@code w} of positive integers where {@code w[i]} is the weight of index {@code i}, implement {@code pickIndex()} which returns an index with probability proportional to its weight.
 *
 * <p><b>Algorithm Strategy (Prefix Sums and Binary Search):</b>
 * <ol>
 *   <li>Build prefix sums of the weights: {@code prefix[i] = w[0] + ... + w[i]}. The last value is the total weight.</li>
 *   <li>Draw a random integer {@code target} in {@code [1, total]}.</li>
 *   <li>Binary search the first index whose prefix sum is {@code >= target}.</li>
 *   <li>That index owns the interval {@code (prefix[i-1], prefix[i]]}, whose size equals its weight, so the probability is proportional to it.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code w = [1, 3]}: the prefix sums are {@code [1, 4]}; targets 1 maps to index 0, targets 2 to 4 map to index 1.</li>
 *   <li>Index 1 is picked with probability 3/4.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(log n) per pick} — Binary search on the prefix array; the build is O(n).</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The prefix sum array.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RandomPickWithWeight {

    public static void main(String[] args) {
        int[] weights = {1, 3};
        RandomPickWithWeight picker = new RandomPickWithWeight(weights, new Random(42));
        int[] counts = new int[weights.length];
        int picks = 10000;
        for (int i = 0; i < picks; i++) {
            counts[picker.pickIndex()]++;
        }
        System.out.println("Input  : w = " + Arrays.toString(weights) + ", picks = " + picks);
        System.out.println("Output : counts = " + Arrays.toString(counts));
    }

    private final int[] prefix;
    private final Random random;

    public RandomPickWithWeight(int[] w) {
        this(w, new Random());
    }

    public RandomPickWithWeight(int[] w, Random random) {
        this.random = random;
        prefix = new int[w.length];
        int sum = 0;
        for (int i = 0; i < w.length; i++) {
            sum += w[i];
            prefix[i] = sum;
        }
    }

    public int pickIndex() {
        if (prefix.length == 0) {
            return -1;
        }
        int target = random.nextInt(prefix[prefix.length - 1]) + 1;
        int lo = 0;
        int hi = prefix.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (prefix[mid] >= target) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }
}
