package com.ranga.design;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Design a {@code Leaderboard} class with three functions: {@code addScore(playerId, score)} adds {@code score} to the player's total (creating the player at {@code score} if new), {@code top(K)} returns the sum of the scores of the top {@code K} players, and {@code reset(playerId)} sets the player's score back to {@code 0}, removing the player. A player is guaranteed to exist before {@code reset} is called.
 *
 * <p><b>Algorithm Strategy (Map for Scores, Bounded Heap for Top K):</b>
 * <ol>
 *   <li>Keep a {@code HashMap<Integer, Integer>} from player id to total score; {@code addScore} and {@code reset} are O(1).</li>
 *   <li>For {@code top(K)}, scan the scores with a min-heap of size {@code K}: offer each score and drop the smallest whenever the heap grows past {@code K}.</li>
 *   <li>The heap then holds the {@code K} largest scores; add them up.</li>
 *   <li>This costs {@code O(n log K)} and avoids sorting all players.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>After {@code addScore(1, 73)}, {@code addScore(2, 56)}, {@code addScore(3, 39)}, {@code addScore(4, 51)}, {@code addScore(5, 4)}: {@code top(1)} is 73.</li>
 *   <li>After {@code reset(1)}, {@code reset(2)}, {@code addScore(2, 51)}, the scores are 51, 39, 51, 4 and the top three sum to 141.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1) update, O(n log K) top} — {@code top} scans all players with a heap of size {@code K}.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The score map plus a heap of up to {@code K} elements.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class DesignALeaderboard {

    public static void main(String[] args) {
        DesignALeaderboard board = new DesignALeaderboard();
        board.addScore(1, 73);
        board.addScore(2, 56);
        board.addScore(3, 39);
        board.addScore(4, 51);
        board.addScore(5, 4);
        System.out.println("Input  : addScore(1, 73), addScore(2, 56), addScore(3, 39), addScore(4, 51), addScore(5, 4), top(1)");
        System.out.println("Output : " + board.top(1));
        System.out.println();
        board.reset(1);
        board.reset(2);
        board.addScore(2, 51);
        System.out.println("Input  : reset(1), reset(2), addScore(2, 51), top(3)");
        System.out.println("Output : " + board.top(3));
    }

    private final Map<Integer, Integer> scores = new HashMap<>();

    public void addScore(int playerId, int score) {
        scores.merge(playerId, score, Integer::sum);
    }

    public int top(int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int score : scores.values()) {
            heap.offer(score);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        int sum = 0;
        for (int score : heap) {
            sum += score;
        }
        return sum;
    }

    public void reset(int playerId) {
        scores.remove(playerId);
    }
}
