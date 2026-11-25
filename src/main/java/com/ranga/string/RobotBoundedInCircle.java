package com.ranga.string;

/**
 * On an infinite plane a robot starts at {@code (0, 0)} facing north. It executes {@code instructions} forever, where {@code G} means go forward one unit, {@code L} turns left 90 degrees and {@code R} turns right 90 degrees. Return {@code true} if and only if there is a circle in the plane such that the robot never leaves it.
 *
 * <p><b>Algorithm Strategy (One Pass Is Enough):</b>
 * <ol>
 *   <li>Simulate the instructions once, tracking position and direction.</li>
 *   <li>If the robot is back at the origin, it repeats the same cycle forever: bounded.</li>
 *   <li>If it ends facing a different direction than north, then after at most four repetitions it has turned all the way around and returned to the origin: bounded.</li>
 *   <li>Otherwise it ends facing north away from the origin, so each repetition moves it farther: unbounded.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code GGLLGG}: the robot goes up 2, turns around, and goes back down 2, ending at the origin facing south.</li>
 *   <li>Result: {@code true}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — One pass.</li>
 *   <li><b>Space Complexity:</b> {@code O(1)} — Position and direction.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class RobotBoundedInCircle {

    public static void main(String[] args) {
        String[] inputs = {"GGLLGG", "GG", "GL", "GRGRGRG", ""};
        for (String instructions : inputs) {
            System.out.println("Input  : instructions = \"" + instructions + "\"");
            System.out.println("Output : " + isRobotBounded(instructions));
            System.out.println();
        }
    }

    public static boolean isRobotBounded(String instructions) {
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};
        int x = 0;
        int y = 0;
        int dir = 0;
        for (int i = 0; i < instructions.length(); i++) {
            char c = instructions.charAt(i);
            if (c == 'G') {
                x += dx[dir];
                y += dy[dir];
            } else if (c == 'L') {
                dir = (dir + 3) % 4;
            } else if (c == 'R') {
                dir = (dir + 1) % 4;
            }
        }
        return (x == 0 && y == 0) || dir != 0;
    }
}
