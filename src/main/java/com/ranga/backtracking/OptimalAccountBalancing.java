package com.ranga.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given transactions {@code [from, to, amount]} meaning that person {@code from} gave {@code amount} to person {@code to}, return the minimum number of transactions needed to settle all debts, where a transaction can move any amount between any two people.
 *
 * <p><b>Algorithm Strategy (Backtracking over Net Balances):</b>
 * <ol>
 *   <li>Compute each person's net balance (money received minus money given) and keep only the non-zero balances.</li>
 *   <li>Backtrack: take the first non-zero balance {@code debts[i]} and try to settle it against each later balance with the opposite sign, by transferring everything to that person ({@code debts[j] += debts[i]}).</li>
 *   <li>Recurse on the next non-zero balance, count one transaction, then undo the change.</li>
 *   <li>Return the minimum number of transactions over all choices. Skip a branch when the balance of the partner has the same value as one already tried.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [[0,1,10],[2,0,5]]}: person 0 is net -5, person 1 is +10, person 2 is -5.</li>
 *   <li>Two transactions settle everything: {@code 0 -> 1} (5) and {@code 2 -> 1} (5). Result: {@code 2}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n!) worst case} — Backtracking over the non-zero balances.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The balances and recursion.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class OptimalAccountBalancing {

    public static void main(String[] args) {
        int[][][] inputs = {{{0, 1, 10}, {2, 0, 5}}, {{0, 1, 10}, {1, 0, 1}, {1, 2, 5}, {2, 0, 5}}, {{0, 1, 5}, {1, 2, 5}}, {}};
        for (int[][] transactions : inputs) {
            System.out.println("Input  : transactions = " + Arrays.deepToString(transactions));
            System.out.println("Output : " + minTransfers(transactions));
            System.out.println();
        }
    }

    public static int minTransfers(int[][] transactions) {
        if (transactions == null || transactions.length == 0) {
            return 0;
        }
        Map<Integer, Integer> balance = new HashMap<>();
        for (int[] t : transactions) {
            balance.merge(t[0], -t[2], Integer::sum);
            balance.merge(t[1], t[2], Integer::sum);
        }
        List<Integer> debts = new ArrayList<>();
        for (int value : balance.values()) {
            if (value != 0) {
                debts.add(value);
            }
        }
        return settle(debts, 0);
    }

    private static int settle(List<Integer> debts, int start) {
        while (start < debts.size() && debts.get(start) == 0) {
            start++;
        }
        if (start == debts.size()) {
            return 0;
        }
        int best = Integer.MAX_VALUE;
        for (int i = start + 1; i < debts.size(); i++) {
            if (debts.get(i) * debts.get(start) < 0) {
                debts.set(i, debts.get(i) + debts.get(start));
                best = Math.min(best, 1 + settle(debts, start + 1));
                debts.set(i, debts.get(i) - debts.get(start));
            }
        }
        return best;
    }
}
