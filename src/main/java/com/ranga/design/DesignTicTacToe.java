package com.ranga.design;

/**
 * Design a Tic-Tac-Toe game on an {@code n x n} board for two players. {@code move(row, col, player)} places the mark of {@code player} (1 or 2) on an empty cell and returns the winner after the move: {@code 1} or {@code 2} if that player now has a full row, column or diagonal, and {@code 0} if there is no winner yet. Moves are always valid.
 *
 * <p><b>Algorithm Strategy (Counters per Line):</b>
 * <ol>
 *   <li>Keep one counter for each of the {@code n} rows, {@code n} columns and the two diagonals.</li>
 *   <li>A move by player 1 adds +1 to its row, its column, and its diagonals; player 2 adds -1.</li>
 *   <li>If the absolute value of any of those counters reaches {@code n}, that player has filled the line.</li>
 *   <li>Return the player, or 0 if nothing reached {@code n}.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>On a 3x3 board player 1 plays (0,0), (0,1), (0,2): the row-0 counter reaches +3.</li>
 *   <li>The move at (0,2) returns {@code 1}; earlier moves returned {@code 0}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(1) per move} — Four counters are updated.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The row and column counters.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class DesignTicTacToe {

    public static void main(String[] args) {
        DesignTicTacToe game = new DesignTicTacToe(3);
        System.out.println("Input  : move(0, 0, 1)");
        System.out.println("Output : " + game.move(0, 0, 1));
        System.out.println();
        System.out.println("Input  : move(0, 2, 2), move(2, 2, 1), move(1, 1, 2), move(2, 0, 1), move(1, 0, 2), move(2, 1, 1)");
        System.out.println("Output : " + game.move(0, 2, 2) + ", " + game.move(2, 2, 1) + ", " + game.move(1, 1, 2) + ", " + game.move(2, 0, 1) + ", " + game.move(1, 0, 2) + ", " + game.move(2, 1, 1));
    }

    private final int n;
    private final int[] rows;
    private final int[] cols;
    private int diagonal;
    private int antiDiagonal;

    public DesignTicTacToe(int n) {
        this.n = n;
        rows = new int[n];
        cols = new int[n];
    }

    public int move(int row, int col, int player) {
        int delta = player == 1 ? 1 : -1;
        rows[row] += delta;
        cols[col] += delta;
        if (row == col) {
            diagonal += delta;
        }
        if (row + col == n - 1) {
            antiDiagonal += delta;
        }
        if (Math.abs(rows[row]) == n || Math.abs(cols[col]) == n || Math.abs(diagonal) == n || Math.abs(antiDiagonal) == n) {
            return player;
        }
        return 0;
    }
}
