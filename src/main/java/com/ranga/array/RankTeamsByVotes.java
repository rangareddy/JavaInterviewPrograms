package com.ranga.array;

import java.util.Arrays;

/**
 * In a ranking system, every voter ranks all teams from first to last. Teams are ordered by the number of first-place votes; ties are broken by second-place votes, and so on. If teams are tied in every position, they are ordered alphabetically. Given an array of vote strings, return the final ranking as a string.
 *
 * <p><b>Algorithm Strategy (Count Votes per Position and Sort):</b>
 * <ol>
 *   <li>For each team letter, keep an array {@code count[team][position]} of how many times it was placed in each position.</li>
 *   <li>Fill it by walking every vote and every character position.</li>
 *   <li>Sort the teams with a comparator that compares the counts from position 0 onward (more votes first), falling back to the letter.</li>
 *   <li>Join the sorted letters.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For votes {@code ABC, ACB, ABC, ACB, ACB}: A is first 5 times; C is second 3 times and B second 2 times.</li>
 *   <li>Result: {@code ACB}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(V * T + T^2 log T)} — {@code V} votes of {@code T} teams, then a sort comparing up to {@code T} positions.</li>
 *   <li><b>Space Complexity:</b> {@code O(T^2)} — The count matrix.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RankTeamsByVotes {

    public static void main(String[] args) {
        String[][] inputs = {{"ABC", "ACB", "ABC", "ACB", "ACB"}, {"WXYZ", "XYZW"}, {"ZMNAGUEDSJYLBOPHRQICWFXTVK"}, {"BCA", "CAB", "CBA", "ABC", "ACB", "BAC"}};
        for (String[] votes : inputs) {
            System.out.println("Input  : votes = " + Arrays.toString(votes));
            System.out.println("Output : " + rankTeams(votes));
            System.out.println();
        }
    }

    public static String rankTeams(String[] votes) {
        if (votes == null || votes.length == 0) {
            return "";
        }
        int teams = votes[0].length();
        int[][] count = new int[26][teams];
        for (String vote : votes) {
            for (int position = 0; position < teams; position++) {
                count[vote.charAt(position) - 'A'][position]++;
            }
        }
        Character[] order = new Character[teams];
        for (int i = 0; i < teams; i++) {
            order[i] = votes[0].charAt(i);
        }
        Arrays.sort(order, (a, b) -> {
            for (int position = 0; position < teams; position++) {
                int diff = count[b - 'A'][position] - count[a - 'A'][position];
                if (diff != 0) {
                    return diff;
                }
            }
            return a - b;
        });
        StringBuilder result = new StringBuilder();
        for (char team : order) {
            result.append(team);
        }
        return result.toString();
    }
}
