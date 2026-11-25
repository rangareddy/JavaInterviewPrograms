package com.ranga.dp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A frog crosses a river by jumping on stones at the positions in the sorted array {@code stones} (the first stone is at position 0). The frog starts on the first stone and its first jump must be 1 unit. If its last jump was {@code k} units, the next jump must be {@code k - 1}, {@code k} or {@code k + 1} units, and it can only land on a stone. Return {@code true} if the frog can reach the last stone.
 *
 * <p><b>Algorithm Strategy (Set of Jump Sizes per Stone):</b>
 * <ol>
 *   <li>Map each stone position to the set of jump sizes with which the frog can arrive there. Start with {@code {0}} on the first stone.</li>
 *   <li>For each stone in order and for each arriving jump size {@code k}, try the jumps {@code k - 1}, {@code k} and {@code k + 1} (only positive ones).</li>
 *   <li>If the landing position is a stone, add the jump size to that stone's set.</li>
 *   <li>The frog can cross if the last stone's set is not empty.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [0,1,3,5,6,8,12,17]}: 0 -> 1 (jump 1) -> 3 (jump 2) -> 5 (jump 2) -> 8 (jump 3) -> 12 (jump 4) -> 17 (jump 5).</li>
 *   <li>Result: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n^2)} — Each stone has at most n distinct jump sizes.</li>
 *   <li><b>Space Complexity:</b> {@code O(n^2)} — The sets of jump sizes.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class FrogJump {

    public static void main(String[] args) {
        int[][] inputs = {{0, 1, 3, 5, 6, 8, 12, 17}, {0, 1, 2, 3, 4, 8, 9, 11}, {0, 1}, {0, 2}};
        for (int[] stones : inputs) {
            System.out.println("Input  : stones = " + Arrays.toString(stones));
            System.out.println("Output : " + canCross(stones));
            System.out.println();
        }
    }

    public static boolean canCross(int[] stones) {
        if (stones == null || stones.length == 0) {
            return false;
        }
        Map<Integer, Set<Integer>> jumps = new HashMap<>();
        for (int stone : stones) {
            jumps.put(stone, new HashSet<>());
        }
        jumps.get(stones[0]).add(0);
        for (int stone : stones) {
            for (int k : jumps.get(stone)) {
                for (int next = k - 1; next <= k + 1; next++) {
                    if (next > 0 && jumps.containsKey(stone + next)) {
                        jumps.get(stone + next).add(next);
                    }
                }
            }
        }
        return !jumps.get(stones[stones.length - 1]).isEmpty();
    }
}
