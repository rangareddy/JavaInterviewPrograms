package com.ranga.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an integer array {@code nums} of size {@code n}, return all elements that appear more than {@code n / 3} times.
 *
 * <p><b>Algorithm Strategy (Extended Boyer-Moore Voting):</b>
 * <ol>
 *   <li>At most two values can appear more than {@code n / 3} times, because three such values would need more than {@code n} slots.</li>
 *   <li>Keep two candidates with a counter each. A value equal to a candidate increments that counter.</li>
 *   <li>A value that matches neither candidate takes over a candidate whose counter is 0; if both counters are positive it decrements both (three different values cancel out).</li>
 *   <li>The two survivors are only candidates. A second pass counts them and keeps the ones above {@code n / 3}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [1, 1, 1, 3, 3, 2, 2, 2]} the threshold is {@code 8 / 3 = 2}.</li>
 *   <li>Voting pass: 1 reaches count 3, 3 takes the second slot (count 2), then each 2 cancels one vote from both until the second slot is re-taken by 2.</li>
 *   <li>Candidates 1 and 2 both occur 3 times, which is more than 2. Result: {@code [1, 2]}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Two linear passes over the array.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Two candidates and two counters.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class MajorityElementII {

    public static void main(String[] args) {
        int[][] inputs = {{3, 2, 3}, {1}, {1, 2}, {1, 1, 1, 3, 3, 2, 2, 2}};
        for (int[] nums : inputs) {
            System.out.println("Input  : nums = " + Arrays.toString(nums));
            System.out.println("Output : " + majorityElement(nums));
            System.out.println();
        }
    }

    public static List<Integer> majorityElement(int[] nums) {
        List<Integer> result = new ArrayList<>();
        if (nums.length == 0) {
            return result;
        }
        int candidate1 = nums[0];
        int candidate2 = nums[0];
        int count1 = 0;
        int count2 = 0;
        for (int num : nums) {
            if (num == candidate1) {
                count1++;
            } else if (num == candidate2) {
                count2++;
            } else if (count1 == 0) {
                candidate1 = num;
                count1 = 1;
            } else if (count2 == 0) {
                candidate2 = num;
                count2 = 1;
            } else {
                count1--;
                count2--;
            }
        }
        count1 = 0;
        count2 = 0;
        for (int num : nums) {
            if (num == candidate1) {
                count1++;
            } else if (num == candidate2) {
                count2++;
            }
        }
        if (count1 > nums.length / 3) {
            result.add(candidate1);
        }
        if (count2 > nums.length / 3 && candidate2 != candidate1) {
            result.add(candidate2);
        }
        return result;
    }
}
