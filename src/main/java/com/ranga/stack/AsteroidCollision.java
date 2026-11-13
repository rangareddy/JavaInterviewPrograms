package com.ranga.stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Asteroids in a row are given as integers: the absolute value is the size and the sign is the direction (positive moves right, negative moves left). All move at the same speed. When two meet, the smaller one explodes, both explode if they are the same size, and asteroids moving in the same direction never meet. Return the state after all collisions.
 *
 * <p><b>Algorithm Strategy (Stack Simulation):</b>
 * <ol>
 *   <li>Only a right-moving asteroid followed by a left-moving one can collide. Keep a stack of the asteroids that survive so far.</li>
 *   <li>For each new asteroid, if it moves right (or the stack is empty, or the top moves left) it cannot collide with anything on the stack, so push it.</li>
 *   <li>Otherwise it moves left and the top moves right, so they collide: if the top is smaller, pop it and keep checking against the next top; if they are equal, pop the top and destroy the new one; if the top is bigger, the new asteroid is destroyed.</li>
 *   <li>The stack from bottom to top is the final state, in the original order.</li>
 * </ol>
 *
 * <p><b>Example walkthrough</b>
 * <ul>
 *   <li>For {@code [5, 10, -5]}: {@code -5} meets {@code 10} and is destroyed, leaving {@code [5, 10]}.</li>
 *   <li>For {@code [10, 2, -5]}: {@code -5} destroys {@code 2}, then meets {@code 10} and is destroyed, leaving {@code [10]}.</li>
 *   <li>For {@code [8, -8]}: both explode, leaving {@code []}.</li>
 * </ul>
 *
 * <p><b>Complexity Analysis:</b>
 * <ul>
 *   <li><b>Time Complexity:</b> {@code O(n)} — Every asteroid is pushed once and popped at most once.</li>
 *   <li><b>Space Complexity:</b> {@code O(n)} — The stack.</li>
 * </ul>
 *
 * @author Ranga Reddy
 */
public class AsteroidCollision {

    public static void main(String[] args) {
        int[][] inputs = {{5, 10, -5}, {8, -8}, {10, 2, -5}, {-2, -1, 1, 2}};
        for (int[] asteroids : inputs) {
            System.out.println("Input  : asteroids = " + Arrays.toString(asteroids));
            System.out.println("Output : " + Arrays.toString(asteroidCollision(asteroids)));
            System.out.println();
        }
    }

    public static int[] asteroidCollision(int[] asteroids) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (int asteroid : asteroids) {
            boolean alive = true;
            while (alive && asteroid < 0 && !stack.isEmpty() && stack.peek() > 0) {
                int top = stack.peek();
                if (top < -asteroid) {
                    stack.pop();
                } else if (top == -asteroid) {
                    stack.pop();
                    alive = false;
                } else {
                    alive = false;
                }
            }
            if (alive) {
                stack.push(asteroid);
            }
        }
        int[] result = new int[stack.size()];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }
        return result;
    }
}
